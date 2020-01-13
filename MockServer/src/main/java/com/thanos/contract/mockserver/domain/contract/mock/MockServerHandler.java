package com.thanos.contract.mockserver.domain.contract.mock;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.CharStreams;
import com.thanos.contract.mockserver.domain.contract.Contract;
import com.thanos.contract.mockserver.domain.contract.ContractService;
import com.thanos.contract.mockserver.domain.contract.Schema;
import com.thanos.contract.mockserver.infrastructure.eventbus.ContractUpdatedEvent;
import com.thanos.contract.mockserver.infrastructure.eventbus.EventBusFactory;
import com.thanos.contract.mockserver.infrastructure.eventbus.NewMockMappingEvent;
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
public class MockServerHandler implements Runnable {

    private static final String CRLF = System.lineSeparator();

    private String index;
    private List<Contract> contractList = new ArrayList<>();
    private List<Schema> schemaList = new ArrayList<>();
    private ContractService contractService;

    public MockServerHandler(String index, ContractService contractService, AsyncEventBus asyncEventBus) {
        this.contractService = contractService;
        this.index = index;

        asyncEventBus.register(this);
        refreshLocalCache(index);
    }

    private synchronized void refreshLocalCache(String index) {
        reloadContracts(index);
        reloadSchemas();
    }

    private void reloadSchemas() {
        final List<String> schemaNeeded = contractList.stream()
                .map(Contract::getSchemaIndex).collect(Collectors.toList());
        final List<Schema> schemaByIndex = contractService.getSchemaByIndex(schemaNeeded);
        if (!schemaByIndex.isEmpty())
            schemaList = schemaByIndex;
    }

    private void reloadContracts(String index) {
        final List<Contract> contractByIndex = contractService.getContractByIndex(index);
        if (!contractByIndex.isEmpty())
            contractList = contractByIndex;
    }

    @Override
    public void run() {
        try (ServerSocket ss = startServerSocket()) {
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

    private ServerSocket startServerSocket() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        log.info("MockServer for {} start up @ {}", index, ss.getLocalPort());

        EventBusFactory.getInstance().post(
                new NewMockMappingEvent(contractList.get(0).getIndex(), ss.getLocalPort()));

        return ss;
    }


    String process(String inputRequest) {
        final MessageService messageService = new MessageService(contractList, schemaList);
        return messageService.process(inputRequest);
    }

    @Subscribe
    public void receiveContractUpdateEvent(ContractUpdatedEvent contractUpdatedEvent) {
        if (contractUpdatedEvent.getIndex().equals(this.index)) {
            refreshLocalCache(index);
        }
    }

    @Subscribe
    public void receiveSchemaUpdateEvent() {

    }


}
