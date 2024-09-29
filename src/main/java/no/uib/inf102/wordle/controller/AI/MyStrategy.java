package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;
    private List<String> possibleAnswers;
    private int guessCount = 0;

    public MyStrategy(Dictionary dictionary) {
        this.dictionary = dictionary;
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback == null) {
            String firstUniqueWord = BestStartGuess();
            if (firstUniqueWord != null) {
                guessCount++;
                return firstUniqueWord;
            }
        }
        if (feedback != null) {
            guesses.eliminateWords(feedback);

        }

        List<String> sortedWords;
        sortedWords = scoreWords(guesses.possibleAnswers());

        guessCount++;
        return sortedWords.get(0);
    }

    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
        possibleAnswers = guesses.possibleAnswers();

    }

    /**
     * Finds the best word to start with. The best word is the one with the
     * highest score based on the frequency of characters in the possible
     * answers, and all unique characters.
     *
     * @return the best word to start with.
     */
    private String BestStartGuess() {
        List<String> uniqueWords = UniqueCharacter(possibleAnswers);
        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(uniqueWords);
        return findBestWord(uniqueWords, frequencyList);
    }

    /**
     * Calculates a score for each word based on the frequency of characters in
     * the possible answers. The best word is the one with the highest score.
     * Method used for BestStartGuess.
     *
     * @param words the list of words to choose from.
     * @param frequencyList list of HashMaps where each HashMap contains the
     * frequency of characters at each position.
     */
    private String findBestWord(List<String> words, List<HashMap<Character, Integer>> frequencyList) {

        String bestWord = "";
        int maxScore = -1;

        for (String word : words) {
            int tempCount = 0;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                tempCount += frequencyList.get(i).getOrDefault(c, 0);
            }

            if (tempCount > maxScore) {
                maxScore = tempCount;
                bestWord = word;
            }
        }

        return bestWord;
    }

    /**
     * Creates a list with all the words that have unique characters.
     *
     * @param possibleAnswers the list of possible answers.
     * @return a list of words with unique characters.
     */
    private List<String> UniqueCharacter(List<String> possibleAnswers) {
        List<String> uniqueWords = new ArrayList<>();
        for (String word : possibleAnswers) {
            if (allDifferent(word)) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    /**
     * Sort the word list based on the frequency of characters in the possible
     * answers. The best word is the one with the highest score.
     *
     * @param possibleAnswers the list of possible answers.
     * @return a list of words sorted by their score. Highest score first.
     */
    private List<String> scoreWords(List<String> possibleAnswers) {
        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(possibleAnswers);
        HashMap<String, Integer> wordScores = new HashMap<>();

        for (String word : possibleAnswers) {
            int score = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                score += frequencyList.get(i).getOrDefault(c, 0);
            }
            wordScores.put(word, score);
        }

        List<String> sortedWords = new ArrayList<>(wordScores.keySet());
        Collections.sort(sortedWords, new Comparator<String>() {

            @Override
            public int compare(String w1, String w2) {
                int score1 = wordScores.get(w1);
                int score2 = wordScores.get(w2);

                return score2 - score1;
            }
        });

        return sortedWords;
    }

    /**
     * Checks if all characters in the given word are different.
     *
     * @param word to check.
     * @return true if all characters are different, false if not.
     */
    private boolean allDifferent(String word) {
        Set<Character> charSet = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!charSet.add(c)) {
                return false;
            }
        }

        return true;
    }
}
