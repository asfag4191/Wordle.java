package no.uib.inf102.wordle.model.word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import no.uib.inf102.wordle.model.Dictionary;

/**
 * This class describes a structure of two lists for a game of Wordle: The list
 * of words that can be used as guesses and the list of words that can be
 * possible answers.
 */
public class WordleWordList {

    /**
     * All words in the game. These words can be used as guesses.
     */
    private Dictionary allWords;

    /**
     * A subset of <code>allWords</code>. <br>
     * </br>
     * These words can be the answer to a wordle game.
     */
    private List<String> possibleAnswers;

    /**
     * Create a WordleWordList that uses the full words and limited answers of
     * the GetWords class.
     */
    public WordleWordList(Dictionary dictionary) {
        this.allWords = dictionary;
        this.possibleAnswers = new ArrayList<>(dictionary.getAnswerWordsList());
    }

    /**
     * Get the list of all guessing words.
     *
     * @return all words
     */
    public Dictionary getAllWords() {
        return allWords;
    }

    /**
     * Returns the list of possible answers.
     *
     * @return
     */
    public List<String> possibleAnswers() {
        return Collections.unmodifiableList(possibleAnswers);
    }

    /**
     * Eliminates words from the possible answers list using the given
     * <code>feedback</code>
     *
     * @param feedback the feedback to eliminate words with.
     */
    public void eliminateWords(WordleWord feedback) { //O(m*k)
        List<String> newPossibleAnswer = new ArrayList<>(); //O(1)

        for (String word : possibleAnswers) { //O(m)
            if (WordleWord.isPossibleWord(word, feedback)) { //O(k)
                newPossibleAnswer.add(word); //O(1)
            }
            possibleAnswers = newPossibleAnswer; //O(1)
        }
    }

    /**
     * Returns the amount of possible answers in this WordleWordList
     *
     * @return size of
     */
    public int size() {
        return possibleAnswers.size();
    }

    /**
     * Removes the given <code>answer</code> from the list of possible answers.
     *
     * @param answer
     */
    public void remove(String answer) {
        possibleAnswers.remove(answer);
    }

    /**
     * Returns the word length in the list of valid guesses.
     *
     * @return
     */
    public int wordLength() {
        return allWords.WORD_LENGTH;
    }

    /**
	 * Returns the best word to guess based on the frequency of characters in the
	 * possible answers. The best word is the one with the highest frequency of
	 * characters. 
	 * Count the frequeny of each character at every position across all pssoible answers,
	 * selecting the word with the highest overall frequency score.
	 *
	 * @return the best word to guess.
     */
    public String bestWord() { //O(m*k)
        List<HashMap<Character, Integer>> bestFrequency = bestFrequency(possibleAnswers); //O(m*k)
        String bestWord = "";
        int maxCount = -1;

        for (String word : possibleAnswers) { //O(m)
            int tempCount = 0; //O(1)

            for (int i = 0; i < wordLength(); i++) { //O(k)
                char c = word.charAt(i); //O(1)
                if (bestFrequency.get(i).containsKey(c)) { //O(1)
                    tempCount += bestFrequency.get(i).get(c); //O(1)
                } else {
                    tempCount += 0; 
                }

            }

            if (tempCount > maxCount) { //O(1)
                maxCount = tempCount;
                bestWord = word;
            }
        }

        return bestWord; //O(1)
    }

    /**
     * Count the frequency of each character, at every position in the given
     * list of possible answers. Each position in the word is represented by a
     * HashMap, which maps each character to the number of times it occurs at
     * that position across all words.
     *
     * @param possibleAnswers a list of possible answers.
     * @return a list of HashMaps, where each HashMap maps a character to the
     * number of times it occurs in a given position.
     */
    public List<HashMap<Character, Integer>> bestFrequency(List<String> possibleAnswers) { //O(m*k)
        List<HashMap<Character, Integer>> bestFrequency = new ArrayList<>(); //O(1)

        for (int i = 0; i < wordLength(); i++) { //O(k)
            bestFrequency.add(new HashMap<>()); //O(1)
        }

        for (String word : possibleAnswers) { //O(m)
            for (int i = 0; i < wordLength(); i++) { //O(k)
                char c = word.charAt(i); //O(1)
                if (bestFrequency.get(i).containsKey(c)) { //O(1)
                    bestFrequency.get(i).put(c, bestFrequency.get(i).get(c) + 1); //O(1)
                } else { //O(1)
                    bestFrequency.get(i).put(c, 1); //O(1)
                }
            }
        }

        return bestFrequency; //O(1)
    }

}


