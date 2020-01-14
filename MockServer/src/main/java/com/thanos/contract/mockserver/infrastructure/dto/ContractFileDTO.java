package com.thanos.contract.mockserver.infrastructure.dto;

import com.thanos.contract.mockserver.domain.contract.model.Contract;
import com.thanos.contract.mockserver.domain.contract.model.ContractField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContractFileDTO {

    String name;
    String version;
    String consumer;
    Map<String, String> schema;
    LinkedHashMap<String, String> req;
    LinkedHashMap<String, String> res;

    public static List<Contract> buildFrom(Iterable<Object> ymlResult) {
        final List<Contract> result = new ArrayList<>();
        for (Object record : ymlResult) {
            if (record instanceof ContractFileDTO) {
                result.add(((ContractFileDTO) record).toContract());
            }
        }
        return result;
    }

    public Contract toContract() {
        LinkedList<ContractField> request = buildContractFieldList(req);
        LinkedList<ContractField> response = buildContractFieldList(res);
        String schemaIndex = schema.get("provider") + "-" + schema.get("name") + "-" + schema.get("version");
        return new Contract(name, version, schemaIndex, consumer,
                schema.get("provider"), request, response);
    }

    LinkedList<ContractField> buildContractFieldList(LinkedHashMap<String, String> originDtoMap) {
        LinkedList<ContractField> result = new LinkedList<>();
        for (String key : originDtoMap.keySet()) {
            String content = originDtoMap.get(key);
            result.add(new ContractField(key, content));
        }
        return result;
    }

}
