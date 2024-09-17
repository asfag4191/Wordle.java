package no.uib.inf102.wordle.controller.AI;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;

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
            String firstUniqueWord = guesses.findFirstOptimized();
            if (firstUniqueWord != null) {
                return firstUniqueWord;
            }
        }

        // Hvis det ikke er det første gjettet, eller ingen unike ord ble funnet, bruk frekvensmetoden
        if (feedback != null) {
            guesses.eliminateWords(feedback);
        }

        return guesses.bestWord();
    }

    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
    }
}
