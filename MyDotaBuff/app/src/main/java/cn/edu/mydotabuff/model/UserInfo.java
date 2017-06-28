package cn.edu.mydotabuff.model;

public class UserInfo {
	
	private String userName;
	private String userID;
	private String imgUrl;
	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", userID=" + userID
				+ ", imgUrl=" + imgUrl + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfo(String userName, String userID, String imgUrl) {
		super();
		this.userName = userName;
		this.userID = userID;
		this.imgUrl = imgUrl;
	}
	
	

}
