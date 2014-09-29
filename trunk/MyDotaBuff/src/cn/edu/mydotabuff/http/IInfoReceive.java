package cn.edu.mydotabuff.http;

/**
 * 
 ��Ȩ���У���Ȩ����(C)2013���������?
 * �ļ���ƣ�com.goopai.selfdrive.http.IInfoReceive.java ϵͳ��ţ�?ϵͳ��ƣ�SelfDrive
 * ģ���ţ� ģ����ƣ�?����ĵ���?�������ڣ�2013-11-14 ����12:26:31 �� �ߣ�½����
 * ����ժҪ�����պ�̨��ݽӿ�?���еĴ�����������Σ�����������������෽���� �ļ�����:
 */
public interface IInfoReceive {
	/**
	 * �������?
	 * 
	 * @param msgType
	 *            ��Ϣ���?
	 * @param jsonStr
	 *            json�ִ�
	 */
	void onMsgReceiver(ResponseObj receiveInfo);

	public class ResponseObj {
		public ReceiveMsgType getMsgType() {
			return msgType;
		}

		public void setMsgType(ReceiveMsgType msgType) {
			this.msgType = msgType;
		}

		public String getJsonStr() {
			return jsonStr;
		}

		public void setJsonStr(String jsonStr) {
			this.jsonStr = jsonStr;
		}

		private ReceiveMsgType msgType;
		private String jsonStr;
	}

	public enum ReceiveMsgType {
		/**
		 * �ɹ�����
		 * */
		OK("ok"),
		/**
		 * ʧ�ܷ���
		 */
		FAILED("failed"),
		/**
		 * ��ʱ����
		 */
		TIMEOUT("timeout"),
		/**
		 * �������ҳ������?
		 */
		NOT_FOUND("not_found");

		private String TypeCode;

		/**
		 * ����һ������ָ���ַ��ö���?
		 * 
		 * @param inTypeCode
		 */
		private ReceiveMsgType(String inTypecode) {
			this.TypeCode = inTypecode;
		}

		@Override
		public String toString() {
			return TypeCode;
		}

		/**
		 * ���ָ�����ַ��ȡ��Ӧ��ö��ֵ
		 * 
		 * @param inActionCode
		 * @return
		 */
		public static ReceiveMsgType getMsgType(String inTypeCode) {
			for (ReceiveMsgType type : ReceiveMsgType.values()) {
				if (type.TypeCode.equals(inTypeCode))
					return type;
			}
			return null;
		}
	}
}
