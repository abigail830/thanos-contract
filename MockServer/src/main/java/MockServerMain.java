import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.thanos.contract.mockserver.controller.MockServerController;
import com.thanos.contract.mockserver.controller.RestApiController;
import com.thanos.contract.mockserver.domain.mapping.MockMappingService;
import com.thanos.contract.mockserver.domain.mockserver.MockServerService;
import com.thanos.contract.mockserver.infrastructure.parser.PropertiesParser;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.rest.RestHandlerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.muserver.MuServerBuilder.httpServer;

@Slf4j
public class MockServerMain {

    private static int httpPort = 8080;

    private static MuServer webServer;
    private static MockServerController mockServerController;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            //read properties
            PropertiesParser propertiesParser = new PropertiesParser();
            propertiesParser.init();

            //startup web server
            final MockMappingService mockMappingService = new MockMappingService();
            startupWebServer(mockMappingService);

            //startup mockController
            mockServerController = new MockServerController(mockMappingService, new MockServerService());
            mockServerController.initMock();

            printStartupLog();

        } catch (IOException e) {
            log.error("{}", e);
            log.error("IOException during startup, going to exit...");
            System.exit(5);
        } catch (Exception ex) {
            log.error("{}", ex);
            log.error("Exception during startup, going to exit...");
            System.exit(9);
        }
        addShutdownHook();
    }

    synchronized static void printStartupLog() {
        log.info("Startup as standalone mode: {}", PropertiesParser.getStandaloneFlag());
        log.info("###########################################");
        log.info("            Service is up!                 ");
        log.info("###########################################");
    }

    static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Going to shutdown webServer ...");
            webServer.stop();
            log.info("###########################################");
            log.info("           Shut down complete !            ");
            log.info("###########################################");
        }));
    }

    private static void startupWebServer(MockMappingService mockMappingService) throws IOException {
        PropertiesParser.getPropValues("port")
                .ifPresent(s -> httpPort = Integer.valueOf(s));

        webServer = httpServer()
                .withHttpPort(httpPort)
                .addHandler(RestHandlerBuilder.restHandler(new RestApiController(mockMappingService))
                        .addCustomWriter(new JacksonJaxbJsonProvider())
                        .addCustomReader(new JacksonJaxbJsonProvider())
                        .withOpenApiJsonUrl("/openapi.json")
                        .withOpenApiHtmlUrl("/api.html"))
                .addHandler(Method.GET, "/health", ((muRequest, muResponse, map) -> {
                    muResponse.write("UP");
                }))
                .addHandler(Method.GET, "/", ((muRequest, muResponse, map) -> {
                    muResponse.redirect("/api.html");
                })).start();

        log.info("Web Server started at " + webServer.uri());
    }
}
