package com.thanos.contract.mockserver.domain.contract.model;

import com.thanos.contract.mockserver.domain.contract.validate.Validator;
import com.thanos.contract.mockserver.domain.contract.validate.ValidatorFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ContractField {

    private String name;
    private String content;
    private Validator validator;

    public ContractField(String name, String content) {
        this.name = name;
        this.content = content;
        validator = ValidatorFactory.getValidator(content);
    }

    public boolean match(String actualContent) {
        return validator.validate(actualContent);
    }
}
