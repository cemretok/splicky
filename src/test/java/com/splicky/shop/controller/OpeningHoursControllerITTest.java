package com.splicky.shop.controller;

import com.splicky.shop.service.OpeningHours;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OpeningHoursController.class)
public class OpeningHoursControllerITTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpeningHours openingHours;

    @Test
    public void it_should_test_get_next_opening_date() throws Exception {
        LocalDateTime inputDate = LocalDateTime.parse("2023-09-02T12:22:11.824");
        LocalDateTime expectedNextOpeningDate = LocalDateTime.parse("2023-08-30T08:00");

        when(openingHours.nextOpeningDate(any(LocalDateTime.class))).thenReturn(expectedNextOpeningDate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/nextOpeningDate")
                        .param("date", inputDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        content = content.replaceAll("^\"|\"$", ""); // Remove leading and trailing double quotes
        LocalDateTime actualNextOpeningDate = LocalDateTime.parse(content, DateTimeFormatter.ISO_DATE_TIME);

        assertEquals(expectedNextOpeningDate, actualNextOpeningDate);
    }

    @Test
    public void it_should_test_is_open_on() throws Exception {
        LocalDateTime inputDate = LocalDateTime.parse("2023-08-29T12:22:11.824");
        boolean isOpen = true;

        when(openingHours.isOpenOn(any(LocalDateTime.class))).thenReturn(isOpen);

        mockMvc.perform(MockMvcRequestBuilders.get("/isOpenOn")
                        .param("date", inputDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(isOpen)));
    }
}
