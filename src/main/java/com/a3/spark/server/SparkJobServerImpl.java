package com.a3.spark.server;

import com.a3.spark.server.datamodel.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mala on 03.12.2015.
 */
@Component
public class SparkJobServerImpl implements SparkJobServer {

    private RestTemplate restTemplate;
    private String baseUrl;

    @Autowired
    private SparkJobServerEngine jobServerEngine;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        this.baseUrl = jobServerEngine.getBaseUrl();
    }

    @Override
    public String submitSparkJob(String appName, String classPath, String context) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>("input.string = 1b cd", headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/jobs")
                .queryParam("appName", appName)
                .queryParam("classPath", classPath)
                .queryParam("context", context);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.build().encode().toUri(), HttpMethod.POST, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseEntity.getBody());

        return root.path("result").path("jobId").asText();
    }

    @Override
    public String submitSparkJobSync(String appName, String classPath, String context, Integer timeout) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>("input.string = 1b cd", headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/jobs")
                .queryParam("appName", appName)
                .queryParam("classPath", classPath)
                .queryParam("context", context)
                .queryParam("sync", "true")
                .queryParam("timeout", timeout);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.build().encode().toUri(), HttpMethod.POST, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseEntity.getBody());

        return root.path("result").path("jobId").asText();
    }

    @Override
    public String cancelSparkJob(String jobId) {
        //TODO implement delete support
        return "";
    }

    @Override
    public JobStatus queryJobStatus(String jobId) {
        ResponseEntity<SparkJob> responseEntity = restTemplate.getForEntity(baseUrl + "/jobs/" + jobId, SparkJob.class);
        return responseEntity.getBody().getStatus();
    }

    @Override
    public List<SparkJob> listAllSparkJobs() {
        ResponseEntity<List<SparkJob>> responseEntity = restTemplate.exchange(baseUrl + "/jobs",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<SparkJob>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public List<SparkJobContext> listAllSparkContexts() {
        ResponseEntity<List<SparkJobContext>> responseEntity = restTemplate.exchange(baseUrl + "/contexts",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<SparkJobContext>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public List<SparkJobApp> listAllSparkJobApps() {
        ResponseEntity<List<SparkJobApp>> responseEntity = restTemplate.exchange(baseUrl + "/jars",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<SparkJobApp>>() {
                });
        return responseEntity.getBody();
    }
}
