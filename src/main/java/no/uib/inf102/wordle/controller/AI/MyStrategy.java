package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.AnswerType;
import no.uib.inf102.wordle.model.word.WordleCharacter;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;
    private List<String> possibleAnswers;
    private int guessCount = 0; // Teller for antall gjetninger


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
        if (guessCount >= 1) {
            sortedWords = scoreWords(guesses.possibleAnswers()); // Hent ord sortert med laveste score
        } else {
            sortedWords = scoreWordsReversed(guesses.possibleAnswers()); // Hent ord sortert med høyest score
        }
    
        guessCount++; // Øk gjetningstelleren for hver gang et ord velges
        return sortedWords.get(0); // Velger det første ordet i den sorterte listen
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
//lager en liste som sorterere ordene etter score, høyeste score først. 
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

    private List<String> scoreWordsReversed(List<String> possibleAnswer){
        List<HashMap<Character, Integer>> frequencyList = guesses.bestFrequency(possibleAnswers);
        HashMap<String, Integer> wordScores = new HashMap<>();
        
        //Beregner scoren for hvert ord 
        for (String word : possibleAnswers) {
            int score = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                score += frequencyList.get(i).getOrDefault(c, 0);
            }
            wordScores.put(word, score);
        }
        //Sorterer ordene etter deres score i stigende rekkefølge
        List<String> sortedWords = new ArrayList<>(wordScores.keySet());
        Collections.sort(sortedWords, new Comparator<String>() {
            @Override
            public int compare(String w1, String w2) {
                int score1 = wordScores.get(w1);
                int score2 = wordScores.get(w2);

                //Sorter i stigende rekkefølge (laveste poengsum først)
                return score1 - score2;
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
