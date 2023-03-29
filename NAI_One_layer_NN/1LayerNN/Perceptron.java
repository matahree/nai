import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Perceptron {

	private String langName;
	private double threshold;
	private Vector weights;

	public Perceptron(String langName, double threshold, List<Double> weightsList) {
		this.langName = langName;
		this.threshold = threshold;
		this.weights = new Vector("Weights", weightsList);
	}
	
	public String getLangName() {
		return langName;
	}
	
	public Vector getWeights() {
		return weights;
	}


	public double predict(Vector vector) {
		double net = dotProduct(vector, weights) - threshold;
		return 1 / (1 + Math.exp(-net));
	}

	private double dotProduct(Vector vector1, Vector vector2) {
		double sum = 0;
		for (int i = 0, len = vector1.getDimension(); i < len; i++) {
			sum += vector1.getCoordinates().get(i) * vector2.getCoordinates().get(i);
		}
		return sum;
	}

	public void deltaRuleWeights(Vector vector, double d, double y) {
		double normalization = getNormalization();
		double delta = Layer.getLearningRate() * (d - y);
		weights.setCoordinates(IntStream.range(0, weights.getDimension())
				.mapToDouble(i -> weights.getCoordinates().get(i) + (vector.getCoordinates().get(i) * delta))
				.boxed().collect(Collectors.toList()));
		normalizeWeights(normalization);
	}

	public void deltaRuleThreshold(double d, double y) {
		threshold += Layer.getLearningRate() * (d - y) * -1;
	}
	
	public double getNormalization() {
		return (double) 1 / weights.getCoordinates().stream().mapToDouble(Double::doubleValue).sum();
	}
	
	public void normalizeWeights(double normalization) {
		weights.setCoordinates(weights.getCoordinates().stream().map(value -> value * normalization).collect(Collectors.toList()));
	}

	
	@Override
	public String toString() {
		return String.format("perceptron: [langName: %s]", langName);
	}

}
