package no.ntnu.mmfplanner.jira.model;

public class Issue {

	private long id = 0;
	private String name;
	private String key;
	private String originalEstimate;
	private Epic epic;

	public Issue() {
	}

	public Issue(long id, String name, String key, String originalEstimate, Epic epic) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.originalEstimate = originalEstimate;
		this.epic = epic;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOriginalEstimate() {
		return originalEstimate;
	}

	public void setOriginalEstimate(String originalEstimate) {
		this.originalEstimate = originalEstimate;
	}

	public Epic getEpic() {
		return epic;
	}

	public void setEpic(Epic epic) {
		this.epic = epic;
	}

}
