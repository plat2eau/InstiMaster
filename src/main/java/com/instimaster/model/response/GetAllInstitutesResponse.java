package com.instimaster.model.response;

import com.instimaster.model.institute.Institute;
import lombok.Data;

import java.util.List;


@Data
public class GetAllInstitutesResponse {
    private final List<Institute> institutes;
}
