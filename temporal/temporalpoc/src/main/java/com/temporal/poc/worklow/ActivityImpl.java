package com.temporal.poc.worklow;

import com.temporal.poc.dto.OrderStatusRequest;

public class ActivityImpl implements Activity{

    @Override
    public void placeReturnOrder() {

        System.out.println("***** Return Order has been placed");
    }

    @Override
    public void setReturnOrderCreated() {

        System.out.println("***** Return Order has been created");
    }

    @Override
    public void setReturnOrderPickedUp(OrderStatusRequest orderStatusRequest) {
        System.out.println("***** Status for Return Order "+orderStatusRequest.getReturnOrderNo()+" is: "+orderStatusRequest.getStatus());
    }

    @Override
    public void setReturnOrderQC(String orderNo){
        System.out.println("***** Status for Return Order "+orderNo+" is: QC Checked ");
    }

    @Override
    public void setReturnOrderDeliveredToWH(OrderStatusRequest orderStatusRequest) {
        System.out.println("***** Status for Return Order "+orderStatusRequest.getReturnOrderNo()+" is: "+orderStatusRequest.getStatus());
    }
}
