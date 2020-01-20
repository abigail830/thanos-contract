package com.thanos.contract.codegenerator.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegexFieldGeneratorTest {

    @Test
    public void getExpectedValue() {
        final RegexFieldGenerator regexField = new RegexFieldGenerator("0|1", 0, 1);
        assertTrue(regexField.getExpectedValue().matches("0|1"));
    }

    @Test
    public void getExpectedValidation() {
        final RegexFieldGenerator regexField = new RegexFieldGenerator("0|1", 0, 1);
        System.out.println(regexField.getExpectedValidation());
        assertEquals("Assert.assertTrue(response.substring(0, 1).matches(\"0|1\"));", regexField.getExpectedValidation());
    }

    @Test
    public void getExpectedValidation_range() {
        final RegexFieldGenerator regexField = new RegexFieldGenerator("\\d{2}", 0, 1);
        final String result = regexField.getExpectedValidation();
        System.out.println(result);
        assertEquals("Assert.assertTrue(response.substring(0, 1).matches(\"\\\\d{2}\"));", result);
    }

}