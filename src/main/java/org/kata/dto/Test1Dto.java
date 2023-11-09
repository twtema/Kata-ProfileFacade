package org.kata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "blablabla")
public class Test1Dto {
    @Schema(description = "ICP", example = "123-456-7890")
    private String icp;

    @Schema(description = "First name", example = "Steve")
    private String name;

    @Override
    public String toString() {
        return "Test1Dto{" +
                "icp='" + icp + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
