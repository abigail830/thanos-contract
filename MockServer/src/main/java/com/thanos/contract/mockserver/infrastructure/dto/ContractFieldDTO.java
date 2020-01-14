package com.thanos.contract.mockserver.infrastructure.dto;

import com.thanos.contract.mockserver.domain.mockserver.model.ContractField;
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
