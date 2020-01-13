package com.thanos.contract.mockserver.domain.contract;

import com.thanos.contract.mockserver.infrastructure.client.ContractRestClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ContractService {

    ContractRepository contractRepository;

    public ContractService() {
        this.contractRepository = new ContractRestClient();
    }

    public List<String> getAllContractIndex() {
        try {
            return contractRepository.getAllContractIndex();
        } catch (IOException e) {
            log.error("HTTP GET fail when getAllContractIndex: {}", e);
            return new ArrayList<>();
        }
    }

    public List<Contract> getContractByIndex(String index) {
        try {
            return contractRepository.getContractByIndex(index);
        } catch (IOException e) {
            log.error("HTTP GET fail when getContractByIndex[{}]: {}", index, e);
            return new ArrayList<>();
        }
    }

    public List<Schema> getSchemaByIndex(List<String> schemaNeeded) {
        try {
            return contractRepository.getSchemaByIndex(schemaNeeded);
        } catch (IOException e) {
            log.error("HTTP GET fail when getSchemaByIndex[{}]: {}", schemaNeeded, e);
            return new ArrayList<>();
        }
    }
}
