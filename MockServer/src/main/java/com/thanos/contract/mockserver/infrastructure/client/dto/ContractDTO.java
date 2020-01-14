package com.thanos.contract.mockserver.infrastructure.client.dto;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.ContractField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    private String name;
    private String version;
    private String schemaIndex;
    private String consumer;
    private String provider;

    private LinkedList<ContractFieldDTO> req;
    private LinkedList<ContractFieldDTO> res;

    public String getIndex() {
        return provider + '-' + consumer;
    }

    public Contract toContract() {
        final LinkedList<ContractField> request = req.stream()
                .map(ContractFieldDTO::toContractField).collect(Collectors.toCollection(LinkedList::new));
        final LinkedList<ContractField> response = res.stream()
                .map(ContractFieldDTO::toContractField).collect(Collectors.toCollection(LinkedList::new));

        return new Contract(name, version, schemaIndex, consumer, provider, request, response);
    }

}
