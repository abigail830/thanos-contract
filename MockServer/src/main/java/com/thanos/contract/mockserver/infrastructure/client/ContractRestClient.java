package com.thanos.contract.mockserver.infrastructure.client;

import com.google.gson.reflect.TypeToken;
import com.thanos.contract.mockserver.domain.contract.Contract;
import com.thanos.contract.mockserver.domain.contract.ContractRepository;
import com.thanos.contract.mockserver.domain.contract.Schema;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ContractRestClient implements ContractRepository {

    private static final String CONTRACT_SERVICE_BASE_URL = "http://localhost:8081/apis";
    OkHttpClient client = new OkHttpClient();

    /**
     * Using by MockServerController
     */
    @Override
    public List<String> getAllContractIndex() throws IOException {
        HttpUrl.Builder urlBuild = HttpUrl.parse(CONTRACT_SERVICE_BASE_URL + "/contracts/indexs").newBuilder();
        final String url = urlBuild.build().toString();
        log.debug("url going to approach is: {}", url);

        String result = httpGet(url);
        return JsonUtil.toObject(result, new TypeToken<List<String>>() {
        }.getType());

    }

    /**
     * Using by MockServerController
     */
    @Override
    public List<Contract> getContractByIndex(String indexName) throws IOException {
        HttpUrl.Builder urlBuild = HttpUrl.parse(CONTRACT_SERVICE_BASE_URL + "/contracts").newBuilder();
        urlBuild.addQueryParameter("index", indexName);
        final String url = urlBuild.build().toString();
        log.debug("url going to approach is: {}", url);

        String result = httpGet(url);
        return JsonUtil.toObject(result, new TypeToken<List<Contract>>() {
        }.getType());

    }

    @Override
    public List<Schema> getSchemaByIndex(List<String> schemaNeededs) throws IOException {
        HttpUrl.Builder urlBuild = HttpUrl.parse(CONTRACT_SERVICE_BASE_URL + "/contracts").newBuilder();
        schemaNeededs.forEach(schemaIndex -> {
            urlBuild.addQueryParameter("index", schemaIndex);
        });
        final String url = urlBuild.build().toString();
        log.debug("url going to approach is: {}", url);

        String result = httpGet(url);
        return JsonUtil.toObject(result, new TypeToken<List<Schema>>() {
        }.getType());
    }

    private String httpGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
