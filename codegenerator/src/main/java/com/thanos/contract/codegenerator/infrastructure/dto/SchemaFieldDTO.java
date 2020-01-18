package com.thanos.contract.codegenerator.infrastructure.dto;

import com.thanos.contract.codegenerator.domain.model.SchemaField;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchemaFieldDTO {
    String name;
    String type;
    Integer length;
    String content;

    public SchemaField toSchemaField() {
        return new SchemaField(name, type, length, content);
    }

}
