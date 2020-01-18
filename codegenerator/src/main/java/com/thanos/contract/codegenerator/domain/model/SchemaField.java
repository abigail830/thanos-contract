package com.thanos.contract.codegenerator.domain.model;

import com.thanos.contract.codegenerator.domain.Generator;
import com.thanos.contract.codegenerator.domain.GeneratorFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchemaField {
    String name;
    String type;
    Integer length;
    String content;
    Generator generator;

    public SchemaField(String name, String type, Integer length, String content) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.content = content;
        this.generator = new GeneratorFactory().getGenerator(content);
    }

    @Override
    public String toString() {
        return "SchemaField{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", content='" + content + '\'' +
                '}';
    }

}
