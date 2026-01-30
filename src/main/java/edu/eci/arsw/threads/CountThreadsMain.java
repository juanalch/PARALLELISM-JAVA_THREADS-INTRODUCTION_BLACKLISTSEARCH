package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){

        CountThread countThread1 = new CountThread(0, 99);
        CountThread countThread2 = new CountThread(99, 199);
        CountThread countThread3 = new CountThread(199, 299);

        // Inicio con Start()
        //countThread1.start();
        //countThread2.start();
        //countThread3.start();

        //Inicio con Run()
        countThread1.run();
        countThread2.run();
        countThread3.run();
        

        
        
    }
    
}
