package com.instimaster.util;

import com.instimaster.entity.Institute;

import java.util.UUID;

public class IdUtil {

    public static final String PREFIX = "INS";

    public static String generateInstituteId(Institute institute) {
        String randomUUID = UUID.randomUUID().toString();
        return PREFIX.concat(randomUUID);
    }

    public static Long generateUserId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
