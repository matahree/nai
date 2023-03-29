package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vector {
    protected List<Double> values = new ArrayList();

    //constructor
    public Vector()
    {

    }
    //constructor with the dimension of the vector
    public Vector(int dimension)
    {
        List<Double> temp = new ArrayList();

        for(int i = 0; i < dimension; ++i)
        {
            temp.add(0.0D);
        }
        this.values = temp;
    }
    //constructor with the data set and dimesnion of data
    public Vector(List<Double> values) {

        this.values = values;
    }
    //return dimension
    public int getDimension() {
        return this.values.size();
    }

    //it makes the vector take all the coordinates and
    //divides them by the square root of the sum of them to the power of two
    public Vector normalize()
    {
        double denominator = 0;
        //goes through all the dimensions and adds their squares to the denominator
        for (int i = 0; i < getDimension(); i++)
        {
            denominator+= values.get(i)*values.get(i);
        }
        //roots the denominator
        denominator = Math.sqrt(denominator);

        List<Double> temp = new ArrayList<>();
        //adds each dimension divided by the denominator to the new list of data
        for (int i = 0; i < getDimension(); i++)
        {
            temp.add(values.get(i)/denominator);
        }
        return new Vector(temp);
    }

    // the sum of each dimension multiplies by the other vector's dimension
    public static double scalarProduct(Vector vec1, Vector vec2)
    {
        double result = 0;
        //checks if vectors match
        if(vec1.getDimension() == vec2.getDimension())
        {
            //adds the multiplication to result
            for (int i = 0; i < vec1.getDimension(); i++)
            {
                result += vec1.values.get(i) * vec2.values.get(i);
            }
        }
        return result;
    }
    //returns a vector for  each dimension is the result of the multiplication by the value
    public Vector multiply(double value)
    {
        List<Double> temp = new ArrayList<>();

        for (int i = 0; i < getDimension(); i++)
        {
            temp.add(values.get(i) * value);
        }
        return new Vector(temp);
    }
    //creates a vector for which each dimsension is the sum on the dimensions of the two given vectors
    public static Vector add(Vector vector1, Vector vector2)
    {
        List<Double> temp = new ArrayList<>();
        for (int i = 0; i < vector1.getDimension(); i++)
        {
            temp.add(vector1.values.get(i) + vector2.values.get(i));
        }
        return new Vector(temp);
    }
    //the same as above but multiplied instead of added
    public static Vector multiply(Vector vector1, Vector vector2)
    {
        List<Double> temp = new ArrayList<>();

        for (int i = 0; i < vector1.getDimension(); i++)
        {
            temp.add(vector1.values.get(i) * vector2.values.get(i));
        }
        return new Vector(temp);
    }
    //gets the value of the index in the vector (dimension)
    public Double getValue(int index) throws Exception {
        if (index > this.getDimension()) {
            throw new Exception();
        } else {
            return (Double)this.values.get(index);
        }
    }
    //changes the value of the vector at a given index
    public void setValue(int index, double value) throws Exception {
        if (index > this.getDimension()) {
            throw new Exception();
        } else {
            this.values.set(index, value);
        }
    }
    //essentialy nmakes this vector the same as the one that is given
    public void setValues(Vector vec1)
    {
        for(int i = 0; i < this.values.size(); ++i)
        {
            this.values.set(i, (Double)vec1.values.get(i));
        }
    }

    public String toString() {
        return this.values.toString();
    }
}

