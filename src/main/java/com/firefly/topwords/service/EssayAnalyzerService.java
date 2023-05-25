package com.firefly.topwords.service;

import com.firefly.topwords.model.Essay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import static com.firefly.topwords.StartApplication.ARTICLE_TEXT;
import static com.firefly.topwords.StartApplication.URLS_PATH;

@Slf4j
public class EssayAnalyzerService {

    private final WordCounterService wordCounterService;

    public EssayAnalyzerService() {
        this.wordCounterService = new WordCounterService();
    }

    public void analyzeEssays() {
        log.info("Begin processing URLs");
        InputStream resource = getClass().getClassLoader().getResourceAsStream(URLS_PATH);
        if (resource != null) {
            // Lazily stream URLS to not load all to memory at once
            BufferedReader stream = new BufferedReader(new InputStreamReader(resource));
            // Not using parallel stream because the rate limiter kicks in.
            stream.lines().forEach(this::countTopWordsInEssay);
        }

        printTopWords();
    }

    private void countTopWordsInEssay(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String content = doc.select(ARTICLE_TEXT).text();
            Essay essay = new Essay(content);
            wordCounterService.aggregateTopWords(essay);
        } catch (IOException e) {
           handleExceptions(url, e);
        }
    }

    private void handleExceptions(String url, IOException e) {
        if (e.getMessage().contains("Status=404")) {
            log.error("Website {} not found", url);
        } else if (e.getMessage().contains("Status=999")) {
            log.error("Too many requests to engadget.");
            // Didnt get this error without parallel stream but just in case. If I had time I would probably create
            // a retry mechanism or if the pushback is long then store top words in a stateful cache and continue from the last url.
            printTopWords();
            System.exit(1);
        } else {
            log.error("Failed to fetch website {} due to exception {}", url, e.getMessage());
        }
    }

    private void printTopWords() {
        log.info("Print top 10 words");
        Set<String> wordCounts = wordCounterService.getTopWords();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info(gson.toJson(wordCounts));
    }
}
