package com.splicky.shop.service;

import java.time.LocalDateTime;
public interface OpeningHours {

     boolean isOpenOn(LocalDateTime date);
     LocalDateTime nextOpeningDate(LocalDateTime date);
}
