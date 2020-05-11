package com.userRed.redesigned.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userRed.redesigned.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {

    }

    public Users makeTestUser(){
        Users us = new Users();
        us.setUsername("smth smth, can be blank aswel");
        us.setEmail("hotHot@hotmail.com");
        us.setName("testName");
        us.setPassword("q923uy");
        us.setDateOfBirth("2020-03-03");
        us.setGenderType("FEMALE");
        us.setCreatedAt();
        return us;
    }

    @Test
    void testBiggerThan30CharactersUsername() throws Exception {
        //register using 30 characters
        Users tempUsr = makeTestUser();
        tempUsr.setUsername("hfbakjDBADHJabdabdHJBadshbahdhdbHBWDHJbdjbasjdbasjdBjahdbdbJhasdbalsjhdbajshdBahjdbjaDBJHabdskhb");
        //it's 96 characters


        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tempUsr)))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    void testEmptyEmail_thenReturnsUNAUTHORIZED() throws Exception {

        Users us = new Users();

        us.setUsername("smth smth, can be blank aswel");
        us.setEmail("               ");
        us.setName("testName");
        us.setPassword("q923uy");
        us.setDateOfBirth("2020-03-03");
        us.setGenderType("FEMALE");

        mockMvc.perform(post("http://localhost:8080/user/registerWithMail", 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(us)))
                .andExpect(status().isUnauthorized());
    }

    @Test()
    void testCannotRegisterWithExisitingUsername_thenReturnsHttpCONFLICT() throws Exception{

        Users tmpUsr = makeTestUser();
        tmpUsr.setUsername("goku9000");
        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tmpUsr)))
                .andExpect(status().isConflict());
    }


    @Test
    void testCannotRegisterWithExistingEmail_thenReturnsHttpCONFLICT() throws Exception{
        Users tmpUsr = makeTestUser();
        tmpUsr.setEmail("tobbe@tobbe.se");

        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tmpUsr)))
                .andExpect(status().isConflict());
    }





}