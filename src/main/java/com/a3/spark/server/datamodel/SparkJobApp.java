package com.a3.spark.server.datamodel;

import java.io.Serializable;

/**
 * Created by mala on 03.12.2015.
 */
public class SparkJobApp implements Serializable {
    private String name;
    private String description;
    private String jarPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
