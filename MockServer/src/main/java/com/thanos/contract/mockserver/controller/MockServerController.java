package com.thanos.contract.mockserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.thanos.contract.mockserver.domain.contract.MockServerProcessor;
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
import java.util.stream.Collectors;

@Slf4j
public class MockServerController {

    ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private List<String> startedIndexs = new ArrayList<>();

    private MockMappingService mockMappingService;
    private MockServerProcessor mockServerProcessor;
    private AsyncEventBus asyncEventBus;

    public MockServerController() {
        mockMappingService = new MockMappingService();
        mockServerProcessor = new MockServerProcessor();

        asyncEventBus = EventBusFactory.getInstance();
        asyncEventBus.register(this);
    }

    /**
     * Group mock server by provider-consumer
     */
    public synchronized void initMock() {
        startedIndexs = mockMappingService.getAllMockMapping().stream()
                .map(MockMapping::getIndex).collect(Collectors.toList());

        final List<String> allContractIndex = mockServerProcessor.getAllContractIndex();

        allContractIndex.stream().filter(index -> !startedIndexs.contains(index))
                .forEach(this::createNewMockForIndex);
    }

    void createNewMockForIndex(String index) {
        log.info("Creating new mock server: {}", index);
        executor.execute(new MockServerThread(index, mockServerProcessor));
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
