package com.thanos.contract.mockserver.controller;

import com.thanos.contract.mockserver.domain.mapping.MockMapping;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import com.thanos.contract.mockserver.domain.mockserver.MockServerService;
import com.thanos.contract.mockserver.exception.BizException;
import com.thanos.contract.mockserver.infrastructure.client.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response buildRequestForContract(@QueryParam("consumer") String consumer,
                                            @QueryParam("provider") String provider,
                                            @QueryParam("contractName") String contractName,
                                            @QueryParam("contractVersion") String contractVersion) {
        try {
            final String result =
                    mockServerService.buildRequestForContract(consumer, provider, contractName, contractVersion);
            return Response.ok(JsonUtil.toJson(result), MediaType.APPLICATION_JSON).build();
        } catch (BizException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/utils/parse-request")
    public Response parseRequest(@QueryParam("request") String request,
                                 @QueryParam("provider") String provider,
                                 @QueryParam("schemaName") String schemaName,
                                 @QueryParam("schemaVersion") String schemaVersion) {
        try {
            final String result = mockServerService.parseRequest(request, provider, schemaName, schemaVersion);
            return Response.ok(JsonUtil.toJson(result), MediaType.APPLICATION_JSON).build();
        } catch (BizException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

}
