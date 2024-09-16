package no.uib.inf102.wordle.model.word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * Create a WordleWordList that uses the full words and limited answers of the
	 * GetWords class.
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
	public void eliminateWords(WordleWord feedback) {
		List<String> newPossibleAnswer=new ArrayList<>(size());

		for (String word : possibleAnswers){ 
			if (WordleWord.isPossibleWord(word,feedback)){
				newPossibleAnswer.add(word);
			}
			possibleAnswers=newPossibleAnswer; //update the possible answers
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
 * Returns the best possible word, given the answer.
 */
public String bestWord() {
    List<HashMap<Character, Integer>> bestFrequency = bestFrequency(possibleAnswers);
    String bestWord = "";
    int maxCount = -1;

    // Find the word with the highest frequency of characters
    for (String word : possibleAnswers) {
        int tempCount = 0;

        for (int i = 0; i < wordLength(); i++) {
            char c = word.charAt(i);
            if (bestFrequency.get(i).containsKey(c)) {
				tempCount += bestFrequency.get(i).get(c);
			} else {
				tempCount += 0;
			}

        }

        // Update the best word if this word has a higher score
        if (tempCount > maxCount) {
            maxCount = tempCount;
            bestWord = word;
        }
    }

    return bestWord;
}

	 //count the number of possible words for each character
	 // holder oversikt over hvor mange ganger hver bokstav forekommer i hver posisjon
	 private List<HashMap<Character, Integer>> bestFrequency(List<String> possibleAnswers){
		List<HashMap<Character, Integer>> bestFrequency= new ArrayList<>();

		for (int i = 0; i < wordLength(); i++) {
			bestFrequency.add(new HashMap<>());
		}

		for(String word: possibleAnswers){
			for (int i = 0; i < wordLength(); i++) {
				char c = word.charAt(i);
				if (bestFrequency.get(i).containsKey(c)){
					bestFrequency.get(i).put(c, bestFrequency.get(i).get(c)+1);
				}else{
					bestFrequency.get(i).put(c, 1);
				}
			}
		}

		return bestFrequency;
	 }

	 //return true if it's all different characters in the word
	 public boolean allDifferent(String word){
		Set<Character> set = new HashSet<>();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i); //O(1)
			if (set.contains(c)) { //O(1)
				return false;
			} else {
				set.add(c); //O(1)
			}
		}

		return true;
	}
	


	}
	 

