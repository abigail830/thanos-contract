package com.thanos.contract.codegenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JunitTemplateFields {

    String className;
    String testMethod;
    String request;
    List<String> fieldValidation;

    public JunitTemplateFields(CombinedContext combinedContext) {
        this.className = combinedContext.getConsumer() + combinedContext.getProvider();
        this.testMethod = combinedContext.getName();
        this.request = generateRequest(combinedContext.getRequest());
        this.fieldValidation = generateFieldValidation(combinedContext.getResponse());
    }

    private List<String> generateFieldValidation(LinkedList<CombinedField> response) {
        return response.stream()
                .map(combinedField -> combinedField.getGenerator().getExpectedValidation())
                .collect(Collectors.toList());

    }

    private String generateRequest(LinkedList<CombinedField> request) {
        return request.stream()
                .map(combinedField -> combinedField.getGenerator().getExpectedValue())
                .collect(Collectors.joining());
    }
}
