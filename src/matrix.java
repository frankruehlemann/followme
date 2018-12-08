import java.lang.IllegalArgumentException;

public class matrix throws IllegalArgumentException{
	private float[][] values;

	public matrix(float[][] values){
		if !checkArray(values) {
			throw new IllegalArgumentException("No correct matrix provided");
		}
		this.values = values;
	}

	public boolean checkArray(float[][] values) {
		if (values.length > 4) {
			return false;
		}
		for (int i=0; i<values.length; i++) {
			if (values[i] > 4) {
				return false;
			}
		}
	}

//	public int[][] sanitize(int[][] array) {
//		if !checkArray(array) {
//			throw new IllegalArgumentException("No correct matrix provided");
//		}
//		for (int i=0; i<4; i++) {
//			if (array.length < i)
//			for (int j=0; j<4; J++) {
//				if array[i][j]
//			}
//		}
//	}
}
