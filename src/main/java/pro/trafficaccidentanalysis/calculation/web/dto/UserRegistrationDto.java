package pro.trafficaccidentanalysis.calculation.web.dto;

public class UserRegistrationDto {

	private String name;
	private String email;
	private String password;
	private String repeatPassword;
	private boolean enabled;

	public UserRegistrationDto(String name, String email, String password, String repeatPassword, boolean enabled) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.repeatPassword = repeatPassword;
		this.enabled = enabled;
	}

	public UserRegistrationDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

}
