package com.company;

import java.text.DecimalFormat;
import java.util.*;


public class PerceptronBrain
{
    private final DecimalFormat df = new DecimalFormat("#.#########");

    private List<DataObject> dataObjectList = new ArrayList<>();
    private List<Perceptron> perceptrons = new ArrayList<>();

    //its the constructor, the constructor for perceptron brain makes a perceptron for each lanaguage directory in the files
    public PerceptronBrain(List<Directory> dirList, double learningRate) throws Exception
    {
        for (int i = 0; i < dirList.size(); i++)
        {
            addTrainSetsFromDirectory(dirList.get(i));
            perceptrons.add(new Perceptron(dirList.get(i).getDirName(), learningRate, dataObjectList.get(0).values().getDimension()));
        }
    }
    // for adding keys to train set and saving them on a file perceptron brain uses this addtrainset method to get these keys
    // and for perceptron to learn from this
    public void addTrainSetsFromDirectory(Directory dir) throws Exception
    {
        String key = dir.getDirName();
        for (int i = 0; i < dir.getDirSizeLocal(); i++)
        {
            dataObjectList.add(new DataObject(key, LetterCounter.getLettersVector(dir.getFileContent(i))));
        }
    }
    // learn is for perceptrons to lear from dataobject
    public void learn() throws Exception
    {
        for (int i = 0; i < perceptrons.size(); i++)
        {
            for (int j = 0; j < dataObjectList.size(); j++)
            {
                perceptrons.get(i).learn(dataObjectList.get(j));
            }
        }
    }
    // classify map stores outputs and compares them
    public String classify(Vector text) throws Exception
    {
        Map<Double, Perceptron> outputMap = new TreeMap<>((o1, o2) -> Double.compare(o2, o1));

        for (Perceptron percepron : perceptrons)
        {
            outputMap.put(percepron.getOutputDouble(text), percepron);
        }
        return outputMap.entrySet().stream().findFirst().get().getValue().getName();
    }

    public double classifyAll(List<Directory> dirList, boolean consoleOn) throws Exception
    {
        int correctGuesses = 0;
        int totalGuesses = 0;
        //goes through each language
        for (int i = 0; i < dirList.size(); i++)
        {
            //goes through each file in a language
            for (int j = 0; j < dirList.get(i).getDirSizeLocal(); j++)
            {
                //it has a String for the language name and a Vector space for the vector resulting from the file
                DataObject dataObject = new DataObject(dirList.get(i).getDirName(), LetterCounter.getLettersVector(dirList.get(i).getFileContent(j)));
                //String that returns the classification done by perceptrons
                String result = classify(dataObject.values());
                //now it goes through each directory and checks if the result is the name of one of them
                if(result.equals(dataObject.type()))
                {
                    if(consoleOn)
                    {
                        //prints out the language
                        System.out.println(dirList.get(i).getDirName() + "/" + dirList.get(i).getFile(j).getName() + " is written in " + result);
                    }
                    correctGuesses++;
                }
                totalGuesses++;
            }
        }
        //returns accuracy
        return (double)correctGuesses/(double)totalGuesses;
    }
}
