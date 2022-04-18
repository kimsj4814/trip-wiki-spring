package com.trip.tripwiki.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.User;

@Repository
public class UpdateDAO {
	
	@Autowired
	private SqlSessionTemplate sqlsession;
	
	public int getSearchListCount(Map<String, Object> map) {
	 return sqlsession.selectOne("Update.usercount",map);	
	}
	public int update(User dto) {
		return sqlsession.update("Update.update",dto);
		
	}
	public int delete(String id) {
		return sqlsession.delete("Update.delete", id);
	}
	public List<User> getSearchList(Map<String, Object> map) {
		return sqlsession.selectList("Update.userlist",map);
	
	}
}
