package com.thanos.contract.codegenerator.controller;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractKeyDTO {

    private String provider;
    private String consumer;
    private String name;
    private String version;

    public String toContractKey() {
        return provider + '-' + consumer + '-' + version + '-' + name;
    }

    public Boolean isValid() {
        if (Strings.isNullOrEmpty(provider)) return false;
        if (Strings.isNullOrEmpty(consumer)) return false;
        if (Strings.isNullOrEmpty(name)) return false;
        if (Strings.isNullOrEmpty(version)) return false;

        return true;
    }
}
