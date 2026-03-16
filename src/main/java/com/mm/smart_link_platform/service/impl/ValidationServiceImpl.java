package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.exception.InvalidUrlException;
import com.mm.smart_link_platform.service.ValidationService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public String validateUrl(String url) {

        validateNullOrBlank(url);
        String normalizedUrl = normalizeUrl(url);
        URI uri = validateUriSyntax(normalizedUrl);
        validateProtocol(uri);
        validateHost(uri);
        return normalizedUrl;
    }

    private void validateNullOrBlank(String url) {
        if (url == null || url.isBlank()) {
            throw new InvalidUrlException("The url cannot be null or blank.");
        }
    }

    private URI validateUriSyntax(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new InvalidUrlException("Invalid URL syntax");
        }
    }

    private void validateProtocol(URI uri) {

        String scheme = uri.getScheme();

        if (scheme == null) {
            throw new InvalidUrlException("URL must contain a protocol");
        }

        if (!scheme.equalsIgnoreCase("http") &&
                !scheme.equalsIgnoreCase("https")) {

            throw new InvalidUrlException("Only HTTP and HTTPS protocols are allowed");
        }
    }

    private void validateHost(URI uri) {
        if (uri.getHost() == null) {
            throw new InvalidUrlException("URL must contain a valid host");
        }
    }

    private String normalizeUrl(String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        return url;
    }
}