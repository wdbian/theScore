package enumeration;

public enum NFLTeam {
	BUFFALO_BILLS("Buffalo Bills", "BUF"),
	MIAMI_DOLPHINS("Miami Dolphins", "MIA"),
	NEW_ENGLAND_PATRIOTS("New England Patriots", "NE");
	
	private String description, shortDescription;
	
	NFLTeam(String description, String shortDescription) {
		this.description = description;
		this.shortDescription = shortDescription;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getShortDescription() {
		return this.shortDescription;
	}
}
