package tech.picnic.assignment.impl;

import tech.picnic.assignment.impl.event.InputPickEvent;
import tech.picnic.assignment.impl.event.OutputPickEvent;
import tech.picnic.assignment.impl.vo.PickVO;
import tech.picnic.assignment.impl.vo.PickerVO;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A class to process a batch of Pick Events.
 */
public class PickEventsBatchProcessor {
    private static final String TEMPERATURE = "ambient";
    private final Map<PickerVO, OutputPickEvent> pickersMap = new TreeMap<>();

    public void processEvents(List<InputPickEvent> pickEvents) {
        pickEvents.forEach(pickEvent -> {
                    if (pickEvent.getArticle() != null) {
                        if (TEMPERATURE.equals(pickEvent.getArticle().getTemperature_zone())) {
                            buildOutPutPickEvent(pickersMap, pickEvent);
                        }
                    }
                }
        );
    }

    public List<OutputPickEvent> processOutput() {
        List<OutputPickEvent> outputPickEvents = pickersMap.keySet().stream().map(pickersMap::get).collect(Collectors.toList());
        pickersMap.clear();
        return outputPickEvents;
    }

    public int getBatchSize() {
        return pickersMap.size();
    }

    private void buildOutPutPickEvent(Map<PickerVO, OutputPickEvent> pickersMap, InputPickEvent pickEvent) {
        OutputPickEvent outputPickEvent = pickersMap.get(pickEvent.getPicker());

        if (outputPickEvent == null) {
            outputPickEvent = new OutputPickEvent();
            outputPickEvent.setPicker_name(pickEvent.getPicker().getName());
            outputPickEvent.setActive_since(pickEvent.getPicker().getActive_since());
        }

        outputPickEvent.getPicks().add(getPick(pickEvent.getArticle().getName(), pickEvent.getTimestamp()));
        pickersMap.put(pickEvent.getPicker(), outputPickEvent);
    }

    private PickVO getPick(String articleName, String timeStamp) {
        PickVO pickVO = new PickVO();
        pickVO.setArticle_name(articleName.toUpperCase());
        pickVO.setTimestamp(timeStamp);
        return pickVO;
    }
}