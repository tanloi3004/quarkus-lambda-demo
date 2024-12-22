package vn.lotte.demo;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

@Named("sqsTriggerHandler")
public class SqsTriggerHandler implements RequestHandler<SQSMessageInput, OutputObject> {

    @Inject
    SQSMessageProcessingService messageProcessingService;

    @Inject
    SqsClient sqsClient;

    @ConfigProperty(name = "quarkus.sqs.target-queue-url")
    String targetQueueUrl;

    @Inject
    ObjectMapper objectMapper; // For JSON serialization

    @Override
    public OutputObject handleRequest(SQSMessageInput sqsMessageInput, Context context) {
        // Validate input
        if (sqsMessageInput == null || sqsMessageInput.getBody() == null) {
            throw new IllegalArgumentException("Invalid SQS message input");
        }

        // Step 1: Process the input message
        OutputObject output = messageProcessingService.processMessage(sqsMessageInput);

        // Serialize OutputObject to JSON for SQS
        String messageBody;
        try {
            messageBody = objectMapper.writeValueAsString(output);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize OutputObject to JSON", e);
        }

        // Step 2: Push to another SQS queue
        try {
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(targetQueueUrl)
                .messageBody(messageBody)
                .build();
            sqsClient.sendMessage(sendMessageRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to SQS", e);
        }

        // Step 3: Add request ID to the response
        return output.setRequestId(context.getAwsRequestId());
    }
}
