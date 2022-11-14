
// lam the sole author of the work in this repository.
import structure5.*;
import java.util.Scanner;

public class Sorting {

    /**
     * Function to read in text from the phone-book.
     * @param vector the vector where all the received text is saved
     * @pre vector is valid vector of students
     * @post vector is populated with student objects from read in phone-book
     */
    public static void readInText(MyVector<Student> vector) {
        Assert.pre(!vector.equals(null), "Vector of students is null");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.nextLine();
            // check if first string is empty or contains dashes if so continue
            if (next.indexOf("------") != -1 || next.isEmpty()) {
                continue;
            }
            // if next is valid student name save it to name and save remaining info
            String name = next;
            String address = scanner.nextLine();
            long campusPhone = scanner.nextLong();
            int suBoxNum = scanner.nextInt();
            long homePhone = scanner.nextLong();
            // create a student object and add it to vector.
            Student st = new Student(name, address, campusPhone, suBoxNum, homePhone);
            vector.add(st);
        }
    }

    /**
     * Helper function that takes name and return number of vowels.
     * @param name String to calculate number of vowels from
     * @return number of vowels in name
     * @pre name is not null
     * @post returns the number of vowels in name
     */
    protected static int countVowel(String name) {
        Assert.pre(!name.equals(null), "Name is null");
        int numVowels = 0; // num vowels counter
        // loop through name and count all vowels
        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);
            // count vowels regardless of case.
            char lower = Character.toLowerCase(character);
            if (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u') {
                numVowels++;
            }
        }
        return numVowels;
    }

    /**
     * Function to add an address to a vector of associations of student addresses.
     * @param <E>     generic can any object to vector
     * @param student vector to add association of address to
     * @param address address to add to vector
     * @pre address is not null
     * @post adds address to student vector, if already there increments count
     */
    protected static <E> void add(MyVector<Association<E, Integer>> student, E address) {
        Assert.pre(!address.equals(null), "Address is null");
        // initialise a new association with address, with initial count of 1
        Association<E, Integer> association = new Association<>(address, 1);
        // check if the address is in list if not add it.
        int index = student.indexOf(association);
        if (index != -1) {
            // address is in list.
            association = student.get(index);
            // update the value in the association
            association.setValue(association.getValue() + 1);
        } else {
            // if not found add it to the list.
            student.add(association);
        }

    }

    /**
     * Function to find students at selected address.
     * @param students vector of students
     * @param address  address to lookup
     * @return number of students at address.
     * @pre address is not null, students is a vector of students
     * @post returns number of students at address.
     */
    protected static String studentAtAddress(MyVector<Student> students, String address) {
        Assert.pre(!address.equals(null), "Address is null");
        // var to store the name of students at address
        String output = "";
        int count = 1; // used for formatting and counting num of students at address
        for (int i = 0; i < students.size(); i++) {
            // take care of case and whitespace
            String house = students.get(i).getAddress().toLowerCase().trim();
            if (address.equals(house)) {
                output += String.valueOf(count) + ". " + students.get(i).getName() + "\n";
                count++;
            }
        }
        return output;
    }

    /**
     * Function for computing question one.
     * @param students The students to sort.
     * @pre students is not null.
     * @post prints the first student if students is unsorted.
     */
    public static void question1(MyVector<Student> students) {
        Assert.pre(!students.equals(null), "Students is null");
        System.out.println("Question 1: ");
        // sort students alphabetically
        students.sort((Student student1, Student student2) -> {
          return student1.getName().compareTo(student2.getName());
        });
        // print the first student.
        System.out.println(students.get(0).getName() + ", appears first.");
    }

    /**
     * Function for computing question two.
     * @param students The students to sort.
     * @pre students is not null, is a vector of Students
     * @post prints students with largest and smallest SU box numbers.
     */
    public static void question2(MyVector<Student> students) {
        Assert.pre(!students.equals(null), "Students is null");
        System.out.println("\nQuestion 2: ");
        // sort using SU box numbers, for comparators use diff of 2 SU boxes
        students.sort((Student student1, Student student2) -> {
            return student1.getSuBoxNum() - student2.getSuBoxNum();
        });
        // print out the first and last elements of sorted students
        System.out.println("Smallest Su Box [" + students.get(0).getSuBoxNum()
                + "]: " + students.get(0).getName());
        System.out.println("Biggest Su Box [" + students.get(students.size() - 1).getSuBoxNum()
                + "] : " + students.get(students.size() - 1).getName());
    }

    /**
     * Function for computing question three.
     * @param students The students to sort.
     * @pre students is not null, is a vector of Students
     * @post prints students with more vowels in their names.
     */
    public static void question3(MyVector<Student> students) {
        Assert.pre(!students.equals(null), "Students is null");
        System.out.println("\nQuestion 3: ");
        // sort using num of vowels, comparator use diff of num of vowels
        students.sort((Student student1, Student student2) -> {
            return countVowel(student1.getName()) - countVowel(student2.getName());
        });
        System.out.println("More vowels: " + students.get(students.size() - 1).getName());
    }

    /**
     * Function for computing question four.
     * @param students The students to sort.
     * @pre students is not null, is a vector of Students
     * @post prints most common address and students living there.
     */
    public static void question4(MyVector<Student> students) {
        Assert.pre(!students.equals(null), "Students is null");
        System.out.println("\nQuestion 4: ");
        // create a vector of association to keep address and count
        MyVector<Association<String, Integer>> addresses = new MyVector<>();
        // add addresses and count to address above
        for (int i = 0; i < students.size(); i++) {
            String address = students.get(i).getAddress().toLowerCase().trim();
            // avoid adding unknown to address
            if (address.equals("unknown")) {
                continue;
            }
            add(addresses, address);
        }
        // sort addresses using diff of address count
        addresses.sort((Association<String, Integer> address1, Association<String, Integer> address2) -> {
            return address1.getValue() - address2.getValue();
        });
        // print most common address
        String address = addresses.get(addresses.size() - 1).getKey();
        System.out.println("Most shared address: " + address);

        // names of students occupying that space
        System.out.println("Students at address: \n" + studentAtAddress(students, address));
    }

    /**
     * Function for computing question five.
     * @param students The students to sort.
     * @pre students is not null, is a vector of Students
     * @post prints 10 most common area codes in descending order.
     */
    public static void question5(MyVector<Student> students) {
        Assert.pre(!students.equals(null), "Students is null");
        System.out.println("\nQuestion 5: ");
        // create a vector of area code and count
        MyVector<Association<Long, Integer>> homePhones = new MyVector<>();
        // populate the vector with counts
        for (int i = 0; i < students.size(); i++) {
            // divide by 10000000 to get the first three nums in phone number
            long homeAreaCodes = students.get(i).getHomePhone() / 10000000;
            // avoid invalid area codes, area codes should be 3 digit long
            if (homeAreaCodes < 100) {
                continue;
            }
            add(homePhones, homeAreaCodes);
        }
        // sort using counts of area codes
        homePhones.sort((Association<Long, Integer> phone1, Association<Long, Integer> phone2) -> {
            return phone1.getValue() - phone2.getValue();
        });
        // print area code and count, toPrint for formatting
        String toPrint = "Area code and Number of students \n";
        int count = 0;
        // should print 10 and avoid indexing out of bounds
        while (homePhones.size() >= 0 && count < 10) {
            toPrint += String.valueOf(count + 1) + ". " + homePhones.get(homePhones.size() - (count + 1)).getKey()
                    + ": " + homePhones.get(homePhones.size() - (count + 1)).getValue()
                    + " students\n";
            count++;
        }
        System.out.println(toPrint);
    }

    /**
     * Function that calls all the functions created.
     * @param args commandline arguments passed during execution of code.
     */
    public static void main(String[] args) {
        // var to store students from the phone-book
        MyVector<Student> students = new MyVector<>();
        // read in text from the phone-book
        readInText(students);
        // Question 1
        question1(students);
        // Question 2
        question2(students);
        // Question 3
        question3(students);
        // Question 4
        question4(students);
        // Question 5
        question5(students);
    }

}
