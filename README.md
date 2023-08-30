# topWords

2 ways to run the app:
1. cd to topWords/target and run: java -jar topWords-1.0-SNAPSHOT.jar
2. via Intellij run 'mvn install' and run the main method in StartApplication class.

**General structure:**

1. StartApplication - app's entry point
2. model package - pojos that represent items:
- Word
- Essay
- BankOfWords (initiated during initiazation)
3. service package - core logic:
- EssayFetcherService
- WordCounterService
