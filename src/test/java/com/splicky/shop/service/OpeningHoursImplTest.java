package com.splicky.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpeningHoursImplTest {

    @Mock
    private OpeningDay openingDays;

    @InjectMocks
    private OpeningHoursImpl openingHours;


    @Test
    public void it_should_return_false_when_shop_is_not_open() {
        //given
        LocalDateTime dateTime = LocalDateTime.parse("2023-08-29T12:00:00");
        when(openingDays.openingTimeRanges()).thenReturn(getOpeningTimeRanges());

        //when
        boolean isOpen = openingHours.isOpenOn(dateTime);

        //then
        assertEquals(false, isOpen);
    }

    @Test
    public void it_should_return_true_when_shop_is_open() {
        //given
        LocalDateTime dateTime = LocalDateTime.parse("2023-08-30T12:00:00");
        when(openingDays.openingTimeRanges()).thenReturn(getOpeningTimeRanges());

        //when
        boolean isOpen = openingHours.isOpenOn(dateTime);

        //then
        assertEquals(true, isOpen);
    }

    @Test
    public void it_should_return_next_opening_time() {
        //given
        LocalDateTime inputDate = LocalDateTime.parse("2023-08-29T12:00:00");
        LocalDateTime expectedNextOpeningDate = LocalDateTime.parse("2023-08-30T08:00:00");
        when(openingDays.openingTimeRanges()).thenReturn(getOpeningTimeRanges());
        //when
        LocalDateTime nextOpeningDate = openingHours.nextOpeningDate(inputDate);
        //then
        assertEquals(expectedNextOpeningDate, nextOpeningDate);
    }

    @Test
    public void it_should_return_next_opening_time_when_current_day_is_after_last_opening_day() {
        //given
        LocalDateTime inputDate = LocalDateTime.parse("2023-09-01T12:00:00");
        LocalDateTime expectedNextOpeningDate = LocalDateTime.parse("2023-09-04T08:00:00");
        when(openingDays.openingTimeRanges()).thenReturn(getOpeningTimeRanges());
        //when
        LocalDateTime nextOpeningDate = openingHours.nextOpeningDate(inputDate);
        //then
        assertEquals(expectedNextOpeningDate, nextOpeningDate);
    }

    private List<OpeningTimeRange> getOpeningTimeRanges() {
        OpeningTimeRange openingTimeRange1 = new OpeningTimeRange(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
        OpeningTimeRange openingTimeRange2 = new OpeningTimeRange(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
        OpeningTimeRange openingTimeRange3 = new OpeningTimeRange(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));

        return List.of(openingTimeRange1, openingTimeRange2, openingTimeRange3);
    }

}