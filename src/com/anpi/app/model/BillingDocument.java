package com.anpi.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_billing_documents_mod")
public class BillingDocument{
	@Id
	@Column(name="id")
	private int id;
	@Column(name="uuid")
	private String uuid;
	@Column(name="path")
	private String path;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="updated_at")
	private Date updatedAt;
	@Column(name="user_id")
	private String userId;
	@Column(name="permissions")
	private int permissions;
	@Column(name="original_file_name")
	private String fileName;
	@Column(name="mime_type")
	private String mimeType;
	@Column(name="mail_sent")
	private int mailSent;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPermissions() {
		return permissions;
	}
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public int getMailSent() {
		return mailSent;
	}
	public void setMailSent(int mailSent) {
		this.mailSent = mailSent;
	}
	@Override
	public String toString() {
		return "BillingDocument [id=" + id + ", uuid=" + uuid + ", path=" + path + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", userId=" + userId + ", permissions=" + permissions
				+ ", fileName=" + fileName + ", mimeType=" + mimeType + ", mailSent=" + mailSent + "]";
	}
	
	

}
