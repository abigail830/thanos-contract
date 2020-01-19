package com.thanos.contract.codegenerator.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorFactory {

    private static final String PATTERN_IN_STRING = "^\\s?([A-Za-z0-a_]*)\\((.*)\\)\\s?$";

    private static final Pattern FUNCTION_PATTERN = Pattern.compile(PATTERN_IN_STRING);

    public Generator getGenerator(String content, Integer startIndex, Integer length) {

        if (content.matches(PATTERN_IN_STRING)) {
            Matcher matcher = FUNCTION_PATTERN.matcher(content);
            if (matcher.find()) {
                String functionName = matcher.group(1);
                if (RegexFieldGenerator.NAME.equals(functionName)) {
                    String regexpValue = matcher.group(2);
                    return new RegexFieldGenerator(regexpValue, startIndex, length);
                }
            }
        } else {
            return new PlainFieldGenerator(content, startIndex, length);
        }
        throw new RuntimeException("parse validator failed.");
    }
}
