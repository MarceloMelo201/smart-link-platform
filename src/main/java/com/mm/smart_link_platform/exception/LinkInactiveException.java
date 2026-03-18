package com.mm.smart_link_platform.exception;

public class LinkInactiveException extends RuntimeException{
    public LinkInactiveException() {
        super("This link is no longer active.");
    }
}
