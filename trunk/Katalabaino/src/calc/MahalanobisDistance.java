package calc;

import org.apache.commons.math.DimensionMismatchException;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics;

public class MahalanobisDistance
{

	private MultivariateSummaryStatistics	mss;
	public RealMatrix						dataMatrix, invCOV;
	public RealVector						mu;

	public MahalanobisDistance(double[][] data) throws DimensionMismatchException
	{
		mss = new MultivariateSummaryStatistics(data[0].length, true);

		// for (int i = 0; i < data.length; i++) {
		// System.out.print(i + ", ");
		// PrintArray(data[i]);
		// }
		for (int i = 0; i < data.length; i++)
			mss.addValue(data[i]);

		dataMatrix = new Array2DRowRealMatrix(data);

		invCOV = new LUDecompositionImpl(mss.getCovariance()).getSolver().getInverse();
		mu = new Array2DRowRealMatrix(mss.getMean()).getColumnVector(0);

		CenterMatrix(dataMatrix, mu);
	}

	public double[] getDistances()
	{
		double[] distances = new double[(int) mss.getN()];

		for (int i = 0; i < distances.length; i++) {

			RealMatrix xT = dataMatrix.getRowMatrix(i);
			RealMatrix x = xT.transpose();

			RealMatrix D2 = xT.multiply(invCOV);

			D2 = D2.multiply(x);

			distances[i] = Math.sqrt(D2.getEntry(0, 0));
		}
		return distances;
	}

	/**
	 * finds the distance of this single observation to the center of the cloud. clones the
	 * observation because it must be centered.
	 * 
	 * @param observation
	 * @return
	 */
	public double getSingleDistance(double[] observation)
	{
		double distance = 0.0;
		double[] data = observation.clone();

		// center the observation
		for (int i = 0; i < mu.getDimension(); i++)
			data[i] -= mu.getEntry(i);

		RealMatrix x = new Array2DRowRealMatrix(data);
		RealMatrix xT = x.transpose();

		RealMatrix D2 = xT.multiply(invCOV);
		D2 = D2.multiply(x);

		distance = Math.sqrt(D2.getEntry(0, 0));

		return distance;
	}

	public static void CenterMatrix(RealMatrix data, RealVector center)
	{
		for (int i = 0; i < data.getRowDimension(); i++) {
			RealVector row = data.getRowVector(i);
			row = row.subtract(center);
			data.setRowVector(i, row);
		}
	}

	// public static void main(String[] args) throws MatrixIndexException,
	// DimensionMismatchException
	// {
	// double[][] data = new double[1000][10];
	// Random r = new Random();
	// for (int i = 0; i < data.length; i++) {
	// for (int j = 0; j < data[i].length; j++)
	// data[i][j] = r.nextGaussian();
	// }
	// RealMatrix rm = new Array2DRowRealMatrix(data);
	// MahalanobisDistance md = new MahalanobisDistance(rm.getData());
	//
	// PrintArray(md.getDistances());
	// }

	public static void PrintArray(double[] mean)
	{
		for (Double d : mean)
			System.out.print(d + ",");
		System.out.println();
	}
}
