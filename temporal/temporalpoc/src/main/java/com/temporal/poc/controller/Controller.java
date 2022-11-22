package com.temporal.poc.controller;

import com.temporal.poc.Service.GreetingsService;
import com.temporal.poc.Service.OrderFulfilmentService;
import com.temporal.poc.dto.OrderRequest;
import com.temporal.poc.dto.OrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    GreetingsService greetingsService;

    @Autowired
    OrderFulfilmentService orderFulfilmentService;

    @PostMapping("/placeReturnOrder")
    public String returnOrderPlaced(@RequestBody OrderRequest orderRequest) {
        orderFulfilmentService.placeOrder(orderRequest);
        return "Return Order: "+orderRequest.getReturnOrderNo() +" is Placed";
    }

    @PostMapping("/returnOrderCreated")
    public String returnOrderCreated(@RequestBody OrderRequest orderRequest) {
        orderFulfilmentService.makeReturnOrderCreated(orderRequest);
        return "Return Order: "+orderRequest.getReturnOrderNo() +" is Created";
    }

    @PostMapping("/returnOrderPickedUp")
    public String returnOrderPickedUp(@RequestBody OrderStatusRequest orderStatusRequest) {
        orderFulfilmentService.makeReturnOrderPickedUp(orderStatusRequest);
        return "Return Order: "+orderStatusRequest.getReturnOrderNo() +" is Picked Up";
    }

    @PostMapping("/returnOrderQC")
    public String returnOrderQC(@RequestBody OrderStatusRequest orderStatusRequest) {
        orderFulfilmentService.makeReturnOrderQC(orderStatusRequest);
        return "Return Order: "+orderStatusRequest.getReturnOrderNo() +" is Quality Checked";
    }

    @PostMapping("/returnOrderDelivered")
    public String returnOrderDelivered(@RequestBody OrderStatusRequest orderStatusRequest) {
        orderFulfilmentService.makeReturnOrderDeliveredToWH(orderStatusRequest);
        return "Return Order: "+orderStatusRequest.getReturnOrderNo() +" is Delivered to WH";
    }

    @PostMapping("/createNamespace")
    public String createNamespace(@RequestParam("name") String nameSpace) {
        orderFulfilmentService.createNamespace(nameSpace);
        return "Namespace created";
    }

//    @PostMapping("/greet")
//    public String greet(@RequestParam("name") String name) throws ExecutionException, InterruptedException {
//        greetingsService.greetings(name);
//
//        return "Done";
//    }


}
