package com.thanos.contract.codegenerator.infrastructure.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Schema {

    private String provider;
    private String version;
    private String name;
    private LinkedList<SchemaField> request;
    private LinkedList<SchemaField> response;

    public String getIndex() {
        return provider + '-' + name + '-' + version;
    }

}
