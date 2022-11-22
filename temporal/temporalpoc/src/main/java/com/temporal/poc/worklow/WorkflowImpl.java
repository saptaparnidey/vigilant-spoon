package com.temporal.poc.worklow;

import com.temporal.poc.dto.OrderRequest;
import com.temporal.poc.dto.OrderStatusRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class WorkflowImpl implements WorkFlow {

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(10))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(3)
            .build();

    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(15))
            .setRetryOptions(retryoptions).build();

      private final Activity activity = Workflow.newActivityStub(Activity.class,options);


    public boolean isReturnOrderCreated = false;

    public boolean isReturnOrderPickedUp = false;

    public boolean isReturnOrderQC = false;

    public boolean isReturnOrderDeliveredToWH = false;

    @Override
    public void startApprovalWorkflow(OrderRequest orderRequest) {

        activity.placeReturnOrder();

        System.out.println("***** Waiting for the return order to get created");
        Workflow.await(() -> isReturnOrderCreated);

        System.out.println("***** Please wait till return order is picked up");
        Workflow.await(() -> isReturnOrderPickedUp);

        int version = Workflow.getVersion("changeStatus",Workflow.DEFAULT_VERSION,1);

        if (version != 1) {
            System.out.println("***** Please wait till return order is quality checked");
            Workflow.await(() -> isReturnOrderQC);

        }
        Workflow.await(() -> isReturnOrderDeliveredToWH);
    }

    @Override
    public void signalReturnOrderCreated() {
        activity.setReturnOrderCreated();
        this.isReturnOrderCreated = true;
    }

    @Override
    public void signalReturnOrderPickedUp(OrderStatusRequest orderStatusRequest) {
        if(isReturnOrderCreated && !(isReturnOrderDeliveredToWH)){
            activity.setReturnOrderPickedUp(orderStatusRequest);
            this.isReturnOrderPickedUp = true;
        }
        else{
            System.out.println("*****Signal not in order..Please see if Created");
        }
    }

    @Override
    public void signalReturnOrderQC(String orderNo){
        if(isReturnOrderPickedUp && !(isReturnOrderDeliveredToWH)){
            activity.setReturnOrderQC(orderNo);
            this.isReturnOrderQC = true;
        }
        else{
            System.out.println("*****Signal not in order..Please see if PickedUp");
        }
    }

    @Override
    public void signalReturnOrderDeliveredToWH(OrderStatusRequest orderStatusRequest) {
        if(isReturnOrderPickedUp || isReturnOrderQC){
            activity.setReturnOrderDeliveredToWH(orderStatusRequest);
            this.isReturnOrderDeliveredToWH = true;
        }
        else{
            System.out.println("*****Signal not in order..Please see if PickedUp/QC");
        }
    }
}
