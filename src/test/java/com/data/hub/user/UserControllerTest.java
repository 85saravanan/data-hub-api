package com.data.hub.user;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsListsAndDeletesAUser() throws Exception {
        String request = "{\"name\":\"Ada Lovelace\",\"email\":\"ada@example.com\"}";

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }
}
