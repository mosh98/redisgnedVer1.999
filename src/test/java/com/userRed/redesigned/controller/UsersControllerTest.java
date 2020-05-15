//package com.userRed.redesigned.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.userRed.redesigned.model.User;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UsersControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void contextLoads() {
//
//    }
//
//    public User makeTestUser(){
//        User us = new User();
//        us.setUsername("smth smth, can be blank aswel");
//        us.setEmail("hotHot@hotmail.com");
//        us.setPassword("q923uy");
//        us.setDate_of_birth("2020-03-03");
//        us.setGender_type("FEMALE");
//        us.setCreatedAt();
//        return us;
//    }
//
//    public User makeTestUserBoilerplate(
//            String username,
//            String password,
//            String email,
//            String dateOfBirth,
//            String genderType){
//
//        User ussr = new User();
//        ussr.setUsername(username);
//        ussr.setEmail(email);
//        ussr.setPassword(password);
//        ussr.setDate_of_birth(dateOfBirth);
//        ussr.setGender_type(genderType);
//
//        return ussr;
//    }
//
//  /*  @Test
//    void testLoginAndDelete_thenReturns200OK() throws Exception {
//
//        String tmpUsername;
//        //make users
//       Users tmpUsr =  makeTestUserBoilerplate("testusername","1234MMM","smth@tobbe.se","2020-03-03","FEMALE");
//       tmpUsername = tmpUsr.getUsername();
//
//
//        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(tmpUsr)))
//                .andExpect(status().isNotAcceptable());
//
//        //login with users
//
//        // delete users
//
//        //check
//
//    }*/
//
//    @Test
//    void testBiggerThan30CharactersUsername() throws Exception {
//        //register using 30 characters
//        User tempUsr = makeTestUser();
//        tempUsr.setUsername("hfbakjDBADHJabdabdHJBadshbahdhdbHBWDHJbdjbasjdbasjdBjahdbdbJhasdbalsjhdbajshdBahjdbjaDBJHabdskhb");
//        //it's 96 characters
//
//
//        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(tempUsr)))
//                .andExpect(status().isNotAcceptable());
//    }
//
//
//    @Test
//    void testEmptyEmail_thenReturnsUNAUTHORIZED() throws Exception {
//
//        User us = new User();
//
//        us.setUsername("smth smth, can be blank aswel");
//        us.setEmail("               ");
//        us.setPassword("q923uy");
//        us.setDate_of_birth("2020-03-03");
//        us.setGender_type("FEMALE");
//
//        mockMvc.perform(post("http://localhost:8080/user/registerWithMail", 42L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(us)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test()
//    void testCannotRegisterWithExisitingUsername_thenReturnsHttpCONFLICT() throws Exception{
//
//        User tmpUsr = makeTestUser();
//        tmpUsr.setUsername("goku9000");
//        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(tmpUsr)))
//                .andExpect(status().isConflict());
//    }
//
//
//    @Test
//    void testCannotRegisterWithExistingEmail_thenReturnsHttpCONFLICT() throws Exception{
//        User tmpUsr = makeTestUser();
//        tmpUsr.setEmail("tobbe@tobbe.se");
//
//        mockMvc.perform(post("http://localhost:8080/user/register", 42L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(tmpUsr)))
//                .andExpect(status().isConflict());
//    }
//
//
//
//
//
//}