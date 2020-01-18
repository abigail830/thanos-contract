package com.thanos.contract.mockserver.domain.mockserver.model;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.thanos.contract.mockserver.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Schema {

    private String provider;
    private String version;
    private String name;
    private LinkedList<SchemaField> request;
    private LinkedList<SchemaField> response;

    public String getIndex() {
        return provider + '-' + name + '-' + version;
    }

    public Message parseRequest(String reqMsg) {
        try {
            LinkedHashMap<String, String> parseResult = Maps.newLinkedHashMap();
            int startIndex = 0;
            for (SchemaField schemaField : request) {
                final String extractedContent =
                        reqMsg.substring(startIndex, startIndex + schemaField.getLength());

                if (!schemaField.match(extractedContent)) {
                    throw new BizException("Request does not match this schema: " + this.name);
                } else {
                    parseResult.put(schemaField.getName(), extractedContent);
                    startIndex += schemaField.getLength();
                }
            }
            return new Message(this, parseResult);

        } catch (StringIndexOutOfBoundsException stringIndexEx) {
            throw new BizException("Input request is shorter then schema expected!", stringIndexEx.getCause());
        }
    }

    public Boolean isValid() {
        if (Strings.isNullOrEmpty(provider) ||
                Strings.isNullOrEmpty(name) ||
                Strings.isNullOrEmpty(version) ||
                request.size() == 0 ||
                response.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
