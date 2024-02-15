package com.instimaster.util;

import com.instimaster.entity.Institute;

import java.util.UUID;

public class IdUtil {

    public static String generateInstituteId(Institute institute) {
        return UUID.randomUUID().toString();
    }

    public static Long generateUserId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
