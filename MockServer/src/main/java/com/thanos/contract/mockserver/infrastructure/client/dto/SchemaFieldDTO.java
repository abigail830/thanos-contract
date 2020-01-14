package com.thanos.contract.mockserver.infrastructure.client.dto;

import com.thanos.contract.mockserver.domain.contract.model.SchemaField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SchemaFieldDTO {
    String name;
    String type;
    Integer length;
    String content;

    public SchemaFieldDTO(String name, String type, Integer length, String content) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.content = content;
    }

    public SchemaField toSchemaField() {
        return new SchemaField(name, type, length, content);
    }


}
