package com.thanos.contract.codegenerator.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractKeyDTO {

    private String provider;
    private String consumer;
    private String name;
    private String version;
    private String host;
    private Integer port;

    public String toContractKey() {
        return provider + '-' + consumer + '-' + version + '-' + name;
    }

    public Boolean isValid() {
        if (isNullOrEmpty(provider) || isNullOrEmpty(consumer) || isNullOrEmpty(name) || isNullOrEmpty(version)) {
            return false;
        }
        if (isNullOrEmpty(host) || port == null) {
            return false;
        }
        return true;
    }
}
