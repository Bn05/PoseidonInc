package com.nnk.poseidoninc.UT.ControllerWebApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "userTEst", authorities = {"USER"})
class HomeControllerWebAppTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void voidCall() throws Exception {

        mockMvc.perform(get(""))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void badCall() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(view().name("home"));
    }
}