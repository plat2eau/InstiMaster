package com.instimaster.util;

import com.instimaster.model.institute.Institute;

import java.util.UUID;

public class IdUtil {

    public static final String PREFIX = "INS";

    public static String getInstituteId(Institute institute) {
        String randomUUID = UUID.randomUUID().toString();
        return PREFIX.concat(randomUUID);
    }
}
