package com.a3.spark.server;

import com.a3.spark.server.datamodel.JobStatus;
import com.a3.spark.server.datamodel.SparkJob;
import com.a3.spark.server.datamodel.SparkJobApp;
import com.a3.spark.server.datamodel.SparkJobContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Interface for handling jobs, apps and contexts in
 * a spark job server environment
 * Created by mala on 03.12.2015.
 */
public interface SparkJobServer {

    /**
     * submit to job sever a job that is executed in async mode
     * @param appName
     * @param classPath
     * @param context
     * @return jobId
     * @throws IOException
     */
    String submitSparkJob(String appName, String classPath, String context) throws IOException;

    /**
     * submit to job sever a job that is executed in sync mode
     * @param appName
     * @param classPath
     * @param context
     * @param timeout
     * @return jobId
     */
    String submitSparkJobSync(String appName, String classPath, String context, Integer timeout) throws IOException;

    /**
     * kill a job
     * @param jobId
     * @return
     */
    void cancelSparkJob(String jobId);

    /**
     * query a job for it's status
     * @param jobId
     * @return job status
     */
    JobStatus queryJobStatus(String jobId);

    /**
     * list all available spark jobs
     * @return
     */
    List<SparkJob> listAllSparkJobs();

    /**
     * list all spark job server contexts
     * @return
     */
    List<SparkJobContext> listAllSparkContexts();

    /**
     * list all spark job server applications
     * @return
     */
    List<SparkJobApp> listAllSparkJobApps();

}
