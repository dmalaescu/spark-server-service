import com.a3.spark.server.SparkJobServer;
import com.a3.spark.server.SparkJobServerConfig;
import com.a3.spark.server.SparkJobServerConfiguration;
import com.a3.spark.server.datamodel.JobStatus;
import com.a3.spark.server.datamodel.SparkJob;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.List;

/**
 * Created by mala on 03.12.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SparkJobServerConfiguration.class)
public class SparkJobServerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SparkJobServer sparkJobServer;

    @Test
    public void testApplicationContext() {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(applicationContext.getBean(SparkJobServerConfig.class));
        Assert.assertNotNull(sparkJobServer);
    }

    @Test
    public void testSparkJobServer_listAllJobs() {
        List<SparkJob> jobs = sparkJobServer.listAllSparkJobs();
        System.out.println(jobs);
    }

    @Test
    public void testSparkJobServer_queryForJob() {
        JobStatus jobStatus = sparkJobServer.queryJobStatus("a6a2075a-91af-4af1-8b14-e6cf31ede4e9");
        System.out.println(jobStatus);
    }

    @Test
    public void testSparkJobServer_submitSparkJob() throws IOException {
        String response = sparkJobServer.submitSparkJob("test-spark-server-yarn", "com.a3.A3YarnSampleSparkJob", "test-context");
        System.out.println(response);
    }
}
