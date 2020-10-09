/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        
        double degree90 = 90 ;

        turtle.forward(sideLength) ;
        turtle.turn(degree90) ;
        turtle.forward(sideLength) ;
        turtle.turn(degree90) ;
        turtle.forward(sideLength) ;
        turtle.turn(degree90) ;
        turtle.forward(sideLength) ;

    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        
        if(sides < 2) {
            throw new RuntimeException("Sides must be > 2");            
        }
        else {
            
            double angle = (((double) sides - 2)*180/ (double) sides) ;
            
            System.out.println("This polygon of " + sides +
                    " sides has inside angles of " + angle) ;
            
            return angle ;
        }


    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        
        if(!(angle > 0) || !(angle<180)) {
            
            throw new RuntimeException("angle is not in range");
        }
        
        else {
            
            double exterior = 180 - angle ;
            
            int sides = (int)Math.round(360/exterior) ;
            
            System.out.println("This polygon of " + angle +
                    " interior angle has " + sides + " sides.") ;
            
            return sides ;
        }
        
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        
        double angle = calculateRegularPolygonAngle(sides) ;
        
        // draw the first side
        turtle.forward(sideLength) ;
        
        // finish the sides and angles
        for (int i = 0; i < sides-1; i++) {
            turtle.turn(180-angle) ;
            turtle.forward(sideLength) ;
        }
        
        
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        
        // getting the distance from point to target in X, Y terms
        double distanceY = targetY - currentY ;
        double distanceX = targetX - currentX ;
        
        // 'heading' is the angle from positive X axis we need to head
        double radianHeading = Math.atan2(distanceY, distanceX) ;
        
        // convert to degrees so we can subtract
        double degreeHeading = Math.toDegrees(radianHeading) ;
                
        // 'adjustment' is the turn required, less the angle we're already at
        double adjustment = 90 - degreeHeading - currentHeading ;
        
        // replacing negative values with positive versions.
        if(adjustment < 0) {
            
            adjustment = 360 + adjustment ;

        }
        
        System.out.println("Adjusting course by " + adjustment + " degrees.") ;

        if((adjustment >360) || (adjustment < 0)) {
            
            throw new RuntimeException("wrong angle!");
        }
        else {
            
            return adjustment ;
        }
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        
        // throw exception if lists are not equivalent
        if (xCoords.size() != yCoords.size()) {
            throw new RuntimeException("coordinate lists not equal length!");
        } 
        
        else {
            ;
        }
        
        // create empty list and variables
        List<Double> headingsList = new ArrayList<>();
        int currentX ;
        int currentY ;
        double currentHeading ;
        int targetX ;
        int targetY ;
        
        for (int i = 0; i < xCoords.size() -1; i++) {
         
                        
            if(i == 0) {

                currentHeading = 0 ;

            }
            
            else {
                
                currentHeading = headingsList.get(i-1) ;
                
            }
            
            currentX = xCoords.get(i) ;
            currentY = yCoords.get(i) ;
            targetX = xCoords.get(i+1) ;
            targetY = yCoords.get(i+1) ;
            
            double nextHeading = calculateHeadingToPoint(currentHeading, currentX, currentY, targetX, targetY) ;
            
            headingsList.add(nextHeading) ;
            
        }
        
        System.out.println("Heading list is " + headingsList) ;
        return headingsList ;
        
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        
        
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        // drawSquare(turtle, 40);
        
        // drawRegularPolygon(turtle, 3, 40) ;
        
        // calculateRegularPolygonAngle(7);
//        TurtleSoup.calculateHeadingToPoint(0.0, 0, 0, 0, 1) ;
//        TurtleSoup.calculateHeadingToPoint(0.0, 0, 0, 1, 0) ;
//        TurtleSoup.calculateHeadingToPoint(1.0, 4, 5, 4, 6) ;
        
        
        // drawPersonalArt(turtle) ;

        // draw the window
        turtle.draw();
    }

}
