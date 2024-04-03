package com.company.bookShop.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Configuration
public class StartInitialization implements CommandLineRunner {
    @Override
    public void run(String... args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        log.info("--------- Start University-service :>> {} <<------------->>", formattedDate);
    }
}
