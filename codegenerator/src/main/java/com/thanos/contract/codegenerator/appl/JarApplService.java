package com.thanos.contract.codegenerator.appl;

import com.thanos.contract.codegenerator.api.dto.ContractDTO;
import com.thanos.contract.codegenerator.domain.CoreGeneratorService;
import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.infrastructure.JsonUtil;

/**
 * This is for using as Jar library
 */
public class JarApplService {

    public String generate(String contractDtoJson) {
        final ContractDTO contractDTO = JsonUtil.toObject(contractDtoJson, ContractDTO.class);
        final CombinedContext combinedContext = contractDTO.toCombinedContext();
        return new CoreGeneratorService().generateToString(combinedContext);
    }

}
