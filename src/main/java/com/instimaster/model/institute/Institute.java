package com.instimaster.model.institute;

import com.instimaster.constant.InputValidationAdvice;
import com.instimaster.constant.InputValidationRegex;
import com.instimaster.util.IdUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.Length;

import java.lang.reflect.InvocationTargetException;

@Entity
@Table(name="Institutes")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Institute {

    @Id
    private String id;
    @Length(min = 0, max = 75)
    @NotNull
    @NotBlank
    private String name;
    @Length(min = 0, max = 150)
    @NotNull
    @NotBlank
    private String location;
    @Length(min = 0, max = 15)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.CONTACT_REGEX,
            message = InputValidationAdvice.CONTACT_ADVICE)
    private String contact;
    @Length(min = 0, max = 10)
    @NotNull
    @NotBlank
    @Pattern(regexp = InputValidationRegex.NAME_REGEX,
            message = InputValidationAdvice.NAME_ADVICE)
    private String head;
    @Length(min = 0, max = 10)
    @Pattern(regexp = InputValidationRegex.WEBSITE_REGEX,
            flags = {Pattern.Flag.CASE_INSENSITIVE},
            message = InputValidationAdvice.WEBSITE_ADVICE)
    private String website;
    @Length(min = 0, max = 10)
    @Pattern(regexp = InputValidationRegex.YEAR_REGEX,
            message = InputValidationAdvice.YEAR_ADVICE)
    private String estabYear;

    public Institute(Institute instituteParam) {
        try {
            BeanUtils.copyProperties(this, instituteParam);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        this.id = IdUtil.getInstituteId(this);
    }
}
