package no.uib.inf102.wordle.controller.AI;

import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;
;


public class MyStrategy implements IStrategy {

    private WordleWordList guesses;
    
//få færre gjetninger. 
    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null) {
            guesses.eliminateWords(feedback);    
        } 
        return guesses.bestWord();
    }


    @Override
    public void reset() {
        // TODO: Implement me :)
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }

    
}
