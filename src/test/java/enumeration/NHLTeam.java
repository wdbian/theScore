package enumeration;

public enum NHLTeam {
	BOSTON_BRUINS("Boston Bruins", "BOS"),
	BUFFALO_SABRES("Buffalo Sabres", "BUF"),
	MONTREAL_CANADIANS("Montreal Canadiens", "MTL"),
	OTTAWA_SENATORS("Ottawa Senators", "OTT"),
	TORONTO_MAPLE_LEAFS("Toronto Maple Leafs", "TOR");
	
	private String description, shortDescription;
	
	NHLTeam(String description, String shortDescription) {
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
