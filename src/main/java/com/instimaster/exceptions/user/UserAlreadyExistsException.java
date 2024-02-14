package com.instimaster.exceptions.user;

import com.instimaster.exceptions.InstiMasterException;

public class UserAlreadyExistsException extends InstiMasterException {
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
