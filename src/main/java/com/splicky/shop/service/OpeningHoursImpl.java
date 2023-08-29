package com.splicky.shop.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class OpeningHoursImpl implements OpeningHours {

    private final OpeningDay openingDays;

    private static final int DAY_NUMBER_IN_WEEK = 7;

    public OpeningHoursImpl(OpeningDay openingDays) {
        this.openingDays = openingDays;
    }

    @Override
    public boolean isOpenOn(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        LocalTime time = date.toLocalTime();

        for (OpeningTimeRange timeRange : openingDays.openingTimeRanges()) {
            if (isWithinTimeRange(dayOfWeek, time, timeRange)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LocalDateTime nextOpeningDate(LocalDateTime date) {
        LocalDateTime nextOpening = date;

        DayOfWeek currentDayOfWeek = nextOpening.getDayOfWeek();
        LocalTime currentTime = nextOpening.toLocalTime();

        for (OpeningTimeRange timeRange : openingDays.openingTimeRanges()) {
            if (isBeforeOpening(currentDayOfWeek, currentTime, timeRange)) {
                return nextOpening.with(timeRange.dayOfWeek()).with(timeRange.openingTime());
            }
        }

        DayOfWeek firstDayOfWeek = openingDays.openingTimeRanges().get(0).dayOfWeek();
        OpeningTimeRange firstTimeRange = openingDays.openingTimeRanges().get(0);

        int daysUntilNextOpening = calculateDaysUntilNextOpening(currentDayOfWeek, firstDayOfWeek);
        nextOpening = nextOpening.plusDays(daysUntilNextOpening);
        nextOpening = nextOpening.with(firstDayOfWeek).with(firstTimeRange.openingTime());

        return nextOpening;

    }

    private boolean isWithinTimeRange(DayOfWeek dayOfWeek, LocalTime time, OpeningTimeRange timeRange) {
        return dayOfWeek == timeRange.dayOfWeek() &&
                time.isAfter(timeRange.openingTime()) &&
                time.isBefore(timeRange.closingTime());
    }

    private boolean isBeforeOpening(DayOfWeek currentDayOfWeek, LocalTime currentTime, OpeningTimeRange timeRange) {
        return currentDayOfWeek.getValue() < timeRange.dayOfWeek().getValue() ||
                (currentDayOfWeek.getValue() == timeRange.dayOfWeek().getValue() &&
                        currentTime.isBefore(timeRange.openingTime()));
    }

    private int calculateDaysUntilNextOpening(DayOfWeek currentDayOfWeek, DayOfWeek firstDayOfWeek) {
        int daysUntilNextOpening = (DAY_NUMBER_IN_WEEK + firstDayOfWeek.getValue() - currentDayOfWeek.getValue()) % DAY_NUMBER_IN_WEEK;
        if (daysUntilNextOpening == 0) {
            daysUntilNextOpening = DAY_NUMBER_IN_WEEK;
        }
        return daysUntilNextOpening;
    }
}
