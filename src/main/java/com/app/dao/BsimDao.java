package com.app.dao;

import java.util.Date;
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
		public Date selectSysdate();
		public Long select_counter(Map params);
		public String selectGetCounterMonthYear(Map params);
		//update
	  	void updateCounterMonthYear(Map params);
		void updateMstCounter(Map params);
		void updateKartuPas(Map params);
		
		//insert
		void insertPas(Pas pas);
	  	void insertMstPositionSpajPas(Map params);
	  	
	  	//delete
		void deletePasSMS(String no_kartu);
	  	void deletePositionSpajPas(String msp_fire_id);
	  	
}
