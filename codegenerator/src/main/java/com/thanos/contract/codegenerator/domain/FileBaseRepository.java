package com.thanos.contract.codegenerator.domain;

import com.thanos.contract.codegenerator.domain.model.Contract;
import com.thanos.contract.codegenerator.domain.model.Schema;
import com.thanos.contract.codegenerator.exception.InfraException;

public interface FileBaseRepository {

    Contract getContractByKey(String key) throws InfraException;

    Schema getSchemaByKey(String key) throws InfraException;
}
