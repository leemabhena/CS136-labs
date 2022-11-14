import structure5.*;

public class Test {

    public static void main(String[] args) {
        Vector<String> vec = new Vector<>();
        vec.add("A");
        vec.add("B");
        vec.add("C");
        vec.add("D");
        vec.add("E");
        vec.add("F");
        vec.add("G");
        vec.add("H");
        SubsetIterator<String> subIt = new SubsetIterator<>(vec);
        while (subIt.hasNext()) {
            System.out.println(subIt.next());
        }
    }

}
