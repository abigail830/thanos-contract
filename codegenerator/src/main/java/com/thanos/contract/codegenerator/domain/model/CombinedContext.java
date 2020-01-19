package com.thanos.contract.codegenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CombinedContext {

    private String name;
    private String version;
    private String consumer;
    private String provider;

    private LinkedList<CombinedField> request;
    private LinkedList<CombinedField> response;

}
