package com.alwyn.propertymanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.alwyn.propertymanagement.dto.UserDTO;
import com.alwyn.propertymanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserDTO userDTO;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#register(UserDTO)}
     */
    @Test
    void testRegister() throws Exception {
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setCity("Oxford");
        userDTO2.setCountry("GB");
        userDTO2.setHouseNo("House No");
        userDTO2.setId(1L);
        userDTO2.setOwnerEmail("jane.doe@example.org");
        userDTO2.setOwnerName("Owner Name");
        userDTO2.setPassword("iloveyou");
        userDTO2.setPhone("6625550144");
        userDTO2.setPostalCode(1L);
        userDTO2.setState("MD");
        userDTO2.setStreet("Street");
        userDTO2.setToken("ABC123");
        when(userService.register(Mockito.<UserDTO>any())).thenReturn(userDTO2);

        UserDTO userDTO3 = new UserDTO();
        userDTO3.setCity("Oxford");
        userDTO3.setCountry("GB");
        userDTO3.setHouseNo("House No");
        userDTO3.setId(1L);
        userDTO3.setOwnerEmail("jane.doe@example.org");
        userDTO3.setOwnerName("Owner Name");
        userDTO3.setPassword("iloveyou");
        userDTO3.setPhone("6625550144");
        userDTO3.setPostalCode(1L);
        userDTO3.setState("MD");
        userDTO3.setStreet("Street");
        userDTO3.setToken("ABC123");
        String content = (new ObjectMapper()).writeValueAsString(userDTO3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"ownerName\":\"Owner Name\",\"ownerEmail\":\"jane.doe@example.org\",\"phone\":\"6625550144\",\"password\""
                                        + ":\"iloveyou\",\"token\":\"ABC123\",\"houseNo\":\"House No\",\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\","
                                        + "\"postalCode\":1,\"country\":\"GB\"}"));
    }

    /**
     * Method under test: {@link UserController#login(UserDTO)}
     */
    @Test
    void testLogin() throws Exception {
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setCity("Oxford");
        userDTO2.setCountry("GB");
        userDTO2.setHouseNo("House No");
        userDTO2.setId(1L);
        userDTO2.setOwnerEmail("jane.doe@example.org");
        userDTO2.setOwnerName("Owner Name");
        userDTO2.setPassword("iloveyou");
        userDTO2.setPhone("6625550144");
        userDTO2.setPostalCode(1L);
        userDTO2.setState("MD");
        userDTO2.setStreet("Street");
        userDTO2.setToken("ABC123");
        when(userService.login(Mockito.<String>any(), Mockito.<String>any())).thenReturn(userDTO2);

        UserDTO userDTO3 = new UserDTO();
        userDTO3.setCity("Oxford");
        userDTO3.setCountry("GB");
        userDTO3.setHouseNo("House No");
        userDTO3.setId(1L);
        userDTO3.setOwnerEmail("jane.doe@example.org");
        userDTO3.setOwnerName("Owner Name");
        userDTO3.setPassword("iloveyou");
        userDTO3.setPhone("6625550144");
        userDTO3.setPostalCode(1L);
        userDTO3.setState("MD");
        userDTO3.setStreet("Street");
        userDTO3.setToken("ABC123");
        String content = (new ObjectMapper()).writeValueAsString(userDTO3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"ownerName\":\"Owner Name\",\"ownerEmail\":\"jane.doe@example.org\",\"phone\":\"6625550144\",\"password\""
                                        + ":\"iloveyou\",\"token\":\"ABC123\",\"houseNo\":\"House No\",\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\","
                                        + "\"postalCode\":1,\"country\":\"GB\"}"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/getAll");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

