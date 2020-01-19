package com.thanos.contract.codegenerator.infrastructure.cache;

import com.thanos.contract.codegenerator.domain.FileBaseRepository;
import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.domain.model.CombinedField;
import com.thanos.contract.codegenerator.exception.InfraException;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.Contract;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.ContractField;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.Schema;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.SchemaField;

import java.util.LinkedList;

public class FileBaseCacheRepoImpl implements FileBaseRepository {

    FileBaseCache fileBaseCache;

    public FileBaseCacheRepoImpl(FileBaseCache fileBaseCache) {
        this.fileBaseCache = fileBaseCache;
    }

    @Override
    public Contract getContractByKey(String key) throws InfraException {
        return FileBaseCache.getContractByKey(key).orElseThrow(() -> new InfraException("Contract not found"));
    }

    @Override
    public Schema getSchemaByKey(String key) throws InfraException {
        return FileBaseCache.getSchemaByIndex(key).orElseThrow(() -> new InfraException("Schema not found"));
    }

    @Override
    public CombinedContext getCombinedContextByContractKey(String contractKey) {

        final Contract contract = getContractByKey(contractKey);
        final Schema schema = getSchemaByKey(contract.getSchemaIndex());

        final LinkedList<CombinedField> combinedReqFields =
                generateCombinedFields(contract.getReq(), schema.getRequest());

        final LinkedList<CombinedField> combinedResFields =
                generateCombinedFields(contract.getRes(), schema.getResponse());

        return new CombinedContext(contract.getName(), contract.getVersion(),
                contract.getConsumer(), contract.getProvider(), combinedReqFields, combinedResFields);
    }

    LinkedList<CombinedField> generateCombinedFields(LinkedList<ContractField> contractFields,
                                                     LinkedList<SchemaField> schemaFields) {
        LinkedList<CombinedField> result = new LinkedList<>();

        for (SchemaField requestField : schemaFields) {
            final String content = contractFields.stream()
                    .filter(contractField -> contractField.getName().equalsIgnoreCase(requestField.getName()))
                    .map(ContractField::getContent)
                    .findFirst()
                    .orElse(requestField.getContent());

            result.add(new CombinedField(requestField.getName(), requestField.getType(),
                    requestField.getLength(), content, requestField.getStartIndex()));
        }
        return result;
    }
}
