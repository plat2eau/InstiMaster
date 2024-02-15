package com.instimaster.constant;

import lombok.Getter;

@Getter
public class InputValidationRegex {

    // Input Validation Regex
    // Loosely verifying contact regex for international numbers with country code
    // Ideally more intrinsic contact number validation should be handled by front-end
    // Taking 15 chars max https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s03.html
    public static final String CONTACT_REGEX = "^(\\+|)([0-9]){1,15}$";
    // Made to allow format INS<UUID>
    public static final String ID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    // No supporting names in languages other than english
    public static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    // Supporting websites in the format https://example.co.in or http://example.co.in
    public static final String WEBSITE_REGEX = "^((http)|(https))\\:\\/\\/([a-z0-9.\\-\\_])+\\.([a-z0-9.\\-\\_])+";
    // Supporting established years starting from 1000 for very old, ancient institutions
    // Oldest institution date back to 1088, so we can safely use 4 chars
    public static final String YEAR_REGEX = "^([0-9]){4}$";


    // Input Validation Advices
    // We can also localise the advice if we want to support multiple languages
    public static final String CONTACT_ADVICE = "Invalid contact format";
    public static final String ID_ADVICE = "Invalid institute id";
    public static final String NAME_ADVICE = "Name should only contain alphabets and spaces";
    public static final String WEBSITE_ADVICE = "Website URL format is not valid";
    public static final String YEAR_ADVICE = "Year should be a 4 digit number";

    // Input Validation Char limits

    public static final int MAX_CONTACT = 15;
    public static final int MAX_ID = 40;
    public static final int MAX_NAME = 75;
    public static final int MAX_HEAD = 75;
    public static final int MAX_WEBSITE = 200;
    public static final int MAX_LOCATION = 250;
}
