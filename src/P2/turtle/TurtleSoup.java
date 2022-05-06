/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 3; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double angle = 180 * (sides - 2) * 1.0 / sides;
        return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX, int targetY) {
        int tempX = targetX - currentX;
        int tempY = targetY - currentY;
        double num = sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2));
        if (currentBearing >= 0 && currentBearing <= 180) {
            if (tempX >= 0) {
                if ((double) tempY / num - cos(toRadians(currentBearing)) <= 0) {
                    return Math.acos((double) tempY / num) / Math.PI * 180.0 - currentBearing;
                } else {
                    return (double) 360.0 - currentBearing + Math.acos((double) tempY / num) / Math.PI * 180.0;
                }
            } else {
                return (double) 360.0 - currentBearing - Math.acos((double) tempY / num) / Math.PI * 180.0;
            }
        } else {
            if (tempX <= 0) {
                if (tempY / num - cos(toRadians(currentBearing)) >= 0) {
                    return currentBearing - Math.acos((double) tempY / num) / Math.PI * 180.0;
                } else {
                    return 360.0 + currentBearing - Math.acos((double) tempY / num) / Math.PI * 180.0;
                }
            } else {
                return currentBearing + Math.acos((double) tempY / num) / Math.PI * 180.0;
            }
        }
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> l = new ArrayList<>();
        l.add(calculateBearingToPoint(0, xCoords.get(0), yCoords.get(0), xCoords.get(1), yCoords.get(1)));
        for (int i = 2; i < xCoords.size(); i++) {
            int tempX = xCoords.get(i - 1) - xCoords.get(i - 2);
            int tempY = yCoords.get(i - 1) - yCoords.get(i - 2);
            double j = Math.acos((double) tempY / Math.sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2))) / Math.PI * 180.0;
            l.add(calculateBearingToPoint(j, xCoords.get(i - 1), yCoords.get(i - 1), xCoords.get(i), yCoords.get(i)));
        }
        return l;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size() <= 3) {
            return points;
        }
        Set<Point> sorder = new HashSet<>();

        Iterator<Point> it = points.iterator();
        Point temp = it.next();
        while (it.hasNext()) {
            Point tempx1 = it.next();
            if (tempx1.x() < temp.x()) {
                temp = tempx1;
            } else if (tempx1.x() == temp.x() && tempx1.y() < temp.y()) {
                temp = tempx1;
            }
        }
        sorder.add(temp);

        ArrayList<Point> Arr = new ArrayList<>(points);
        Point waitp, pointp;
        double anglel1 = 0;
        double anglel2 = 0;
        waitp = temp;
        pointp = Arr.get(0);
        double tempx, tempy;

        do {
            anglel1 = 360;
            for (int i = 0; i < Arr.size(); i++) {
                if (Arr.get(i) != waitp) {
                    tempx = Arr.get(i).x();
                    tempy = Arr.get(i).y();
                    anglel2 = calculateBearingToPoint(0, (int) waitp.x(), (int) waitp.y(), (int) tempx, (int) tempy);
                    if (anglel2 < anglel1) {
                        pointp = Arr.get(i);
                        anglel1 = anglel2;
                    } else if (anglel1 == anglel2) {
                        double distance1 = Math.pow(waitp.x() - tempx, 2) + Math.pow(waitp.y() - tempy, 2);
                        double distance2 = Math.pow(waitp.x() - pointp.x(), 2) + Math.pow(waitp.y() - pointp.y(), 2);
                        if (distance1 > distance2) {
                            pointp = Arr.get(i);
                        }
                    }
                }
            }
            sorder.add(pointp);
            Arr.remove(pointp);
            waitp = pointp;
        } while (waitp != temp);
        return sorder;

    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        int Size = 400, Step = 1, Densi = 1, ColorNum = 5;
        for (int i = 1; i <= Size; i++) {
            switch (i % ColorNum) {
                case 0:
                    turtle.color(PenColor.BLUE);
                    break;
                case 1:
                    turtle.color(PenColor.GREEN);
                    break;
                case 2:
                    turtle.color(PenColor.YELLOW);
                    break;
                case 3:
                    turtle.color(PenColor.RED);
                    break;
                case 4:
                    turtle.color(PenColor.MAGENTA);
                    break;
            }
            turtle.forward(Step * i);
            turtle.turn(360 / ColorNum + Densi);
        }
    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        drawPersonalArt(turtle);
        //draw the window
        turtle.draw();
    }

}
