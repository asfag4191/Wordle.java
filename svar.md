# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k)
    * *I started by using toCharArray(), but this take O(k) time to return the result, but here we have a constant length
    of the word so it would take O(1) time. However, I changed to charAt() because this take O(1) time. 
    
    I start by initializing an empty hashmap wich will take constant time, O(1), and initializing the array take O(1) time. 
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
