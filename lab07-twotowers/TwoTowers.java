
// lam the sole author of code in this repository
import structure5.*;

public class TwoTowers {

    /**
     * Function to compute sum of a subset
     * @param heights Subset to compute sum of.
     * @return sum of the given subset.
     * @post returns sum of the subset.
     */
    protected static double sum(Vector<Double> heights) {
        double total = (double) 0;
        for (double height : heights) {
            total += height;
        }
        return total;
    }

    /**
     * Function that returns the two closest subsets to the configuration(h/2)
     * @param target  The sum to reach.
     * @param heights The vector of all heights.
     * @return The two closest vectors to the configuration.
     * @pre heights is not null.
     * @post returns the two closest vectors to configuration.
     */
    protected static Vector<Vector<Double>> configuration(double target, Vector<Double> heights) {
        Assert.pre(heights != null, "Heights is not null");
        SubsetIterator<Double> iterator = new SubsetIterator<>(heights);
        Vector<Double> closest = new Vector<>(); // closest subset to target
        Vector<Double> secondClosest = new Vector<>(); // second closest subset to target
        for (Vector<Double> subset : iterator) {
            double total = sum(subset); // sum of current subset we iterating over
            double closestTotal = sum(closest); // sum of current closest subset
            double secondTotal = sum(secondClosest); // sum of current 2nd closest subset
            if (total > closestTotal && total < target) { // update closest to new closer subset
                closest = subset;
            } else if (total > secondTotal && total <= closestTotal) { // update 2nd closest subset
                secondClosest = subset;
            }
        }
        Vector<Vector<Double>> firstAndSecond = new Vector<>();
        firstAndSecond.add(closest); // vector with closest @ position 1 and 2nd @ position 2
        firstAndSecond.add(secondClosest);
        return firstAndSecond;
    }

    /**
     * Function to print area and subset.
     * @param message Message to print with the area and subset
     * @param heights The subset to print.
     * @post Prints a nicely formatted subset and its area
     */
    protected static void printArea(String message, Vector<Double> heights) {
        String toPrint = message + ": [ ";
        for (double height : heights) {
            double area = Math.round((height * height)); // print area instead of heights
            int intArea = (int) area;
            toPrint += String.valueOf(intArea) + ", ";
        }
        toPrint = toPrint.substring(0, toPrint.length() - 2); // formatting
        toPrint += " ] = " + sum(heights); // print the sum of area with subsets
        System.out.println(toPrint);
    }

    /**
     * Function that solves the two towers problem and prints the two towers to the
     * screen.
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) { // check for user appropriate use.
            System.out.println("Use: java TwoTowers n");
        } else {
            try { // check if user's input is an integer.
                int n = Integer.parseInt(args[0]); // change user input to int
                Vector<Double> heights = new Vector<>();
                // populate the vector with heights instead of area.
                for (int i = 1; i <= n; i++) {
                    double height = Math.sqrt(i);
                    heights.add(height);
                }
                // configuration to compare subset sums to
                double target = sum(heights) / 2;
                System.out.println("Half height (h/2) is: " + String.valueOf(target));
                // compute the subset sums and compare to target
                Vector<Vector<Double>> firstAndSecond = configuration(target, heights);
                // Print the closest and second closest subsets
                printArea("The best subset is", firstAndSecond.get(0));
                printArea("The second best subset is", firstAndSecond.get(1));
            } catch (Exception e) {
                System.out.println("You must pass in a number as your first commandline argument");
            }
        }
    }

}
