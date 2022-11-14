import java.util.regex.Pattern;
import java.util.regex.Matcher;
import structure5.*;

public class Test {

    public static void main(String[] args) {
        LexiconTrie root = new LexiconTrie();
        root.addWordsFromFile("inputs//small2.txt");
        System.out.println(Pattern.matches("^a[a-z]*$", "a"));

    }
}

// LexiconNode node = new LexiconNode('a', true);
// Random rand = new Random();
// // char a = 'a';
// // for (int i = 0; i < 10; i++) {
// // int b = a + 1;
// // LexiconNode tmp = new LexiconNode((char) b, false);
// // node.addChild(tmp);
// // }
// // node.addChild(new LexiconNode('i', false));
// // node.getChild('i').addChild(new LexiconNode('r', true));
// // LexiconNode root = new LexiconNode(' ', false);
// // root.addChild(node);
// // LexiconNode found = root.find("bat", root);
// // // for (LexiconNode b : node.getChild('i').getChildren()) {
// // // System.out.println(b.getLetter());
// // // }
// // System.out.println(found.getLetter());
