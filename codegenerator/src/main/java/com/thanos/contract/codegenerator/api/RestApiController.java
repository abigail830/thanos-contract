package com.thanos.contract.codegenerator.api;

import com.thanos.contract.codegenerator.api.dto.ContractKeyDTO;
import com.thanos.contract.codegenerator.appl.CodeGeneratorApplService;
import com.thanos.contract.codegenerator.exception.BizException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/apis")
public class RestApiController {

    CodeGeneratorApplService codeGeneratorApplService;

    public RestApiController(CodeGeneratorApplService codeGeneratorApplService) {
        this.codeGeneratorApplService = codeGeneratorApplService;
    }

    @POST
    @Path("/generateToString")
    @Produces(MediaType.APPLICATION_JSON)
    public String generate(ContractKeyDTO contractKeyDTO) {
        if (contractKeyDTO.isValid()) {
            return codeGeneratorApplService.generateJunit(contractKeyDTO.toContractKey());
        } else {
            throw new BizException("Invalid input parameter");
        }
    }

    @POST
    @Path("/generateToString/file")
    @Produces(MediaType.APPLICATION_JSON)
    public void generateToFile(ContractKeyDTO contractKeyDTO) {
        if (contractKeyDTO.isValid()) {
            codeGeneratorApplService.generateJunitToFile(contractKeyDTO.toContractKey());
        } else {
            throw new BizException("Invalid input parameter");
        }
    }

}
