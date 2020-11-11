package pro.trafficaccidentanalysis.calculation.web.calculation;

public class Calculation {
	
	public static double calculateStoppingDistance(String speed, String deceleration) {		
		double speedConverted = Double.parseDouble(speed) / 3.6;
		double stoppingDistance = Math.round((1.1 * speedConverted + Math.pow(speedConverted, 2) / (2 * Double.parseDouble(deceleration))) * 100.0) / 100.0;
		return stoppingDistance;
	}
}
