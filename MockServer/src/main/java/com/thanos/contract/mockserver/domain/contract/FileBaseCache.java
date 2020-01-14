package com.thanos.contract.mockserver.domain.contract;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.Schema;
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

    private static Map<String, List<Contract>> contractsMap = new HashMap<>();
    private static Map<String, List<Schema>> schemasMap = new HashMap<>();

//    static {
//        final List<String> contractPaths = getAllContractPaths();
//        final ContractParser contractParser = new ContractParser();
//
//        contracts = contractPaths.stream()
//                .map(contractParser::parse)
//                .flatMap(Collection::stream).collect(Collectors.toList());
//    }
//
//    static {
//        final List<String> schemaPaths = getAllSchemaPaths();
//        final SchemaFileParser schemaParser = new SchemaFileParser();
//        schemas = schemaPaths.stream()
//                .map(schemaParser::parse)
//                .flatMap(Collection::stream).collect(Collectors.toList());
//    }

    static List<String> getAllContractPaths() {
        final URL resource = FileBaseCache.class.getClassLoader().getResource(CONTRACTS_PATH);
        try {
            return Files.walk(Paths.get(resource.getPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Fail to getAllContractPaths");
            return Collections.EMPTY_LIST;
        }
    }

    static List<String> getAllSchemaPaths() {
        final URL resource = FileBaseCache.class.getClassLoader().getResource(SCHEMAS_PATH);
        try {
            return Files.walk(Paths.get(resource.getPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Fail to getAllContractPaths");
            return Collections.EMPTY_LIST;
        }
    }

    public static synchronized List<Contract> getContractByIndex(String index) {
        final List<Contract> result = contractsMap.get(index);
        return Optional.ofNullable(result).orElse(new ArrayList<>());
    }

    public static synchronized List<Schema> getSchemaByIndex(List<String> schemaNeeded) {
        final List<Schema> result = schemaNeeded.stream()
                .map(s -> schemasMap.get(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return Optional.ofNullable(result).orElse(new ArrayList<>());
    }
}
