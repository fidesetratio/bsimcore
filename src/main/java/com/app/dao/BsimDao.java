package com.app.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.app.model.bsim.Pas;
/**
 * 
 * @author Patar.Tambunan
 * 
 *
 */

public interface BsimDao {
	
		//select
	 	public Map<String, Object> selectBankPusat(String lsbp_id);
		public Map<String,Object> selectdatapolicypas(String spaj);
		public Map<String,Object> selectMstKartuPas(String premi);
	    public Map<String, Object> selectAgenPenutup(String msag_id);
	    public String selectCekNoKartu(String no_kartu);
	    public HashMap<String, Object> selectDetailKartuPas(String no_kartu);
	  
	   
	    
		public Date selectSysdate();
		public Long select_counter(Map params);
		public String selectGetCounterMonthYear(Map params);
		public String selectBsimNoVaSyariah();
		
		//update
	  	void updateCounterMonthYear(Map params);
		void updateMstCounter(Map params);
		void updateKartuPas(Map params);
		void updateVa(Map params);
		void updatePas(Pas pas);
		
		//insert
		void insertPas(Pas pas);
	  	void insertMstPositionSpajPas(Map params);
	  	
	  	//delete
		void deletePasSMS(String no_kartu);
	  	void deletePositionSpajPas(String msp_fire_id);
	  	
}
