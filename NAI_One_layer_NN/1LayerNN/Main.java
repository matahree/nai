import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		String dataPath = "data";
		double learningRate = 0.01;
		double threshold = 0.01;
		
		Layer layer = new Layer(dataPath, learningRate, threshold);
		layer.train(2);
		testActualData(layer);
		
//		layer.predict(Parser.parseText("Basically you'd need to iterate over the map's entry set, remembering both the \"currently known maximum\" and the key associated with it. (Or just the entry containing both, of course.)"));
		
		
//		System.out.println(layer.getPredictionPercentage());
		
		// create larger test data
//		for (File file : new File("actualTrainData").listFiles()) {
//			if (file.isDirectory()) {
//				String langName = file.getName();
//				String text = null;
//				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//				try {
//					System.out.print("Enter text [" + langName + "]: ");
//					text = br.readLine();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				List<String> texts = /*Arrays.asList(text.split("(?<=\\G.{400})"));*/ new ArrayList<>();
//				for (int start = 0; start < text.length(); start += 400) {
//					texts.add(text.substring(start, Math.min(text.length(), start + 400)));
//				}
//				System.out.println(texts.size());
//				for (int i = 0; i < texts.size(); i++) {
//					try {
//						Files.writeString(Paths.get(file.getPath() + "/" + langName + (i + 1) + ".txt"), texts.get(i), StandardCharsets.UTF_8);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		
		// test for 100
//		List<Double> results = Collections.synchronizedList(new ArrayList<>());
//		
//		for (int i = 0; i < 100; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					Layer layer = new Layer(dataPath, learningRate, threshold);
//					layer.train(2);
//					synchronized (results) {
//						results.add(testActualData(layer));
//					}
//				}
//			}).start();
//		}
//		
//		try {
//			Thread.sleep(1000 * 60);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//			
//		System.out.println("Average: " + results.stream().mapToDouble(Double::doubleValue).average());
		
		// user input
		while(true) {
			String langName = null;
			String text = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.print("Enter lang name: ");
				langName = br.readLine();
				System.out.print("Enter text: ");
				text = br.readLine();
				String prediction = layer.predict(new Vector(langName, Parser.parseText(text)));
				System.out.println("Predicted: " + prediction + ", Correct: " + langName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static double testActualData(Layer layer) {
		int filesCount = 0;
		int correctPredictions = 0;
		// iterate data folder
		for (File file : new File("actualTestData").listFiles()) {
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
					String prediction = layer.predict(vector);
					if (prediction.equals(langName))
						correctPredictions++;
				}
			}
		}
		System.out.println("Bigger data: " + ((double) correctPredictions / filesCount) * 100);
		return ((double) correctPredictions / filesCount) * 100;
	}

}
