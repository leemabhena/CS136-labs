import java.util.Iterator;

public class LexiconIterator implements Iterator {

    LexiconNode current;
    LexiconTrie trie;

    public LexiconIterator(LexiconTrie tree) {
        trie = tree;
    }

    public boolean hasNext() {
        return true;
    }

    public LexiconNode next() {
        return new LexiconNode(' ', false);
    }
}
