import com.thanos.contract.mockserver.infrastructure.client.ContractRestClient;
import com.thanos.contract.mockserver.infrastructure.dto.ContractDTO;
import com.thanos.contract.mockserver.infrastructure.dto.ContractFieldDTO;
import com.thanos.contract.mockserver.infrastructure.dto.SchemaDTO;
import com.thanos.contract.mockserver.infrastructure.dto.SchemaFieldDTO;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationBase {

    @Spy
    @InjectMocks
    MockServerMain mockServerMain;

    @Mock
    ContractRestClient contractRestClient;


    @Before
    public void setup() throws Exception {
        prepareContractRestClient();
        MockServerMain.start();
    }

    private void prepareContractRestClient() throws IOException {
//        Mockito.when(contractRestClient.getAllContractIndex()).thenReturn(Arrays.asList("provider-consumer"));
        doReturn(Arrays.asList("provider-consumer")).when(contractRestClient).getAllContractIndex();

        LinkedList<ContractFieldDTO> request = new LinkedList<>();
        request.add(new ContractFieldDTO("field1", "value1"));
        request.add(new ContractFieldDTO("field2", "regex(0|1)"));
        LinkedList<ContractFieldDTO> response = new LinkedList<>();
        response.add(new ContractFieldDTO("field1", "response1"));
        response.add(new ContractFieldDTO("field2", "regex(\\d{2})"));
        final ContractDTO contract = new ContractDTO("contractName", "contractVersion", "provider-name-version",
                "consumer", "provider", request, response);
        doReturn(Arrays.asList(contract)).when(contractRestClient).getContractByIndex("provider-consumer");

        LinkedList<SchemaFieldDTO> req = new LinkedList<>();
        req.add(new SchemaFieldDTO("field1", "CHAR", 6, "value1"));
        req.add(new SchemaFieldDTO("field2", "CHAR", 1, "regex(0|1|2)"));
        LinkedList<SchemaFieldDTO> res = new LinkedList<>();
        res.add(new SchemaFieldDTO("field1", "CHAR", 9, "response1"));
        res.add(new SchemaFieldDTO("field2", "CHAR", 1, "regex(\\d{2})"));
        res.add(new SchemaFieldDTO("field3", "CHAR", 1, "regex(A|B|C)"));
        SchemaDTO schema = new SchemaDTO("provider", "version", "name", req, res);
        doReturn(Arrays.asList(schema)).when(contractRestClient).getSchemaByIndex(Arrays.asList("provider-name-version"));
    }
}
