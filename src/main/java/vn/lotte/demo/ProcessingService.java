package vn.lotte.demo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class ProcessingService {
    @Inject
    PlaywrightService playwrightService;
    public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(ProcessingService.class.getName());

    public OutputObject process(InputObject input) {
        if (input.getName().equals("Stuart")) {
            throw new IllegalArgumentException(CAN_ONLY_GREET_NICKNAMES);
        }
        String result = input.getGreeting() + " " + input.getName();
        OutputObject out = new OutputObject();
        out.setResult(result);
        return out;
    }

    public OutputObject process(String input) {
        OutputObject out = new OutputObject();
        try {
            ShareUrlRequest shareUrlRequest = objectMapper.readValue(input, ShareUrlRequest.class);
            String metaDescription = playwrightService.getMetaDescription(shareUrlRequest);
            out.setResult("Meta description: " + metaDescription);
            // Process the ShareUrlRequest object as needed
            LOGGER.info("Processing ShareUrlRequest: " + metaDescription + ", " + shareUrlRequest);
            return out;
        } catch (JsonProcessingException e) {
            out.setResult("Error processing ShareUrlRequest: " + e.getMessage());
        }
        return out;
    }
}