package sound_pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {

		//싱글톤 패턴으로 사용하기위한 코드들
		
		private static ConnectDB instance=new ConnectDB();
		
		public static ConnectDB getInstance() {
			return instance;
		}
		
		public ConnectDB() {
			
		}
		
		String jdbcUrl="jdbc:mysql://localhost:3306/sound?serverTimezone=UTC&characterEncoding=euckr";//mysql 계정 //타임존 설정도 해줘야함
		String dbId="root";
		String dbPw="1234";
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		String sql="";
		String sql2="";
		String returns="";
		String returns2="";
		
		public String logindb(String id,String pwd) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
				sql = "select user_id,user_pwd from user where user_id=? and user_pwd=?";
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2,pwd);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getString("user_id").equals(id) && rs.getString("user_pwd").equals(pwd)) {
						returns2 = "true"; //로그인 가능
					}else {
						returns2 = "false"; //로그인 실패
					}
				}else{
					returns2="noId";// 아이디 또는 비밀번호 존재 x
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs !=null)try {rs.close();}catch (Exception e2) {}
				if(pstmt !=null)try {pstmt.close();}catch (Exception e2) {}
				if(conn !=null)try {conn.close();}catch (Exception e2) {}
			}
			return returns2;
		}
		
		public String joindb(String id,String pwd) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn=DriverManager.getConnection(jdbcUrl,dbId,dbPw);
				sql = "select user_id from user where user_id=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getString("user_id").equals(id)) {
						returns = "id";//이미 아이디가 있는 경우
					}
				}else {
					//입력한 아이디가 없는 경우
					sql2 = "insert into user values(?,?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1,id);
					pstmt2.setString(2,pwd);
					pstmt2.executeUpdate(); //새로 넣는건 update
					
					returns = "ok";
					
				}
				
			} catch (Exception e) {e.printStackTrace();}finally {
				if(pstmt != null)try {pstmt.close();} catch (SQLException e) {}
				if(conn != null)try {conn.close();} catch (SQLException e) {}
				if(pstmt2 != null)try {pstmt2.close();} catch (SQLException e) {}
				if(rs != null)try {rs.close();} catch (SQLException e) {}
			}
			return returns;
		}
		
		//이거는 사용시간 업데이트
		public String updateUseTime(String id,String day_1,String day_2,String day_3,String day_4
				,String day_5,String day_6,String day_7) {
			try {
				returns2="";
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
				sql = "select user_id from user where user_id=?";
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getString("user_id").equals(id)) {
						sql2="update user set day_1=?,day_2=?,day_3=?,day_4=?,day_5=?,day_6=?,day_7=? where user_id=?";
						pstmt=conn.prepareStatement(sql2);
						pstmt.setString(1, day_1);pstmt.setString(2, day_2);pstmt.setString(3, day_3);pstmt.setString(4, day_4);
						pstmt.setString(5, day_5);pstmt.setString(6, day_6);pstmt.setString(7, day_7);
						pstmt.setString(8, id);
						pstmt.executeUpdate();
						
						returns2 = "true"; //값 변경
						
					}else {
						returns2 = "false"; //로그인 실패
					}
				}else{
					returns2="noId";// 아이디 또는 비밀번호 존재 x
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs !=null)try {rs.close();}catch (Exception e2) {}
				if(pstmt !=null)try {pstmt.close();}catch (Exception e2) {}
				if(conn !=null)try {conn.close();}catch (Exception e2) {}
			}
			return returns2;
		}
		
		
		//seting값 넣기
		public String updateSeting(String id,String seting) {
			try {
				returns2="";
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn=DriverManager.getConnection(jdbcUrl,dbId,dbPw);
				sql="select user_id from user where user_id=?";
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getString("user_id").equals(id)) {
						sql2="update user set user_seting=? where user_id=?";
						pstmt=conn.prepareStatement(sql2);
						pstmt.setString(1, seting);pstmt.setString(2,id);
						pstmt.executeUpdate();
						
						returns2="true";
					}
				}
				
			} catch (Exception e) {e.printStackTrace();}
			
			return returns2;
		}
		
		//사용시간 얻기
		public String getUsetime(String id) {
			String return_day="";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
				sql = "select day_1,day_2,day_3,day_4,day_5,day_6,day_7 from user where user_id=?";
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					return_day+=rs.getString("day_1");
					return_day+=","+rs.getString("day_2");
					return_day+=","+rs.getString("day_3");
					return_day+=","+rs.getString("day_4");
					return_day+=","+rs.getString("day_5");
					return_day+=","+rs.getString("day_6");
					return_day+=","+rs.getString("day_7");
				}
				
			} catch (Exception e) {e.printStackTrace();}finally {
				if(rs !=null)try {rs.close();}catch (Exception e2) {}
				if(pstmt !=null)try {pstmt.close();}catch (Exception e2) {}
				if(conn !=null)try {conn.close();}catch (Exception e2) {}
			}
	
			return return_day;
		}
		
		//사용시간 얻기
				public String getSeting(String id) {
					String return_seting="";
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						conn = DriverManager.getConnection(jdbcUrl,dbId,dbPw);
						sql = "select user_seting from user where user_id=?";
						
						pstmt=conn.prepareStatement(sql);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							return_seting+=rs.getString("user_seting");
							
						}
						
					} catch (Exception e) {e.printStackTrace();}finally {
						if(rs !=null)try {rs.close();}catch (Exception e2) {}
						if(pstmt !=null)try {pstmt.close();}catch (Exception e2) {}
						if(conn !=null)try {conn.close();}catch (Exception e2) {}
					}
			
					return return_seting;
				}
		
		
		
		
//		//데이터베이스와 통신하기 위한 코드가 들어있는 메서드
//		public String connectionDB(String id,String pwd) {
//			try {
//				Class.forName("com.mysql.cj.jdbc.Driver");
//				//db 연결
//				
//				conn=DriverManager.getConnection(jdbcUrl,dbId,dbPw);
//				sql="select user_id from user where user_id=?";//조회
//				pstmt=conn.prepareStatement(sql);
//				pstmt.setString(1, id);
//				rs=pstmt.executeQuery();
//				
//				if(rs.next()) {
//					//정보를 넣는데 이비 정보가 존재할 경우
//					returns ="회원가입 불가";
//				}else {
//					//넣고자 하는 정보가 없을 경우(회원 가입이 가능한 경우)
//					sql2="insert into user values(?,?)"; //삽입
//					pstmt2 = conn.prepareStatement(sql2);
//					pstmt2.setString(1,id);
//					pstmt2.setString(2, pwd);
//					pstmt2.executeUpdate();
//					returns="횐원가입 가능";
//				}
//				
//			} catch (Exception e) {e.printStackTrace();}finally {
//				if(pstmt2 !=null)try {pstmt2.close();}catch (Exception e2) {}
//				if(pstmt !=null)try {pstmt.close();}catch (Exception e2) {}
//				if(conn !=null)try {conn.close();}catch (Exception e2) {}
//			}
//			
//			return returns;
//		}
		

}
