package com.temporal.poc.Service;

import com.google.protobuf.util.Durations;
import com.temporal.poc.constants.TemporalConstants;
import com.temporal.poc.dto.OrderRequest;
import com.temporal.poc.dto.OrderStatusRequest;
import com.temporal.poc.worklow.WorkFlow;
import io.temporal.api.workflowservice.v1.RegisterNamespaceRequest;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderFulfilmentService {

    @Autowired
    WorkflowServiceStubs workflowServiceStubs;

    @Autowired
    WorkflowClient workflowClient;

    public void placeOrder(OrderRequest orderRequest) {
        WorkFlow workflow = createWorkFlowConnection(orderRequest.getReturnOrderNo());
        WorkflowClient.start(workflow::startApprovalWorkflow, orderRequest);
    }

    public void makeReturnOrderCreated(OrderRequest orderRequest) {
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + orderRequest.getReturnOrderNo());
        workflow.signalReturnOrderCreated();
    }

    public void makeReturnOrderPickedUp(OrderStatusRequest orderStatusRequest) {
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + orderStatusRequest.getReturnOrderNo());
        workflow.signalReturnOrderPickedUp(orderStatusRequest);
    }

    public void makeReturnOrderQC(OrderStatusRequest orderStatusRequest) {
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + orderStatusRequest.getReturnOrderNo());
        workflow.signalReturnOrderQC(orderStatusRequest.getReturnOrderNo());
    }

    public void makeReturnOrderDeliveredToWH(OrderStatusRequest orderStatusRequest) {
        WorkFlow workflow = workflowClient.newWorkflowStub(WorkFlow.class, "Order_" + orderStatusRequest.getReturnOrderNo());
        workflow.signalReturnOrderDeliveredToWH(orderStatusRequest);
    }


    public WorkFlow createWorkFlowConnection(String id) {
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TemporalConstants.QUEUE_NAME)
                .setWorkflowId("Order_" + id).build();
        return workflowClient.newWorkflowStub(WorkFlow.class, options);
    }

    public void createNamespace(String name) {
        RegisterNamespaceRequest req = RegisterNamespaceRequest.newBuilder()
                .setNamespace(name)
                .setWorkflowExecutionRetentionPeriod(Durations.fromDays(3)) // keeps the Workflow Execution
                //Event History for up to 3 days in the Persistence store. Not setting this value will throw an error.
                .build();
        workflowServiceStubs.blockingStub().registerNamespace(req);
    }
}
