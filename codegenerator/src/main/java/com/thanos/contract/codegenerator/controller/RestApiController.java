package com.thanos.contract.codegenerator.controller;

import com.thanos.contract.codegenerator.controller.dto.ContractKeyDTO;
import com.thanos.contract.codegenerator.domain.CodeGeneratorService;
import com.thanos.contract.codegenerator.exception.BizException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/apis")
public class RestApiController {

    CodeGeneratorService codeGeneratorService;

    public RestApiController(CodeGeneratorService codeGeneratorService) {
        this.codeGeneratorService = codeGeneratorService;
    }

    @POST
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public String generate(ContractKeyDTO contractKeyDTO) {
        if (contractKeyDTO.isValid()) {
            return codeGeneratorService.generateJunit(contractKeyDTO.toContractKey());
        } else {
            throw new BizException("Invalid input parameter");
        }
    }

    @POST
    @Path("/generate/file")
    @Produces(MediaType.APPLICATION_JSON)
    public void generateToFile(ContractKeyDTO contractKeyDTO) {
        if (contractKeyDTO.isValid()) {
            codeGeneratorService.generateJunitToFile(contractKeyDTO.toContractKey());
        } else {
            throw new BizException("Invalid input parameter");
        }
    }

}
