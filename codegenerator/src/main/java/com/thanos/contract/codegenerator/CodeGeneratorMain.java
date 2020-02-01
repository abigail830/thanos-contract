package com.thanos.contract.codegenerator;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.thanos.contract.codegenerator.api.RestApiController;
import com.thanos.contract.codegenerator.appl.CodeGeneratorApplService;
import com.thanos.contract.codegenerator.domain.CoreGeneratorService;
import com.thanos.contract.codegenerator.domain.FileBaseRepository;
import com.thanos.contract.codegenerator.infrastructure.cache.FileBaseCache;
import com.thanos.contract.codegenerator.infrastructure.cache.FileBaseCacheRepoImpl;
import com.thanos.contract.codegenerator.infrastructure.parser.PropertiesParser;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.rest.RestHandlerBuilder;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

import static io.muserver.MuServerBuilder.httpServer;

@Slf4j
public class CodeGeneratorMain {

    private static int httpPort = 8080;
    private static MuServer webServer;

    private static FileBaseCache fileBaseCache;
    private static FileBaseRepository fileBaseRepository;
    private static CodeGeneratorApplService codeGeneratorApplService;

    public static void main(String[] args) {
        try {

            PropertiesParser propertiesParser = new PropertiesParser();
            propertiesParser.init();

            // for diff mode
            if (PropertiesParser.isPlatformMode()) {
                throw new NotImplementedException();
            } else {
                fileBaseCache = new FileBaseCache();
                fileBaseCache.init();
                fileBaseRepository = new FileBaseCacheRepoImpl(fileBaseCache);
                CoreGeneratorService coreGeneratorService = new CoreGeneratorService();
                codeGeneratorApplService = new CodeGeneratorApplService(fileBaseRepository, coreGeneratorService);
            }
            startupWebServer(codeGeneratorApplService);
            printStartupLog();

        } catch (IOException e) {
            log.error("{}", e);
            log.error("Exception during startup, going to exit...");
            System.exit(5);
        } catch (Exception e) {
            log.error("{}", e);
            log.error("Exception during startup, going to exit...");
            System.exit(9);
        }

        addShutdownHook();

    }

    private static void startupWebServer(CodeGeneratorApplService codeGeneratorApplService) throws IOException {
        PropertiesParser.getPropValues("port")
                .ifPresent(s -> httpPort = Integer.valueOf(s));

        webServer = httpServer()
                .withHttpPort(httpPort)
                .addHandler(RestHandlerBuilder.restHandler(new RestApiController(codeGeneratorApplService))
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

    synchronized static void printStartupLog() {
        log.info("Startup as standalone mode: {}", PropertiesParser.isPlatformMode());
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

}
