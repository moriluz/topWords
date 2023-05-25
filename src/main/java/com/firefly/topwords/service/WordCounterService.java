package com.firefly.topwords.service;

import com.firefly.topwords.model.BankOfWords;
import com.firefly.topwords.model.Essay;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WordCounterService {

    private final BankOfWords bankOfWords;
    private final Map<String, Integer> frequencyMap;

    public WordCounterService() {
        this.bankOfWords = new BankOfWords();
        this.frequencyMap = new ConcurrentHashMap<>();
    }

    public void aggregateTopWords(Essay essay) {
        String[] words = essay.getContent().split("\\s+");

        for (String word : words) {
            if (bankOfWords.isValidWord(word) && bankOfWords.existsInBank(word)) {
                String lowercaseWord = word.toLowerCase();
                frequencyMap.put(lowercaseWord, frequencyMap.getOrDefault(lowercaseWord, 0) + 1);
            }
        }
    }

    public Set<String> getTopWords() {
        return frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}