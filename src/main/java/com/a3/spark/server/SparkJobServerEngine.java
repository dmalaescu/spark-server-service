package com.a3.spark.server;

import com.a3.spark.server.datamodel.SparkJobContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mala on 03.12.2015.
 */
@Component
public class SparkJobServerEngine {

    private String baseUrl;
    private SparkJobServerConfig serverConfig;
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        serverConfig = SparkJobServerConfig.parseConfig("spark-job-server.conf");
        this.baseUrl = "http://" + serverConfig.getSparkJobServerHost() + ":" + serverConfig.getSparkJobServerPort();
        restTemplate = new RestTemplate();

//        recreateSparkJobContexts();
    }
    public void createSparkJobApps(){

    }

    public void recreateSparkJobContexts() {
        for (SparkJobContext jobContext : serverConfig.getContexts()) {
            // try delete the old context
            try{
                restTemplate.delete(baseUrl + "/contexts/" + jobContext.getName());
                // try to sleep for a second, breath
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // create a new fresh copy of the context
            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("num-cpu-cores", String.valueOf(jobContext.getNumCpuCores()));
            uriVariables.put("memory-per-node", jobContext.getMemoryPerNode());
            restTemplate.postForLocation(baseUrl + "/contexts/" + jobContext.getName(), null, uriVariables);
        }
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }
}
