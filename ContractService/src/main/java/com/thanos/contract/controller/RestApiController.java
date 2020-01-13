package com.thanos.contract.controller;

import com.thanos.contract.domain.ContractService;

import javax.ws.rs.Path;

@Path("/apis")
public class RestApiController {

    private ContractService contractService;

    public RestApiController() {
        contractService = ContractService.getInstance();
    }


}
