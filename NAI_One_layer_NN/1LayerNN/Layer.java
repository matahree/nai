import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Layer {

	private static double learningRate;
	private static File trainDir;
	private static File testDir;
	
	public static double getLearningRate() {
		return learningRate;
	}
	
	private List<Perceptron> perceptrons;
	
	public Layer(String dataPath, double learningRate, double threshold) {
		this.learningRate = learningRate;
		
		trainDir = new File(dataPath + "/train");
		testDir = new File(dataPath + "/test");
		
		perceptrons = new ArrayList<>();
		for (File file : trainDir.listFiles()) {
			if (file.isDirectory()) {
//				System.out.println("Creating perceptron: " + file.getName());
				perceptrons.add(new Perceptron(file.getName(), threshold, randomWeights()));
			}
		}
	}
	
	public String predict(Vector vector) {
		Map<String, Double> predictions = new HashMap<>();
		for (Perceptron perceptron : perceptrons) {
			predictions.put(perceptron.getLangName(), perceptron.predict(vector));
		}
		// max output
		Map.Entry<String, Double> maxY = null;
		for (Map.Entry<String, Double> entry : predictions.entrySet()) {
//			System.out.println(entry);
			if (maxY == null)
				maxY = entry;
			else if (maxY.getValue() < entry.getValue())
				maxY = entry;		
		}
		return maxY.getKey();
	}
	
	public void train(double percentageError) {
		while((100 - getPredictionPercentage()) > percentageError) {
			// iterate data folder
			for (File file : trainDir.listFiles()) {
				if (file.isDirectory()) {
					String langName = file.getName();
					// iterate [lang] folder
					for (File textFile : file.listFiles()) {
						String text = null;
						try {
							text = Files.readString(textFile.toPath(), StandardCharsets.UTF_8);
						} catch (IOException e) {
							e.printStackTrace();
						}
						Vector vector = new Vector(langName, Parser.parseText(text));
//						System.out.println("prediction: " + prediction + ", correct: " + langName);
						for (Perceptron perceptron : perceptrons) {
							double y = perceptron.predict(vector);
							double d;
							if (!perceptron.getLangName().equals(langName))
								d = 0;
							else
								d = 1;
							perceptron.deltaRuleWeights(vector, d, y);
							perceptron.deltaRuleThreshold(d, y);
//							System.out.println(perceptron.getWeights().getCoordinates().stream().mapToDouble(Double::doubleValue).sum());
						}
					}
				}
			}
		}
	}
	
	public double getPredictionPercentage() {
		int filesCount = 0;
		int correctPredictions = 0;
		// iterate data folder
		for (File file : testDir.listFiles()) {
			if (file.isDirectory()) {
				String langName = file.getName();
				// iterate [lang] folder
				for (File textFile : file.listFiles()) {
					filesCount++;
					String text = null;
					try {
						text = Files.readString(textFile.toPath(), StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Vector vector = new Vector(langName, Parser.parseText(text));
					String prediction = predict(vector);
					if (prediction.equals(langName))
						correctPredictions++;
				}
			}
		}
		System.out.println(((double) correctPredictions / filesCount) * 100);
		return ((double) correctPredictions / filesCount) * 100;
	}
	
	//normalized
//	private static List<Double> randomWeights() {
//		List<Double> weights = new ArrayList<>();
//		Random r = new Random();
//		double max = 1;
//		double min = 0;
//		for (int i = 0; i < 25; i++) {
//			double number = (r.nextDouble() * (max - min)) + min;
//			weights.add(number);
//			max -= number;
//		}
//		weights.add(max);
//		return weights;
//	}
	
	private static List<Double> randomWeights() {
		List<Double> weights = new ArrayList<>();
		Random r = new Random();
		double max = 1;
		double min = 0.01;
		for (int i = 0; i < 26; i++) {
			double number = (r.nextDouble() * (max - min)) + min;
			weights.add(number);
		}
		return weights;
	}
	
}
