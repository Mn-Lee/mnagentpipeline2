package com.pedmn.mnagentpipeline2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MnAgentPipeline2Application {

    public static void main(String[] args) {
        SpringApplication.run(MnAgentPipeline2Application.class, args);
    }

}
