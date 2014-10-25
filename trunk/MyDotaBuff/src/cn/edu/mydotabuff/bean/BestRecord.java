package cn.edu.mydotabuff.bean;

public class BestRecord {
	
	private String RecordType;
	private String MmatchID;
	private String Result;
	private String HeroName;
	private String RecordNum;
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
	@Override
	public String toString() {
		return "BestRecord [RecordType=" + RecordType + ", MmatchID="
				+ MmatchID + ", Result=" + Result + ", HeroName=" + HeroName
				+ ", RecordNum=" + RecordNum + "]";
	}
	public BestRecord(String recordType, String mmatchID, String result,
			String heroName, String recordNum) {
		super();
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
