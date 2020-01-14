package com.thanos.contract.mockserver.infrastructure.client.dto;

import com.thanos.contract.mockserver.domain.contract.model.ContractField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ContractFieldDTO {

    private String name;
    private String content;

    public ContractFieldDTO(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public ContractField toContractField() {
        return new ContractField(name, content);
    }
}
