package com.thanos.contract.mockserver.domain.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Boolean matchSchemaIndex(String schemaIndex) {
        return this.getSchemaIndex().equals(schemaIndex);
    }

    public String getContractExpectValue(String schemaFieldName) {
        final List<ContractField> result = res.stream()
                .filter(response -> response.getName().equals(schemaFieldName))
                .collect(Collectors.toList());

        if (result.size() >= 1) {
            return result.get(0).getValidator().getExpectedValue();
        } else {
            return "";
        }
    }
}
