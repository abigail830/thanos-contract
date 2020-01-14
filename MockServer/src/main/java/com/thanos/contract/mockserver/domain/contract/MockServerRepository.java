package com.thanos.contract.mockserver.domain.contract;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.exception.BizException;

import java.util.List;

public interface MockServerRepository {

    List<String> getAllContractIndex() throws BizException;

    List<Contract> getContractByIndex(String index) throws BizException;

    List<Schema> getSchemaByIndex(List<String> schemaNeeded) throws BizException;
}
