package com.thanos.contract.codegenerator.infrastructure.cache.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchemaField {
    String name;
    String type;
    Integer length;
    String content;
    Integer startIndex;

    public SchemaField(String name, String type, Integer length, String content, Integer startIndex) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.content = content;
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "SchemaField{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", startIndex=" + startIndex +
                ", length=" + length +
                ", content='" + content + '\'' +
                '}';
    }

}
