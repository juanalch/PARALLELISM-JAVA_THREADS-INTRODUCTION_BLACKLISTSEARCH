package edu.eci.arsw.threads;

/**
* The {@code CountThread} class represents a thread whose lifecycle
* is responsible for printing a sequence of integers to the console.
* Each instance of this class receives two integer values, {@code A} and {@code B},
* which define the lower and upper bounds of the range to be printed.
* When the thread is started, it executes its {@code run()} method and
* prints all integers between {@code A} and {@code B}, inclusive.
*
* This class demonstrates one of the ways to create a thread in Java,
* by extending the {@link Thread} class and overriding the {@code run()} method.

* @author hcadavid
* @author Juana Lozano Chaves y Anderson Fabian García N 
*/

public class CountThread extends Thread {
    private int a;
    private int b;

    /**
    * Creates a new {@code CountThread} with the specified range of values.
    * If the first value is greater than the second, the values are swapped
    * to ensure a valid range.
    *
    * @param a lower bound of the range.
    * @param b upper bound of the range.
    */

    public CountThread(int a, int b) {
        if (a < b) {
            this.a = a;
            this.b = b;

        } else {
            this.a = b;
            this.b = a;
        }
    }
    
    /**
    * Executes the thread and prints all integers in the range
    * defined by {@code a} and {@code b}, inclusive.
    */

    @Override
    public void run() {
        for (int i = a; i <= b; i++) {
            System.out.println(i);
        }
    }
 
}
