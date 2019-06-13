package com.app.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.app.dao.CommonDao;
import com.app.model.Email;
import com.app.model.sms.Smsserver_out;
import com.msig.utils.Common;

public class DAO_common {
	private CommonDao commonDao;
	 public DAO_common(CommonDao commonDao){
		 this.commonDao = commonDao;
	 }
	 
	 public HashMap selectCommon(String sql){
	    	HashMap value = null;
	    	try {
	    		value = commonDao.selectCommon(sql);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
	    
	    public List<HashMap> selectListCommon(String sql){
	    	List<HashMap> value = null;
	    	try {
	    	value = commonDao.selectListCommon(sql);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
	 
	    public Date selectSysdate(){
	    	
	    	Date date = null;
	    	
	    	try {
	    		date = commonDao.selectSysdate();
	    		//session.commit();
	    	}catch(Exception e){
	    		date = null;
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
	    	return date;
	 
	    }
	    
	    public Timestamp selectSystimestamp(){
	    Timestamp date = null;
	    	
	    	try {
	    		date = commonDao.selectSystimestamp();
	    		//session.commit();
	    	}catch(Exception e){
	    		date = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return date;
	 
	    }
	    
	    public String selectSeqEmailId(){
	    	
	    	String value = null;
	    	
	    	try {
	    		value = commonDao.selectSeqEmailId();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	 
	    }
	    
	    
	    public Map selectMstCounter(Map params){
	    	Map value = null;
	    	
	    	try{
	    		value = commonDao.selectMstCounter(params);
	    	}catch(Exception e){
				value = null;
				}finally {
			 }
	    	return value;
	    }
	    
	    public void updateMstCounter( Map params) throws Exception{
	    	
	    	try {
	    			commonDao.updateMstCounter(params);
//	    		}catch(Exception e){
//	    		if(sqlSession==null)
	    		//if(sqlSession!=null)throw new Exception(e);
	    	}finally {
	    	}
	    }
	    
	    public void insertMstEmail(Email email) throws Exception{
	    	try {
	    		commonDao.insertMstEmail(email);    		
	 
	    	}catch(Exception e){
//	    	}finally {
	    	}
	    }
	    
	    public void insertCommon (String query)throws Exception {
	    	try {
	    			commonDao.insertCommon(query);
	    		} catch (Exception e) {
				} finally {
			}
	    }
	    
	/*    public void insertMstEksternalPrint (EksternalPrint eksternalPrint )throws Exception {
	    	SqlSession session = null;
	    	session = sqlSessionFactory.openSession();
	    	try {
	    		MAP_common mapper = session.getMapper(MAP_common.class);
	    		mapper.insertMstEksternalPrint(eksternalPrint);
	    		session.commit();
	    	} catch (Exception e) {
				session.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
	    }
	    */
	    public Map selectMstUrlSecure(String ID_ENCRYPT_1, String ID_ENCRYPT_2){
	    	Map value = null;
	    	
	    	try{
	    		HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("ID_ENCRYPT_1", ID_ENCRYPT_1);
	        	params.put("ID_ENCRYPT_2", ID_ENCRYPT_2);
	    		
	    		value = commonDao.selectMstUrlSecure(params);
	    		
			}catch(Exception e){
				value = null;
				//session.rollback();
			}finally {
				
		    }
	    	return value;
	    }
	    
	    public HashMap select_freePaDmtm_tmSales(HashMap map){
	    	HashMap value = null;
	    	
	    	try{
	    		value = commonDao.select_freePaDmtm_tmSales(map);
	    	}catch(Exception e){
				value = null;
				//session.rollback();
			}finally {
		    }
	    	return value;
	    }
	    
	    public void deleteMstUrlSecure(String ID_ENCRYPT_1, String ID_ENCRYPT_2){
	    	
	    	try{
	    			HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("ID_ENCRYPT_1", ID_ENCRYPT_1);
	        	params.put("ID_ENCRYPT_2", ID_ENCRYPT_2);  
	        	
	        	commonDao.deleteMstUrlSecure(params);
	    	
			}catch(Exception e){
			
			}finally {
				
		    }
	    }
	    
	    
	    public List<HashMap> select_data_nasabah(String nama, String no_ktp, String bod){

	    	List<HashMap> value = null;
	    	try {
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("CUST_IDENTITY_NUMBER", no_ktp);
	    		param.put("CUST_NAME", nama);
	    		param.put("CUST_BOD", bod);
	    		value = commonDao.select_data_nasabah(param);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
	    
	    public List<HashMap> select_data_nasabah_simpol(String dcif){
	    		List<HashMap> value = null;
	    	try {
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("NO_DCIF", dcif);
	    		value = commonDao.select_data_nasabah_simpol(param);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
	    	return value;
	    }
	    
	    public List<HashMap> select_data_nasabah_simprim(String dcif){
	    	List<HashMap> value = null;
	    	try {
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("NO_DCIF", dcif);
	    		value = commonDao.select_data_nasabah_simprim(param);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
	    
	    public List<HashMap> select_data_nasabah_magna(String dcif){
	    	List<HashMap> value = null;
	    	try {
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("NO_DCIF", dcif);
	    		value = commonDao.select_data_nasabah_magna(param);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
	    
	    public List<HashMap> select_data_nasabah_prime(String dcif){
	    	  	List<HashMap> value = null;
	    	try {
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("NO_DCIF", dcif);
	    		value = commonDao.select_data_nasabah_prime(param);
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
	    
	    public void updateMstPolicyDCIF(String reg_spaj, String no_dcif) throws Exception{
	    	
	    	try{
	    	HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("REG_SPAJ", reg_spaj);
	        	params.put("DCIF", no_dcif);
	    		
commonDao.updateMstPolicyDCIF(params);
	    	}catch(Exception e){
				e.printStackTrace();
			}finally {
		    }
	    }
	    
	    public void insertAksesHist(HashMap map) throws Exception{
	    	
	    		
	    	try {
	    		
	    		commonDao.insertAksesHist(map);
	    		
	    		}catch(Exception e){
//	    		if(sqlSession==null)
	    		e.printStackTrace();
	    	}finally {
	    	
	    	}
	    }
	    
	    public void LstHistActivityWs(int client_id, int process_id, String process_desc, 
	    		String process_result, String method, String msah_ip, String err, Date StartDate){
	    	try {
		    	HashMap<String, Object> s_params = new HashMap<String, Object>();
				s_params.put("CLIENT_ID", client_id);
				s_params.put("PROCESS_ID", process_id);
				s_params.put("PROCESS_DESC",process_desc.replaceAll("[\r\n]+", " "));
				s_params.put("PROCESS_RESULT", process_result);
				s_params.put("METHOD", (method==null || "".equals(method))?"":((method.equals("GET"))?0:1));
				s_params.put("MSAH_IP", msah_ip);
				s_params.put("ERR", err);
				s_params.put("START_DATE", StartDate);
				insertLstHistActvWs(s_params);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    public void insertLstHistActvWs(HashMap map) throws Exception{
	    	
	    	try {
	    		
	    		commonDao.insertLstHistActvWs(map);
	    		
	    	
	    	}catch(Exception e){
//	    	
	    	}finally {
	    	}
	    }
	    
	    public void insertLstHistActvWsOut(HashMap map) throws Exception{
	    	
	    	try {
	    		
	    		commonDao.insertLstHistActvWsOut(map);
	    
	    	}catch(Exception e){
	    	}finally {
	    	
	    	}
	    }

		public Integer selectLoginProposal(String kode, String web) {
		Integer count = 0;
	    	
	    	try {
	    		
	    		HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("kode", kode);
	        	params.put("web", web);
	        	
	    		count = commonDao.selectLoginProposal(params);
	    	
	    	}catch(Exception e){
	    		//session.rollback();
	    	}finally {
	    	}
	    	
			return count;
		}

		public String selectSeqUrlSecureId() {
			
	    	String id = "";
	    	
	    	try {
	    	id = commonDao.selectSeqUrlSecureId();
	    		//session.commit();
	    	}catch(Exception e){
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
			return id;
		}

		public void insertMstUrlSecure(String sec_1, String sec_2,
				String encrypt_1, String encrypt_2, String kode, String link) {
			
	    	try {
	    		
	    		HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("sec_1", sec_1);
	        	params.put("sec_2", sec_2);
	        	params.put("encrypt_1", encrypt_1);
	        	params.put("encrypt_2", encrypt_2);
	        	params.put("kode", kode);
	        	params.put("link", link);
	    		
	        	commonDao.insertMstUrlSecure(params);
	    		
	    		
	    	}catch(Exception e){
//	    		if(sqlSession==null)
	    		
	    		//e.printStackTrace();
	    	}finally {
	    		
	    	}
		}

		public String selectEncrypt(String desc) {
			
	    	String encrypt = "";
	    	
	    	try {
	    		encrypt = commonDao.selectEncrypt(desc);
	    		//session.commit();
	    	}catch(Exception e){
	    		//session.rollback();
	    	}finally {
	    	}
	    	
			return encrypt;
		}
		/**
		* @author eriza
		* @since Okt 15, 2015
		* @description untuk login espaj 
		*/
		public ArrayList<HashMap<String, Object>> selectLstUserExternal(String username) {
			ArrayList<HashMap<String, Object>> result = null;

			try {
				result = commonDao.selectLstUserExternal(username);
			} catch (Exception e) {
			} finally {
			
			}
			return result;
		}
		/*public UserEspaj selectLoginAuthenticationFirst(UserEspaj userEspaj) {
			SqlSession session = null;
	    	session = sqlSessionFactory.openSession();

			try {
				MAP_common mapper = session.getMapper(MAP_common.class);
				userEspaj = mapper.selectLoginAuthenticationFirst(userEspaj);
			} catch (Exception e) {
			} finally {
				session.close();
			}
			return userEspaj;
		}	*/
	/*	public UserEspaj selectLoginAuthenticationNextMobile(UserEspaj userEspaj) {
			SqlSession session = null;
	    	session = sqlSessionFactory.openSession();

			try {
				MAP_common mapper = session.getMapper(MAP_common.class);
				userEspaj = mapper.selectLoginAuthenticationNextMobile(userEspaj);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			return userEspaj;
		}*/
		public ArrayList<HashMap<String, Object>> selectAgentDataForMobile(String username) {
			ArrayList<HashMap<String, Object>> result = null;

			try {
				result = commonDao.selectAgentDataForMobile(username);
			} catch (Exception e) {
			} finally {
			}
			return result;
		}
		
		  
	    public List<HashMap> selectLstPekerjaan(){
	    		List<HashMap> value = null;
	    	try {
	    		value = commonDao.selectLstPekerjaan();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
		  
	    public List<HashMap> selectLstJabatan(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.selectLstJabatan();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectLstPropinsi(){
	    		List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectLstPropinsi();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectLstQuestion(){
	   	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectLstQuestion();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectLstLabelQuestion(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectLstLabelQuestion();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectProdukBancass(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectProdukBancass();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectProdukRider(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectProdukRider();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	  	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectProdukDropdown(){
	    		List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectProdukDropdown();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	  	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectPaket(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectPaket();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectBankPusat(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectBankPusat();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectBankCabang(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectBankCabang();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectFund(){
	    	List<HashMap> value = null;
	    	try {
	   		value = commonDao.SelectFund();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectRegionalID(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectRegionalID();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectTypeProduct(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectTypeProduct();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectRateRider(){
	    	List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectRateRider();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
		
	    public List<HashMap> SelectFaktorUP(){
	    		List<HashMap> value = null;
	    	try {
	    		value = commonDao.SelectFaktorUP();
	    		//session.commit();
	    	}catch(Exception e){
	    		value = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return value;
	    }
	    
	    public void insertSmsserverout(Smsserver_out sms_out) throws Exception{
	    		
	    	
	    	try {
	    		commonDao.insertSmsserverout(sms_out);
	    	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally {
	    	
	    	}
	    }
	    
	    public List<HashMap> selectTotalPremi(String beg_date, String end_date) {
	    	List<HashMap> result = null;
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("beg_date", beg_date);
			params.put("end_date", end_date);
			
			try {
	    		result = commonDao.selectTotalPremi(params);
			} catch(Exception e) {
//				e.printStackTrace();
			} finally {
			}
			
			return result;
		}
	    
	    public List<HashMap> selectDetailPremi(String beg_date, String end_date) {
	    	List<HashMap> result = null;
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("beg_date", beg_date);
			params.put("end_date", end_date);
			
			try {
	    	result = commonDao.selectDetailPremi(params);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
			}
			
			return result;
		}
	    
	    public HashMap<String, Object> selectMstConfig(Integer appId, String section, String subSection) {
	        HashMap<String, Object> result = null;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("app_id", appId);
	        params.put("section", section);
	        params.put("sub_section", subSection);
	        
	        try {
	           result = commonDao.selectMstConfig(params);
	        } catch (Exception e) {
	            result = null;
	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
	    
	    public void insertMstSpajCrt(Map map) throws Exception{
	    	
	    	try {
	    		commonDao.insertMstSpajCrt(map);
	    	}catch(Exception e){
//	    		if(sqlSession==null)
	    		e.printStackTrace();
	    	}finally {
	    	}
	    }
	    
	    public int selectAccessClient(String client_key, String las_id) {
			
	    	int count = 0;
	    	
	    	try {
	    	
	    		HashMap<Object, String> params = new HashMap<Object, String>();
	        	params.put("client_key", client_key);
	        	params.put("las_id", las_id);
	        	
	    		count = commonDao.selectAccessClient(params);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally {
	    	}
	    	
			return count;
		}
	    
	    public HashMap<String, Object> SelectVersion(int flag_app, int flag_platform){
	       HashMap<String, Object> result = null;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("flag_app", flag_app);
	        params.put("flag_platform", flag_platform);
	    	try {
	    	result = commonDao.SelectVersion(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		result = null;
	    		//session.rollback();
	    	}finally {
	    
	    	}
	    	
	    	return result;
	    }
	    
	    public HashMap<String, Object> selectSpajBeingProcessed(String spaj) {
	        HashMap<String, Object> result = null;
	        
	        try {
	            result = commonDao.selectSpajBeingProcessed(spaj);
	        } catch (Exception e) {
	            result = null;
//	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
	    
	   /* public boolean isProduction() {
	        SqlSession session = sqlSessionFactory.openSession();
	        boolean result = false;
	        
	        try {
	            if (session != null && session.getConnection().getMetaData().getURL().contains("jdbc:oracle:thin:@128.21.22.31:1522:ajsdb")) {// AJSDB
	                result = true;
	            }
	        } catch (Exception e) {
	            result = false;
	        } finally {
	            session.close();
	        }
	        
	        return result;
	    }*/
	    
	    public ArrayList<HashMap> selectProductBisnis(String spaj) {
	   	ArrayList<HashMap> result = null;
	    	try {
	    		result = commonDao.selectProductBisnis(spaj);
	    	} catch (Exception e) {
	    		result = null;
	    	}finally {
	    	}
	    	return result;
	    }
	
}
