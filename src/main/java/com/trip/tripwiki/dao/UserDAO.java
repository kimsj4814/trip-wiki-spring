package com.trip.tripwiki.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.User;

@Repository
public class UserDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int add(User user) {
		// TODO Auto-generated method stub
		return sqlSession.insert("Users.add",user);
	}

	public User idcheck(String id) {
		return sqlSession.selectOne("Users.idcheck",id);
	}
	public User idcheck(String id,String email) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", id);
		map.put("user_email", email);
		return sqlSession.selectOne("Users.idandemailcheck",map);
	}
	public int createAuthkey(String user_id, String authkey) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("user_mail_authkey", authkey);
		return sqlSession.update("Users.update_mail_auth_key", map);
	}
	public int userAuth(String user_email,String user_mail_authkey,String id) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_mail_authkey", user_mail_authkey);
		map.put("user_email", user_email);
		map.put("user_id",id);
		return sqlSession.update("Users.userAuth", map);
	}
	public List<User> MailToEmailGroup(String email){
		return sqlSession.selectList("Users.IdinEmail",email);
	}
	public User KeyselectorForUsers(String key, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", id);
		map.put("user_mail_authkey", key);
		return sqlSession.selectOne("Users.getKey",map);
	}
	public int updatePassword(String user_id,String user_password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("user_password",user_password);
		return sqlSession.update("Users.updatePass",map);
	}

	public int addKakao(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", "kakao"+id);
		return sqlSession.insert("Users.addKakao",map);
	}
}
