package vn.lotte.demo;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SQSMessageProcessingService {

    private final ProcessingService processingService;

    public SQSMessageProcessingService(ProcessingService processingService) {
        this.processingService = processingService;
    }

    public OutputObject processMessage(SQSMessageInput sqsMessageInput) {
        // Log the receipt of the SQS message
        System.out.println("Processing SQS Message: " + sqsMessageInput.getMessageId());

        // Parse the message body (assuming it's a JSON that maps to InputObject)
        InputObject input = parseMessageBody(sqsMessageInput.getBody());

        // Use the existing ProcessingService to process the InputObject
        return processingService.process(input);
    }

    private InputObject parseMessageBody(String messageBody) {
        // Simulate JSON parsing; you can use a library like Jackson for real implementation
        InputObject input = new InputObject();

        // Example parsing logic (replace with real JSON parsing)
        String[] parts = messageBody.split(":");
        input.setName(parts[0]);
        input.setGreeting(parts[1]);

        return input;
    }
}
