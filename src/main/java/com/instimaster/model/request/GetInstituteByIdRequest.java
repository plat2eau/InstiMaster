package com.instimaster.model.request;

import com.instimaster.constant.InputValidationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;



@Data
public class GetInstituteByIdRequest {
    @Length(max = InputValidationRegex.MAX_ID)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.ID_REGEX,
            message = InputValidationRegex.ID_ADVICE)
    private String id;
}
