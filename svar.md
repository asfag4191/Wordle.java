# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k)/O(1)
    * *I started by using toCharArray(), but this take O(k) time to return the result, but here we have a constant length
    of the word so it would take O(1) time. However, I changed to charAt() to return the character at the specified index 
    because this take O(1) time. 
    
    I start by initializing an empty hashmap wich will take constant time, O(1), and initializing the new array feedback, which also takes O(1) time. The for-loop iterates over every charachter in the answer string. The loop runs k-times, where k is the length of the word. Then we get the acharacter at index i and stores it in c, which will take O(1) time. Then the HashMap is updated, and if the character is in the map, the count is updated, if not it returns 0. This process will take O(1) time, because it's constant. The put is also constant O(1) time, updates to store the new count for the character. 

    Then we have a new for loop which will do the same as the above, the for loop takes O(k) time to go through the answer, and it has O(1) operations. If the answertype is correct, instead of increment the counting of c, it decrement when it finds a answer with feedback that is correct. This is to keep track of how many times a character from answer has been used, take O(1) time. 

    The lastly we have also a for loop that iterates over the anwser, takes O(k) time where its operations takes O(1) times. It runs k times, where k is the length of the word. The map and its operations also takes O(1) time. 

    After it returns a new WordleWord wich has the guess and its correpsonidn feedback O(1) time.

    Sum up:
    All loop lops over WordleWords.
    1. loop: O(k), runs k-times, O(1) operations. 
    2. loop: O(k), runs k-times, O(1) operations.
    3. loop: O(k), runs k-times, O(1) operations. 

Overall time complexity is O(3k). but in big-O notations we don't take in constant, so it's O(k). We can also disguss that because we have given a given string of 5 constants, it takes constant time where k always is 5, so it's O(1).
    *

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O(?)
    * *Insert description of why the method has the given runtime*

## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O(?)
    * *Insert description of why the method has the given runtime*



# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

*Write your answer here*
