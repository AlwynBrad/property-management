package com.alwyn.propertymanagement.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.alwyn.propertymanagement.dto.PropertyDTO;
import com.alwyn.propertymanagement.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PropertyController.class})
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class PropertyControllerTest {
    @Autowired
    private PropertyController propertyController;

    @MockBean
    private PropertyService propertyService;

    /**
     * Method under test: {@link PropertyController#saveProperty(PropertyDTO)}
     */
    @Test
    void testSaveProperty() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("Test Description");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        when(propertyService.saveProperty(Mockito.<PropertyDTO>any())).thenReturn(propertyDTO);

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setAddress("42 Main St");
        propertyDTO2.setDescription("Test Description");
        propertyDTO2.setId(1L);
        propertyDTO2.setPrice(10.0d);
        propertyDTO2.setTitle("Dr");
        propertyDTO2.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(propertyDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"Test Description\",\"price\":10.0,\"address\":\"42"
                                        + " Main St\",\"userId\":1}"));
    }

    /**
     * Method under test: {@link PropertyController#getAllProperties()}
     */
    @Test
    void testGetAllProperties() throws Exception {
        when(propertyService.getAllProperties()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/properties");
        MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link PropertyController#getAllPropertiesForUser(Long)}
     */
    @Test
    void testGetAllPropertiesForUser() throws Exception {
        when(propertyService.getAllPropertiesForUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/properties/users/{userId}",
                1L);
        MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link PropertyController#updateProperty(PropertyDTO, Long)}
     */
    @Test
    void testUpdateProperty() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("Test Description");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        when(propertyService.updateProperty(Mockito.<PropertyDTO>any(), Mockito.<Long>any())).thenReturn(propertyDTO);

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setAddress("42 Main St");
        propertyDTO2.setDescription("Test Description");
        propertyDTO2.setId(1L);
        propertyDTO2.setPrice(10.0d);
        propertyDTO2.setTitle("Dr");
        propertyDTO2.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(propertyDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/properties/{propertyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"Test Description\",\"price\":10.0,\"address\":\"42"
                                        + " Main St\",\"userId\":1}"));
    }

    /**
     * Method under test: {@link PropertyController#updatePropertyDescription(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyDescription() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("Test Description");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        when(propertyService.updatePropertyDescription(Mockito.<PropertyDTO>any(), Mockito.<Long>any()))
                .thenReturn(propertyDTO);

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setAddress("42 Main St");
        propertyDTO2.setDescription("Test Description");
        propertyDTO2.setId(1L);
        propertyDTO2.setPrice(10.0d);
        propertyDTO2.setTitle("Dr");
        propertyDTO2.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(propertyDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/v1/properties/update-description/{propertyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"Test Description\",\"price\":10.0,\"address\":\"42"
                                        + " Main St\",\"userId\":1}"));
    }

    /**
     * Method under test: {@link PropertyController#updatePropertyPrice(PropertyDTO, Long)}
     */
    @Test
    void testUpdatePropertyPrice() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("42 Main St");
        propertyDTO.setDescription("Test Description");
        propertyDTO.setId(1L);
        propertyDTO.setPrice(10.0d);
        propertyDTO.setTitle("Dr");
        propertyDTO.setUserId(1L);
        when(propertyService.updatePropertyPrice(Mockito.<PropertyDTO>any(), Mockito.<Long>any()))
                .thenReturn(propertyDTO);

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setAddress("42 Main St");
        propertyDTO2.setDescription("Test Description");
        propertyDTO2.setId(1L);
        propertyDTO2.setPrice(10.0d);
        propertyDTO2.setTitle("Dr");
        propertyDTO2.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(propertyDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/v1/properties/update-price/{propertyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"description\":\"Test Description\",\"price\":10.0,\"address\":\"42"
                                        + " Main St\",\"userId\":1}"));
    }

    /**
     * Method under test: {@link PropertyController#deleteProperty(Long)}
     */
    @Test
    void testDeleteProperty() throws Exception {
        doNothing().when(propertyService).deleteProperty(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/properties/{propertyId}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link PropertyController#deleteProperty(Long)}
     */
    @Test
    void testDeleteProperty2() throws Exception {
        doNothing().when(propertyService).deleteProperty(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/properties/{propertyId}",
                1L);
        requestBuilder.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(propertyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

