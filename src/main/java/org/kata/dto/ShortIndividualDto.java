package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Data
@Builder
@Jacksonized
@Schema(description = "DTO according to minimum fields in Individual entity")
public class ShortIndividualDto {
    @Schema(description = "ICP", example = "123-456-7890")
    private String icp;

    @Schema(description = "First name", example = "Steve")
    private String name;

    @Schema(description = "Surname", example = "Jobs")
    private String surname;

    @Schema(description = "Second name", example = "Paul")
    private String patronymic;

    @Schema(description = "Full name", example = "Steven Paul Jobs")
    private String fullName;

    @Schema(description = "Date of birth", example = "1955-02-24")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date birthDate;
}
