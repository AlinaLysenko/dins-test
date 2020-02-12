package com.alina.dinstest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DinsTestApplicationTests {

    private static final String TOKEN_ID = "83tgoebidxgj37gehvusnzjd";
    private final MockMvc mockMvc;

    @MockBean
    private SimpleController controller;

    @Autowired
    public DinsTestApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testTokenIsAbsent() throws Exception {
        String name = "Ivan";
        long companyId = 777L;
        when(controller.findUser(eq(companyId), eq(name), isNull()))
                .thenReturn(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        mockMvc.perform(get(endpoint(companyId, name))).andExpect(status().isUnauthorized());
    }

    @Test
    public void testTokenIsWrong() throws Exception {
        String name = "Ivan";
        long companyId = 777L;
        when(controller.findUser(eq(companyId), eq(name), AdditionalMatchers.not(eq(TOKEN_ID))))
                .thenReturn(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        mockMvc.perform(get(endpoint(companyId, name)).cookie(new Cookie("token", TOKEN_ID + "wrong")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testTokenIsPresentAndAccessGranted() throws Exception {
        String name = "Ivan";
        long companyId = 777L;
        when(controller.findUser(eq(companyId), eq(name), eq(TOKEN_ID)))
                .thenReturn(new ResponseEntity(HttpStatus.OK));
        mockMvc.perform(get(endpoint(companyId, name)).cookie(new Cookie("token", TOKEN_ID)))
                .andExpect(status().isOk());
    }

    @Test
    public void testTokenIsPresentAndAccessIsNotGranted() throws Exception {
        String name = "Ivan";
        when(controller.findUser(AdditionalMatchers.not(eq(777L)), eq(name), eq(TOKEN_ID)))
                .thenReturn(new ResponseEntity(HttpStatus.FORBIDDEN));
        mockMvc.perform(get(endpoint(778L, name)).cookie(new Cookie("token", TOKEN_ID)))
                .andExpect(status().isForbidden());
    }

    private String endpoint(long companyId, String name) {
        return String.format("/company/%d/users?name=%s", companyId, name);
    }
}
