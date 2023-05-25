package com.firefly.topwords;

import com.firefly.topwords.service.EssayAnalyzerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartApplication {

    public static final String BANK_OF_WORDS_PATH = "words.txt";
    public static final String URLS_PATH = "urls.txt";
    public static final String ARTICLE_TEXT = ".article-text";

    /**
     * fetch list of essays and count the top 10 words from all the essays combined.
     * A valid word will:
     * 1. Contain at least 3 characters.
     * 2. Contain only alphabetic characters.
     * 3. Be part of our bank of words (not all the words in the bank are valid according to the
     * previous rules)
     * The output should be pretty json printed to the stdout.
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        EssayAnalyzerService essayAnalyzerService = new EssayAnalyzerService();
        essayAnalyzerService.analyzeEssays();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        log.info("Time took to run {} ms", timeElapsed);
    }
}