
// lam the sole author of code in this repository.

// Word level is more efficient as it takes less time to run compared to character level analysis.

import java.util.Scanner;
import java.util.Random;

/**
 * WordLevel
 */
public class WordLevel {

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

    /**
     * Function to process user input
     * @param text         the string to process
     * @param k            the number of letters to use
     * @param table        the table to add words to
     * @param oneWordTable the table to add words to used as fallback
     */
    public static void processText(String text, int k, Table table, Table oneWordTable) {
        // split string ino individual words
        String[] textArr = text.split(" ");
        for (int i = 0; i < textArr.length; i++) {
            // avoid looping out of range
            if (i + k >= textArr.length) {
                break;
            }
            // sequence to add to the table
            String sequence = "";
            for (int j = 0; j < k; j++) {
                sequence += textArr[i + j] + " ";
            }
            // add sequence to the table
            table.add(sequence, textArr[i + k]);
            oneWordTable.add(textArr[i], textArr[i + 1]);
        }
    }

    /**
     * Generates text to be printed
     * @param table        the table to add words to
     * @param oneWordTable the table to add words to used as fallback
     * @param k            the number of letters to use
     * @param start        the int to use as starting index of generated string
     * @return generated text
     */
    public static String generateText(Table table, Table oneWordTable, int k, int start) {

        String generatedText = table.getKey(start); // text to generate
        // run loop about 500 times
        for (int i = 0; i < 300; i++) {
            // change seq into array of words
            String[] seqArr = generatedText.split(" ");
            String seq = ""; // holds last n k words
            // select last k words
            for (int j = k; j >= 0; j--) {
                // avoid indexing out of range
                if ((seqArr.length - j) >= seqArr.length) {
                    break;
                }
                seq += seqArr[(seqArr.length) - j] + " ";
            }
            // check if the selected sequence is in the table
            if (table.choose(seq) != null) {
                // if not null choose next char and append it to generated text
                generatedText += table.choose(seq) + " ";
            } else if (oneWordTable.choose(seq) != null) {
                // if not check the last char in sequence in oneCharTable and choose next char
                generatedText += oneWordTable.choose(String.valueOf(seq.charAt(seq.length() - 1))) + " ";
            } else {
                // fallback if last two do not work
                generatedText += " ";
            }

        }
        return generatedText;
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
            // create table
            Table table = new Table();
            Table oneWordTable = new Table();
            // process text
            processText(text, k, table, oneWordTable);
            // generate text
            Random random = new Random();
            int start = random.nextInt(table.length()); // random starting point
            String generatedText = generateText(table, oneWordTable, k, start);

            System.out.println(generatedText);
        }

    }
}
