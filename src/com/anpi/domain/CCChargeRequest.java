package com.anpi.domain;

import com.google.gson.annotations.SerializedName;

//@XmlRootElement(name = "ccProfileChargeRequest")
public class CCChargeRequest {

	@SerializedName("Order")
	private Order order;

	@SerializedName("Profile")
	private Profile profile;

	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
