package com.thanos.contract.codegenerator.infrastructure.parser.dto;

import com.thanos.contract.codegenerator.infrastructure.cache.dto.SchemaField;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchemaFieldDTO {
    private String name;
    private String type;
    private Integer length;
    private String content;

    public SchemaField toSchemaField(Integer startIndex) {
        return new SchemaField(name, type, length, content, startIndex);
    }

}
