package com.thanos.contract.codegenerator.infrastructure.parser;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateParser {

    private MustacheFactory mf;

    public TemplateParser() {
        mf = new DefaultMustacheFactory();
    }

    public String parseJunitTemplate(Map<String, Object> content) throws IOException {
        Mustache mustache = mf.compile("template/junit.template");
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, content).flush();
        return writer.toString();
    }
}
