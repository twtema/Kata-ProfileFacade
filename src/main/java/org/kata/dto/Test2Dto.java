package org.kata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "blablabla")
public class Test2Dto {

    @Schema(description = "ICP", example = "123-456-7890")
    private String icp;

    @Override
    public String toString() {
        return "Test2Dto{" +
                "icp='" + icp + '\'' +
                '}';
    }
}
