package com.temporal.poc.worklow;

import com.temporal.poc.dto.OrderStatusRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity {

    @ActivityMethod
    void placeReturnOrder();

    @ActivityMethod
    void setReturnOrderCreated();

    @ActivityMethod
    void setReturnOrderPickedUp(OrderStatusRequest orderStatusRequest);

    @ActivityMethod
    void setReturnOrderQC(String orderNo);

    @ActivityMethod
    void setReturnOrderDeliveredToWH(OrderStatusRequest orderStatusRequest);

}
