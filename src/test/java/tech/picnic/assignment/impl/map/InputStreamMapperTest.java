package tech.picnic.assignment.impl.map;

import org.junit.jupiter.api.Test;
import tech.picnic.assignment.impl.event.InputPickEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputStreamMapperTest {

    private InputStreamMapper inputStreamMapper = new InputStreamMapper();

    @Test
    public void shouldMapInputStreamToPickEventsCorrectly() throws IOException {

        // given
        InputStream source = getClass().getResourceAsStream("../happy-path-input.json-stream");
        InputStreamReader inputStreamReader = new InputStreamReader(source);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        // when
        List<InputPickEvent> inputPickEventList = inputStreamMapper.inputStreamToInputPickEvents(reader);

        // then
        assertEquals(3, inputPickEventList.size());
    }
}