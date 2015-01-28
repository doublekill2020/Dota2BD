package cn.edu.mydotabuff.common.http;

public interface IInfoReceive {

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
