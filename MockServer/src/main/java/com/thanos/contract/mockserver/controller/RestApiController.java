package com.thanos.contract.mockserver.controller;

import com.thanos.contract.mockserver.domain.mapping.MockMapping;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import com.thanos.contract.mockserver.domain.mockserver.MockServerService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/apis")
public class RestApiController {

    private MockMappingService mockMappingService;
    private MockServerService mockServerService;

    public RestApiController(MockMappingService mockMappingService, MockServerService mockServerService) {
        this.mockMappingService = mockMappingService;
        this.mockServerService = mockServerService;
    }

    @GET
    @Path("/mappings/indexes/{index}")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getPortByIndex(@PathParam("index") String index) {
        final Integer portByIndex = mockMappingService.getPortByIndex(index);
        return Optional.ofNullable(portByIndex).orElse(0);
    }

    @GET
    @Path("/mappings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MockMapping> getAllMockMapping() {
        return mockMappingService.getAllMockMapping();
    }


    @GET
    @Path("/utils/build-request")
    @Produces({MediaType.APPLICATION_JSON})
    public String buildRequestForContract(@QueryParam("consumer") String consumer,
                                          @QueryParam("provider") String provider,
                                          @QueryParam("name") String name,
                                          @QueryParam("version") String version) {
        return mockServerService.buildRequestForContract(consumer, provider, name, version);
    }

}
