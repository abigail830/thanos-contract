package com.thanos.contract.codegenerator.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import com.thanos.contract.codegenerator.domain.model.CombinedField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractFieldDTO {
    private String name;
    private String type;
    private Integer length;
    private String contractContent;
    private String schemaContent;

    CombinedField toCombinedField(int startIndex) {
        String content;
        if (!Strings.isNullOrEmpty(contractContent)) {
            content = contractContent;
        } else {
            content = schemaContent;
        }
        return new CombinedField(name, type, length, content, startIndex);
    }
}
