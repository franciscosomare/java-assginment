package tech.picnic.assignment.impl;

import org.junit.jupiter.api.Test;
import tech.picnic.assignment.impl.event.OutputPickEvent;
import tech.picnic.assignment.impl.vo.PickVO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputPickEventWriterTest {

    private OutputPickEventWriter outputPickEventWriter = new OutputPickEventWriter();

    @Test
    public void shouldPicksBeWritedIntoOutputStream() throws IOException {
        // given
        List<OutputPickEvent> outputPickEvents = getOutputPickEvents();
        ByteArrayOutputStream sink = new ByteArrayOutputStream();

        // when
        outputPickEventWriter.createOutputStreamSink(outputPickEvents, sink);
        String actualOutput = new String(sink.toByteArray(), StandardCharsets.UTF_8);

        // then
        assertTrue(actualOutput.contains("Francisco"));
        assertTrue(actualOutput.contains("2018-09-20T08:20:00Z"));
        assertTrue(actualOutput.contains("ACME TOMATE"));
        assertTrue(actualOutput.contains("2018-12-20T11:50:48Z"));
    }

    private List<OutputPickEvent> getOutputPickEvents() {
        List<OutputPickEvent> outputPickEvents = new ArrayList<>();
        OutputPickEvent outputPickEvent = getOutputPickEvent();
        outputPickEvents.add(outputPickEvent);
        return outputPickEvents;
    }

    private OutputPickEvent getOutputPickEvent() {
        OutputPickEvent outputPickEvent = new OutputPickEvent();
        outputPickEvent.setActive_since("2018-09-20T08:20:00Z");
        outputPickEvent.setPicker_name("Francisco");
        outputPickEvent.setPicks(getPicks());
        return outputPickEvent;
    }

    private SortedSet<PickVO> getPicks() {
        TreeSet<PickVO> picks = new TreeSet<>();
        PickVO pickVO = getPickVO();
        picks.add(pickVO);
        return picks;
    }

    private PickVO getPickVO() {
        PickVO pickVO = new PickVO();
        pickVO.setArticle_name("ACME TOMATE");
        pickVO.setTimestamp("2018-12-20T11:50:48Z");
        return pickVO;
    }
}