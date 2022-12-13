package skku.skkujoon.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProblemDto {
    private String titleKo;

    private int level;

    private boolean isSolvable;

    private boolean isPartial;

    public UpdateProblemDto(String titleKo, int level, boolean isSolvable, boolean isPartial) {
        this.titleKo = titleKo;
        this.level = level;
        this.isSolvable = isSolvable;
        this.isPartial = isPartial;
    }

}
