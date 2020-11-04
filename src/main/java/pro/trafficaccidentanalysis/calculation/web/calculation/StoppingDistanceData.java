package pro.trafficaccidentanalysis.calculation.web.calculation;

public class StoppingDistanceData {

	private double speed;
	private double deceleration;
	private double stoppingDistance;

	public StoppingDistanceData() {
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(double deceleration) {
		this.deceleration = deceleration;
	}

	public double getStoppingDistance() {
		return stoppingDistance;
	}

	public void setStoppingDistance(double stoppingDistance) {
		this.stoppingDistance = stoppingDistance;
	}
	
	public void calculateStoppingDistance() {
		this.stoppingDistance = Calculation.calculateStoppingDistance(speed, deceleration);
	}

}
