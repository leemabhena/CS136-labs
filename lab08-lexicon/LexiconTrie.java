// lan the sole author of work in this repository.

import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

public class LexiconTrie implements Lexicon {

    /* the root node of the lexicon tree */
    protected LexiconNode root;

    /* the size of the lexicon tree */
    protected int size;

    /**
     * Initializes the fields of the class; sets root to empty space and sets the
     * size to 0.
     */
    public LexiconTrie() {
        root = new LexiconNode(' ', false);
        size = 0;
    }

    /**
     * Adds word to the lexicon trie.
     * @param word the word to add to the trie.
     * @return returns true is word is added successfully else false.
     * @post word is added to the trie
     */
    public boolean addWord(String word) {
        // if word is already there, do nothing and return false
        if (containsWord(word)) {
            return false;
        } else {
            // add the word to the trie
            addWordHelper(word, root);
            size += 1; // increment the size as words are added.
            return true;
        }
    }

    /**
     * Adds words from file.
     * @param filename the name of the file to add words from.
     * @return returns the number of words added from file
     * @post adds words to trie from file and returns the num words added.
     */
    public int addWordsFromFile(String filename) {
        File file = new File(filename);
        // check if file exists, then read from it
        if (file.exists()) {
            try { // avoid any errors that may arise from reading the file
                Scanner scanner = new Scanner(file);
                int count = 0; // count the number of words in file
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    addWord(word); // add word to the trie
                    count++;
                }
                scanner.close(); // close the scanner after use.
                return count;
            } catch (Exception e) {
                System.out.println("There was an error reading from your file try again:)");
                return 0;
            }
        } else { // if file does not exist, alert the user and num words is 0
            System.out.println("File does not exist");
            return 0;
        }
    }

    /**
     * Function to remove words from the trie.
     * @param word, the word to be removed from the trie.
     * @return returns true iff the word is removed otherwise false.
     * @post the word is removed from the trie.
     */
    public boolean removeWord(String word) {
        // look for word in node, note node is the node of last char in word
        LexiconNode node = find(word, root);
        if (node != null && node.isWord()) { // if word is there
            node.isWord(false); // set the last node of the word's isWord to false.
            cleanNodes(word); // remove unnecessary nodes that are no longer valid words.
            size -= 1; // decrement the size of the node after removing
            return true;
        } else {
            return false; // word is not there in the trie
        }
    }

    /**
     * Function that gives the number of words in the trie.
     * @return returns number of words in the trie.
     * @post returns the number of words in the trie.
     */
    public int numWords() {
        return size;
    }

    /**
     * Function to check if the word is in trie.
     * @param word, the word to check if it exists in the trie.
     * @return returns true iff word is in trie otherwise false.
     * @post returns true iff word is in trie otherwise false.
     */
    public boolean containsWord(String word) {
        // look for word in node, note result is the node of last char in word
        LexiconNode result = find(word, root);
        return result != null && result.isWord();
    }

    /**
     * Function to check if the prefix is in trie.
     * @param prefix, the prefix to check for existence in the trie.
     * @return returns true iff prefix is in trie otherwise false.
     * @post returns true iff prefix is in trie otherwise false.
     */
    public boolean containsPrefix(String prefix) {
        // look for word in node, note result is the node of last char in word
        LexiconNode result = find(prefix, root);
        // if result is not null then prefix exist
        boolean outcome = result == null ? false : true;
        return outcome;
    }

    /**
     * Creates an iterator for trie.
     * @return returns the iterator for the trie.
     * @post returns the iterator for the trie.
     */
    public Iterator<String> iterator() {
        // create a vector of all the words
        Vector<String> words = allWords("", root, new Vector<>());
        // return an iterator of all words.
        return words.iterator();
    }

    // Optional (extra credit) implementation

    /**
     * This method returns a pointer to a set of strings,
     * where each entry is a suggested correction for the target.
     * @param target      the target string to generate words for.
     * @param maxDistance is the max number of differences allowed between
     *                    target and suggested string.
     * @return returns a set of all suggestions.
     * @post returns a set of all suggestions.
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
        SetVector<String> set = new SetVector<>();
        // generate all suggestions starting from the root node.
        suggestions(target, maxDistance, set, root, "", ' ');
        return set;
    }

    // Optional (extra credit) implementation
    /**
     * Function that matches all given regular expressions.
     * @param pattern The regEx expression to match
     * @return it return a set of all words matching pattern
     * @post returns all words matching pattern.
     */
    public Set<String> matchRegex(String pattern) {
        // find all the matches
        String regEx = "^"; // create a regular expression
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*' || pattern.charAt(i) == '?') {
                regEx += "[a-z]" + pattern.charAt(i); // should match all characters a-z 0 or more
            } else {
                regEx += pattern.charAt(i); // if character just search for it
            }
        }
        regEx += "$"; // end of the regular expression
        // generate the set of words
        SetVector<String> set = allMatches(regEx, "", root, new SetVector<>());
        return set;
    }

    // All the helper methods are coded below !!!!!!

    /**
     * Function that gets the last node of the word being looked for starting from
     * the root node.
     * @param word     the word being searched for in the trie.
     * @param currNode the trie to search the word from.
     * @return returns the last node of the word if it exists otherwise null.
     * @post returns the last node of the word.
     */
    private LexiconNode find(String word, LexiconNode currNode) {
        if (!currNode.getChildren().isEmpty()) { // as long as there are still children left
            char firstChar = Character.toLowerCase(word.charAt(0)); // take care of case
            LexiconNode child = currNode.getChild(firstChar);
            if (word.length() == 1 && child != null) { // return the last node
                return child;
            } else if (child == null) { // return null if not found
                return child;
            } else {
                return find(word.substring(1), child); // reduce word, and recurse on the child node.
            }
        } else {
            return null; // if there are no children word does not exist since start ar root
        }
    }

    /**
     * Function that adds word to a trie.
     * @param word The word to add to trie
     * @param node the node where the word is to be added, at the root.
     * @post adds word to trie.
     */
    private void addWordHelper(String word, LexiconNode node) {
        if (word.length() <= 0) { // if the word is empty dont add anything
            return;
        }
        char firstChar = Character.toLowerCase(word.charAt(0)); // case
        LexiconNode child = node.getChild(firstChar);
        // check if node in children
        if (child == null && word.length() > 1) { // not there
            // create a new lexicon node and add it there
            node.addChild(new LexiconNode(firstChar, false));
        } else if (child == null && word.length() == 1) { // last char make it a word
            node.addChild(new LexiconNode(firstChar, true));
        } else if (child != null && word.length() == 1) {
            child.isWord(true); // if there just set the last char isWord to true
        }
        // what if child is there, or now that is there
        child = node.getChild(firstChar);
        addWordHelper(word.substring(1), child);
    }

    /**
     * Function that removes leaf nodes that are not words, helper method for
     * remove.
     * @param word the word to be removed.
     * @post all redundant nodes are removed from the trie.
     */
    private void cleanNodes(String word) {
        if (word.length() == 1) { // check if the first char of word has isWord set to true
            LexiconNode firstChar = find(String.valueOf(word.charAt(0)), root); // locate first word node
            if (firstChar != null && firstChar.getChildren().isEmpty() && !firstChar.isWord()) {
                root.removeChild(word.charAt(0)); // if the first char is not valid word remove it
            }
            return;
        }
        LexiconNode child = find(word, root); // locate last node of word
        LexiconNode parent = find(word.substring(0, word.length() - 1), root); // parent node
        if (child.getChildren().isEmpty() && !child.isWord()) { // if not valid word remove it
            parent.removeChild(child.getLetter());
            cleanNodes(word.substring(0, word.length() - 1));
        }
    }

    /**
     * Function that returns all the words in the trie.
     * @param word  the word we are generating trie, starts off as empty string.
     * @param node  the trie where we are generating words from.
     * @param words the vector of all the words in the trie, starts empty.
     * @return returns a vector of all the words in the trie.
     * @post return all the words in the trie.
     */
    private Vector<String> allWords(String word, LexiconNode node, Vector<String> words) {
        if (node.isWord()) { // add the word to the trie.
            words.add(word.trim() + String.valueOf(node.getLetter())); // remove whitespace from word.
        }
        word += String.valueOf(node.getLetter()); // add the current letter to the word
        for (LexiconNode child : node.getChildren()) {
            allWords(word, child, words); // generate words for all children
        }
        return words;
    }

    /**
     * Function that returns all the suggested words
     * @param word          word to generate suggestions for
     * @param distance      the max variation allowed between 2 words.
     * @param set           the set of words meeting suggestions criteria
     * @param node          the node to check words from
     * @param generatedWord all words checked
     * @param ch            the characters of the nodes
     * @return returns a set of words meeting the suggestion criterion.
     * @post returns a set of words meeting the suggestion criterion.
     */
    private void suggestions(String word, int distance,
            SetVector<String> set, LexiconNode node, String generatedWord, char ch) {
        if (word.length() == 0 && distance >= 0) { // word is within max distance range
            generatedWord += ch; // create words as l recurse
            LexiconNode checkWord = find(generatedWord.trim(), root); // look for last node of the words
            if (checkWord != null && checkWord.isWord()) { // if not null, and is word it meets
                set.add(generatedWord.trim()); // criteria add it to set
            }
            return; // stop recursing
        }
        if (word.length() == 0) { // we have recursed though the whole word
            return;
        }
        if (word.length() > 0 && node.getChildren().isEmpty()) { // if len of word > generated word, return
            return;
        }
        generatedWord += String.valueOf(node.getLetter()); // create a new word each time
        char currChar = word.charAt(0); // compare the char in word and in lexicon
        for (LexiconNode child : node.getChildren()) {
            char wordLetter = child.getLetter();
            if (currChar != wordLetter) { // words vary reduce distance
                suggestions(word.substring(1), distance - 1, set, child, generatedWord, child.getLetter());
            } else { // continue with the same distance
                suggestions(word.substring(1), distance, set, child, generatedWord, child.getLetter());
            }
        }
    }

    /**
     * Function that creates a set of all words matching a regular expression.
     * @param regEx The regular expression.
     * @param word  all the words in the trie generated within each recursive call
     * @param node  the node to look for patterns from, usually root
     * @param words set of all the words matching the given pattern.
     * @return returns set of all word matching given pattern.
     * @post returns set of all word matching given pattern.
     */
    private SetVector<String> allMatches(String regEx, String word, LexiconNode node, SetVector<String> words) {
        if (node.isWord()) { // add the word to the set.
            Pattern p = Pattern.compile(regEx); // compile the regular expression
            Matcher m = p.matcher(word.trim() + String.valueOf(node.getLetter())); // avoid missing last char
            if (m.matches()) { // check if the generated word matches the regular expression
                words.add(word + String.valueOf(node.getLetter())); // remove whitespace from word.
            }
        }
        word += String.valueOf(node.getLetter()); // add the current letter to the word
        for (LexiconNode child : node.getChildren()) {
            allMatches(regEx, word, child, words); // generate words for all children and check for matches
        }
        return words;
    }
}
