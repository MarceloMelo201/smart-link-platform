package com.mm.smart_link_platform.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateLink() throws Exception {

        String json = """
                {
                 "originalUrl": "http://google.com"
                }
                """;

        mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").isNotEmpty());
    }

    @Test
    void shouldReturnLinkStats() throws Exception {
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

        String shortUrl = JsonPath.read(response, "$.shortUrl");

        mockMvc.perform(get("/api/links/" + shortUrl + "/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value("http://google.com"))
                .andExpect(jsonPath("$.shortCode").value(shortUrl))
                .andExpect(jsonPath("$.totalClicks").value(0))
                .andExpect(jsonPath("$.uniqueIps").value(0))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.recentAccesses").isArray())
                .andExpect(jsonPath("$.recentAccesses").isEmpty());
    }

    @Test
    void shouldReturn404WhenLinkNotFound() throws Exception {
        mockMvc.perform(get("/api/links/dontExists/stats"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Url not found.")));
    }

}