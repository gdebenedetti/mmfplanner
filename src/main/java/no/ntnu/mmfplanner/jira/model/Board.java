package no.ntnu.mmfplanner.jira.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private long id = 0;
	private String name;
	private String self;
	private String type;
	private List<Epic> epics;

	public Board() {
	}

	public Board(long id, String name, String self, String type) {
		super();
		this.id = id;
		this.name = name;
		this.self = self;
		this.type = type;
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

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Epic> getEpics() {
		if (epics == null)
			epics = new ArrayList<Epic>();
		return epics;
	}

	public void setEpics(List<Epic> epics) {
		this.epics = epics;
	}

}
