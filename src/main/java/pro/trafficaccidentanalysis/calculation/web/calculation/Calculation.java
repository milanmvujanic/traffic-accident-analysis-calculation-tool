package pro.trafficaccidentanalysis.calculation.web.calculation;

public class Calculation {
	
	public static double calculateStoppingDistance(String speed, String deceleration, String reactionTime, String brakingForceIncrease) {		
		double speedConverted = Double.parseDouble(speed) / 3.6;
		double decelerationConverted = Double.parseDouble(deceleration);
		double reactionTimeConverted = Double.parseDouble(reactionTime);
		double brakingForceIncreaseConverted = Double.parseDouble(brakingForceIncrease);
		
		double stoppingDistance = Math.round((reactionTimeConverted * speedConverted + 
				Math.pow((speedConverted - (decelerationConverted * brakingForceIncreaseConverted / 2)), 2) / (2 * decelerationConverted)) * 100.0) / 100.0;
		return stoppingDistance;
	}
}
