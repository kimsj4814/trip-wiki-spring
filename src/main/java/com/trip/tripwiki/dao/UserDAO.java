package com.trip.tripwiki.dao;

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
	
}
