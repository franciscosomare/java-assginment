package tech.picnic.assignment.impl;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import tech.picnic.assignment.api.EventProcessorFactory;
import tech.picnic.assignment.api.StreamProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PickingEventProcessorFactoryTest {

    @ParameterizedTest
    @MethodSource("happyPathTestCaseInputProvider")
    void testHappyPath(
            int maxEvents,
            Duration maxTime,
            String inputResource,
            String expectedOutputResource)
            throws IOException, JSONException {
        try (EventProcessorFactory factory = new PickingEventProcessorFactory();
             StreamProcessor processor = factory.createProcessor(maxEvents, maxTime);
             InputStream source = getClass().getResourceAsStream(inputResource);
             ByteArrayOutputStream sink = new ByteArrayOutputStream()) {
            processor.process(source, sink);
            String expectedOutput = loadResource(expectedOutputResource);
            String actualOutput = new String(sink.toByteArray(), StandardCharsets.UTF_8);
            JSONAssert.assertEquals(expectedOutput, actualOutput, JSONCompareMode.STRICT);
        }
    }

    static Stream<Arguments> happyPathTestCaseInputProvider() {
        return Stream.of(
                Arguments.of(
                        100,
                        Duration.ofSeconds(30),
                        "happy-path-input.json-stream",
                        "happy-path-output.json"));
    }

    private String loadResource(String resource) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resource);
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    /**
     * Verifies that precisely one {@link EventProcessorFactory} can be service-loaded.
     */
    @Test
    void testServiceLoading() {
        Iterator<EventProcessorFactory> factories =
                ServiceLoader.load(EventProcessorFactory.class).iterator();
        assertTrue(factories.hasNext(), "No EventProcessorFactory is service-loaded");
        factories.next();
        assertFalse(factories.hasNext(), "More than one EventProcessorFactory is service-loaded");
    }
}
