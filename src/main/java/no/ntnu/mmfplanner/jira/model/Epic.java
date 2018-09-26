package no.ntnu.mmfplanner.jira.model;

import java.util.ArrayList;
import java.util.List;

public class Epic {

	private long id = 0;
	private String name;
	private String key;
	private String summary;
	private boolean done;
	private List<Issue> issues;

	public Epic() {
	}

	public Epic(long id, String name, String key, String summary, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.summary = summary;
		this.done = done;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public List<Issue> getIssues() {
		if (issues == null)
			issues = new ArrayList<Issue>();
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

}
