package com.thanos.contract.mockserver.domain.mockserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    private String name;
    private String version;
    private String schemaIndex;
    private String consumer;
    private String provider;

    private LinkedList<ContractField> req;
    private LinkedList<ContractField> res;

    public String getIndex() {
        return provider + '-' + consumer;
    }

    public Integer getPriority() {
        return req.size();
    }

    public Boolean matchSchemaIndex(String schemaIndex) {
        return this.getSchemaIndex().equals(schemaIndex);
    }

    public String getContractExpectValue(String schemaFieldName) {
        final Optional<ContractField> contractField = res.stream()
                .filter(response -> response.getName().equals(schemaFieldName))
                .findFirst();

        if (contractField.isPresent()) {
            return contractField.get().getValidator().getExpectedValue();
        } else {
            return "";
        }
    }
}
