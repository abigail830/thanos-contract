package com.thanos.contract.codegenerator.infrastructure.cache;

import com.thanos.contract.codegenerator.domain.FileBaseRepository;
import com.thanos.contract.codegenerator.domain.model.Contract;
import com.thanos.contract.codegenerator.domain.model.Schema;
import com.thanos.contract.codegenerator.exception.InfraException;

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
}
