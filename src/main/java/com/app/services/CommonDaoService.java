package com.app.services;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.dao.BsimDao;
import com.app.dao.CommonDao;

@Service
public class CommonDaoService {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	public HashMap selectCommon(String query){
		CommonDao dao=sqlSession.getMapper(CommonDao.class);
		return (HashMap)dao.selectCommon(query);
	}
}
