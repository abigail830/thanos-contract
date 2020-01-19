package com.thanos.contract.codegenerator.domain;

public class PlainFieldGenerator implements Generator {

    private String content;
    private Integer startIndex;
    private Integer length;

    public PlainFieldGenerator(String content, Integer startIndex, Integer length) {
        this.content = content;
        this.startIndex = startIndex;
        this.length = length;
    }

    @Override
    public String getExpectedValue() {
        return content;
    }

    @Override
    public String getExpectedValidation() {
        Integer endIndex = startIndex + length;
        return "Assert.assertEquals(\"" + content + "\", response.substring(" + startIndex + ", " + endIndex + "));";
    }
}
