package vn.lotte.demo;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import java.util.Collections;
import java.util.List;

@Named("testLambdaSqs")
public class TestLambdaSQS implements RequestHandler<SQSEvent, OutputObject> {

    @Inject
    ProcessingService service;

    @Override
    public OutputObject handleRequest(SQSEvent event, Context context) {
        // Safely retrieve the list of messages, defaulting to an empty list if null
        List<SQSEvent.SQSMessage> messages = event.getRecords();
        if (messages == null) {
            messages = Collections.emptyList();
            System.out.println("No records found in the SQSEvent.");
        }

        for (SQSEvent.SQSMessage msg : messages) {
            // Debug the message body
            System.out.println("Received message: " + msg.getBody());

            // Process each message
            service.process(msg.getBody());
        }

        return new OutputObject().setRequestId(context.getAwsRequestId());
    }
}
