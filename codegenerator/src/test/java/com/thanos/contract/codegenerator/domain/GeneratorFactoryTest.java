package com.thanos.contract.codegenerator.domain;

import org.junit.Test;

import static org.junit.Assert.fail;

public class GeneratorFactoryTest {

    @Test
    public void getGenerator_plain() {
        final Generator generator = new GeneratorFactory().getGenerator("1", 1, 2);
        if (!(generator instanceof PlainFieldGenerator)) fail();
    }

    @Test
    public void getGenerator_regex() {
        final Generator generator = new GeneratorFactory().getGenerator("regex(\\d{6})", 1, 2);
        if (!(generator instanceof RegexFieldGenerator)) fail();
    }
}