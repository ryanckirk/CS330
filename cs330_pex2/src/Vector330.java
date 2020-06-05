import java.awt.*;
import java.util.*;
import java.util.regex.Pattern;
/**
 * @author Ryan Kirk
 */


public class Vector330<dir, unit, d> {
    /**
     * Declares the max difference value for equality. This is due to the fact that floating point numbers are not always
     * //exactly precise.
     */

    private final double EPS = 1.0E-09;



    private int xVector;
    private int yVector;








    public Vector330(int xInput, int yInput){
        this.xVector= xInput;
        this.yVector=yInput;
    }






    double getxVectorDouble(){
        return this.xVector;
    }
    double getyVectorDouble(){
        return this.yVector;
    }
    int getxVectorInt(){
        return (int)this.xVector;
    }
    int getyVectorInt(){
        return (int)this.yVector;
    }
    long getxVectorLong(){
        return (long)this.xVector;
    }
    long getyVectorLong(){
        return (long)this.yVector;
    }


    void setxVector(int input){
        this.xVector = input;
    }
    void setyVector(int input){
        this.yVector = input;
    }

    Vector330 subtract (Vector330 other){
        Vector330 res = new Vector330(0,0);
        res.xVector = this.xVector - other.xVector;
        res.yVector = this.yVector - other.yVector;
        return res;
    }
    Vector330 add (Vector330 other){
        Vector330 res = new Vector330(0,0);
        res.xVector = this.xVector + other.xVector;
        res.yVector = this.yVector + other.yVector;
        return res;
    }
    Vector330 scale (double scaleMultiplier){
        Vector330 res = new Vector330(0,0);
        //res.xVector = this.xVector * scaleMultiplier;
        //res.yVector = this.yVector *scaleMultiplier;
        return res;
    }
    double magnitude(){
        //Formula for magnitude
        double result = Math.sqrt((this.xVector*this.xVector)*(this.yVector*this.yVector));
        return result;
    }

    double direction(){
        double result = Math.atan2(this.yVector, this.xVector);
        return result;
    }

    boolean equals(Vector330 other){
        if ((this.xVector-other.xVector)< EPS){
            if ((this.yVector-other.yVector)< EPS){
                //Validates that both vectors are in fact equal
                return true;
            }
        }
        return false;
    }

}
