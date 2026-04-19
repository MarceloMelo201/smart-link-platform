package com.mm.smart_link_platform.controller;

import com.jayway.jsonpath.JsonPath;
import com.mm.smart_link_platform.config.RabbitTestConfig;
import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(RabbitTestConfig.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    void shouldRedirectLink() throws Exception {

        String json = """
                {
                 "originalUrl": "http://google.com"
                }
                """;

        String response = mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String shortCode = JsonPath.read(response, "$.shortUrl");

        mockMvc.perform(get("/" + shortCode)
                        .header("User-Agent", "JUnit-Test")
                        .header("Referer", "http://localhost"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://google.com"));
    }

    @Test
    void shouldReturn404WhenLinkNotFound() throws Exception {

        mockMvc.perform(get("/dontExists")
                        .header("User-Agent", "JUnit-Test")
                        .header("Referer", "http://localhost"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Url not found."));
    }

    @Test
    void shouldReturn410WhenLinkExpired() throws Exception {

        String json = """
                {
                  "originalUrl": "http://google.com"
                }
                """;

        String response = mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String shortCode = JsonPath.read(response, "$.shortUrl");

        Link link = linkRepository.findByShortCode(shortCode).get();
        link.setExpiresAt(LocalDateTime.now().minusDays(1));
        linkRepository.save(link);

        mockMvc.perform(get("/" + shortCode)
                        .header("User-Agent", "JUnit-Test")
                        .header("Referer", "http://localhost"))
                .andExpect(status().isGone())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Link expired, please try again."));
    }

    @Test
    void shouldReturn403WhenLinkInactive() throws Exception {

        String json = """
                {
                  "originalUrl": "http://google.com"
                }
                """;

        String response = mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String shortCode = JsonPath.read(response, "$.shortUrl");

        Link link = linkRepository.findByShortCode(shortCode).get();
        link.setActive(false);
        linkRepository.save(link);

        mockMvc.perform(get("/" + shortCode)
                        .header("User-Agent", "JUnit-Test")
                        .header("Referer", "http://localhost"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors[0]")
                        .value("This link is no longer active."));
    }
}