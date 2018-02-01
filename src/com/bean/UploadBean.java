package com.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "upload")
public class UploadBean {

	private String value = "Teste";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
