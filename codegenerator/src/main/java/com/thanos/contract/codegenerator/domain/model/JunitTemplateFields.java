package com.thanos.contract.codegenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JunitTemplateFields {

    String className;
    String testMethod;
    String request;
    String host;
    Integer port;
    List<String> fieldValidation;
}
