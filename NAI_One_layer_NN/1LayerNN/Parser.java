import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {

	public static Set<Vector> parseData(String dataFile) {

		Set<Vector> data = new HashSet<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line;
			int dimension = 0;
			while ((line = br.readLine()) != null) {
				String vectorClass = line.substring(line.lastIndexOf(',') + 1);
				List<Double> coordinates = Arrays.stream(line.substring(0, line.lastIndexOf(',')).split("\\s*,\\s*"))
						.map(Double::parseDouble).collect(Collectors.toList());
				if (dimension == 0)
					dimension = coordinates.size();
				if (dimension == coordinates.size())
					data.add(new Vector(vectorClass, coordinates));
				else {
					br.close();
					throw new IllegalArgumentException("File contains different vector dimensions!");
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return data;

	}
	
	public static List<Double> parseText(String text) {
		
		String abc = "abcdefghijklmnopqrstuvwxyz";
		Map<Character, Integer> abcCount = new HashMap<>();
		for (char letter : abc.toCharArray())
			abcCount.put(letter, 0);
		text = text.toLowerCase();
		
		for (char symbol : text.toCharArray()) {
			if (abc.contains(Character.toString(symbol))) {
				abcCount.put(symbol, abcCount.get(symbol) + 1);
			}
		}
		
		int lettersCount = abcCount.values().stream().mapToInt(Integer::intValue).sum();
		List<Double> weightsList = new ArrayList<>();
		
		for (char letter : abc.toCharArray()) {
			int letterCount = abcCount.get(letter);
			if (letterCount != 0)
				weightsList.add((double) letterCount / lettersCount);
			else
				weightsList.add(0.0);
		}
		return weightsList;
		
	}

}
