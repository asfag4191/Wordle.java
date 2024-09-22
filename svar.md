# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords

* I have given the runtime behind all of the codes as well as this explanation*

## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k)/O(1)
    * *I started by using toCharArray(), but this take O(k) time to return the result, but here we have a constant length
    of the word so it would take O(1) time. However, I changed to charAt() to return the character at the specified index 
    because this take O(1) time. 
    
    I start by initializing an empty hashmap wich will take constant time, O(1), and initializing the new array feedback, which also takes O(1) time. The for-loop iterates over every charachter in the answer string. The loop runs k-times, where k is the length of the word. Then we get the character at index i and stores it in c, which will take O(1) time. Then the HashMap is updated, and if the character is in the map, the count is updated, if not it returns 0. This process will take O(1) time, because it's constant. The put is also constant O(1) time, updates to store the new count for the character. 

    Then we have a new for loop which will do the same as the above, the for loop takes O(k) time to go through the answer, and it has O(1) operations. If the answertype is correct, instead of increment the counting of c, it decrement when it finds a answer with feedback that is correct. This is to keep track of how many times a character from answer has been used, take O(1) time. 

    The lastly we have also a for loop that iterates over the anwser, takes O(k) time where its operations takes O(1) times. It runs k times, where k is the length of the word. The map and its operations also takes O(1) time. 

    After it returns a new WordleWord wich has the guess and its correpsonidn feedback O(1) time.

    Sum up:
    All loop lops over WordleWords.
    1. loop: O(k), runs k-times, O(1) operations. 
    2. loop: O(k), runs k-times, O(1) operations.
    3. loop: O(k), runs k-times, O(1) operations. 

    Overall time complexity is O(3k), which simplifies to O(k). If we consider the word length 'k' to always be a constant (like 5 in Wordle), then the complexity could be considered O(1), as it runs in constant time.
    *

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O(m*k)
    * *Created a new ArrayList to minimize operations, O(1). Then I take and iterate over every word in possibleanswer, which has size m ,and time O(m). Inside the loop I check if the word has a valid feedback (matchwords from task 1), which takes O(k) time. This gives us O(m*k)
    
    Sum up:
    1. Iterating over possibleanswer: O(m), there are 'm' words.
    2. isPossible for every word: O(k), Given matchword and getWordString. Comapres each characters in the string. 
    
    Overall time complexity is O(m*k). We can argument that the runtime is only O(m), because k=5, constant. *

## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O(m*k)
    * *It start by calling on the elimnateWords, which has a run time O(m*k) (Given explanation earlier). This happens when there are words to eliminate based on the feedback. Then I am calling on bestWord wich will evaluate the best word based on frequenzy, O(m*k) runtime. 

    bestFrequenzy starts with creating a new ArrayList which will hold the HashMap, where each hashmap will store the character frequencies at specific positions in word. Creating this takes O(1) time. Iterating over wordleWords takes O(k) time, and initializing a new HashMap for each position in the word, O(1) time. 
    Then we are iterating through every possibleAnswer, O(m) time. In the inner loop it iterates over wordleWords, which take O(k) time. O(m)*O(k) creates O(m*k). All the other operations take O(1) time. 

    bestWord starts by its first line calling the bestfrequenzy method, which calculates the frequenzy across all words in possiblewords, takes O(m*k) time. Where m is the number of words in possibleanswer and k is the length of each word. 

    Then we have a outer for loop that iterates over possibleAnswers, O(m). Then the inner for loop iterates over wordleWords, O(k). This combined is O(m*k) time. All together this function takes:   O(m*k)+O(m*k)=O(m*k). The sum of the two main parts. 

    Sum up:
    1. bestFrequenzy: O(m*k) (helper method for the bestWord)
    2. bestWord: O(m*k)

    Frequenzystrategy
    1. Invoking eliminatewords which takes O(m*k) time
    2. Calling bestWord, evaluates the best word based on frequenzy,  O(m*k) time. 

    The overall time complexity is O(m*k)+O(m*k)=O(m*k)
    *



# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

*Write your answer here*
