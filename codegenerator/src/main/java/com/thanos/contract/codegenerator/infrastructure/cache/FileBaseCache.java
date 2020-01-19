package com.thanos.contract.codegenerator.infrastructure.cache;

import com.thanos.contract.codegenerator.infrastructure.cache.dto.Contract;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.Schema;
import com.thanos.contract.codegenerator.infrastructure.parser.ContractFileParser;
import com.thanos.contract.codegenerator.infrastructure.parser.SchemaFileParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileBaseCache {

    private static final String CONTRACTS_PATH = "contracts";
    private static final String SCHEMAS_PATH = "schemas";

    private static Map<String, Contract> contractsMap = new HashMap<>();
    private static Map<String, Schema> schemasMap = new HashMap<>();


    static synchronized Optional<Contract> getContractByKey(String key) {
        final Contract result = contractsMap.get(key);
        return Optional.ofNullable(result);
    }


    static synchronized Optional<Schema> getSchemaByIndex(String index) {
        final Schema schema = schemasMap.get(index);
        return Optional.ofNullable(schema);
    }

    static List<String> getPaths(String path) {
        final URL resource = FileBaseCache.class.getClassLoader().getResource(path);
        try {
            return Files.walk(Paths.get(resource.getPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Fail to get paths");
            return Collections.EMPTY_LIST;
        }
    }

    public synchronized void init() {
        initContractMap();
        initSchemaMap();
    }

    void initContractMap() {
        final List<String> contractPaths = getPaths(CONTRACTS_PATH);
        final ContractFileParser contractParser = new ContractFileParser();

        contractPaths.stream().map(contractParser::parse).flatMap(Collection::stream)
                .forEach(contract -> contractsMap.put(contract.getKey(), contract));
    }

    void initSchemaMap() {
        final List<String> schemaPaths = getPaths(SCHEMAS_PATH);
        final SchemaFileParser schemaParser = new SchemaFileParser();
        schemaPaths.stream().map(schemaParser::parse).flatMap(Collection::stream)
                .forEach(schema -> schemasMap.put(schema.getIndex(), schema));
    }
}
