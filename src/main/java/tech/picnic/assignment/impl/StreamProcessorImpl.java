package tech.picnic.assignment.impl;

import tech.picnic.assignment.api.StreamProcessor;
import tech.picnic.assignment.impl.event.InputPickEvent;
import tech.picnic.assignment.impl.map.InputStreamMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StreamProcessorImpl implements StreamProcessor {

    private static final int TIME_OUT = 0;
    private static final int SUB_LIST_START = 0;

    private final int maxEvents;
    private final Duration maxTime;
    private int pickEventsRead;
    private final OutputPickEventWriter outputPickEventWriter = new OutputPickEventWriter();
    private boolean shouldStillProcessEvents = true;

    public StreamProcessorImpl(int maxEvents, Duration maxTime) {
        this.maxEvents = maxEvents;
        this.maxTime = maxTime;
    }

    @Override
    public void process(InputStream source, OutputStream sink) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(source);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        InputStreamMapper inputStreamMapper = new InputStreamMapper();
        PickEventsBatchProcessor pickEventsBatchProcessor = new PickEventsBatchProcessor();

        Date processStartTime = Calendar.getInstance().getTime();

        while (shouldStillProcessEvents) {
            Date now = Calendar.getInstance().getTime();
            if (maxTime.compareTo(Duration.ofMillis(now.getTime() - processStartTime.getTime())) < TIME_OUT) {
                outputPickEventWriter.createOutputStreamSink(pickEventsBatchProcessor.processOutput(), sink);
                shouldStillProcessEvents = false;
            } else {
                List<InputPickEvent> pickEvents = inputStreamMapper.inputStreamToInputPickEvents(reader);
                if (!pickEvents.isEmpty()) {
                    processEventsInBatch(sink, pickEventsBatchProcessor, pickEvents);
                }
            }
        }
    }

    /**
     * Processes Pick Events from the given input stream, writen the result to the provided output stream.
     *
     * @param sink                     The source of data to be processed.
     * @param pickEventsBatchProcessor provides the functionalities to process the batch of events
     * @param pickEvents               Pick Events read
     */
    private void processEventsInBatch(OutputStream sink, PickEventsBatchProcessor pickEventsBatchProcessor, List<InputPickEvent> pickEvents) {

        pickEventsRead += pickEvents.size();

        if (pickEventsRead == maxEvents) {
            pickEventsBatchProcessor.processEvents(pickEvents);
            outputPickEventWriter.createOutputStreamSink(pickEventsBatchProcessor.processOutput(), sink);
            shouldStillProcessEvents = false;
        } else if (pickEventsBatchProcessor.getBatchSize() + pickEventsRead <= maxEvents) {
            pickEventsBatchProcessor.processEvents(pickEvents);
        } else {
            int eventsToRead = maxEvents - pickEventsBatchProcessor.getBatchSize();
            List<InputPickEvent> inputPickEventsToRead = pickEvents.subList(SUB_LIST_START, eventsToRead);
            pickEventsBatchProcessor.processEvents(inputPickEventsToRead);
            outputPickEventWriter.createOutputStreamSink(pickEventsBatchProcessor.processOutput(), sink);
            shouldStillProcessEvents = false;
        }
    }
}