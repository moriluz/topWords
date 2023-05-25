package com.firefly.topwords.model;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.firefly.topwords.StartApplication.BANK_OF_WORDS_PATH;

@Slf4j
public class BankOfWords {

    private final Set<String> wordBank = new HashSet<>();

    public BankOfWords() {
        log.info("Initiate bank of words");
        initBankOfWords();
    }

    private void initBankOfWords() {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(BANK_OF_WORDS_PATH);
        if (resource != null) {
            BufferedReader stream = new BufferedReader(new InputStreamReader(resource));
            // Not adding words that dont match first rules since we filter them anyway
            Set<String> words = stream.lines().filter(this::isValidWord).collect(Collectors.toSet());
            wordBank.addAll(words);
        }
    }

    public boolean existsInBank(String word) {
        return wordBank.contains(word);
    }

    public boolean isValidWord(String word) {
        return word.length() >= 3 && word.matches("[a-zA-Z]+");
    }
}