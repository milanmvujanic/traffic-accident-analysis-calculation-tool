package pro.trafficaccidentanalysis.calculation.web.calculation;

public class StoppingDistance {

	private String speed;
	private String deceleration;
	private double stoppingDistance;
	
	private boolean stoppingDistanceExists;

	public StoppingDistance() {
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(String deceleration) {
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
		stoppingDistanceExists = (stoppingDistance > 0.0) ? true : false;
	}
	
	public boolean isStoppingDistanceExists() {
		return stoppingDistanceExists;
	}
}
