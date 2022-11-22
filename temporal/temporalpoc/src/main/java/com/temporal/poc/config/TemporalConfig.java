package com.temporal.poc.config;

import com.temporal.poc.worklow.ActivityImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class TemporalConfig {

    @Value("${temporal.address}")
    private String temporalServiceAddress;

    @Value("${temporal.namespace}")
    private String temporalNamespace;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalServiceAddress)
                .build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
        return WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder().setNamespace(temporalNamespace).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public ActivityImpl SignUpActivity() {
        return new ActivityImpl();
    }

}
