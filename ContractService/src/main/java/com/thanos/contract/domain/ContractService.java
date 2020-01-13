package com.thanos.contract.domain;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class ContractService {

    private static ContractService contractService = null;

    private ContractService() {
    }

    public static ContractService getInstance() {
        if (contractService == null) {
            contractService = new ContractService();
        }
        return contractService;
    }
}
