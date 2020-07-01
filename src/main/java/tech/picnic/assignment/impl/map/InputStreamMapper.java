package tech.picnic.assignment.impl.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.picnic.assignment.impl.event.InputPickEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputStreamMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamMapper.class);

    public List<InputPickEvent> inputStreamToInputPickEvents(BufferedReader reader) throws IOException {


        List<InputPickEvent> inputPickEvents = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonLine;

        LOGGER.info("Starting to read input file");

        while ((jsonLine = reader.readLine()) != null) {
            try {
                InputPickEvent inputPickEvent = objectMapper.readValue(jsonLine, InputPickEvent.class);
                inputPickEvents.add(inputPickEvent);
            } catch (Exception e) {
                return inputPickEvents;
            }
        }
        return inputPickEvents;
    }
}
