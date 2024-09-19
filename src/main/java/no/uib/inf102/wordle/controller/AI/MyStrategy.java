package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;
    private WordleWord feedback;
    private List<String> possibleAnswers;

    public MyStrategy(Dictionary dictionary) {
        this.dictionary = dictionary;
        reset();
    }

//String firstUniqueWord = guesses.findFirstAllDifferent();
//få færre gjetninger. 
    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback == null) {
            // Velg det første ordet med alle forskjellige bokstaver
            String firstUniqueWord = BestStartGuess();
            if (firstUniqueWord != null) {
                return firstUniqueWord;
            }
        }
        // Hvis det ikke er det første gjettet, eller ingen unike ord ble funnet, bruk frekvensmetoden
        if (feedback != null) {
            guesses.eliminateWords(feedback);

        }

        List<String> sortedWords = scoreWords(guesses.possibleAnswers());
        return sortedWords.get(0); // Velger det ordet med høyest score
    }

    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
        possibleAnswers = guesses.possibleAnswers();

    }

    private String BestStartGuess() {
        List<String> uniqueWords = UniqueCharacter(possibleAnswers);
        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(uniqueWords);
        return findBestWord(uniqueWords, frequencyList);
    }

    // Helper method to find the best word based on the frequency list
    private String findBestWord(List<String> words, List<HashMap<Character, Integer>> frequencyList) {
        String bestWord = "";
        int maxScore = -1;

        for (String word : words) {
            int tempCount = 0;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                tempCount += frequencyList.get(i).getOrDefault(c, 0);
            }

            // Update the best word if this word has a higher score
            if (tempCount > maxScore) {
                maxScore = tempCount;
                bestWord = word;
            }
        }

        return bestWord;
    }

//lager en liste med ord som har forskjellige bokstaver og bruker denne 
    private List<String> UniqueCharacter(List<String> possibleAnswers) {
        List<String> uniqueWords = new ArrayList<>();
        for (String word : possibleAnswers) {
            if (guesses.allDifferent(word)) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    private List<String> scoreWords(List<String> possibleAnswers) {
        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(possibleAnswers);
        HashMap<String, Integer> wordScores = new HashMap<>();

        // Beregn score for hvert ord
        for (String word : possibleAnswers) {
            int score = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                score += frequencyList.get(i).getOrDefault(c, 0);
            }
            wordScores.put(word, score);
        }

        // Sorter ordene etter deres score i synkende rekkefølge
        List<String> sortedWords = new ArrayList<>(wordScores.keySet());
        Collections.sort(sortedWords, new Comparator<String>() {
           
            @Override
            public int compare(String w1, String w2) {
                // Få poengsummen for hvert ord
                int score1 = wordScores.get(w1);
                int score2 = wordScores.get(w2);

                // Sorter i synkende rekkefølge (høyeste poengsum først)
                return score2 - score1;
            }
        });

        return sortedWords;
    }

}









   /*  private String getFirstUniqueWord() {
        String bestWord=null;
        int highestScore=-1;

        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(possibleAnswers);
        for (String word : possibleAnswers) {
            if (guesses.allDifferent(word)) { // Bruker allDifferent fra guesses
                int score = calculateWordScore(word, frequencyList);
                if (score > highestScore) {
                    highestScore = score;
                    bestWord = word;
                }
            }
        }
        return bestWord;
    }

    private int calculateWordScore(String word, List<HashMap<Character, Integer>> frequencyList) {
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            score += frequencyList.get(i).get(c);
        }
        return score;

    }

} */
