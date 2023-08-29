package com.splicky.shop.service;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningTimeRange(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
}
