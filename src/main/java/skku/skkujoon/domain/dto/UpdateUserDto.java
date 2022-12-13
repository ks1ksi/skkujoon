package skku.skkujoon.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String bio;

    private int solvedCount;

    private int tier;

    private int rating;

    @JsonProperty("rank")
    private int ranking;

    private int globalRank;

    public UpdateUserDto(String bio, int solvedCount, int tier, int rating, int ranking, int globalRank) {
        this.bio = bio;
        this.solvedCount = solvedCount;
        this.tier = tier;
        this.rating = rating;
        this.ranking = ranking;
        this.globalRank = globalRank;
    }

}
