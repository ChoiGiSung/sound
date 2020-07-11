<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="sound_pack.*" %>
   
   
    
<%request.setCharacterEncoding("UTF-8");
//db가 아니라 여기서 한글이 안됬던거

//여기서 request.getParameter로 안드로이드에서 보낸 값들을 받습니다.
//안드로이드에서 보낸 sendMsg = "id="+strings[0]+"&pwd="+strings[1]; 여기서
// 키값과 request.getParameter안의 값이 같아야 합니다 ㅎㅎ 당연히 타입도 같아야 하구요.


	
	// 다시 안드로이드로 어떠한 값을 보내고 싶을 때는 out.print를 사용하면 됩니다 ㅎㅎ
//	if(id.equals("rain483") && pwd.equals("1234")){
//		out.print("참 true");
//	}else{
//		out.print("거짓 false");
//	}


//싱클톤 방식으로 자바 클래스를 불러온다


	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String type = request.getParameter("type");//로그인 요청인지 회원가입 요청인지를
				//구분하여 메서드를 실행하도록합니다
	
				//사용날짜 받기
	String day_1=request.getParameter("day_1");
	String day_2=request.getParameter("day_2");
	String day_3=request.getParameter("day_3");
	String day_4=request.getParameter("day_4");
	String day_5=request.getParameter("day_5");
	String day_6=request.getParameter("day_6");
	String day_7=request.getParameter("day_7");
			//세팅값 받기
	String seting=request.getParameter("seting");
			//세팅값 get
	String getseting=request.getParameter("getseting");
	
	//싱글톤 방식으로 자바 클래스를 불러옵니다.
	ConnectDB connectDB= ConnectDB.getInstance();
	if(type.equals("login")){
		String returns = connectDB.logindb(id, pwd);
		out.print(returns);
	}else if(type.equals("join")){
		String returns = connectDB.joindb(id, pwd);
		out.print(returns);
		
	}else if(type.equals("usetime")){
		String returns = connectDB.updateUseTime(id, day_1,day_2,day_3,day_4,day_5,day_6,day_7);
		out.print(returns);
	}else if(type.equals("getday")){
		String returns = connectDB.getUsetime(id);
		out.print(returns);
		
	}else if(type.equals("seting")){
		String returns = connectDB.updateSeting(id,seting);
		out.print(returns);
		
	}else if(type.equals("getseting")){
		String returns = connectDB.getSeting(id);
		out.print(returns);
		
	}
	

%>
