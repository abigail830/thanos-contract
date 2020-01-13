package com.thanos.contract.mockserver.domain.contract;

import java.io.IOException;
import java.util.List;

public interface ContractRepository {

    List<String> getAllContractIndex() throws IOException;

    List<Contract> getContractByIndex(String index) throws IOException;

    List<Schema> getSchemaByIndex(List<String> schemaNeeded) throws IOException;
}
