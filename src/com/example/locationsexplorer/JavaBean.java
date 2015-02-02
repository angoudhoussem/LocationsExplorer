package com.example.locationsexplorer;

public class JavaBean {


	String ItemName;
	int imgResID;
	String title;
    String 	adresse;
    String url;
    String numtel;
 
	


	public JavaBean(String itemName, int imgResID, String title,
			String adresse, String url, String numtel) {
		super();
		this.ItemName = itemName;
		this.imgResID = imgResID;
		this.title = title;
		this.adresse = adresse;
		this.url = url;
		this.numtel = numtel;
	}


	public JavaBean() {
		super();
	}


	public JavaBean(String title) {
		
		this.title = title;
	}


	public void setItemName(String itemName) {
		ItemName = itemName;
	}


	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}


	public String getItemName() {
		return ItemName;
	}


	public int getImgResID() {
		return imgResID;
	}


	public String getTitle() {
		return title;
	}


	public String getAdresse() {
		return adresse;
	}


	public String getUrl() {
		return url;
	}


	public String getNumtel() {
		return numtel;
	}

	

}
