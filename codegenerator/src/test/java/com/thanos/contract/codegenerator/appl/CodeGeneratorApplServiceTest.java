package com.thanos.contract.codegenerator.appl;

import com.thanos.contract.codegenerator.domain.CoreGeneratorService;
import com.thanos.contract.codegenerator.infrastructure.cache.FileBaseCache;
import com.thanos.contract.codegenerator.infrastructure.cache.FileBaseCacheRepoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class CodeGeneratorApplServiceTest {

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get("target/generated-test-sources/Consumer1ProviderTest.java"));
        Files.deleteIfExists(Paths.get("target/generated-test-sources/Consumer2ProviderTest.java"));
    }

    private CodeGeneratorApplService codeGeneratorApplService;

    @Before
    public void setUp() throws Exception {
        final FileBaseCache fileBaseCache = new FileBaseCache();
        fileBaseCache.init();
        final FileBaseCacheRepoImpl fileBaseCacheRepo = new FileBaseCacheRepoImpl(fileBaseCache);
        codeGeneratorApplService = new CodeGeneratorApplService(fileBaseCacheRepo, new CoreGeneratorService());
    }

    @Test
    public void generateJunit_schema1() {
        String contractKey = "provider" + '-' + "consumer1" + '-' + "v2" + '-' + "test_case_1";
        final String result = codeGeneratorApplService.generateJunit(contractKey);
        System.out.println(result);
        assertTrue(result.contains("Assert.assertEquals(\"This is the expected memo for choice 1  \", response.substring(10, 50));"));
        assertTrue(result.contains("Assert.assertEquals(\"SUPPLEMENT\", response.substring(0, 10));"));
        assertTrue(result.contains("private static final String HOST = \"127.0.0.1\";"));
    }

    @Test
    public void generateJunit_schema2() {
        String contractKey = "provider" + '-' + "consumer2" + '-' + "v1" + '-' + "test_case_2";
        codeGeneratorApplService.generateJunitToFile(contractKey, "target/generated-test-sources");
        assertTrue(Files.exists(Paths.get("target/generated-test-sources/Consumer2ProviderTest.java")));
    }

    @Test
    public void generateJunitToFile() {
        String contractKey = "provider" + '-' + "consumer1" + '-' + "v2" + '-' + "test_case_1";
        codeGeneratorApplService.generateJunitToFile(contractKey, "target/generated-test-sources");
        assertTrue(Files.exists(Paths.get("target/generated-test-sources/Consumer1ProviderTest.java")));

    }
}