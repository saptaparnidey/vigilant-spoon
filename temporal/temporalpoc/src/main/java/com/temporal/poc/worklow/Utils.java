package com.temporal.poc.worklow;

import io.temporal.api.workflow.v1.WorkflowExecutionInfo;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionRequest;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionResponse;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.Optional;

public class Utils {

//    public static final WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
//
//    public static final WorkflowClient client = WorkflowClient.newInstance(service);


    public static String getWorkflowStatus(WorkflowClient client, String workflowId) {
        WorkflowStub existingUntyped = client.newUntypedWorkflowStub(workflowId, Optional.empty(), Optional.empty());
        DescribeWorkflowExecutionRequest describeWorkflowExecutionRequest =
                DescribeWorkflowExecutionRequest.newBuilder()
                        .setNamespace(client.getOptions().getNamespace())
                        .setExecution(existingUntyped.getExecution())
                        .build();

        DescribeWorkflowExecutionResponse resp =
                client.getWorkflowServiceStubs().blockingStub().describeWorkflowExecution(describeWorkflowExecutionRequest);
        System.out.println("**** PARENT: " + resp.getWorkflowExecutionInfo().getParentExecution().getWorkflowId());

        WorkflowExecutionInfo workflowExecutionInfo = resp.getWorkflowExecutionInfo();
        return workflowExecutionInfo.getStatus().toString();
    }
}
