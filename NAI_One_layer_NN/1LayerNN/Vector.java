import java.util.List;
import java.util.Objects;

public class Vector {

	private String vectorClass;
	private List<Double> coordinates;
	private int dimension;

	public Vector(String vectorClass, List<Double> coordinates) {
		this.vectorClass = vectorClass;
		this.coordinates = coordinates;
		this.dimension = coordinates.size();
	}

	public Vector(List<Double> coordinates) {
		this(null, coordinates);
	}

	public String getVectorClass() {
		return vectorClass;
	}

	public List<Double> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

	public int getDimension() {
		return dimension;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector vector = (Vector) obj;
		return Objects.equals(vectorClass, vector.vectorClass) && Objects.equals(coordinates, vector.coordinates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vectorClass, coordinates);
	}
	
	@Override
	public String toString() {
		return String.format("vector: [vectorClass: %s,coordinates: %s]", vectorClass, coordinates);
	}

}
