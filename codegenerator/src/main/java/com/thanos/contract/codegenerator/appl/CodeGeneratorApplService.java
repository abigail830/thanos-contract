package com.thanos.contract.codegenerator.appl;

import com.thanos.contract.codegenerator.domain.CoreGeneratorService;
import com.thanos.contract.codegenerator.domain.FileBaseRepository;
import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.infrastructure.parser.PropertiesParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeGeneratorApplService {

    private FileBaseRepository fileBaseRepository;
    private CoreGeneratorService coreGeneratorService;

    public CodeGeneratorApplService(FileBaseRepository fileBaseRepository, CoreGeneratorService coreGeneratorService) {
        this.fileBaseRepository = fileBaseRepository;
        this.coreGeneratorService = coreGeneratorService;
    }

    public void generateJunitToFile(String contractKey) {
        generateJunitToFile(contractKey, PropertiesParser.getJunitBasePath());
    }

    public void generateJunitToFile(String contractKey, String basePath) {
        final CombinedContext combinedContext = fileBaseRepository.getCombinedContextByContractKey(contractKey);
        coreGeneratorService.generateToFile(combinedContext, basePath);
    }

    public String generateJunit(String contractKey) {
        final CombinedContext combinedContext = fileBaseRepository.getCombinedContextByContractKey(contractKey);
        return coreGeneratorService.generateToString(combinedContext);
    }



}
