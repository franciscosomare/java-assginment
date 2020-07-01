package tech.picnic.assignment.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.picnic.assignment.impl.event.OutputPickEvent;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OutputPickEventWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutputPickEventWriter.class);

    public void createOutputStreamSink(List<OutputPickEvent> outputPickEvents, OutputStream sink) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String outputEvents = objectMapper.writeValueAsString(outputPickEvents);
            sink.write(outputEvents.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Sink was processed successfully");
        } catch (Exception e) {
            LOGGER.info("Sink was processed with error: " + e);
        }
    }
}