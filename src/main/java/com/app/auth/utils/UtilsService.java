package com.app.auth.utils;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

//@Service
public class UtilsService {


    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public  static Date dateTimeAddMinutes(int minutes){
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(minutes, ChronoUnit.MINUTES));
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

}
