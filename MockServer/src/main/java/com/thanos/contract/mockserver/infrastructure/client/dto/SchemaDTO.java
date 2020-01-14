package com.thanos.contract.mockserver.infrastructure.client.dto;

import com.thanos.contract.mockserver.domain.contract.model.Schema;
import com.thanos.contract.mockserver.domain.contract.model.SchemaField;
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
public class SchemaDTO {

    private String provider;
    private String version;
    private String name;
    private LinkedList<SchemaFieldDTO> request;
    private LinkedList<SchemaFieldDTO> response;

    public String getIndex() {
        return provider + '-' + name + '-' + version;
    }

    public Schema toSchema() {
        final LinkedList<SchemaField> req = request.stream()
                .map(SchemaFieldDTO::toSchemaField).collect(Collectors.toCollection(LinkedList::new));
        final LinkedList<SchemaField> res = response.stream()
                .map(SchemaFieldDTO::toSchemaField).collect(Collectors.toCollection(LinkedList::new));
        return new Schema(provider, version, name, req, res);
    }
}
