package dev.devs;

import lombok.SneakyThrows;

import java.time.Duration;
import java.time.Instant;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        Instant start = Instant.now();
        Thread.sleep(3000);
        Instant end = Instant.now();
        System.out.println("Duration: " + Duration.between(start, end).getNano()); // Gives Weird output
        System.out.println("Duration: " + Duration.between(start, end).getSeconds());
        System.out.println("Duration: " + Duration.between(start, end).toMillis());
    }
}