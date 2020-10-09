// Hello World

import java.io.*;

import java.util.Scanner;

import java.lang.*;

class HelloWorldApp {
    public static void main(String[] args) {

        System.out.println("Hello World!"); // Display the string.

    }
}

class GravityCalculator {
	public static void main(String[] args) {
		
		// acceleration
		double a = -9.81 ;

		// velocity
		double v = 10 ;

		// time passed
		double t = 3 ;
		double t2 = Math.pow(t,2) ;

		// solution

		double position = (0.5 * a * t2 ) + v ;

		System.out.println("The position of the object at " + t + "seconds is " + position ) ;
	}

}
