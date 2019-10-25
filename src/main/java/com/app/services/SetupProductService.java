package com.app.services;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.dao.VPProductBisnisDao;


@Service
public class SetupProductService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	public List<HashMap<String,Object>> selectVpProductBisnis(int lsbs_id,int lsdbs_number,int paymode, String kurs){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap<>();
		params.put("lsbs_id", lsbs_id);
		params.put("lsdbs_number", lsdbs_number);
		params.put("paymode", paymode);
		params.put("kurs", kurs);
		return (List<HashMap<String,Object>>)dao.selectVpProductBisnis(params);
	}
	
	
	
}
