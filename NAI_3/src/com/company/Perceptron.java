package com.company;

import java.util.Objects;
    // in this project we check 3 perceptron 1 checks if its english another if its polish and another if its german
    //and you determine the language if 1 says yes and the others say no
    // so we need a perceptron brain class cause a brain is the system of 3 perceptrons
      //  1 for each language
public class Perceptron
{
    private final String name;
    private Vector weightVector;
    private final double learningRate;
    private double bias = 0;

    public Perceptron(String name, double learningRate, int dimension)
    {
        this.name = name;
        this.learningRate = learningRate;
        weightVector = new Vector(dimension);
    }
    // bias is theta
    public void learn(DataObject dataObject) throws Exception
    {
        double output = getOutput(dataObject.values());

        Vector newWeight = Vector.add(weightVector, (dataObject.values().multiply(learningRate*(strToInt(dataObject.type())-output))));
        bias = bias - learningRate * (strToInt(dataObject.type()) - output);
        weightVector.setValues(newWeight);
    }

    public int getOutput(Vector input)
    {
        double net = Vector.scalarProduct(weightVector,input) - bias;
        return net >= 0 ? 1 : 0;
    }

    public double getOutputDouble(Vector input)
    {
        return Vector.scalarProduct(weightVector,input) - bias;
    }

    public int strToInt(String str)
    {
        if(Objects.equals(name, str))
        {
            return 1;
        }
        return 0;
    }

    public String getName()
    {
        return name;
    }
}
