package pro.trafficaccidentanalysis.calculation.web.calculation;

public class StoppingDistance {

	private String speed;
	private String deceleration;
	private double stoppingDistance;
	private String reactionTime = "1.1";
	private String brakingForceIncrease = "0.2";

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
		this.stoppingDistance = Calculation.calculateStoppingDistance(speed, deceleration, reactionTime,
				brakingForceIncrease);
		stoppingDistanceExists = (stoppingDistance > 0.0) ? true : false;
	}

	public boolean isStoppingDistanceExists() {
		return stoppingDistanceExists;
	}

	public String getReactionTime() {
		return reactionTime;
	}

	public void setReactionTime(String reactionTime) {
		this.reactionTime = reactionTime;
	}

	public String getBrakingForceIncrease() {
		return brakingForceIncrease;
	}

	public void setBrakingForceIncrease(String brakingForceIncrease) {
		this.brakingForceIncrease = brakingForceIncrease;
	}

}
