package cn.edu.mydotabuff.common;



/**
版权所有：版权所有(C)2014，固派软件
文件名称：com.ihengtu.didi.client.common.Ori.java
系统编号：
系统名称：DidiforClient
模块编号：
模块名称：
设计文档：
创建日期：2014-4-24 下午3:29:43
作 者：何鹏程
Version: 1.0
内容摘要：
类中的代码包括三个区段：类变量区、类属性区、类方法区。
文件调用:
 */
	public enum Orientation_Sensor {
		DEGREE_0("DEGREE_0"),
		DEGREE_90("DEGREE_90"),
		DEGREE_180("DEGREE_180"),
		DEGREE_270("DEGREE_270"),
		DEGREE_ERROR("DEGREE_ERROR"),
		;
		private String TypeCode;
		
		/**
		 * ����һ������ָ���ַ��ö��ֵ
		 * @param inTypeCode
		 */
		private Orientation_Sensor(String inTypecode){
			this.TypeCode=inTypecode;
		}
		
		@Override
		public String toString(){
			return TypeCode;
		}
		
		public static Orientation_Sensor getMsgType(String inTypeCode){
			for(Orientation_Sensor type:Orientation_Sensor.values()){
				if(type.TypeCode.equals(inTypeCode))
					return type;
			}
			return null;
		}
		
		public static Orientation_Sensor getOrientationSensor(float inclination,float rotation){
			Orientation_Sensor mOrientation_Sensor=Orientation_Sensor.DEGREE_ERROR;
			float inaccuracy = 50;
			float inaccuracy_horizontal = 50;
			float boundary = 0;
			if(inaccuracy >0 && inaccuracy <90){
				if(inclination>0){
					boundary = 90;
					if(boundary-inaccuracy<=inclination && inclination<= boundary+inaccuracy){
						mOrientation_Sensor = Orientation_Sensor.DEGREE_180;
					}
				}else{
					boundary = -90;
					if(boundary-inaccuracy<=inclination && inclination<= boundary+inaccuracy){
						mOrientation_Sensor = Orientation_Sensor.DEGREE_0;
					}
				}
				if(rotation>0){
					boundary = 90;
					if(boundary-inaccuracy_horizontal<=rotation && rotation<= boundary+inaccuracy_horizontal){
						mOrientation_Sensor = Orientation_Sensor.DEGREE_270;
					}
				}else{
					boundary = -90;
					if(boundary-inaccuracy_horizontal<=rotation && rotation<= boundary+inaccuracy_horizontal){
						mOrientation_Sensor = Orientation_Sensor.DEGREE_90;
					}
				}
			}
			return mOrientation_Sensor;
		}
}
