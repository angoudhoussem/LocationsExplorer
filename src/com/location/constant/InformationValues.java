package com.location.constant;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class InformationValues {
	String nom;
	String adress;
	String references;
	LatLng location;
	String Url;
	String tel;
	String id;
	String Image;
    
	public InformationValues(String nom, String adress, String references,
			LatLng location, String url, String tel, double latitude,
			double langitude) {
		super();
		this.nom = nom;
		this.adress = adress;
		this.references = references;
		this.location = location;
		Url = url;
		this.tel = tel;
		this.latitude = latitude;
		this.langitude = langitude;
	}

	public InformationValues() {
		super();
	}

	public String getUrl() {
		return Url;
	}

	public String getTel() {
		return tel;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLangitude() {
		return langitude;
	}

	public void setLangitude(double langitude) {
		this.langitude = langitude;
	}

	double latitude;
	double langitude;

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getId() {
		return id;
	}

	public String getImage() {
		return Image;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImage(String image) {
		Image = image;
	}

}
