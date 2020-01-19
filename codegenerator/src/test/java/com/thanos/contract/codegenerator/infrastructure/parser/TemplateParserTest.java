package com.thanos.contract.codegenerator.infrastructure.parser;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class TemplateParserTest {

    @Test
    public void parseJunitTemplate() throws IOException {

        Map<String, Object> content = new HashMap<>();
        content.put("host", "127.0.0.1");
        content.put("port", "12345");
        content.put("plainTextValidation", Arrays.asList("assertTrue(true);", "assertFalse(false);"));

        final TemplateParser templateParser = new TemplateParser();
        final String result = templateParser.parseJunitTemplate(content);

        assertTrue(result.contains("127.0.0.1"));
        assertTrue(result.contains("12345"));
        assertTrue(result.contains("assertTrue(true);"));
        assertTrue(result.contains("assertFalse(false);"));
    }
}