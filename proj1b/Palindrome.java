/**
 * Created by Joseph on 2/4/2017.
 */
public class Palindrome {

    public static void main(String[] args) {

    }

    /**
     * Converts a string into a deque of characters
     */
    public static Deque<Character> wordToDeque(String word) {
        //Creates array deque of characters and casts it into a deque of characters
        Deque<Character> characterDeque = new LinkedListDeque<>();
        //Saves length of word into a variable
        int wordLength = word.length();
        //Iterates through each character in word and adds it to the back of the deque
        for (int i = 0; i < wordLength; i++) {
            characterDeque.addLast(word.charAt(i));
        }
        return characterDeque;
    }

    /**
     * Returns a boolean that says whether a given string is a palindrome or not; converts
     * the string into a deque of characters and calls its helper isPalindrome (overloaded) method
     * to check if it is the same forward and backward
     */
    public static boolean isPalindrome(String word) {
        Deque<Character> characterDeque = wordToDeque(word);
        return isPalindrome(characterDeque);
    }

    /**
     * Checks whether a deque of characters is symmetric; implemented using recursion
     */
    public static boolean isPalindrome(Deque<Character> word) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        if (word.removeFirst() == word.removeLast()) {
            return isPalindrome(word);
        }
        return false;
    }

    /**
     * Checks whether a word is the same forward and backward, with equality defined
     * by the character comparator cc; calls helper isPalindrome
     */
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    /**
     * Checks whether a deque of characters is symmetric; implemented using recursion
     * Equality defined by character comparator's equalChars method
     */
    public static boolean isPalindrome(Deque<Character> word, CharacterComparator cc) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        if (cc.equalChars(word.removeFirst(), word.removeLast())) {
            return isPalindrome(word, cc);
        }
        return false;
    }

}
