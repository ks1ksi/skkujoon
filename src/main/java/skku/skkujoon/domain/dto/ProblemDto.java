package skku.skkujoon.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class ProblemDto {

    @JsonProperty("problemId")
    private Long problemNumber;

    private String titleKo;

    private int level;

    private int solvedBySkku;

}
