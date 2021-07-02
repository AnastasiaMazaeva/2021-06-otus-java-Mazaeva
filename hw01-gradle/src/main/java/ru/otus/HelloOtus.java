package ru.otus;

import com.google.common.math.IntMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HelloOtus {

    private static final Logger log = LoggerFactory.getLogger(HelloOtus.class);

    public static void main(String[] args) {
        Random random = new Random();
        int yourLuckyRandom = IntMath.mod(random.nextInt(1444), random.nextInt(6174));
        log.info("You are lucky today, and your number is {}! Be aware of the birds.", yourLuckyRandom);
    }
}
