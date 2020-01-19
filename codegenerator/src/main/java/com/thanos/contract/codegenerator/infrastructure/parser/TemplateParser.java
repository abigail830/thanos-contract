package com.thanos.contract.codegenerator.infrastructure.parser;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.thanos.contract.codegenerator.domain.model.JunitTemplateFields;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TemplateParser {

    private MustacheFactory mf;

    public TemplateParser() {
        mf = new DefaultMustacheFactory();
    }

    public String parseJunitTemplateToString(JunitTemplateFields junitTemplateFields) throws IOException {
        Mustache mustache = mf.compile("template/junit.template");
        final StringWriter writer = new StringWriter();
        mustache.execute(writer, junitTemplateFields).flush();
        return writer.toString();
    }

    public void parseJunitTemplateToFile(JunitTemplateFields junitTemplateFields, String outputFile) throws IOException {
        Mustache mustache = mf.compile("template/junit.template");
        final PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile), true);
        mustache.execute(writer, junitTemplateFields).flush();

    }
}
