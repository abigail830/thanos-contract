package com.thanos.contract.codegenerator.domain;

import com.thanos.contract.codegenerator.domain.model.Contract;
import com.thanos.contract.codegenerator.domain.model.Schema;

public class CodeGeneratorService {

    FileBaseRepository fileBaseRepository;

    public CodeGeneratorService(FileBaseRepository fileBaseRepository) {
        this.fileBaseRepository = fileBaseRepository;
    }

    public String generate(String contractKey) {

        final Contract contract = fileBaseRepository.getContractByKey(contractKey);

        final String schemaKey = contract.getSchemaIndex();
        final Schema schemaByKey = fileBaseRepository.getSchemaByKey(schemaKey);

        // TODO: generate detail logic

        return null;
    }


}
