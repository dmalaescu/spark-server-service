package com.a3.spark.server;

import com.a3.spark.server.datamodel.SparkJob;
import com.a3.spark.server.datamodel.SparkJobApp;
import com.a3.spark.server.datamodel.SparkJobContext;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mala on 03.12.2015.
 */
public class SparkJobServerConfig {
    private String sparkJobServerHost;
    private Integer sparkJobServerPort;
    private List<SparkJobContext> contexts;
    private List<SparkJobApp> apps;
    private List<SparkJob> jobs;

    public static SparkJobServerConfig parseConfig(String configFileName) {
        SparkJobServerConfig config = new SparkJobServerConfig();
        Config serverConfig = ConfigFactory.load(configFileName).getConfig("spark-job-server");
        config.sparkJobServerHost = serverConfig.getString("hostname");
        config.sparkJobServerPort = serverConfig.getInt("port");
        // build apps
        config.apps = serverConfig.getConfigList("apps").stream()
                .map(t -> {
                    SparkJobApp sparkJobApp = new SparkJobApp();
                    sparkJobApp.setDescription(t.getString("description"));
                    sparkJobApp.setJarPath(t.getString("jar-path"));
                    sparkJobApp.setName(t.getString("name"));
                    return sparkJobApp;
                })
                .collect(Collectors.toList());
        config.jobs = serverConfig.getConfigList("jobs").stream()
                .map(t -> {
                    SparkJob sparkJob = new SparkJob();
                    sparkJob.setAppName(t.getString("app-name"));
                    sparkJob.setClasspath(t.getString("classpath"));
                    sparkJob.setDescription(t.getString("description"));
                    return sparkJob;
                }).collect(Collectors.toList());
        config.contexts = serverConfig.getConfigList("contexts").stream()
                .map(t -> {
                    SparkJobContext sparkJobContext = new SparkJobContext();
                    sparkJobContext.setName(t.getString("name"));
                    sparkJobContext.setNumCpuCores(t.getInt("num-cpu-cores"));
                    sparkJobContext.setMemoryPerNode(t.getString("memory-per-node"));
                    return sparkJobContext;
                }).collect(Collectors.toList());
        return config;
    }

    public String getSparkJobServerHost() {
        return sparkJobServerHost;
    }

    public Integer getSparkJobServerPort() {
        return sparkJobServerPort;
    }

    public List<SparkJobContext> getContexts() {
        return contexts;
    }

    public List<SparkJobApp> getApps() {
        return apps;
    }

    public List<SparkJob> getJobs() {
        return jobs;
    }
}
