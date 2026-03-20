package com.nilsson.wigellApi.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("Member with id " + id + " can not be found.");
    }
}
