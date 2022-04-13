package com.trip.tripwiki.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.trip.tripwiki.dao.UserDAO;
import com.trip.tripwiki.domain.AuthorizationKakao;
import com.trip.tripwiki.domain.User;



@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private Oauth2Kakao oauth2Kakao;
	@Autowired
	private UserDAO dao;
	
	@Value("${my.savefolder}")
	private String uploadFolder;
	
	@Override
	public User isId(String id) {
		return dao.idcheck(id);
	}//isId String end;
	@Override
	public int isId(String id, String password) {
		// TODO Auto-generated method stub
		User user = dao.idcheck(id);
		if(user == null) {
			return -1;
		}else {
			if(user.getUser_password().equals(password)) {
				if(user.getUser_mail_auth() == 0) {
					return 2;
				}else {
					return 1;
				}
			}else {
				return 0;
			}
		}
	}//isID end
	@Override
	public int add(User user) {
		user.setUser_profile_original("default.png");
		File file = new File(uploadFolder + File.separator + 
				user.getUser_profile_original());
		return dao.add(user);
	}//addUser end
	@Override
	public int changePassword(String id, String password) {
		return dao.updatePassword(id, password);
	}
	@Override
	public String getKakaoAccessToken(String code) {
		AuthorizationKakao authorization = oauth2Kakao.callTokenApi(code);
		String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorization.getAccess_token());
		return userInfoFromKakao;
	}
	
//	@Override
//	public String getKakaoAccessToken(String code) {
//		 	String access_Token="";
//	        String refresh_Token ="";
//	        String reqURL = "https://kauth.kakao.com/oauth/token";
//	        try{
//	            URL url = new URL(reqURL);
//	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//	            conn.setRequestMethod("POST");
//	            conn.setDoOutput(true);
//	            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//	            BufferedWriter bw = new BufferedWriter(
//	            		new OutputStreamWriter(conn.getOutputStream()));
//	            StringBuilder sb = new StringBuilder();
//	            sb.append("grant_type=authorization_code");
//	            sb.append("&client_id=7766f2940612dd165b60ff116edf4541"); //TODO REST_API_KEY 입력
//	            sb.append("&redirect_uri=http://localhost:8081/trip/auth"); //TODO 인가코드 받은 redirect_uri 입력
//	            sb.append("&code=" + code);
//	            bw.write(sb.toString());
//	            bw.flush();
//	            //결과 코드가 200이라면 성공
//	            int responseCode = conn.getResponseCode();
//	            System.out.println("responseCode : " + responseCode);
//	            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//	            BufferedReader br = new BufferedReader(
//	            		new InputStreamReader(conn.getInputStream()));
//	            String line = "";
//	            String result = "";
//	            while ((line = br.readLine()) != null) {
//	                result += line;
//	            }
//	            System.out.println("response body : " + result);
//	            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//	            JsonParser parser = new JsonParser();
//	            JsonElement element = parser.parse(result);
//	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//	            System.out.println("access_token : " + access_Token);
//	            System.out.println("refresh_token : " + refresh_Token);
//	            br.close();
//	            bw.close();
//	        }catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        return access_Token;
//	}

//	@Override
//	public String isNickname(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String chkNickname(String id) {
//		// TODO Auto-generated method stub
//		return "";	
//	}


	
}
