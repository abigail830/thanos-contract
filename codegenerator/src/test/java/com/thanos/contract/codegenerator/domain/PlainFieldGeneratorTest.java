package com.thanos.contract.codegenerator.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlainFieldGeneratorTest {

    @Test
    public void getExpectedValue() {
        final PlainFieldGenerator content = new PlainFieldGenerator("content", 0, 7);
        assertEquals("content", content.getExpectedValue());
    }

    @Test
    public void getExpectedValidation() {
        final PlainFieldGenerator content = new PlainFieldGenerator("content", 0, 7);
        System.out.println(content.getExpectedValidation());
        assertEquals("Assert.assertEquals(\"content\", response.substring(0, 7));", content.getExpectedValidation());
    }
}