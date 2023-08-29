package com.splicky.shop.config;

import com.splicky.shop.service.OpeningDay;
import com.splicky.shop.service.OpeningTimeRange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class OpeningDayConfig {
    @Bean
    OpeningDay openingDay() {
        return new OpeningDay(List.of(
                new OpeningTimeRange(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)),
                new OpeningTimeRange(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)),
                new OpeningTimeRange(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(16, 0))
        ));
    }
}
