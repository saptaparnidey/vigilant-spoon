package com.temporal.poc.Service;

import com.temporal.poc.worklow.Greetings;
import com.temporal.poc.worklow.Utils;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
//import static com.temporal.poc.worklow.Utils.client;

@Service
public class GreetingsService {

    @Autowired
    WorkflowClient workflowClient;

    private static final String workflowId = "GreetingWorkflow";

    public void greetings(String name) throws ExecutionException, InterruptedException{
        Greetings greetings = createWorkflowConnection(name);
        WorkflowClient.start(greetings::greetWorkflow, name);

        printWorkflowStatus();
        WorkflowStub untyped =WorkflowStub.fromTyped(greetings);
        String greeting =untyped.getResult(String.class);
        printWorkflowStatus();
        System.out.println("Greeting: " + greeting);

        return;

    }



    public Greetings createWorkflowConnection(String name){
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue("task_q")
                .setWorkflowId(name+"_123").build();

        return workflowClient.newWorkflowStub(Greetings.class, options);
    }

    private void printWorkflowStatus() {
        System.out.println("Workflow Status: " + Utils.getWorkflowStatus(workflowClient, "Hello_123"));
    }


}
