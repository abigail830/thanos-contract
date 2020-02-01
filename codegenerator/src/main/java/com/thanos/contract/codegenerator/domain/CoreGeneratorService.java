package com.thanos.contract.codegenerator.domain;

import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.domain.model.JunitTemplateFields;
import com.thanos.contract.codegenerator.exception.BizException;
import com.thanos.contract.codegenerator.infrastructure.parser.TemplateParser;

import java.io.IOException;

public class CoreGeneratorService {

    public String generateToString(CombinedContext combinedContext) {
        JunitTemplateFields fields = new JunitTemplateFields(combinedContext);
        try {
            return new TemplateParser().parseJunitTemplateToString(fields);
        } catch (IOException e) {
            throw new BizException("IOException when parse junit template", e);
        }
    }

    public void generateToFile(CombinedContext combinedContext, String basePath) {
        JunitTemplateFields fields = new JunitTemplateFields(combinedContext);
        try {
            new TemplateParser().parseJunitTemplateToFile(fields, basePath);
        } catch (IOException e) {
            throw new BizException("IOException when parse junit template", e);
        }
    }
}
