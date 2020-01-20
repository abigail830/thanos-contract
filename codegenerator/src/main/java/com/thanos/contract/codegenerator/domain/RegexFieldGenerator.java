package com.thanos.contract.codegenerator.domain;

import com.mifmif.common.regex.Generex;

public class RegexFieldGenerator implements Generator {

    private String content;
    private Integer startIndex;
    private Integer length;

    public static final String NAME = "regex";

    public RegexFieldGenerator(String content, Integer startIndex, Integer length) {
        this.content = content;
        this.startIndex = startIndex;
        this.length = length;
    }

    @Override
    public String getExpectedValue() {
        Generex generex = new Generex(content);
        return generex.random();
    }

    @Override
    public String getExpectedValidation() {
        Integer endIndex = startIndex + length;
        if (content.contains("\\")) {
            content = content.replace("\\", "\\\\");
        }
        return "Assert.assertTrue(response.substring(" + startIndex + ", " + endIndex + ").matches(\"" + content + "\"));";
    }
}
