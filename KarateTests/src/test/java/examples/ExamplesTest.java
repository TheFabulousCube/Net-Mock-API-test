package examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

public class ExamplesTest {
    private static Process serviceUnderTest;

    @BeforeAll
    static void setup() throws InterruptedException {
        boolean shouldStartServices = !Boolean.getBoolean("servicesRunning");
        if (shouldStartServices) {
            startExampleService();
        }
    }

    static void startExampleService() throws InterruptedException {
        String SUTBaseUrl = "https://localhost:7196";
        String APIenvironment = "Test";
        System.setProperty("SUTBaseUrl", SUTBaseUrl);


        String dotnet = "dotnet";
        String dll = "Net Mock API test.dll";
        String workspaceFolder = "C:\\Temp\\KarateTest\\SystemUnderTest\\Net Mock API test\\bin\\Debug\\net6.0";

        ProcessBuilder builder = new ProcessBuilder(dotnet, dll);
        Map<String, String> env = builder.environment();
        env.put("ASPNETCORE_URLS", SUTBaseUrl);
        env.put("ASPNETCORE_ENVIRONMENT", APIenvironment);
        builder.directory(new File(workspaceFolder));


        if (workspaceFolder != null && workspaceFolder != "") {
            builder.redirectErrorStream(true);
            builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(workspaceFolder.trim() + "/service.log")));
        }

        try {
            System.out.println("Starting system under test");
            serviceUnderTest = builder.start();
            synchronized(serviceUnderTest) {
//                 TODO Wait until we get message from service indicating it's ready? Do Health checks until success? Something more robust than arbritary timeout
                serviceUnderTest.wait(3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Stopping system under test");
        boolean shouldStartServices = !Boolean.getBoolean("servicesRunning");
        if (shouldStartServices) {
            serviceUnderTest.destroy();
        }
    }

    @Test
    void testParallel() {
        Results results = Runner.path("classpath:examples")
                .outputCucumberJson(true)
                .outputJunitXml(true)
                .parallel(5);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }


}
