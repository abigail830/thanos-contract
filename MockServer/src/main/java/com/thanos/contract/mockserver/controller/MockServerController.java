package com.thanos.contract.mockserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.thanos.contract.mockserver.domain.contract.ContractService;
import com.thanos.contract.mockserver.domain.contract.mock.MockServerHandler;
import com.thanos.contract.mockserver.domain.mapping.MockMapping;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import com.thanos.contract.mockserver.infrastructure.eventbus.ContractUpdatedEvent;
import com.thanos.contract.mockserver.infrastructure.eventbus.EventBusFactory;
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
    private ContractService contractService;
    private AsyncEventBus asyncEventBus;

    public MockServerController() {
        mockMappingService = MockMappingService.getInstance();
        contractService = new ContractService();

        asyncEventBus = EventBusFactory.getInstance();
        asyncEventBus.register(this);
    }

    /**
     * Group mock server by provider-consumer
     */
    public synchronized void initMock() {
        startedIndexs = mockMappingService.getAllMockMapping().stream()
                .map(MockMapping::getIndex).collect(Collectors.toList());

        final List<String> allContractIndex = contractService.getAllContractIndex();

        allContractIndex.stream().filter(index -> !startedIndexs.contains(index))
                .forEach(this::createNewMockForIndex);
    }

    void createNewMockForIndex(String index) {
        log.info("Creating new mock server: {}", index);
        executor.execute(new MockServerHandler(index, contractService, asyncEventBus));
    }

    @Subscribe
    public void receiveContractUpdateEvent(ContractUpdatedEvent contractUpdatedEvent) {
        if (!startedIndexs.contains(contractUpdatedEvent.getIndex())) {
            createNewMockForIndex(contractUpdatedEvent.getIndex());
            log.info("Receive ContractUpdateEvent and created MockServer: {}",
                    contractUpdatedEvent.getIndex());
        }
    }

}
