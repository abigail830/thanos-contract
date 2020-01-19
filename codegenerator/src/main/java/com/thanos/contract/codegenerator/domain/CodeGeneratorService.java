package com.thanos.contract.codegenerator.domain;

import com.google.common.base.CaseFormat;
import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.domain.model.CombinedField;
import com.thanos.contract.codegenerator.domain.model.JunitTemplateFields;
import com.thanos.contract.codegenerator.exception.BizException;
import com.thanos.contract.codegenerator.infrastructure.parser.TemplateParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CodeGeneratorService {

    private FileBaseRepository fileBaseRepository;
    private TemplateParser templateParser;

    public CodeGeneratorService(FileBaseRepository fileBaseRepository) {
        this.fileBaseRepository = fileBaseRepository;
        this.templateParser = new TemplateParser();
    }

    public String generateJunit(String contractKey, String host, Integer port) {

        final CombinedContext combinedContext = fileBaseRepository.getCombinedContextByContractKey(contractKey);

        JunitTemplateFields fields = generateTemplateFields(combinedContext, host, port);
        try {
            return templateParser.parseJunitTemplateToString(fields);
        } catch (IOException e) {
            throw new BizException("IOException when parse junit template", e);
        }

    }

    private JunitTemplateFields generateTemplateFields(CombinedContext combinedContext, String host, Integer port) {

        String className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, combinedContext.getConsumer()) +
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, combinedContext.getProvider());

        String methodName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, combinedContext.getName());

        String request = generateRequest(combinedContext.getRequest());

        List<String> validations = generateFieldValidation(combinedContext.getResponse());

        return new JunitTemplateFields(className, methodName, request, host, port, validations);
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
