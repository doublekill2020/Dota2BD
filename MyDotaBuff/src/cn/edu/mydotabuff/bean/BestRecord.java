package cn.edu.mydotabuff.bean;

import java.io.Serializable;

public class BestRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 10291603L;
	private String ImageUri;
	private String RecordType;
	private String MmatchID;
	private String Result;
	private String HeroName;
	private String RecordNum;
	public String getImageUri() {
		return ImageUri;
	}
	public void setImageUri(String imageUri) {
		ImageUri = imageUri;
	}
	public String getRecordType() {
		return RecordType;
	}
	public void setRecordType(String recordType) {
		RecordType = recordType;
	}
	public String getMmatchID() {
		return MmatchID;
	}
	public void setMmatchID(String mmatchID) {
		MmatchID = mmatchID;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getHeroName() {
		return HeroName;
	}
	public void setHeroName(String heroName) {
		HeroName = heroName;
	}
	public String getRecordNum() {
		return RecordNum;
	}
	public void setRecordNum(String recordNum) {
		RecordNum = recordNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "BestRecord [ImageUri=" + ImageUri + ", RecordType="
				+ RecordType + ", MmatchID=" + MmatchID + ", Result=" + Result
				+ ", HeroName=" + HeroName + ", RecordNum=" + RecordNum + "]";
	}
	public BestRecord(String imageUri, String recordType, String mmatchID,
			String result, String heroName, String recordNum) {
		super();
		ImageUri = imageUri;
		RecordType = recordType;
		MmatchID = mmatchID;
		Result = result;
		HeroName = heroName;
		RecordNum = recordNum;
	}
	public BestRecord() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	

}
