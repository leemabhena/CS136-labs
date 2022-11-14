
// lam the sole author of the work in this repository.
import java.util.Scanner;
import java.util.Random;

public class WordGen {

  /**
   * Function that processes user input and adds it to table
   * @param text         the String to process
   * @param k            the size of sequences
   * @param table        where we add sequences of size k
   * @param oneCharTable where to add sequences of size 1
   */
  public static void processCharacters(String text, int k, Table table, Table oneCharTable) {
    // process the user input and store it in a table
    String character = ""; // stores the next char to add to the table
    int start = 0; // starting index of k, default 0 start index of strings
    int end = k - 1; // end index k more than the start index
    int pointer = k; // moving pointer which points at the next char to add to table
    String sequence = ""; // seq of size k added as key to the table

    for (int i = 0; i < text.length(); i++) {
      // build a table of only one character
      String currentCharacter = String.valueOf(text.charAt(i));
      String nextCh = String.valueOf(text.charAt(i + 1));
      oneCharTable.add(currentCharacter, nextCh); // add the next char after current char to table
      // build a sequence of size k, starting at start value and ending at end value
      sequence = text.substring(start, end + 1);
      // take the character next to sequence
      character = String.valueOf(text.charAt(pointer));
      table.add(sequence, character); // add elements to the table
      start++; // move the start index forward by 1
      end++; // move the end index forward by 1
      pointer++; // move the pointer index forward by 1
      // avoid indexing out of range and wrap around to beginning of text
      if (end == text.length() - 1) {
        break;
      }
    }

  }

  /**
   * Function to generate random text
   * @param table        where we add sequences of size k
   * @param oneCharTable where we add sequences of size 1
   * @param k            the size of sequences
   * @return randomly generated string
   */
  public static String generateText(Table table, Table oneCharTable, int k) {
    // Generate text using the values in the table
    // choose a random starting sequence
    Random random = new Random();
    String startingSeq = table.getKey(random.nextInt(table.length()));
    String generatedText = startingSeq; // String of text we about to generate
    int initial = 0; // starting index of k, default 0 start index of strings
    int finalIndex = k; // end index k more than the start index
    for (int i = 0; i < 550; i++) { // create string of size about 500.
      String seq = generatedText.substring(initial, finalIndex); // select last k chars
      // check if the selected sequence is in the table
      if (table.choose(seq) != null) {
        // if not null choose next char and append it to generated text
        generatedText += table.choose(seq);
      } else if (oneCharTable.choose(String.valueOf(seq.charAt(seq.length() - 1))) != null) {
        // if not check the last char in sequence in oneCharTable and choose next char
        generatedText += oneCharTable.choose(String.valueOf(seq.charAt(seq.length() - 1)));
      } else {
        generatedText += " ";
      }
      initial++; // move initial index 1 unit forward
      finalIndex++; // move final index 1 unit forward
    }

    return generatedText;

  }

  /**
   * Function to read in text
   * @return string of inputted text
   */

  public static String readInText() {
    // grab user input
    Scanner scanner = new Scanner(System.in);
    StringBuffer textBuffer = new StringBuffer();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      textBuffer.append(line);
      textBuffer.append("\n");
    }

    return textBuffer.toString();

  }

  public static void main(String[] args) {
    if (args.length == 0) {
      // no args, so print usage line and do nothing else
      System.out.println("Usage: java WordGen <k>");
    } else {
      // convert first argument to k
      int k = Integer.parseInt(args[0]);
      // read in text
      String text = readInText();
      // new Table with keys of size k
      Table table = new Table();
      // one character table with k = 1
      Table oneCharTable = new Table();
      // process characters
      processCharacters(text, k, table, oneCharTable);
      // generate text
      String generatedText = generateText(table, oneCharTable, k);
      // nicely format the generated string to start with capital letter
      int firstPeriod = generatedText.indexOf(".");
      int lastPeriod = generatedText.lastIndexOf(".");
      String formattedText = generatedText.substring(firstPeriod + 1, lastPeriod + 1).trim();

      System.out.println("The generated text is.....\n");

      if (formattedText.length() > 1) {
        System.out.println(formattedText);
      } else {
        System.out.println(generatedText);
      }

    }
  }

}
