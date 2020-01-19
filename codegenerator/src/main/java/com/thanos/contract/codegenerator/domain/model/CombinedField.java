package com.thanos.contract.codegenerator.domain.model;

import com.thanos.contract.codegenerator.domain.Generator;
import com.thanos.contract.codegenerator.domain.GeneratorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CombinedField {

    private String name;
    private String type;
    private Integer length;
    private String content;
    private Integer startIndex;
    private Generator generator;

    public CombinedField(String name, String type, Integer length, String content, Integer startIndex) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.content = content;
        this.startIndex = startIndex;
        this.generator = new GeneratorFactory().getGenerator(content, startIndex, length);
    }

}
