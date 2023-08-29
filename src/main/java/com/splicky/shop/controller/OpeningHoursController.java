package com.splicky.shop.controller;

import com.splicky.shop.service.OpeningHours;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class OpeningHoursController {

    private final OpeningHours openingHours;

    public OpeningHoursController(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    @GetMapping("/nextOpeningDate")
    public ResponseEntity<LocalDateTime> getNextOpeningDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        LocalDateTime nextOpeningDate = openingHours.nextOpeningDate(date);
        return ResponseEntity.ok(nextOpeningDate);
    }

    @GetMapping("/isOpenOn")
    public ResponseEntity<Boolean> isOpenOn(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        boolean isOpen = openingHours.isOpenOn(date);
        return ResponseEntity.ok(isOpen);
    }
}
