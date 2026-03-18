package com.mm.smart_link_platform.service;

public interface RedirectService {

    String resolveLink(String shortCode, String ip, String userAgent, String referer);

}
