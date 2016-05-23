package com.wingnity.jsonparsingtutorial;

public class Actors {

	private String title;
	private String image;
	private String description;

	public Actors() {
		// TODO Auto-generated constructor stub
	}

	public Actors(String title, String description, String image) {
		super();
		//this.name = name;
		this.description = description;
		this.title = title;
		this.image = image;
	}


/*	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
*/
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
