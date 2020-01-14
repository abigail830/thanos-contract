package com.thanos.contract.mockserver.domain.contract;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.infrastructure.client.MockServerRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MockServerProcessor {

    private MockServerRepository mockServerRepository;

    public MockServerProcessor() {
        this.mockServerRepository = new MockServerRepositoryImpl();
    }

    public List<String> getAllContractIndex() {
        try {
            return mockServerRepository.getAllContractIndex();
        } catch (Exception e) {
            log.error("HTTP GET fail when getAllContractIndex: {}", e);
            return new ArrayList<>();
        }
    }

    public List<Contract> getContractByIndex(String index) {
        try {
            return mockServerRepository.getContractByIndex(index);
        } catch (Exception e) {
            log.error("HTTP GET fail when getContractByIndex[{}]: {}", index, e);
            return new ArrayList<>();
        }
    }

    public List<Schema> getSchemaByIndex(List<String> schemaNeeded) {
        try {
            return mockServerRepository.getSchemaByIndex(schemaNeeded);
        } catch (Exception e) {
            log.error("HTTP GET fail when getSchemaByIndex[{}]: {}", schemaNeeded, e);
            return new ArrayList<>();
        }
    }
}
