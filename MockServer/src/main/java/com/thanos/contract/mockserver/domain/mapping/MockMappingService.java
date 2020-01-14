package com.thanos.contract.mockserver.domain.mapping;

import com.thanos.contract.mockserver.exception.BizException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MockMappingService {

    public List<MockMapping> getAllMockMapping() {
        final Map<String, Integer> allMockMapping = MockMappingCache.getAllMockMapping();
        return allMockMapping.keySet().stream()
                .map(key -> new MockMapping(key, allMockMapping.get(key)))
                .collect(Collectors.toList());
    }

    public void addNewMockMapping(MockMapping mockMapping) throws BizException {
        MockMappingCache.addNewMockMapping(mockMapping);
    }

}
