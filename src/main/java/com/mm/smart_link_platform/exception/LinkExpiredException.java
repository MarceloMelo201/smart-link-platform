package com.mm.smart_link_platform.exception;

public class LinkExpiredException extends RuntimeException{
    public LinkExpiredException() {
        super("Link expired, please try again");
    }
}
