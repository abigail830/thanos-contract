package com.thanos.contract.mockserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.thanos.contract.mockserver.domain.contract.MockServerService;
import com.thanos.contract.mockserver.domain.contract.MockServerThread;
import com.thanos.contract.mockserver.domain.mapping.MockMapping;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import com.thanos.contract.mockserver.exception.BizException;
import com.thanos.contract.mockserver.infrastructure.eventbus.EventBusFactory;
import com.thanos.contract.mockserver.infrastructure.eventbus.NewMockMappingEvent;
import com.thanos.contract.mockserver.infrastructure.eventbus.ShutdownMockEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MockServerController {

    ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private List<String> startedIndexs = new ArrayList<>();

    private MockMappingService mockMappingService;
    private MockServerService mockServerService;
    private AsyncEventBus asyncEventBus;
    private Boolean standalone;

    public MockServerController(Boolean standalone) {
        this.standalone = standalone;
        mockMappingService = new MockMappingService();
        mockServerService = new MockServerService();

        asyncEventBus = EventBusFactory.getInstance();
        asyncEventBus.register(this);
    }

    /**
     * Group mock server by provider-consumer
     */
    public synchronized void initMock() {
        startedIndexs = mockMappingService.getAllMockMappingIndexs();

        final List<String> allContractIndex = mockServerService.getAllContractIndex();

        allContractIndex.stream().filter(index -> !startedIndexs.contains(index))
                .forEach(this::createNewMockForIndex);
    }

    void createNewMockForIndex(String index) {
        log.info("Creating new mock server: {}", index);
        executor.execute(new MockServerThread(index, mockServerService));
    }

    @Subscribe
    public void receiveNewMockMappingEvent(NewMockMappingEvent newMockMappingEvent) {
        try {
            mockMappingService.addNewMockMapping(
                    new MockMapping(newMockMappingEvent.getIndex(), newMockMappingEvent.getPort()));

        } catch (BizException ex) {
            asyncEventBus.post(
                    new ShutdownMockEvent(newMockMappingEvent.getIndex(), newMockMappingEvent.getPort()));
        }
    }

}
