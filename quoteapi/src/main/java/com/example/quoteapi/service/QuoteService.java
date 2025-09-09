package com.example.quoteapi.service;

import com.example.quoteapi.model.Quote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {
    private final List<Quote> quotes = List.of(
    new Quote("Things You Love Makes You Happy So Love EveryThing. - Uma Maheshwara Chary J"),
    new Quote("Life is what happens when you're busy making other plans. - John Lennon"),
    new Quote("Present isn't in our hands So Live it. - Karnakar J"),
    new Quote("The best time to plant a tree was 20 years ago. The second best time is now. - Chinese Proverb"),
    new Quote("It does not matter how slowly you go as long as you do not stop. - Confucius"),
    new Quote("Consistency makes you confident. - Uma Maheshwara Chary "));

    private final Random random = new Random();

    public Quote getRandomQuote() {
        return quotes.get(random.nextInt(quotes.size()));
    }
}
