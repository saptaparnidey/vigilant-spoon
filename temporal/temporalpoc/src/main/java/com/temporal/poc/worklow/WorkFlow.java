package com.temporal.poc.worklow;

import com.temporal.poc.dto.OrderRequest;
import com.temporal.poc.dto.OrderStatusRequest;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkFlow {

    @WorkflowMethod
    void startApprovalWorkflow(OrderRequest orderRequest);

    @SignalMethod
    void signalReturnOrderCreated();

    @SignalMethod
    void signalReturnOrderPickedUp(OrderStatusRequest orderStatusRequest);

    @SignalMethod
    void signalReturnOrderQC(String orderNo);

    @SignalMethod
    void signalReturnOrderDeliveredToWH(OrderStatusRequest orderStatusRequest);

}
