import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Java Port of RAKE (Rapid Automatic Keyword Extraction algorithm)
 * implementation in python at (https://github.com/aneesha/RAKE).
 *
 * Author: AskDrCatcher
 * License: MIT
 */
public class Extractor {

    public static Map<String, Double> getKeywords(String stopPath, String text) throws IOException {
        final Extractor rakeInstance = new Extractor();

        final List<String> sentenceList = rakeInstance.splitSentences(text);
        final Pattern stopWordPattern = rakeInstance.buildStopWordRegex(stopPath);
        final List<String> phraseList = rakeInstance.generateCandidateKeywords(sentenceList, stopWordPattern);
        final Map<String, Double> wordScore = rakeInstance.calculateWordScores(phraseList);
        return rakeInstance.generateCandidateKeywordScores(phraseList, wordScore);

    }

    private static boolean isNumber(final String str) {
        return str.matches("[0-9.]");
    }

    private static List<String> loadStopWords(String filePath) throws FileNotFoundException, IOException {

        if (filePath == null || filePath.trim().length() == 0) {
            filePath = "FoxStoplist.txt";
        }

        final List<String> stopWords = new ArrayList<String>();
        final BufferedReader br = new BufferedReader(new InputStreamReader(Extractor.class.getResourceAsStream(filePath)));

        try {

            String line = br.readLine();

            while (line != null) {

                if (!line.startsWith("#")) { //add the line which is not a comment
                    stopWords.add(line);
                }

                line = br.readLine();
            }

        } finally {
            br.close();
        }

        return stopWords;
    }

    private static List<String> separateWords(final String text, final int minimumWordReturnSize) {

        final List<String> separateWords = new ArrayList<String>();
        final String[] words = text.split("[^a-zA-Z0-9_\\+\\-/]");

        if (words != null && words.length > 0) {

            for (final String word : words) {

                String wordLowerCase = word.trim().toLowerCase();

                if (wordLowerCase.length() > 0 && wordLowerCase.length() > minimumWordReturnSize &&
                        !isNumber(wordLowerCase)) {

                    separateWords.add(wordLowerCase);
                }
            }
        }

        return separateWords;
    }


    private static List<String> splitSentences(final String text) {

        final String[] sentences = text.split("[.!?,;:\\t\\\\-\\\\\"\\\\(\\\\)\\\\\\'\\u2019\\u2013]");

        if (sentences != null) {
            return new ArrayList<String>(Arrays.asList(sentences));
        } else {
            return new ArrayList<String>();
        }
    }

    private static Pattern buildStopWordRegex(final String stopWordFilePath) throws IOException {

        final List<String> stopWords = loadStopWords(stopWordFilePath);
        final StringBuilder stopWordPatternBuilder = new StringBuilder();
        int count = 0;
        for(final String stopWord: stopWords) {
            if (count++ != 0) {
                stopWordPatternBuilder.append("|");
            }
            stopWordPatternBuilder.append("\\b").append(stopWord).append("\\b");
        }

        return Pattern.compile(stopWordPatternBuilder.toString(), Pattern.CASE_INSENSITIVE);
    }

    private static List<String> generateCandidateKeywords(List<String> sentenceList, Pattern stopWordPattern) {
        final List<String> phraseList = new ArrayList<String>();

        for (final String sentence : sentenceList) {

            final String sentenceWithoutStopWord = stopWordPattern.matcher(sentence).replaceAll("|");
            final String[] phrases = sentenceWithoutStopWord.split("\\|");

            if (null != phrases && phrases.length > 0) {
                for(final String phrase : phrases) {
                    if (phrase.trim().toLowerCase().length() > 0) {
                        phraseList.add(phrase.trim().toLowerCase());
                    }
                }
            }
        }

        return phraseList;
    }

    private static Map<String,Double> calculateWordScores(List<String> phraseList) {

        final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
        final Map<String, Integer> wordDegree = new HashMap<String, Integer>();
        final Map<String, Double> wordScore = new HashMap<String, Double>();

        for (final String phrase : phraseList) {

            final List<String> wordList = separateWords(phrase, 0);
            final int wordListLength = wordList.size();
            final int wordListDegree = wordListLength - 1;

            for (final String word : wordList) {

                if (!wordFrequency.containsKey(word)) {
                    wordFrequency.put(word, 0);
                }

                if (!wordDegree.containsKey(word)) {
                    wordDegree.put(word, 0);
                }

                wordFrequency.put(word, wordFrequency.get(word) + 1);
                wordDegree.put(word, wordDegree.get(word) + wordListDegree);
            }
        }

        final Iterator<String> wordIterator = wordFrequency.keySet().iterator();

        while (wordIterator.hasNext()) {
            final String word = wordIterator.next();

            wordDegree.put(word, wordDegree.get(word) + wordFrequency.get(word));

            if (!wordScore.containsKey(word)) {
                wordScore.put(word, 0.0);
            }

            wordScore.put(word, wordDegree.get(word) / (wordFrequency.get(word) * 1.0));
        }

        return wordScore;
    }

    public static Map<String, Double> generateCandidateKeywordScores(List<String> phraseList,
                                                              Map<String, Double> wordScore) {

        final Map<String, Double> keyWordCandidates = new HashMap<String, Double>();

        for (String phrase : phraseList) {

            final List<String> wordList = separateWords(phrase, 0);
            double candidateScore = 0;

            for (final String word : wordList) {
                candidateScore += wordScore.get(word);
            }

            keyWordCandidates.put(phrase, candidateScore);
        }

        return keyWordCandidates;
    }

    public static void main(String[] args) throws IOException {

        final String text =
                "Compatibility of systems of linear constraints over the set of natural numbers. Criteria of compatibility of a system of linear Diophantine equations, strict inequations, and nonstrict inequations are considered. Upper bounds for components of a minimal set of solutions and algorithms of construction of minimal generating sets of solutions for all types of systems are given. These criteria and the corresponding algorithms for constructing a minimal supporting set of solutions can be used in solving all the considered types of systems and systems of mixed types.";

        final Extractor rakeInstance = new Extractor();

        final List<String> sentenceList = rakeInstance.splitSentences(text);
        final String stopPath = "SmartStoplist.txt";
        final Pattern stopWordPattern = rakeInstance.buildStopWordRegex(stopPath);
        final List<String> phraseList = rakeInstance.generateCandidateKeywords(sentenceList, stopWordPattern);
        final Map<String, Double> wordScore = rakeInstance.calculateWordScores(phraseList);
        final Map<String, Double> keywordCandidates = rakeInstance.generateCandidateKeywordScores(phraseList, wordScore);

        System.out.println("keyWordCandidates = "+ keywordCandidates);

        System.out.println("sortedKeyWordCandidates = " +
                rakeInstance.sortKeyWordCandidates(keywordCandidates));
    }

    private static LinkedHashMap<String, Double> sortKeyWordCandidates
            (Map<String,Double> keywordCandidates) {

        final LinkedHashMap<String, Double> sortedKeyWordCandidates = new LinkedHashMap<String, Double>();
        int totaKeyWordCandidates = keywordCandidates.size();
        final List<Map.Entry<String, Double>> keyWordCandidatesAsList =
                new LinkedList<Map.Entry<String, Double>>(keywordCandidates.entrySet());

        Collections.sort(keyWordCandidatesAsList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Double>)o2).getValue()
                        .compareTo(((Map.Entry<String, Double>)o1).getValue());
            }
        });

        totaKeyWordCandidates = totaKeyWordCandidates / 3;
        for(final Map.Entry<String, Double> entry : keyWordCandidatesAsList) {
            sortedKeyWordCandidates.put(entry.getKey(), entry.getValue());
            if (--totaKeyWordCandidates == 0) {
                break;
            }
        }

        return sortedKeyWordCandidates;
    }
}