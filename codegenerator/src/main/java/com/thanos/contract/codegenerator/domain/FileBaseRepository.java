package com.thanos.contract.codegenerator.domain;

import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.exception.InfraException;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.Contract;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.Schema;

public interface FileBaseRepository {

    Contract getContractByKey(String key) throws InfraException;

    Schema getSchemaByKey(String key) throws InfraException;

    CombinedContext getCombinedContextByContractKey(String contractKey);
}
