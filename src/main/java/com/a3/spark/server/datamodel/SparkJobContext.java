package com.a3.spark.server.datamodel;

/**
 * Created by mala on 03.12.2015.
 */
public class SparkJobContext {
    private String name;
    private Integer numCpuCores;
    private String memoryPerNode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumCpuCores() {
        return numCpuCores;
    }

    public void setNumCpuCores(Integer numCpuCores) {
        this.numCpuCores = numCpuCores;
    }

    public String getMemoryPerNode() {
        return memoryPerNode;
    }

    public void setMemoryPerNode(String memoryPerNode) {
        this.memoryPerNode = memoryPerNode;
    }
}
