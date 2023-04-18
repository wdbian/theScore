package enumeration;

public enum League {
	NHL("NHL Hockey", "NHL"),
	NFL("NFL Football", "NFL"),
	MLB("MLB Baseball", "MLB"),
	NBA("NBA Basketball", "NBA"),
	NCAAF("NCAA Football", "NCAAF"),
	CFL("CFL Football", "CFL");
	
	private String description, shortDescription;
	
	League(String description, String shotDescription) {
		this.description = description;
		this.shortDescription = shotDescription;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getShortDescription() {
		return this.shortDescription;
	}
}
