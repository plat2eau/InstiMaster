package com.instimaster.model.response;

import com.instimaster.entity.Institute;
import lombok.Data;


@Data
public class GetInstituteByIdResponse {
    private final Institute institute;
}
