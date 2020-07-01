package tech.picnic.assignment.impl.event;

import lombok.Getter;
import lombok.Setter;
import tech.picnic.assignment.impl.vo.ArticleVO;
import tech.picnic.assignment.impl.vo.PickerVO;

@Getter
@Setter
public class InputPickEvent {

    private String id;
    private String timestamp;
    private PickerVO picker;
    private ArticleVO article;
    private Integer quantity;

}