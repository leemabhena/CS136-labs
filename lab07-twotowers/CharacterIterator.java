import structure5.*;

/**
 * An iterator that yields the consecutive characters of a String, in order
 */
public class CharacterIterator extends AbstractIterator<Character> {

	private int current;
	private String data;

	public CharacterIterator(String str) {
		data = str;
		// set current to start of String
		reset();
	}

	public Character next() {
		Character curr = get();
		// update current pointer
		current++;
		return curr;
	}

	public boolean hasNext() {
		// if curr < length of string there are more char
		return current < data.length();
	}

	public void reset() {
		// set the current pointer to start of String
		current = 0;

	}

	public Character get() {
		// grabs current character
		Character currChar = data.charAt(current);
		return currChar;
	}

	public static void main(String[] args) {
		CharacterIterator ci = new CharacterIterator("Hello world!");
		for (char c : ci) {
			System.out.println(c);
		}
	}
}
