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
	
	

	public List<HashMap<String,Object>> selectMstLanguangeScripts(int lsbs_id,int lsdbs_number,int paymode, String kurs){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap<>();
		params.put("lsbs_id", lsbs_id);
		params.put("lsdbs_number", lsdbs_number);
		params.put("paymode", paymode);
		params.put("kurs", kurs);
		return (List<HashMap<String,Object>>)dao.selectMstLanguangeScripts(params);
	}
	
	
	public List<HashMap<String,Object>> selectNProdVar(int lsbs_id,int lsdbs_number){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap<>();
		System.out.println("lsbs_id:"+lsbs_id);
		System.out.println("lsdbs_number:"+lsdbs_number);
		
		params.put("lsbs_id", lsbs_id);
		params.put("lsdbs_number", lsdbs_number);
		return (List<HashMap<String,Object>>)dao.selectNProdVar(params);
	}
	
	public List<HashMap<String,Object>> selectNProdFunction(int lsbs_id,int lsdbs_number,int paymode, String kurs){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap<>();
		params.put("lsbs_id", lsbs_id);
		params.put("lsdbs_number", lsdbs_number);
		params.put("cara_bayar", paymode);
		params.put("kurs", kurs);
		return (List<HashMap<String,Object>>)dao.selectNProdFunction(params);
	}
	
	public List<HashMap<String,Object>> selectNProdFunctionOrderBy(int lsbs_id,int lsdbs_number,int paymode, String kurs){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap<>();
		params.put("lsbs_id", lsbs_id);
		params.put("lsdbs_number", lsdbs_number);
		params.put("cara_bayar", paymode);
		params.put("kurs", kurs);
		return (List<HashMap<String,Object>>)dao.selectNProdFunctionOrderBy(params);
	}
	
	public Double selectNilaiNew(int jenis, int lsbs, int lsdbs, String lku, int lscb, int lamaBayar, int lamaTanggung, int tahunKe, int umur){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap();
		params.put("jenis", new Integer(jenis));
		params.put("lsbs", new Integer(lsbs));
		params.put("lsdbs", new Integer(lsdbs));
		params.put("lku", lku);
		params.put("lscb", new Integer(lscb));
		params.put("lamaBayar", new Integer(lamaBayar));
		params.put("lamaTanggung", new Integer(lamaTanggung));
		params.put("tahunKe", new Integer(tahunKe));
		params.put("umur", new Integer(umur));
		return (Double)dao.selectNilaiNew(params);
	}
	
	public Double selectNilai(int jenis, int lsbs, String lku, int lscb, int lamaBayar, int lamaTanggung, int tahunKe, int umur){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap();
		params.put("jenis", new Integer(jenis));
		params.put("lsbs", new Integer(lsbs));
		params.put("lku", lku);
		params.put("lscb", new Integer(lscb));
		params.put("lamaBayar", new Integer(lamaBayar));
		params.put("lamaTanggung", new Integer(lamaTanggung));
		params.put("tahunKe", new Integer(tahunKe));
		params.put("umur", new Integer(umur));
		return (Double)dao.selectNilaiNew(params);
	}
	
	public Double selectPremiSuperSehat(int lsbs, int lsdbs, int umur, String lku){
		VPProductBisnisDao dao=sqlSession.getMapper(VPProductBisnisDao.class);
		HashMap<String,Object> params = new HashMap();
		params.put("lsbs", new Integer(lsbs));
		params.put("lsdbs", new Integer(lsdbs));
		params.put("umur", new Integer(umur));
		params.put("lku", lku);
		return (Double)dao.selectPremiSuperSehat(params);
	}
}
