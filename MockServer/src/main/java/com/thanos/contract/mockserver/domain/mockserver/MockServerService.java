package com.thanos.contract.mockserver.domain.mockserver;

import com.thanos.contract.mockserver.domain.mockserver.model.Contract;
import com.thanos.contract.mockserver.domain.mockserver.model.Schema;
import com.thanos.contract.mockserver.infrastructure.cache.FileBaseCacheRepoImpl;
import com.thanos.contract.mockserver.infrastructure.client.ContractRestClientRepoImpl;
import com.thanos.contract.mockserver.infrastructure.parser.PropertiesParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MockServerService {

    private MockServerRepository mockServerRepository;

    public MockServerService() {
        if (PropertiesParser.getStandaloneFlag()) {
            this.mockServerRepository = new FileBaseCacheRepoImpl();
        } else {
            this.mockServerRepository = new ContractRestClientRepoImpl();
        }
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
