package com.instimaster.model.request;

import com.instimaster.constant.InputValidationRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class UpdateInstituteRequest {

    @Length(max = InputValidationRegex.MAX_ID)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.ID_REGEX,
            message = InputValidationRegex.ID_ADVICE)
    private String id;

    @Length(max = InputValidationRegex.MAX_NAME)
    @NotNull
    @NotBlank
    private String name;

    @Length(max = InputValidationRegex.MAX_LOCATION)
    @NotNull
    @NotBlank
    private String location;

    @Length(max = InputValidationRegex.MAX_CONTACT)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.CONTACT_REGEX,
            message = InputValidationRegex.CONTACT_ADVICE)
    private String contact;

    @Length(max = InputValidationRegex.MAX_HEAD)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.NAME_REGEX,
            message = InputValidationRegex.NAME_ADVICE)
    private String head;

    @Length(max = InputValidationRegex.MAX_WEBSITE)
    @Pattern(regexp = InputValidationRegex.WEBSITE_REGEX,
            flags = {Pattern.Flag.CASE_INSENSITIVE},
            message = InputValidationRegex.WEBSITE_ADVICE)
    private String website;

    @Length(max = 4)
    @Pattern(regexp = InputValidationRegex.YEAR_REGEX,
            message = InputValidationRegex.YEAR_ADVICE)
    private String estabYear;
}
