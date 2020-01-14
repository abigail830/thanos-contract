package com.thanos.contract.mockserver.controller;

import com.thanos.contract.mockserver.domain.mapping.MockMapping;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/apis")
public class RestApiController {

    private MockMappingService mockMappingService;

    public RestApiController(MockMappingService mockMappingService) {
        this.mockMappingService = mockMappingService;
    }

    @GET
    @Path("/mappings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MockMapping> getAllMockMapping() {
        return mockMappingService.getAllMockMapping();
    }

    @POST
    @Path("/mappings")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMockMapping(MockMapping mockMapping) {
        mockMappingService.addNewMockMapping(mockMapping);
    }

}
