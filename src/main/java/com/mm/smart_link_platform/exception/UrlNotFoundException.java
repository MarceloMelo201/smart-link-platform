package com.mm.smart_link_platform.exception;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException() {
        super("Url not found.");
    }
}
