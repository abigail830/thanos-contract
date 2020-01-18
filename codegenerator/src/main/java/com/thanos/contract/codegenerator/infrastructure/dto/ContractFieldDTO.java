package com.thanos.contract.codegenerator.infrastructure.dto;

import com.thanos.contract.codegenerator.domain.model.ContractField;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractFieldDTO {

    private String name;
    private String content;

    public ContractField toContractField() {
        return new ContractField(name, content);
    }
}
