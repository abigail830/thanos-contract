package com.thanos.contract.mockserver.domain.mapping;

import java.util.HashMap;
import java.util.Map;

public class MockMappingCache {

    private static Map<String, Integer> mockMappings = new HashMap<>();

    static void addNewMockMapping(MockMapping mockMapping) {
        mockMappings.put(mockMapping.getIndex(), mockMapping.getPort());
    }

    static Map<String, Integer> getAllMockMapping() {
        return mockMappings;
    }
}
