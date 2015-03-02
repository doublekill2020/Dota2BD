
var str00="http://www.jizhuomi.com/android/";
var str01="名称或邮箱不能为空";
var str02="名称或邮箱格式不对";
var str03="留言不能为空或过长";
var str06="显示UBB表情>>";
var intMaxLen="1000";
var strFaceName="Haha|Hehe|Love|Misdoubt|Music|Nothing_to_say|Sad|Shame|Sleep|Smile|Stop|What|Adore|After_boom|Angry|Cool|Cry|Effort|Faint|Grimace";
var strFaceSize="48";
var strBatchView="";
var strBatchInculde="";
var strBatchCount="";

$(document).ready(function(){ 

try{

	$.getScript("http://www.jizhuomi.com/android/function/c_html_js.asp?act=batch"+unescape("%26")+"view=" + escape(strBatchView)+unescape("%26")+"inculde=" + escape(strBatchInculde)+unescape("%26")+"count=" + escape(strBatchCount));

	var objImageValid=$("img[@src^='http://www.jizhuomi.com/android/function/c_validcode.asp?name=commentvalid']");
	if(objImageValid.size()>0){
		objImageValid.css("cursor","pointer");
		objImageValid.click( function() {
				objImageValid.attr("src","http://www.jizhuomi.com/android/function/c_validcode.asp?name=commentvalid"+"&amp;random="+Math.random());
		} );
	};

}catch(e){};

});