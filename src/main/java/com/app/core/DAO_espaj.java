package com.app.core;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;


import com.app.dao.CommonDao;
import com.app.model.esehat.DropDownMenu;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Agentrec;
import com.app.model.gadget.prod.Benefeciary;
import com.app.model.gadget.prod.Biayainvestasi;
import com.app.model.gadget.prod.Checklist;
import com.app.model.gadget.prod.Cmdeditbac;
import com.app.model.gadget.prod.CommandChecklist;
import com.app.model.gadget.prod.ContactPerson;
import com.app.model.gadget.prod.Datarider;
import com.app.model.gadget.prod.Datausulan;
import com.app.model.gadget.prod.DetailPembayaran;
import com.app.model.gadget.prod.DetilInvestasi;
import com.app.model.gadget.prod.Employee;
import com.app.model.gadget.prod.InvestasiUtama;
import com.app.model.gadget.prod.PembayarPremi;
import com.app.model.gadget.prod.Pemegang;
import com.app.model.gadget.prod.Personal;
import com.app.model.gadget.prod.PesertaPlus_mix;
import com.app.model.gadget.prod.Rekening_client;
import com.app.model.gadget.prod.RencanaPenarikan;
import com.app.model.gadget.prod.Simas;
import com.app.model.gadget.prod.Tertanggung;
import com.app.model.gadget.prod.User;
import com.app.model.gadget.sub.ProfileRisk;
import com.app.model.table.MstAccountRecurTemp;
import com.app.model.table.MstAddressBillingTemp;
import com.app.model.table.MstAddressNewTemp;
import com.app.model.table.MstBenefeciaryTemp;
import com.app.model.table.MstClientNewTemp;
import com.app.model.table.MstDaftarInvestasiTemp;
import com.app.model.table.MstKeluargaTemp;
import com.app.model.table.MstKycTemp;
import com.app.model.table.MstPesertaTemp;
import com.app.model.table.MstPremiTemp;
import com.app.model.table.MstProductTemp;
import com.app.model.table.MstQuestionAnswerTemp;
import com.app.model.table.MstReffBiiTemp;
import com.app.model.table.MstRekClientTemp;
import com.app.model.table.MstSpajTemp;
import com.app.utils.CommonUtil;
import com.app.utils.FormatString;
import com.app.utils.f_hit_umur;
import com.msig.utils.Common;

public class DAO_espaj {
	
	 private CommonDao commonDao;
	 public DAO_espaj(CommonDao commonDao){
		 this.commonDao = commonDao;
	 }
	
	    public Map selectGetNewNoVirtualAccount(Map params){
	    	
	    	Map map = null;
	    	
	    	try {
	    		map = (HashMap) commonDao.selectGetNewNoVirtualAccount(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
	    	return map;
	 
	    }
	    
	        public List<Map> selectGetNewNoVirtualAccountList(Map params){
	    	
	    	List map = null;
	    	
	    	try {
	    		map = (List) commonDao.selectGetNewNoVirtualAccountList(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return map;
	 
	    }
	    
	    public Map selectCategoryBisnis(Map params){
	    	Map map = null;
	    	
	    	try {
	    		map = (HashMap) commonDao.selectCategoryBisnis(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    		
	    	}
	    	
	    	return map;
	 
	    }
	    
	    public void updateMstDetVaNoVa( Map params) throws Exception{
	    	
	    	
	    	try {
	    		commonDao.updateMstDetVaByNoVa(params);
	    		
	    	}catch(Exception e){
//	    		
	    	}finally {
	    		
	    	}
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
		
	
		public Map selectProdukBank(String no_temp) {
			Map map = null;
	    	
	    	try {
	    		map = (HashMap) commonDao.selectProdukBank(no_temp);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return map;
		}
		
		public Map selectDataReffBank(Map params) {
			Map map = null;
	    	
	    	try {
	    	map = (HashMap) commonDao.selectDataReffBank(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    	}
	    	
	    	return map;
		}
		
		public int selectNoGadget(String no_gadget) {
	         int result = 0;
	        try {
	    		result = commonDao.selectNoGadget(no_gadget);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
		
		public HashMap<String, Object> selectbyNoGadget(String no_gadget) {
	         HashMap<String, Object> result = new HashMap<String, Object>();
	        
	        try {
	           result = commonDao.selectbyNoGadget(no_gadget);
	        } catch (Exception e) {
//	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
		
		/**
		 *@author Ryan
		 * @throws Exception 
		 *@since Sept 21, 2015
		 *@description TODO
		 *//*
		public int InsertSpajToDB(Map dataSpaj) throws Exception{
			int result = 0;
			MstSpajTemp mstSpajTemp =(MstSpajTemp) dataSpaj.get("mstSpajTemp");
			List<MstKeluargaTemp> listMstKeluargaTemp = (List<MstKeluargaTemp>) dataSpaj.get("listMstKeluargaTemp");
			List<MstProductTemp> listMstProductTemp = (List<MstProductTemp>) dataSpaj.get("listMstProductTemp");
			List<MstKycTemp> listMstKycTemp = (List<MstKycTemp>) dataSpaj.get("listMstKycTemp");
			MstAddressBillingTemp mstAddressBillingTemp = (MstAddressBillingTemp) dataSpaj.get("mstAddressBillingTemp");
			List<MstBenefeciaryTemp> listMstBenefeciaryTemp = (List<MstBenefeciaryTemp>) dataSpaj.get("listMstBenefeciaryTemp");
			List<MstBiayaUlinkTemp> listMstBiayaUlinkTemp = (List<MstBiayaUlinkTemp>) dataSpaj.get("listMstBiayaUlinkTemp");
			List<MstDaftarInvestasiTemp> listMstDaftarInvestasiTemp = (List<MstDaftarInvestasiTemp>) dataSpaj.get("listMstDaftarInvestasiTemp");
			List<MstPesertaTemp> listMstPesertaTemp= (List<MstPesertaTemp>) dataSpaj.get("listMstPesertaTemp");
			MstAccountRecurTemp mstAccountRecurTemp = (MstAccountRecurTemp) dataSpaj.get("mstAccountRecurTemp");
			MstRekClientTemp mstRekClientTemp = (MstRekClientTemp) dataSpaj.get("mstRekClientTemp");
			List<MstKuisionerTemp> listMstKuisionerTemp = (List<MstKuisionerTemp>) dataSpaj.get("listMstKuisionerTemp");
			List<MstPremiTemp> listMstPremiTemp = (List<MstPremiTemp>) dataSpaj.get("listMstPremiTemp");
			List<MstClientNewTemp> listClientNewTemp = (List<MstClientNewTemp>) dataSpaj.get("lstClientNewTemp");
			List<MstQuestionAnswerTemp> listQuestionAnswerTemp = (List<MstQuestionAnswerTemp>) dataSpaj.get("listMstQuestionAnswerTemp");
			List<MstAddressNewTemp> listAddrNewTemp = (List<MstAddressNewTemp>) dataSpaj.get("lstAddrNewTemp");
			BigDecimal counter = (BigDecimal) dataSpaj.get("msco_value");
			String no_temp = (String) dataSpaj.get("no_temp");
			String va = (String) dataSpaj.get("no_va");
			String gadget_spaj_key = (String) dataSpaj.get("gadget_spaj_key");
			
			DAO_common dao_common = new DAO_common();
			DAO_espaj dao_espaj = new DAO_espaj();
			Date sysdate = dao_common.selectSysdate();
			
			SqlSession session = sqlSessionFactory.openSession();
			
			try{
				MAP_common mapper = session.getMapper(MAP_common.class);
				
				
				Map valueUpdateVa = new HashMap();
				valueUpdateVa.put("flag_active", 1);
				valueUpdateVa.put("spaj_temp", no_temp);
				valueUpdateVa.put("input_date", sysdate);
				valueUpdateVa.put("user_input", 0);
				mapper.updateCommon(Database.generateQuery_UPDATE(Sql.UPDATE, "eka.mst_det_va", valueUpdateVa, "no_va = '"+va+"'").toString());
				mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_spaj_temp", mstSpajTemp, false).toString());
				for(int i = 0 ; i < listMstKeluargaTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_keluarga_temp", listMstKeluargaTemp.get(i),false).toString());
				for(int i = 0 ; i < listMstProductTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_product_temp", listMstProductTemp.get(i),false).toString());
				for(int i = 0 ; i < listMstKycTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_kyc_temp", listMstKycTemp.get(i),false).toString());
				mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_address_billing_temp", mstAddressBillingTemp,false).toString());
				for(int i = 0 ; i < listMstBenefeciaryTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_benefeciary_temp", listMstBenefeciaryTemp.get(i),false).toString());
				for(int i = 0 ; i < listMstDaftarInvestasiTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_daftar_invest_temp", listMstDaftarInvestasiTemp.get(i),false).toString());
				for(int i = 0 ; i < listMstPesertaTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_peserta_temp", listMstPesertaTemp.get(i),false).toString());
				for(int i = 0 ; i < listMstPremiTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_premi_temp", listMstPremiTemp.get(i),false).toString());
				mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_rek_client_temp", mstRekClientTemp,false).toString());
				mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_account_recur_temp", mstAccountRecurTemp,false).toString());
				for(int i = 0 ; i < listClientNewTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_client_new_temp", listClientNewTemp.get(i),false).toString());
				for(int i = 0 ; i < listQuestionAnswerTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_question_answer_temp", listQuestionAnswerTemp.get(i),false).toString());
				for(int i = 0 ; i < listAddrNewTemp.size() ; i++)
					mapper.updateCommon(Database.generateQuery_INSERT("eka.mst_address_new_temp", listAddrNewTemp.get(i),false).toString());
				session.commit();
				result = 1;
	    	}catch(Exception e){
	    		session.rollback();
	    		e.printStackTrace();
	    	}finally {
	    		session.close();
	    	}
			return result;
			
		}*/
		
		public HashMap<String, Object> selectDataProposal(String no_temp) {
	        HashMap<String, Object> result = new HashMap<String, Object>();
	        
	        try {
	            result = commonDao.selectDataProposal(no_temp);
	        } catch (Exception e) {
//	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
	    
		public ArrayList<HashMap<String, Object>> selectDataCrp(String no_temp) {
	        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
	        try {
	            result = commonDao.selectDataCrp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
		
	    public Pemegang selectppTemp(String no_temp) {
	       Pemegang result = null;
	        try {
	            result = commonDao.selectppTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public AddressBilling selectAddressBillingTemp(String no_temp) {
	        AddressBilling result = null;
	        try {
	            result = commonDao.selectAddressBillingTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public Tertanggung selectttgTemp(String no_temp) {
	       Tertanggung result = null;
	        try {
	            result = commonDao.selectttgTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public PembayarPremi selectPemPremiTemp(String no_temp) {
	       PembayarPremi result = null;
	        try {
	            result = commonDao.selectPemPremiTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public Account_recur selectAccRecurTemp(String no_temp) {
	        Account_recur result = null;
	        try {
	            result = commonDao.selectAccRecurTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public Datausulan selectMstProductTemp(String no_temp) {
	        Datausulan result = null;
	        try {
	            result = commonDao.selectMstProductTemp(no_temp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	        return result;
	    }
	    
	    public List<Datarider> selectMstProductTempRider(String no_temp) {
	        List<Datarider> result = null;
	            
	        try {
	            result = commonDao.selectMstProductTempRider(no_temp);
	        } catch(Exception e) {
//	          e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
	    
	    public List<PesertaPlus_mix> selectPesertaTemp(String no_temp) {
	       List<PesertaPlus_mix> result = null;
	            
	        try {
	            result = commonDao.selectPesertaTemp(no_temp);
	        } catch(Exception e) {
//	          e.printStackTrace();
	        } finally {
	        }
	        
	        return result;
	    }
	    
	    public List<Benefeciary> select_benef_temp(String no_temp) {
	        List<Benefeciary> result = null;
	            
	        try {
	            result = commonDao.select_benef_temp(no_temp);
	        } catch(Exception e) {
//	          e.printStackTrace();
	        } finally {
	         }
	        
	        return result;
	    }
	    
	      public String selectIdLstPekerjaan(String namaPekerjaan) {
	            String result = null;
	                    
	            try {
	                result = commonDao.selectIdLstPekerjaan( namaPekerjaan );
	            } catch (Exception e) {
//	              e.printStackTrace();
	            } finally {
	          
	            }
	            
	            return result;
	        }
	    
	      public Integer selectCountKeluarga(String no_temp) {
	            Integer result = null;
	            
	            try {
	               result =commonDao.selectCountKeluarga(no_temp);
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = null;
	            } finally {
	             }
	            
	            return result;
	        }
	      
	      public Agen select_detilagen2(String no_temp) {
	            Agen result = null;
	            try {
	               result = commonDao.select_detilagen2(no_temp);
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	         
	            }
	            
	            if(result == null) return new Agen(); else return result;           
	        }
	      
	      public double selectSumPremiTemp(String no_temp) {
	            double result = 0.0;
	            
	            try {
	                result = commonDao.selectSumPremiTemp( no_temp );
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = 0.0;
	            } finally {
	            }
	            
	            return result;
	        }
	      
	      public Rekening_client select_rek_client_temp(String no_temp) {
	            Rekening_client result = null;
	            try {
	               result = commonDao.select_rek_client_temp(no_temp);
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	            }
	            
	            if(result == null) return new Rekening_client(); else return result;            
	        }  
	      
	      
	      public List<DetilInvestasi> selectDaftarInvestasiTemp(String no_temp) {
	            List<DetilInvestasi> result = null;
	                
	            try {
	               result = commonDao.selectDaftarInvestasiTemp(no_temp);
	            } catch(Exception e) {
	                e.printStackTrace();
	            } finally {
	           }
	            
	            return result;
	        }
	      
	      public String selectkodegutri(String nama, String tanggal, CommonDao mapper) throws Exception {
	            String result = null;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("nama", nama);
	            params.put("tanggal", tanggal);         
	            try {
	                result = mapper.selectkodegutri( params );
	            } catch (Exception e) {
	               throw e;
	            } 
	            return result;
	        }   
	      
	      public HashMap<String, Object> selectRegionalAgen(String kodeagen, CommonDao mapper) {
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("kodeagen", kodeagen);
	                    
	            return mapper.selectRegionalAgen( params );
	        }
	      
//	      public Long selectCounter(String strBranch) {
//	            SqlSession sqlSession = sqlSessionFactory.openSession();
//	            Long result = null;
//	            HashMap<String, Object> params = new HashMap<String, Object>();
//	            params.put("kodecbg", strBranch);           
//	            try {
//	                MAP_espaj mapper = sqlSession.getMapper(MAP_espaj.class);
//	                result = mapper.selectCounter( params );
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	                result = null;
//	            } finally {
//	                sqlSession.close();
//	            }           
//	            return result;
//	        }
	      
	       public void updateMstCounter(Long intIDCounter, String strBranch) throws Exception{
	           HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("intIDCounter", intIDCounter);
	            params.put("strBranch", strBranch);             
	            try {
	                commonDao.commonupdateMstCounter(params);
	             }catch(Exception e){
//	              if(sqlSession==null)
	                e.printStackTrace();
	                throw new Exception(e);
	            }finally {
	            }
	        }
	       
	       
	       public void insertMstAgentTmp(String strTmpSPAJ, String v_stragentnama, CommonDao mapper) throws Exception{ 
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("strTmpSPAJ", strTmpSPAJ);
	            params.put("v_stragentnama", v_stragentnama);
	            mapper.insertMstAgentTmp(params);               
	        }
	       
	       public Long selectCounterClient(String gc_strTmpBranch) {
	            Long result = null;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("kodecbg", gc_strTmpBranch);
	            
	            try {
	                result = commonDao.selectCounterClient( params );
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = null;
	            } finally {
	               
	            }
	            
	            return result;
	        }      
	       
	       public void updateMstCounterClient(Long intIDCounter, String strBranch) throws Exception{                        
	         
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("intIDCounter", intIDCounter);
	            params.put("strBranch", strBranch);             
	            try {
	                commonDao.updateMstCounterClient(params);
	                //if(sqlSession==null)session.commit();
	            }catch(Exception e){
//	              if(sqlSession==null)
	               e.printStackTrace();
	            }finally {
	           }
	        }
	       
	       public String selectKeteranganKerja(String lsp_id) {
	           String result = null;       
	            try {
	               result = commonDao.selectKeteranganKerja( lsp_id );
	            } catch (Exception e) {
//	              e.printStackTrace();
	            } finally {
	             
	            }
	            
	            return result;
	        }
	       
	       
	        public Integer updateClientTtg(Tertanggung tertanggung,  CommonDao mapper) {        
	            Integer result = 0;     
	            result = mapper.updateClientTtg(tertanggung);
	            return result;
	        }
	        
	        public Integer updateMstAddressTtg(Tertanggung tertanggung,  CommonDao mapper)throws Exception {        
	            Integer result = 0;     
	            try {
	                result = mapper.updateMstAddressTtg(tertanggung);
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }           
	            return result;
	        }
	        
	        public void insertMstAddressTtg(Tertanggung tertanggung,  CommonDao mapper) {       
	             mapper.insertMstAddressTtg(tertanggung);
	        }
	        
	        public Integer insertMstClientTtg(Tertanggung tertanggung, CommonDao mapper) {
	            Integer result = 0;
	            result = mapper.insertMstClientTtg(tertanggung);
	            return result;
	        }
	        
	        
	        public List<DropDownMenu> selectBidangUsaha(String flag, CommonDao mapper) {
	            List<DropDownMenu> result = null;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("flag", flag);
	            result = mapper.selectBidangUsaha( params );        
	            return result;
	        }
	        
	        
	        public Integer updateMstClientPP(Pemegang pemegang, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.updateMstClientPP(pemegang);
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	        
	        public Integer insertMstClientPP(Pemegang pemegang, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.insertMstClientPP( pemegang );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }
	            return result;
	        }
	        
	        
	        public Integer updateMstAddressPP(Pemegang pemegang, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.updateMstAddressPP(pemegang);
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	        
	        public Integer insertMstAddressPP(Pemegang pemegang, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.insertMstAddressPP( pemegang );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }           
	            return result;
	        }
	        
	        public Integer updateMstCompany(Personal personal, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.updateMstCompany(personal);
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	        
	        public Integer insertMstCompany(Personal personal, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.insertMstCompany( personal );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }
	            return result;
	        }
	        
	        public Integer updateMstCompanyAddress(Pemegang pemegang, CommonDao mapper)throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.updateMstCompanyAddress( pemegang );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }
	            return result;
	        }
	                
	        public Integer insertMstCompanyAddress(Pemegang pemegang, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.insertMstCompanyAddress( pemegang );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	        
	        public Integer selectLineBusLstBisnis(String lsbs_id) {
	                Integer result = null;
	                
	                try {
	                    result = commonDao.selectLineBusLstBisnis(lsbs_id);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    result = null;
	                } finally {
	                }
	                
	                return result;
	         }
	        
	        //proc_save_mst_policy
	        public Integer updateMstPolicy(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao mapper) throws Exception{
	            Integer result = 0;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("pemegang", pemegang);
	            params.put("tertanggung", datausulan);
	            params.put("datausulan", datausulan);
	            params.put("agen", agen);           
	            try {
	                result = mapper.updateMstPolicy( params );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	        
	        public Integer insertMstPolicy(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao mapper) throws Exception {
	            Integer result = 0;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("pemegang", pemegang);
	            params.put("tertanggung", datausulan);
	            params.put("datausulan", datausulan);
	            params.put("agen", agen);           
	            try {
	                result = mapper.insertMstPolicy( params );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }           
	            return result;
	        }
	                
	    public void updateLeadReffBii(String mspo_plan_provider, String strTmpSPAJ, CommonDao mapper) throws Exception{             
	                HashMap<String, Object> params = new HashMap<String, Object>();
	                params.put("kode", mspo_plan_provider);
	                params.put("spaj", strTmpSPAJ); 
	                mapper.updateLeadReffBii(params);           
	    }       
	        
	    public Integer selectMstTransHist(String reg_spaj, CommonDao mapper) throws Exception {
	            Integer result = 0;         
	            try {
	                result = mapper.selectMstTransHist(reg_spaj);
	            } catch (Exception e) {
	                result = 0;
	                throw e;
	            }
	            return result;
	     }      
	        
	    public void updateMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao mapper) throws Exception {
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("reg_spaj", reg_spaj);
	        params.put("kolom_tgl", kolom_tgl);
	        params.put("tgl", tgl);
	        params.put("kolom_user", kolom_user);
	        params.put("lus_id", lus_id);
	        mapper.updateMstTransHistory(params);           
	    }   
	    
	    public void insertMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao mapper) throws Exception {
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("reg_spaj", reg_spaj);
	        params.put("kolom_tgl", kolom_tgl);
	        params.put("tgl", tgl);
	        params.put("kolom_user", kolom_user);
	        params.put("lus_id", lus_id);
	        mapper.insertMstTransHistory(params);       
	    }   
	    
	    public void updateBungaMstPolicy(Pemegang pemegang, CommonDao mapper) throws Exception {
	        mapper.updateBungaMstPolicy( pemegang );
	    }   
	    
	    public void deleteMstKyc(String spaj, String kyc_id, String kyc_pp, CommonDao mapper) throws Exception {
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("spaj", spaj);
	        params.put("kyc", kyc_id);
	        params.put("kyc_pp", kyc_pp);    
	        mapper.deleteMstKyc( params );          
	    }   
	    
	    
	    public void insertKyc(String strTmpSPAJ,Integer noUrut,String kycId,String kycPp,String kycDesc, CommonDao mapper) throws Exception {
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("reg_spaj", strTmpSPAJ);
	        params.put("no_urut",noUrut);
	        params.put("kyc_id",kycId);
	        params.put("kyc_pp",kycPp);
	        params.put("kyc_desc", kycDesc);
	        mapper.insertKyc( params );     
	    }   

	    public void updateMonitoringSpaj(String no_blanko, Integer jenis, String type, Date tgl_kembali_ke_agen, Date tgl_terima_dari_agen, String jenis_further, String keterangan_further,String msag_id,String nama_pemegang, String emailcc, CommonDao mapper) {
	        Map param = new HashMap();
	        param.put("value", no_blanko);
	        param.put("jenis", jenis);      
	        param.put("type", type);
	        param.put("tgl_kembali_ke_agen", tgl_kembali_ke_agen);
	        param.put("tgl_terima_dari_agen", tgl_terima_dari_agen);
	        param.put("jenis_further", jenis_further);
	        param.put("keterangan_further", keterangan_further);        
	        param.put("msag_id", msag_id);
	        param.put("nama_pemegang", nama_pemegang);
	        param.put("emailcc", emailcc);
	        mapper.updateMonitoringSpaj( param );
	    }
	    
	    public Integer updateMstWorksiteFlag(Pemegang pemegang, CommonDao mapper)throws Exception {
	        Integer result = 0;
	        try {
	            result = mapper.updateMstWorksiteFlag(pemegang);
	        }catch(Exception e) {
	            result = 0;
	            throw e;            
	        }   
	        return result;
	    }   
	    
	    public void insertMstWorksiteFlag(Pemegang pemegang, CommonDao mapper) throws Exception {   
	            mapper.insertMstWorksiteFlag(pemegang);
	    }   
	    
	    
	     public Long select_Counter(Integer number, String lca_id) {
	            Long result = null;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("number", number);
	            params.put("lca_id", lca_id);
	            try {
	                result = commonDao.select_Counter( params );
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = null;
	            } finally {
	            }           
	            return result;
	        }
	        
	    
	     public void update_Counter(Long IDCounter, String lca_id, Integer number) throws Exception {
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("IDCounter", IDCounter);
	            params.put("lca_id", lca_id);
	            params.put("number", number);
	            try {
	                commonDao.update_Counter( params );
	          
	            } catch(Exception e) {
	               
	            } finally {
	                
	            }   
	    }        
	     
	     public List<DropDownMenu> selectJenisForm() {
	            List<DropDownMenu> result = null;                   
	            try {
	                result = commonDao.selectJenisForm();
	            } catch(Exception e) {
//	              e.printStackTrace();
	            } finally {
	              
	            }
	            
	            return result;
	        }
	     
	    public Integer update_no_blanko(String no_blanko, String spaj, Integer lsjs_id, CommonDao mapper) throws Exception
	    {
	        Integer result = 0;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("no_blanko", no_blanko);
	        params.put("spaj", spaj);
	        params.put("lsjs_id", lsjs_id);     
	        try {
	            result = mapper.update_no_blanko( params );
	        } catch(Exception e) {
	            result = 0;
	            throw e;
	        }       
	        return result;
	    } 
	     
	    public Integer updateMstInsured(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao mapper) throws Exception {
	        Integer result = 0;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("pemegang", pemegang);
	        params.put("tertanggung",tertanggung);
	        params.put("datausulan",datausulan);
	        params.put("agen",agen);
	        try {
	            result = mapper.updateMstInsured( params );
	            //if(sqlSession==null)session.commit();
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertMstInsured(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao mapper) throws Exception {
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("pemegang", pemegang);
	        params.put("tertanggung",tertanggung);
	        params.put("datausulan",datausulan);
	        params.put("agen",agen);        
	        mapper.insertMstInsured( params );      
	    }   
	    
	    public void deleteMstKeluarga(String lsbs_id, CommonDao mapper) throws Exception {    
	            mapper.deleteMstKeluarga( lsbs_id );        
	    }       
	        
	    public void insertMstKeluarga(String strTmpSPAJ, String nama, Integer lsre_id, Date tanggal_lahir, Integer insured,Integer no, CommonDao mapper) throws Exception {
	        HashMap<String, Object> param = new HashMap<String, Object>();
	        param.put("strTmpSPAJ", strTmpSPAJ);
	        param.put("nama", nama);
	        param.put("lsre_id", lsre_id);
	        param.put("tanggal_lahir", tanggal_lahir );
	        param.put("insured", insured);
	        param.put("no", no);
	        mapper.insertMstKeluarga( param );      
	    }
	    
	    public void insert_Mst_Keluarga( HashMap<String, Object> params, CommonDao mapper) throws Exception {
	            mapper.insertMstKeluarga( params );         
	    }
	    
	    public Integer updateMstKeluarga( HashMap<String, Object> params, CommonDao mapper ) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateMstKeluarga( params );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public Integer updateNewClientPayerBadanHukum(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateNewClientPayerBadanHukum( pembayarPremi );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   

	    public void insertNewClientPayerBadanHukum(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {    
	            mapper.insertNewClientPayerBadanHukum( pembayarPremi );     
	    }   
	    
	    public Integer updateAddressPemPremiIndividu(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateAddressPemPremiIndividu( pembayarPremi );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertAddressPemPremiBadanHukum(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {
	            mapper.insertAddressPemPremiBadanHukum( pembayarPremi );        
	    }       
	    
	    public void updateMstPolicyPayer( HashMap<String, Object> params, CommonDao mapper ) throws Exception {
	            mapper.updateMstPolicyPayer( params );      
	    }   
	        
	    public Integer updateNewClientPayerIndividu(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateNewClientPayerIndividu( pembayarPremi );          
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertNewClientPayerIndividu(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {   
	            mapper.insertNewClientPayerIndividu( pembayarPremi );           
	    }   
	    
	    public void insertAddressPemPremiIndividu(PembayarPremi pembayarPremi, CommonDao mapper) throws Exception {    
	            mapper.insertAddressPemPremiIndividu( pembayarPremi );          
	    }       
	        
	    public Integer updateMstClientPic(ContactPerson contactPerson, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateMstClientPic( contactPerson );            
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	        
	    public void insertMstClientPic(ContactPerson contactPerson, CommonDao mapper) throws Exception {    
	            mapper.insertMstClientPic( contactPerson );         
	    }
	    
	    public Integer updateMstAddressPic(ContactPerson contactPerson, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateMstAddressPic( contactPerson );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   

	    public void insertMstAddressPic(ContactPerson contactPerson, CommonDao mapper) throws Exception {
	            mapper.insertMstAddressPic( contactPerson );            
	    }
	        
	    public void deleteMstCompanyContactFamily(String strPOClientID, CommonDao mapper) throws Exception {        
	            mapper.deleteMstCompanyContactFamily( strPOClientID );      
	    }       
	    
	    public void inserMstCompanyContactFamily( Map param1, CommonDao mapper) throws Exception {      
	            mapper.inserMstCompanyContactFamily( param1 );          
	    }   
	        
	    public Agen selectMstAgent( Map param1 , CommonDao mapper) throws Exception {
	        Agen result = null;
	        try {
	            result = mapper.selectMstAgent( param1 );
	        }catch(Exception e){            
	          //  throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertMstAgentProd( Agen agen,CommonDao mapper ) throws Exception {
	            mapper.insertMstAgentProd( agen );          
	    }
	    
	     public Integer selectIsAgenCorporate(String kdAgen) {
	            Integer result = null;          
	            try {
	                 result = commonDao.selectIsAgenCorporate( kdAgen );
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = null;
	            } finally {
	            }           
	            return result;
	        }
	    
	     public Integer selectIsAgenKaryawan(String kdAgen) {
	            Integer result = null;          
	            try {
	                result = commonDao.selectIsAgenKaryawan( kdAgen );
	            } catch (Exception e) {
	                e.printStackTrace();
	                result = null;
	            } finally {
	            }           
	            return result;
	        }
	    
	     public void insertMstAgentComm(Agen agen, CommonDao mapper) throws Exception {
	         mapper.insertMstAgentComm( agen );         
	    }
	     
	     
	     public void insertMstAgentArtha( Agen agen, CommonDao mapper ) throws Exception {          
	                mapper.insertMstAgentArtha( agen );             
	    }
	     
	     public List<Map<String, Object>> selectAgentRekruter( String kdAgen, CommonDao mapper) throws Exception {
	         List<Map<String, Object>> result = null;
	            try {
	                result = mapper.selectAgentRekruter( kdAgen );
	            }catch(Exception e){
	                throw e;        
	            }
	            return result;       
	     }
	     
	     public void insertMstAgentRekruter( Agen agen, CommonDao mapper) throws Exception {
	            mapper.insertMstAgentRekruter( agen );          
	    }
	     
	     public HashMap<String, Object> selectAgentCodeAO(String selectAgentCodeAO) {
	            HashMap<String, Object> result = null;    
	            try {
	                result = commonDao.selectAgentCodeAO( selectAgentCodeAO );
	            } catch (Exception e) {
	            	e.printStackTrace();
	            } finally {
	            }
	            
	            return result;
	        }
	     
	     public HashMap<String, Object> selectAgentCodeAO(String selectAgentCodeAO,CommonDao mapper) {
	            HashMap<String, Object> result = null;    
	            try {
	                result = mapper.selectAgentCodeAO( selectAgentCodeAO );
	             
	                
	            } catch (Exception e) {
//	              e.printStackTrace();
	            } finally {
	            }
	            
	            return result;
	        }
	     
	    public void insertMstAgentBa(Agen agen, CommonDao mapper) throws Exception {        
	        mapper.insertMstAgentBa( agen );
	    }
	    
	    public Integer selectKabupaten(String nama_wilayah, CommonDao mapper) throws Exception
	    {
	        Integer result = 0;         
	        try {
	            result = mapper.selectKabupaten( nama_wilayah );
	        } catch (Exception e) {
	            result = 0;
	          //  throw e;
	        }      
	        return result;
	    }
	    
	    public Integer updateMstAddressBilling(AddressBilling addressBilling, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateMstAddressBilling( addressBilling );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertMstAddressBilling(AddressBilling addressBilling, CommonDao mapper) throws Exception {
	            mapper.insertMstAddressBilling( addressBilling );       
	    }
	    
	    public Integer updateMstRekClient(Rekening_client rekening_client, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.updateMstRekClient( rekening_client );
	        }catch(Exception e){
	            result = 0;
	            throw e;        
	        }
	        return result;
	    }   
	    
	    public void insertMstRekClient(Rekening_client rekening_client, CommonDao mapper) throws Exception {
	            mapper.insertMstRekClient( rekening_client );           
	    }
	    
	    public void insertMstRekClientHist(Rekening_client rekening_client, CommonDao mapper) throws Exception {        
	            mapper.insertMstRekClientHist( rekening_client );       
	    }
	    
	    
	    public void insertMstAccountRecur(Account_recur account_recur, CommonDao mapper) throws Exception {
	            mapper.insertMstAccountRecur( account_recur );          
	    }
	    
	    
	    public void insertMstPositionSpaj(String lus_id, String msps_desc, String reg_spaj, int addSecond, CommonDao mapper) throws DataAccessException{
	            Map p = new HashMap();
	            p.put("lus_id", lus_id);
	            p.put("msps_desc", msps_desc);
	            p.put("reg_spaj", reg_spaj);
	            p.put("addSecond", addSecond);          
	            mapper.insertMstPositionSpaj( p );
	    }
	    
	    public void updateMstInsuredTglAdmin(String spaj,Integer insuredNo, Date tanggal,Integer show, CommonDao mapper) throws DataAccessException{
	            Map param = new HashMap();
	            param.put("spaj", spaj);
	            param.put("insuredNo", insuredNo);
	            param.put("show", show);
	            param.put("tanggal", tanggal);
	            mapper.updateMstInsuredTglAdmin( param );       
	    }
	    
	    
	    public void insertMst_position_no_spaj_pb(String spaj, String lus_id, int lspd, Integer lssp, String desc,Integer count,  CommonDao mapper) throws DataAccessException {
	            Map params = new HashMap();
	            params.put("strTmpSPAJ", spaj);
	            params.put("lus_id", lus_id);
	            params.put("lspd", new Integer(lspd));
	            params.put("lssp", lssp);
	            params.put("desc", desc);
	            params.put("tgl", "SYSDATE+0.0000"+count);    
	            mapper.insertMst_position_no_spaj_pb(params);           
	    }   
	    
	    public void insertMstPositionSpajRedFlag(String lus_id, String msps_desc, String reg_spaj, int addSecond,String jenis, CommonDao mapper) throws DataAccessException{
	        Map p = new HashMap();
	        p.put("lus_id", lus_id);
	        p.put("msps_desc", msps_desc);
	        p.put("reg_spaj", reg_spaj);
	        p.put("addSecond", addSecond);
	        p.put("jenis", jenis);
	        mapper.insertMstPositionSpajRedFlag(p);     
	    }
	    
	    public void insertMst_sts_client(String mcl_id, CommonDao mapper) {
	        Map param=new HashMap();    
	        param.put("strInsClientID", mcl_id);        
	        mapper.insertMst_sts_client(param);         
	    }
	    
	     public String selectAddmonths(Map param27, CommonDao mapper) throws Exception {
	            String result = null;
	            try {
	                result = mapper.selectAddmonths(param27);
	            } catch (Exception e) {         
	                throw e;
	            }      
	            return result;
	       }
	     
	     public void inserMst_product_insured45(Map param28, CommonDao mapper) {          
	                mapper.inserMst_product_insured45(param28);          
	      }
	     
	     public void insertMst_product_insuredPA(Map param28, CommonDao mapper) {
	                mapper.inserMst_product_insured45(param28);
	      }
	     
	     public void inserMst_product_insured1(Map param28, CommonDao mapper) {        
	                mapper.inserMst_product_insured1(param28);             
	      }
	     
	     
	     public void insertMst_product_insured_rider(Map param28, CommonDao mapper) {     
	                mapper.insertMst_product_insured_rider(param28);         
	      }
	         
	    public Double sum_premi ( String spaj )
	    {
	           Double result = null;
	            try {
	               result = commonDao.sum_premi(spaj);
	            } catch (Exception e) {
//	              e.printStackTrace();
	            } finally {
	             
	            }           
	            return result;  
	    }
	     
	    public void insertmst_deposit(DetailPembayaran detailPembayaran) {
	        try {
	             commonDao.insertmst_deposit( detailPembayaran );
	        } catch(Exception e) {
	        } finally {
	        }       
	    }
	    
	     public Map<String, Object> selectNamaPlan(Map param , CommonDao mapper) throws Exception {
	            Map<String, Object> result = null;    
	            try {
	                result = mapper.selectNamaPlan( param );
	            } catch (Exception e) {
	                throw e;
	            }           
	            return result;
	        }
	     
	     public void insertMstEmp(Employee employee) {
	            try {
	                 commonDao.insertMstEmp( employee );
	            } catch(Exception e) {
	            } finally {
	            }       
	        }
	    
	     public Integer update_mst_ulink(InvestasiUtama investasiUtama, CommonDao mapper) throws Exception {
	            Integer result = 0;    
	            try {
	                result = mapper.update_mst_ulink( investasiUtama );
	            }catch(Exception e){
	                result = 0;
	                throw e;        
	            }
	            return result;
	       }    
	        
	    public void insert_mst_ulink(InvestasiUtama investasiUtama, CommonDao mapper) throws Exception {        
	            mapper.insert_mst_ulink( investasiUtama );          
	    }
	        
	    public void insertFixed(Map param, CommonDao mapper) throws Exception {   
	            mapper.insertFixed( param );            
	    }
	    
	    public void insertMst_biaya_ulink(Map param, CommonDao mapper) throws Exception {       
	            mapper.insertMst_biaya_ulink( param );      
	    }

	    public void insertMst_benefeciary(Benefeciary benefeciary, CommonDao mapper) throws Exception {     
	            mapper.insertMst_benefeciary( benefeciary );            
	    }       

	     
	    public void insertMst_rencana_penarikan(RencanaPenarikan rencanaPenarikan, CommonDao mapper) throws Exception {
	        mapper.insertMst_rencana_penarikan( rencanaPenarikan );     
	    }
	    
	    public void insert_mst_peserta_plus_mix(PesertaPlus_mix pesertaPlus_mix, CommonDao mapper) throws Exception {
	        mapper.insert_mst_peserta_plus_mix( pesertaPlus_mix );      
	    }   
	    
	    public void updateSpajTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	        Map p = new HashMap();
	        p.put("no_temp", no_temp);
	        p.put("reg_spaj", reg_spaj);    
	        mapper.updateMstSpajTemp(p);        
	    }
	    
	    public void updateProductTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	        Map p = new HashMap();
	        p.put("no_temp", no_temp);
	        p.put("reg_spaj", reg_spaj);
	        mapper.updateMstPesertaTemp(p);     
	    }
	    
	    public void updateAddressBillingTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	        Map p = new HashMap();
	        p.put("no_temp", no_temp);
	        p.put("reg_spaj", reg_spaj);    
	        mapper.updateMstAddressBillingTemp(p);      
	    }
	    
	    public void updatePesertaTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	        Map p = new HashMap();
	        p.put("no_temp", no_temp);
	        p.put("reg_spaj", reg_spaj);
	        mapper.updateMstPesertaTemp(p);     
	    }
	    
	     public Integer selectCountQuestionaireTemp(String no_temp, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.selectCountQuestionaireTemp( no_temp );
	        }catch(Exception e){
	            result = 0;
	            throw e;
	        }
	        return result;
	       }    
	    
	     public Integer selectCountMedquestTemp(String no_temp, CommonDao mapper) throws Exception {
	        Integer result = 0;    
	        try {
	            result = mapper.selectCountMedquestTemp( no_temp );
	        }catch(Exception e){
	            result = 0;
	            throw e;    
	        }
	        return result;
	       }
	    
	     public Integer selectCountbenefTemp(String no_temp, CommonDao mapper) throws Exception {
	            Integer result = 0;    
	            try {
	                result = mapper.selectCountbenefTemp( no_temp );
	            }catch(Exception e){
	                result = 0;
	                throw e;        
	            }
	            return result;
	       }    
	    
	     public String selectCountReffBiiTemp(String no_temp, CommonDao mapper) throws Exception {
	        String result = "";    
	        try {
	            result = mapper.selectCountReffBiiTemp( no_temp );
	        }catch(Exception e){
	            throw e;            
	        }
	        return result;
	       }
	     
	     public void copyMedquestTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);        
	            mapper.insertMedQuestFromTemp(p);           
	        }
	    
	     public void copyReffBiiTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);        
	            mapper.insertReffBiiFromTemp(p);            
	    }
	     
	     public void copyQuestionaireTemp(String no_temp,String reg_spaj, CommonDao mapper) throws DataAccessException{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);            
	            mapper.insertQuestionaireFromTemp(p);
	     }
	     
	     
	     public void updateMstDetVa(Map paramx, CommonDao mapper) throws DataAccessException{
	            mapper.updateMstDetVa(paramx);              
	    }
	         
	     
	    
	     
	    public void proc_save_rekening(Cmdeditbac edit,String strTmpSPAJ,Integer kode_flag, CommonDao mapper)throws Exception
	        {
	            Integer kode_account=edit.getDatausulan().getFlag_account();
	            Integer v_intAutoDebet = edit.getDatausulan().getMste_flag_cc();
	            String v_pil_invest = null;
	            if (kode_flag.intValue() == 1)
	            {
	                Integer rollover = edit.getPowersave().getMps_roll_over();
	                v_pil_invest = Integer.toString(rollover.intValue());
	            }
	            if (kode_account.intValue() == 2 || kode_account.intValue() ==3 ) 
	            {
	                if (kode_flag.intValue() != 1) 
	                {
//	                  insert mst rek client dan mst rek hist
	                    proc_save_rek_client(edit,strTmpSPAJ, mapper);
	                    
	                }else{
	                    if (v_pil_invest.equalsIgnoreCase("2"))
	                    {
//	                      insert mst rek client dan mst rek hist
	                        proc_save_rek_client(edit,strTmpSPAJ, mapper );
	                    }else{
	                        String v_strAcctHolder1 = edit.getRekening_client().getMrc_no_ac().toUpperCase();
	                        String v_bank1 = edit.getRekening_client().getLsbp_id();
	                        String atasnama1 = edit.getRekening_client().getMrc_nama().toUpperCase();
	                        String cabang_bank = edit.getRekening_client().getMrc_cabang().toUpperCase();
	                        String kota_rek = edit.getRekening_client().getMrc_kota().toUpperCase();
	                        if (!v_strAcctHolder1.equalsIgnoreCase("") || !v_bank1.equalsIgnoreCase("") || !atasnama1.equalsIgnoreCase("") || !cabang_bank.equalsIgnoreCase("") || !kota_rek.equalsIgnoreCase(""))
	                        {
//	                          insert mst rek client dan mst rek hist
	                            proc_save_rek_client(edit,strTmpSPAJ, mapper);
	                        }
	                    }
	                }
	                
	                if ((kode_account.intValue() ==3) ||(v_intAutoDebet.intValue()==1) || (v_intAutoDebet.intValue()==2))
	                {
	                    proc_save_account_recur(edit,strTmpSPAJ, mapper);
	                    //System.out.println("insert account recur");                       
	                }

	            }else{
	                if ((v_intAutoDebet.intValue()==1) || (v_intAutoDebet.intValue()==2) || (v_intAutoDebet.intValue()==9))
	                {
	                    proc_save_account_recur(edit,strTmpSPAJ, mapper);
	                    //System.out.println("insert account recur");
	                }
	            }
	        }
	        
	        
	        private void proc_save_rek_client(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            String keterangan = edit.getRekening_client().getNotes();
	            String v_strregionid = edit.getAgen().getKode_regional().toUpperCase();
	            Integer jns_nsbh = null;
	            if((FormatString.rpad("0",(v_strregionid.substring(0,4)),4).equalsIgnoreCase("0914"))){
	                jns_nsbh = edit.getRekening_client().getMrc_jn_nasabah();
	            }
	            edit.getRekening_client().setMrc_jn_nasabah(jns_nsbh);

	            if(keterangan == null){
	                keterangan = "";
	            }
	            if(keterangan.equalsIgnoreCase("")){
	                keterangan = "Input Rekening Client";
	            }
	            edit.getRekening_client().setNotes(keterangan);
	            edit.getRekening_client().setReg_spaj(strTmpSPAJ);
	            edit.getRekening_client().setLus_id(edit.getPemegang().getLus_id());
	            
	            String kode_va = "";
	            if(!CommonUtil.isEmpty(edit.getTertanggung().getMste_no_vacc())){
	                kode_va = edit.getTertanggung().getMste_no_vacc().substring(0, 4);
	            }
	            if(("8006,8093".indexOf(kode_va)>-1)){
	                //BSM
	                edit.getRekening_client().setLsbp_va("156");
	            }else if(("8076,8066".indexOf(kode_va)>-1)){
	                //BSM Syariah
	                edit.getRekening_client().setLsbp_va("224");
	            }else if(("7720,7721".indexOf(kode_va)>-1)){
	                //Bank Harda
	                edit.getRekening_client().setLsbp_va("144");
	            }
	            
	            int rowupdated =  updateMstRekClient( edit.getRekening_client(), mapper);       
	            //edit mst rek_client
	            if (rowupdated==0)
	            {
	                insertMstRekClient(edit.getRekening_client(), mapper);
	                //System.out.println("insert rek cliet");
	            }       
	          //  insertMstRekClientHist(edit.getRekening_client(), mapper);
	            //System.out.println("insert rek cliet hist");
	        }
	        
	        
	        private void proc_save_account_recur(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper)throws ServletException,IOException
	        {
	            
	            edit.getAccount_recur().setReg_spaj(strTmpSPAJ);
	            edit.getAccount_recur().setMar_jenis(edit.getDatausulan().getMste_flag_cc());
	            edit.getAccount_recur().setLus_id(edit.getPemegang().getLus_id());
	            
	            try {
	                insertMstAccountRecur( edit.getAccount_recur(), mapper);
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        
	        
	        public void proc_save_addr_bill(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            String v_strBillRegion=edit.getAddressbilling().getRegion();
	            edit.getAddressbilling().setReg_spaj(strTmpSPAJ);
	            if(edit.getAddressbilling().getKota() != null) {
	                edit.getAddressbilling().setLska_id(selectKabupaten(edit.getAddressbilling().getKota().toUpperCase().trim(), mapper ));
	            }
	            if(v_strBillRegion != null) {
	                edit.getAddressbilling().setLca_id(FormatString.rpad("0",(v_strBillRegion.substring(0,2)),2));
	                edit.getAddressbilling().setLwk_id(FormatString.rpad("0",(v_strBillRegion.substring(2,4)),2));
	                edit.getAddressbilling().setLsrg_id(FormatString.rpad("0",(v_strBillRegion.substring(4,6)),2));
	            }
	            
	            edit.getAddressbilling().setKota(edit.getAddressbilling().getKota_tgh());
	            edit.getAddressbilling().setFlag_cc(edit.getDatausulan().getMste_flag_cc());
	            //------------------------------------------------------------
	            // Insert Insured information to MST_ADDRESS_BILLING
	                int rowupdated = updateMstAddressBilling( edit.getAddressbilling(), mapper );           
//	              System.out.println("update mst billing");
	                if (rowupdated ==0)
	                {
	                    insertMstAddressBilling( edit.getAddressbilling(), mapper );
	                    //System.out.println("insert mst billing");
	                }
	        }
	        
	        
	        
	        public void proc_save_data_ttg (Cmdeditbac edit, String strInsClientID, Date v_strDateNow, CommonDao mapper )throws Exception
	        {
	            
	            
	            String tujuan_asr_ttg =null;//--
	            String sumber_hasil_ttg=null;//--
	            
	            edit.getTertanggung().setMkl_smbr_penghasilan((sumber_hasil_ttg));
	                    
	            edit.getTertanggung().setMkl_kerja_ket(edit.getTertanggung().getMkl_kerja_ket());       
	            edit.getTertanggung().setMkl_dana_from(edit.getPemegang().getMkl_dana_from());
	            edit.getTertanggung().setMkl_hasil_from(edit.getPemegang().getMkl_hasil_from());
	            edit.getTertanggung().setMkl_smbr_hasil_from(edit.getPemegang().getMkl_smbr_hasil_from());
	            edit.getTertanggung().setMkl_sumber_premi(edit.getPemegang().getMkl_sumber_premi());
	            edit.getTertanggung().setLsre_id_premi(edit.getPemegang().getLsre_id_premi());
	            
	            edit.getTertanggung().setLus_id(edit.getPemegang().getLus_id());
	            edit.getTertanggung().setMcl_id(strInsClientID);
	            edit.getTertanggung().setMcl_jenis(new Integer(0));
	            edit.getTertanggung().setMcl_blacklist(new Integer(0));
	            edit.getTertanggung().setMcl_first_alias(edit.getPemegang().getMcl_first_alias());
	            edit.getTertanggung().setMcl_company_name(edit.getTertanggung().getMcl_company_name());
	            edit.getTertanggung().setMkl_kerja_ket(edit.getTertanggung().getMkl_kerja_ket());
	            
	            if (edit.getPemegang().getLsre_id().intValue() == 1)
	            {
	                edit.getTertanggung().setEmail(edit.getPemegang().getEmail());
	            }
	            edit.getTertanggung().setMspe_email(edit.getTertanggung().getEmail());              
	            
	            int rowupdated = updateClientTtg( edit.getTertanggung(), mapper);
	            if (rowupdated ==0)
	            {
	                insertMstClientTtg( edit.getTertanggung(), mapper );
	                //System.out.println("insert mst client ttg");
	            }
	            
	            // Insert Insured Client information to MST_ADDRESS_NEW
	            int rowupdated1 = updateMstAddressTtg( edit.getTertanggung(), mapper);
	            if (rowupdated1 ==0)
	            {
	                insertMstAddressTtg( edit.getTertanggung(), mapper );
	                //System.out.println("insert mst address ttg");
	            }
	        }
	        
	        public void proc_save_data_pp(Cmdeditbac edit, String strPOClientID,Date v_strDateNow, CommonDao mapper  )throws Exception
	        {           
	            //pengiriman polis
	            //Pekerjaan
	            edit.getPemegang().setMkl_kerja(selectKeteranganKerja(edit.getPemegang().getMkl_kerja()));
	            edit.getPemegang().setMspo_korespondensi(edit.getPemegang().getMspo_korespondensi());
	            
	            edit.getPemegang().setMcl_id(strPOClientID);
	            if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
	                edit.getPemegang().setMcl_jenis(new Integer(1));
	            }else{
	                edit.getPemegang().setMcl_jenis(new Integer(0));
	            }
	            edit.getPemegang().setMcl_blacklist(new Integer(0));
	            edit.getPemegang().setMspe_email(edit.getPemegang().getEmail());
	            edit.getPemegang().setMkl_dana_from(edit.getPemegang().getMkl_dana_from());
	            edit.getPemegang().setMkl_hasil_from(edit.getPemegang().getMkl_hasil_from());
	            edit.getPemegang().setMkl_smbr_hasil_from(edit.getPemegang().getMkl_smbr_hasil_from());
	            edit.getPemegang().setMkl_sumber_premi(edit.getPemegang().getMkl_sumber_premi());
	            edit.getPemegang().setLsre_id_premi(edit.getPemegang().getLsre_id_premi());
	            
	            //TAMBAHAN UNTUK INPUT BADAN HUKUM / USAHA
//	            edit.getPersonal().setMcl_id(edit.getPemegang().getMcl_id());
//	            edit.getPersonal().setFlag_ws(0);
//	            edit.getPersonal().setLca_id(edit.getAgen().getLca_id());
//	            edit.getPersonal().setLwk_id(edit.getAgen().getLwk_id());
//	            edit.getPersonal().setLsrg_id(edit.getAgen().getLsrg_id());
//	            edit.getPersonal().setLsrg_nama(edit.getAgen().getLsrg_nama());
//	            edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
//	            edit.getPersonal().setMcl_first(edit.getPemegang().getMcl_first());
	            //List<DropDown> gelar = ((List<DropDown>)query("selectGelar", 1));
	            Map<String, String> params = new HashMap<String, String>();
	                    
//	            List<DropDownMenu> bidangUsaha = (List<DropDownMenu>) selectBidangUsaha("0", mapper);
//	            if(bidangUsaha != null){
//	                for(int i = 0 ; i < bidangUsaha.size() ; i++){
//	                    if((bidangUsaha.get(i).getValue().toUpperCase()).equals(edit.getPemegang().getMkl_industri())){
//	                        edit.getPersonal().setLju_id(Integer.parseInt(bidangUsaha.get(i).getKey()));
//	                        i = bidangUsaha.size();
//	                    }
//	                }
//	            }
//	            
//	            edit.getPersonal().setMpt_usaha_desc(edit.getPemegang().getMkl_industri());
//	            edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
	            if(edit.getDatausulan().getJenis_pemegang_polis() == 1){        
	                edit.getPemegang().setLside_id(7);//
	                edit.getPemegang().setMspe_no_identity(edit.getPersonal().getMpt_npwp());
	                edit.getPemegang().setAlamat_kantor(edit.getPemegang().getAlamat_rumah());
	                edit.getPemegang().setArea_code_kantor(edit.getPemegang().getArea_code_rumah());
	                if("-".equals(edit.getPemegang().getArea_code_kantor()))edit.getPemegang().setArea_code_kantor("");
	                edit.getPemegang().setArea_code_kantor2(edit.getPemegang().getArea_code_rumah2());
	                edit.getPemegang().setTelpon_kantor(edit.getPemegang().getTelpon_rumah());
	                if("-".equals(edit.getPemegang().getTelpon_kantor()))edit.getPemegang().setTelpon_kantor("");
	                edit.getPemegang().setTelpon_kantor2(edit.getPemegang().getTelpon_rumah2());
	                edit.getPemegang().setKd_pos_kantor(edit.getPemegang().getKd_pos_rumah());
	                edit.getPemegang().setKota_kantor(edit.getPemegang().getKota_rumah());
	                edit.getPemegang().setMcl_first_alias(edit.getPemegang().getMcl_first_alias());
	                edit.getPemegang().setMcl_company_name(edit.getPemegang().getMcl_company_name());
	                edit.getPemegang().setMkl_kerja_ket(edit.getPemegang().getMkl_kerja_ket());
	            }
	            
	            /*------------------------------------------------------------
	             Insert Policy Holder Client information to MST_CLIENT*/    
	                int rowupdated = updateMstClientPP( edit.getPemegang(), mapper );
	                if (rowupdated ==0)
	                {
	                    insertMstClientPP( edit.getPemegang(), mapper );
	                    //System.out.println("insert mst client PP");
	                }
	                
	                //------------------------------------------------------------
	                // Insert Policy Holder Home Address information to MST_ADDRESS
//	                  System.out.println("update mst address pp");                
	                int rowupdated1 = updateMstAddressPP( edit.getPemegang(), mapper);
	                if (rowupdated1 ==0)
	                {
	                    insertMstAddressPP( edit.getPemegang(), mapper );
	                    //System.out.println("insert mst client PP");
	                }
	                
	                /*------------------------------------------------------------
	                 Insert Policy Holder Company information to MST_COMPANY*/
	                    if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
	                        int rowupdated2 = updateMstCompany( edit.getPersonal(), mapper );
	                        //System.out.println("update mst client pp");
	                        if (rowupdated2==0)
	                        {
	                            insertMstCompany( edit.getPersonal(), mapper );
	                            //System.out.println("insert mst company pp");
	                        }
	                    }
	                    
	                    //Insert Policy Holder Home Address Company information to MST_ADDRESS_NEW
	                    if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
	                        int rowupdated3 = updateMstCompanyAddress( edit.getPemegang(), mapper );
	                        //System.out.println("update mst client pp");
	                        if (rowupdated3==0)
	                        {
	                            insertMstCompanyAddress( edit.getPemegang(), mapper );
	                            //System.out.println("insert mst company pp");
	                        }
	                    }       
	        }
	        
	        private void proc_save_mst_policy(  Cmdeditbac edit, String strPOClientID,String strTmpSPAJ,Date v_strDateNow , String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao mapper)throws Exception
	        {
	            DateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");          
	            DAO_common dao_common = new DAO_common(commonDao);
	            
	            try {
	                //****** mst policy ****************
	                edit.getPemegang().setMcl_id(strPOClientID);
	                edit.getDatausulan().setReg_spaj(strTmpSPAJ);
	                edit.getDatausulan().setLus_id(edit.getPemegang().getLus_id());
	                edit.getDatausulan().setLstp_id(edit.getDatausulan().getTipeproduk());
	                edit.getPemegang().setMspo_policy_holder(strPOClientID);
	                edit.getDatausulan().setMspo_ins_period(edit.getDatausulan().getMspr_ins_period());
	                
	                if(edit.getDatausulan().getConvert()==null){
	                    edit.getDatausulan().setKopiSPAJ("");
	                }else{
	                    if(edit.getDatausulan().getMssur_se()!=null){
	                        if(edit.getDatausulan().getMssur_se()==1 || edit.getDatausulan().getMssur_se()==2 || edit.getDatausulan().getMssur_se()==3){    
	                            edit.getDatausulan().setKopiSPAJ(edit.getPowersave().getMsl_spaj_lama());
	                        }else {
	                            edit.getDatausulan().setKopiSPAJ("");
	                        }
	                    }else{
	                        edit.getDatausulan().setKopiSPAJ("");
	                    }
	                    
	                }
	                
	                if (!FormatString.rpad("0",(strBranch),2).equalsIgnoreCase("09"))
	                {
	                    edit.getPemegang().setMspo_pribadi(new Integer(0));
	                }

	                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                Calendar tgl_sekarang = Calendar.getInstance(); 
	                String tgl_s =  FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.DATE)),2);
	                tgl_s = tgl_s.concat("/");
	                tgl_s = tgl_s.concat(FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.MONTH)+1),2));
	                tgl_s = tgl_s.concat("/");
	                tgl_s = tgl_s.concat(Integer.toString(tgl_sekarang.get(Calendar.YEAR)));    
	                Date v_strInputDate;
	                
	                v_strInputDate = df.parse(tgl_s);
	                edit.getDatausulan().setMspo_spaj_date(edit.getPemegang().getMspo_spaj_date());
	                
	                //tambahan Yusuf - 12 feb 08
	                //mspo_flat diisi 0 apabila individu biasa, 1 bila inputan bank (bii), 2 bila inputan bank (sinarmas - simas prima)
	                //if(currentUser.getJn_bank().intValue() == 0 || currentUser.getJn_bank().intValue() == 1) {
//	                    edit.getPemegang().setMspo_flat(1);
	                /*}else if(currentUser.getJn_bank().intValue() == 2) {
	                    edit.getPemegang().setMspo_flat(2);
	                }else if(currentUser.getJn_bank().intValue() == 3) {
	                    edit.getPemegang().setMspo_flat(3);
	                }else {
	                    edit.getPemegang().setMspo_flat(0);
	                }*/
	                edit.getPemegang().setMspo_flat(0);
	                
	                if(edit.getAgen().getLca_id().equals("42")){
	                    edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
	                    //edit.getPemegang().setMspo_follow_up(0);
	                }else{
	                    if(!(edit.getPemegang().getSumber_id()==null?"":edit.getPemegang().getSumber_id()).equals("")){
	                        edit.getPemegang().setMspo_follow_up(4);
	                        edit.getPemegang().setMspo_customer(edit.getPemegang().getSumber_id());
	                    }else{
	                        if(!(edit.getPemegang().getMspo_customer()==null?"":edit.getPemegang().getMspo_customer()).equals("")){
	                            edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
	                        }else{
	                            edit.getPemegang().setMspo_customer(edit.getPemegang().getSumber_id());
	                        }
	                        
	                    }
	                    if(edit.getFlag_gmanual_spaj()!=null){
	                        edit.getPemegang().setMspo_customer("");
	                    }
	                }   
	                    
	                //Anta - Memberikan flag terhadap produk2 Syariah
	                //Deddy - penentuan kategori syariah berdasarkan linebus di lst_bisnis, flag syariah = 3            
	                if( selectLineBusLstBisnis(edit.getDatausulan().getLsbs_id().toString())==3){
	                    edit.getDatausulan().setMspo_syahriah(1);
	                }else{
	                    edit.getDatausulan().setMspo_syahriah(0);
	                };
	                
	                Date TigaPuluhNov2012 = defaultDateFormat.parse("30/11/2012");
	                if(edit.getDatausulan().getMste_beg_date().after(TigaPuluhNov2012)){
	                    edit.getDatausulan().setMspo_flag_new(1);
	                }else{
	                    edit.getDatausulan().setMspo_flag_new(0);
	                }       
	                
	                //proc_save_mst_policy
	                int rowupdated= updateMstPolicy(edit.getPemegang(),edit.getTertanggung(),edit.getDatausulan(),edit.getAgen(), mapper );
	                //System.out.println("edit mst policy");
	                if (rowupdated == 0)
	                {
	                    insertMstPolicy( edit.getPemegang(),edit.getTertanggung(),edit.getDatausulan(),edit.getAgen(), mapper );
	                    //System.out.println("insert mst policy");
	                }           
	                
	                if(edit.getAgen().getLca_id().equals("58")){
	                    //untuk masukkan kode appointmentID
	                    updateLeadReffBii(edit.getPemegang().getMspo_plan_provider(), strTmpSPAJ, mapper );             
	                }
	                
	                if(edit.getPemegang().getMspo_spaj_date() != null) {
	                    Date tanggal = edit.getPemegang().getMspo_spaj_date();
	                    if(tanggal == null) tanggal = dao_common.selectSysdate();
	                            
	                    Integer exist = selectMstTransHist( strTmpSPAJ, mapper );               
	                    
	                    if(exist > 0){
	                        updateMstTransHistory(strTmpSPAJ, "tgl_spaj_asli", tanggal, null, null, mapper);                    
	                    }else{
	                        insertMstTransHistory(strTmpSPAJ, "tgl_spaj_asli", tanggal, null, null, mapper);
	                    }               
	                }
	                Integer flag_simponi=edit.getDatausulan().getIsBungaSimponi();
	                Integer flag_tahapan=edit.getDatausulan().getIsBonusTahapan();
	                edit.getPemegang().setReg_spaj(strTmpSPAJ);         
	                //update bunga simponi
	                if (flag_simponi.intValue()==1)
	                {
	                    updateBungaMstPolicy( edit.getPemegang(), mapper );             
	                }           
	                //update bonus  tahapan
	                if (flag_tahapan.intValue()==1)
	                {
	                    Double bonus =edit.getPemegang().getBonus_tahapan();
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(), mapper ); 
	                }
	                if((edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==3) || (edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==4) ){
	                    Double bonus = 5.00;
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(), mapper ); 
	                }else if((edit.getDatausulan().getLsbs_id()==114 && edit.getDatausulan().getLsdbs_number()>=2)  ){
	                    Double bonus = 2.92;
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(), mapper ); 
	                }
	                
	                String []pendapatanRutinBulan = edit.getPemegang().getPendapatanBulan();
	                String []tujuanInvestasi = edit.getPemegang().getTujuanInvestasi();
	                Integer counter=0;
	                deleteMstKyc( strTmpSPAJ, "3", "2", mapper);    
	                for(int i=0;i<pendapatanRutinBulan.length;i++){
	                    if(!pendapatanRutinBulan[i].contains("-")){
	                        insertKyc(strTmpSPAJ, counter++, "3", "2", pendapatanRutinBulan[i], mapper);
	                    }
	                }
	                deleteMstKyc( strTmpSPAJ, "5", "2", mapper);    
	                for(int i=0;i<tujuanInvestasi.length;i++){
	                    if(!tujuanInvestasi[i].contains("-")){
	                    insertKyc(strTmpSPAJ, counter++, "5", "2", tujuanInvestasi[i], mapper);
	                    }
	                }
	                
	                //Ridhaal - update no_blanko menjadi Non Further setelah SPAJ di input
	                updateMonitoringSpaj(edit.getPemegang().getMspo_no_blanko(), null, "update_further_to_NF_inputSpaj", null, null , null, null,null,null,strTmpSPAJ, mapper);
	        
	            } catch (ParseException e) {
	                e.printStackTrace();
	                throw e;
	            }
	        }
	        
	        
	        private void proc_save_worksite_flag(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {       
	            Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
	            if (flag_worksite.intValue() == 1 || flag_worksite.intValue() == 2 || (edit.getAgen().getLca_id().equals("52") && edit.getDatausulan().getMste_flag_cc()==3) || edit.getDatausulan().getMste_gmit()==1) 
	            {
	                if(!CommonUtil.isEmpty(edit.getPemegang().getMspo_customer())){             
	                }
	                edit.getPemegang().setReg_spaj(strTmpSPAJ);
	                int rowupdate = updateMstWorksiteFlag( edit.getPemegang(), mapper);
	                if (rowupdate == 0)
	                {
	                    insertMstWorksiteFlag( edit.getPemegang(), mapper);
	                }
	            }
	        }
	        
	        private void proc_save_noblanko(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
	            if (flag_worksite.intValue() == 1)
	            {
	                //no_blanko
	                String no_blanko = edit.getPemegang().getMspo_no_blanko();
	                if (no_blanko.equalsIgnoreCase(""))
	                {
	                    //Calendar tgl_sekarang = Calendar.getInstance(); 
	                    String lca_id = edit.getAgen().getLca_id();
	                    Long intIDCounter = select_Counter( new Integer(71), lca_id );
	                    
	                    //--------------------------------------------
	                    //Increase current SPAJ No by 1 and
	                    //update the value to MST_COUNTER table
	                    intIDCounter = new Long(intIDCounter.longValue()+1);                
	                    //int b =update("update_counter", param);
	                    String blanko = FormatString.rpad("0",Long.toString(intIDCounter),7);
	                    //edit.getPemegang().setMspo_no_blanko(blanko);
	                }
	            }
	            if(edit.getCurrentUser().getLca_id().equals("58")){         
	                String lca_id = edit.getAgen().getLca_id();
	                Long intIDCounter = select_Counter( new Integer(71), lca_id );
	                intIDCounter = new Long(intIDCounter.longValue()+1);
	                update_Counter(intIDCounter, edit.getCurrentUser().getLca_id(), new Integer(71) );              
	            }
	            String no_blanko = edit.getPemegang().getMspo_no_blanko();
	            if (no_blanko== null) no_blanko = "";
	            String nomor = no_blanko.replaceAll(" ", "").toUpperCase();
	            
	            List<DropDownMenu> daftar = selectJenisForm();
	            for(DropDownMenu d : daftar){
	                if(nomor.startsWith(d.getKey())){
	                  update_no_blanko(nomor.substring(d.getKey().length()), strTmpSPAJ, Integer.valueOf(d.getValue()), mapper);
	                }   
	            }
	        }
	        
	        private void proc_save_mst_insured(Cmdeditbac edit,String strInsClientID,String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            

	            //**********insert mst insured ***************
	            edit.getDatausulan().setReg_spaj(strTmpSPAJ);
	            edit.getTertanggung().setMste_insured(strInsClientID);
	            edit.getTertanggung().setMste_age(edit.getTertanggung().getUsiattg());
	            Date v_strPaymentDate=null;
	            Date strDebitDate= null;
	            v_strPaymentDate = edit.getPemegang().getMste_tgl_recur();          
	            if (v_strPaymentDate != null)
	            {
	                strDebitDate = v_strPaymentDate;
	            }   
	            
	            edit.getPemegang().setMste_tgl_recur(strDebitDate);
	            edit.getDatausulan().setLus_id(edit.getPemegang().getLus_id());
	            
	            Integer v_intAutoDebet = edit.getDatausulan().getMste_flag_cc();
	            Integer lssa_id = new Integer(0);
	            Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
	            if (flag_worksite.intValue()==1 && v_intAutoDebet.intValue() == 3)
	            {
	                lssa_id = new Integer(3);
	            }else{
	                lssa_id = new Integer(1);
	            }
	            
	            edit.getDatausulan().setLssa_id(lssa_id);       
	            Integer flag_el = edit.getDatausulan().getMste_flag_el();
	            if (flag_el == null)
	            {
	                edit.getDatausulan().setMste_flag_el(new Integer(0));
	            }
	            Integer flag_investasi = edit.getDatausulan().getMste_flag_investasi();
	            if (flag_investasi ==null)
	            {
	                edit.getDatausulan().setMste_flag_investasi(new Integer(0));
	            }
	            Integer flag_gmit = edit.getDatausulan().getMste_gmit();
	            if (flag_gmit == null){
	                edit.getDatausulan().setMste_gmit(new Integer(0));
	            }       
	            
	            Map param = new HashMap();
	            if(edit.getPemegang().getMste_spaj_asli() == null) edit.getPemegang().setMste_spaj_asli(1);     
	            if(CommonUtil.isEmpty(edit.getDatausulan().getMste_flag_el())){
	                edit.getDatausulan().setMste_flag_el(0);
	            }   
	            
	            int rowupdated = updateMstInsured( edit.getPemegang(), edit.getTertanggung(), edit.getDatausulan(), edit.getAgen() , mapper);
	            //edit mst insured
	            if ( rowupdated==0)
	            {
	                insertMstInsured( edit.getPemegang(), edit.getTertanggung(), edit.getDatausulan(), edit.getAgen(), mapper);
	                //System.out.println("insert mst insured"); 
	            }
	            // save KYC untuk Pendapatan & Tujuan Investasi
	            String []pendapatanRutinBulan = edit.getTertanggung().getPendapatanBulan();
	            String []tujuanInvestasi = edit.getTertanggung().getTujuanInvestasi();
	            deleteMstKyc( strTmpSPAJ, "3", "1", mapper);    
	            Integer counter=0;  
	            for(int i=0;i<pendapatanRutinBulan.length;i++){
	                if(!pendapatanRutinBulan[i].contains("-")){
	                    insertKyc(strTmpSPAJ, counter++, "3", "1", pendapatanRutinBulan[i], mapper);
	                }
	            }       
	            
	            deleteMstKyc( strTmpSPAJ, "5", "1", mapper);    
	            for(int i=0;i<tujuanInvestasi.length;i++){
	                if(!tujuanInvestasi[i].contains("-")){
	                insertKyc(strTmpSPAJ, counter++, "5", "1", tujuanInvestasi[i], mapper);
	                }
	            }
	        }
	        
	        private void proc_save_suamiistri_ttg(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            deleteMstKeluarga(strTmpSPAJ, mapper);
	            
	            String nama_suamiistri = edit.getTertanggung().getNama_si();
	            Date tanggal_lahir_suamiistri = edit.getTertanggung().getTgllhr_si();
	            if (!nama_suamiistri.equalsIgnoreCase(""))
	            {
	                insertMstKeluarga(strTmpSPAJ, edit.getTertanggung().getNama_si(), 5, tanggal_lahir_suamiistri, 0, 0, mapper);
	                //System.out.println("insert mst keluarga");
	            }
	        
	            String nama_anak1 = edit.getTertanggung().getNama_anak1();
	            if (!nama_anak1.equalsIgnoreCase(""))
	            {       
	                Date tanggal_lahir_anak1 = edit.getTertanggung().getTgllhr_anak1();
	                insertMstKeluarga(strTmpSPAJ, edit.getTertanggung().getNama_anak1(), 4, tanggal_lahir_anak1, 0, 1, mapper);
	            }
	            
	            String nama_anak2 = edit.getTertanggung().getNama_anak2();
	            if (!nama_anak2.equalsIgnoreCase(""))
	            {           
	                Date tanggal_lahir_anak2 = edit.getTertanggung().getTgllhr_anak2();
	                insertMstKeluarga(strTmpSPAJ, edit.getTertanggung().getNama_anak2(), 4, tanggal_lahir_anak2, 0, 2, mapper);
	            }
	            
	            String nama_anak3 = edit.getTertanggung().getNama_anak3();
	            if (nama_anak3 == null)
	            {
	                nama_anak3 = "";
	            }
	            if (!nama_anak3.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak3 = edit.getTertanggung().getTgllhr_anak3();
	                insertMstKeluarga(strTmpSPAJ, edit.getTertanggung().getNama_anak3(), 4, tanggal_lahir_anak3, 0, 3, mapper);         
	            }
	        }
	        
	        private void proc_save_suamiistri_pp(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            String nama_suamiistri = edit.getPemegang().getNama_si();
	            if (!nama_suamiistri.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_suamiistri = edit.getPemegang().getTgllhr_si();      
	                insertMstKeluarga(strTmpSPAJ, edit.getPemegang().getNama_si(), 5, tanggal_lahir_suamiistri, 1, 0, mapper);
	            }
	            
	            String nama_anak1 = edit.getPemegang().getNama_anak1();
	            if (!nama_anak1.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak1 = edit.getPemegang().getTgllhr_anak1();            
	                insertMstKeluarga(strTmpSPAJ, edit.getPemegang().getNama_anak1(), 4, tanggal_lahir_anak1, 1, 1, mapper);
	            }
	            
	            String nama_anak2 = edit.getPemegang().getNama_anak2();
	            if (!nama_anak2.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak2 = edit.getPemegang().getTgllhr_anak2();        
	                insertMstKeluarga(strTmpSPAJ, edit.getPemegang().getNama_anak2(), 4, tanggal_lahir_anak2, 1, 2, mapper);
	            }
	            
	            String nama_anak3 = edit.getPemegang().getNama_anak3();
	            if (!nama_anak3.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak3 = edit.getPemegang().getTgllhr_anak3();
	                insertMstKeluarga(strTmpSPAJ, edit.getPemegang().getNama_anak3(), 4, tanggal_lahir_anak3, 1, 3, mapper);
	            }
	        }
	        
	        private void proc_save_keluarga(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper) throws Exception{
	            Calendar tgl_sekarang = Calendar.getInstance(); 
	                        
	            Integer tanggal2 = tgl_sekarang.get(Calendar.DATE);
	            Integer bulan2 = tgl_sekarang.get(Calendar.MONTH)+1;
	            Integer tahun2 = tgl_sekarang.get(Calendar.YEAR);
	            f_hit_umur umr = new f_hit_umur();
	            if(edit.getDatausulan().getJenis_pemegang_polis()==0){
	                if(!edit.getPemegang().getPemegang_polis().equals("2")){
	                    if (edit.getPemegang().getPemegang_polis().equals("0")){
	                        Integer usiaSuami = 0;
	                        Integer tanggal1 = edit.getPemegang().getTgl_suami().getDate();
	                        Integer bulan1 = edit.getPemegang().getTgl_suami().getMonth()+1;
	                        Integer tahun1 = edit.getPemegang().getTgl_suami().getYear()+1900;
	                        usiaSuami = umr.umur(tahun1,bulan1,tanggal1,tahun2,bulan2,tanggal2);
	                        //Map paramSuami = new HashMap();
	                        HashMap<String, Object> paramSuami = new HashMap<String, Object>();
	                        paramSuami.put("strTmpSPAJ", strTmpSPAJ);
	                        paramSuami.put("nama", edit.getPemegang().getNama_suami());
	                        paramSuami.put("lsre_id","5");
	                        paramSuami.put("tanggal_lahir",edit.getPemegang().getTgl_suami());
	                        paramSuami.put("insured",0);
	                        paramSuami.put("no",0);
	                        paramSuami.put("pekerjaan",edit.getPemegang().getPekerjaan_suami());
	                        paramSuami.put("jabatan",edit.getPemegang().getJabatan_suami());
	                        paramSuami.put("penghasilan", edit.getPemegang().getPenghasilan_suami());
	                        paramSuami.put("npwp",edit.getPemegang().getNpwp_suami());
	                        paramSuami.put("bidang",edit.getPemegang().getBidang_suami());
	                        paramSuami.put("usia",usiaSuami);
	                        paramSuami.put("nama_perusahaan",edit.getPemegang().getPerusahaan_suami());         
	                        int rowupdated =  updateMstKeluarga( paramSuami, mapper);
	                        if(rowupdated == 0) insert_Mst_Keluarga( paramSuami, mapper);
	                        
	                    }else if (edit.getPemegang().getPemegang_polis().equals("1")){

	                        Integer tanggalAyah = edit.getPemegang().getTgl_ayah().getDate();
	                        Integer bulanAyah = edit.getPemegang().getTgl_ayah().getMonth()+1;
	                        Integer tahunAyah = edit.getPemegang().getTgl_ayah().getYear()+1900;
	                        Integer tanggalIbu = edit.getPemegang().getTgl_ibu().getDate();
	                        Integer bulanIbu = edit.getPemegang().getTgl_ibu().getMonth()+1;
	                        Integer tahunIbu = edit.getPemegang().getTgl_ibu().getYear()+1900;
	                        Integer usiaAyah = umr.umur(tahunAyah,bulanAyah,tanggalAyah,tahun2,bulan2,tanggal2);
	                        Integer usiaIbu = umr.umur(tahunIbu,bulanIbu,tanggalIbu,tahun2,bulan2,tanggal2);
	                        //Map paramAyah = new HashMap();
	                        HashMap<String, Object> paramAyah = new HashMap<String, Object>();
	                        paramAyah.put("strTmpSPAJ", strTmpSPAJ);
	                        paramAyah.put("nama", edit.getPemegang().getNama_ayah());
	                        paramAyah.put("lsre_id","2");
	                        paramAyah.put("tanggal_lahir",edit.getPemegang().getTgl_ayah());
	                        paramAyah.put("insured",0);
	                        paramAyah.put("no",1);
	                        paramAyah.put("pekerjaan",edit.getPemegang().getPekerjaan_ayah());
	                        paramAyah.put("jabatan",edit.getPemegang().getJabatan_ayah());
	                        paramAyah.put("penghasilan", edit.getPemegang().getPenghasilan_ayah());
	                        paramAyah.put("npwp",edit.getPemegang().getNpwp_ayah());
	                        paramAyah.put("bidang",edit.getPemegang().getBidang_ayah());
	                        paramAyah.put("usia",usiaAyah);
	                        paramAyah.put("nama_perusahaan",edit.getPemegang().getPerusahaan_ayah());
	                        int rowupdated2 =  updateMstKeluarga( paramAyah, mapper );
	                        if(rowupdated2 == 0) insert_Mst_Keluarga( paramAyah, mapper );

	                        //Map paramIbu = new HashMap();
	                        HashMap<String, Object> paramIbu = new HashMap<String, Object>();
	                        paramIbu.put("strTmpSPAJ", strTmpSPAJ);
	                        paramIbu.put("nama", edit.getPemegang().getNama_ibu());
	                        paramIbu.put("lsre_id","2");
	                        paramIbu.put("tanggal_lahir",edit.getPemegang().getTgl_ibu());
	                        paramIbu.put("insured",0);
	                        paramIbu.put("no",2);
	                        paramIbu.put("pekerjaan",edit.getPemegang().getPekerjaan_ibu());
	                        paramIbu.put("jabatan",edit.getPemegang().getJabatan_ibu());
	                        paramIbu.put("penghasilan", edit.getPemegang().getPenghasilan_ibu());
	                        paramIbu.put("npwp",edit.getPemegang().getNpwp_ibu());
	                        paramIbu.put("bidang",edit.getPemegang().getBidang_ibu());
	                        paramIbu.put("usia",usiaIbu);
	                        paramIbu.put("nama_perusahaan",edit.getPemegang().getPerusahaan_ibu());
	                        int rowupdated3 =  updateMstKeluarga( paramIbu, mapper );
	                        if(rowupdated3 == 0) insert_Mst_Keluarga( paramIbu, mapper );                   
	                    }
	                }
	            }
	        }   
	        
	        private void proc_save_pembayar_premi(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper)  throws Exception{           
	            String []pendapatanRutinBulan = edit.getPembayarPremi().getPendapatanBulan();
	            String []pendapatanNonTahun = edit.getPembayarPremi().getPendapatanTahun();
	            String []tujuanInvestasi = edit.getPembayarPremi().getTujuanInvestasi();
	            Integer flagMcl = 0; //untuk menandakan dy mcl id baru / pemegang
	            Integer counter = 0;
	            String gc_strTmpBranch = "WW";
	            Long intIDCounter =null;
	            String nomor = "";
	            String mclId = "";
	            String lsreIdPremi = edit.getPembayarPremi().getLsre_id_premi();
	            String alasan = edit.getPembayarPremi().getAlasan();
	            String strPemPremiID = "";
	            String v_strDateNow = "";
	            Map alasanClient = new HashMap();
	            String calonPembayarpremi = edit.getPembayarPremi().getLsre_id_premi();
	            
	            if(!calonPembayarpremi.equals("40")){       
	                intIDCounter = selectCounterClient( gc_strTmpBranch );
	                updateMstCounterClient(new Long(intIDCounter.longValue()+1), gc_strTmpBranch);
	                
	                
	                nomor =("000000000").concat(Long.toString(intIDCounter.longValue() + 1));
	                strPemPremiID = gc_strTmpBranch.concat(nomor.substring((nomor.length()-10),nomor.length()));
	                
	                if(calonPembayarpremi.equals("41")){
	                    // jika calon pembayar premi adalah perusahaan
	                    //create pembayar premi (mcl_id baru)
	                    edit.getPembayarPremi().setMcl_id(strPemPremiID);
	                    edit.getPembayarPremi().setMcl_jenis("1");
	                    
	                    //Map dataPolicy = new HashMap();
	                    HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
	                    dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
	                    dataPolicy.put("reg_spaj", strTmpSPAJ);
	                    dataPolicy.put("mspo_payer",strPemPremiID);
	                
	                    Integer rowupdated1 = updateNewClientPayerBadanHukum(edit.getPembayarPremi(), mapper);
	                    if (rowupdated1 == 0){
	                        insertNewClientPayerBadanHukum(edit.getPembayarPremi(), mapper);
	                    }
	        
	                    Integer rowupdated2 = updateAddressPemPremiIndividu(edit.getPembayarPremi(), mapper);
	                    if (rowupdated2 == 0){
	                        insertAddressPemPremiBadanHukum(edit.getPembayarPremi(), mapper);
	                    }
	                    updateMstPolicyPayer(dataPolicy, mapper);               
	                }else{//selain perusahaan
	                        
	                    edit.getPembayarPremi().setMcl_id(strPemPremiID);
	                    edit.getPembayarPremi().setMcl_jenis("0");
	                    
	                    // Map dataPolicy = new HashMap();
	                    HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
	                    dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
	                    dataPolicy.put("reg_spaj", strTmpSPAJ);
	                    dataPolicy.put("mspo_payer", strPemPremiID);
	                    
	                    Integer rowupdated1 = updateNewClientPayerIndividu(edit.getPembayarPremi(), mapper);                
	                    if (rowupdated1 == 0){
	                         insertNewClientPayerIndividu(edit.getPembayarPremi(), mapper);
	                    }
	                    
	                    Integer rowupdated2 = updateAddressPemPremiIndividu(edit.getPembayarPremi(), mapper);
	                    if (rowupdated2 == 0){
	                        insertAddressPemPremiIndividu(edit.getPembayarPremi(), mapper);
	                    }
	                    updateMstPolicyPayer(dataPolicy, mapper);   
	                }           
	            }else{
	                //Map dataPolicy = new HashMap();
	                HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
	                dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
	                dataPolicy.put("reg_spaj", strTmpSPAJ);
	                dataPolicy.put("mspo_payer", edit.getPemegang().getMcl_id());           
	                updateMstPolicyPayer(dataPolicy, mapper);   
	            }
	            
	            deleteMstKyc( strTmpSPAJ, "6", "0", mapper);    
	            if (edit.getPembayarPremi().getMkl_kerja_other_radio().equals("1")){
	                insertKyc(strTmpSPAJ, counter++, "6", "0", edit.getPembayarPremi().getMkl_kerja_other(), mapper );
	            }
	                
	            deleteMstKyc( strTmpSPAJ, "7", "0", mapper);    
	            if(edit.getPembayarPremi().getTotal_rutin()==null)edit.getPembayarPremi().setTotal_rutin("");
	            if (!edit.getPembayarPremi().getTotal_rutin().equals("")){
	                insertKyc(strTmpSPAJ, counter++, "7", "0", edit.getPembayarPremi().getTotal_rutin(), mapper );
	            }
	                    
	            deleteMstKyc( strTmpSPAJ, "8", "0", mapper);
	            if(edit.getPembayarPremi().getTotal_non_rutin()==null)edit.getPembayarPremi().setTotal_non_rutin("");
	            if (!edit.getPembayarPremi().getTotal_non_rutin().equals("")){
	                insertKyc(strTmpSPAJ, counter++, "8", "0", edit.getPembayarPremi().getTotal_non_rutin(), mapper );
	            }       
	        
	            deleteMstKyc( strTmpSPAJ, "3", "0", mapper);
	            for(int i=0;i<pendapatanRutinBulan.length;i++){
	                if(!pendapatanRutinBulan[i].contains("-")){
	                    insertKyc(strTmpSPAJ, counter++, "3", "0", pendapatanRutinBulan[i], mapper);
	                }
	            }
	                    
	            deleteMstKyc( strTmpSPAJ, "4", "0", mapper);
	            for(int i=0;i<pendapatanNonTahun.length;i++){
	                if(!pendapatanNonTahun[i].contains("-")){
	                    insertKyc(strTmpSPAJ, counter++, "4", "0", pendapatanNonTahun[i], mapper );
	                }
	            }
	                
	            deleteMstKyc( strTmpSPAJ, "5", "0", mapper);
	            for(int i=0;i<tujuanInvestasi.length;i++){
	                if(!tujuanInvestasi[i].contains("-")){
	                insertKyc(strTmpSPAJ, counter++, "5", "0", tujuanInvestasi[i], mapper );
	                }
	            }
	            
	        }

	        private void proc_save_data_pic(Cmdeditbac edit, String strPOClientID, CommonDao mapper)throws Exception
	        {
	            
	            edit.getContactPerson().setMcl_id(strPOClientID);
	            edit.getContactPerson().setFlag_ut(1);
	            edit.getContactPerson().setNo_urut(1);
	            edit.getContactPerson().setTelp_kantor(edit.getContactPerson().getTelpon_kantor());
	            edit.getContactPerson().setTelp_hp(edit.getContactPerson().getNo_hp());
	            edit.getContactPerson().setLus_id(edit.getPemegang().getLus_id());
	            
	            // Insert Policy Holder PIC information to MST_COMPANY_CONTACT*/
	             int rowupdated =  updateMstClientPic(  edit.getContactPerson(), mapper );
	             if (rowupdated==0)
	                {
	                 insertMstClientPic(  edit.getContactPerson(), mapper );
	                    //System.out.println("insert mst client pic");
	                }       
	            // Insert Policy Holder PIC Home Address information to MST_COMPANY_CONTACT_ADDRESS
	            int rowupdated1 =  updateMstAddressPic(  edit.getContactPerson(), mapper );
	            if (rowupdated1==0)
	            {
	                 insertMstAddressPic(  edit.getContactPerson(), mapper );
	                //System.out.println("insert mst address pic");
	            }       
	            deleteMstCompanyContactFamily( strPOClientID, mapper);  
	            String nama_suamiistri = edit.getContactPerson().getNama_si();
	            if(nama_suamiistri == null)nama_suamiistri = "";
	            if (!nama_suamiistri.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_suamiistri = edit.getContactPerson().getTgllhr_si();
	                Map param1=new HashMap();
	                //HashMap<String, Object> param1 = new HashMap<String, Object>();
	                param1.put("mcl_id", strPOClientID);
	                param1.put("nama", edit.getContactPerson().getNama_si());
	                param1.put("lsre_id",5);
	                param1.put("tanggal_lahir", tanggal_lahir_suamiistri );
	                param1.put("insured", 1);
	                param1.put("no", 0);
	                inserMstCompanyContactFamily( param1, mapper );
	                //System.out.println("insert mst keluarga");
	            }
	            
	            String nama_anak1 = edit.getContactPerson().getNama_anak1();
	            if(nama_anak1 == null)nama_anak1 = "";
	            if (!nama_anak1.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak1 = edit.getContactPerson().getTgllhr_anak1();
	                Map param1=new HashMap();
	                param1.put("mcl_id", strPOClientID);
	                param1.put("nama", edit.getContactPerson().getNama_anak1());
	                param1.put("lsre_id",4);
	                param1.put("tanggal_lahir", tanggal_lahir_anak1);
	                param1.put("insured", 1);
	                param1.put("no", 1);
	                inserMstCompanyContactFamily( param1, mapper );
	                //System.out.println("insert mst keluarga");
	            }
	            
	            String nama_anak2 = edit.getContactPerson().getNama_anak2();
	            if(nama_anak2 == null)nama_anak2 = "";
	            if (!nama_anak2.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak2 = edit.getContactPerson().getTgllhr_anak2();
	                Map param1=new HashMap();
	                param1.put("mcl_id", strPOClientID);
	                param1.put("nama", edit.getContactPerson().getNama_anak2());
	                param1.put("lsre_id",4);
	                param1.put("tanggal_lahir", tanggal_lahir_anak2);
	                param1.put("insured", 1);
	                param1.put("no", 2);
	                inserMstCompanyContactFamily( param1, mapper );
	                //System.out.println("insert mst keluarga");
	            }
	                
	            String nama_anak3 = edit.getContactPerson().getNama_anak3();
	            if(nama_anak3 == null)nama_anak3 = "";
	            if (!nama_anak3.equalsIgnoreCase(""))
	            {
	                Date tanggal_lahir_anak3 = edit.getContactPerson().getTgllhr_anak3();
	                Map param1=new HashMap();
	                param1.put("mcl_id", strPOClientID);
	                param1.put("nama", edit.getContactPerson().getNama_anak3());
	                param1.put("lsre_id",4);
	                param1.put("tanggal_lahir", tanggal_lahir_anak3);
	                param1.put("insured", 1);
	                param1.put("no", 3);
	                inserMstCompanyContactFamily( param1, mapper);
	                //System.out.println("insert mst keluarga");
	            }
	        }
	        
	        public void proc_save_agen_two(Cmdeditbac edit, String strTmpSPAJ ,String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao mapper) throws Exception{
	        /*	Integer intBII =new Integer(0);
	            Integer v_intPribadi = edit.getPemegang().getMspo_pribadi();
	            if(v_intPribadi == null)v_intPribadi = 0;
	            String v_kode_ao = edit.getPemegang().getMspo_ao().toUpperCase();
	            Integer v_intFollowUp = edit.getPemegang().getMspo_follow_up();
	                    
	            Agentrec[]  arrAgentRec;
	            Agentrec[]  arrAgentArtha;
	            Agentrec[]  arrAgentartha1;
	            Agentrec[]  arrAgentWorksite = null;
	            Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
	            Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
	            
	            if (!FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("46") || v_strAgentId.equalsIgnoreCase("000000"))
	            {
	            }
	            
	            if (FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("09"))
	            {
	                intBII = new Integer(1);
	            }else{
	                v_intPribadi = new Integer(0);
	                edit.getPemegang().setMspo_pribadi(new Integer(0));
	            }
	            */
	            
	        	proc_save_agen(edit, strTmpSPAJ, v_strAgentId, strAgentBranch, strBranch, strWilayah, strRegion, v_strregionid, mapper);
	        }
	 	   
	        
	        public void proc_save_agen(Cmdeditbac edit, String strTmpSPAJ ,String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao mapper) throws Exception
	        {       
	            
	            Integer intBII =new Integer(0);
	            Integer v_intPribadi = edit.getPemegang().getMspo_pribadi();
	            if(v_intPribadi == null)v_intPribadi = 0;
	            String v_kode_ao = edit.getPemegang().getMspo_ao().toUpperCase();
	            Integer v_intFollowUp = edit.getPemegang().getMspo_follow_up();
	                    
	            Agentrec[]  arrAgentRec;
	            Agentrec[]  arrAgentArtha;
	            Agentrec[]  arrAgentartha1;
	            Agentrec[]  arrAgentWorksite = null;
	            
	            
	            Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
	            Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
	            if (!FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("46") || v_strAgentId.equalsIgnoreCase("000000"))
	            {
	                if (tahun_beg_date_polis.intValue() > 2006)
	                {
	                    arrAgentRec = proc_process_agent_2007(v_strAgentId,1, mapper);
	                    arrAgentWorksite = proc_process_agent_2007(v_strAgentId,2, mapper);
	                }else{
	                    arrAgentRec = proc_process_agent(v_strAgentId, mapper);
	                }
	            }else{
	                arrAgentRec=null;
	                arrAgentWorksite=null;
	            }
	            if (FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("09"))
	            {
	                intBII = new Integer(1);
	                
	            }else{
	                v_intPribadi = new Integer(0);
	                edit.getPemegang().setMspo_pribadi(new Integer(0));
	            }
	            
	            if (v_strAgentId.equalsIgnoreCase("000000"))
	            {
	                
	                proc_save_agen_prod(edit,arrAgentRec,strTmpSPAJ ,new Integer(1),new Integer(0),null, mapper);
	                //System.out.println("insert mst_agent_prod");
	                
	                //insert mst agent comm
	                proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(1), mapper);
	            }else{      
	                if(FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("58")){
	                    for (int i = 1 ; i<=arrAgentRec.length-1 ; i++)
	                    {
	                        proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper );
	                    }
	                }
	                if (FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("46"))
	                {
	                    arrAgentArtha = proc_agent_artha(v_strAgentId, mapper);
	                    arrAgentartha1 = proc_process_agent_artha_2007(v_strAgentId, mapper);

	                    for (int i = 1 ; i<=4 ; i++)
	                    {
	                        Integer ssbm=new Integer(0);
	                        proc_save_agen_prod(edit,arrAgentartha1,strTmpSPAJ ,new Integer(i),ssbm,null, mapper);
	                        if (intBII.intValue() == 0  &&  arrAgentartha1[i].getComm_id() != null ) 
	                        {
//	                          insert mst agent comm
	                            proc_save_agen_comm(edit,arrAgentartha1, strTmpSPAJ ,new Integer(i), mapper);
	                            //System.out.println("insert mst_agent_comm" + i);
	                        }else{
	                            
	                            if (v_intPribadi.intValue() == 1) 
	                            {
	                                continue;
	                            }

	                            if ((strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 1))
	                            {
	                                continue;
	                            }

	                            if (strAgentBranch.equalsIgnoreCase("0903") && v_kode_ao.equalsIgnoreCase(v_strAgentId))    
	                            {
	                            
	                            }else{
	                                if  (arrAgentartha1[i].getComm_id()!=null)
	                                {
	                                    if  (arrAgentartha1[i].getComm_id().intValue() == 4) 
	                                    {
//	                                      insert mst agent comm
	                                        proc_save_agen_comm(edit,arrAgentartha1, strTmpSPAJ ,new Integer(i), mapper);
	                                        //System.out.println("insert mst_agent_comm" + i);

	                                    }else{
	                                        if (arrAgentartha1[i].getComm_id().intValue() == 3 && ( strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 2))
	                                        {
//	                                          insert mst agent comm
	                                            proc_save_agen_comm(edit,arrAgentartha1, strTmpSPAJ ,new Integer(i), mapper);
	                                            //System.out.println("insert mst_agent_comm" + i);
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                    for (int i = 1 ; i<=6 ; i++)
	                    {
	                        proc_save_agen_artha(edit,arrAgentArtha, strTmpSPAJ,new Integer(i), mapper);
	                    }
	                }else{
	                    for (int i = 1 ; i<=4 ; i++)
	                    {
	                        Integer lvl_fcd=0;
	                        Integer ssbm=new Integer(0);
	                        if ((arrAgentRec[i].getLevel_id()).intValue() == 1)
	                        {
	                            ssbm=(arrAgentRec[i].getSbm());
	                        }else{
	                            if ((arrAgentRec[i].getLevel_id()).intValue() == 2)
	                            {
	                                ssbm=(arrAgentRec[i].getBm());
	                            }
	                        }
	                        
	                        //Validate for referensi(Tambang Emas) -- UNTUK PENUTUP, LEV_COMM diisi 4, sisanya null
	                        if(arrAgentRec[i].getLevel_id()!=4){
	                            if(edit.getAgen().getJenis_ref()==null){
	                                
	                            }
	                            else if(edit.getAgen().getJenis_ref()==0){
	                                arrAgentRec[i].setComm_id(null);
	                            }
	                        }
	                        //End
	                        
	                        if(FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("65")){
	                            if(arrAgentRec[i].getLevel_id()==4){
	                                lvl_fcd = arrAgentRec[i].getLvl_fcd();
	                            }
	                        }
	                        
	                        proc_save_agen_prod(edit,arrAgentRec,strTmpSPAJ ,new Integer(i),ssbm,lvl_fcd, mapper);
	                        //System.out.println("insert mst_agent_prod");
	                        //System.out.println("insert mst_agent_prod" + i);
	                        
	                        //(27 Jun 2014) - Deddy : khusus untuk Agency Kode 37M103, tidak perlu proses komisi.
	                        //MANTA (13/02/2017) - Agent Binary Sistem (37Y600) tidak perlu proses komisi
	                        if(!v_strregionid.equals("37M103") && !v_strregionid.equals("37Y600")){                     
	                            if(!CommonUtil.isEmpty(edit.getAgen().getMsag_asnew())){
	                                if(edit.getAgen().getMsag_asnew()==1){
	                                    proc_save_agen_rec(edit, arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                }
	                            }
	                                
	                            if(!FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("58")){
	                                if(FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("42")){
	                                {
	                                    if (intBII.intValue() == 0  &&  arrAgentWorksite[i].getComm_id() != null ) 
	                                        proc_save_agen_comm(edit,arrAgentWorksite, strTmpSPAJ ,new Integer(i), mapper);//Req Kuseno (25 Mar 2014) :Khusus worksite, struktur leader di table mst_agent_comm berdasarkan  mst_agent.mst_leader_comm, table mst_agent_prod tetap seperti biasa.
	                                    }
	                                }else if (intBII.intValue() == 0  &&  arrAgentRec[i].getComm_id() != null && !FormatString.rpad("0",(strAgentBranch.substring(0,2)),2).equalsIgnoreCase("42") ) 
	                                {
	                                        proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                }else{
	            
	                                    if (v_intPribadi.intValue() == 1) 
	                                    {
	                                        continue;
	                                    }
	            
	                                    if ((strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 1))
	                                    {
	                                        continue;
	                                    }
	            
	                                    if (strAgentBranch.equalsIgnoreCase("0903") && v_kode_ao.equalsIgnoreCase(v_strAgentId))    
	                                    {
	                                        
	                                    }else{
	                                        if  (arrAgentRec[i].getComm_id()!=null)
	                                        {
	                                            if  (arrAgentRec[i].getComm_id().intValue() == 4) 
	                                            {
	            //                                  insert mst agent comm
	                                                proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                                //System.out.println("insert mst_agent_comm" + i);
	            
	                                            }else{
	                                                if (arrAgentRec[i].getComm_id().intValue() == 3 && ( strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 2))
	                                                {
	            //                                      insert mst agent comm
	                                                    proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                                    //System.out.println("insert mst_agent_comm" + i);
	                                                }
	                                            }
	                                        }
	                                        //Untuk ACD
	                                        else{
	                                            if(strBranch.equals("63")){
	                                                proc_save_agen_comm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                            }
	                                        }
	                                    }
	                                }
	                            }
	                            if (i==4)
	                            {
	                                if (!(strBranch.equalsIgnoreCase("08") || strBranch.equalsIgnoreCase("09") || strBranch.equalsIgnoreCase("37") || strBranch.equalsIgnoreCase("42") || strBranch.equalsIgnoreCase("52") || strBranch.equalsIgnoreCase("67")))
	                                {
	                                    proc_save_agen_comm_rm(edit,arrAgentRec, strTmpSPAJ ,new Integer(i), mapper);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	            if (intBII.intValue() == 1 )
	            {
	                Integer ssbm=new Integer(0);
	                Agentrec[]  arrAgentRec1;
	                if(!v_kode_ao.equals("")){
	                    
	                
	                arrAgentRec1 = proc_process_agentao(v_kode_ao, mapper);
	                for (int i = 1 ; i<=4 ; i++)
	                {

	                    if ((arrAgentRec1[i].getLevel_id()).intValue() == 1)
	                    {
	                        ssbm=(arrAgentRec1[i].getSbm());
	                    }else{
	                        if ((arrAgentRec1[i].getLevel_id()).intValue() == 2)
	                        {
	                            ssbm=(arrAgentRec1[i].getBm());
	                        }
	                    }
	                    
	                    edit.getAgen().setReg_spaj(strTmpSPAJ);
	                    edit.getAgen().setMsag_id(arrAgentRec1[i].getAgent_id());
	                    edit.getAgen().setLstb_id(arrAgentRec1[i].getBisnis_id());
	                    edit.getAgen().setLsle_id(arrAgentRec1[i].getLevel_id());
	                    edit.getAgen().setLev_comm(arrAgentRec1[i].getComm_id());
	                    edit.getAgen().setFlag_sbm(ssbm);
	                    
	                    insertMstAgentBa(edit.getAgen(), mapper);
	                    //System.out.println("insert mst_agent_ba");
	                }
	            }
	            }
	            edit.getAgen().setMsag_id(v_strAgentId);
	        }
	        
	        
	        private Agentrec[] proc_process_agentao(String v_strAgentId, CommonDao mapper) throws Exception
	        {
	            
	            Integer intCounter = new Integer(0);
	            Agentrec[]  arrAgentRec1;
	            arrAgentRec1  = new Agentrec[5];
	            String strTmpAgentId=null;
	            String strLeaderId=null;
	            Integer intLevelComm = new Integer(4);
	            Integer intBisnisId =null ;
	            Integer intLevelId =null;
	            Integer strbm=null;
	            Integer strsbm=null;
	            Integer strjenis =null;
	            Integer intCommId =null;
	            strLeaderId = v_strAgentId;
	            
	            if(!v_strAgentId.equalsIgnoreCase("")){
/*	            Map mapAgentCodeAO = selectAgentCodeAO(v_strAgentId);
 * 
 * 
*/	    
	            Map mapAgentCodeAO = selectAgentCodeAO(v_strAgentId);
	            	//     Map mapAgentCodeAO = new HashMap();
/*mapAgentCodeAO.put("AGENT_3", "901858");
mapAgentCodeAO.put("AGENT_2", "901795");
mapAgentCodeAO.put("AGENT_1", "901795");
mapAgentCodeAO.put("AGENT_4", "901841");*/


	            String code_lvl3 = (String) mapAgentCodeAO.get("AGENT_3");
	            String code_lvl2 = (String) mapAgentCodeAO.get("AGENT_2");
	            String code_lvl1 = (String) mapAgentCodeAO.get("AGENT_1");
	            
	                for (int i = 4 ; i>=1 ; i--)
	                {
	                    
	                   if ((4 - i) == (intCounter.intValue()))
	                   {

	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        strTmpAgentId = strLeaderId;
	                        if(CommonUtil.isEmpty(strTmpAgentId)){
	                            if(i==3){
	                                strTmpAgentId=code_lvl3;
	                            }else if(i==2){
	                                strTmpAgentId=code_lvl2;
	                            }else if(i==1){
	                                strTmpAgentId=code_lvl1;
	                            }
	                        }                       
	                            Map param = new HashMap();                          
	                            param.put("strTmpAgentId", strTmpAgentId);
	                            Agen data2 =  selectMstAgent( param, mapper);
	                                                    
	                            if (data2!=null)
	                            {       
	                                intBisnisId = data2.getLstb_id();
	                                intLevelId = data2.getLsle_id();
	                                strLeaderId = data2.getMst_leader();
	                                intCommId = data2.getMsag_komisi();
	                                strbm = data2.getMsag_flag_bm();
	                                strsbm = data2.getMsag_sbm();
	                                strjenis = data2.getMsag_jenis();
	                            }
	                            //System.out.println("select mst agent");       
	                            if (i == 4 && strLeaderId.equalsIgnoreCase("") ) 
	                            {
	                                strLeaderId = "000606";
	                            }
	                            
	                        arrAgentRec1[intCounter.intValue()]= new Agentrec();
	                        arrAgentRec1[intCounter.intValue()].setBisnis_id(intBisnisId);
	                        arrAgentRec1[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                        arrAgentRec1[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                        if (intCommId.intValue() == 1)
	                        {
	                            arrAgentRec1[intCounter.intValue()].setComm_id(intLevelComm);
	                        }else{
	                            arrAgentRec1[intCounter.intValue()].setComm_id(null);
	                        }
	                        arrAgentRec1[intCounter.intValue()].setBm(strbm);
	                        arrAgentRec1[intCounter.intValue()].setSbm(strsbm);
	                
	                        intLevelComm = new Integer(intLevelComm.intValue() - 1);

	                        //iii = new Integer(i);
	                        while (intLevelId.intValue() < i)
	                        {
	                            intCounter = new Integer(intCounter.intValue() + 1);
	                            arrAgentRec1[intCounter.intValue()]= new Agentrec();
	                            arrAgentRec1[intCounter.intValue()].setBisnis_id(arrAgentRec1[intCounter.intValue()-1].getBisnis_id());
	                            arrAgentRec1[intCounter.intValue()].setAgent_id(arrAgentRec1[intCounter.intValue()-1].getAgent_id());
	                            arrAgentRec1[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                            arrAgentRec1[intCounter.intValue()].setComm_id(null);
	                            if (strjenis.intValue() == 7 && i == 4)
	                            {
	                                if (intLevelId.intValue() < 4 && intCommId.intValue() == 1)
	                                {
	                                    arrAgentRec1[intCounter.intValue()].setComm_id(intLevelComm);
	                                 intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                }
	                            }
	                            arrAgentRec1[intCounter.intValue()].setBm(arrAgentRec1[intCounter.intValue()-1].getBm());
	                            arrAgentRec1[intCounter.intValue()].setSbm(arrAgentRec1[intCounter.intValue()-1].getSbm());
	                            i = (i - 1);
	                        }
	                   }
	                }
	                
	            }
	            return arrAgentRec1;
	        }       
	        
	        
	        
	        private void proc_save_agen_comm_rm(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, CommonDao mapper) throws Exception
	        {           
	            Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
	            Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
	            if (tahun_beg_date_polis.intValue() > 2006)
	            {
	                Integer lev_comm = null;
	                if (arrAgentRec[4] != null)
	                {
	                    lev_comm = arrAgentRec[4].getComm_id();
	                }
	                if (lev_comm == null)
	                {
	                    if (arrAgentRec[3] != null)
	                    {
	                        lev_comm = arrAgentRec[3].getComm_id();
	                    }
	                    if (lev_comm == null)
	                    {
	                        if (arrAgentRec[2] != null)
	                        {
	                            lev_comm = arrAgentRec[2].getComm_id();
	                        }
	                        if (lev_comm == null)
	                        {
	                            if ( arrAgentRec[1] != null)
	                            {
	                                lev_comm = arrAgentRec[1].getComm_id();
	                            }
	                            if (lev_comm == null)
	                            {
	                                if (arrAgentRec[0] != null)
	                                {
	                                    lev_comm = arrAgentRec[0].getComm_id();
	                                }
	                            }
	                        }
	                    }
	                }
	                String rm =arrAgentRec[4].getMsag_rm();
	                if (rm==null)
	                {
	                    rm ="";
	                }
	                if (!rm.equalsIgnoreCase(""))
	                {
	                    edit.getAgen().setReg_spaj(strTmpSPAJ);
	                    edit.getAgen().setMsag_id(rm);
	                    edit.getAgen().setLev_comm(new Integer(lev_comm.intValue() - 1));
	                    /*
	                    boolean flag_pwr = products.powerSave(edit.getDatausulan().getLsbs_id().toString());
	                    if (flag_pwr && edit.getAgen().getLev_comm() <  4)
	                    {
	                        
	                    }else{  */
	                        insertMstAgentComm( edit.getAgen(), mapper);
	                        //System.out.println("insert mst_agent_comm"); THN 2007
	                    // }

	                }
	            }
	        }   
	        
	        
	        private void proc_save_agen_artha(Cmdeditbac edit, Agentrec[]  arrAgentArtha,String strTmpSPAJ , Integer i, CommonDao mapper)throws Exception
	        {
	            
	            edit.getAgen().setReg_spaj(strTmpSPAJ);
	            edit.getAgen().setMsag_id(arrAgentArtha[i.intValue()].getAgent_id());
	            edit.getAgen().setLev_comm(arrAgentArtha[i.intValue()].getComm_id());
	            edit.getAgen().setLsla_id(arrAgentArtha[i.intValue()].getLevel_id());
	            insertMstAgentArtha( edit.getAgen(), mapper );
	            //System.out.println("insert mst_agent_artha");
	        }   
	        
	        private void proc_save_agen_rec(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, CommonDao mapper)throws Exception
	        {           
	            edit.getAgen().setReg_spaj(strTmpSPAJ);
	            if((arrAgentRec[i].getLevel_id()).intValue() == 4){//apabila penutupnya level SE baru insert.
	                List<Map<String, Object>> listRekruterNewAgency = selectAgentRekruter(arrAgentRec[i.intValue()].getAgent_id(), mapper);
	                
	                for(int j=0;j<listRekruterNewAgency.size();j++){
	                    Map rekruterNewAgency = listRekruterNewAgency.get(j);
	                    edit.getAgen().setNo_urut(((BigDecimal) rekruterNewAgency.get("URUT")).intValue());
	                    edit.getAgen().setMsag_id((String) rekruterNewAgency.get("KD_AGEN"));
	                    edit.getAgen().setMsrk_level(((BigDecimal) rekruterNewAgency.get("LEV_REKRUTER")).intValue());
	                    edit.getAgen().setLsle_id(((BigDecimal) rekruterNewAgency.get("LSLE_ID")).intValue());
	                    insertMstAgentRekruter(edit.getAgen(), mapper );
	                }
	            }
	        }
	        
	        
	        private Agentrec[] proc_process_agent_artha_2007(String v_strAgentId, CommonDao mapper) throws Exception
	        {
	            
	            Integer intCounter = new Integer(0);
	            Agentrec[]  arrAgentartha1;
	            arrAgentartha1  = new Agentrec[5];
	            String strTmpAgentId=null;
	            String strLeaderId=null;
	            Integer intLevelComm = new Integer(4);
	            Integer intBisnisId =null ;
	            Integer intLevelId =null;
	            Integer strbm=null;
	            Integer strsbm=null;
	            Integer strjenis =null;
	            Integer intCommId =null;
	            Integer ii =new Integer(1);
	            Integer iii = new Integer(1);
	            String lcaid =null;
	            String strrm = null;
	            String cabang =null;

	             strLeaderId = v_strAgentId;
	             if (v_strAgentId.equalsIgnoreCase("000000"))
	             {
	                    strTmpAgentId = strLeaderId;
	                    intBisnisId = new Integer(1);
	                    intLevelId = new Integer(2);
	                    intCommId = new Integer(1);
	                    intCounter = new Integer(intCounter.intValue() + 1);
	                    arrAgentartha1[intCounter.intValue()]= new Agentrec();
	                    arrAgentartha1[intCounter.intValue()].setBisnis_id(intBisnisId);
	                    arrAgentartha1[intCounter.intValue()].setLevel_id(new Integer( 5 - intCounter.intValue()));
	                    arrAgentartha1[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                    if (intCommId==null)
	                    {
	                        intCommId = new Integer(0);
	                    }
	                    if (intCommId.intValue() == 1)
	                    {
	                        arrAgentartha1[intCounter.intValue()].setComm_id(intLevelComm);
	                    }else{
	                        arrAgentartha1[intCounter.intValue()].setComm_id(null);
	                    }
	                    intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                    iii = ii;
	                                        
	                    while (intLevelId.intValue() < iii.intValue())
	                    {
	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        arrAgentartha1[intCounter.intValue()]= new Agentrec();
	                        arrAgentartha1[intCounter.intValue()].setBisnis_id(arrAgentartha1[intCounter.intValue()-1].getBisnis_id());
	                        arrAgentartha1[intCounter.intValue()].setAgent_id(arrAgentartha1[intCounter.intValue()-1].getAgent_id());
	                        arrAgentartha1[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                        arrAgentartha1[intCounter.intValue()].setComm_id(null); 
	                        iii = new Integer(iii.intValue() - 1);  
	                    }
	            
	             }else{
	                 
	                    Map param1 = new HashMap();                         
	                    param1.put("strTmpAgentId",v_strAgentId);
	                    Agen data2 =  selectMstAgent( param1, mapper);
	                    if (data2!=null)
	                    {       
	                        cabang=data2.getLca_id();
	                    }
	                    
	                for (int i = 4 ; i>=1 ; i--)
	                {
	                    
	                   if ((4 - i) == (intCounter.intValue()))
	                   {

	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        strTmpAgentId = strLeaderId;
	                        
	                        Map param = new HashMap();                          
	                        param.put("strTmpAgentId",strTmpAgentId);
	                        data2 =  selectMstAgent( param, mapper);
	                        
	                            if (data2!=null)
	                            {       
	                                intBisnisId = data2.getLstb_id();
	                                intLevelId = data2.getLsle_id();
	                                strLeaderId = data2.getMst_leader();
	                                intCommId = data2.getMsag_komisi();
	                                strbm = data2.getMsag_flag_bm();
	                                strsbm = data2.getMsag_sbm();
	                                strjenis = data2.getMsag_jenis();
	                                lcaid = data2.getLca_id();
	                                strrm = data2.getMsag_rm();
	                                //intLevelId = new Integer(intLevelId.intValue() - 2);
	                                if (intLevelId.intValue() <= 0 )
	                                {
	                                    intLevelId = new Integer(1);
	                                }
	                            }
	                            if (strLeaderId == null)
	                            {
	                                strLeaderId="";
	                            }
	                            //System.out.println("select mst agent");   
	                            if (intLevelId.intValue() == 0   && strsbm.intValue() == 0) 
	                            {
	                                strLeaderId = "";
	                            }
	                            
	                            if (intLevelId.intValue() == 0   && strsbm.intValue() == 1 && strLeaderId.equalsIgnoreCase("")) 
	                            {
	                                strLeaderId = strTmpAgentId;
	                            }
	                            
	                            Integer level_leader = null;
	                            if (!strLeaderId.equalsIgnoreCase(""))
	                            {                       
	                                Map param4 = new HashMap();                         
	                                param4.put("strTmpAgentId",strTmpAgentId);
	                                Agen data4 =  selectMstAgent( param4, mapper);
	                                
	                                if (data4!=null)
	                                {
	                                    level_leader = data4.getLsle_id();
	                                    if ((level_leader.intValue() == 0)  && intLevelId.intValue() == 0 )
	                                    {
	                                        if (i > 0)
	                                        strLeaderId = "";
	                                    }
	                                }
	                            }
	                            
	                            if (i == 4 && strLeaderId.equalsIgnoreCase("") ) 
	                            {
	                                strLeaderId = "000606";
	                            }
	                            
	                        if (intLevelId.intValue() <= i)
	                        {
	                            arrAgentartha1[intCounter.intValue()]= new Agentrec();
	                            arrAgentartha1[intCounter.intValue()].setBisnis_id(intBisnisId);
	                            arrAgentartha1[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                            arrAgentartha1[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                            arrAgentartha1[intCounter.intValue()].setLca_id(lcaid);
	                            arrAgentartha1[intCounter.intValue()].setMsag_rm(strrm);
	                            if (intCommId==null)
	                            {
	                                intCommId = new Integer(0);
	                            }
	                            if (intCommId.intValue() == 1)
	                            {
	                                arrAgentartha1[intCounter.intValue()].setComm_id(intLevelComm);
	                            }else{
	                                arrAgentartha1[intCounter.intValue()].setComm_id(null);
	                            }
	                            arrAgentartha1[intCounter.intValue()].setBm(strbm);
	                            arrAgentartha1[intCounter.intValue()].setSbm(strsbm);
	                            intLevelComm = new Integer(intLevelComm.intValue() - 1);

	                           // iii = new Integer(i);
	                            while ((intLevelId.intValue()) < i)
	                            {
	                                intCounter = new Integer(intCounter.intValue() + 1);
	                                arrAgentartha1[intCounter.intValue()]= new Agentrec();
	                                arrAgentartha1[intCounter.intValue()].setBisnis_id(arrAgentartha1[intCounter.intValue()-1].getBisnis_id());
	                                arrAgentartha1[intCounter.intValue()].setAgent_id(arrAgentartha1[intCounter.intValue()-1].getAgent_id());
	                                arrAgentartha1[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                                arrAgentartha1[intCounter.intValue()].setComm_id(null);
	                                arrAgentartha1[intCounter.intValue()].setLca_id(lcaid);
	                                arrAgentartha1[intCounter.intValue()].setMsag_rm(strrm);
	                                //AGENCY SYSTEM strjenis.intValue() == 7
	                                if ( (cabang.equalsIgnoreCase("46")) && i == 4)
	                                {
	                                    if (intLevelId.intValue() < 4 && intCommId.intValue() == 1)
	                                    {
	                                        arrAgentartha1[intCounter.intValue()].setComm_id(intLevelComm);
	                                     intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                    }
	                                }
	                                arrAgentartha1[intCounter.intValue()].setBm(arrAgentartha1[intCounter.intValue()-1].getBm());
	                                arrAgentartha1[intCounter.intValue()].setSbm(arrAgentartha1[intCounter.intValue()-1].getSbm());

	                                i = (i - 1);
	                            }
	                        }else{
	                            i=i+1;
	                            intCounter = intCounter - 1;
	                        }
	                   }
	                }
	            }
	            
	            return arrAgentartha1;
	        }
	        
	        
	        
	        
	        private Agentrec[] proc_agent_artha(String v_strAgentId, CommonDao mapper) throws Exception
	        {
	            
	            Integer intCounter = new Integer(0);
	            Agentrec[]  arrAgentArtha;
	            arrAgentArtha  = new Agentrec[7];
	            String strTmpAgentId=null;
	            String strLeaderId=null;
	            Integer intLevelComm = new Integer(6);
	            Integer intBisnisId =null ;
	            Integer intLevelId =null;
	            Integer intLevelId1 = new Integer(2);
	            Integer strbm=null;
	            Integer strsbm=null;
	            Integer strjenis =null;
	            Integer intCommId =null;
	            Integer ii =new Integer(1);
	            Integer iii = new Integer(1);
	            String lcaid =null;
	            String strrm = null;
	            String cabang =null;

	             strLeaderId = v_strAgentId;
	             if (v_strAgentId.equalsIgnoreCase("000000"))
	             {
	                    strTmpAgentId = strLeaderId;
	                    intBisnisId = new Integer(1);
	                    intLevelId = new Integer(2);
	                    intCommId = new Integer(1);
	                    intCounter = new Integer(intCounter.intValue() + 1);
	                    arrAgentArtha[intCounter.intValue()]= new Agentrec();
	                    arrAgentArtha[intCounter.intValue()].setBisnis_id(intBisnisId);
	                    arrAgentArtha[intCounter.intValue()].setLevel_id(new Integer( 7 - intCounter.intValue()));
	                    arrAgentArtha[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                    if (intCommId==null)
	                    {
	                        intCommId = new Integer(0);
	                    }
	                    if (intCommId.intValue() == 1)
	                    {
	                        arrAgentArtha[intCounter.intValue()].setComm_id(intLevelComm);
	                    }else{
	                        arrAgentArtha[intCounter.intValue()].setComm_id(null);
	                    }
	                    intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                    iii = ii;
	                                        
	                    while (intLevelId.intValue() < iii.intValue())
	                    {
	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        arrAgentArtha[intCounter.intValue()]= new Agentrec();
	                        arrAgentArtha[intCounter.intValue()].setBisnis_id(arrAgentArtha[intCounter.intValue()-1].getBisnis_id());
	                        arrAgentArtha[intCounter.intValue()].setAgent_id(arrAgentArtha[intCounter.intValue()-1].getAgent_id());
	                        arrAgentArtha[intCounter.intValue()].setLevel_id(new Integer(7 - intCounter.intValue()));
	                        arrAgentArtha[intCounter.intValue()].setComm_id(null);  
	                        iii = new Integer(iii.intValue() - 1);  
	                    }
	            
	             }else{
	                 
	                    Map param1 = new HashMap();                         
	                    param1.put("strTmpAgentId",v_strAgentId);
	                    Agen data2 =  selectMstAgent( param1, mapper);
	                    if (data2!=null)
	                    {       
	                        cabang= data2.getLca_id();
	                    }
	                    
	                for (int i = 6 ; i>=1 ; i--)
	                {
	                    
	                   if ((6 - i) == (intCounter.intValue()))
	                   {

	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        strTmpAgentId = strLeaderId;
	                        
	                            Map param = new HashMap();  
	                            param.put("strTmpAgentId",strTmpAgentId);
	                                data2 =  selectMstAgent( param, mapper);
	                            if (data2!=null)
	                            {       
	                                cabang= data2.getLca_id();
	                            }
	                            
	                            if (data2!=null)
	                            {       
	                                intBisnisId = data2.getLstb_id();
	                                intLevelId = data2.getLsle_id();
	                                strLeaderId = data2.getMst_leader();
	                                intCommId = data2.getMsag_komisi();
	                                strbm = data2.getMsag_flag_bm();
	                                strsbm = data2.getMsag_sbm();
	                                strjenis = data2.getMsag_jenis();
	                                lcaid = data2.getLca_id();
	                                strrm = data2.getMsag_rm();
	                                if (intLevelId.intValue()>0)
	                                {
	                                    intLevelId1 = new Integer(intLevelId.intValue() + 2);
	                                }else{
	                                    if (strsbm.intValue() == 1)
	                                    {
	                                        intLevelId1  = new Integer(2);
	                                    }else{
	                                        intLevelId1 = new Integer(1);
	                                    }
	                                }
	                            }
	                            
	                            //System.out.println("select mst agent");       
	                            if (i == 6 && strLeaderId.equalsIgnoreCase("") ) 
	                            {
	                                strLeaderId = strTmpAgentId;
	                                if (intLevelId.intValue() == 0  && strsbm.intValue() == 1)
	                                {
	                                    strLeaderId = strrm;
	                                }
	                            }
	                            
	                            if (intLevelId.intValue() == 0   && strsbm.intValue() == 1 && strLeaderId.equalsIgnoreCase("")) 
	                            {
	                                strLeaderId = strrm;
	                            }
	                    int b =i-2;
	                    if (b <0)
	                    {
	                        b= 1;
	                    }
	                    if (intLevelId.intValue() <= b)
	                    {       
	                        arrAgentArtha[intCounter.intValue()]= new Agentrec();
	                        arrAgentArtha[intCounter.intValue()].setBisnis_id(intBisnisId);
	                        arrAgentArtha[intCounter.intValue()].setLevel_id(new Integer(7 - intCounter.intValue()));
	                        arrAgentArtha[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                        arrAgentArtha[intCounter.intValue()].setLca_id(lcaid);
	                        arrAgentArtha[intCounter.intValue()].setMsag_rm(strrm);
	                        if (intCommId==null)
	                        {
	                            intCommId = new Integer(0);
	                        }
	                        if (intCommId.intValue() == 1)
	                        {
	                            arrAgentArtha[intCounter.intValue()].setComm_id(intLevelComm);
	                        }else{
	                            arrAgentArtha[intCounter.intValue()].setComm_id(null);
	                        }
	                        arrAgentArtha[intCounter.intValue()].setBm(strbm);
	                        arrAgentArtha[intCounter.intValue()].setSbm(strsbm);
	                        intLevelComm = new Integer(intLevelComm.intValue() - 1);

	                       // iii = new Integer(i);
	                        while (intLevelId1.intValue() < i)
	                        {
	                            intCounter = new Integer(intCounter.intValue() + 1);
	                            arrAgentArtha[intCounter.intValue()]= new Agentrec();
	                            arrAgentArtha[intCounter.intValue()].setBisnis_id(arrAgentArtha[intCounter.intValue()-1].getBisnis_id());
	                            arrAgentArtha[intCounter.intValue()].setAgent_id(arrAgentArtha[intCounter.intValue()-1].getAgent_id());
	                            arrAgentArtha[intCounter.intValue()].setLevel_id(new Integer(7 - intCounter.intValue()));
	                            arrAgentArtha[intCounter.intValue()].setComm_id(null);
	                            arrAgentArtha[intCounter.intValue()].setLca_id(lcaid);
	                            arrAgentArtha[intCounter.intValue()].setMsag_rm(strrm);
	                            //AGENCY SYSTEM strjenis.intValue() == 7
	                            if ( cabang.equalsIgnoreCase("46") && i == 6)
	                            {
	                                if (intLevelId1.intValue() < 6 && intCommId.intValue() == 1)
	                                {
	                                    arrAgentArtha[intCounter.intValue()].setComm_id(intLevelComm);
	                                 intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                }
	                            }
	                            arrAgentArtha[intCounter.intValue()].setBm(arrAgentArtha[intCounter.intValue()-1].getBm());
	                            arrAgentArtha[intCounter.intValue()].setSbm(arrAgentArtha[intCounter.intValue()-1].getSbm());

	                            i = (i - 1);
	                        }
	                    }else{
	                        i=i+1;
	                        intCounter = intCounter - 1;
	                    }
	                   }
	                }
	            }
	            
	            return arrAgentArtha;
	        }   
	            
	        private void proc_save_agen_comm(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, CommonDao mapper)throws Exception
	        {
	            
	            	DateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	                
	                edit.getAgen().setReg_spaj(strTmpSPAJ);
	                edit.getAgen().setMsag_id(arrAgentRec[i.intValue()].getAgent_id());
	                
	                //lca_id 63 untuk ACD (Agency Career Development) //Anta
	                if(edit.getAgen().getLca_id().equals("58") || edit.getAgen().getLca_id().equals("63") ){
	                    edit.getAgen().setLev_comm(arrAgentRec[i.intValue()].getLevel_id());
	                }else{
	                    edit.getAgen().setLev_comm(arrAgentRec[i.intValue()].getComm_id());
	                }
	                

	                boolean dapatKomisi = true; 
	                //Yusuf - 16 Juli 09 - Request mba Wesni helpdesk #13943
	                //bila yg nutup itu, bukan level ME (1,2,3), khusus worksite, maka OR dan komisi hilang semua
	                if(arrAgentRec.length > 1){
	                    if(arrAgentRec[i].getAgent_id() != null){
	                        Map param = new HashMap();                          
	                        param.put("strTmpAgentId", arrAgentRec[i].getAgent_id());
	                        Agen data2 =  selectMstAgent( param, mapper);
	                        if(data2 != null){
	                            String lca_id       =  data2.getLca_id();
	                            BigDecimal lsle_id  = new BigDecimal (data2.getLsle_id() );
	                            if(lca_id != null && lsle_id != null){
	                                if(lca_id.equals("42")){
	                                    param = new HashMap();
	                                    param.put("strTmpAgentId", arrAgentRec[i].getAgent_id());                       
	                                    data2 =  selectMstAgent( param, mapper);
	                                    lca_id      =  data2.getLca_id();
	                                    lsle_id     = new BigDecimal(arrAgentRec[i].getLevel_id());
	                                }
	                                if(lca_id.equals("42") && lsle_id.intValue() < 4){
	                                    dapatKomisi = false;
	                                }                       
	                                if(lca_id.equals("42") && selectIsAgenCorporate(arrAgentRec[1].getAgent_id())==1 && lsle_id.intValue()==3){
	                                    dapatKomisi=true;
	                                }                   
	                                if(lca_id.equals("42") && selectIsAgenCorporate(arrAgentRec[1].getAgent_id())==0 && lsle_id.intValue()==3 && selectIsAgenKaryawan(arrAgentRec[i].getAgent_id())!=1){
	                                    dapatKomisi=true;
	                                }                       
	                                if(lca_id.equals("42") && selectIsAgenKaryawan(arrAgentRec[i].getAgent_id())==1){
	                                    Date TigaSatuJanuaryDuaRibuSepuluh=null;
	                                    try {
	                                        TigaSatuJanuaryDuaRibuSepuluh = defaultDateFormat.parse("31/1/2010");
	                                    } catch (ParseException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                                    }
	                                    if(edit.getDatausulan().getMste_beg_date().after(TigaSatuJanuaryDuaRibuSepuluh)){
	                                        dapatKomisi=false;
	                                    }
	                                }
	                            
	                                if(edit.getDatausulan().getLsbs_id().toString().equals("096") && (edit.getDatausulan().getLsdbs_number()>=13 && edit.getDatausulan().getLsdbs_number()<=15)){
	                                    if(lca_id.equals("37") && edit.getAgen().getLwk_id().equals("A8")){
	                                        lsle_id     = new BigDecimal(arrAgentRec[i].getLevel_id());
	                                        if(lsle_id.intValue()==4){
	                                            dapatKomisi=true;
	                                        }else{
	                                            dapatKomisi=false;
	                                        }
	                                    }else if(lca_id.equals("37") && edit.getAgen().getLwk_id().equals("A9")){
	                                        lsle_id     = new BigDecimal(arrAgentRec[i].getLevel_id());
	                                        if(lsle_id.intValue()!=1){
	                                            dapatKomisi=true;
	                                        }else{
	                                            dapatKomisi=false;
	                                        }
	                                    }
	                                }
	                                //Deddy : Semua lca_id 09, tidak perlu proses komisi individu, hanya proses komisi reff bii
	                                if(lca_id.equals("09")){
	                                    dapatKomisi = false;
	                                }
	                                
	                            }
	                        }
	                    }
	                }           
	                //lca_id 63 untuk ACD (Agency Career Development) //Anta
	                //Agen penutup lev 4 saja yg dapat komisi
	                if(edit.getAgen().getLca_id().equals("63") && edit.getAgen().getLev_comm() < 4){
	                    dapatKomisi = false;
	                }
	                //Reference
	                if(edit.getAgen().getJenis_ref()==null){
	                    //ga usah diapa2in
	                }
	                else if(edit.getAgen().getJenis_ref().intValue()==0){
	                    if(edit.getAgen().getLev_comm() < 4){
	                        dapatKomisi = false;
	                    }
	                }
	                //MANTA - Khusus jalur Broker MNC 
	                if(edit.getAgen().getLca_id().equals("62") && edit.getBroker().isFlagbroker() == true){
	                    dapatKomisi = false;
	                }
	                
	                if(dapatKomisi){
	                    insertMstAgentComm(edit.getAgen(), mapper);
	                    //System.out.println("insert mst_agent_comm");
	                }   
	        }   
	            
	            
	        private void proc_save_agen_prod(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, Integer flag_sbm, Integer lvl_fcd,  CommonDao mapper)throws Exception
	        {
	                
	                edit.getAgen().setReg_spaj(strTmpSPAJ);
	                edit.getAgen().setLstb_id(arrAgentRec[i.intValue()].getBisnis_id());
	                edit.getAgen().setMsag_id(arrAgentRec[i.intValue()].getAgent_id());
	                if((edit.getAgen().getLca_id().equals("62") && edit.getBroker().isFlagbroker() == true) || (edit.getAgen().getKode_regional().equals("37M1") && edit.getAgen().getLsrg_id().equals("03")) ||
	                        (edit.getAgen().getKode_regional().equals("37Y6") && edit.getAgen().getLsrg_id().equals("00"))){
	                    edit.getAgen().setLev_comm(null);
	                }else{
	                    edit.getAgen().setLev_comm(arrAgentRec[i.intValue()].getComm_id());
	                }
	                edit.getAgen().setLsle_id(arrAgentRec[i.intValue()].getLevel_id());
	                edit.getAgen().setFlag_sbm(flag_sbm);
	                edit.getAgen().setLvl_fcd(lvl_fcd);
	                
	               /// insertMstAgentProd( edit.getAgen(), mapper );
	                //System.out.println("insert mst_agent_prod");
	        }           
	            
	            
	        private Agentrec[] proc_process_agent(String v_strAgentId, CommonDao mapper) throws Exception
	        {       
	            
	            Integer intCounter = new Integer(0);
	            Agentrec[]  arrAgentRec;
	            arrAgentRec  = new Agentrec[5];
	            String strTmpAgentId=null;
	            String strLeaderId=null;
	            Integer intLevelComm = new Integer(4);
	            Integer intBisnisId =null ;
	            Integer intLevelId =null;
	            Integer strbm=null;
	            Integer strsbm=null;
	            Integer strjenis =null;
	            Integer intCommId =null;
	            Integer ii =new Integer(1);
	            Integer iii = new Integer(1);

	             strLeaderId = v_strAgentId;
	             if (v_strAgentId.equalsIgnoreCase("000000"))
	             {
	                    strTmpAgentId = strLeaderId;
	                    intBisnisId = new Integer(1);
	                    intLevelId = new Integer(2);
	                    intCommId = new Integer(1);
	                    intCounter = new Integer(intCounter.intValue() + 1);
	                    arrAgentRec[intCounter.intValue()]= new Agentrec();
	                    arrAgentRec[intCounter.intValue()].setBisnis_id(intBisnisId);
	                    arrAgentRec[intCounter.intValue()].setLevel_id(new Integer( 5 - intCounter.intValue()));
	                    arrAgentRec[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                    if (intCommId==null)
	                    {
	                        intCommId = new Integer(0);
	                    }
	                    if (intCommId.intValue() == 1)
	                    {
	                        arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                    }else{
	                        arrAgentRec[intCounter.intValue()].setComm_id(null);
	                    }
	                    intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                    iii = ii;
	                    
	                    while (intLevelId.intValue() < iii.intValue())
	                    {
	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        arrAgentRec[intCounter.intValue()]= new Agentrec();
	                        arrAgentRec[intCounter.intValue()].setBisnis_id(arrAgentRec[intCounter.intValue()-1].getBisnis_id());
	                        arrAgentRec[intCounter.intValue()].setAgent_id(arrAgentRec[intCounter.intValue()-1].getAgent_id());
	                        arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                        arrAgentRec[intCounter.intValue()].setComm_id(null);    
	                        iii = new Integer(iii.intValue() - 1);  
	                    }               
	             }else{
	                    for (int i = 4 ; i>=1 ; i--)
	                    {
	                           if ((4 - i) == (intCounter.intValue()))
	                           {

	                                intCounter = new Integer(intCounter.intValue() + 1);
	                                strTmpAgentId = strLeaderId;
	                                
	                                    Map param = new HashMap();                          
	                                    param.put("strTmpAgentId",strTmpAgentId);
	                                    Agen data2 =  selectMstAgent( param, mapper);
	                                    if (data2!=null)
	                                    {       
	                                        intBisnisId = data2.getLstb_id();
	                                        intLevelId = data2.getLsle_id();
	                                        strLeaderId = data2.getMst_leader();
	                                        intCommId = data2.getMsag_komisi();
	                                        strbm = data2.getMsag_flag_bm();
	                                        strsbm = data2.getMsag_sbm();
	                                        strjenis = data2.getMsag_jenis();
	                                    }
	                                    if (i == 4 && strLeaderId.equalsIgnoreCase("") ) 
	                                    {
	                                        strLeaderId = "000606";
	                                    }
	                                    arrAgentRec[intCounter.intValue()]= new Agentrec();
	                                    arrAgentRec[intCounter.intValue()].setBisnis_id(intBisnisId);
	                                    arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                                    arrAgentRec[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                                    if (intCommId==null)
	                                    {
	                                        intCommId = new Integer(0);
	                                    }
	                                    if (intCommId.intValue() == 1)
	                                    {
	                                        arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                                    }else{
	                                        arrAgentRec[intCounter.intValue()].setComm_id(null);
	                                    }
	                                    arrAgentRec[intCounter.intValue()].setBm(strbm);
	                                    arrAgentRec[intCounter.intValue()].setSbm(strsbm);
	                                    intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                    // iii = new Integer(i);
	                                    while (intLevelId.intValue() < i)
	                                    {
	                                        intCounter = new Integer(intCounter.intValue() + 1);
	                                        arrAgentRec[intCounter.intValue()]= new Agentrec();
	                                        arrAgentRec[intCounter.intValue()].setBisnis_id(arrAgentRec[intCounter.intValue()-1].getBisnis_id());
	                                        arrAgentRec[intCounter.intValue()].setAgent_id(arrAgentRec[intCounter.intValue()-1].getAgent_id());
	                                        arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                                        arrAgentRec[intCounter.intValue()].setComm_id(null);
	                                        //AGENCY SYSTEM strjenis.intValue() == 7
	                                        if (strjenis.intValue() == 7 && i == 4)
	                                        {
	                                            if (intLevelId.intValue() < 4 && intCommId.intValue() == 1)
	                                            {
	                                                arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                                             intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                            }   
	                                        }
	                                        arrAgentRec[intCounter.intValue()].setBm(arrAgentRec[intCounter.intValue()-1].getBm());
	                                        arrAgentRec[intCounter.intValue()].setSbm(arrAgentRec[intCounter.intValue()-1].getSbm());

	                                        i = (i - 1);
	                                    }
	                               }
	                            }
	                        }
	                        
	                        return arrAgentRec;
	            }
	            
	        
	        public Agentrec[] proc_process_agent_2007(String v_strAgentId, Integer flag, CommonDao mapper) throws Exception
	        {   
	            
	            //flag default 1 : untuk struktur agent berdasarkan mst_leader, 2 : untuk struktur agent berdasarkan mst_leader_comm
	            Integer intCounter = new Integer(0);
	            Agentrec[]  arrAgentRec;
	            arrAgentRec  = new Agentrec[5];
	            String strTmpAgentId=null;
	            String strLeaderId=null;
	            Integer intLevelComm = new Integer(4);
	            Integer intBisnisId =null ;
	            Integer intLevelId =null;
	            Integer strbm=null;
	            Integer strsbm=null;
	            Integer strjenis =null;
	            Integer intCommId =null;
	            Integer ii =new Integer(1);
	            Integer iii = new Integer(1);
	            String lcaid =null;
	            String strrm = null;
	            String cabang =null;
	            Integer lvl_fcd=null;

	             strLeaderId = v_strAgentId;
	             if (v_strAgentId.equalsIgnoreCase("000000"))
	             {
	                    strTmpAgentId = strLeaderId;
	                    intBisnisId = new Integer(1);
	                    intLevelId = new Integer(2);
	                    intCommId = new Integer(1);
	                    intCounter = new Integer(intCounter.intValue() + 1);
	                    arrAgentRec[intCounter.intValue()]= new Agentrec();
	                    arrAgentRec[intCounter.intValue()].setBisnis_id(intBisnisId);
	                    arrAgentRec[intCounter.intValue()].setLevel_id(new Integer( 5 - intCounter.intValue()));
	                    arrAgentRec[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                    if (intCommId==null)
	                    {
	                        intCommId = new Integer(0);
	                    }
	                    if (intCommId.intValue() == 1)
	                    {
	                        arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                    }else{
	                        arrAgentRec[intCounter.intValue()].setComm_id(null);
	                    }
	                    intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                    iii = ii;
	                    while (intLevelId.intValue() < iii.intValue())
	                    {
	                        intCounter = new Integer(intCounter.intValue() + 1);
	                        arrAgentRec[intCounter.intValue()]= new Agentrec();
	                        arrAgentRec[intCounter.intValue()].setBisnis_id(arrAgentRec[intCounter.intValue()-1].getBisnis_id());
	                        arrAgentRec[intCounter.intValue()].setAgent_id(arrAgentRec[intCounter.intValue()-1].getAgent_id());
	                        arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                        arrAgentRec[intCounter.intValue()].setComm_id(null);    
	                        iii = new Integer(iii.intValue() - 1);  
	                    }               
	             }else{          
	                    Map param1 = new HashMap();                         
	                    param1.put("strTmpAgentId",v_strAgentId);
	                    param1.put("flag", flag);
	                    Agen datautama =  selectMstAgent( param1, mapper);  
	                    if (datautama!=null)
	                    {       
	                        cabang = datautama.getLca_id();
	                    }
	                    for (int i = 4 ; i>=1 ; i--)
	                    {
	                        
	                       if ((4 - i) == (intCounter.intValue()))
	                       {

	                            intCounter = new Integer(intCounter.intValue() + 1);
	                            strTmpAgentId = strLeaderId;
	                            Map param = new HashMap();                          
	                            param.put("strTmpAgentId",strTmpAgentId);
	                            param.put("flag", flag);
	                            Agen data2 =  selectMstAgent( param, mapper);   
	                            if (data2!=null){
	                                intBisnisId = data2.getLstb_id();
	                                intLevelId = data2.getLsle_id();
	                                strLeaderId = data2.getMst_leader();
	                                intCommId = data2.getMsag_komisi();
	                                strbm = data2.getMsag_flag_bm();
	                                strsbm = data2.getMsag_sbm();
	                                strjenis = data2.getMsag_jenis();
	                                lcaid = data2.getLca_id();
	                                strrm = data2.getMsag_rm();                         
	                            }
	                            
	                            //System.out.println("select mst agent");       
	                            if(strLeaderId == null){
	                                strLeaderId="";
	                            }                           
	                            if (i == 4 && strLeaderId.equalsIgnoreCase("") )
	                            {   
	                                strLeaderId = "000606";
	                            }
	                            arrAgentRec[intCounter.intValue()]= new Agentrec();
	                            arrAgentRec[intCounter.intValue()].setBisnis_id(intBisnisId);
	                            arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                            arrAgentRec[intCounter.intValue()].setAgent_id(strTmpAgentId);
	                            arrAgentRec[intCounter.intValue()].setLca_id(lcaid);
	                            arrAgentRec[intCounter.intValue()].setMsag_rm(strrm);
	                            if (intCommId==null)
	                            {
	                                intCommId = new Integer(0);
	                            }
	                            if (intCommId.intValue() == 1)
	                            {
	                                arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                            }else{
	                                arrAgentRec[intCounter.intValue()].setComm_id(null);
	                            }
	                            arrAgentRec[intCounter.intValue()].setBm(strbm);
	                            arrAgentRec[intCounter.intValue()].setSbm(strsbm);
	                            intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                            
	                            if(intLevelId==4 && cabang.equals("65")){
	                                arrAgentRec[intCounter.intValue()].setLvl_fcd((Integer)data2.getLvl_fcd());
	                            }
	                            
	                            // iii = new Integer(i);
	                            while (intLevelId.intValue() < i)
	                            {
	                                try {
	                                    intCounter = new Integer(intCounter.intValue() + 1);
	                                    arrAgentRec[intCounter.intValue()]= new Agentrec();
	                                    arrAgentRec[intCounter.intValue()].setBisnis_id(arrAgentRec[intCounter.intValue()-1].getBisnis_id());
	                                    arrAgentRec[intCounter.intValue()].setAgent_id(arrAgentRec[intCounter.intValue()-1].getAgent_id());
	                                    arrAgentRec[intCounter.intValue()].setLevel_id(new Integer(5 - intCounter.intValue()));
	                                    arrAgentRec[intCounter.intValue()].setComm_id(null);
	                                    arrAgentRec[intCounter.intValue()].setLca_id(lcaid);
	                                    arrAgentRec[intCounter.intValue()].setMsag_rm(strrm);
	                                    
	                                  //AGENCY SYSTEM strjenis.intValue() == 7
	                                    if ( (strjenis.intValue() == 7 || strjenis.intValue() == 1 ||(cabang.equalsIgnoreCase("46")) || !(cabang.equalsIgnoreCase("08")|| cabang.equalsIgnoreCase("09")  || cabang.equalsIgnoreCase("42") ) )&& i == 4)
	                                    //  if ( (strjenis.intValue() == 7 || strjenis.intValue() == 1 || !(cabang.equalsIgnoreCase("08")|| cabang.equalsIgnoreCase("09")  || cabang.equalsIgnoreCase("42") ) )&& i == 4)
	                    
	                                    {
	                                        if (intLevelId.intValue() < 4 && intCommId.intValue() == 1)
	                                        {
	                                            arrAgentRec[intCounter.intValue()].setComm_id(intLevelComm);
	                                         intLevelComm = new Integer(intLevelComm.intValue() - 1);
	                                        }
	                                    }    
	                                    arrAgentRec[intCounter.intValue()].setBm(arrAgentRec[intCounter.intValue()-1].getBm());
	                                    arrAgentRec[intCounter.intValue()].setSbm(arrAgentRec[intCounter.intValue()-1].getSbm());                           
	                                }catch(ArrayIndexOutOfBoundsException aioobe) {
	                                    
	                                }
	                                i = (i - 1);                                
	                            }
	                       }
	                    }
	                }
	                
	                return arrAgentRec;
	            }
	        
	        
	        
	        public void proc_save_product_insured(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Integer flag_jenis_plan, Date ldt_endpay1, User currentUser, CommonDao mapper)throws ServletException,IOException
	        {
	            
	            Integer intClass =null;
	            Integer v_intClass = edit.getDatausulan().getMspr_class();
	            Integer v_intBaseBusinessId = edit.getDatausulan().getLsbs_id();
	            Integer v_intBaseBusinessNo = edit.getDatausulan().getLsdbs_number();
	            Double v_curBasePremium = edit.getDatausulan().getMspr_premium();
	            Date v_strBeginDate = edit.getDatausulan().getMste_beg_date();
	            String v_strKurs = edit.getDatausulan().getKurs_p().toUpperCase();
	            Date v_strEndDate = edit.getDatausulan().getMste_end_date();
	            Double v_intBaseRate = edit.getDatausulan().getRate_plan(); 
	            Integer v_intInsPeriod = edit.getDatausulan().getMspr_ins_period();
	            Double v_curUP = edit.getDatausulan().getMspr_tsi();
	            intClass = v_intClass;
	            Double disc = edit.getDatausulan().getMspr_discount();
	            if(disc==null){
	                disc=0.;
	            }

	            //request dr. ingrid - untuk semua produk powersave, apabila usia tertanggung >= 69, maka UP = 0.5 PREMI, dengan nilai max Rp. 100 jt / $10.000 (Yusuf - 11/03/2008)

//	          ------------------------------------------------------------
	            // Insert Basic Plan information to MST_PRODUCT_INSURED
	            if (flag_jenis_plan.intValue()==4)
	            {
	                intClass = v_intClass;
	                Map param28=new HashMap();
	                param28.put("strTmpSPAJ",strTmpSPAJ);   
	                param28.put("v_intBaseBusinessId",v_intBaseBusinessId);
	                param28.put("v_intBaseBusinessNo",v_intBaseBusinessNo);
	                param28.put("v_strKurs",v_strKurs);
	                param28.put("v_strBeginDate",v_strBeginDate);
	                param28.put("v_strEndDate",v_strEndDate);
	                param28.put("intClass",intClass);
	                param28.put("v_intBaseRate",v_intBaseRate);
	                param28.put("v_curBasePremium",v_curBasePremium);
	                param28.put("v_intInsPeriod",v_intInsPeriod);
	                param28.put("v_curUP",v_curUP);
	                param28.put("mspr_discount",disc);
	                inserMst_product_insured45( param28, mapper );
	                
	                //System.out.println("insert mst product insured");
	            }else if (flag_jenis_plan.intValue() == 5)
	                    {
	                        v_intClass = new Integer(1);
	                        Double v_curUP_A = new Double(0);
	                        Double v_curUP_B = new Double(0);
	                        Double v_curUP_D = new Double(0);
	                        if (v_intBaseBusinessNo.intValue() == 2)
	                        {
	                            if(FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("134") || FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("166")){
	                                v_curUP_A = null;
	                            }else{
	                                v_curUP_A = v_curUP;
	                            }
	                        }else if (v_intBaseBusinessNo.intValue() == 3)
	                            {
	                            if(FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("134") || FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("166")){
	                                v_curUP_A = null;
	                                v_curUP_B = null;
	                            }else{
	                                v_curUP_A = v_curUP;
	                                v_curUP_B = v_curUP;
	                            }
	                            }else{
	                                
	                                if(FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("134") || FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("166")){
	                                    v_curUP_A = null;
	                                    v_curUP_B = null;
	                                    v_curUP_D = null;
	                                }else{
	                                    v_curUP_A = v_curUP;
	                                    v_curUP_B = v_curUP;
	                                    v_curUP_D = v_curUP;
	                                }
	                                
	                            }
	                        intClass = v_intClass;
	                        Map param28=new HashMap();
	                        param28.put("strTmpSPAJ",strTmpSPAJ);   
	                        param28.put("v_intBaseBusinessId",v_intBaseBusinessId);
	                        param28.put("v_intBaseBusinessNo",v_intBaseBusinessNo);
	                        param28.put("v_strKurs",v_strKurs);
	                        param28.put("v_strBeginDate",v_strBeginDate);
	                        param28.put("v_strEndDate",v_strEndDate);
	                        param28.put("intClass",intClass);
	                        param28.put("v_intBaseRate",v_intBaseRate);
	                        param28.put("v_curBasePremium",v_curBasePremium);
	                        param28.put("v_intInsPeriod",v_intInsPeriod);
	                        param28.put("v_curUP",v_curUP);
	                        param28.put("v_curUP_A",v_curUP_A);
	                        param28.put("v_curUP_B",v_curUP_B);
	                        param28.put("v_curUP_D",v_curUP_D);
	                        param28.put("mspr_discount",disc);
	                        insertMst_product_insuredPA( param28, mapper );
	                        //System.out.println("insert mst product insured");
	                    }else{
	                        Map param28=new HashMap();
	                        param28.put("strTmpSPAJ",strTmpSPAJ);   
	                        param28.put("v_intBaseBusinessId",v_intBaseBusinessId);
	                        param28.put("v_intBaseBusinessNo",v_intBaseBusinessNo);
	                        param28.put("v_strKurs",v_strKurs);
	                        param28.put("v_strBeginDate",v_strBeginDate);
	                        param28.put("v_strEndDate",v_strEndDate);
	                        param28.put("v_intBaseRate",v_intBaseRate);
	                        param28.put("v_curBasePremium",v_curBasePremium);
	                        param28.put("v_intInsPeriod",v_intInsPeriod);
	                        param28.put("v_curUP",v_curUP);
	                        param28.put("ldt_endpay",ldt_endpay1);
	                        param28.put("mspr_discount",disc);
	                        inserMst_product_insured1( param28, mapper );
	                        
	                        //lufi- setiap edit dan input baru Powersave keluarga syariah nextbayar=mspr_beg_date
	                        if(FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("175") || FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("174")|| FormatString.rpad("0", v_intBaseBusinessId.toString(), 3).equals("176")){
	                            //uwDao.updateNextBayar(strTmpSPAJ, v_strBeginDate);
	                        }
	                        //System.out.println("insert mst product insured");
	                        /*if (v_intBaseBusinessId.intValue()==134)
	                        {
	                            if (ldt_endpay1.getYear() != v_strEndDate.getYear())
	                            {
	                                email.send(
	                                        new String[] {props.getProperty("admin.yusuf")}, null,
	                                        "PLATINUM LINK SALAH END PAY DATE NYA CEK LAGI, end pay tahunnya tidak sama dengan end date polis dengan spaj no " + strTmpSPAJ + " oleh "+ currentUser.getName() + " ["+currentUser.getDept()+"]",
	                                        strTmpSPAJ, currentUser);
	                                System.out.print("PLATINUM LINK SALAH END PAY DATE NYA CEK LAGI, end pay tahunnya tidak sama dengan end date polis");
	                                throw new RuntimeException("Tanggal End Pay tidak sama dengan tanggal End Date Polis.");
	                            }
	                        }*/
	                    }
	        }
	        
	        private void proc_save_rider(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao mapper )throws ServletException,IOException
	        {
	            
	        
	            String v_strKurs = edit.getDatausulan().getKurs_p().toUpperCase();
	            Integer jumlah_rider = edit.getDatausulan().getJmlrider();
	            Integer[] v_intRiderId;
	            v_intRiderId = new Integer[jumlah_rider.intValue()+1];
	            Integer[] v_intRiderNo;
	            v_intRiderNo = new Integer[jumlah_rider.intValue()+1];
	            Integer[] unitRider;
	            unitRider = new Integer[jumlah_rider.intValue()+1];
	            Integer[] classRider;
	            classRider = new Integer[jumlah_rider.intValue()+1];
	            Integer[] insperiodRider;
	            insperiodRider = new Integer[jumlah_rider.intValue()+1];
	            Double[] upRider;
	            upRider = new Double[jumlah_rider.intValue()+1];
	            Date[] end_dateRider;
	            end_dateRider = new Date[jumlah_rider.intValue()+1];
	            Date[] beg_dateRider;
	            beg_dateRider = new Date[jumlah_rider.intValue()+1];
	            Date[] end_payRider;
	            end_payRider = new Date[jumlah_rider.intValue()+1];
	            Double[] rateRider;
	            rateRider = new Double[jumlah_rider.intValue()+1];
	            Integer[] percentRider;
	            percentRider = new Integer[jumlah_rider.intValue()+1];
	            Double[] premi_rider;
	            premi_rider = new Double[jumlah_rider.intValue()+1];
	            //(Deddy)           
	            Double[] premi_tahunan;
	            premi_tahunan = new Double[jumlah_rider.intValue()+1];
	            String[] kursRider;
	            kursRider = new String[jumlah_rider.intValue()+1];
	            Double[] premi_arider;
	            premi_arider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_brider;
	            premi_brider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_crider;
	            premi_crider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_drider;
	            premi_drider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_mrider;
	            premi_mrider = new Double[jumlah_rider.intValue()+1];
	            Integer[] ins_rider;
	            ins_rider = new Integer[jumlah_rider.intValue()+1];
	            Double[] mspr_extra;
	            mspr_extra = new Double[jumlah_rider.intValue()+1];
	            //(Deddy)
	            Integer[] mpr_cara_bayar_rider;
	            Integer[] lscb_id_rider;
	            Integer flag_ekasehat = 0;
	            mpr_cara_bayar_rider = new Integer[jumlah_rider.intValue()+1];
	            lscb_id_rider = new Integer[jumlah_rider.intValue()+1];
	            String status = edit.getPemegang().getStatus();
	            Integer[] flag_jenis_up;
	            flag_jenis_up=new Integer[jumlah_rider.intValue()+1];
	            if (status == null)
	            {
	                status = "input";
	            }
	            Integer flag_hcp= new Integer(0);
	            Integer flag_rider_hcp =edit.getDatausulan().getFlag_rider_hcp();
	            if (flag_rider_hcp == null)
	            {
	                flag_rider_hcp = new Integer(0);
	            }
	            
	            List dtrd = edit.getDatausulan().getDaftaRider();
	            if (jumlah_rider.intValue()>0)
	            {
	                for (int i =0 ; i<dtrd.size();i++)
	                {
	                    Double diskon_karyawan = 1.0;
	                    
	                    Datarider rd= (Datarider)dtrd.get(i);
	                    v_intRiderId[i] = rd.getLsbs_id();
	                    v_intRiderNo[i] = rd.getLsdbs_number();
	                    unitRider[i] = rd.getMspr_unit();
	                    classRider[i] = rd.getMspr_class();
	                    insperiodRider[i] = rd.getMspr_ins_period();
	                    upRider[i] =rd.getMspr_tsi();
	                    end_dateRider[i] = rd.getMspr_end_date();
	                    if(end_dateRider[i].after(edit.getDatausulan().getMste_end_date())){
	                        end_dateRider[i]=edit.getDatausulan().getMste_end_date();
	                    }   
	                    beg_dateRider[i] = rd.getMspr_beg_date();
	                    end_payRider[i] = rd.getMspr_end_pay();
//	                  if(end_payRider[i].after(edit.getDatausulan().getMspr_end_pay())){
//	                      end_payRider[i]=edit.getDatausulan().getMspr_end_pay();
//	                  }
	                    rateRider[i] =rd.getMspr_rate();
	                    percentRider[i] = rd.getMspr_persen();
	                    if(rd.getMspr_persen()==null){
	                        percentRider[i] = 0;
	                    }
	            
	                    //(Deddy)
	                    premi_rider[i] =rd.getMspr_premium();
	                    if(premi_rider[i]==null || premi_rider[i].equals("")){
	                        premi_rider[i]=0.0;
	                    }
	                    //(Deddy)
	                    premi_tahunan[i] =rd.getMrs_premi_tahunan();
	                    kursRider[i] = v_strKurs;
	                    premi_arider[i] = rd.getMspr_tsi_pa_a();
	                    premi_brider[i] = rd.getMspr_tsi_pa_b();
	                    premi_drider[i] = rd.getMspr_tsi_pa_d();
	                    premi_mrider[i] = rd.getMspr_tsi_pa_m();
	                    ins_rider[i] = rd.getMspr_tt();
	                    mspr_extra [i] = rd.getMspr_extra();
	                    if (v_intRiderId[i] == 819)
	                    {
	                        flag_hcp = new Integer(1);
	                        edit.getDatausulan().setFlag_hcp(flag_hcp);
	                    }
	                }
	            }
	            for (int i =0 ; i<dtrd.size();i++)
	            {
	                    Map param28=new HashMap();
	                    param28.put("strTmpSPAJ",strTmpSPAJ);   
	                    param28.put("v_intBaseBusinessId",v_intRiderId[i]);
	                    param28.put("v_intBaseBusinessNo",v_intRiderNo[i]);
	                    param28.put("v_strKurs",kursRider[i]);
	                    param28.put("v_strBeginDate",beg_dateRider[i]);
	                    param28.put("v_strEndDate",end_dateRider[i]);
	                    param28.put("intClass",classRider[i]);
	                    param28.put("v_intBaseRate",rateRider[i]);
	                    //(Deddy) - Cara bayar langsung : premi utama & rider diisi semua, sedangkan potong bunga : premi utama diisi & premi rider 0
	                //  if(edit.getPowersave().getMpr_cara_bayar_rider()!=null  ){
	                //      if(edit.getPowersave().getMpr_cara_bayar_rider()==1 || edit.getPowersave().getMpr_cara_bayar_rider()==3){
	                //          param28.put("v_curBasePremium",0);
	                //      }else param28.put("v_curBasePremium",premi_rider[i]);
	                //  }else 
	                        param28.put("v_curBasePremium",premi_rider[i]);
	                    
	                    param28.put("v_intInsPeriod",insperiodRider[i]);
	                    param28.put("v_curUP",upRider[i]);
	                    param28.put("intPAA",premi_arider[i]);
	                    param28.put("intPAB",premi_brider[i]);
	                    param28.put("intPAC",premi_crider[i]);
	                    param28.put("intPAD",premi_drider[i]);
	                    param28.put("intPAMotor",premi_mrider[i]);
	                    param28.put("v_intUnit",unitRider[i]);
	                    if(v_intRiderId[i]==822){
	                        param28.put("ldt_endpay",null);
	                    }else{
	                        param28.put("ldt_endpay",end_payRider[i]);
	                    }
	                    
	                    if("827,828,814,815".indexOf(v_intRiderId[i].toString())>-1){
	                        param28.put("v_puptb",1);//REQ RUDY (5 Sept 2012)- flag puptb di mst_product_insured lgsg diset 1 apabila waiver payor 827 dan 828(tambahan 814 dan 815 juga diset 1)
	                    }else{
	                        param28.put("v_puptb",0);
	                    }
	                    Integer flagBikinPusing = 0; // 0 = bukan rider tertanggung tambahan, 1 rider tertanggung tambahan
//	                  if((v_intRiderId[i]==820 && v_intRiderNo[i] >105) || (v_intRiderId[i]==826 && v_intRiderNo[i] >10) || (v_intRiderId[i]==823 && v_intRiderNo[i] >15) || (v_intRiderId[i]==825 && v_intRiderNo[i] >15) || (v_intRiderId[i]==819 && ((v_intRiderNo[i] >=20 && v_intRiderNo[i] <=140) || (v_intRiderNo[i] >160 && v_intRiderNo[i] <=280) || (v_intRiderNo[i] >300 && v_intRiderNo[i] <=380) || (v_intRiderNo[i] >390 && v_intRiderNo[i] <=430) || (v_intRiderNo[i] >450 && v_intRiderNo[i] <=530))) ){
//	                          flagBikinPusing = 1;
//	                  }
	                    
	                    param28.put("v_percent",percentRider[i]);
	                    param28.put("ins_rider",ins_rider[i]);
	                    param28.put("mspr_extra",mspr_extra[i]);
	                    if(v_intRiderId[i]==835){
	                        param28.put("flag_up_sc",1);
	                    }else{
	                        param28.put("flag_up_sc",0);
	                    }
	                    if(flagBikinPusing==0){
	                        insertMst_product_insured_rider( param28, mapper );
	                    }
	                    
	                    //System.out.println("insert mst product insured rider");
	                    //(Deddy) untuk bagian ini dipastiin dulu apakah stable link nantinya akan memakai ini juga.klo iya, di tabel utama mst_slink perlu ditambah kolom total ridernya ga.
	                    // ......
	                    
	                    if (v_intRiderId[i] == 819 || v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825  || v_intRiderId[i]==826)
	                    {
	                        if(v_intRiderId[i]==819  || v_intRiderId[i]==826){
//	                          insert peserta HCP Family (utama)
	                            if ((flag_hcp.intValue() == 1) || (flag_rider_hcp.intValue() == 0))
	                            {
	                                Simas simas = new Simas();
	                                simas.setLsbs_id(v_intRiderId[i]);
	                                simas.setLsdbs_number(v_intRiderNo[i]);
	                                if(v_intRiderId[i]==819){
	                                    if ((v_intRiderNo[i].intValue() >= 1 && v_intRiderNo[i].intValue() <= 20) || (v_intRiderNo[i].intValue() >= 141 && v_intRiderNo[i].intValue() <= 160)){
	                                        simas.setDiscount(new Double(0));
	                                    }else{
	                                        simas.setDiscount(new Double(10));
	                                    }
	                                }else if(v_intRiderId[i]==826){
	                                    if ((v_intRiderNo[i].intValue() >= 1 && v_intRiderNo[i].intValue() <= 10)){
	                                        simas.setDiscount(new Double(0));
	                                    }else{
	                                        simas.setDiscount(new Double(10));
	                                    }
	                                }
	                                
	                                simas.setPremi(premi_rider[i]);
	                                
//	                              if (status.equalsIgnoreCase("input") || (flag_rider_hcp.intValue() == 0))
//	                              {
//	                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                              }
	                            }
	                        }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
	                            Simas simas = new Simas();
	                            simas.setLsbs_id(v_intRiderId[i]);
	                            simas.setLsdbs_number(v_intRiderNo[i]);
	                            simas.setDiscount(new Double(0));
	                            simas.setPremi(premi_rider[i]);
//	                          if(edit.getDatausulan().getLsbs_id().intValue()==183 || edit.getDatausulan().getLsbs_id().intValue()==189 || edit.getDatausulan().getLsbs_id().intValue()==193 || edit.getDatausulan().getLsbs_id().intValue()==195){
//	                              if (status.equalsIgnoreCase("input")){
//	                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                              }
//	                          }else{
//	                              if(flagBikinPusing==0){
//	                                  if (status.equalsIgnoreCase("input")){
//	                                      proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                                  }else if (status.equalsIgnoreCase("edit")){
//	                                      proc_save_peserta(edit,strTmpSPAJ,simas,"edit");
//	                                      flag_ekasehat = 1;
//	                                  }
//	                              }
//	                          }
	                        }               
	                    }
	                    }
	        }
	        
	        private void proc_save_rider_ultimate(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao mapper )throws ServletException,IOException
	        {
	            
	        
	            String v_strKurs = edit.getDatausulan().getKurs_p().toUpperCase();
	            Integer jumlah_rider = edit.getDatausulan().getJmlrider();
	            Integer[] v_intRiderId;
	            v_intRiderId = new Integer[jumlah_rider.intValue()+1];
	            Integer[] v_intRiderNo;
	            v_intRiderNo = new Integer[jumlah_rider.intValue()+1];
	            Integer[] unitRider;
	            unitRider = new Integer[jumlah_rider.intValue()+1];
	            Integer[] classRider;
	            classRider = new Integer[jumlah_rider.intValue()+1];
	            Integer[] insperiodRider;
	            insperiodRider = new Integer[jumlah_rider.intValue()+1];
	            Double[] upRider;
	            upRider = new Double[jumlah_rider.intValue()+1];
	            Date[] end_dateRider;
	            end_dateRider = new Date[jumlah_rider.intValue()+1];
	            Date[] beg_dateRider;
	            beg_dateRider = new Date[jumlah_rider.intValue()+1];
	            Date[] end_payRider;
	            end_payRider = new Date[jumlah_rider.intValue()+1];
	            Double[] rateRider;
	            rateRider = new Double[jumlah_rider.intValue()+1];
	            Integer[] percentRider;
	            percentRider = new Integer[jumlah_rider.intValue()+1];
	            Double[] premi_rider;
	            premi_rider = new Double[jumlah_rider.intValue()+1];
	            //(Deddy)           
	            Double[] premi_tahunan;
	            premi_tahunan = new Double[jumlah_rider.intValue()+1];
	            String[] kursRider;
	            kursRider = new String[jumlah_rider.intValue()+1];
	            Double[] premi_arider;
	            premi_arider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_brider;
	            premi_brider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_crider;
	            premi_crider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_drider;
	            premi_drider = new Double[jumlah_rider.intValue()+1];
	            Double[] premi_mrider;
	            premi_mrider = new Double[jumlah_rider.intValue()+1];
	            Integer[] ins_rider;
	            ins_rider = new Integer[jumlah_rider.intValue()+1];
	            Double[] mspr_extra;
	            mspr_extra = new Double[jumlah_rider.intValue()+1];
	            //(Deddy)
	            Integer[] mpr_cara_bayar_rider;
	            Integer[] lscb_id_rider;
	            Integer flag_ekasehat = 0;
	            mpr_cara_bayar_rider = new Integer[jumlah_rider.intValue()+1];
	            lscb_id_rider = new Integer[jumlah_rider.intValue()+1];
	            String status = edit.getPemegang().getStatus();
	            Integer[] flag_jenis_up;
	            flag_jenis_up=new Integer[jumlah_rider.intValue()+1];
	            if (status == null)
	            {
	                status = "input";
	            }
	            Integer flag_hcp= new Integer(0);
	            Integer flag_rider_hcp =edit.getDatausulan().getFlag_rider_hcp();
	            if (flag_rider_hcp == null)
	            {
	                flag_rider_hcp = new Integer(0);
	            }
	            
	            List dtrd = edit.getDatausulan().getDaftaRider();
	            if (jumlah_rider.intValue()>0)
	            {
	                for (int i =0 ; i<dtrd.size();i++)
	                {
	                    Double diskon_karyawan = 1.0;                    
	                    Datarider rd= (Datarider)dtrd.get(i);
	                    
	                    if(rd.getLsbs_id()==810){
	                		rd.setMspr_tsi(edit.getDatausulan().getMspr_tsi());
//	                		if(edit.getDatausulan().getLscb_id()==6){
//	                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.1);
//	                		}else if(edit.getDatausulan().getLscb_id()==1){
//	                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.27);
//	                		}else if(edit.getDatausulan().getLscb_id()==2){
//	                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.525);
//	                		}else{
//	                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium());
//	                		}
	                		rd.setMspr_ins_period(60 - edit.getTertanggung().getMste_age());
	                		rd.setMspr_beg_date(edit.getDatausulan().getMste_beg_date());
	                		rd.setMspr_end_date(FormatDateAdd(edit.getDatausulan().getMste_beg_date(), Calendar.YEAR, rd.getMspr_ins_period()));
	                    }
	                    
	                    v_intRiderId[i] = rd.getLsbs_id();
	                    v_intRiderNo[i] = rd.getLsdbs_number();
	                    unitRider[i] = rd.getMspr_unit();
	                    classRider[i] = rd.getMspr_class();
	                    insperiodRider[i] = rd.getMspr_ins_period();
	                    upRider[i] =rd.getMspr_tsi();
	                    end_dateRider[i] = rd.getMspr_end_date();
	                    if(end_dateRider[i].after(edit.getDatausulan().getMste_end_date())){
	                        end_dateRider[i]=edit.getDatausulan().getMste_end_date();
	                    }   
	                    beg_dateRider[i] = rd.getMspr_beg_date();
	                    end_payRider[i] = rd.getMspr_end_pay();
//	                  if(end_payRider[i].after(edit.getDatausulan().getMspr_end_pay())){
//	                      end_payRider[i]=edit.getDatausulan().getMspr_end_pay();
//	                  }
	                    rateRider[i] =rd.getMspr_rate();
	                    percentRider[i] = rd.getMspr_persen();
	                    if(rd.getMspr_persen()==null){
	                        percentRider[i] = 0;
	                    }
	            
	                    //(Deddy)
	                    premi_rider[i] =rd.getMspr_premium();
	                    if(premi_rider[i]==null || premi_rider[i].equals("")){
	                        premi_rider[i]=0.0;
	                    }
	                    //(Deddy)
	                    premi_tahunan[i] =rd.getMrs_premi_tahunan();
	                    kursRider[i] = v_strKurs;
	                    premi_arider[i] = rd.getMspr_tsi_pa_a();
	                    premi_brider[i] = rd.getMspr_tsi_pa_b();
	                    premi_drider[i] = rd.getMspr_tsi_pa_d();
	                    premi_mrider[i] = rd.getMspr_tsi_pa_m();
	                    ins_rider[i] = rd.getMspr_tt();
	                    mspr_extra [i] = rd.getMspr_extra();
	                    if (v_intRiderId[i] == 819)
	                    {
	                        flag_hcp = new Integer(1);
	                        edit.getDatausulan().setFlag_hcp(flag_hcp);
	                    }
	                }
	            }
	            for (int i =0 ; i<dtrd.size();i++)
	            {
	                    Map param28=new HashMap();
	                    param28.put("strTmpSPAJ",strTmpSPAJ);   
	                    param28.put("v_intBaseBusinessId",v_intRiderId[i]);
	                    param28.put("v_intBaseBusinessNo",v_intRiderNo[i]);
	                    param28.put("v_strKurs",kursRider[i]);
	                    param28.put("v_strBeginDate",beg_dateRider[i]);
	                    param28.put("v_strEndDate",end_dateRider[i]);
	                    param28.put("intClass",classRider[i]);
	                    param28.put("v_intBaseRate",rateRider[i]);
	                    //(Deddy) - Cara bayar langsung : premi utama & rider diisi semua, sedangkan potong bunga : premi utama diisi & premi rider 0
	                //  if(edit.getPowersave().getMpr_cara_bayar_rider()!=null  ){
	                //      if(edit.getPowersave().getMpr_cara_bayar_rider()==1 || edit.getPowersave().getMpr_cara_bayar_rider()==3){
	                //          param28.put("v_curBasePremium",0);
	                //      }else param28.put("v_curBasePremium",premi_rider[i]);
	                //  }else 
	                        param28.put("v_curBasePremium",premi_rider[i]);
	                    
	                    param28.put("v_intInsPeriod",insperiodRider[i]);
	                    param28.put("v_curUP",upRider[i]);
	                    param28.put("intPAA",premi_arider[i]);
	                    param28.put("intPAB",premi_brider[i]);
	                    param28.put("intPAC",premi_crider[i]);
	                    param28.put("intPAD",premi_drider[i]);
	                    param28.put("intPAMotor",premi_mrider[i]);
	                    param28.put("v_intUnit",unitRider[i]);
	                    if(v_intRiderId[i]==822){
	                        param28.put("ldt_endpay",null);
	                    }else{
	                        param28.put("ldt_endpay",end_payRider[i]);
	                    }
	                    
	                    if("827,828,814,815".indexOf(v_intRiderId[i].toString())>-1){
	                        param28.put("v_puptb",1);//REQ RUDY (5 Sept 2012)- flag puptb di mst_product_insured lgsg diset 1 apabila waiver payor 827 dan 828(tambahan 814 dan 815 juga diset 1)
	                    }else{
	                        param28.put("v_puptb",0);
	                    }
	                    Integer flagBikinPusing = 0; // 0 = bukan rider tertanggung tambahan, 1 rider tertanggung tambahan
//	                  if((v_intRiderId[i]==820 && v_intRiderNo[i] >105) || (v_intRiderId[i]==826 && v_intRiderNo[i] >10) || (v_intRiderId[i]==823 && v_intRiderNo[i] >15) || (v_intRiderId[i]==825 && v_intRiderNo[i] >15) || (v_intRiderId[i]==819 && ((v_intRiderNo[i] >=20 && v_intRiderNo[i] <=140) || (v_intRiderNo[i] >160 && v_intRiderNo[i] <=280) || (v_intRiderNo[i] >300 && v_intRiderNo[i] <=380) || (v_intRiderNo[i] >390 && v_intRiderNo[i] <=430) || (v_intRiderNo[i] >450 && v_intRiderNo[i] <=530))) ){
//	                          flagBikinPusing = 1;
//	                  }
	                    
	                    param28.put("v_percent",percentRider[i]);
	                    param28.put("ins_rider",ins_rider[i]);
	                    param28.put("mspr_extra",mspr_extra[i]);
	                    if(v_intRiderId[i]==835){
	                        param28.put("flag_up_sc",1);
	                    }else{
	                        param28.put("flag_up_sc",0);
	                    }
	                    if(flagBikinPusing==0){
	                        insertMst_product_insured_rider( param28, mapper );
	                    }
	                    
	                    //System.out.println("insert mst product insured rider");
	                    //(Deddy) untuk bagian ini dipastiin dulu apakah stable link nantinya akan memakai ini juga.klo iya, di tabel utama mst_slink perlu ditambah kolom total ridernya ga.
	                    // ......
	                    
	                    if (v_intRiderId[i] == 819 || v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825  || v_intRiderId[i]==826)
	                    {
	                        if(v_intRiderId[i]==819  || v_intRiderId[i]==826){
//	                          insert peserta HCP Family (utama)
	                            if ((flag_hcp.intValue() == 1) || (flag_rider_hcp.intValue() == 0))
	                            {
	                                Simas simas = new Simas();
	                                simas.setLsbs_id(v_intRiderId[i]);
	                                simas.setLsdbs_number(v_intRiderNo[i]);
	                                if(v_intRiderId[i]==819){
	                                    if ((v_intRiderNo[i].intValue() >= 1 && v_intRiderNo[i].intValue() <= 20) || (v_intRiderNo[i].intValue() >= 141 && v_intRiderNo[i].intValue() <= 160)){
	                                        simas.setDiscount(new Double(0));
	                                    }else{
	                                        simas.setDiscount(new Double(10));
	                                    }
	                                }else if(v_intRiderId[i]==826){
	                                    if ((v_intRiderNo[i].intValue() >= 1 && v_intRiderNo[i].intValue() <= 10)){
	                                        simas.setDiscount(new Double(0));
	                                    }else{
	                                        simas.setDiscount(new Double(10));
	                                    }
	                                }
	                                
	                                simas.setPremi(premi_rider[i]);
	                                
//	                              if (status.equalsIgnoreCase("input") || (flag_rider_hcp.intValue() == 0))
//	                              {
//	                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                              }
	                            }
	                        }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
	                            Simas simas = new Simas();
	                            simas.setLsbs_id(v_intRiderId[i]);
	                            simas.setLsdbs_number(v_intRiderNo[i]);
	                            simas.setDiscount(new Double(0));
	                            simas.setPremi(premi_rider[i]);
//	                          if(edit.getDatausulan().getLsbs_id().intValue()==183 || edit.getDatausulan().getLsbs_id().intValue()==189 || edit.getDatausulan().getLsbs_id().intValue()==193 || edit.getDatausulan().getLsbs_id().intValue()==195){
//	                              if (status.equalsIgnoreCase("input")){
//	                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                              }
//	                          }else{
//	                              if(flagBikinPusing==0){
//	                                  if (status.equalsIgnoreCase("input")){
//	                                      proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//	                                  }else if (status.equalsIgnoreCase("edit")){
//	                                      proc_save_peserta(edit,strTmpSPAJ,simas,"edit");
//	                                      flag_ekasehat = 1;
//	                                  }
//	                              }
//	                          }
	                        }               
	                    }
	                    }
	        }
	                    
	        private void proc_save_karyawan(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao mapper)throws Exception
	        {
	            
	            Integer v_intBaseBusinessId = edit.getDatausulan().getLsbs_id();
	            Integer v_intBaseBusinessNo = edit.getDatausulan().getLsdbs_number();
	            Double v_curBasePremium = edit.getDatausulan().getMspr_premium();
	            Date v_strBeginDate = edit.getDatausulan().getMste_beg_date();
	            
	            String nama_plan="";
	            Map param22=new HashMap();
	            param22.put("kode_bisnis",v_intBaseBusinessId);
	            param22.put("no_bisnis",v_intBaseBusinessNo);
	            // Map data4 = (HashMap) querySingle("select.nama_plan", param22);
	            Map data4 = selectNamaPlan(param22, mapper);
	            
	            if (data4!=null)
	            {       
	                nama_plan=((String)data4.get("LSDBS_NAME")).toUpperCase();
	            }
	            //System.out.println("select nama plan");
	            
	            if( edit.getDatausulan().getMste_flag_cc()==3){
	                edit.getEmployee().setPlan(nama_plan);
	                edit.getEmployee().setNo_urut(new Integer(1));
	                edit.getEmployee().setReg_spaj(strTmpSPAJ);
	                //edit.getEmployee().setPotongan(v_curBasePremium);
	                edit.getEmployee().setTgl_proses(v_strBeginDate);
	                edit.getEmployee().setStatus(new Integer(1));
	                edit.getEmployee().setKeterangan("");
	        
	                insertMstEmp( edit.getEmployee() );
	                //System.out.println("edit mst emp");
	            }
	        }           
	                    
	                    
	        private void proc_unitlink(Cmdeditbac edit, String strTmpSPAJ,Date v_strDateNow,Integer v_intActionBy , User currentUser ,Date ldt_endpay1,  Date ldt_endpay4,  Date ldt_endpay5, CommonDao mapper)throws Exception
	        {
	            //FIXME
	            System.out.println("================== START PROC_UNITLINK ==================");
	            //Variables 
	            Date v_tglsurat = edit.getInvestasiutama().getMu_tgl_surat();
	            Date v_strBeginDate = edit.getDatausulan().getMste_beg_date();
	            Date v_strEndDate = edit.getDatausulan().getMste_end_date();
	            Double v_curBasePremium = edit.getDatausulan().getMspr_premium();
	            
	            Integer v_topup_tunggal = edit.getInvestasiutama().getDaftartopup().getPil_tunggal();
	            Double v_jmlhtopup_tunggal = edit.getInvestasiutama().getDaftartopup().getPremi_tunggal();
	            Integer v_topup_berkala = edit.getInvestasiutama().getDaftartopup().getPil_berkala();
	            Double v_jmlhtopup_berkala = edit.getInvestasiutama().getDaftartopup().getPremi_berkala();
	                    
	            Integer lt_id_tunggal = edit.getDatausulan().getLi_trans_tunggal();
	            Integer lt_id_berkala = edit.getDatausulan().getLi_trans_berkala();

	            List invvl = edit.getInvestasiutama().getDaftarinvestasi();     
	                    
	            //Hitung Biaya Ulink dulu
	            double[] biayaUlink = {0, 0, 0, 0};
	            
	            for(int i=0; i<edit.getInvestasiutama().getDaftarbiaya().size(); i++) {
	                Biayainvestasi bi = (Biayainvestasi) edit.getInvestasiutama().getDaftarbiaya().get(i);
	                biayaUlink[bi.getMu_ke()] += bi.getMbu_jumlah();
	            }
	            
	            int mu_ke = 1;  
	            //Save MST_ULINK untuk Premi Pokok
	            proc_save_mst_ulink(edit,strTmpSPAJ, v_intActionBy ,v_tglsurat,1,1,1,v_curBasePremium,v_jmlhtopup_berkala,v_jmlhtopup_tunggal , v_topup_berkala,v_topup_tunggal,v_strBeginDate,"", mapper);
	            //Save MST_DET_ULINK untuk Premi Pokok
	            for(int i=0; i<invvl.size(); i++) {
	                DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                if(di.getMdu_persen1() != null) {
	                    if(di.getMdu_persen1() > 0) {
	                        //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                        int persen_tu = 0; 
	                        proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), persen_tu, di.getMdu_persen1() * (v_curBasePremium - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, mapper);
	                    }
	                }
	            }       
	            if (v_topup_berkala.intValue() > 0 ){
	                mu_ke++;
	                //Save MST_ULINK untuk Top-Up Berkala
	                proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,2,lt_id_tunggal,lt_id_berkala ,v_jmlhtopup_berkala  , 0., 0.,0 , 0,v_strBeginDate,"berkala", mapper);   
	                //Save MST_DET_ULINK untuk Top-Up Berkala
	                for(int i=0; i<invvl.size(); i++) {
	                    DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                    if(di.getMdu_persen1() != null) {
	                        if(di.getMdu_persen1() > 0) {
	                            //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                            int persen_tu = 0; 
	                            proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), di.getMdu_persen1(), di.getMdu_persen1() * (v_jmlhtopup_berkala - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, mapper);
	                        }
	                    }
	                }
	            }
	            if (v_topup_tunggal.intValue() > 0 ){
	                mu_ke++;
	                //Save MST_ULINK untuk Top-Up Tunggal
	                proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,mu_ke,lt_id_tunggal,lt_id_berkala ,v_jmlhtopup_tunggal , 0., 0. , 0, 0, v_strBeginDate, "tunggal", mapper); 
	                //Save MST_DET_ULINK untuk Top-Up Tunggal
	                for(int i=0; i<invvl.size(); i++) {
	                    DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                    if(di.getMdu_persen1() != null) {
	                        if(di.getMdu_persen1() > 0) {
	                            //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                            int persen_tu = 0; 
	                            proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), di.getMdu_persen1(), di.getMdu_persen1() * (v_jmlhtopup_tunggal - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, mapper);
	                        }
	                    }
	                }
	            }
	            
	            //Deddy (10 aug 2012) - Apabila special offer untuk additional unit, diinsert ke mst_ulink dan det_ulink dgn lt_id = 10
	            if(edit.getTertanggung().getMste_flag_special_offer()!=null){
	                if(edit.getTertanggung().getMste_flag_special_offer()==2){
	                    mu_ke++;
	                    proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,mu_ke,10,10 ,edit.getDatausulan().getPremi_additional_unit() , 0., 0. , 0, 0, v_strBeginDate, "Additional Unit", mapper);   
	                    //Save MST_DET_ULINK untuk Premi Pokok
	                    for(int i=0; i<invvl.size(); i++) {
	                        DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                        if(di.getMdu_persen1() != null) {
	                            if(di.getMdu_persen1() > 0) {
	                                //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                                int persen_tu = 0; 
	                                proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), persen_tu, di.getMdu_persen1() * (edit.getDatausulan().getPremi_additional_unit() - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, mapper);
	                            }
	                        }
	                    }
	                }
	            }
	            
	            for(int i=0; i<edit.getInvestasiutama().getDaftarbiaya().size(); i++) {
	                Biayainvestasi bi = (Biayainvestasi) edit.getInvestasiutama().getDaftarbiaya().get(i);
	                
	                if(edit.getDatausulan().getLsbs_id().intValue() != 202 && edit.getDatausulan().getLsbs_id().intValue() != 162 && 
	                        edit.getDatausulan().getLsbs_id().intValue() != 159 && edit.getDatausulan().getLsbs_id().intValue() != 160 && edit.getDatausulan().getLsbs_id().intValue() != 191 &&
	                        !edit.getAgen().getLca_id().equals("46") 
	                        && bi.getLjb_id().intValue()!=43 && bi.getLjb_id().intValue()!=437 && bi.getLjb_id().intValue() >= 20 
	                        && bi.getMbu_jumlah() == 0.0 && bi.getMbu_persen() == 0.0) {
	                    throw new RuntimeException("BIAYA UNIT LINK = 0 UNTUK LJB_ID = " + bi.getLjb_id());
	                }
	                
	                Map param30 = new HashMap();
	                param30.put("strTmpSPAJ", strTmpSPAJ);
	                param30.put("mu_ke", bi.getMu_ke());
	                param30.put("ljb_id", bi.getLjb_id());
	                param30.put("mbu_jumlah", bi.getMbu_jumlah());
	                param30.put("mbu_persen", bi.getMbu_persen());
	                int ljb_id = bi.getLjb_id();
	                param30.put("ldt_endpay", 
	                        ljb_id == 2 ? ldt_endpay1 : 
	                            ljb_id == 3 ? ldt_endpay4 : 
	                                ljb_id == 12 ? ldt_endpay5 : null);             
	                insertMst_biaya_ulink( param30, mapper );           
	            }
	            //FIXME
	            System.out.println("================== END PROC_UNITLINK ==================");              
	        }        
	                    
	                    
	        private void proc_save_mst_ulink(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Date v_tglsurat,Integer mu_ke, Integer lt_id_tunggal,Integer lt_id_berkala , Double v_premiexcell , Double v_jmlhtopup_berkala, Double v_jmlhtopup_tunggal , Integer v_topup_berkala, Integer v_topup_tunggal , Date tgl_trans, String keterangan, CommonDao mapper)throws Exception
	        {
	            
	            Integer lt_id = new Integer(1);
	            Integer v_topup = new Integer(0);
	            Double v_jmlhtopup =new Double(0);
	            Integer bulan = new Integer(6);
	            Integer premi_ke = mu_ke;
	            if (keterangan.equalsIgnoreCase("tunggal"))
	            {
	                lt_id  = lt_id_tunggal;
	                v_topup = v_topup_tunggal;
	                v_jmlhtopup = v_jmlhtopup_tunggal;
	            }
	            if (keterangan.equalsIgnoreCase("berkala"))
	            {
	                lt_id  = lt_id_berkala;
	                v_topup = v_topup_berkala;
	                v_jmlhtopup = v_jmlhtopup_berkala;
	            }
	            if (keterangan.equalsIgnoreCase("Additional Unit"))
	            {
	                lt_id  = lt_id_tunggal;
	                v_topup = v_topup_berkala;
	                v_jmlhtopup = v_jmlhtopup_berkala;
	                premi_ke=0;
	            }
	            if (keterangan.equalsIgnoreCase(""))
	            {
	                if (v_topup_berkala > 0)
	                {
	                    lt_id  = new Integer(1);
	                    v_topup = v_topup_berkala;
	                    v_jmlhtopup = v_jmlhtopup_berkala;
	                }
	                if (v_topup_tunggal > 0)
	                {
	                    if (v_topup_berkala == 0)
	                    {
	                        lt_id  = new Integer(1);
	                        v_topup = v_topup_tunggal;
	                        v_jmlhtopup = v_jmlhtopup_tunggal;
	                    }
	                }
	            }
	            if (mu_ke.intValue() == 3)
	            {
	                bulan = null;
	            }
	            edit.getInvestasiutama().setReg_spaj(strTmpSPAJ);
	            edit.getInvestasiutama().setMu_ke(mu_ke);
	            edit.getInvestasiutama().setMu_jlh_premi(v_premiexcell);
	            edit.getInvestasiutama().setMu_tgl_surat(v_tglsurat);
	            edit.getInvestasiutama().setMu_bulan_surat(bulan);
	            edit.getInvestasiutama().setLt_id(lt_id);
	            edit.getInvestasiutama().setMu_periodic_tu(v_topup);
	            edit.getInvestasiutama().setMu_jlh_tu(v_jmlhtopup);
	            edit.getInvestasiutama().setMu_tgl_trans(tgl_trans);
	            edit.getInvestasiutama().setMu_premi_ke(premi_ke);
	            int rowupdated = update_mst_ulink(edit.getInvestasiutama(), mapper);        
	            if (rowupdated ==0)
	            {
	                insert_mst_ulink(edit.getInvestasiutama(), mapper); 
	            }
	        }           
	                    
	        private void proc_save_det_ulink(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Integer value,Integer persen, Double jumlah , Integer mu_ke , String id ,  Date tgl_trans, CommonDao mapper)throws Exception
	        {           
//	          Excellink Secure $  
	            Map param=new HashMap();
	            param.put("strTmpSPAJ",strTmpSPAJ);
	            param.put("v_fixedvalue",value);
	            param.put("jmlhfixed",jumlah);
	            param.put("mu_ke",mu_ke);
	            param.put("v_persen_tu",persen);
	            param.put("v_last_trans",tgl_trans);
	            param.put("nilai",id);
	            insertFixed(param, mapper);
	        }           
	            
	        private void proc_save_benef(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper )throws ServletException,IOException
	        {
	            
	            //data penerima manfaat
	            List benef = edit.getDatausulan().getDaftabenef();
	            Integer jmlpenerima = edit.getDatausulan().getJml_benef();

	            for (int i=0; i<jmlpenerima.intValue();i++)
	            {
	                Benefeciary bf1= (Benefeciary)benef.get(i);
	                bf1.setReg_spaj(strTmpSPAJ);
	                bf1.setMsaw_number(new Integer(i+1));
	            }
	            //------------------------------------------------------------
	            // Insert Beneficiary information to MST_BENEFICIARY
	            if (jmlpenerima.intValue() >0)
	            {
	                for (int i=0; i<jmlpenerima.intValue();i++)
	                {
	                    Benefeciary bf1= (Benefeciary)benef.get(i);
	                    try {
	                        insertMst_benefeciary(bf1, mapper );
	                    } catch (Exception e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }   
	            
	        private void proc_save_dth(Cmdeditbac edit, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {
	            
	            //data penerima manfaat
	            List dth = edit.getTertanggung().getDaftarDth();
	            Integer jmldth = edit.getTertanggung().getJml_dth();
	            if(jmldth==null) jmldth = 0;
	            //------------------------------------------------------------
	            // Insert Beneficiary information to MST_BENEFICIARY
	            if (jmldth.intValue() >0)
	            {
	                for (int i=0; i<jmldth.intValue();i++)
	                {
	                    RencanaPenarikan rp1= (RencanaPenarikan)dth.get(i);
	                    rp1.setReg_spaj(strTmpSPAJ);
	                    rp1.setJenis(1);
	                    rp1.setLus_id(Integer.parseInt(edit.getCurrentUser().getLus_id()));
	                    rp1.setProses(0);
	                    insertMst_rencana_penarikan(rp1, mapper);
	                }
	            }
	        }           
	            
	        // *Data Peserta Tambahan
	        private void proc_save_pesertax(Cmdeditbac edit, PesertaPlus_mix pesertaPlus_mix, String strTmpSPAJ, CommonDao mapper)throws Exception
	        {               
	                    // *NEW
	                    List ptx_mix = edit.getDatausulan().getDaftarplus_mix(); // *gabungan
	                    Integer jumPtx_mix = 0;
	                    if(ptx_mix == null){
	                        jumPtx_mix = new Integer (0);
	                    }else{
	                         jumPtx_mix = ptx_mix.size();
	                    }
	                    Integer NoUrut = new Integer (0);
	                                        
	                    
	                    if(jumPtx_mix > 0){
	                        for (int i=0; i<jumPtx_mix.intValue(); i++)
	                        {
	                            PesertaPlus_mix plus2 = (PesertaPlus_mix)ptx_mix.get(i);
	                            if("183,189,193,195,201,204,214".indexOf(plus2.getLsbs_id().toString())>-1 &&  plus2.getLsbs_id().intValue() < 300){
	                                NoUrut+=1;
	                                plus2.setNama(edit.getTertanggung().getMcl_first().toUpperCase());
	                            }else if("819,820,823,825,826,832,833,836,838,840,841,842,843".indexOf(plus2.getLsbs_id().toString())>-1){
	                                NoUrut+=1;
	                            }
	                            
	                            plus2.setReg_spaj(strTmpSPAJ);
	                            plus2.setNo_urut(new Integer (NoUrut));
	                            if(plus2.getFlag_jenis_peserta() == 0 || plus2.getFlag_jenis_peserta() == null){
	                                plus2.setNo_reg("1a");
	                                plus2.setFlag_jenis_peserta(0);
	                            }else if(plus2.getFlag_jenis_peserta() == 1){
	                                plus2.setNo_reg("1b");
	                            }else if(plus2.getFlag_jenis_peserta() == 2){
	                                plus2.setNo_reg("1c");
	                            }else if(plus2.getFlag_jenis_peserta() == 3){
	                                plus2.setNo_reg("1d");
	                            }else if(plus2.getFlag_jenis_peserta() == 4){
	                                plus2.setNo_reg("1e");
	                            }else if(plus2.getFlag_jenis_peserta() == 5){
	                                plus2.setNo_reg("1f");
	                            }
	                            
	                            if(plus2.getFlag_jenis_peserta() == 0){
	                                plus2.setNama(plus2.getNama());
	                                plus2.setTgl_lahir(edit.getTertanggung().getMspe_date_birth());
	                                plus2.setUmur(edit.getTertanggung().getMste_age());
//	                              plus2.setLsre_id(edit.getPemegang().getLsre_id());
	                                plus2.setKelamin(edit.getTertanggung().getMspe_sex());
	                            }
	                            System.out.println(plus2.getNama());
	                            
	                            plus2.setFlag_admedika(new Integer(0));
	                            if(plus2.getLsbs_id().intValue()==819){
	                                plus2.setFlag_val_send(new Integer(0));
	                                plus2.setFlag_jenis_peserta(plus2.getFlag_jenis_peserta());
	                            }else{
	                                plus2.setNext_send(edit.getDatausulan().getMste_beg_date());
	                                plus2.setFlag_val_send(new Integer(1));
	                                plus2.setFlag_jenis_peserta(plus2.getFlag_jenis_peserta());
	                            }
	                            plus2.setLsbs_id(plus2.getLsbs_id());
	                            if("183,189,193,195,201,204,214".indexOf(plus2.getLsbs_id().toString())>-1){
	                                plus2.setLsdbs_number(edit.getDatausulan().getLsdbs_number());
	                                plus2.setPremi(edit.getDatausulan().getMspr_premium());
	                                plus2.setKelamin(edit.getTertanggung().getMspe_sex());
	                                plus2.setFlag_jenis_peserta(0);
	                                plus2.setNo_reg("1a");
	                                if(edit.getDatausulan().getMste_flag_cc()==1){
	                                    plus2.setMspo_provider(2);
	                                }else{                          
	                                    plus2.setMspo_provider(edit.getDatausulan().getMspo_provider());
	                                }
	                            }else{
	                                plus2.setLsdbs_number(plus2.getLsdbs_number());
	                                plus2.setPremi(plus2.getMspr_premium());
	                            }
	                            if("823,838,840,841,842,843".indexOf(plus2.getLsbs_id().toString())>-1){
	                                plus2.setMspo_provider(2);
	                            }
	                            
	                            plus2.setDiscount(0.0);
	                            plus2.setTanggal_lahir(plus2.getTanggal_lahir());
	                            plus2.setUmur(plus2.getUmur());
	                            plus2.setTinggi(plus2.getTinggi());
	                            plus2.setBerat(plus2.getBerat());
	                            plus2.setLsne(plus2.getLsne());
	                            plus2.setKerja(plus2.getKerja());
	                            }
	                        
	                            if (jumPtx_mix.intValue() >0)
	                            {
	                                for (int i=0; i<jumPtx_mix.intValue();i++)
	                                {
	                                    PesertaPlus_mix plus2 = (PesertaPlus_mix)ptx_mix.get(i);
	                                    insert_mst_peserta_plus_mix(plus2, mapper);
	                                }
	                            }
	                        }
	            }       
	        
	    public void proc_save_mst_policy_pas(User currentUser, Cmdeditbac edit, String strPOClientID,String strTmpSPAJ,Date v_strDateNow , String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao mapper) throws Exception
	    {
	        try {
	            //****** mst policy ****************

	            edit.getPemegang().setMcl_id(strPOClientID);
	            edit.getDatausulan().setReg_spaj(strTmpSPAJ);
	            edit.getPemegang().setMspo_age(edit.getPemegang().getUsiapp());
	            edit.getDatausulan().setLus_id(edit.getPemegang().getLus_id());
	            edit.getDatausulan().setLstp_id(edit.getDatausulan().getTipeproduk());
	            edit.getPemegang().setMspo_policy_holder(strPOClientID);
	            edit.getDatausulan().setMspo_ins_period(edit.getDatausulan().getMspr_ins_period());
	            
//	                  if(edit.getDatausulan().isPsave || edit.getPowersave().getMsl_spaj_lama()!=null){
//	                      edit.getDatausulan().setKopiSPAJ(edit.getPowersave().getMsl_spaj_lama());
//	                  }else{
//	                      edit.getDatausulan().setKopiSPAJ("");
//	                  }
	            if(edit.getDatausulan().getConvert()==null){
	                edit.getDatausulan().setKopiSPAJ("");
	            }else{
	                if(edit.getDatausulan().getMssur_se()!=null){
	                    if(edit.getDatausulan().getMssur_se()==1 || edit.getDatausulan().getMssur_se()==2 || edit.getDatausulan().getMssur_se()==3){    
	                        edit.getDatausulan().setKopiSPAJ(edit.getPowersave().getMsl_spaj_lama());
	                    }else {
	                        edit.getDatausulan().setKopiSPAJ("");
	                    }
	                }else{
	                    edit.getDatausulan().setKopiSPAJ("");
	                }
	                
	            }
	            
	            if (!FormatString.rpad("0",(strBranch),2).equalsIgnoreCase("09"))
	            {
	                edit.getPemegang().setMspo_pribadi(new Integer(0));
	            }

	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            Calendar tgl_sekarang = Calendar.getInstance(); 
	            String tgl_s =  FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.DATE)),2);
	            tgl_s = tgl_s.concat("/");
	            tgl_s = tgl_s.concat(FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.MONTH)+1),2));
	            tgl_s = tgl_s.concat("/");
	            tgl_s = tgl_s.concat(Integer.toString(tgl_sekarang.get(Calendar.YEAR)));    
	            Date v_strInputDate;
	            
	            v_strInputDate = df.parse(tgl_s);
	            edit.getDatausulan().setMspo_spaj_date(edit.getPemegang().getMspo_spaj_date());
	            
	            //tambahan Yusuf - 12 feb 08
	            //mspo_flat diisi 0 apabila individu biasa, 1 bila inputan bank (bii), 2 bila inputan bank (sinarmas - simas prima)
	            
	            if(currentUser.getJn_bank().intValue() == 0 || currentUser.getJn_bank().intValue() == 1) {
	                edit.getPemegang().setMspo_flat(1);
	            }else if(currentUser.getJn_bank().intValue() == 2) {
	                edit.getPemegang().setMspo_flat(2);
	            }else if(currentUser.getJn_bank().intValue() == 3) {
	                edit.getPemegang().setMspo_flat(3);
	            }else {
	                edit.getPemegang().setMspo_flat(0);
	            }
	            
	            if(edit.getAgen().getLca_id().equals("42")){
	                edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
	                //edit.getPemegang().setMspo_follow_up(0);
	            }else{
	                if(!(edit.getPemegang().getSumber_id()==null?"":edit.getPemegang().getSumber_id()).equals("")){
	                    edit.getPemegang().setMspo_follow_up(4);
	                    edit.getPemegang().setMspo_customer(edit.getPemegang().getSumber_id());
	                }else{
	                    if(!(edit.getPemegang().getMspo_customer()==null?"":edit.getPemegang().getMspo_customer()).equals("")){
	                        edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
	                    }else{
	                        edit.getPemegang().setMspo_customer(edit.getPemegang().getSumber_id());
	                    }
	                    
	                }
	                if(edit.getFlag_gmanual_spaj()!=null){
	                    edit.getPemegang().setMspo_customer("");
	                }
	            }
	            
	            //Anta - Memberikan flag terhadap produk2 Syariah
	            //Deddy - penentuan kategori syariah berdasarkan linebus di lst_bisnis, flag syariah = 3
	            if(mapper.selectLineBusLstBisnis(edit.getDatausulan().getLsbs_id().toString())==3){
	                edit.getDatausulan().setMspo_syahriah(1);
	            }else{
	                edit.getDatausulan().setMspo_syahriah(0);
	            };
	            
	            Date TigaPuluhNov2012 = new SimpleDateFormat("dd/MM/yyyy").parse("30/11/2012");
	            if(edit.getDatausulan().getMste_beg_date().after(TigaPuluhNov2012)){
	                edit.getDatausulan().setMspo_flag_new(1);
	            }else{
	                edit.getDatausulan().setMspo_flag_new(0);
	            }
	            
	            //tambahan Yusuf - muamalat
	            //TIDAK JADI DITAMBAH : DIINPUT DIDEPAN
	            //boolean muamalat = products.muamalat(edit.getDatausulan().getLsbs_id().intValue(), edit.getDatausulan().getLsdbs_number().intValue());
	            //if(muamalat) {
	                //edit.getDatausulan().setMspo_nasabah_acc(edit.getAccount_recur().getMar_acc_no());
	            //}
	            
	            HashMap param = new HashMap();
	            param.put("pemegang",edit.getPemegang());
	            param.put("tertanggung",edit.getTertanggung());
	            param.put("datausulan",edit.getDatausulan());
	            param.put("agen",edit.getAgen());
	            int rowupdated = mapper.updateMstPolicy(param);
	            //logger.info("edit mst policy");
	            if (rowupdated == 0)
	            {
	                if(edit.getDatausulan().getLsbs_id()==187 && (edit.getDatausulan().getLsdbs_number()>=11 && edit.getDatausulan().getLsdbs_number()<=14) 
	                        && (edit.getDatausulan().getMste_flag_cc()==1 || edit.getDatausulan().getMste_flag_cc()==2) ){
	                    mapper.insertMstPolicyPasAutodebet(param);
	                }else{
	                    mapper.insertMstPolicyPas(param);
	                }
	                //logger.info("insert mst policy");
	            }
	            if(edit.getAgen().getLca_id().equals("58")){
	                //untuk masukkan kode appointmentID
	                updateLeadReffBii(edit.getPemegang().getMspo_plan_provider(), strTmpSPAJ, mapper);
	            }
	            if(edit.getPemegang().getMspo_spaj_date() != null) saveMstTransHistory(strTmpSPAJ, "tgl_spaj_asli", edit.getPemegang().getMspo_spaj_date(), null, null, mapper);
	            Integer flag_simponi=edit.getDatausulan().getIsBungaSimponi();
	            Integer flag_tahapan=edit.getDatausulan().getIsBonusTahapan();
	            edit.getPemegang().setReg_spaj(strTmpSPAJ);
	            //update bunga simponi
	            if (flag_simponi.intValue()==1)
	            {
	                mapper.updateBungaMstPolicy(edit.getPemegang());
	            }
	            
	            //update bonus  tahapan
	            if (flag_tahapan.intValue()==1)
	            {
	                Double bonus =edit.getPemegang().getBonus_tahapan();
	                edit.getPemegang().setMspo_under_table(bonus);
	                mapper.updateBungaMstPolicy(edit.getPemegang());
	            }
	            //bagian ini untuk Bonus Maxi Deposit.
	            if((edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==3) || (edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==4) ){
	                Double bonus = 5.00;
	                edit.getPemegang().setMspo_under_table(bonus);
	                mapper.updateBungaMstPolicy(edit.getPemegang());
	            }else if((edit.getDatausulan().getLsbs_id()==114 && edit.getDatausulan().getLsdbs_number()>=2)  ){
	                Double bonus = 2.92;
	                edit.getPemegang().setMspo_under_table(bonus);
	                mapper.updateBungaMstPolicy(edit.getPemegang());
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void proc_save_mst_insured_pas(Cmdeditbac edit,String strInsClientID,String strTmpSPAJ, CommonDao mapper)
	    {
	        
	        //**********insert mst insured ***************
	        edit.getDatausulan().setReg_spaj(strTmpSPAJ);
	        edit.getTertanggung().setMste_insured(strInsClientID);
	        edit.getTertanggung().setMste_age(edit.getTertanggung().getUsiattg());
	        Date v_strPaymentDate=null;
	        Date strDebitDate= null;
	        v_strPaymentDate = edit.getPemegang().getMste_tgl_recur();          
	        if (v_strPaymentDate != null)
	        {
	            strDebitDate = v_strPaymentDate;
	        }   
	        edit.getPemegang().setMste_tgl_recur(strDebitDate);
	        edit.getDatausulan().setLus_id(edit.getPemegang().getLus_id());
	        
	        Integer v_intAutoDebet = edit.getDatausulan().getMste_flag_cc();
	        Integer lssa_id = new Integer(0);
	        Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
//	      if (flag_worksite.intValue()==1 && v_intAutoDebet.intValue() == 3)
//	      {
	            lssa_id = new Integer(5);
//	      }else{
//	          lssa_id = new Integer(1);
//	      }
	        edit.getDatausulan().setLssa_id(lssa_id);
	        
	        Integer flag_el = edit.getDatausulan().getMste_flag_el();
	        if (flag_el == null)
	        {
	            edit.getDatausulan().setMste_flag_el(new Integer(0));
	        }
	        
	        Integer flag_investasi = edit.getDatausulan().getMste_flag_investasi();
	        if (flag_investasi ==null)
	        {
	            edit.getDatausulan().setMste_flag_investasi(new Integer(0));
	        }
	        
	        Integer flag_gmit = edit.getDatausulan().getMste_gmit();
	        if (flag_gmit == null){
	            edit.getDatausulan().setMste_gmit(new Integer(0));
	        }           
	        
	        HashMap param = new HashMap();
	        
	        //(Yusuf) flag menandakan bahwa SPAJ asli atau fotokopian
	        //kalau fotokopian, gak boleh print tanda terima polis (di print polis) - khusus bancass
	        if(edit.getPemegang().getMste_spaj_asli() == null) edit.getPemegang().setMste_spaj_asli(1);
	        
	        //(Deddy) Req Rudi : Apabila cabang 01, maka otomatis set flag_karyawan(mste_flag_el) = 1, selain itu 0
	        if(edit.getAgen().getLca_id().equals("01")){
	            edit.getDatausulan().setMste_flag_el(1);
	        }else{
	            edit.getDatausulan().setMste_flag_el(0);
	        }
	        
	        param.put("pemegang",edit.getPemegang());
	        param.put("tertanggung",edit.getTertanggung());
	        param.put("datausulan",edit.getDatausulan());
	        param.put("agen",edit.getAgen());
	        int rowupdated = mapper.updateMstInsured(param);
	        //edit mst insured
	        if ( rowupdated==0)
	        {
	            if(edit.getDatausulan().getLsbs_id()==187 && (edit.getDatausulan().getLsdbs_number()>=11 && edit.getDatausulan().getLsdbs_number()<=14) 
	                    && (edit.getDatausulan().getMste_flag_cc()==1 || edit.getDatausulan().getMste_flag_cc()==2) ){
	                mapper.insertMstInsuredPasAutodebet(param);
	            }else{
	                mapper.insertMstInsuredPas(param);
	            }
	            //logger.info("insert mst insured");    
	        }
	    }
	    
	    public void saveMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao mapper){
	        if(tgl == null) tgl = new Date();
	        
	        HashMap map = new HashMap();
	        map.put("reg_spaj", reg_spaj);
	        map.put("kolom_tgl", kolom_tgl);
	        map.put("tgl", tgl);
	        map.put("kolom_user", kolom_user);
	        map.put("lus_id", lus_id);
	        
	        Integer exist = mapper.selectMstTransHist(reg_spaj);
	        
	        if(exist > 0){
	            mapper.updateMstTransHistory(map);
	        }else{
	            mapper.insertMstTransHistory(map);
	        }
	    }
	        
	    public Integer selectinvestasiutamakosong( Integer kode_flag) {
	              Integer result = null;
	            try {
	               result = commonDao.selectinvestasiutamakosong( kode_flag );
	            }catch(Exception e){
	                e.printStackTrace();            
	            }finally {
	              
	            }
	            return result;       
	    }
	     
	    public ArrayList<DetilInvestasi> selectdetilinvestasikosong(Integer kode_flag) {
	        
	        ArrayList<DetilInvestasi> result = null;
	        try {
	            result = commonDao.selectdetilinvestasikosong(kode_flag);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           
	        }
	        return result;
	    }
	    
	    
	    public Double select_biaya_akuisisi(int kode_produk,int number_produk,int cara_bayar,int ke,int period) throws SQLException {
	        Double result = null;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("kode_produk", kode_produk);
	        params.put("number_produk", number_produk);
	        params.put("cara_bayar", cara_bayar);
	        params.put("ke", ke);
	      //  params.put("period", period);
	        params.put("period", 80);
	        
	        try {
	             result = commonDao.select_biaya_akuisisi(params);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	          
	        }
	        return result;
	    }   
	    
	    
	    public Double selectNilai(int jenis, int lsbs, String lku, int lscb, int lamaBayar, int lamaTanggung, int tahunKe, int umur) throws SQLException {
	        Double result = null;
	        Map params = new HashMap();
	        //params.put("jenis", new Integer(jenis));
	        params.put("jenis", new Integer(1));
	        params.put("lsbs", new Integer(lsbs));
	        params.put("lku", lku);
	        //params.put("lscb", new Integer(lscb));
	        params.put("lscb", new Integer(3));
	        //params.put("lamaBayar", new Integer(lamaBayar));
	        params.put("lamaBayar", new Integer(80));
	        //params.put("lamaTanggung", new Integer(lamaTanggung));
	        params.put("lamaTanggung", new Integer(80));
	        //params.put("tahunKe", new Integer(tahunKe));
	        params.put("tahunKe", new Integer(1));
	        params.put("umur", new Integer(umur));
	                        
	        try {
	            //Double result = query.selectNilai(1, 190, "01", 3, 80, 80, 1, umur_tt);
	            result = commonDao.selectNilai(params);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           
	        }
	        return result;              
	    }
	    
	    
	    public void Paralel_Lanjutan (Cmdeditbac edit, CommonDao mapper)throws Exception{
	        updateMstPolicyPosition(edit.getPemegang().getReg_spaj(),new Integer(27),1, mapper);
	        updatePositionMstInsured(edit.getPemegang().getReg_spaj(),new Integer(27), 1 ,1, mapper);              
	        insertMstPositionSpaj(edit.getPemegang().getLus_id().toString(),"TRANSFER KE SPEEDY", edit.getPemegang().getReg_spaj(), 5, mapper);
	        
	        String result_simultan = prosesSimultanMedis(edit.getPemegang().getReg_spaj(), edit.getPemegang().getLus_id(), mapper );                
	        String result_kuesioner = prosesValQuest(edit.getPemegang().getReg_spaj(), edit.getPemegang().getLus_id(), mapper);
	        
	        // Tgl Terima Doc 
	        DAO_common dao_common = new DAO_common(mapper);
	        Date tanggal = dao_common.selectSysdate();
	        updateMstInsuredTglAdmin(edit.getPemegang().getReg_spaj(), 1, tanggal, 2, mapper);
	        
	        Integer exist = selectMstTransHist( edit.getPemegang().getReg_spaj(), mapper ); 
	        if(exist > 0){
	            updateMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_berkas_terima_uw", tanggal, null, "0", mapper);        
	        }else{
	            insertMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_berkas_terima_uw", tanggal, null, "0", mapper);        
	        }               
	        if(updateMstpositionSpajTgl(edit.getPemegang().getReg_spaj(), edit.getPemegang().getLus_id(), tanggal, "TGL SPAJ DOC","TGL SPAJ DOC", mapper)==0){
	            insertMstPositionSpaj(edit.getPemegang().getLus_id().toString(), "TGL SPAJ DOC", edit.getPemegang().getReg_spaj(), 2, mapper);
	        }
	        
	        // Tgl Terima SPAJ
	        updateMstInsuredTglAdmin(edit.getPemegang().getReg_spaj(), 1, tanggal, 0, mapper);
	            exist = selectMstTransHist( edit.getPemegang().getReg_spaj(), mapper ); 
	        if(exist > 0){
	            updateMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_terima_spaj_uw", tanggal, null, "0", mapper);      
	        }else{
	            insertMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_terima_spaj_uw", tanggal, null, "0", mapper);      
	        }
	        if(updateMstpositionSpajTgl(edit.getPemegang().getReg_spaj(), edit.getPemegang().getLus_id(), tanggal, "TGL TERIMA SPAJ","TGL TERIMA SPAJ", mapper)==0){
	            insertMstPositionSpaj(edit.getPemegang().getLus_id().toString(), "TGL TERIMA SPAJ", edit.getPemegang().getReg_spaj(), 3, mapper);
	        }
	    
	        //KYC
	        updateProsesKyc(edit.getPemegang().getReg_spaj(), 1, edit.getPemegang().getLus_id().toString(), 0, tanggal, mapper);
	            
	        //tidak ada checklist
	        //Copy Checklist untuk Update flag_uw =1 , PROSES CHECKLIST DI UW
	        //CommandChecklist cmd = new CommandChecklist();
	        //cmd.setLspd_id(27);
	        //cmd.setReg_spaj(edit.getPemegang().getReg_spaj());
	        //cmd.setListChecklist(selectCheckListBySpaj(edit.getPemegang().getReg_spaj(), mapper));
	        //saveChecklist(cmd, edit.getPemegang().getLus_id().toString(), tanggal, mapper);
	                
	        //Proses Akseptasi , langsung Accepted
	        //(8 SEPT 2015) - apabila status akseptasi nya further, tidak perlu otomatis menjalankan akseptasi.(terkait dengan perubahan questionare kesehatan).
	        //(7 OKT 2015) - apabila produk save series  lakukan proses akseptasi/*
	        String lsbs_id = "190";     
//	        if(selectPositionSpaj( edit.getPemegang().getReg_spaj(), mapper ) != 3 ){
	            String result_akseptasi = prosesAkseptasiSpeedy(edit.getPemegang().getReg_spaj(),  edit.getPemegang().getLus_id().toString(), mapper);
	            System.out.println("result akseptasi:"+result_akseptasi);
	            if(!result_akseptasi.equalsIgnoreCase("sukses")){
	                Integer error = 1;
	            }else{
	                //Udh Akseptasi , Update Flag done untuk NB
	                updateFlagAprove(edit.getPemegang().getReg_spaj(),1,"flag_approve_uw", mapper);
	                 exist = selectMstTransHist( edit.getPemegang().getReg_spaj(), mapper );    
	                if(exist > 0){
	                    updateMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_approve_uw", tanggal, null, "0", mapper);                  
	                }else{
	                    insertMstTransHistory(edit.getPemegang().getReg_spaj(), "tgl_approve_uw", tanggal, null, "0", mapper);  
	                }                       
	                insertMstPositionSpaj(edit.getPemegang().getLus_id().toString(), "Approve By New Business/UW", edit.getPemegang().getReg_spaj(), 4, mapper);

	            }
//	        }
	        
	        // execute f_jurnal approval NB
	        try{
	        	String UWapproval = prosesJurnalUWApproval(edit.getPemegang().getReg_spaj(), 0, mapper );  
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	            
	        String result_reins = save_reinsNew(edit.getPemegang().getReg_spaj(), mapper);              
	        //err-- HashMap mtStatus = selectWfGetStatus(edit.getPemegang().getReg_spaj(),1, mapper);

	        updateMstPolicyPosition(edit.getPemegang().getReg_spaj(),new Integer(251),1, mapper);
	        updatePositionMstInsured(edit.getPemegang().getReg_spaj(),new Integer(251), 5 ,1, mapper);              
	        insertMstPositionSpaj(edit.getPemegang().getLus_id().toString(),"TRANSFER KE WAITING PROSES NB", edit.getPemegang().getReg_spaj(), 5, mapper);
	        String OK = "";
	        
	    }
	    
	    public Map selectF_check_posisi(String spaj, CommonDao mapper) throws DataAccessException {
	        return (HashMap) mapper.selectF_check_posisi(spaj);
	    }
	            
	    public Integer selectPositionSpaj(String spaj, CommonDao mapper) {
	        Integer result = 0; 
	        result = mapper.selectPositionSpaj(spaj);    
	        return result;
	    }
	    
	    public String prosesAkseptasiSpeedy(String s_spaj, String lus_id, CommonDao mapper) throws Exception{
	        HashMap map = new HashMap();
	        String result = "";
	        map.put("lus_id", lus_id);
	        map.put("spaj", s_spaj);
	        map.put("result", result);
	        mapper.prosesAkseptasiSpeedy(map);   
	        result = (String) map.get("result");            
	        return result;          
	    }
	    
	    public String prosesSimultanMedis(String strTmpSPAJ, Integer lus_id, CommonDao mapper) throws Exception{ 
	        String result = "";
	        HashMap<String, Object> params = new HashMap<String, Object>();             
	        params.put("spaj", strTmpSPAJ);
	        params.put("lus_id", lus_id);
	        params.put("result", "");
	        mapper.prosesSimultanMedis(params);  
	        result = (String) params.get("result");
	        return result;
	    }
	    
	    public String prosesJurnalUWApproval(String strTmpSPAJ, Integer lus_id, CommonDao mapper) throws Exception{ 
	        String result = "";
	        HashMap<String, Object> params = new HashMap<String, Object>();             
	        params.put("spaj", strTmpSPAJ);
	        params.put("lus_id", 0);
	        params.put("lsbj_id", 4);
	        mapper.prosesJurnalUWApproval(params);  
	        result = (String) params.get("result");
	        return result;
	    }
	    
	    public String prosesValQuest(String strTmpSPAJ, Integer lus_id, CommonDao mapper) throws Exception{ 
	        String result = "";
	        HashMap<String, Object> params = new HashMap<String, Object>();             
	        params.put("spaj", strTmpSPAJ);
	        params.put("lus_id", lus_id);
	        params.put("result", "");
	        mapper.prosesValQuest(params);   
	        result = (String) params.get("result");
	        return result;
	    }       
	    
	    public String prosesReas(String strTmpSPAJ, Integer lus_id, CommonDao mapper) throws Exception{ 
	        String result = "";
	        HashMap<String, Object> params = new HashMap<String, Object>();             
	        params.put("spaj", strTmpSPAJ);
	        params.put("lus_id", lus_id);
	        params.put("result", "");
	        mapper.prosesReas(params);   
	        result = (String) params.get("result");
	        return result;
	    }
	    
	    public Integer selectStatusAksep(String strTmpSPAJ, CommonDao mapper) throws Exception{ 
	        return mapper.selectStatusAksep(strTmpSPAJ);
	    }
	    
	    public Integer updateMstpositionSpajTgl(String strTmpSPAJ, Integer userId, Date tglkirim, String desc, String keterangan, CommonDao mapper ) {      
	        Integer result = 0; 
	        HashMap<String, Object> params = new HashMap<String, Object>();             
	        params.put("lus_id", userId);
	        params.put("spaj", strTmpSPAJ);
	        params.put("tglkirim",tglkirim);
	        params.put("msps_desc", desc);
	        params.put("keterangan", keterangan);
	        result = mapper.updateMstpositionSpajTgl(params);
	        return result;
	    }       
	    
	    public void updateProsesKyc(String spaj, Integer insured,String lusId, Integer mste_flag_Yuw, Date mste_kyc_date, CommonDao mapper) throws Exception{
	        Map param=new HashMap();
	        param.put("reg_spaj", spaj);
	        param.put("mste_insured_no", insured);
	        param.put("mste_flag_uw", mste_flag_Yuw);
	        param.put("mste_kyc_date", mste_kyc_date);
	        param.put("lusId", lusId);
	        mapper.updateProsesKyc( param );        
	    }
	    
	    public List<Checklist> selectCheckListBySpaj(String reg_spaj, CommonDao mapper) throws DataAccessException{
	         List<Checklist> result = new ArrayList<Checklist>();
	         result = mapper.selectCheckListBySpaj(reg_spaj);
	        return result;
	    }
	    
	    
	    public void saveChecklist(CommandChecklist cmd, String lusId, Date tanggal, CommonDao mapper) throws Exception {            
	                    
	        Integer lssa_id = selectStatusAksep( cmd.reg_spaj, mapper );
	        String lca_id = selectLcaIdBySpaj(cmd.reg_spaj, mapper );
	                    
	        Integer cekflag=0;
	    
	        cmd.setLssa_id(lssa_id);
	        cmd.setLca_id(lca_id);
	        for(Checklist c : cmd.listChecklist) {
	    
	            c.setReg_spaj(cmd.reg_spaj);
	            if(c.flag_adm == null) c.flag_adm = 0;
	            if(c.flag_bancass == null) c.flag_bancass = 0;
	            if(c.flag_uw == null) c.flag_uw = 0;
	            if(c.flag_print == null) c.flag_print = 0;
	            if(c.flag_filling == null) c.flag_filling = 0;
	            
	            if(cmd.lspd_id == 1) { //adm
	                c.lus_id_adm        = Integer.valueOf(lusId);
	                c.tgl_adm           = tanggal;
	                cmd.setCekflag(-1);
	                if(c.flag_adm == null) c.flag_adm = 0;
	            }else if(cmd.lspd_id == 2) { //uw
	                c.lus_id_uw         = Integer.valueOf(lusId);
	                c.tgl_uw            = tanggal;
	                cmd.setCekflag(-1);
	                if(c.flag_uw == null) c.flag_uw = 0;
	            }else if(cmd.lspd_id == 6) { //print
	                c.lus_id_print      = Integer.valueOf(lusId);
	                c.tgl_print         = tanggal;
	                cmd.setCekflag(-1);
	                if(c.flag_print == null) c.flag_print = 0;
	            }else if(cmd.lspd_id == 7) { //tanda terima -> filling
	                c.lus_id_filling    = Integer.valueOf(lusId);
	                c.tgl_filling       = tanggal;
	                cmd.setCekflag(-1);
	                if(c.flag_filling == null) c.flag_filling = 0;
	            }else if(cmd.lspd_id == -1) { //menu terpisah 
	                c.lus_id_filling    = Integer.valueOf(lusId);
	                c.tgl_filling       = tanggal;
	    //          cmd.setCekflag(0);
	    //          c.tgl_print         = now;
	    //          c.lus_id_print      = Integer.valueOf(currentUser.getLus_id());
	                if(lssa_id==10 && cmd.centang==1&&cmd.lca_id.equals("09")){//request uw jika akseptasi khusus checklist print& filling ter-checklist
	                    c.tgl_print         = tanggal;
	                    c.lus_id_print      = Integer.valueOf(lusId);
	                    if(c.flag_filling!=0){
	                        cekflag=cekflag+1;
	                    }
	                    cmd.setCekflag(cekflag);
	                }else{
	                    cmd.setCekflag(-1);
	                }
	                
	            }
	            
	            //update atau insert
	            Integer upd = updateMstChecklist( c, mapper );              
	            if(upd == 0) {
	                insertMstChecklist( c, mapper );
	            }
	            
	            if(c.lc_id==33 && (c.flag_adm==1 || c.flag_bancass==1 || c.flag_uw==1 || c.flag_print == 1 || c.flag_filling == 1)){//Req Hadi (22 OCt 2012) : Jika Ceklist SPH & File upload SPH memang ada, Otomatis akan ditandai exist(flag = 1)
	          //      String directory = GenProperties.propClass.getProperty("pdf.ebserver.export")+"\\Polis"+"\\"+cmd.lca_id+"\\"+cmd.reg_spaj;
	            	String directory="";
	            			
	            	File destDir = new File(directory);
	                if(destDir.exists()) {
	                    String[] children = destDir.list();
	                    for(int i=0; i<children.length; i++) {
	                        if(children[i].startsWith(cmd.reg_spaj+"SPH") ){
	                            updateFlagSPH(cmd.reg_spaj, 1, mapper);
	                        }
	                    }
	                }
	            }
	            
	        }
	        
	    }
	    
	    public String selectLcaIdBySpaj(String strTmpSPAJ, CommonDao mapper) throws Exception{ 
	        return mapper.selectLcaIdBySpaj(strTmpSPAJ);
	    }
	    
	    public Integer updateMstChecklist(Checklist c, CommonDao mapper ) throws Exception{ 
	        return mapper.updateMstChecklist(c);
	    }
	    
	    public void insertMstChecklist(Checklist c, CommonDao mapper ) throws Exception{ 
	        mapper.insertMstChecklist(c);
	    }
	    
	    public void updateFlagSPH(String reg_spaj, Integer mspo_flag_sph, CommonDao mapper){
	        Map params = new HashMap();
	        params.put("reg_spaj", reg_spaj);
	        params.put("mspo_flag_sph", mspo_flag_sph);
	        mapper.updateFlagSPH(params);
	    }
	    
	    public void updateFlagAprove(String reg_spaj,Integer flag_approve, String kolom, CommonDao mapper){
	        Map map = new HashMap();
	        map.put("reg_spaj", reg_spaj);
	        map.put("kolom", kolom);
	        map.put("flag_approve", flag_approve);
	        mapper.updateFlagAprove(map);
	    }
	    
	    public String save_reinsNew(String spaj, CommonDao mapper) throws DataAccessException{
	        String result="";
	        Map param = new HashMap();
	        param.put("reg_spaj", spaj);    
	         param.put("result", "");
	        mapper.save_reinsNew( param ); 
	        result = (String) param.get("result");
	        return result;
	    }
	    
	    public void updatePositionMstInsured(String spaj, Integer lspdId, Integer lssaId, Integer insuredNo, CommonDao mapper) throws DataAccessException{
	        Map param = new HashMap();
	        param.put("spaj", spaj);        
	        param.put("lspdId", lspdId);    
	        param.put("lssaId", lssaId);    
	        param.put("insuredNo", insuredNo);
	        
	        mapper.updatePositionMstInsured( (HashMap<String, Object>) param );
	    }
	    
	    public void updateMstPolicyPosition(String spaj, Integer lspdId, Integer lstbId, CommonDao mapper) throws DataAccessException{
	        HashMap<String, Object> param = new HashMap<String, Object>();  
	        param.put("spaj", spaj);        
	        param.put("lspdId", lspdId);    
	        param.put("lstbId", lstbId);        
	        mapper.updateMstPolicyPosition( param );  
	    }
	    
	    public HashMap selectWfGetStatus(String spaj,Integer insuredNo, CommonDao mapper) throws DataAccessException{
	        HashMap param = new HashMap();
	        param.put("txtnospaj", spaj);
	        param.put("li_insured_no", insuredNo);
	        return mapper.selectWfGetStatus( param );
	    }
	    
	    public Map selectMstCntPolis(Map params){
	    	
	    	Map map = null;
	    	
	    	try {
	    		map = (HashMap) commonDao.selectMstCntPolis(params);
	    		//session.commit();
	    	}catch(Exception e){
	    		map = null;
	    		//session.rollback();
	    	}finally {
	    	
	    	}
	    	
	    	return map;
	 
	    }
		
		public Integer selectMstSpajCrtExists(String no_polis) {
			Integer result = null;
			try {
				result = commonDao.selectMstSpajCrtExists(no_polis);
			} catch(Exception e) {
				result = null;
			} finally {
				
			}
			
			return result;
		}
		
	    public String selectSpajSeq(String strBranch) {
	        String result = "";
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("kodecbg", strBranch);           
	        try {
	            result = commonDao.selectSpajSeq( params );
	        } catch (Exception e) {
	            e.printStackTrace();
	            result = null;
	        } finally {
	           
	        }           
	        return result;
	    }
		
		public String selectSertifikatTemp(Map params) {
			String result = "";
			
			try {
				result = commonDao.selectSertifikatTemp(params);
			} catch(Exception e) {
				result = null;
			} finally {
			}
			return result;
		}
		
	    public ArrayList<HashMap<String, Object>> selectReffBIIbyJenis(String jn_bank){
	        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
	    	try {
	       	result = commonDao.selectReffBIIbyJenis(jn_bank);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		result = null;
	    	}finally {
	    		
	    	}
	    	return result;
	    }
	    
		public static Date FormatDateAdd(Date tanggal, int kalendar, int angka) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(tanggal);
			cal.add(kalendar, angka);
			return cal.getTime();
		}
	    
	    public void updateLstMember(String no_va) throws Exception{
	    	try{
	    		commonDao.updateLstMember(no_va);
	    	}catch(Exception e){
		 		e.printStackTrace();
			}finally {
		    }
	    }
}
