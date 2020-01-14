package com.thanos.contract.controller;

import com.thanos.contract.domain.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Path("/apis")
public class RestApiController {

    private ContractService contractService;

    public RestApiController() {
        contractService = ContractService.getInstance();
    }

    @GET
    @Path("/contracts/indexs")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getAllContractIndexs() {
        List<String> result = new ArrayList<String>();
        result.add("provider-consumer");

        return result;
    }

    @GET
    @Path("/contracts")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contract> getContractByIndex(@QueryParam("index") String index) {

        if (index == null || index.isEmpty())
            return new ArrayList<>();
        else {
            LinkedList<ContractField> request = new LinkedList<>();
            request.add(new ContractField("field1", "value1"));
            request.add(new ContractField("field2", "regex(0|1)"));

            LinkedList<ContractField> response = new LinkedList<>();
            response.add(new ContractField("field1", "response1"));
            response.add(new ContractField("field2", "regex(\\d{2})"));
            final Contract contract = new Contract("contractName", "contractVersion", "provider-name-version",
                    "consumer", "provider", request, response);

            return Arrays.asList(contract);
        }

    }

    @GET
    @Path("/schemas")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Schema> getSchemaByIndex(@QueryParam("index") String index) {

        if (index == null || index.isEmpty())
            return new ArrayList<>();
        else {
            LinkedList<SchemaField> request = new LinkedList<>();
            request.add(new SchemaField("field1", "CHAR", 6, "value1"));
            request.add(new SchemaField("field2", "CHAR", 1, "regex(0|1|2)"));

            LinkedList<SchemaField> response = new LinkedList<>();
            response.add(new SchemaField("field1", "CHAR", 9, "response1"));
            response.add(new SchemaField("field2", "CHAR", 1, "regex(\\d{2})"));
            response.add(new SchemaField("field3", "CHAR", 1, "regex(A|B|C)"));

            Schema schema = new Schema("provider", "version", "name",
                    request, response);

            return Arrays.asList(schema);
        }

    }

}
