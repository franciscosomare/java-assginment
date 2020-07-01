package tech.picnic.assignment.impl.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import tech.picnic.assignment.impl.map.ActiveSinceDateMapper;

@Getter
@Setter
public class PickerVO implements Comparable<PickerVO> {

    private String id;
    private String name;
    private String active_since;

    @SneakyThrows
    @Override
    public int compareTo(PickerVO o) {
        int comparationResult = ActiveSinceDateMapper.getActiveSinceDate(active_since).compareTo(ActiveSinceDateMapper.getActiveSinceDate(o.active_since));
        if (comparationResult != 0) {
            return comparationResult;
        }
        return id.compareTo(o.getId());
    }
}
