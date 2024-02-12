package com.instimaster.model.response;

import com.instimaster.model.institute.Institute;
import lombok.Data;


@Data
public class GetInstituteByIdResponse {
    private final Institute institute;
}
