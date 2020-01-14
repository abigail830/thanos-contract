package com.thanos.contract.mockserver.infrastructure.client;

import com.thanos.contract.mockserver.domain.contract.MockServerRepository;
import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.exception.BizException;
import com.thanos.contract.mockserver.infrastructure.client.dto.ContractDTO;
import com.thanos.contract.mockserver.infrastructure.client.dto.SchemaDTO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MockServerRepositoryImpl implements MockServerRepository {

    private ContractRestClient restClient = new ContractRestClient();

    @Override
    public List<String> getAllContractIndex() throws BizException {
        try {
            return restClient.getAllContractIndex();
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    @Override
    public List<Contract> getContractByIndex(String index) throws BizException {
        try {
            return restClient.getContractByIndex(index).stream()
                    .map(ContractDTO::toContract).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schema> getSchemaByIndex(List<String> schemaNeeded) throws BizException {
        try {
            return restClient.getSchemaByIndex(schemaNeeded).stream()
                    .map(SchemaDTO::toSchema).collect(Collectors.toList());
        } catch (IOException e) {
            throw new BizException(e.getMessage(), e);
        }
    }
}
