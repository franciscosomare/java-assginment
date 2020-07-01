package tech.picnic.assignment.impl;

import org.junit.jupiter.api.Test;
import tech.picnic.assignment.impl.event.InputPickEvent;
import tech.picnic.assignment.impl.event.OutputPickEvent;
import tech.picnic.assignment.impl.vo.ArticleVO;
import tech.picnic.assignment.impl.vo.PickerVO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PickEventsBatchProcessorTest {

    private final PickEventsBatchProcessor pickEventsBatchProcessor = new PickEventsBatchProcessor();

    @Test
    public void shouldProcessPicksWithTemperatureAmbient() {
        // given
        List<InputPickEvent> inputPickEventList = getInputPickEventsAmbient();

        // when
        pickEventsBatchProcessor.processEvents(inputPickEventList);

        // then
        assertEquals(1, pickEventsBatchProcessor.getBatchSize());
    }

    @Test
    public void shouldNotProcessPicksWithTemperatureChilled() {
        // given
        List<InputPickEvent> inputPickEventList = getInputPickEventsChilled();

        // when
        pickEventsBatchProcessor.processEvents(inputPickEventList);

        // then
        assertEquals(0, pickEventsBatchProcessor.getBatchSize());
    }

    @Test
    public void shouldPickEventsProcessedBeOrdered() {
        // given
        List<InputPickEvent> inputPickEventList = new ArrayList<>();
        inputPickEventList.add(getInputPickEvent("ambient"));
        inputPickEventList.add(getInputPickEvent("chilled"));
        inputPickEventList.add(getSecondPickEvent("ambient"));

        // when
        pickEventsBatchProcessor.processEvents(inputPickEventList);
        List<OutputPickEvent> outputPickEvents = pickEventsBatchProcessor.processOutput();

        // then
        assertEquals("Joris", outputPickEvents.get(0).getPicker_name());
        assertEquals("Francisco", outputPickEvents.get(1).getPicker_name());
    }

    @Test
    public void shouldPicksBeOrderedByTimestamp() {
        // given
        List<InputPickEvent> inputPickEventList = new ArrayList<>();
        inputPickEventList.add(getInputPickEvent("ambient"));
        inputPickEventList.add(getSecondPickEvent("ambient"));
        inputPickEventList.add(getThirdPickEvent("ambient"));

        // when
        pickEventsBatchProcessor.processEvents(inputPickEventList);
        List<OutputPickEvent> outputPickEvents = pickEventsBatchProcessor.processOutput();

        // then
        assertEquals("2018-12-18T11:50:48Z", outputPickEvents.get(0).getPicks().first().getTimestamp());
        assertEquals("2018-12-19T11:50:48Z", outputPickEvents.get(0).getPicks().last().getTimestamp());
    }

    private List<InputPickEvent> getInputPickEventsChilled() {
        List<InputPickEvent> inputPickEvents = new ArrayList<>();
        InputPickEvent inputPickEvent = getInputPickEvent("chilled");
        inputPickEvents.add(inputPickEvent);
        return inputPickEvents;
    }

    private List<InputPickEvent> getInputPickEventsAmbient() {
        List<InputPickEvent> inputPickEvents = new ArrayList<>();
        InputPickEvent inputPickEvent = getInputPickEvent("ambient");
        inputPickEvents.add(inputPickEvent);
        return inputPickEvents;
    }

    private InputPickEvent getInputPickEvent(String temperature) {
        InputPickEvent inputPickEvent = new InputPickEvent();
        inputPickEvent.setArticle(getArticle(temperature));
        inputPickEvent.setTimestamp("2018-12-20T11:50:48Z");
        inputPickEvent.setId("1");
        inputPickEvent.setPicker(getPicker());
        inputPickEvent.setQuantity(1);
        return inputPickEvent;
    }

    private InputPickEvent getSecondPickEvent(String temperature) {
        InputPickEvent inputPickEvent = new InputPickEvent();
        inputPickEvent.setArticle(getArticle(temperature));
        inputPickEvent.setTimestamp("2018-12-19T11:50:48Z");
        inputPickEvent.setId("5");
        inputPickEvent.setPicker(getPickerSecondEvent());
        inputPickEvent.setQuantity(1);
        return inputPickEvent;
    }

    private InputPickEvent getThirdPickEvent(String temperature) {
        InputPickEvent inputPickEvent = new InputPickEvent();
        inputPickEvent.setArticle(getSecondArticle(temperature));
        inputPickEvent.setTimestamp("2018-12-18T11:50:48Z");
        inputPickEvent.setId("56");
        inputPickEvent.setPicker(getPickerSecondEvent());
        inputPickEvent.setQuantity(1);
        return inputPickEvent;
    }

    private PickerVO getPicker() {
        PickerVO pickerVO = new PickerVO();
        pickerVO.setActive_since("2018-09-20T08:20:00Z");
        pickerVO.setId("12");
        pickerVO.setName("Francisco");
        return pickerVO;
    }

    private PickerVO getPickerSecondEvent() {
        PickerVO pickerVO = new PickerVO();
        pickerVO.setActive_since("2018-08-20T08:20:00Z");
        pickerVO.setId("13");
        pickerVO.setName("Joris");
        return pickerVO;
    }

    private ArticleVO getArticle(String temperature) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId("3");
        articleVO.setName("ACME Bananas");
        articleVO.setTemperature_zone(temperature);
        return articleVO;
    }

    private ArticleVO getSecondArticle(String temperature) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId("3");
        articleVO.setName("ACME Apples");
        articleVO.setTemperature_zone(temperature);
        return articleVO;
    }
}