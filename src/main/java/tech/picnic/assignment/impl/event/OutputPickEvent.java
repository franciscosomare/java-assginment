package tech.picnic.assignment.impl.event;

import lombok.Getter;
import lombok.Setter;
import tech.picnic.assignment.impl.vo.PickVO;

import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
public class OutputPickEvent {

    private String picker_name;
    private String active_since;
    private SortedSet<PickVO> picks = new TreeSet<>();

}