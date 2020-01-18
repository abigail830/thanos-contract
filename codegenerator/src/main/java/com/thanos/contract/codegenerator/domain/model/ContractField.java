package com.thanos.contract.codegenerator.domain.model;

import com.thanos.contract.codegenerator.domain.Generator;
import com.thanos.contract.codegenerator.domain.GeneratorFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ContractField {

    private String name;
    private String content;
    private Generator generator;

    public ContractField(String name, String content) {
        this.name = name;
        this.content = content;
        generator = new GeneratorFactory().getGenerator(content);
    }

}
