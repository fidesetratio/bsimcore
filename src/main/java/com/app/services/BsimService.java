package com.app.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.core.ProcessPas;
import com.app.dao.BsimDao;
import com.app.dao.CommonDao;
import com.app.model.bsim.Pas;
import com.app.model.gadget.prod.Cmdeditbac;

@Service
public class BsimService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	public HashMap<String,Object> selectDataPolicyPas(String spaj){
		BsimDao dao=sqlSession.getMapper(BsimDao.class);
		return (HashMap<String,Object>)dao.selectdatapolicypas(spaj);
	}

	public HashMap<String,Object> selectMstKartuPas(String premi){
		BsimDao dao=sqlSession.getMapper(BsimDao.class);
		return (HashMap<String,Object>)dao.selectMstKartuPas(premi);
	}
	
	public Map<String, Object> selectBankPusat(String lsbp_id){
			BsimDao dao=sqlSession.getMapper(BsimDao.class);
			return (HashMap<String,Object>)dao.selectBankPusat(lsbp_id);
	}
	
	public Date selectSysdate(){
		BsimDao dao=sqlSession.getMapper(BsimDao.class);
		return dao.selectSysdate();
}
	
	public String selectBsimNoVaSyariah(){
		BsimDao dao=sqlSession.getMapper(BsimDao.class);
		return dao.selectBsimNoVaSyariah();
}
	public CommonDao getCommonDao(){
		CommonDao dao=sqlSession.getMapper(CommonDao.class);
		return dao;
	}
	
	
	
	 
	 public Pas insert(Pas pas) throws Exception{
		BsimDao dao=sqlSession.getMapper(BsimDao.class);
		TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        String status = null;
        try {
            	Long intIDCounter = (Long) select_counter(113, "01");
            	int intID = intIDCounter.intValue();
            	Calendar tgl_sekarang = Calendar.getInstance(); 
            	Integer inttgl2=new Integer(tgl_sekarang.get(Calendar.YEAR));
            	if (intIDCounter.longValue() == 0)
    			{
    				intIDCounter = new Long ((long)((tgl_sekarang.get(Calendar.YEAR))* 1000000));
    			}else{
    				Integer inttgl1=new Integer(selectGetCounterMonthYear(113, "01"));
    				if (inttgl1.intValue() != inttgl2.intValue())
    				{
    					intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("000000")));
    					//ganti dengan tahun skarang
    					updateCounterMonthYear(inttgl2.toString(), 113, "01");
    					//reset nilai counter dengan 0
    					intID = 0;
    					updateMstCounter2("0", 113, "01");
    					//System.out.println("update mst counter start di tahun baru");
    				}else{
    					intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("000000"))+intIDCounter);
    				}
    			}
        	
            	Long intFireId = new Long(intIDCounter.longValue() + 1);
    			intID = intID + 1;
    			updateMstCounter3(intID, 113, "01");
    			String mspFireId = intFireId.toString();
    			pas.setMsp_fire_id(mspFireId);
    			
    			//input mst_pas_sms
    			dao.insertPas(pas);   
    			
    			
    			
    		
    		//update mst_kartu_pas 
    			
    			if(pas.getNo_kartu() != null){
    				if(!pas.getNo_kartu().trim().equals("")){
    					Map paramsukp = new HashMap();
    		    		paramsukp.put("no_kartu", pas.getNo_kartu());
    		    		paramsukp.put("flag_active", 1);    	
    		    		paramsukp.put("tgl_active", 1); 
    		    		dao.updateKartuPas(paramsukp);
    				}
    					
    			}
	    		
	    		
	    		//input mst_position_spaj_pas
	    		Map paramimpsp = new HashMap();
	    		paramimpsp.put("lus_id", pas.getLus_id().toString());
	    		paramimpsp.put("msps_desc", "NEW ENTRY DATA");
	    		paramimpsp.put("reg_id", pas.getMsp_fire_id());
	    		paramimpsp.put("addSecond", 5);    		
	    		dao.insertMstPositionSpajPas(paramimpsp); 
	    		transactionManager.commit(txStatus);
	    		status = "Data Tersimpan";			
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return pas;
	}
	
	 public PlatformTransactionManager getTransactionManager(){
		 return transactionManager;
	 }
	 
	 
	  public Long select_counter(Integer number , String lca_id) {
		  BsimDao dao=sqlSession.getMapper(BsimDao.class);
		  Long counter = null;
	      Map param = new HashMap();
	      param.put("number",number);
	      param.put("lca_id",lca_id);
	      counter = dao.select_counter(param);
	      return counter;
	    }
	  public String selectGetCounterMonthYear(Integer aplikasi, String cabang) throws DataAccessException {
		  BsimDao dao=sqlSession.getMapper(BsimDao.class);
		  String counter = null;
	      Map params = new HashMap();
	      params.put("aplikasi", new Integer(aplikasi));
	      params.put("cabang", cabang);
	      counter = dao.selectGetCounterMonthYear(params);
	      return counter;
		}  
	  
	  
	  public void updateMstCounter2(String ld_cnt,Integer ai_id,String ls_cabang) throws Exception {
			
		  		BsimDao dao=sqlSession.getMapper(BsimDao.class);
				Map params = new HashMap();
	    		params.put("msco_value",ld_cnt);
	    		params.put("msco_number",new Integer(ai_id));
	    		params.put("lca_id",ls_cabang);
	    		if(Long.valueOf(ld_cnt)<0){
	    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(ld_cnt));
	    		}
	    	    dao.updateMstCounter(params);
	    	
			
		}
	
	  
	  public void updateMstCounter3(Integer ld_cnt,Integer ai_id,String ls_cabang) throws Exception {
			
				BsimDao dao=sqlSession.getMapper(BsimDao.class);
				
	    		Map params = new HashMap();
	    		params.put("msco_value",Long.valueOf(ld_cnt));
	    		params.put("msco_number",new Integer(ai_id));
	    		params.put("lca_id",ls_cabang);
	    		
	    		if(Long.valueOf(ld_cnt)<0){
	    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(ld_cnt));
	    		}
	    	    dao.updateMstCounter(params);
	    }
	  
	  public void updateCounterMonthYear(String nilai, Integer aplikasi, String cabang) throws Exception {
			
				BsimDao dao=sqlSession.getMapper(BsimDao.class);
				
	    		Map params = new HashMap();
	    		params.put("nilai", Long.valueOf(nilai));
	    		params.put("aplikasi", new Integer(aplikasi));
	    		params.put("cabang", cabang);
	    	    
	    		if(Long.valueOf(nilai)<0){
	    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(nilai));
	    		}
	    		dao.updateCounterMonthYear(params);
	    }
	  
	  
		public void update_no_va(String no_va, String no_temp)  throws Exception
		{	
			BsimDao dao=sqlSession.getMapper(BsimDao.class);
			Map param = new HashMap();
			param.put("spaj_temp", no_temp);
			param.put("no_va", no_va);
			dao.updateVa(param);
		}
	    
		
		
		
		
		
}
