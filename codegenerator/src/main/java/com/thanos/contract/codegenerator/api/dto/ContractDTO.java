package com.thanos.contract.codegenerator.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thanos.contract.codegenerator.domain.model.CombinedContext;
import com.thanos.contract.codegenerator.domain.model.CombinedField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractDTO {

    private ContractKeyDTO contractKey;
    private String schemaId;
    private LinkedList<ContractFieldDTO> req;
    private LinkedList<ContractFieldDTO> res;

    public CombinedContext toCombinedContext() {

        final LinkedList<CombinedField> requestCombinedFields = generateCombinedFields(req);
        final LinkedList<CombinedField> responseCombinedFields = generateCombinedFields(res);

        return new CombinedContext(contractKey.getName(), contractKey.getVersion(),
                contractKey.getConsumer(), contractKey.getProvider(),
                requestCombinedFields, responseCombinedFields);
    }

    LinkedList<CombinedField> generateCombinedFields(LinkedList<ContractFieldDTO> dtoFields) {
        LinkedList<CombinedField> combinedFields = new LinkedList<>();
        int startIndex = 0;
        for (ContractFieldDTO contractField : dtoFields) {
            combinedFields.add(contractField.toCombinedField(startIndex));
            startIndex += contractField.getLength();
        }
        return combinedFields;
    }


}
