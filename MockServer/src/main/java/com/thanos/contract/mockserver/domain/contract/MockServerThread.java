package com.thanos.contract.mockserver.domain.contract;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.CharStreams;
import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.MessageService;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.infrastructure.eventbus.EventBusFactory;
import com.thanos.contract.mockserver.infrastructure.eventbus.NewMockMappingEvent;
import com.thanos.contract.mockserver.infrastructure.eventbus.ShutdownMockEvent;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the handler for each MockServer thread
 */
@Slf4j
public class MockServerThread implements Runnable {

    private static final String CRLF = System.lineSeparator();

    private String index;
    private int port;
    private ServerSocket ss;
    private List<Contract> contractList = new ArrayList<>();
    private List<Schema> schemaList = new ArrayList<>();
    private List<String> schemaNeeded = new ArrayList<>();
    private AsyncEventBus asyncEventBus;

    private MockServerService mockServerProcessor;

    public MockServerThread(String index, MockServerService mockServerProcessor) {
        this.mockServerProcessor = mockServerProcessor;
        this.index = index;

        asyncEventBus = EventBusFactory.getInstance();
        asyncEventBus.register(this);

        refreshLocalCache(index);
    }

    private synchronized void refreshLocalCache(String index) {
        reloadContracts(index);
        reloadSchemas();
    }

    private void reloadSchemas() {
        if (!schemaNeeded.isEmpty()) {
            final List<Schema> schemaByIndex = mockServerProcessor.getSchemaByIndex(schemaNeeded);
            if (!schemaByIndex.isEmpty()) {
                schemaList = schemaByIndex;
                log.info("Schema List reloaded with {} records now", schemaList.size());
            }
        }
    }

    private void reloadContracts(String index) {
        final List<Contract> contractByIndex = mockServerProcessor.getContractByIndex(index);
        if (!contractByIndex.isEmpty()) {
            contractList = contractByIndex;
            schemaNeeded = contractList.stream()
                    .map(Contract::getSchemaIndex).collect(Collectors.toList());
            log.info("Contract List reloaded with {} records now", contractList.size());
        }
    }

    @Override
    public void run() {
        try {
            this.ss = startServerSocket();
            while (true) {
                Socket socket = ss.accept();
                log.info("Consumer: " + socket.getInetAddress().getLocalHost() + " connected");
                new Thread(Unchecked.runnable(() -> {
                    String input = CharStreams.toString(new InputStreamReader(socket.getInputStream()));

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(process(input) + CRLF);
                    bw.flush();
                    socket.shutdownOutput();
                })).start();
            }
        } catch (IOException | UncheckedIOException ioEx) {
            log.warn("IOException when create socket");
            ioEx.printStackTrace();
        }
    }

    void stop() {
        try {
            if (ss != null) {
                ss.close();
            }
        } catch (IOException e) {
            log.warn("{}", e);
        }

    }

    private ServerSocket startServerSocket() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        this.port = ss.getLocalPort();
        log.info("MockServer for {} start up @ {}", index, port);

        asyncEventBus.post(new NewMockMappingEvent(index, port));
        return ss;
    }

    String process(String inputRequest) {
        final MessageService messageService = new MessageService(contractList, schemaList);
        return messageService.process(inputRequest);
    }

    @Subscribe
    public void receiveShutdownMockEvent(ShutdownMockEvent shutdownMockEvent) {
        if (this.index.equals(shutdownMockEvent.getIndex()) && this.port == shutdownMockEvent.getPort()) {
            log.info("Duplicate MockServer created, going to stop {}", shutdownMockEvent);
            stop();
        }
    }

}
