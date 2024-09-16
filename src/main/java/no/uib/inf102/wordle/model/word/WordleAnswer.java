package no.uib.inf102.wordle.model.word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import no.uib.inf102.wordle.model.Dictionary;


/**
 * This class represents an answer to a Wordle puzzle.
 * 
 * The answer must be one of the words in the LEGAL_WORDLE_LIST.
 */
public class WordleAnswer {

    private final String WORD;

    private Dictionary dictionary;

    private static Random random = new Random();

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     */
    public WordleAnswer(Dictionary dictionary) {
        this(random, dictionary);
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     * using a specified random object.
     * This gives us the opportunity to set a seed so that tests are repeatable.
     */
    public WordleAnswer(Random random, Dictionary dictionary) {
        this(getRandomWordleAnswer(random, dictionary), dictionary);
    }

    /**
     * Creates a WordleAnswer object with a given word.
     * 
     * @param answer
     */
    public WordleAnswer(String answer, Dictionary dictionary) {
        this.WORD = answer.toLowerCase();
        this.dictionary = dictionary;
    }

    /**
     * Gets a random wordle answer
     * 
     * @param random
     * @return
     */
    private static String getRandomWordleAnswer(Random random, Dictionary dictionary) {
        List<String> possibleAnswerWords = dictionary.getAnswerWordsList();
        int randomIndex = random.nextInt(possibleAnswerWords.size());
        String newWord = possibleAnswerWords.get(randomIndex);
        return newWord;
    }

    /**
     * Guess the Wordle answer. Checks each character of the word guess and gives
     * feedback on which that is in correct position, wrong position and which is
     * not in the answer word.
     * This is done by updating the AnswerType of each WordleCharacter of the
     * WordleWord.
     * 
     * @param wordGuess
     * @return wordleWord with updated answertype for each character.
     */
    public WordleWord makeGuess(String wordGuess) {
        if (!dictionary.isLegalGuess(wordGuess))
            throw new IllegalArgumentException("The word '" + wordGuess + "' is not a legal guess");

        WordleWord guessFeedback = matchWord(wordGuess, WORD);
        return guessFeedback;
    }

    /**
     * Generates a WordleWord showing the match between <code>guess</code> and
     * <code>answer</code>
     * 
     * @param guess
     * @param answer
     * @return
     */
    public static WordleWord matchWord(String guess, String answer) {
        int wordLength = answer.length();
        if (guess.length() != wordLength)
            throw new IllegalArgumentException("Guess and answer must have same number of letters but guess = " + guess
                    + " and answer = " + answer);

        // TODO: Implement me :)

        char [] ans = answer.toCharArray();
        char [] guess_= guess.toCharArray();
        Map<Character, Integer> map = new HashMap<>();

        AnswerType[] feedback = new AnswerType[wordLength];

        //builds the map of how many times each character appears in the answer
        for (char c: ans){
            if (map.containsKey(c)){
                map.put(c, map.get(c)+1); //increment if already in map
            }else{
                map.put(c, 1); //add if not in map
        }
    }
        for (int i = 0; i < wordLength; i++) {
            feedback[i] = AnswerType.WRONG;
            if (ans[i] == guess_[i]) {
                feedback[i] = AnswerType.CORRECT;
                map.put(ans[i], map.get(ans[i]) - 1); //decrement the count of the character in the answer.
            }else{                                    //decrement to not count the same character again.
                feedback[i] = AnswerType.WRONG; //if the character is not in the answer
            }
        }
        //if the character is in the answer but not in the correct position
        for (int i = 0; i < wordLength; i++) { 
            if (feedback[i] == AnswerType.WRONG && map.containsKey(guess_[i]) && map.get(guess_[i]) > 0) { //if the character is in the answer but not in the correct position.
                feedback[i] = AnswerType.MISPLACED; 
                map.put(guess_[i], map.get(guess_[i]) - 1); 

            }
        }
        return new WordleWord(guess, feedback);
    }
}