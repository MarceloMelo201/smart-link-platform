package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.service.TokenService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String CHARACTERS_BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int LENGTH = 9;

    @Override
    public String generateShortCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int index = SECURE_RANDOM.nextInt(CHARACTERS_BASE62.length());
            sb.append(CHARACTERS_BASE62.charAt(index));
        }
        return sb.toString();
    }
}
