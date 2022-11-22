package com.temporal.poc;

import com.temporal.poc.constants.TemporalConstants;
import com.temporal.poc.worklow.Activity;
import com.temporal.poc.worklow.Greetings;
import com.temporal.poc.worklow.GreetingsImpl;
import com.temporal.poc.worklow.WorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TemporalpocApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(TemporalpocApplication.class, args);
		WorkerFactory factory = appContext.getBean(WorkerFactory.class);
		Activity signUpActivity = appContext.getBean(Activity.class);
		Worker worker = factory.newWorker(TemporalConstants.QUEUE_NAME);
		worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
		worker.registerActivitiesImplementations(signUpActivity);
		factory.start();
	}

}
