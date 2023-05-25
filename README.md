# topWords

2 ways to run the app:
1. cd to topWords/target and run: java -jar topWords-1.0-SNAPSHOT.jar
2. via Intellij run 'mvn install' and run the main method in StartApplication class.

I ended up writing it in Java mainly for convenience and speed.
TBH, I initially thought of making this a spring boot app but didnt want to add redundant dependency overhead.

2 things I wanted to mention that I wasn't sure of:
1. In the bank of words, if the word from the article is "ZZ" (which also exists in the bankOfWords) - I understood that the first rules "win" and the word ZZ is not valid.
2. Also, about casing, I chose to not present "the" and "The" as 2 different top words and used lowercase since I figured it's not really interesting. Hope thats ok.

**General structure:**

1. StartApplication - app's entry point
2. model package - pojos that represent items:
- Word
- Essay
- BankOfWords (initiated during initiazation)
3. service package - core logic:
- EssayFetcherService
- WordCounterService
