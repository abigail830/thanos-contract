package com.thanos.contract.codegenerator.infrastructure.parser;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.thanos.contract.codegenerator.domain.model.JunitTemplateFields;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateParser {

    private MustacheFactory mf;

    public TemplateParser() {
        mf = new DefaultMustacheFactory();
    }

    public String parseJunitTemplateToString(JunitTemplateFields junitTemplateFields) throws IOException {
        Mustache mustache = mf.compile("template/junit.template");
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, junitTemplateFields).flush();
        return writer.toString().replaceAll("&quot;", "\"");
    }

    public void parseJunitTemplateToFile(JunitTemplateFields junitTemplateFields, String basePath) throws IOException {
        final String result = parseJunitTemplateToString(junitTemplateFields);

        String fileName = junitTemplateFields.getClassName() + "Test.java";
        Files.write(Paths.get(basePath, fileName), result.getBytes());
    }
}
