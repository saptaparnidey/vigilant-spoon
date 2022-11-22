package com.temporal.poc.worklow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;


public interface Greetings {

//    public static final String QUEUE_NAME = "test_q";

    @WorkflowMethod
    public String greetWorkflow(String name);

    @SignalMethod
    void setName(String name);
}
