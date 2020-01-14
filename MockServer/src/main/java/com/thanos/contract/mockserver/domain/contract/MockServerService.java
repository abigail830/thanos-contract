package com.thanos.contract.mockserver.domain.contract;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.infrastructure.client.MockServerRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MockServerService {

    private MockServerRepository mockServerRepository;

    public MockServerService() {
        this.mockServerRepository = new MockServerRepositoryImpl();
    }

    public List<String> getAllContractIndex() {
        return mockServerRepository.getAllContractIndex();
    }

    public List<Contract> getContractByIndex(String index) {
        return mockServerRepository.getContractByIndex(index);
    }

    public List<Schema> getSchemaByIndex(List<String> schemaNeeded) {
        return mockServerRepository.getSchemaByIndex(schemaNeeded);

    }
}
