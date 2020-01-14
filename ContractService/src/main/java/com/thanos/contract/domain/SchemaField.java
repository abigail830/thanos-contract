package com.thanos.contract.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SchemaField {
    String name;
    String type;
    Integer length;
    String content;

    public SchemaField(String name, String type, Integer length, String content) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.content = content;

    }


}
