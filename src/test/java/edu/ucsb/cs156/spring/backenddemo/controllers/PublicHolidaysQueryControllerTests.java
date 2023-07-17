package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spring.backenddemo.services.PublicHolidayQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PublicHolidaysQueryController.class)
public class PublicHolidaysQueryControllerTests {
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PublicHolidayQueryService publicHolidayQueryService;

    @Test
    public void test_getPublicHolidays() throws Exception {
        String year = "2021";
        String countryCode = "US";
        String fakeJsonResult = "{ \"fake\" : \"result\" }";
        when(publicHolidayQueryService.getJSON(year, countryCode)).thenReturn(fakeJsonResult);
        MvcResult response = mockMvc.perform(get("/api/publicholidays/get").param("year", year)
                .param("countryCode", countryCode)).andExpect(status().isOk()).andReturn();
        String actualResult = response.getResponse().getContentAsString();
        assertEquals(fakeJsonResult, actualResult);
        verify(publicHolidayQueryService, times(1)).getJSON(year, countryCode);
    }
}
