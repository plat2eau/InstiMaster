package com.instimaster.constant;

import lombok.Getter;

@Getter
public class InputValidationRegex {

    // Loosely verifying contact regex for international numbers with country code
    // Ideally more intrinsic contact number validation should be handled by front-end
    // Taking 15 chars max https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s03.html
    public static final String CONTACT_REGEX = "^(\\+|)([0-9]){1,15}$";
    // No supporting names in languages other than english
    public static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    // Supporting websites in the format https://example.co.in or http://example.co.in
    public static final String WEBSITE_REGEX = "^((http)|(https))\\:\\/\\/([a-z0-9.\\-\\_])+\\.([a-z0-9.\\-\\_])+";
    // Supporting established years starting from 1000 for very old, ancient institutions
    // Oldest institution date back to 1088, so we can safely use 4 chars
    public static final String YEAR_REGEX = "^([0-9]){4}$";
}
