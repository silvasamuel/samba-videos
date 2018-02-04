package com.bean;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import com.amazon.UploadObjectSingleOperation;

@ManagedBean(name = "upload")
@SessionScoped
public class UploadBean {

	private UploadObjectSingleOperation objectUpload = new UploadObjectSingleOperation();
	private UploadedFile file;
	private boolean isDisabled = true;
	
	public void uploadVideo() throws IOException, InterruptedException {
        if(file != null) {
        	objectUpload.upload(file.getFileName(), file.getContents());
        	
        	isDisabled = false;
        	
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	public void watchUploadedVideo() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		objectUpload.watchUploadedVideo();
		
		String url = objectUpload.getVideoUrl();
		
		if(url != "") {			
			externalContext.redirect(objectUpload.getVideoUrl());
		} else {
			FacesMessage message = new FacesMessage("Warning", "Please wait, the file is still being converted!");
            FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
}
