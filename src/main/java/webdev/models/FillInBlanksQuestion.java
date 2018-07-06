package webdev.models;

import javax.persistence.Entity;

@Entity
public class FillInBlanksQuestion extends Question{
	private String variables;

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	
}
