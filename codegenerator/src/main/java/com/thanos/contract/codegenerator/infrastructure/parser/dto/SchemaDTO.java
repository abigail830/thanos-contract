package com.thanos.contract.codegenerator.infrastructure.parser.dto;

import com.thanos.contract.codegenerator.infrastructure.cache.dto.Schema;
import com.thanos.contract.codegenerator.infrastructure.cache.dto.SchemaField;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchemaDTO {

    private String provider;
    private String version;
    private String name;
    private LinkedList<SchemaFieldDTO> request;
    private LinkedList<SchemaFieldDTO> response;

    public static List<Schema> buildFrom(Iterable<Object> ymlResult) {
        final List<Schema> result = new LinkedList<>();
        for (Object record : ymlResult) {
            if (record instanceof SchemaDTO) {
                result.add(((SchemaDTO) record).toSchema());
            }
        }
        return result;
    }

    public String getIndex() {
        return provider + '-' + name + '-' + version;
    }

    public Schema toSchema() {
        LinkedList<SchemaField> schemaReqFields = toSchemaFields(request);
        LinkedList<SchemaField> schemaResFields = toSchemaFields(response);
        return new Schema(provider, version, name, schemaReqFields, schemaResFields);
    }

    LinkedList<SchemaField> toSchemaFields(LinkedList<SchemaFieldDTO> dtoFields) {
        int index = 0;
        LinkedList<SchemaField> schemaReqFields = new LinkedList<>();

        for (SchemaFieldDTO schemaFieldDTO : dtoFields) {
            schemaReqFields.add(schemaFieldDTO.toSchemaField(index));
            index += schemaFieldDTO.getLength();
        }

        return schemaReqFields;
    }
}
