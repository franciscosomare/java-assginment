package tech.picnic.assignment.impl.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import tech.picnic.assignment.impl.map.ActiveSinceDateMapper;

@Getter
@Setter
public class PickVO implements Comparable<PickVO> {

    private String article_name;
    private String timestamp;

    @SneakyThrows
    @Override
    public int compareTo(PickVO o) {
        return ActiveSinceDateMapper.getActiveSinceDate(timestamp).compareTo(ActiveSinceDateMapper.getActiveSinceDate(o.timestamp));
    }
}