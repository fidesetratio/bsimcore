package com.app.core;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.dao.CommonDao;
import com.app.model.Transfer;
import com.app.model.bsim.Akseptasi;
import com.app.model.bsim.Client;
import com.app.model.bsim.Command;
import com.app.model.bsim.D_DS_Sar;
import com.app.model.bsim.Pas;
import com.app.model.bsim.Policy;
import com.app.model.bsim.Product;
import com.app.model.bsim.Reas;
import com.app.model.bsim.ReasTemp;
import com.app.model.esehat.DropDownMenu;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.AddressNew;
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
import com.app.utils.CommonUtil;
import com.app.utils.FormatString;
import com.app.utils.f_hit_umur;
import com.msig.utils.DateUtil;
import com.msig.utils.FormatDate;

public class ProcessPas{
	private  PlatformTransactionManager transactionManager;
	private CommonDao commonDao;
	public ProcessPas(){
		super();
	}
	
	public ProcessPas(CommonDao commonDao,PlatformTransactionManager transactionManager){
		this.transactionManager = transactionManager;
		this.commonDao = commonDao;
		
	}
    private SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat f1 = new DecimalFormat ("0");
    private DecimalFormat f3 = new DecimalFormat("000");
    private NumberFormat dec2 = new DecimalFormat("#.00;(#,##0.00)");
    private SimpleDateFormat sdfDd=new SimpleDateFormat("dd");
   
    
    public HashMap<String, Object> selectBankPusat(String lsbpId) {
        HashMap<String, Object> bank = null;
        
        try {
            bank = commonDao.selectBankPusat(lsbpId);
        } finally {
           
        }
        
        return bank;
    }
    
    public String insertPas(Pas pas) throws Exception{
    	String status = null;
    	try {
    		CommonDao mapper = this.commonDao;
    		
    		//Set MSP_FIRE_ID	-----------------------------------------	
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
    		
			//-------------------------------------------------
			
    		//input mst_pas_sms
    			mapper.insertPas(pas);   
    		
    		//update mst_kartu_pas 
	    		Map paramsukp = new HashMap();
	    		paramsukp.put("no_kartu", pas.getNo_kartu());
	    		paramsukp.put("flag_active", 1);    	
	    		paramsukp.put("tgl_active", 1); 
	    		mapper.updateKartuPas(paramsukp);
    		
    		//input mst_position_spaj_pas
	    		Map paramimpsp = new HashMap();
	    		paramimpsp.put("lus_id", pas.getLus_id().toString());
	    		paramimpsp.put("msps_desc", "NEW ENTRY DATA");
	    		paramimpsp.put("reg_id", pas.getMsp_fire_id());
	    		paramimpsp.put("addSecond", 5);    		
	    		mapper.insertMstPositionSpajPas(paramimpsp); 
	    		
	    
	    	status = "Data Tersimpan";						
	    	//sqlSession.rollback();
    	}catch(Exception e){
//    		if(sqlSession==null)
    	
    		e.printStackTrace();
    		throw e;
    	}finally {
    		
    	}
    	 return status;
    }
    
    public String deletePas(Pas pas) throws Exception{
    	String status = null;
    	try {
    		CommonDao mapper = commonDao;
    		
    		//delete mst_pas_sms
    			mapper.deletePasSMS(pas.getNo_kartu());   
    		
    		//update mst_kartu_pas     		
    			Map paramsukp = new HashMap();
	    		paramsukp.put("no_kartu", pas.getNo_kartu());
	    		paramsukp.put("flag_active", 0); 
	    		paramsukp.put("tgl_active", 0); 
	    	mapper.updateKartuPas(paramsukp);
    		mapper.deletePositionSpajPas(pas.getMsp_fire_id()); 
	    	status = "Data PAS SMS dihapus karena ada kegagalan pada saat tranfer";						
	    	//sqlSession.rollback();
    	}catch(Exception e){
//    		
    		e.printStackTrace();
    		throw e;
    	}finally {
    		
    	}
    	 return status;
    }
    
   /* public String transferPas(String no_kartu) throws Exception{
    	
    	try {
    		CommonDao mapper = commonDao;
    		
    		    		
	    	//cek MSP_ID
	    		 HashMap<String, Object> datapassms = mapper.selectMstPasSms(no_kartu);
    		
    		//Proses transfer
	    		HttpClient httpClient = new HttpClient();
//	    		PostMethod  method = new PostMethod("http://localhost:8080/E-Lions/common/json.htm?window=submitPaBsm");
	    		 PostMethod  method = new PostMethod("https://elions.sinarmasmsiglife.co.id/common/json.htm?window=submitPaBsm");
//	    		PostMethod  method = new PostMethod("http://128.21.34.16/E-LionsTest/common/json.htm?window=submitPaBsm");
	    		
	    		StringRequestEntity requestEntity = new StringRequestEntity(
	    				"bsmib=1&msp_id="+ datapassms.get("MSP_ID").toString(),
	    			    "application/x-www-form-urlencoded",
	    			    null);
	    		
	    		method.setRequestEntity(requestEntity);
	    		
	    	    httpClient.executeMethod(method);
	    	    	  
	    	   if (method.getStatusCode() == HttpStatus.SC_OK) {
	    					
	    						JSONObject responsePost = new JSONObject(new JSONTokener(
	    								new InputStreamReader(
	    										method.getResponseBodyAsStream())));
	    						System.out.println("Create response: "
	    								+ responsePost.toString());
	    						Gson gson = new Gson();
	    						BsimServer feedObjects = new BsimServer();
	    						String result=responsePost.getString("result");
	    						
	    						if (result.equals("success")){
	    							String spaj=responsePost.getString("spaj");
	    							 sqlSession.commit();
	    							 return spaj;
	    						}else{
	    							return "error";
	    						}
	    					
	    				}
	    	      
	    	   sqlSession.rollback();
    		//sqlSession.commit();
    	}catch(Exception e){
//    		if(sqlSession==null)
    		sqlSession.rollback();
    		e.printStackTrace();
    		throw e;
    	}finally {
    		
    	}
    	
    	return null;
    }*/
  /*  
    public String transferPas2(Pas pas) {
        String result = null;
        Cmdeditbac edit = new Cmdeditbac();
        pas.setLus_id_uw_pas(0);
        pas.setLus_login_name("SYSTEM");
        //langsung set ke posisi payment
        pas.setLspd_id(4);
        User currentUser = new User();
        currentUser.setLus_id("0");
        currentUser.setLus_full_name("SYSTEM");
        currentUser.setJn_bank(2);
        edit = processPas("update", pas, null, "input", currentUser);
        
        if(edit.getPemegang().getMspo_policy_no() == null) {
            result = "error";
        } else {
            //proses create PDF
            String pdfName = pas.getNo_kartu();
            String no_polis_induk = null; 
            String nama_plan = null;
            
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                MAP_bsim mapper = sqlSession.getMapper(MAP_bsim.class);
                
                Map  kartu = mapper.selectDetailKartuPas(pdfName);
                no_polis_induk = kartu.get("NO_POLIS_INDUK").toString();
                nama_plan = kartu.get("NAMA_PLAN").toString();
                
                boolean isProd = false;
                String pdfPolisPath = "C:\\EkaWeb\\PDF";
                
                if (sqlSession != null && sqlSession.getConnection().getMetaData().getURL().contains("jdbc:oracle:thin:@128.21.22.31:1522:ajsdb")) {// AJSDB
                    isProd = true;
                    pdfPolisPath = GenProperties.propClass.getProperty("pdf.ebserver.export")+"\\Polis";
                }
                ITextPdf.generateSertifikatPaBsm(pas.getNo_kartu(), no_polis_induk, "73", "14", nama_plan, pas.getMsp_up(), pas.getMsp_premi(), pdfPolisPath);
                
                String emailFrom = pas.getMsp_pas_email();
                String[] emailBcc = new String[] {"ridhaal@sinarmasmsiglife.co.id","daru@sinarmasmsiglife.co.id"};
                String emailSubject = "Polis Personal Accident Risiko ABD";
                String emailMessage = "Nasabah Terhormat,<br><br>"+
                            "Selamat, Anda telah terdaftar sebagai pemegang polis asuransi Personal Accident Risiko ABD dari Sinarmas MSIG Life.<br>"+
                            "Terlampir adalah sertifikat polis sebagai panduan Anda dalam memahami ketentuan produk secara ringkas.<br><br>"+
                            "Terima kasih.<br><br>"+
                            "Salam Hangat,<br>"+
                            "Sinarmas MSIG Life";
                
                String setUrlForPdf = pdfPolisPath+"\\bsm\\73\\";
                String fileName = setUrlForPdf+pdfName+".pdf";
                fileName = setUrlForPdf + no_polis_induk + "-" + "073" + "-" + pdfName + ".pdf";
                File file = new File(fileName);  
                List<File> attachments = new ArrayList<File>();
                attachments.add( file );
                if (!isProd) attachments = null;// Don't add attachments if not in production
                
                EmailPool.send("SMiLe E-Lions", 0, 0, 0, 0, 
                        null, 0, 0, mapper.selectSysdate(), null, true, "info@sinarmasmsiglife.co.id",                                             
                        new String[]{emailFrom}, 
                        null,
                        emailBcc,
                        emailSubject, 
                        emailMessage,
                        attachments,30,11);
                
                result = edit.getPemegang().getReg_spaj();
            } catch(Exception e) {
                logger.error("ERROR :", e);
            } finally {
                
            }
        }
        
        return result;
    }*/
    
    public Long select_counter(Integer number , String lca_id) {
        Long counter = null;
        
        try {
            Map param = new HashMap();
    		param.put("number",number);
    		param.put("lca_id",lca_id);
            counter = commonDao.select_counter(param);
        } finally {
            
        }
        
        return counter;
    }
    
    public String selectGetCounterMonthYear(Integer aplikasi, String cabang) throws DataAccessException {
		
        String counter = null;
        
        try {
            Map params = new HashMap();
            params.put("aplikasi", new Integer(aplikasi));
    		params.put("cabang", cabang);
            counter = commonDao.selectGetCounterMonthYear(params);
        } finally {
            
        }
        
        return counter;
	}  
    
    public Date selectSysdate() throws DataAccessException {
		Date sysdate = null;
        
        try {
            sysdate = commonDao.selectSysdate();
        } finally {
            
        }
        
        return sysdate;
	}
    
    public HashMap<String, Object> selectMstKartuPas(String premi) {
        HashMap<String, Object> datakartupas = null;
        
        try {
            datakartupas = commonDao.selectMstKartuPas(premi);
        } finally {
            
        }
        
        return datakartupas;
    }
    
    public void updateKartuPas1(String no_kartu, Integer flag_active) throws Exception{
    	
    	try{
    		
    		Map params = new HashMap();
        	params.put("no_kartu", no_kartu);
        	params.put("flag_active", flag_active);
    		
        	commonDao.updateKartuPas(params);
   ;
		}catch(Exception e){
		
    		e.printStackTrace();
    		throw e;
		}finally {
	    }
    }
    
    public void updateCounterMonthYear(String nilai, Integer aplikasi, String cabang) throws Exception {
			
    	
    	try{
    		
    		Map params = new HashMap();
    		params.put("nilai", Long.valueOf(nilai));
    		params.put("aplikasi", new Integer(aplikasi));
    		params.put("cabang", cabang);
    	    
    		if(Long.valueOf(nilai)<0){
    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(nilai));
    		}
    		
    		commonDao.updateCounterMonthYear(params);
    	
		}catch(Exception e){
			e.printStackTrace();
    		throw e;
		}finally {
			
	    }
		
	}
    
    public void updateMstCounter2(String ld_cnt,Integer ai_id,String ls_cabang) throws Exception {
	 	
    	try{
    		Map params = new HashMap();
    		params.put("msco_value",ld_cnt);
    		params.put("msco_number",new Integer(ai_id));
    		params.put("lca_id",ls_cabang);
    		
    		if(Long.valueOf(ld_cnt)<0){
    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(ld_cnt));
    		}
    	    	
    		commonDao.updateMstCounter(params);
    
		}catch(Exception e){
			e.printStackTrace();
    		throw e;
		}finally {
			
	    }
		
	}
    
  public void updateMstCounter3(Integer ld_cnt,Integer ai_id,String ls_cabang) throws Exception {
		
    	
    	try{
    		
    		Map params = new HashMap();
    		params.put("msco_value",Long.valueOf(ld_cnt));
    		params.put("msco_number",new Integer(ai_id));
    		params.put("lca_id",ls_cabang);
    		
    		if(Long.valueOf(ld_cnt)<0){
    			throw new RuntimeException("ERROR INSERT MST_COUNTER, NILAI : " + Long.valueOf(ld_cnt));
    		}
    	    	
    		commonDao.updateMstCounter(params);
    	
		}catch(Exception e){
			e.printStackTrace();
    		throw e;
		}finally {
		
	    }
		
	}
    
    public void insertMstPositionSpajPas(String lus_id, String msps_desc, String reg_id, Integer addSecond) throws Exception{
    	
    	try {
    		Map params = new HashMap();
        	params.put("lus_id", lus_id);
        	params.put("msps_desc", msps_desc);
        	params.put("reg_id", reg_id);
        	params.put("addSecond", addSecond);
    		commonDao.insertMstPositionSpajPas(params);    		
    		//sqlSession.rollback();
    	}catch(Exception e){
//    		if(sqlSession==null)
    		e.printStackTrace();
    		throw e;
    	}finally {
    		
    	}
    }
    
    public HashMap<String, Object> selectdatapolicypas(String spaj) {
        HashMap<String, Object> datapolicypas = null;
        
        try {
            datapolicypas = commonDao.selectdatapolicypas(spaj);
        } finally {
            
        }
        
        return datapolicypas;
    }
    
    /**
     * Process Pas (pindahan dari E-Lions)
     * WARNING: codingan yang tidak berhubungan sama PA BSM ada bbrp yg di comment / tidak dipindahkan
     * harap cek terlebih dahulu sebelum menggunakan method ini untuk produk PAS yang lain
     * (keyword search : "-> Not needed for PA BSM - Daru")
     * 
     * @param request
     * @param jenis_proses
     * @param pas
     * @param err
     * @param keterangan
     * @param currentUser
     * @param errors
     * @return
     * @author daru
     * @since Nov 22, 2017
     */
    public Cmdeditbac processPas( String jenis_proses, Pas pas, String keterangan, com.app.model.gadget.prod.User currentUser) {
    	/*TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
    */
    	Cmdeditbac edit= new Cmdeditbac();
        int umurPp=0;
        
        try{/*
            DAO_espaj dao_espaj = new DAO_espaj(commonDao);*/
            CommonDao mapper = commonDao;
            CommonDao mapperEspaj = commonDao;
            
            Integer lus_id = Integer.parseInt(currentUser.getLus_id());
            Date beg_date = pas.getMsp_pas_beg_date();//commonDao.selectSysdateTrunc();
            Date end_date = pas.getMsp_pas_end_date();//commonDao.selectSysdateTrunc();
            pas.setLus_id(lus_id);
            pas.setLus_login_name(currentUser.getLus_full_name());
            pas.setDist("05");
            pas.setLspd_id(4);
            
            //tgl beg date
            int tanggal1= beg_date.getDate();
            int bulan1 = beg_date.getMonth()+1;
            int tahun1 = beg_date.getYear()+1900;
            
            //tgl lahir ttg
            int tanggal2=pas.getMsp_date_of_birth().getDate();
            int bulan2=pas.getMsp_date_of_birth().getMonth()+1;
            int tahun2=pas.getMsp_date_of_birth().getYear()+1900;
            
            //tgl lahir pp
            int tanggal3=pas.getMsp_pas_dob_pp().getDate();
            int bulan3=pas.getMsp_pas_dob_pp().getMonth()+1;
            int tahun3=pas.getMsp_pas_dob_pp().getYear()+1900;
            
            //hit umur ttg, pp, pic
            f_hit_umur umr= new f_hit_umur();
            int umur =  umr.umur(tahun2,bulan2,tanggal2,tahun1,bulan1,tanggal1);
            umurPp =  umr.umur(tahun3,bulan3,tanggal3,tahun1,bulan1,tanggal1);
            
            pas.setMsp_age(umur);
                
            
            edit.setPemegang(new Pemegang());
            edit.setPersonal(new Personal());
            edit.setContactPerson(new ContactPerson());
            edit.setTertanggung(new Tertanggung());
            edit.setDatausulan(new Datausulan());
            edit.setAgen(new Agen());
            edit.setAddressbilling(new AddressBilling());
            edit.setRekening_client(new Rekening_client());
            edit.setAccount_recur(new Account_recur());
            
            // agen
            Map agentMap = mapper.selectAgenPenutup(pas.getMsag_id());
            String regionid = (String) agentMap.get("REGIONID");
            edit.getAgen().setMsag_id((String) agentMap.get("ID"));
            edit.getAgen().setMcl_first((String) agentMap.get("NAMA"));
            edit.getAgen().setKode_regional(regionid);
            edit.getAgen().setLca_id(regionid.substring(0, 2));
            edit.getAgen().setLwk_id(regionid.substring(2, 4));
            edit.getAgen().setLsrg_id(regionid.substring(4, 6));
            if(edit.getAgen().getLca_id() == "58"){
                edit.getAgen().setLrb_id(pas.getLrb_id().toString());
            }
            edit.getAgen().setJenis_ref(2);
            edit.getContactPerson().setNama_lengkap("");
            
            // pemegang
            edit.getPemegang().setMspo_plan_provider(pas.getMspo_plan_provider());
            edit.getPemegang().setNo_hp(pas.getMsp_mobile());
            edit.getPemegang().setNo_hp2(pas.getMsp_mobile2());
            edit.getPemegang().setMcl_first(pas.getMsp_pas_nama_pp());
            edit.getPemegang().setMspe_no_identity(pas.getMsp_identity_no());
            edit.getPemegang().setMspe_date_birth(pas.getMsp_pas_dob_pp());
            edit.getPemegang().setMspe_email(pas.getMsp_pas_email());
            edit.getPemegang().setEmail(pas.getMsp_pas_email());
            edit.getPemegang().setAlamat_rumah(pas.getMsp_address_1());
            edit.getPemegang().setKota_rumah(pas.getMsp_city());
            edit.getPemegang().setKd_pos_rumah(pas.getMsp_postal_code());
            edit.getPemegang().setMkl_red_flag(0);
            edit.getPemegang().setApplication_id(pas.getApplication_id());
            edit.getPemegang().setMspo_no_kerjasama(pas.getTm_id());
            edit.getPemegang().setSpv(pas.getSpv_id());
            
            //randy
            edit.getPemegang().setCf_job_code(pas.getCf_job_code());
            edit.getPemegang().setCf_customer_id(pas.getCf_customer_id());
            edit.getPemegang().setCf_campaign_code(pas.getCf_campaign_code());
            edit.getPemegang().setMspo_nasabah_dcif(pas.getCif());
            
            if(("AP/BP,PAS BPD").indexOf(pas.getJenis_pas()) > -1){
                edit.getPemegang().setMspo_ao(pas.getMsag_id_pp());
            }else if(("DBD BP").indexOf(pas.getJenis_pas()) > -1){
                if (pas.getMsag_id_pp() != null){
                    edit.getPemegang().setMspo_ao(pas.getMsag_id_pp());
                }else{
                    edit.getPemegang().setMspo_ao(pas.getMsag_id());
                }
            }else{
                edit.getPemegang().setMspo_ao(pas.getMsag_id());
            }
            edit.getPemegang().setMspo_pribadi(pas.getPribadi());
            edit.getPemegang().setMspe_sex(pas.getMsp_sex_pp());
            edit.getPemegang().setReg_spaj(pas.getReg_spaj());
            edit.getPemegang().setMspo_policy_no(pas.getMspo_policy_no());
            edit.getPemegang().setMspo_spaj_date(new Date());
            edit.getPemegang().setMste_tgl_recur(pas.getMsp_tgl_debet());
            edit.getPemegang().setLus_id(pas.getLus_id());
            edit.getPemegang().setMspo_age(umurPp);
            edit.getPemegang().setUsiapp(umurPp);
            edit.getPemegang().setMspe_place_birth(pas.getMsp_pas_tmp_lhr_pp());
            edit.getPemegang().setArea_code_rumah(pas.getMsp_area_code_rumah());
            if(pas.getLside_id() != null){
                edit.getPemegang().setLside_id(pas.getLside_id());
            }
            edit.getPemegang().setTelpon_rumah(pas.getMsp_pas_phone_number());
            edit.getPemegang().setLsre_id(pas.getLsre_id());
            edit.getPemegang().setMkl_tujuan("");
            edit.getPemegang().setTujuana("");
            edit.getPemegang().setMkl_pendanaan("");
            edit.getPemegang().setDanaa("");
            edit.getPemegang().setMkl_smbr_penghasilan("");
            edit.getPemegang().setShasil("");
            edit.getPemegang().setDanaa2("");
            edit.getPemegang().setMkl_kerja("");
            edit.getPemegang().setKerjaa("");
            edit.getPemegang().setKerjab("");
            edit.getPemegang().setMkl_industri("");
            edit.getPemegang().setIndustria("");
            edit.getPemegang().setLca_id(FormatString.rpad("0",(regionid.substring(0,2)),2));
            edit.getPemegang().setLwk_id(FormatString.rpad("0",(regionid.substring(2,4)),2));
            edit.getPemegang().setLsrg_id(FormatString.rpad("0",(regionid.substring(4,6)),2));
            
            if(pas.getMsag_id_pp() == null)pas.setMsag_id_pp("");
            if(("AP/BP,AP/BP ONLINE,DBD AGENCY,DBD SYARIAH,DBD BP, DBD MALL,PAS BPD,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                edit.getPemegang().setLsne_id(pas.getMsp_warga());
                edit.getPemegang().setMspe_sts_mrt(pas.getMsp_status_nikah().toString());
                edit.getPemegang().setLsag_id(pas.getLsag_id());
                edit.getPemegang().setMcl_agama(pas.getMsp_agama());
                edit.getPemegang().setLsed_id(pas.getMsp_pendidikan());
                edit.getPemegang().setMkl_kerja(pas.getMsp_occupation());
                if(pas.getMsp_occupation_else() == null)pas.setMsp_occupation_else("");
                if(pas.getMsp_occupation_else() == null){
                    edit.getPemegang().setKerjaa("");
                }else{
                    edit.getPemegang().setKerjaa(pas.getMsp_occupation_else());
                }
                
                if(("AP/BP,AP/BP ONLINE,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                    if(pas.getMsp_company_jabatan() == null)pas.setMsp_company_jabatan("");
                    edit.getPemegang().setKerjab(pas.getMsp_company_jabatan());
                    edit.setPersonal(new Personal());
                    //set mcl id terbaru ada dibawah
                    //edit.getPersonal().setMcl_id(mcl_id);
                    edit.getPersonal().setMcl_first(pas.getMsp_company_name());
//                  edit.getPersonal().setLju_id(pas.getMsp_company_usaha());
                    if(pas.getLju_id() != null){
                        edit.getPersonal().setLju_id(pas.getLju_id());
                    }
                    edit.getPemegang().setAlamat_kantor(pas.getMsp_company_address());
                    edit.getPemegang().setKd_pos_kantor(pas.getMsp_company_postal_code());
                    edit.getPemegang().setTelpon_kantor(pas.getMsp_company_phone_number());
                }
                
            }
            
            // address billing
            edit.getAddressbilling().setNo_hp(pas.getMsp_mobile());
            edit.getAddressbilling().setNo_hp2(pas.getMsp_mobile2());
            edit.getAddressbilling().setMsap_contact(pas.getMsp_full_name());
            edit.getAddressbilling().setMsap_address(pas.getMsp_address_1());
            edit.getAddressbilling().setMsap_area_code1(pas.getMsp_area_code_rumah());
            edit.getAddressbilling().setKota(pas.getMsp_city());
            edit.getAddressbilling().setMsap_zip_code(pas.getMsp_postal_code());
            edit.getAddressbilling().setE_mail(pas.getMsp_pas_email());
            edit.getAddressbilling().setReg_spaj(pas.getReg_spaj());
            edit.getAddressbilling().setRegion(regionid);
            edit.getAddressbilling().setLca_id(FormatString.rpad("0",(regionid.substring(0,2)),2));
            edit.getAddressbilling().setLwk_id(FormatString.rpad("0",(regionid.substring(2,4)),2));
            edit.getAddressbilling().setLsrg_id(FormatString.rpad("0",(regionid.substring(4,6)),2));
            
            // rekening client
            if("DBD BP".equals(pas.getJenis_pas())){
                edit.getRekening_client().setNotes("DBD BP");
            }else if("DBD AGENCY".equals(pas.getJenis_pas())){
                edit.getRekening_client().setNotes("DBD AGENCY");
            }else if("DBD SYARIAH".equals(pas.getJenis_pas())){
                edit.getRekening_client().setNotes("DBD SYARIAH");
            }else if("DBD MALL".equals(pas.getJenis_pas())){
                edit.getRekening_client().setNotes("DBD MALL");
            }else if(("INDIVIDU,AP/BP,AP/BP ONLINE,MALLINSURANCE,PAS BPD,BP SMS,BP CARD,PAS SYARIAH,SMART ACCIDENT CARE").indexOf(pas.getJenis_pas()) > -1){
                edit.getRekening_client().setNotes("PAS");
            }else{//simple bisnis
                edit.getRekening_client().setNotes(pas.getJenis_pas());
            }
            //edit.getRekening_client().setNotes("PAS");
            // tabungan ataupun kartu kredit, kedua rekening harus diisi & untuk tunai, rekening tabungan harus diisi
            
            if("".equals(pas.getLsbp_id()) || "0".equals(pas.getLsbp_id()) || pas.getLsbp_id() == null){
                pas.setLsbp_id("0");
                pas.setMsp_no_rekening("-");
                pas.setMsp_rek_cabang("-");
                pas.setMsp_rek_kota("-");
                pas.setMsp_rek_nama("-");
            }
            
            edit.getRekening_client().setLsbp_id(pas.getLsbp_id());
            //if(pas.getMsp_flag_cc() == 2){//tabungan
                if(pas.getMsp_rek_nama() == null)edit.getRekening_client().setMrc_nama("-");
                else edit.getRekening_client().setMrc_nama(pas.getMsp_rek_nama().toUpperCase());
                if(pas.getMsp_rek_cabang() == null)edit.getRekening_client().setMrc_cabang("-");
                else edit.getRekening_client().setMrc_cabang(pas.getMsp_rek_cabang().toUpperCase());
                if(pas.getMsp_rek_kota() == null)edit.getRekening_client().setMrc_kota("-");
                else edit.getRekening_client().setMrc_kota(pas.getMsp_rek_kota().toUpperCase());
                
                
            //}
            edit.getRekening_client().setMrc_jn_nasabah(0);//none
            edit.getRekening_client().setMrc_kurs("01");//rupiah
            edit.getRekening_client().setNo_account(pas.getMsp_no_rekening());
            edit.getRekening_client().setMrc_no_ac(pas.getMsp_no_rekening());
            edit.getRekening_client().setMrc_no_ac_lama(pas.getMsp_no_rekening());
            edit.getRekening_client().setMrc_jenis(2);// rek client
            
            // account recur
            edit.getAccount_recur().setLbn_id(pas.getLsbp_id_autodebet());
            edit.getAccount_recur().setLus_id(pas.getLus_id());
            edit.getAccount_recur().setMar_number(1);
            edit.getAccount_recur().setMar_acc_no(pas.getMsp_no_rekening_autodebet());
            edit.getAccount_recur().setMar_holder(pas.getMsp_rek_nama_autodebet());
            edit.getAccount_recur().setMar_expired(pas.getMsp_tgl_valid());
            edit.getAccount_recur().setMar_active(1);
            if("SMART ACCIDENT CARE".equals(pas.getJenis_pas()) && (pas.getMsp_flag_cc()==1 || pas.getMsp_flag_cc()==2)){ //randy (belum ditambah kalau dia pilihnya flag_cc = 1. idambil dr eccel)
                edit.getAccount_recur().setFlag_autodebet_nb(1);
            }
            
            // data usulan
//          String lsdbs_number = uwDao.selectCekPin(pas.getPin(), 1);
//          if(lsdbs_number == null)lsdbs_number = "x";
            String lsdbs_number = "x";
            /*if(pas.getPin() != null){
                lsdbs_number = uwDao.selectCekPin(pas.getPin(), 1);
            }else{
                lsdbs_number = uwDao.selectCekNoKartu(pas.getNo_kartu(), "", 1);
            }*/
            
//            if(pas.getNo_kartu() != null){
//                // Cek kartu baru
//                String old_card = uwDao.selectCekNoKartu(pas.getNo_kartu(), "PAS", 1);
//                String new_card = uwDao.selectCekNoKartu(pas.getNo_kartu(), "CARD", 1);
//                if(old_card == null && "05".equals(new_card)) {
//                    pas.setNew_card(1);
//                    lsdbs_number = (pas.getProduk()<10 ? "0"+pas.getProduk().toString() : pas.getProduk().toString());
//                } else {
//                    lsdbs_number = uwDao.selectCekNoKartu(pas.getNo_kartu(), "", 1);
//                }
//            }else{
//                lsdbs_number = uwDao.selectCekPin(pas.getPin(), 1);
//            }
            
            if(pas.getNo_kartu()!= null){
            	if(!pas.getNo_kartu().trim().equals("")){

                    lsdbs_number = mapper.selectCekNoKartu(pas.getNo_kartu());
            	}
            }
            
            
            if(("AP/BP,AP/BP ONLINE,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                lsdbs_number = "05";
            }else if("PAS BPD".equals(pas.getJenis_pas())){
                lsdbs_number = "06";
            }
            
            if("DBD BP".equals(pas.getJenis_pas())){
                lsdbs_number = "01";
            }else if("DBD AGENCY".equals(pas.getJenis_pas())){
                lsdbs_number = "02";
            }else if("DBD SYARIAH".equals(pas.getJenis_pas())){
                lsdbs_number = "01";
            }else if("DBD MALL".equals(pas.getJenis_pas())){
                lsdbs_number = "03";
            }
            int mspo_pay_period = 1;
        
            Double mspr_tsi = null;
            Double mspr_premium = null;
            
            if("DBD BP".equals(pas.getJenis_pas()) || "DBD AGENCY".equals(pas.getJenis_pas()) || "DBD SYARIAH".equals(pas.getJenis_pas())){
                if("50000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(2000000);
                }else if("100000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(4000000);
                }else if("150000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(6000000);
                }else if("200000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(8000000);
                }else if("250000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(10000000);
                }else{
                    mspr_tsi = new Double(0);
                }
                mspr_premium = new Double(pas.getMsp_premi());
            }else if("DBD MALL".equals(pas.getJenis_pas()) ){
                if("100000".equals(pas.getMsp_premi())){
                    mspr_tsi = new Double(2000000);
                }else{
                    mspr_tsi = new Double(0);
                }
                mspr_premium = new Double(pas.getMsp_premi());
            }else if(("INDIVIDU,AP/BP,AP/BP ONLINE,MALLINSURANCE,PAS BPD,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                //kode plan 187
                if("01".equals(lsdbs_number)){
                    mspr_tsi = new Double(100000000);
                }else if("02".equals(lsdbs_number)){
                    mspr_tsi = new Double(50000000);
                }else if("03".equals(lsdbs_number)){
                    mspr_tsi = new Double(100000000);
                }else if("04".equals(lsdbs_number)){
                    mspr_tsi = new Double(200000000);
                }else if("05".equals(lsdbs_number)){
                    mspr_tsi = new Double(50000000);
                }else if("06".equals(lsdbs_number)){
                    mspr_tsi = new Double(50000000);
                }else{
                    mspr_tsi = new Double(100000000);
                }
                if("01".equals(lsdbs_number) && pas.getLscb_id() == 3){
                    mspr_premium = new Double(150000);
                }else if("01".equals(lsdbs_number) && pas.getLscb_id() == 6){
                    mspr_premium = new Double(15000);
                }else if("01".equals(lsdbs_number) && pas.getLscb_id() == 1){
                    mspr_premium = new Double(150000 * 0.27);
                }else if("01".equals(lsdbs_number) && pas.getLscb_id() == 2){
                    mspr_premium = new Double(150000 * 0.525);
                }else if("02".equals(lsdbs_number) && pas.getLscb_id() == 3){
                    mspr_premium = new Double(300000);
                }else if("02".equals(lsdbs_number) && pas.getLscb_id() == 6){
                    mspr_premium = new Double(30000);
                }else if("02".equals(lsdbs_number) && pas.getLscb_id() == 1){
                    mspr_premium = new Double(300000 * 0.27);
                }else if("02".equals(lsdbs_number) && pas.getLscb_id() == 2){
                    mspr_premium = new Double(300000 * 0.525);
                }else if("03".equals(lsdbs_number) && pas.getLscb_id() == 3){
                    mspr_premium = new Double(500000);
                }else if("03".equals(lsdbs_number) && pas.getLscb_id() == 6){
                    mspr_premium = new Double(50000);
                }else if("03".equals(lsdbs_number) && pas.getLscb_id() == 1){
                    mspr_premium = new Double(500000 * 0.27);
                }else if("03".equals(lsdbs_number) && pas.getLscb_id() == 2){
                    mspr_premium = new Double(500000 * 0.525);
                }else if("04".equals(lsdbs_number) && pas.getLscb_id() == 3){
                    mspr_premium = new Double(900000);
                }else if("04".equals(lsdbs_number) && pas.getLscb_id() == 6){
                    mspr_premium = new Double(90000);
                }else if("04".equals(lsdbs_number) && pas.getLscb_id() == 1){
                    mspr_premium = new Double(900000 * 0.27);
                }else if("04".equals(lsdbs_number) && pas.getLscb_id() == 2){
                    mspr_premium = new Double(900000 * 0.525);
                }else if("05".equals(lsdbs_number)){
                    mspr_premium = new Double(74000);
                }else if("06".equals(lsdbs_number)){
                    mspr_premium = new Double(74000);
                } else{
                    mspr_premium = new Double(0);
                }
                
                // New card
                if(pas.getNew_card() == 1) {
                    mspr_premium = new Double(pas.getMsp_premi());
                }
            }else if(pas.getProduct_code().equals("205") && "PAS SYARIAH".equals(pas.getJenis_pas())){
                // Product_Code = Lsbs_id= Kode Plan 205 --> PAS Syariah
                //set Product=Lsdbs_number= (1->PAS Syariah Perdana (NEW)/2->PAS Syariah Single (NEW)/3->PAS Syariah Ceria (NEW)/4->PAS Syariah Ideal (NEW))
                //(5->PAS Syariah Perdana/6->PAS Syariah Single/7->PAS Syariah Ceria/8->PAS Syariah Ideal)
                double faktor_premi=1;
                if(pas.getLscb_id()==3){
                    faktor_premi = 1;
                }
                else if(pas.getLscb_id()==2){
                    faktor_premi = 0.525;
                }
                else if(pas.getLscb_id()==1){
                    faktor_premi = 0.27;
                }
                else  if(pas.getLscb_id()==6){
                    faktor_premi = 0.1;
                }
                
                if(pas.getProduk()==1 || pas.getProduk()==5){
                    mspr_tsi = new Double(100000000);
                    mspr_premium = new Double(faktor_premi*300000);
                }else if(pas.getProduk()==2 || pas.getProduk()==6){
                    mspr_tsi = new Double(50000000);
                    mspr_premium = new Double(faktor_premi*500000);
                }else if(pas.getProduk()==3 || pas.getProduk()==7){
                    mspr_tsi = new Double(100000000);
                    mspr_premium = new Double(faktor_premi*900000);
                }else if(pas.getProduk()==4 || pas.getProduk()==8){
                    mspr_tsi = new Double(200000000);
                    mspr_premium = new Double(faktor_premi*1600000);
                }
                
      
                //update no VA dari kolom excelnya
                edit.getTertanggung().setMste_no_vacc(pas.getNo_va());
                
            }else if(pas.getProduct_code().equals("187") && "SMART ACCIDENT CARE".equals(pas.getJenis_pas())){

                double faktor_premi=1;
                if(pas.getLscb_id()==3){
                    faktor_premi = 1;
                }
                else if(pas.getLscb_id()==2){
                    faktor_premi = 0.525;
                }
                else if(pas.getLscb_id()==1){
                    faktor_premi = 0.27;
                }
                else  if(pas.getLscb_id()==6){
                    faktor_premi = 0.1;
                }
                
                if(pas.getProduk()==11 || pas.getProduk()==15){
                    mspr_tsi = new Double(100000000);
                    mspr_premium = new Double(faktor_premi*300000);
                }else if(pas.getProduk()==12 || pas.getProduk()==16){
                    mspr_tsi = new Double(50000000);
                    mspr_premium = new Double(faktor_premi*500000);
                }else if(pas.getProduk()==13 || pas.getProduk()==17){
                    mspr_tsi = new Double(100000000);
                    mspr_premium = new Double(faktor_premi*900000);
                }else if(pas.getProduk()==14 || pas.getProduk()==18){
                    mspr_tsi = new Double(200000000);
                    mspr_premium = new Double(faktor_premi*1600000);
                }
                
                if(pas.getMsp_flag_cc()==1 || pas.getMsp_flag_cc()==2){
                    pas.setLspd_id(118);
                }
            }
            else{// simple bisnis
                mspr_tsi = new Double(pas.getMsp_up());
                mspr_premium = new Double(pas.getMsp_premi());
            }
            edit.getDatausulan().setIsBungaSimponi(0);
            edit.getDatausulan().setIsBonusTahapan(0);
            edit.getDatausulan().setLssp_id(10);
            edit.getDatausulan().setLssa_id(5);
            edit.getDatausulan().setLspd_id(4);
            if("187".equals(pas.getProduct_code()) && "SMART ACCIDENT CARE".equals(pas.getJenis_pas())){
                if(pas.getMsp_flag_cc()==1 || pas.getMsp_flag_cc()==2){
                    edit.getDatausulan().setLspd_id(118);
                }
            }
            
            edit.getDatausulan().setMspo_age(umurPp);
            edit.getDatausulan().setMspo_spaj_date(new Date());
            edit.getDatausulan().setMspo_beg_date(pas.getMsp_pas_beg_date());
            edit.getDatausulan().setMspo_end_date(pas.getMsp_pas_end_date());
            edit.getDatausulan().setMste_medical(0);
            edit.getDatausulan().setLscb_id(pas.getLscb_id());
            edit.getDatausulan().setMspr_tsi(mspr_tsi);
            edit.getDatausulan().setMspr_premium(mspr_premium);
            edit.getDatausulan().setMspr_discount(new Double(0));
            edit.getDatausulan().setMste_flag_cc(pas.getMsp_flag_cc());// rek client = 0.tunai, 2.tabungan, 1.CC
            edit.getDatausulan().setFlag_worksite(0);
            edit.getDatausulan().setFlag_account(2);// 0 untuk umum  1 untuk account recur 2 untuk rek client 3 untuk account recur dan rek client
            edit.getDatausulan().setKode_flag(0);//default
            edit.getDatausulan().setMspo_beg_date(pas.getMsp_pas_beg_date());
            edit.getDatausulan().setMspo_end_date(pas.getMsp_pas_end_date());
            edit.getDatausulan().setMste_beg_date(pas.getMsp_pas_beg_date());
            edit.getDatausulan().setMste_end_date(pas.getMsp_pas_end_date());
            edit.getDatausulan().setMspo_ins_period(1);
            edit.getDatausulan().setMspr_ins_period(1);
            edit.getDatausulan().setMspo_pay_period(mspo_pay_period);
            if("DBD BP".equals(pas.getJenis_pas())){
                edit.getDatausulan().setFlag_jenis_plan(5);//DBD
                edit.getDatausulan().setLsbs_id(203);//DBD
                edit.getDatausulan().setLsdbs_number(1);//DBD BP
            }else if("DBD AGENCY".equals(pas.getJenis_pas())){
                edit.getDatausulan().setFlag_jenis_plan(5);//DBD
                edit.getDatausulan().setLsbs_id(203);//DBD
                edit.getDatausulan().setLsdbs_number(2);//DBD AGENCY
            }else if("DBD SYARIAH".equals(pas.getJenis_pas())){
                edit.getDatausulan().setFlag_jenis_plan(5);//DBD
                edit.getDatausulan().setLsbs_id(209);//DBD SYARIAH
                edit.getDatausulan().setLsdbs_number(1);//DBD SYARIAH
            }else if("DBD MALL".equals(pas.getJenis_pas())){
                edit.getDatausulan().setFlag_jenis_plan(5);//DBD
                edit.getDatausulan().setLsbs_id(203);//DBD
                edit.getDatausulan().setLsdbs_number(3);//DBD MALL
            }else if(("INDIVIDU,AP/BP,AP/BP ONLINE,MALLINSURANCE,PAS BPD,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                edit.getDatausulan().setFlag_jenis_plan(5);//PAS
                edit.getDatausulan().setLsbs_id(187);//PAS
                edit.getDatausulan().setLsdbs_number(Integer.parseInt(lsdbs_number));//PAS
            }else{
                if(pas.getProduct_code().equals("45") || pas.getProduct_code().equals("130") || pas.getProduct_code().equals("205")){//super protection
                    edit.getDatausulan().setFlag_jenis_plan(5);//SIMPLE BISNIS/ BAC SIMPLE --untuk PA
                }else{
                    edit.getDatausulan().setFlag_jenis_plan(0);//SIMPLE BISNIS/ BAC SIMPLE
                }
                edit.getDatausulan().setLsbs_id(Integer.parseInt(pas.getProduct_code()));//SIMPLE BISNIS/ BAC SIMPLE
                edit.getDatausulan().setLsdbs_number(pas.getProduk());//SIMPLE BISNIS/ BAC SIMPLE
            }
            edit.getDatausulan().setKurs_p("01");
            edit.getDatausulan().setLku_id("01");
            edit.getDatausulan().setMspo_nasabah_acc(pas.getNo_kartu());
            edit.getDatausulan().setJenis_pemegang_polis(0);
            
           edit.getTertanggung().setMspo_plan_provider(pas.getMspo_plan_provider());
                edit.getTertanggung().setNo_hp(pas.getMsp_mobile());
                edit.getTertanggung().setNo_hp2(pas.getMsp_mobile2());
                edit.getTertanggung().setMspe_no_identity(pas.getMsp_identity_no_tt());
                edit.getTertanggung().setAlamat_rumah(pas.getMsp_address_1());
                edit.getTertanggung().setKota_rumah(pas.getMsp_city());
                edit.getTertanggung().setKd_pos_rumah(pas.getMsp_postal_code());
                edit.getTertanggung().setMspe_email(pas.getMsp_pas_email());
                edit.getTertanggung().setEmail(pas.getMsp_pas_email());
                edit.getTertanggung().setMkl_red_flag(0);
            edit.getTertanggung().setMcl_first(pas.getMsp_full_name());
            edit.getTertanggung().setMspe_date_birth(pas.getMsp_date_of_birth());
            edit.getTertanggung().setMste_age(pas.getMsp_age());
            edit.getTertanggung().setUsiattg(pas.getMsp_age());
            edit.getTertanggung().setDanaa("");
            edit.getTertanggung().setDanaa("");
            edit.getTertanggung().setDanaa2("");
            edit.getTertanggung().setShasil("");
            edit.getTertanggung().setMkl_pendanaan("");
            edit.getTertanggung().setMkl_smbr_penghasilan("");
            edit.getTertanggung().setMkl_kerja("");
            edit.getTertanggung().setMkl_industri("");
            edit.getTertanggung().setKerjaa("");
            edit.getTertanggung().setKerjab("");
            edit.getTertanggung().setIndustria("");
            edit.getTertanggung().setMspe_place_birth(pas.getMsp_pas_tmp_lhr_tt());
            edit.getTertanggung().setTujuana("");
            edit.getTertanggung().setMkl_tujuan("");
            edit.getTertanggung().setLca_id(FormatString.rpad("0",(regionid.substring(0,2)),2));
            edit.getTertanggung().setLwk_id(FormatString.rpad("0",(regionid.substring(2,4)),2));
            edit.getTertanggung().setLsrg_id(FormatString.rpad("0",(regionid.substring(4,6)),2));
            edit.getTertanggung().setReg_spaj(pas.getReg_spaj());
            edit.getTertanggung().setMspo_policy_no(pas.getMspo_policy_no());
            edit.getTertanggung().setLus_id(pas.getLus_id());
            edit.getTertanggung().setArea_code_rumah(pas.getMsp_area_code_rumah());
            edit.getTertanggung().setMspe_sex(pas.getMsp_sex_tt());
            if(pas.getLside_id() != null){
                edit.getTertanggung().setLside_id(pas.getLside_id());
            }
            if(pas.getLsre_id() == 1){
                edit.getTertanggung().setNo_hp(pas.getMsp_mobile());
                edit.getTertanggung().setNo_hp2(pas.getMsp_mobile2());
                edit.getTertanggung().setMspe_no_identity(pas.getMsp_identity_no());
                edit.getTertanggung().setAlamat_rumah(pas.getMsp_address_1());
                edit.getTertanggung().setKota_rumah(pas.getMsp_city());
                edit.getTertanggung().setKd_pos_rumah(pas.getMsp_postal_code());
                edit.getTertanggung().setMspe_email(pas.getMsp_pas_email());
                edit.getTertanggung().setEmail(pas.getMsp_pas_email());
                edit.getTertanggung().setMspe_sex(pas.getMsp_sex_pp());
                edit.getTertanggung().setTelpon_rumah(pas.getMsp_pas_phone_number());
                if(("AP/BP,AP/BP ONLINE,DBD AGENCY,DBD SYARIAH,DBD BP, DBD MALL,PAS BPD,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                    edit.getTertanggung().setLsne_id(pas.getMsp_warga());
                    edit.getTertanggung().setMspe_sts_mrt(pas.getMsp_status_nikah().toString());
                    edit.getTertanggung().setLsag_id(pas.getLsag_id());
                    edit.getTertanggung().setMcl_agama(pas.getMsp_agama());
                    edit.getTertanggung().setLsed_id(pas.getMsp_pendidikan());
                    edit.getTertanggung().setMkl_kerja(pas.getMsp_occupation());
                    if(pas.getMsp_occupation_else() == null)pas.setMsp_occupation_else("");
                    if(pas.getMsp_occupation_else() == null){
                        edit.getTertanggung().setKerjaa("");
                    }else{
                        edit.getTertanggung().setKerjaa(pas.getMsp_occupation_else());
                    }
                    if(("AP/BP,AP/BP ONLINE,BP SMS,BP CARD").indexOf(pas.getJenis_pas()) > -1){
                        if(pas.getMsp_company_jabatan() == null)pas.setMsp_company_jabatan("");
                        edit.getTertanggung().setKerjab(pas.getMsp_company_jabatan());
                        edit.getTertanggung().setAlamat_kantor(pas.getMsp_company_address());
                        edit.getTertanggung().setKd_pos_kantor(pas.getMsp_company_postal_code());
                        edit.getTertanggung().setTelpon_kantor(pas.getMsp_company_phone_number());
                    }
                }
            }
            
            // No Virtual Account
            if(pas.getNo_kartu() != null) {
                HashMap<String, Object> kartu = mapper.selectDetailKartuPas(pas.getNo_kartu());
                if(kartu != null && kartu.get("NO_VA") != null)
                    edit.getTertanggung().setMste_no_vacc((String) kartu.get("NO_VA"));
            }
            
            int hasil = 0;
            edit = insertspajpas(edit,currentUser, pas.getJenis_pas(),  mapper, mapperEspaj);
            pas.setReg_spaj(edit.getPemegang().getReg_spaj());
            if(!"".equals(edit.getPemegang().getReg_spaj()) && false){
                
                // reas
                
                Reas reas=new Reas();
                reas.setLstbId(new Integer(1));
                String las_reas[]=new String[3];
                las_reas[0]="Non-Reas";
                las_reas[1]="Treaty";
                las_reas[2]="Facultative";
                reas.setCurrentUser(currentUser);   
                reas.setSpaj(edit.getPemegang().getReg_spaj());
                
                Map mPosisi=selectF_check_posisi(reas.getSpaj(), mapperEspaj);
                Integer lspdIdTemp=(Integer)mPosisi.get("LSPD_ID");
                reas.setLspdId(lspdIdTemp);
                String lspdPosTemp=(String)mPosisi.get("LSPD_POSITION");
                //produk asm
                Map mMap = mapper.selectDataUsulan(reas.getSpaj());
                Integer lsbsId=(Integer)mMap.get("LSBS_ID");

                //tertanggung
                Map mTertanggung=mapper.selectTertanggung(reas.getSpaj());
                reas.setInsuredNo((Integer)mTertanggung.get("MSTE_INSURED_NO"));
                reas.setMsteInsured((String)mTertanggung.get("MCL_ID"));
                reas.setUmurTt((Integer)mTertanggung.get("MSTE_AGE"));
                //
                Map mStatus=selectWfGetStatus(reas.getSpaj(),reas.getInsuredNo(), mapperEspaj);
                reas.setLiAksep((Integer)mStatus.get("LSSA_ID"));
                reas.setLiReas((Integer)mStatus.get("MSTE_REAS"));
                if (reas.getLiAksep()==null) 
                    reas.setLiAksep( new Integer(1));
                
                
                //dw1 //pemegang
                Policy policy=selectDw1Underwriting(reas.getSpaj(),reas.getLspdId(),reas.getLstbId(), mapper);
                if(policy!=null){
                    reas.setMspoPolicyHolder(policy.getMspo_policy_holder());
                    reas.setNoPolis(policy.getMspo_policy_no());
                    reas.setInsPeriod(policy.getMspo_ins_period());
                    reas.setPayPeriod(policy.getMspo_pay_period());
                    reas.setLkuId(policy.getLku_id());
                    reas.setUmurPp(policy.getMspo_age());
                    reas.setLcaId(policy.getLca_id());
                    reas.setLcaId(policy.getLca_id());
                    reas.setMste_kyc_date(policy.getMste_kyc_date());
                }
                
                //simultan===========================================================================================================
                //Integer sim = uwDao.prosesSimultan(1, edit.getPemegang().getReg_spaj(), edit.getAgen().getLca_id(), policy.getMspo_policy_holder(), reas.getMsteInsured());
                String spaj;
                Integer lsreIdPp;
                String lcaIdPp;
                int info;
                List lsSimultanTt,lsSimultanPp;

                spaj=edit.getPemegang().getReg_spaj();
                info=0;
                Command command=new Command();
                command.setCurrentUser(currentUser);
                command.setCount(new Integer(0));
                //
                Map a = mapper.selectCheckPosisi(spaj);
                int li_pos=Integer.parseInt(a.get("LSPD_ID").toString());
                String ls_pos=a.get("LSPD_POSITION").toString();
                Map map=mapper.selectDataUsulan(spaj);
                lsbsId=(Integer)map.get("LSBS_ID");
                //validasi Posisi SPAJ harus UW (2)
                if(li_pos !=2 ){
                    info=1;
                    //MessageBox('Info', 'Posisi Polis Ini Ada di ' + ls_pos )
                }else if(lsbsId.intValue()==161){//produk sinarmas
                    info=3;
                }
                //
                Map mPemegang=mapper.selectPemegang(spaj);
                
                String mclIdPp,mclFirstPp,sDateBirthPp;
                Date mspeDateBirthPp,mspeDateBirthTt;
                Integer lsreIdTt;
                String mclIdTt,mclFirstTt,sDateBirthTt,lcaIdTt;
                //data pemegang
                DateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
                lsreIdPp=(Integer)mPemegang.get("LSRE_ID");
                mclIdPp=(String)mPemegang.get("MCL_ID");
                mclFirstPp=(String)mPemegang.get("MCL_FIRST");
                mspeDateBirthPp=(Date)mPemegang.get("MSPE_DATE_BIRTH");
                sDateBirthPp=fd.format(mspeDateBirthPp);
                lcaIdPp=(String)mPemegang.get("LCA_ID");
                //data tertanggung
                //Map mTertanggung=uwDao.selectTertanggung(spaj);
                lsreIdTt=(Integer)mTertanggung.get("LSRE_ID");
                mclIdTt=(String)mTertanggung.get("MCL_ID");
                mclFirstTt=(String)mTertanggung.get("MCL_FIRST");
                mspeDateBirthTt=(Date)mTertanggung.get("MSPE_DATE_BIRTH");
                sDateBirthTt=fd.format(mspeDateBirthTt);
                lcaIdTt=(String)mTertanggung.get("LCA_ID");
                //cek mcl_id
                if(!(mclIdTt.substring(0,2).equalsIgnoreCase("XX")))
                    if(!(mclIdTt.substring(0,2).equalsIgnoreCase("WW")))
                        if(!(mclIdPp.substring(0,2).equalsIgnoreCase("XX")))
                            if(!(mclIdPp.substring(0,2).equalsIgnoreCase("WW"))){
                                info=2;
                                //MessageBox('Info', 'Proses Simultan Sudah pernah dilakukan untuk Pemegang & Tertanggung Polis!!!')
                            }   
                int spasi,titik,koma,pjg;
                //pemegang ambil nama depan saja 
                spasi=mclFirstPp.indexOf(' ');
                titik=mclFirstPp.indexOf('.');
                koma=mclFirstPp.indexOf(',');
                pjg=mclFirstPp.length();
                if(spasi>0)
                    mclFirstPp=mclFirstPp.substring(0,spasi);
                else if(titik>0)
                    mclFirstPp=mclFirstPp.substring(0,titik);
                else if(koma>0)
                    mclFirstPp=mclFirstPp.substring(0,koma);
                //Tertanggung ambil nama depan saja 
                spasi=mclFirstTt.indexOf(' ');
                titik=mclFirstTt.indexOf('.');
                koma=mclFirstTt.indexOf(',');
                pjg=mclFirstTt.length();
                if(spasi>0)
                    mclFirstTt=mclFirstTt.substring(0,spasi);
                else if(titik>0)
                    mclFirstTt=mclFirstTt.substring(0,titik);
                else if(koma>0)
                    mclFirstTt=mclFirstTt.substring(0,koma);
                //
                HashMap param=new HashMap();
                param.put("mcl_id",mclIdPp);
                param.put("nama",mclFirstPp);
                param.put("tgl_lhr",sDateBirthPp);
                lsSimultanPp=mapper.selectSimultanNew(param);
                param=new HashMap(); 
                param.put("mcl_id",mclIdTt);
                param.put("nama",mclFirstTt);
                param.put("tgl_lhr",sDateBirthTt);
                lsSimultanTt=mapper.selectSimultanNew(param);
//              if(logger.isDebugEnabled())logger.debug(""+lsSimultanPp.size());
//              if(logger.isDebugEnabled())logger.debug(""+lsSimultanTt.size());
                Integer proses=null;
//              if(logger.isDebugEnabled())logger.debug("isi list simultan ="+lsSimultanTt.size());

                command.setSpaj(spaj);
                command.setLsreIdPp(lsreIdPp);
                command.setLcaIdPp(lcaIdPp);
                command.setLsSimultanPp(lsSimultanPp);
                command.setLsSimultanTt(lsSimultanTt);
                command.setRowPp(lsSimultanPp.size());
                command.setRowTt(lsSimultanTt.size());
                command.setFlagAdd(lsreIdPp.intValue());
                command.setError(new Integer(info));
                command.setFlagId(ls_pos);
                command.setMapPemegang(mPemegang);
                command.setMapTertanggung(mTertanggung);
                
                int choosePp=0;
                int chooseTt=0;
                
                if(command.getLsreIdPp()==1){//hbungan diri sendiri
                    choosePp=chooseTt;
                }
                String idSimultanPp,idSimultanTt;
                idSimultanPp=(String)mPemegang.get("ID_SIMULTAN");
                idSimultanTt=(String)mTertanggung.get("ID_SIMULTAN");
                
                //mcl_id ambil yang baru karena data baru ini yang akan di isi
                Map mPpOld=command.getMapPemegang();
                Map mTtOld=command.getMapTertanggung();
                mclIdPp=(String)mPpOld.get("MCL_ID");
                mclIdTt=(String)mTtOld.get("MCL_ID");
        
                Integer sim = prosesSimultan(command, mclIdPp, mclIdTt, idSimultanPp, idSimultanTt, mapper);
                //====================================================================================================================
                //Integer sim = uwDao.prosesSimultan(1, edit.getPemegang().getReg_spaj(), edit.getAgen().getLca_id(), policy.getMspo_policy_holder(), reas.getMsteInsured());
                
                //reas
                reas.setLstbId(1);
                reas.setLspdId(2);

                prosesReasUnderwritingPas(reas,   mapper, mapperEspaj);
                                
                //billing
                List lsDp = new ArrayList();
                wf_ins_bill_pas(edit.getPemegang().getReg_spaj(), new Integer(1), new Integer(1), edit.getDatausulan().getLsbs_id(), edit.getDatausulan().getLsdbs_number(), lspdIdTemp, new Integer(1), lsDp, lus_id.toString(), policy,  mapper);
                
                //=================================================
//              uwManager.prosesAkseptasiPas(akseptasi, pas, user, 0, 0, "", errors);
                pas.setReg_spaj(edit.getPemegang().getReg_spaj());
                //hasil=this.elionsManager.prosesTransferPembayaran(transfer,flagNew,errors);
                //akseptasi======================================
                int result = 0;
                //try{
                    Akseptasi akseptasi = new Akseptasi();
                    
                    akseptasi.setLiAksep(5);
                    akseptasi.setLspdId(4);
                    if(pas.getJenis_pas().equals("SMART ACCIDENT CARE") && (map.get("MSTE_FLAG_CC").toString().equals("1") || map.get("MSTE_FLAG_CC").toString().equals("2"))){
                        akseptasi.setLspdId(118);
                        pas.setLspd_id(118);
                    }
                    akseptasi.setLsspId(1);
                    akseptasi.setLssaId(5);
                    akseptasi.setCurrentUser(currentUser);
                    akseptasi.setSpaj(pas.getReg_spaj());
                    akseptasi.setInsuredNo(1);
                    akseptasi.setLcaId(regionid.substring(0, 2));
                    akseptasi.setLsbsId(edit.getDatausulan().getLsbs_id());
                    akseptasi.setProses("1");
                    int thn = 0;
                    int bln = 0;
                    String desc = "";
                    result =  prosesAkseptasiPas(akseptasi,thn,bln,desc, mapper, mapperEspaj);
              
                //=======================================================================================
                //transfer ke payment
                Transfer transfer = new Transfer();
                DecimalFormat fmt = new DecimalFormat ("000");
                Map mDataUsulan=mapper.selectDataUsulan(edit.getPemegang().getReg_spaj());
                transfer.setSpaj(edit.getPemegang().getReg_spaj());
                transfer.setPModeId((Integer)mDataUsulan.get("LSCB_ID"));
                transfer.setBegDate((Date)mDataUsulan.get("MSTE_BEG_DATE"));
                transfer.setEndDate((Date)mDataUsulan.get("MSTE_END_DATE"));
                transfer.setMedical((Integer)mDataUsulan.get("MSTE_MEDICAL"));
                transfer.setLsbsId((Integer)mDataUsulan.get("LSBS_ID"));
                transfer.setLsdbsNumber((Integer)mDataUsulan.get("LSDBS_NUMBER"));
                transfer.setBisnisId(fmt.format(transfer.getLsbsId().intValue()));
                transfer.setPremium((Double)mDataUsulan.get("MSPR_PREMIUM"));
                transfer.setTsi((Double)mDataUsulan.get("MSPR_TSI"));
                //tertanggung
                Map mTertanggung2=mapper.selectTertanggung(transfer.getSpaj());
                transfer.setInsuredNo((Integer)mTertanggung2.get("MSTE_INSURED_NO"));
                transfer.setMsteInsured((String)mTertanggung2.get("MCL_ID"));
                transfer.setMsagId((String)mTertanggung2.get("MSAG_ID"));
                transfer.setUmurTt((Integer)mTertanggung2.get("MSTE_AGE"));
                transfer.setCurrentUser(currentUser);
                transfer.setLsDp(new ArrayList());
                transfer.setLiLama(0);
                Map mStatus2=selectWfGetStatus(transfer.getSpaj(),transfer.getInsuredNo(), mapperEspaj);
                transfer.setLiAksep((Integer)mStatus.get("LSSA_ID"));
                transfer.setLiReas((Integer)mStatus.get("MSTE_REAS"));
                transfer.setLspdId(new Integer(4));
                transfer.setLstbId(new Integer(1));
                transfer.setLiPosisi(4);//pembayaran
                policy=selectDw1Underwriting(transfer.getSpaj(),transfer.getLspdId(),transfer.getLstbId(), mapper);
                if(policy!=null){
                    transfer.setMspoPolicyHolder(policy.getMspo_policy_holder());
                    transfer.setNoPolis(policy.getMspo_policy_no());
                    transfer.setInsPeriod(policy.getMspo_ins_period());
                    transfer.setPayPeriod(policy.getMspo_pay_period());
                    transfer.setLkuId(policy.getLku_id());
                    transfer.setUmurPp(policy.getMspo_age());
                    transfer.setLcaId(policy.getLca_id());
                }
                transfer.setPolicy(policy);
                if(pas.getJenis_pas().equals("SMART ACCIDENT CARE") && (map.get("MSTE_FLAG_CC").toString().equals("1") || map.get("MSTE_FLAG_CC").toString().equals("2"))){
                    policy=selectDw1Underwriting(reas.getSpaj(),118,1, mapper);
                }else{
                    policy=selectDw1Underwriting(reas.getSpaj(),4,1, mapper);
                }
                
                edit.getPemegang().setMspo_policy_no(policy.getMspo_policy_no());
                pas.setMspo_policy_no(policy.getMspo_policy_no());
                String x ="";
                updateKartuPas2(pas.getNo_kartu(), 1, edit.getPemegang().getReg_spaj(), mapper);
                // update spaj ke det va
                if(pas.getNo_kartu() != null) {
                    HashMap<String, Object> kartu = mapper.selectDetailKartuPas(pas.getNo_kartu());
                    if(!CommonUtil.isEmpty(kartu) && !CommonUtil.isEmpty(kartu.get("NO_VA")))
                        updateDetVa((String) kartu.get("NO_VA"), currentUser.getLus_id(), edit.getPemegang().getReg_spaj(), mapper);
                }
                if("insert".equals(jenis_proses)){
                    mapper.insertPas(pas);
                }else{
                    mapper.updatePas(pas);
                }
                  
            }else{
                pas.setMspo_policy_no(null);
              //  pas.setReg_spaj(null);
            }
            insertMstPositionSpajPas(pas.getLus_id().toString(), "TRANSFER DATA", pas.getMsp_fire_id(), 5, mapper);
            
            if(pas.getReg_spaj() != null){
            	 //transactionManager.commit(txStatus);
            }else{
            	if(!pas.getReg_spaj().trim().equals("")){
            		//transactionManager.rollback(txStatus);
            	}
            }
            
         
        }
        catch (Exception e){
        	//transactionManager.rollback(txStatus);
        	e.printStackTrace();
            pas.setMspo_policy_no(null);
            pas.setReg_spaj(null);
            edit.getPemegang().setReg_spaj(null);
            edit.getPemegang().setMspo_policy_no(null);
            throw new IllegalArgumentException();
        } finally {
            
        }
        
        return edit;
    }
    
    
    public Policy selectDw1Underwriting(String spaj,Integer lspdId,Integer lstbId, CommonDao mapper){
        HashMap params=new HashMap();
        params.put("spaj",spaj);
        params.put("lspdId",lspdId);
        params.put("lstbId",lstbId);
        return mapper.selectDw1Underwriting(params);
    }
    public Cmdeditbac insertspajpas(Object cmd, com.app.model.gadget.prod.User currentUser, String jenis_produk,  CommonDao mapper, CommonDao mapperEspaj)
    {
        Cmdeditbac edit= (Cmdeditbac) cmd;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String strTmpSPAJ=null;
        try {
            String strPOClientID =null;
            String strInsClientID = null;
            Long intIDCounter =null;
            
            String strAgentBranch = null;
            String strBranch = null;
            String strWilayah = null;
            String strRegion = null;

            String gc_strTmpBranch= "WW";
            Long intSPAJ = null;
            Integer inttgl1 =null;
            Integer inttgl2 =null;
            Integer li_tahun =null;
            String ldt_endpay="";
            Date ldt_endpay1=null; 
            Date ldt_endpay4 =null;
            Date ldt_endpay5 =null;
            Integer ai_month=null;          
            String kode_id = null;
            
            //bikin error di insert pas
//          if(!edit.getCurrentUser().getCab_bank().equals("") && edit.getCurrentUser().getJn_bank().intValue() == 2) {
//              if(edit.getDatausulan().getLsbs_id().intValue()==142&& edit.getDatausulan().getLsdbs_number().intValue()==2){
//                  edit.getAgen().setMsag_id("016409");
//              }else if(edit.getDatausulan().getLsbs_id().intValue()==164&& edit.getDatausulan().getLsdbs_number().intValue()==2){
//                  edit.getAgen().setMsag_id("021052");
//              }
//          }
            
            Integer flag_gutri = edit.getDatausulan().getFlag_gutri();
            if (flag_gutri  == null)
            {
                flag_gutri =new Integer(0);
            }
            if (flag_gutri.intValue() == 1)
            {
                edit.getPemegang().setMste_flag_guthrie(flag_gutri);
                String nama ="GUTHRIE  PECCONINA  INDONESIA";
                String tgl = "19951013";
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("nama", nama);
                params.put("tanggal", tgl);
                kode_id = mapperEspaj.selectkodegutri(params);
            }
            
            Calendar tgl_sekarang = Calendar.getInstance(); 
            Integer v_intRelation = edit.getPemegang().getLsre_id();
            Integer v_intActionBy = edit.getPemegang().getLus_id();
            
            Integer flag_rider = edit.getDatausulan().getFlag_rider();
            Integer flag_jenis_plan = edit.getDatausulan().getFlag_jenis_plan();
            Integer flag_platinumlink = edit.getDatausulan().getFlag_platinumlink();
            if (flag_platinumlink ==null)
            {
                flag_platinumlink = new Integer(0);
            }
            Integer kode_flag = edit.getDatausulan().getKode_flag();
            Integer flag_as = edit.getDatausulan().getFlag_as();
            
            Date v_strBeginDate = edit.getDatausulan().getMste_beg_date();
            Integer v_intInsPeriod = edit.getDatausulan().getMspr_ins_period();
            Date v_strEndDate = edit.getDatausulan().getMste_end_date();
            Integer jumlah_rider = edit.getDatausulan().getJmlrider();
            
            Date tanggal = new Date();
            
            String sysdate = defaultDateFormat.format(new Date());
            
            DateFormat dfh = new SimpleDateFormat("HH");
            DateFormat dfm = new SimpleDateFormat("mm");
            
//          String hh = ServletRequestUtils.setStringParameter("hh", dfh.format(tanggal));
//          String mm = ServletRequestUtils.getStringParameter(request, "mm", dfm.format(tanggal));
            String tanggalTerimaAdmin;
            
            
            
            String tgl_s =  FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.DATE)),2);
            tgl_s = tgl_s.concat("/");
            tgl_s = tgl_s.concat(FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.MONTH)+1),2));
            tgl_s = tgl_s.concat("/");
            tgl_s = tgl_s.concat(Integer.toString(tgl_sekarang.get(Calendar.YEAR)));    
            Date v_strInputDate = df.parse(tgl_s);
            Date v_strDateNow = v_strInputDate;
//          Integer index_biaya = edit.getInvestasiutama().getJmlh_biaya();
//          if (index_biaya==null)
//          {
//              index_biaya = new Integer(0);
//          }

            String v_strregionid = edit.getAgen().getKode_regional().toUpperCase();
            String v_strAgentId = edit.getAgen().getMsag_id().toUpperCase();
            String v_stragentnama = edit.getAgen().getMcl_first().toUpperCase();
            
            //Integer v_intAutoDebet = edit.getDatausulan().getMste_flag_cc();
            Integer lssa_id = new Integer(0);
//          Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
//          if (flag_worksite.intValue()==1 && v_intAutoDebet.intValue() == 3)
//          {
//              lssa_id = new Integer(3);
//          }else{
//              lssa_id = new Integer(1);
//          }
            edit.getDatausulan().setLssa_id(lssa_id);
            
            //----------------------------------
            // Get the Agent Branch information
            if  (v_strAgentId.equalsIgnoreCase("000000"))
            {
                   strBranch = FormatString.rpad("0",(v_strregionid.substring(0,2)),2);
                   strWilayah = FormatString.rpad("0",(v_strregionid.substring(2,4)),2);
                   strRegion = FormatString.rpad("0",(v_strregionid.substring(4,6)),2);
                   strAgentBranch = strBranch.concat("00");
            }else{
                    HashMap m = new HashMap();
                    m.put("kodeagen", v_strAgentId);
                    Map data = mapperEspaj.selectRegionalAgen(m);
                    
                    if (data!=null)
                    {       
                         strBranch = (String)data.get("STRBRANCH");
                         strWilayah = (String)data.get("STRWILAYAH");
                         strRegion = (String)data.get("STRREGION");
                         strAgentBranch = (String)data.get("STRAGENTBRANCH");
                    }

            }
            
            edit.getAgen().setLca_id(strBranch);
            edit.getAgen().setLwk_id(strWilayah);
            edit.getAgen().setLsrg_id(strRegion);
            edit.getAgen().setKode_regional(strAgentBranch);

            
            //------------------------------------
            //cari & update counter
            /*
            Map m = new HashMap();
            m.put("kodecbg", strBranch);
            intIDCounter = (Long) querySingle("select.counter", m);
            
            if(edit.getFlag_gmanual_spaj()!=null){
                if(edit.getFlag_gmanual_spaj()>=1){
                    strTmpSPAJ = edit.getTertanggung().getReg_spaj();
                }else{
                    if (intIDCounter.longValue() == 0)
                    {
                        intIDCounter = new Long ((long)((tgl_sekarang.get(Calendar.YEAR))* 100000));
                    }else{
                        if ((intIDCounter).toString().length()==9)
                        {
                            inttgl1=new Integer(Integer.parseInt((intIDCounter).toString().substring(0,4)));
                            inttgl2=new Integer(tgl_sekarang.get(Calendar.YEAR));
                            if("09,62".indexOf(strBranch) >-1){
                                inttgl1=new Integer(Integer.parseInt((intIDCounter).toString().substring(0,2)));
                                inttgl2=Integer.parseInt(inttgl2.toString().substring(2,4));
                                intIDCounter= new Long(Long.parseLong(inttgl2.toString() +intIDCounter.toString().substring(intIDCounter.toString().length()-7, intIDCounter.toString().length())));
                            }

                            if (inttgl1.intValue() != inttgl2.intValue())
                            {
                                intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("00000")));
                                if("09,62".indexOf(strBranch) >-1){
                                    intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("0000000")));
                                }
                                Map param=new HashMap();
                                param.put("intIDCounter", intIDCounter);
                                param.put("kodecbg", strBranch);
                                update("update.mst_counter", param);
                                //logger.info("update mst counter start di tahun baru");
                            }
                        }
                    }
                    
                    //--------------------------------------------
                    //Increase current SPAJ No by 1 and
                    //update the value to MST_COUNTER table
                    Map param=new HashMap();
                    param.put("IDCounter", new Long(intIDCounter.longValue()+1));
                    param.put("kodecbg", strBranch);
                    update("update.mst_counter_up", param);
                    //logger.info("update mst counter naik");
                    intSPAJ = new Long(intIDCounter.longValue() + 1);
                    strTmpSPAJ = strBranch.concat(intSPAJ.toString());
                }
            }else{
                if (intIDCounter.longValue() == 0)
                {
                    intIDCounter = new Long ((long)((tgl_sekarang.get(Calendar.YEAR))* 100000));
                }else{
                    if ((intIDCounter).toString().length()==9)
                    {
                        inttgl1=new Integer(Integer.parseInt((intIDCounter).toString().substring(0,4)));
                        inttgl2=new Integer(tgl_sekarang.get(Calendar.YEAR));
                        if("09,62".indexOf(strBranch) >-1){
                            inttgl1=new Integer(Integer.parseInt((intIDCounter).toString().substring(0,2)));
                            inttgl2=Integer.parseInt(inttgl2.toString().substring(2,4));
                            intIDCounter= new Long(Long.parseLong(inttgl2.toString() +intIDCounter.toString().substring(intIDCounter.toString().length()-7, intIDCounter.toString().length())));
                        }

                        if (inttgl1.intValue() != inttgl2.intValue())
                        {
                            intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("00000")));
                            if("09,62".indexOf(strBranch) >-1){
                                intIDCounter=new Long(Long.parseLong(inttgl2.toString().concat("0000000")));
                            }
                            Map param=new HashMap();
                            param.put("intIDCounter", intIDCounter);
                            param.put("kodecbg", strBranch);
                            update("update.mst_counter", param);
                            //logger.info("update mst counter start di tahun baru");
                        }
                    }
                }
                
                //--------------------------------------------
                //Increase current SPAJ No by 1 and
                //update the value to MST_COUNTER table
                Map param=new HashMap();
                param.put("IDCounter", new Long(intIDCounter.longValue()+1));
                param.put("kodecbg", strBranch);
                update("update.mst_counter_up", param);
                //logger.info("update mst counter naik");
                intSPAJ = new Long(intIDCounter.longValue() + 1);
                strTmpSPAJ = strBranch.concat(intSPAJ.toString());
            }*/
            
            //MANTA (23/06/2017) - Generate No SPAJ langsung dari sequence
            if(edit.getFlag_gmanual_spaj()!=null){
                if(edit.getFlag_gmanual_spaj()>=1){
                    strTmpSPAJ = edit.getTertanggung().getReg_spaj();
                }else{
                    strTmpSPAJ = mapper.selectSeqNoSpaj(strBranch);
                }
            }else{
                strTmpSPAJ = mapper.selectSeqNoSpaj(strBranch);
            }
            
            String no_pb=edit.getPemegang().getNo_pb();
            //----------------------------------------------------------
            //Insert no spaj dan nama agent untuk agent baru di MST_AGENT_TEMP table -> Not needed in PA BSM - Daru
//            if (v_strAgentId.equalsIgnoreCase("000000"))
//            {
//                Map param1=new HashMap();
//                param1.put("strTmpSPAJ", strTmpSPAJ);
//                param1.put("v_stragentnama", v_stragentnama);
//                insert("insert.mst_agent_temp", param1);
//                //logger.info("insert kalau agent baru");
//            }

            String nomor=null;
            if (flag_gutri.intValue() ==1)
            {
                strInsClientID=edit.getTertanggung().getMcl_id();
            }else{
                //----------------------------------------------------
                //Get Insured Client ID counter from MST_COUNTER table
        /*      Map param2=new HashMap();
                param2.put("gc_strTmpBranch", gc_strTmpBranch);
                intIDCounter = (Long) querySingle("select.counter_client_id", param2);
        */      //logger.info("select counter mcl id ttg");

                /* Increase current Insured Client ID by 1 and
                 update the value to MST_COUNTER table */
        /*      Map param3=new HashMap();
                param3.put("gc_strTmpBranch", gc_strTmpBranch);
                param3.put("IDCounter", new Long(intIDCounter.longValue()+1));
                update("update.clientid", param3);
        */      //logger.info(intIDCounter);
                //logger.info("update counter mcl id ttg");

                /* Combine Branch Information and Client Id Counter
                 to get the temporary Insured Client ID */
        /*      nomor =("000000000").concat(Long.toString(intIDCounter.longValue() + 1));
            //  logger.info(nomor);
                strInsClientID = gc_strTmpBranch.concat(nomor.substring((nomor.length()-10),nomor.length()));
        */  //  logger.info(strInsClientID);
//              input data tertanggung di mst client new dan mst address new
                
                strInsClientID = mapper.selectSequenceClientID();
                proc_save_data_ttg(edit, strInsClientID, v_strDateNow, mapperEspaj);
            }
                
            
            
            //-------------------------------------------------
            // Check Policy Holder and Insured relation

            if (v_intRelation.intValue() != 1)
            {
                if (flag_gutri.intValue() ==1)
                {
                    strPOClientID=kode_id;
                }else{  
                    /* If Policy Holder and Insured is not the same person
                        then create the new Client ID
                        Get Policy Holder Client ID counter from MST_COUNTER table*/
                /*      Map param6 = new HashMap();
                        param6.put("gc_strTmpBranch", gc_strTmpBranch);
                        intIDCounter = (Long) querySingle("select.counter_client_id", param6);
                */      //logger.info("select counter mcl id pp");
    
                    /* Increase current Policy Holder Client ID by 1 and
                       update the value to MST_COUNTER table*/
                /*      Map param7=new HashMap();
                        param7.put("gc_strTmpBranch", gc_strTmpBranch);
                        param7.put("IDCounter", new Long(intIDCounter.longValue()+1));
                        update("update.clientid", param7);
                */      //logger.info("update mcl id pp");
    
                    /* Combine Branch Information and Client Id Counter
                     to get the temporary Policy Holder Client ID*/
                /*      nomor =("000000000").concat(Long.toString(intIDCounter.longValue() + 1));
                        strPOClientID = gc_strTmpBranch.concat(nomor.substring((nomor.length()-10),nomor.length()));
                */      //input data pemegang polis di mst client new dan mst_address new
                        
                        strPOClientID = mapper.selectSequenceClientID();
                        proc_save_data_pp(edit, strPOClientID, v_strDateNow, mapperEspaj);
                    }                       

                }else{
                    if (flag_gutri.intValue() ==1)
                    {
                        strPOClientID=kode_id;
                    }else{  

                    // If Policy Holder and Insured is the same person
                        strPOClientID = strInsClientID;
                    }
                }   
            
                //insert mst_policy
                //proc_save_mst_policy(currentUser, edit,strPOClientID,strTmpSPAJ,v_strDateNow ,v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid);
                //isi no_blanko dan dimasukkan ke mst_policy
                // no_kartu = mspo_nasabah_acc
            
            	if(edit.getDatausulan().getMspo_nasabah_acc() != null){
                HashMap paramBlanko = new HashMap();
                paramBlanko.put("no_kartu", edit.getDatausulan().getMspo_nasabah_acc());
                String mspo_no_blanko = mapper.selectNoBlankoPas(paramBlanko);
                edit.getPemegang().setMspo_no_blanko(mspo_no_blanko);
            	};
                proc_save_mst_policy_pas(currentUser, edit,strPOClientID,strTmpSPAJ,v_strDateNow ,v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid, mapperEspaj);
                
//              proc_save_worksite_flag(edit,strTmpSPAJ);
//              proc_save_noblanko(edit,strTmpSPAJ);
                
//              insert mst insured
                proc_save_mst_insured_pas(edit,strInsClientID,strTmpSPAJ, mapperEspaj);          
    
                //proc_save_suamiistri_ttg(edit,strTmpSPAJ);
                
                //proc_save_suamiistri_pp(edit,strTmpSPAJ);
                
//              if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
//                  proc_save_data_pic(edit,strPOClientID);
//              }
                
                //------------------------------------------------------------
                // Process Application closing and Agent Commission
                if("AP/BP".equals(jenis_produk) || "AP/BP ONLINE".equals(jenis_produk) || "BP CARD".equals(jenis_produk)){// tidak ada komisi
//                    proc_save_agen_bp(edit,strTmpSPAJ, v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid); -> Not needed for PA BSM - Daru
                }else{//pas, dbd bp, dbd agency -- komisi hanya lev 4
                    proc_save_agen_two(edit,strTmpSPAJ, v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid, mapperEspaj);
                }
    
                //input  address billing
                proc_save_addr_bill(edit,strTmpSPAJ, mapperEspaj);
                    
                //-------------------------------------------------
                // Insert rekening baik account recur maupun rek client
                proc_save_rekening(edit,strTmpSPAJ,kode_flag, mapperEspaj);
                                    
                // Insert information to MST_POSITION_SPAJ
                insertMstPositionSpaj(v_intActionBy.toString(), "INPUT SPAJ", strTmpSPAJ, 0, mapperEspaj);
                updateMstInsuredTglAdmin(strTmpSPAJ, 1, tanggal, 0, mapperEspaj);

              //  Integer exist = commonDao.selectMstTransHist(strTmpSPAJ);
                //saveMstTransHistory(strTmpSPAJ, "tgl_input_spaj_admin", tanggal, null, null, mapperEspaj);
//              //logger.info("insert posisi spaj");
//              try {
//                  Thread.sleep(2000); 
//              } catch (InterruptedException e) {
//                  logger.error("ERROR :", e);
//              }
                
//                if(edit.getAgen().getLca_id().equals("58")){ -> Not needed for PA BSM - Daru
//                    proc_save_reff_mall(edit, strTmpSPAJ,currentUser);
//                }
                
                //insert ke mst_position_spaj u/history no PB(dian)
                //bacDao.insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),1, 10, "NO PB: "+no_pb,2);
                if(edit.getDatausulan().getLsbs_id()==187 && (edit.getDatausulan().getLsdbs_number()>=11 && edit.getDatausulan().getLsdbs_number()<=14) 
                        && (edit.getDatausulan().getMste_flag_cc()==1 || edit.getDatausulan().getMste_flag_cc()==2) ){
                	insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),118, 10, "NO PB: "+no_pb,2, mapperEspaj);
                }else{
                	insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),4, 10, "NO PB: "+no_pb,2, mapperEspaj);
                }
//              if(edit.getPemegang().getInfo_special_case() != null && !"".equals(edit.getPemegang().getInfo_special_case())) {
//                  //insert ke mst_position_spaj u/ info special case (yusuf)
//                  bacDao.insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),1, 10, "SC:"+edit.getPemegang().getInfo_special_case(),5);
//              }
                // red_flag , ryan
                if(edit.getPemegang().getMkl_red_flag()==1||edit.getTertanggung().getMkl_red_flag()==1){
                    String jenis =edit.getPemegang().getMkl_kerja();
                    String jenis2 =edit.getTertanggung().getMkl_kerja();
                    insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk Pekerjaan : Pemegang: "+jenis+" & Tertanggung: "+jenis2, strTmpSPAJ, 5,"REDFLAG", mapperEspaj);
                }
                if (kode_flag.intValue()>1 ){
                if(edit.getInvestasiutama().getDaftartopup().getRedFlag_topup_berkala()==1){
                    if(edit.getDatausulan().getLku_id().equals("01")){
                    	insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > Rp. 100 Juta", strTmpSPAJ, 10,"REDFLAG", mapperEspaj);
                    }else if(edit.getDatausulan().getLku_id().equals("02")){
                        insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > $ 10000", strTmpSPAJ, 10,"REDFLAG", mapperEspaj);  
                    }
                }}
                //------------------------------------------------------------
                // Insert Insured information to MST_STS_CLIENT
                if (flag_gutri.intValue() !=1)
                {
                    insertMst_sts_client(strInsClientID, mapperEspaj);
                    //logger.info("insert status client");
                }
                /*
                if (flag_rider.intValue()==1)
                {
                    li_tahun =v_intInsPeriod;
                    if ((flag_jenis_plan.intValue()==1) )
                    {
                        li_tahun = new Integer(6);
                    }
                    ai_month=new Integer((li_tahun.intValue() * 12) - 1);
                    //ai_month=new Integer((li_tahun.intValue() * 12) );
                        
                    Map param27=new HashMap();
                    param27.put("v_strBeginDate",v_strBeginDate);   
                    param27.put("ai_month",ai_month);   
                    //logger.info("add month");
                    
                    Date ldt_endpay2 =null;
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    ldt_endpay2=df1.parse((String)querySingle("select.addmonths", param27));
                                
                    int tgl_endpay = ldt_endpay2.getDate();
                    int bln_endpay = ldt_endpay2.getMonth()+1;
                    int thn_endpay = ldt_endpay2.getYear()+1900;
                    ldt_endpay = (FormatString.rpad("0",Integer.toString(tgl_endpay),2)).concat("/");
                    ldt_endpay = ldt_endpay.concat(FormatString.rpad("0",Integer.toString(bln_endpay),2));
                    ldt_endpay = ldt_endpay.concat("/");
                    ldt_endpay = ldt_endpay.concat(Integer.toString(thn_endpay));
                    ldt_endpay1 = df.parse(ldt_endpay);
                                
                    //end pay untuk biaya administrasi
                    
                        Calendar tanggal_sementara = Calendar.getInstance();
                        Date ldt_endpay3 =null;
                        Integer ai_month1=new Integer(-1);
                        Map param28=new HashMap();
                        param28.put("v_strBeginDate",v_strEndDate); 
                        param28.put("ai_month",ai_month1);  
                        ldt_endpay3=df1.parse((String)querySingle("select.addmonths", param28));
                        
                        tgl_endpay = ldt_endpay3.getDate();
                        bln_endpay = ldt_endpay3.getMonth()+1;
                        thn_endpay = ldt_endpay3.getYear()+1900;
                        ldt_endpay = (FormatString.rpad("0",Integer.toString(tgl_endpay),2)).concat("/");
                        ldt_endpay = ldt_endpay.concat(FormatString.rpad("0",Integer.toString(bln_endpay),2));
                        ldt_endpay = ldt_endpay.concat("/");
                        ldt_endpay = ldt_endpay.concat(Integer.toString(thn_endpay));
                        tanggal_sementara.set(thn_endpay,bln_endpay,tgl_endpay);    
                        tanggal_sementara.add(Calendar.DATE,1);
                        tgl_endpay = tanggal_sementara.getTime().getDate();
                        bln_endpay = tanggal_sementara.getTime().getMonth();
                        thn_endpay = tanggal_sementara.getTime().getYear()+1900;
                        ldt_endpay = (FormatString.rpad("0",Integer.toString(tgl_endpay),2)).concat("/");
                        ldt_endpay = ldt_endpay.concat(FormatString.rpad("0",Integer.toString(bln_endpay),2));
                        ldt_endpay = ldt_endpay.concat("/");
                        ldt_endpay = ldt_endpay.concat(Integer.toString(thn_endpay));
                        ldt_endpay4 = df.parse(ldt_endpay);
                    if (flag_jenis_plan.intValue()!=1)
                    {
                        ldt_endpay4 =ldt_endpay1;
                    }
                    
                    //biaya pokok
//                  Integer ai_month3=new Integer((10 * 12) - 1);
//                  Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
//                  Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
//                  if ((flag_platinumlink.intValue() == 1) && (tahun_beg_date_polis.intValue() > 2006))
//                  {
//                       ai_month3 =  new Integer(ai_month3.intValue() + 1);
//                  }
//                          
//                  Map param29=new HashMap();
//                  param29.put("v_strBeginDate",v_strBeginDate);   
//                  param29.put("ai_month",ai_month3);  
//                  //logger.info("add month");
//                  
//                  Date ldt_endpay6 =null;
//                  ldt_endpay6=df1.parse((String)querySingle("select.addmonths", param29));
//                      
//                  tgl_endpay = ldt_endpay6.getDate();
//                  bln_endpay = ldt_endpay6.getMonth()+1;
//                  thn_endpay = ldt_endpay6.getYear()+1900;
//                  ldt_endpay = (FormatString.rpad("0",Integer.toString(tgl_endpay),2)).concat("/");
//                  ldt_endpay = ldt_endpay.concat(FormatString.rpad("0",Integer.toString(bln_endpay),2));
//                  ldt_endpay = ldt_endpay.concat("/");
//                  ldt_endpay = ldt_endpay.concat(Integer.toString(thn_endpay));
//                  ldt_endpay5 = df.parse(ldt_endpay);

                }           
*/
                //------------------------------------------------------------
                // Insert Basic Plan information to MST_PRODUCT_INSURED
                proc_save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser, mapperEspaj);     
                
                //untuk DBD FREE                
                if((edit.getDatausulan().getLsbs_id() == 187 && (edit.getDatausulan().getLsdbs_number() == 2 || edit.getDatausulan().getLsdbs_number() == 3 ||  edit.getDatausulan().getLsdbs_number() == 4
                        ||  edit.getDatausulan().getLsdbs_number() == 12 ||  edit.getDatausulan().getLsdbs_number() == 13 ||  edit.getDatausulan().getLsdbs_number() == 14)) || 
                        (edit.getDatausulan().getLsbs_id() == 205 && (edit.getDatausulan().getLsdbs_number() == 2 || edit.getDatausulan().getLsdbs_number() == 3 ||  edit.getDatausulan().getLsdbs_number() == 4 ||
                                edit.getDatausulan().getLsdbs_number() == 6 ||  edit.getDatausulan().getLsdbs_number() == 7 || edit.getDatausulan().getLsdbs_number() == 8))){                                  
                    int lsbs_id = edit.getDatausulan().getLsbs_id();
                    int lsdbs_id = edit.getDatausulan().getLsdbs_number();
                    double mspr_tsi = edit.getDatausulan().getMspr_tsi();
                    double mspr_premium = edit.getDatausulan().getMspr_premium();
                    edit.getDatausulan().setLsbs_id(824);
                    edit.getDatausulan().setLsdbs_number(1);
                    edit.getDatausulan().setMspr_tsi(new Double(500000));
                    edit.getDatausulan().setMspr_premium(new Double(0));
                    proc_save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser, mapperEspaj);
                    edit.getDatausulan().setLsbs_id(lsbs_id);
                    edit.getDatausulan().setLsdbs_number(lsdbs_id);
                    edit.getDatausulan().setMspr_tsi(mspr_tsi);
                    edit.getDatausulan().setMspr_premium(mspr_premium);
                }
//              
//                  if (jumlah_rider.intValue()>0)
//                  {
//                      proc_save_rider(edit, strTmpSPAJ,v_intActionBy );
//                  }
//              
//              Double jumlah_premi = this.bacDao.sum_premi(strTmpSPAJ);
//              if (jumlah_premi == null)
//              {
//                  jumlah_premi = new Double(0);
//              }
//
//              if (flag_gutri.intValue() == 1)
//              {
//                  DetailPembayaran dp = new DetailPembayaran();
//                  dp.setReg_spaj(strTmpSPAJ);
//                  dp.setKe(new Integer(1));
//                  dp.setJenis_ttp(new Integer(1));
//                  dp.setCara_bayar(new Integer(5));
//                  dp.setTgl_bayar(df.parse("01/11/2006"));
//                  dp.setTgl_jatuh_tempo(null);
//                  dp.setTgl_rk(df.parse("01/11/2006"));
//                  dp.setKurs(edit.getDatausulan().getLku_id());
//                  dp.setJml_bayar(jumlah_premi);
//                  dp.setNilai_kurs(new Double(0));
//                  dp.setTgl_skrg(new Date());
//                  dp.setRef_polis_no(null);
//                  dp.setKeterangan(null);
//                  dp.setLus_login_name(Integer.toString(edit.getDatausulan().getLus_id()));
//                  dp.setAktif(new Integer(1));
//                  dp.setBank(new Integer(42));
//                  dp.setStatus("B");
//                  dp.setNo_kttp(null);
//                  this.bacDao.insertmst_deposit(dp);
//              }
                
                //produk karyawan, nik
//              if (flag_as.intValue()==2)
//              {
//                  proc_save_karyawan(edit, strTmpSPAJ,v_intActionBy );
//              }
//                      
                //-----------------------------------------------------------
                //-- Insert Excellink information to MST_ULINK and MST_DET_ULINK
//              if (kode_flag.intValue() > 1 && kode_flag.intValue() != 11 && kode_flag.intValue() != 15)
//              {
//                  proc_unitlink(edit,strTmpSPAJ,v_strDateNow,v_intActionBy ,currentUser ,ldt_endpay1 ,ldt_endpay4 ,ldt_endpay5);
//              }                       
//                  
//              //Power Save
//              if (kode_flag.intValue() == 1)
//              {
//                  proc_powersave(edit,strTmpSPAJ,v_strDateNow,v_intActionBy );
//              }
//                      
//              if (kode_flag.intValue() == 11 || kode_flag.intValue() == 15)
//              {
//                  proc_powersave_stable(edit,strTmpSPAJ,v_strDateNow,v_intActionBy );
//              }
                //------------------------------------------------------------
                // Insert Beneficiary information to MST_BENEFICIARY
//              proc_save_benef(edit, strTmpSPAJ );
                
                //insert peserta simas
//              Integer flag_simas = edit.getDatausulan().getFlag_simas();
//              if (flag_simas == null)
//              {
//                  flag_simas = new Integer(0);
//              }
//              if (flag_simas.intValue() == 1)
//              {
//                  Simas simas = new Simas();
//                  simas.setLsbs_id(edit.getDatausulan().getLsbs_id());
//                  simas.setLsdbs_number(edit.getDatausulan().getLsdbs_number());
//                  //simas.setDiscount(new Double(0));
//                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//              }   
                
//              if(edit.getDatausulan().getLsbs_id().intValue()==183){
//                  
//              }
        
        } catch (Exception e){
        	e.printStackTrace();
            strTmpSPAJ="";
            throw new IllegalArgumentException();
//            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//            prosesError(currentUser, e);
        //    logger.error("ERROR :" + e);
        }
        //return strTmpSPAJ;
//      strTmpSPAJ="";
//      TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        //prosesError(currentUser, e);
        edit.getPemegang().setReg_spaj(strTmpSPAJ);
        return edit;
    }
    
    public Integer prosesSimultan(Command command,String lsClientPpOld,String lsClientTtOld,String idSimultanPp,String idSimultanTt, CommonDao mapper)throws Exception{
        //TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        String lsClientPpNew = null,lsClientTtNew=null;String lsClientPmNew=null;
        String lsClientPp = null,lsClientTt=null;//String lsClientPm =null;
        //String lsClientPmOld = bacDao.selectMspoPayerOld(command.getSpaj());
        
        Integer insured=1;
        //hubungan diri sendiri mapu
        if(command.getLsreIdPp()==1){//hubungan diri sendiri
            if (lsClientPpOld.substring(0,2).equalsIgnoreCase("00") ){ // sudah mengunakan pac_conter dari awal proses BAC , jadi tidak perlu generate MCL_ID yang baru.
                    lsClientPpNew = lsClientPpOld;
                }else{
                    
                    lsClientPpNew=mapper.selectSequenceClientID();
                    if(lsClientPpNew==null){
//                        TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//                        return null;
                        throw new Exception("ID Client is null in process simultan");
                    }
                    //
                    Client client=new Client();
                    AddressNew addressNew=new AddressNew();
                    Personal personal = new Personal();
                    ContactPerson contactPerson = new ContactPerson();
                    client=selectMstClientNew(lsClientPpNew,lsClientPpOld, mapper);
                    addressNew=selectMstAddressNew(lsClientPpNew,lsClientPpOld, mapper);
                    mapper.insertMstClientNew(client);
                    mapper.insertMstAddressNew(addressNew);
                    updateMstPolicyMspoPolicyHolder(command.getSpaj(),lsClientPpNew, mapper);
                    updateMstInsuredMsteInsured(command.getSpaj(),lsClientPpNew, mapper);
        //          bacDao.updateMstPolicyMspoPayer(command.getSpaj(), lsClientPpNew);
        //           tambahan untuk badan usaha
                                
//                    if(client.getMcl_jenis() == 1){ -> Not needed for PA BSM - Daru
//                        personal = commonDao.selectProfilePerusahaan(lsClientPpOld);
//                        contactPerson = bacDao.selectpic(lsClientPpOld);
//                        personal.setMcl_id(lsClientPpNew);
//                        contactPerson.setMcl_id(lsClientPpNew);
//                        bacDao.insertMstCompanyId(personal, lsClientPpOld);
//                        bacDao.insertMstCompanyContactId(contactPerson, lsClientPpOld);
//                        bacDao.updateMstCompanyContactAddressId(contactPerson, lsClientPpOld);
//                        delete("delete.mst_company_contact_family", lsClientPpOld);
//                        String nama_suamiistri = contactPerson.getNama_si();
//                        if(nama_suamiistri == null)nama_suamiistri = "";
//                        if (!nama_suamiistri.equalsIgnoreCase(""))
//                        {
//                            Date tanggal_lahir_suamiistri = contactPerson.getTgllhr_si();
//                            Map param1=new HashMap();
//                            param1.put("mcl_id", lsClientPpNew);
//                            param1.put("nama", contactPerson.getNama_si());
//                            param1.put("lsre_id",5);
//                            param1.put("tanggal_lahir", tanggal_lahir_suamiistri );
//                            param1.put("insured", 1);
//                            param1.put("no", 0);
//                            bacDao.insertMstCompanyContactFamily(param1);
//                            //logger.info("insert mst keluarga");
//                        }
//                        
//                        String nama_anak1 = contactPerson.getNama_anak1();
//                        if(nama_anak1 == null)nama_anak1 = "";
//                        if (!nama_anak1.equalsIgnoreCase(""))
//                        {
//                            Date tanggal_lahir_anak1 = contactPerson.getTgllhr_anak1();
//                            Map param1=new HashMap();
//                            param1.put("mcl_id", lsClientPpNew);
//                            param1.put("nama", contactPerson.getNama_anak1());
//                            param1.put("lsre_id",4);
//                            param1.put("tanggal_lahir", tanggal_lahir_anak1);
//                            param1.put("insured", 1);
//                            param1.put("no", 1);
//                            bacDao.insertMstCompanyContactFamily(param1);
//                            //logger.info("insert mst keluarga");
//                        }
//                        
//                        String nama_anak2 = contactPerson.getNama_anak2();
//                        if(nama_anak2 == null)nama_anak2 = "";
//                        if (!nama_anak2.equalsIgnoreCase(""))
//                        {
//                            Date tanggal_lahir_anak2 = contactPerson.getTgllhr_anak2();
//                            Map param1=new HashMap();
//                            param1.put("mcl_id", lsClientPpNew);
//                            param1.put("nama", contactPerson.getNama_anak2());
//                            param1.put("lsre_id",4);
//                            param1.put("tanggal_lahir", tanggal_lahir_anak2);
//                            param1.put("insured", 1);
//                            param1.put("no", 2);
//                            bacDao.insertMstCompanyContactFamily(param1);
//                            //logger.info("insert mst keluarga");
//                        }
//                        
//                        String nama_anak3 = contactPerson.getNama_anak2();
//                        if(nama_anak3 == null)nama_anak3 = "";
//                        if (!nama_anak3.equalsIgnoreCase(""))
//                        {
//                            Date tanggal_lahir_anak3 = contactPerson.getTgllhr_anak3();
//                            Map param1=new HashMap();
//                            param1.put("mcl_id", lsClientPpNew);
//                            param1.put("nama", contactPerson.getNama_anak3());
//                            param1.put("lsre_id",4);
//                            param1.put("tanggal_lahir", tanggal_lahir_anak3);
//                            param1.put("insured", 1);
//                            param1.put("no", 3);
//                            bacDao.insertMstCompanyContactFamily(param1);
//                            //logger.info("insert mst keluarga");
//                        }
//                        bacDao.deleteMstCompanyContactId(contactPerson, lsClientPpOld);
//                        bacDao.deleteMstCompanyId(personal, lsClientPpOld);
//                    }
                }
            //===
            lsClientPp=lsClientPpNew;
            lsClientTt=lsClientPpNew;
            if(idSimultanPp==null){//tidak simultan
                idSimultanPp=mapper.selectSequenceSimultanID();
                idSimultanTt=idSimultanPp;
            }
            lsClientTtNew=lsClientPpNew;
        }else{//hubungan orang lain
            //pemegang
            if (lsClientPpOld.substring(0,2).equalsIgnoreCase("00") ){ // sudah mengunakan pac_conter dari awal proses BAC , jadi tidak perlu generate MCL_ID yang baru.
                lsClientPpNew = lsClientPpOld;
                lsClientTtNew = lsClientTtOld;
            }else{
                lsClientPpNew=mapper.selectSequenceClientID();
                if(lsClientPpNew==null){
//                    TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//                    return null;
                    throw new Exception("ID Client is null in process simultan");
                }
                Client client=new Client();
                AddressNew addressNew=new AddressNew();
                Personal personal = new Personal();
                ContactPerson contactPerson = new ContactPerson();
                client=selectMstClientNew(lsClientPpNew,lsClientPpOld, mapper);
                addressNew=selectMstAddressNew(lsClientPpNew,lsClientPpOld, mapper);
                mapper.insertMstClientNew(client);
                mapper.insertMstAddressNew(addressNew);
                updateMstPolicyMspoPolicyHolder(command.getSpaj(),lsClientPpNew, mapper);
    //           tambahan untuk badan usaha
//                if(client.getMcl_jenis() == 1){ -> Not needed for PA BSM - Daru
//                    personal = commonDao.selectProfilePerusahaan(lsClientPpOld);
//                    contactPerson = bacDao.selectpic(lsClientPpOld);
//                    personal.setMcl_id(lsClientPpNew);
//                    contactPerson.setMcl_id(lsClientPpNew);
//                    bacDao.insertMstCompanyId(personal, lsClientPpOld);
//                    bacDao.insertMstCompanyContactId(contactPerson, lsClientPpOld);
//                    bacDao.updateMstCompanyContactAddressId(contactPerson, lsClientPpOld);
//                    bacDao.deleteMstCompanyContactFamily(lsClientPpOld);
//                    String nama_suamiistri = contactPerson.getNama_si();
//                    if(nama_suamiistri == null)nama_suamiistri = "";
//                    if (!nama_suamiistri.equalsIgnoreCase(""))
//                    {
//                        Date tanggal_lahir_suamiistri = contactPerson.getTgllhr_si();
//                        Map param1=new HashMap();
//                        param1.put("mcl_id", lsClientPpNew);
//                        param1.put("nama", contactPerson.getNama_si());
//                        param1.put("lsre_id",5);
//                        param1.put("tanggal_lahir", tanggal_lahir_suamiistri );
//                        param1.put("insured", 1);
//                        param1.put("no", 0);
//                        bacDao.insertMstCompanyContactFamily(param1);
//                        //logger.info("insert mst keluarga");
//                    }
//                    
//                    String nama_anak1 = contactPerson.getNama_anak1();
//                    if(nama_anak1 == null)nama_anak1 = "";
//                    if (!nama_anak1.equalsIgnoreCase(""))
//                    {
//                        Date tanggal_lahir_anak1 = contactPerson.getTgllhr_anak1();
//                        Map param1=new HashMap();
//                        param1.put("mcl_id", lsClientPpNew);
//                        param1.put("nama", contactPerson.getNama_anak1());
//                        param1.put("lsre_id",4);
//                        param1.put("tanggal_lahir", tanggal_lahir_anak1);
//                        param1.put("insured", 1);
//                        param1.put("no", 1);
//                        bacDao.insertMstCompanyContactFamily(param1);
//                        //logger.info("insert mst keluarga");
//                    }
//                    
//                    String nama_anak2 = contactPerson.getNama_anak2();
//                    if(nama_anak2 == null)nama_anak2 = "";
//                    if (!nama_anak2.equalsIgnoreCase(""))
//                    {
//                        Date tanggal_lahir_anak2 = contactPerson.getTgllhr_anak2();
//                        Map param1=new HashMap();
//                        param1.put("mcl_id", lsClientPpNew);
//                        param1.put("nama", contactPerson.getNama_anak2());
//                        param1.put("lsre_id",4);
//                        param1.put("tanggal_lahir", tanggal_lahir_anak2);
//                        param1.put("insured", 1);
//                        param1.put("no", 2);
//                        bacDao.insertMstCompanyContactFamily(param1);
//                        //logger.info("insert mst keluarga");
//                    }
//                    
//                    String nama_anak3 = contactPerson.getNama_anak2();
//                    if(nama_anak3 == null)nama_anak3 = "";
//                    if (!nama_anak3.equalsIgnoreCase(""))
//                    {
//                        Date tanggal_lahir_anak3 = contactPerson.getTgllhr_anak3();
//                        Map param1=new HashMap();
//                        param1.put("mcl_id", lsClientPpNew);
//                        param1.put("nama", contactPerson.getNama_anak3());
//                        param1.put("lsre_id",4);
//                        param1.put("tanggal_lahir", tanggal_lahir_anak3);
//                        param1.put("insured", 1);
//                        param1.put("no", 3);
//                        bacDao.insertMstCompanyContactFamily(param1);
//                        //logger.info("insert mst keluarga");
//                    }
//                    bacDao.deleteMstCompanyContactId(contactPerson, lsClientPpOld);
//                    bacDao.deleteMstCompanyId(personal, lsClientPpOld);
//                }
                //===
                //tertanggung
                lsClientTtNew=mapper.selectSequenceClientID();
                if(lsClientTtNew==null){
//                    TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//                    return null;
                    throw new Exception("ID Client is null in process simultan");
                }
                Client client2=new Client();
                AddressNew addressNew2=new AddressNew();
                client2=selectMstClientNew(lsClientTtNew,lsClientTtOld, mapper);
                addressNew2=selectMstAddressNew(lsClientTtNew,lsClientTtOld, mapper);
                mapper.insertMstClientNew(client2);
                mapper.insertMstAddressNew(addressNew2);
                updateMstInsuredMsteInsured(command.getSpaj(),lsClientTtNew, mapper);
                
            }
            lsClientPp=lsClientPpNew;
            lsClientTt=lsClientTtNew;
            if(idSimultanPp==null){//tidak simultan
                idSimultanPp=mapper.selectSequenceSimultanID();
            }
            if(idSimultanTt==null){//tidak simultan
                idSimultanTt=mapper.selectSequenceSimultanID();
            }
            
//          if (lsClientPmOld != null){
//              
//          //pembayar premi
//          lsClientPmNew= uwDao.wf_get_client_id(command.getLcaIdPp());
//          if(lsClientPmNew==null){
//              TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//              return null;
//          }
//              Client client3 = new Client();
//              AddressNew addressNew3 = new AddressNew();
//              client3 = uwDao.selectMstClientNew(lsClientPmNew, lsClientPmOld);
//              addressNew3 = uwDao.selectMstAddressNew(lsClientPmNew, lsClientPmOld);
//              uwDao.insertMstClientNew(client3);
//              uwDao.insertMstAddressNew(addressNew3);
//              bacDao.updateMstPolicyMspoPayer(command.getSpaj(), lsClientPmNew);
//          }
            
        }

        wf_sts_client(lsClientPp,new Integer(1), mapper);
        wf_sts_client(lsClientTt,new Integer(1), mapper);
        
        
        wfInsSimultanNew(command.getSpaj(), lsClientTtNew, lsClientPpNew,insured,idSimultanPp,idSimultanTt, mapper);
        
        return new Integer(0);
    }
    
    public Client selectMstClientNew(String mclIdnew,String mclIdOld, CommonDao mapper){
        HashMap param=new HashMap();
        param.put("mcl_id_new",mclIdnew);
        param.put("mcl_id_old",mclIdOld);
        return mapper.selectMstClientNew(param);
    }
    
    public AddressNew selectMstAddressNew(String mclIdNew,String mclIdOld, CommonDao mapper){
        HashMap param=new HashMap();
        param.put("mcl_id_new",mclIdNew);
        param.put("mcl_id_old",mclIdOld);
        return mapper.selectMstAddressNew(param);
    }
    
    public void updateMstPolicyMspoPolicyHolder(String spaj,String mspoPolicyHolder, CommonDao mapper){
        Map up_policy=new HashMap();
        up_policy.put("ls",mspoPolicyHolder);
        up_policy.put("nospaj",spaj);
        mapper.commonupdateMstPolicy(up_policy);
    }
    
    public void updateMstInsuredMsteInsured(String spaj,String msteInsured, CommonDao mapper){
        Map up_insured=new HashMap();
        up_insured.put("ls",msteInsured);
        up_insured.put("nospaj",spaj);
        mapper.commonupdateMstInsured(up_insured);
    }
    
    public void wf_sts_client(String mclId,Integer ljcId, CommonDao mapper){
        List list=selectMstStsClient(mclId,ljcId, mapper);
        if(list.isEmpty()){
            insertMstStsClient(mclId,ljcId,new Integer(1), mapper);
        }else{
            updateMstStsClient(mclId,ljcId,new Integer(1), mapper);
        }
        
    }
    
    public List selectMstStsClient(String MclId,Integer ljcId, CommonDao mapper){
        Map param=new HashMap();
        param.put("mcl_id",MclId);
        param.put("ljc_id",ljcId);
        return mapper.selectMstStsClientMscActive(param);
    }
    
    public void insertMstStsClient(String mclId,Integer ljcId,Integer mscActive, CommonDao mapper){
        Map insParam=new HashMap();
        insParam.put("mcl_id",mclId);           
        insParam.put("ljc_id",ljcId);
        insParam.put("msc_active",mscActive);
        mapper.insertMstStsClient(insParam);
    }
    
    public void updateMstStsClient(String mclId,Integer ljcId,Integer mscActive, CommonDao mapper){
        Map upParam=new HashMap();
        upParam.put("mcl_id",mclId);
        upParam.put("ljc_id",ljcId);
        upParam.put("msc_active",mscActive);
        mapper.updateMstStsClient(upParam);
    }
    
    public void wfInsSimultanNew(String spaj,String msteInsured,String mspoPolicyHolder,Integer insured,String idSimultanPp,String idSimultanTt, CommonDao mapper){
        Integer l_simultan_no = null;
           String s_client,idSimultan;
           for(int j=1;j<=2;j++){
               if(j== 1) {
                   idSimultan=idSimultanTt;
                   s_client = msteInsured; 
               }else {
                   idSimultan=idSimultanPp;
                   s_client = mspoPolicyHolder;
               }   
               if((j== 1) || (!msteInsured.equals(mspoPolicyHolder))){
                   l_simultan_no=mapper.selectMstSimultaneous(s_client);
               }
               if(l_simultan_no==null)
                   l_simultan_no=new Integer(0);
               
               l_simultan_no=new Integer(l_simultan_no+1);
               //
               insertMstSimultaneousNew(spaj,s_client,l_simultan_no,(j-1),insured,idSimultan, mapper);
           }
    }
    
    public void insertMstSimultaneousNew(String spaj,String mclId,Integer SimultanNo,int value,Integer insured,String idSimultan, CommonDao mapper){
        Map param=new HashMap();
        param.put("txtnospaj",spaj);
        param.put("s_client",mclId);
        param.put("l_simultan_no",SimultanNo);
        param.put("j",new Integer(value));
        param.put("mste_insured_no",insured);
        param.put("id_simultan",idSimultan);
        mapper.insertMstSimultaneous(param);
    }
    
    public Map prosesReasUnderwritingPas(Reas dataReas,  CommonDao mapper, CommonDao mapperEspaj)throws Exception{
        //TransactionInterceptor.currentTransactionStatus().setRollbackOnly();  
        Map map=new HashMap();  
        Integer liReas=null;
        Integer liReasRider=null;
        Integer liBackup=null;
        boolean abAdd=true;
        Map mapReas=getReasIndividu(dataReas,abAdd,  mapper, mapperEspaj);
        if(mapReas==null)
            return null;
        
        liReas=(Integer)mapReas.get("liReas");
        liReasRider=(Integer)mapReas.get("liReasRider");
        if(liReasRider==1 && liReas==0)
            liReas=1;
        
        // UW LImit Untuk fakultatif
        //power save tidak ada fakultatif kecuali umur tt >=69
        Map mData=mapper.selectDataUsulan(dataReas.getSpaj());
        Integer lsbsId=(Integer)mData.get("LSBS_ID");
        String prodSave="086, 094, 123, 124, 142, 143, 144, 155, 158,40,130, 175, 176, 177, 207";
        String bisnisId=f3.format(lsbsId);
        Integer pos=prodSave.indexOf(bisnisId);
        if(liReas==2){//jika facultative
            if(pos>0 && dataReas.getUmurTt()<69){ //bila powersave dan umur < 69
                liReas=1;
                updateMReasTempMsteReas(dataReas.getSpaj(),liReas, null, mapper);
            }
        }   
        if(liReas==null){
         //   TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            return null;
        }else if(liReas==0){    //Non Reas
            liBackup=3;
            Boolean ok = false;
            do{
                try{
                    insertMstPositionSpaj(dataReas.getCurrentUser().getLus_id(), "PROSES REAS(NON REAS)", dataReas.getSpaj(), 0, mapperEspaj);
                    ok=true;
                }catch(Exception e){};
            }while (!ok);
        }else if(liReas==1){    //Treaty
            liBackup=2;
            Boolean ok = false;
            do{
                try{
                    insertMstPositionSpaj(dataReas.getCurrentUser().getLus_id(), "PROSES REAS(TREATY)", dataReas.getSpaj(), 0, mapperEspaj);
                    ok=true;
                }catch(Exception e){};
            }while (!ok);
        }else if(liReas==2){    //facultative
            liBackup=0;
            Boolean ok = false;
            do{
                try{
                    insertMstPositionSpaj(dataReas.getCurrentUser().getLus_id(), "PROSES REAS(FACULTATIVE)", dataReas.getSpaj(), 0, mapperEspaj);
                    ok=true;
                }catch(Exception e){};
            }while (!ok);
        }
        //
        Integer countSim=mapper.selectCountMstSimultaneous(dataReas.getSpaj());
        if(countSim==0){
            Map mPemegang=mapper.selectPemegang(dataReas.getSpaj());
            Integer lsreId=(Integer)mPemegang.get("LSRE_ID");
            
            String idSimultanPp,idSimultanTt = null;
            //cek ada id simultan blom jika ada ambil
            if(lsreId==1){
                idSimultanPp=mapper.selectMstSimultaneousIdSimultan(dataReas.getMspoPolicyHolder());
                if(idSimultanPp==null){
                    idSimultanPp=mapper.selectSequenceSimultanID();
                }
                idSimultanTt=idSimultanPp;
            }else{
                idSimultanPp=mapper.selectMstSimultaneousIdSimultan(dataReas.getMspoPolicyHolder());
                idSimultanTt=mapper.selectMstSimultaneousIdSimultan(dataReas.getMsteInsured());
                if(idSimultanPp==null)
                    idSimultanPp=mapper.selectSequenceSimultanID();
                if(idSimultanTt==null)
                    idSimultanTt=mapper.selectSequenceSimultanID();
            }
            wfInsSimultanNew(dataReas.getSpaj(), dataReas.getMsteInsured(), dataReas.getMspoPolicyHolder(),
                    dataReas.getInsuredNo(),idSimultanPp,idSimultanTt, mapper);
        }
        
        //bila bulannya begdate < bulannya sysdate, liReas=2 di buat fakultatif sesuai permintaan atik (june 9 2008)
        Integer hasil= mapper.selectIntMonth(dataReas.getSpaj());
//      if (hasil==0){
//          liReas=2;
//          liBackup=0;
//          uwDao.updateMReasTempMsteReas(dataReas.getSpaj(), liReas, liBackup);
//      }else 
//          liReas=liReas;
        
        updateMstInsuredReasnBackup(dataReas.getSpaj(),dataReas.getInsuredNo(),liReas,liBackup,null,null, mapper);
        wf_ins_cash_tpd(dataReas.getSpaj(),dataReas.getInsuredNo(), mapper);
        //proses simultan medis
        Integer medis=null;
        //medis=prosesSimultanMedis(dataReas.getSpaj(),dataReas.getInsuredNo());
        //email automatis ke akturia tentang hasil reas
        //emailReas(dataReas);
        map.put("liReas",liReas);
        map.put("medis", medis);
        return map;
    }
    
    public Map getReasIndividu(Reas dataReas,boolean abAdd, CommonDao mapper, CommonDao mapperEspaj)throws Exception{
        int aiCnt=0;
        String spaj=dataReas.getSpaj();
        Integer liReas=0;
        boolean reasRider=false;
        Integer liReasRider=0;
        Integer liBisnisId[]=new Integer[5];
        Integer liBisnisNo[]=new Integer[5];
        Integer liReasClient[]=new Integer[5];
        Double ldecRateSar[]=new Double[5];
        Double ldecLimit[]=new Double[5];
        Double[] ldecLimitTemp=new Double[5];
        Double sarTemp1[]=new Double[10]; 
        Double ldecTotalReas[]=new Double[5];
        Integer liThKe,liMedis=null,liInsuredNo,liStsPolis=null,liCbayar,liLbayar,liLTanggung,liInsMonth,liFlat;
        Integer liTypeReas=null;
        String lsRegSpaj,lsKursId=null;
        Double ldecUp=null,ldecBunga,ldecReasComp;
        Double ldecQsReas[]=new Double[5];
        int liCnt[]=new int[10], liAge[]=new int[10];
        Double[][][] ldecReasLf=new Double[3][6][3]; // dec{2} pada pb
        Double[][][] ldecReasPa=new Double[3][6][4]; // dec{2} pada pb
        Double[][][] ldecReasPk=new Double[3][6][3]; // dec{2} pada pb
        Double[][][] ldecReasSs=new Double[3][6][3]; // dec{2} pada pb
        Double[][][] ldecReasCp=new Double[3][6][3]; // dec{2} pada pb
        Double[][][] ldecReasTPD=new Double[3][6][3]; //Tambahan Dian
        Double ldecExtMort[]=new Double[10]; //dec{3}
        Double ldecKurs,ldecTsi=null,ldecSar,ldecReasSar,ldecOwnSar=null;
        //Double ldecReasHcp[]=new Double [5]; //simultan,tsi,sar,retensi,reas

        Date begDate;
        Integer liTypeBisnis;
        Integer paramTypeBisnis=1;
        int posDoc[] = new int[3];
        
        Map mPosisi=selectF_check_posisi(spaj, mapperEspaj);
        Integer lspdIdTemp=(Integer)mPosisi.get("LSPD_ID");
        Integer lspdId=lspdIdTemp;
        
        Integer lstbId=1;//individu
        Integer umurPp,umurTt;
        String lcaId;
        String lsOldSpaj,lsClient,lkuId,noPolis;
        Integer liSimultanNo;
        Integer liPHolder = null,liStsAksep;
        int llRow[] = new int[10];
        List ldsProd;
        //int flagRider=0;
        posDoc[1]=2;
        posDoc[2]=10;
        String msteInsured,mspoPolicyHolder,idSimultan;
        Integer insuredNo;
        
        //data usulan asuransi
        Map mDataUsulan=mapper.selectDataUsulan(spaj);
        begDate=(Date)mDataUsulan.get("MSTE_BEG_DATE");
        Integer lsbsId=(Integer)mDataUsulan.get("LSBS_ID");
        Integer lsdbsNumber=(Integer)mDataUsulan.get("LSDBS_NUMBER");
        //tertanggung
        Map mTertanggung=mapper.selectTertanggung(spaj);
        insuredNo=(Integer)mTertanggung.get("MSTE_INSURED_NO");
        msteInsured=(String)mTertanggung.get("MCL_ID");
        umurTt=(Integer)mTertanggung.get("MSTE_AGE");
        //pemegang
        Policy policy=selectDw1Underwriting(spaj,lspdId,lstbId, mapper);
        mspoPolicyHolder=policy.getMspo_policy_holder();
        //insPeriod=policy.getMspo_ins_period();
        //payPeriod=policy.getMspo_pay_period();
        lkuId=policy.getLku_id();
        umurPp=policy.getMspo_age();
        //lcaId=policy.getLca_id();
        
        //Deklarasi variabel Array
        for(liCnt[1]=1;liCnt[1]<=2;liCnt[1]++){ // 1=inssured ; 2=policy-holder
            for(liCnt[2]=1;liCnt[2]<=5;liCnt[2]++) {// 1=total-sar-simultan ; 2=tsi ; 3=resiko-awal ; 4=retensi ; 5=sum-reas
                ldecReasLf [liCnt[1]] [liCnt[2]] [1]=new Double(0);  //term
                ldecReasLf [liCnt[1]] [liCnt[2]] [2]=new Double(0);  //life
                ldecReasPa [liCnt[1]] [liCnt[2]] [1]=new Double(0);  //ssp
                ldecReasPa [liCnt[1]] [liCnt[2]] [2]=new Double(0);  //pa include (risk-a)
                ldecReasPa [liCnt[1]] [liCnt[2]] [3]=new Double(0);  //pa rider
                ldecReasPk [liCnt[1]] [liCnt[2]] [1]=new Double(0);  //pk include
                ldecReasPk [liCnt[1]] [liCnt[2]] [2]=new Double(0);  //pk rider
                ldecReasSs [liCnt[1]] [liCnt[2]] [1]=new Double(0);  //ssh,ss,ss+
                ldecReasCp [liCnt[1]] [liCnt[2]] [1]=new Double(0);  //cash plan
            }
        }
        
        /*
        Yusuf : contoh cara bacanya
        ldecReasLf [1] [1] [1] = INSURED, TOTAL SAR SIMULTAN, TERM
        ldecReasLf [1] [2] [1] = INSURED, TSI, TERM
         */
        
        liCnt[4]=0;
        ldecExtMort[1]=new Double(0);
        ldecKurs=selectGetKursReins("02",begDate, mapper);
        //
//      Integer jum=uwDao.selectCountMReasTemp(spaj);
        // delete table m_reas_temp dan sar temp
        //if(jum>0){
            mapper.deleteMReasTemp(spaj);
            mapper.deleteMSarTemp(spaj);
            mapper.deleteMReasTempNew(spaj);
        //}
        //
        int llReas;
        
        String bisnisId=f3.format(lsbsId);
        String bisnisNumber=f3.format(lsdbsNumber);
        if("024,031,033,070,071,072,172".indexOf(bisnisId)>0)
            llReas=2;
        else
            llReas=1;
        
        double retensi_pa = (double) 0;     
        
        for(liCnt[1]=1;liCnt[1]<=llReas;liCnt[1]++){// 1=inssured ; 2=policy-holder     
            for(liCnt[2]=1;liCnt[2]<=2;liCnt[2]++) {    // 1=simultan ; 2=now               
                if(liCnt[2]==1){
                    lsOldSpaj=null;
                    liSimultanNo=null;
                    if (liCnt[1] == 1)
                        lsClient = msteInsured;
                    else    
                        lsClient = mspoPolicyHolder;
                    //
                    lsOldSpaj=mapper.selectMstCancelRegSpaj(spaj);
                    //TODO
                    if(lsOldSpaj!=null)
                        liSimultanNo=selectMinMstSimultaneous(lsOldSpaj,lsClient, mapper);
                    if(liSimultanNo==null)
                        liSimultanNo=0;
                    //
                    //simultan tertanggung kecuali produk ekasiswa emas
                    //liCnt[1]-1 untuk flag pemegang coz 0=tertanggung 1=pemegang
                    /**
                     * TODO
                     * kudu benerin reas kalo insert cuma 1 yaitu tertanggung jadi pemegangnya ga di insert
                     */
                    
                    if (lsbsId.equals(172)){
                        idSimultan=selectIdSimultan(spaj, 1, mapper);
                    }else {
                        if (lsOldSpaj!=null){
                            idSimultan=selectIdSimultan(lsOldSpaj, liCnt[1]-1, mapper);
                        }else
                            idSimultan=selectIdSimultan(spaj, liCnt[1]-1, mapper);
                    }       
                    ldsProd=selectDdsSarNew(idSimultan,spaj,liSimultanNo, mapper);
                    llRow[1]=ldsProd.size();
                }else{
                    ldsProd=selectDdsSarnNew(spaj,insuredNo, mapper);                 
//                  idSimultan=uwDao.selectIdSimultan(spaj, liCnt[1]-1);
//                  ldsProd=uwDao.selectDdsSarNew(idSimultan, spaj, 0);
                    llRow[1]=ldsProd.size();
                }
                //
                
                for (liCnt[3] = 1;liCnt[3]<=llRow[1];liCnt[3]++){ // per-detil product
                    D_DS_Sar dataProd=(D_DS_Sar)ldsProd.get(liCnt[3]-1);
                    liBisnisId[1]=dataProd.getLsbs_id();
                    liBisnisNo[1]=dataProd.getLsdbs_number();
                    liReasClient[1]=dataProd.getLsdbs_reas_client();
                    noPolis=dataProd.getMspo_policy_no();
                    
                    if(dataProd.getRetensi_ssp() != null) retensi_pa += dataProd.getRetensi_ssp();
                    
                    if(liBisnisId[1]<600){//pokok;
                        liThKe= new Integer(selectTahunKe(defaultDateFormat.format(dataProd.getMste_beg_date()),defaultDateFormat.format(begDate), mapper).intValue());
                        liMedis=dataProd.getMste_medical();
                        lsRegSpaj=dataProd.getReg_spaj();
                        liInsuredNo=dataProd.getMste_insured_no();
                        liStsPolis=dataProd.getLssp_id();
                        liCbayar=dataProd.getLscb_id();
                        liLbayar=dataProd.getMspo_pay_period();
                        liLTanggung=dataProd.getMspo_ins_period();
                        liInsMonth=dataProd.getMspo_ins_bulan();
                        liFlat=dataProd.getMspo_flat();
                        ldecBunga=dataProd.getMspr_bunga_kpr();
                        liTypeBisnis=dataProd.getLstb_id();
                        liTypeReas=dataProd.getLstr_id();
                        ldecQsReas[1]=dataProd.getLstr_quota_reas();
                        lsKursId=dataProd.getLku_id();
                        ldecUp=dataProd.getMspr_tsi();
                        liBisnisId[2]=liBisnisId[1];// assign main_bisnis_id
                        liBisnisNo[2]=liBisnisNo[1];// assign main_bisnis_no
                        //
                        if(liCnt[2] == 1){
                            liPHolder= dataProd.getMssm_pemegang();
                            liStsAksep = dataProd.getLssa_id();
                            if (liStsPolis == 10 && liStsAksep != 5){
                                if(liStsAksep!= 8){
                                     return null;
                                }
                            }
                        }else{
                            ldecQsReas[2]   = ldecQsReas[1];
                            liReasClient[2] = liReasClient[1];
                            liAge[1] = umurTt;
                            liAge[2] = umurTt;
                            if (liReasClient[2] == 2 || liReasClient[2] == 3)
                                liAge[2] = umurPp;
                            if (liCnt[1] == 1 )
                                liPHolder = 0 ;
                            else 
                                liPHolder = 1;
                        }
                        //
                        ldecTsi=selectGetTsiReas(spaj,insuredNo,liPHolder,liBisnisId[2],liBisnisNo[2],liBisnisId[1],
                                liBisnisNo[1],lsKursId,ldecUp, mapper);
                        if(ldecTsi==null)
                            return null;
                        if(ldecTsi == 0)
                            continue;
                        //
                        if(liReasClient[1]== 1 && liPHolder ==0  || liReasClient[1]== 2 && 
                                liPHolder ==1 || liReasClient[1] > 2){
                            if(liTypeBisnis==1){//individu
                                ldecRateSar[1] = selectGetSar(1, liBisnisId[1], liBisnisNo[1], lsKursId, liCbayar, liLbayar, liLTanggung, liThKe, umurTt, mapper);
                                ldecRateSar[2] = selectGetSar(2, liBisnisId[1], liBisnisNo[1], lsKursId, liCbayar, liLbayar, liLTanggung, liThKe, umurTt, mapper);
                            }else if(liTypeBisnis==2){//MRI
//                                if(liInsMonth==null) -> Not needed for PA BSM - Daru
//                                    liInsMonth =0;
//                                //
//                                ldecRateSar[1] = uwDao.selectGetMriSar(1, liBisnisId[1], liFlat, ldecBunga, 
//                                                            new Integer( (liLTanggung * 12) + liInsMonth), liThKe,err);
//                                ldecRateSar[2] = uwDao.selectGetMriSar(2, liBisnisId[1], liFlat, ldecBunga, 
//                                                            new Integer( (liLTanggung * 12) + liInsMonth), liThKe,err);
                            }
                            //
                            if (ldecRateSar[1]==null || ldecRateSar[2]==null )
                                return null;
                            //
                            ldecSar      = new Double(ldecTsi * ldecRateSar[1] / 1000);
                            if (liCnt[2] == 1)
                                if (liBisnisId[1]==56||liBisnisId[1]==55||liBisnisId[1]==64||liBisnisId[1]==64){
                                    ldecSar      = new Double(ldecTsi * ldecRateSar[1] / 1000);
                                }else 
//                              ldecSar = new Double(ldecSar * ldecRateSar[2] / 1000);
                                    ldecSar= dataProd.getMspr_tsi();    
                            if (liBisnisId[1] == 66 || liBisnisId[1] == 79 || 
                                    liBisnisId[1] == 80)
                                ldecSar = new Double(ldecSar * 1.5); //procare,SEHAT,PRIVACY
                            //
                            ldecReasSar = new Double(ldecSar * ldecQsReas[1] / 100);
                            ldecOwnSar  = new Double(ldecSar - ldecReasSar);
                            //
                            if( dataProd.getLsgr_id()==3){// simas sehat harian/HEALTH
                                if (liCnt[2]== 1)
                                    ldecReasSs[liCnt[1]][1][1] =new Double( ldecReasSs[liCnt[1]][1][1] +  
                                                                    selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasSs[liCnt[1]][2][1] =new Double( ldecReasSs[liCnt[1]][2][1] +ldecTsi );
                                    if(bisnisId=="132"|| bisnisId.equals("132")||bisnisId=="131"|| bisnisId.equals("131")||bisnisId=="183" || bisnisId.equals("183")||bisnisId=="189"|| bisnisId.equals("189")||bisnisId=="178"|| bisnisId.equals("178") || bisnisId=="193"|| bisnisId.equals("193") || bisnisId.equals("195") || bisnisId=="204"|| bisnisId.equals("204") || bisnisId=="208"|| bisnisId.equals("208")){
                                        ldecReasSs[liCnt[1]][3][1] =new Double( ldecReasSs[liCnt[1]][3][1]+ ldecSar);
                                    }else{
                                    ldecReasSs[liCnt[1]][3][1] =new Double( ldecReasSs[liCnt[1]][3][1]+ ldecSar / 100 );//bc
                                    }
                                }   
                            }else if(dataProd.getLsgr_id()==2){  // simas super protection /PA
                                if (liCnt[2]== 1)
                                    ldecReasPa[liCnt[1]][1][1] =new Double( ldecReasPa[liCnt[1]][1][1] + 
                                                                    selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasPa[liCnt[1]][2][1] =new Double( ldecReasPa[liCnt[1]][2][1] +ldecTsi );
                                    ldecReasPa[liCnt[1]][3][1] =new Double( ldecReasPa[liCnt[1]][3][1]+ldecSar );
                                }   
                            }else{//others LIFE
                                //simultan
                                if (liCnt[2]== 1)
                                    //simultan
                                    ldecReasLf[liCnt[1]][1][2] =new Double( ldecReasLf[liCnt[1]][1][2] + 
                                                                    selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                //now
                                if (liCnt[2] == 2){
                                    //tsi
                                    ldecReasLf[liCnt[1]][2][2] =new Double( ldecReasLf[liCnt[1]][2][2] +ldecTsi );
                                    //sar
                                    ldecReasLf[liCnt[1]][3][2] =new Double( ldecReasLf[liCnt[1]][3][2]+ ldecSar );
                                
                                }   
                            }
                            //
                            if (abAdd)
                                if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){ // khusus untu EKA SISWA EMAS.. yang tertanggungnya aja yang direasin
                                    break;  
                                }else
                                aiCnt=insertMSarTemp(spaj,noPolis,liBisnisId[1],liBisnisNo[1],lsKursId,ldecSar,
                                        liStsPolis,liMedis,aiCnt,dataProd.getReg_spaj_ref(), mapper);
                        
                    }
                    //rider 
                    }else if(dataProd.getLsgr_id()==4){
                        if (liReasClient[1] == 1 && liPHolder == 0  || liReasClient[1] == 2 && 
                                liPHolder == 1 || liReasClient[1] > 2){
                            ldecUp=dataProd.getMspr_tsi();
                            ldecTsi =selectGetTsiReas(spaj,insuredNo,liPHolder,liBisnisId[2],liBisnisNo[2],
                                    liBisnisId[1],liBisnisNo[1],lsKursId,ldecUp, mapper);
                    
                            if(ldecTsi==null)
                                return null;
                            ldecSar=ldecTsi;
                            //untuk produk 601-808 tidak ada sar 223012008 (tabel atik)
//                          ldecSar=0.0;
                            if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu eka siswa.. yang tertanggungnya aja yang direasin
                                break;  
                            }else
                                aiCnt=insertMSarTemp(spaj,noPolis,liBisnisId[1],liBisnisNo[1],lsKursId,ldecSar,
                                        liStsPolis,liMedis,aiCnt,dataProd.getReg_spaj_ref(), mapper);
                            //
                            if(liBisnisId[1]==800){// PA Rider
                                if (liCnt[2]== 1)
                                    ldecReasPa[liCnt[1]][1][3] =new Double( ldecReasPa[liCnt[1]][1][3] + 
                                                                            selectKursAdjust(ldecSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasPa[liCnt[1]][2][3] =new Double( ldecReasPa[liCnt[1]][2][3] + ldecTsi );
                                    ldecReasPa[liCnt[1]][3][3] =new Double( ldecReasPa[liCnt[1]][3][3]+ldecSar );
                                }
                            }else if(liBisnisId[1]==801){// PK Rider
                                if (liCnt[2]== 1)
                                    ldecReasPk[liCnt[1]][1][2] =new Double( ldecReasPk[liCnt[1]][1][2] + 
                                                                            selectKursAdjust(ldecSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasPk[liCnt[1]][2][2] =new Double( ldecReasPk[liCnt[1]][2][2] +ldecTsi );
                                    ldecReasPk[liCnt[1]][3][2] =new Double( ldecReasPk[liCnt[1]][3][2]+ldecSar );
                                }   
                            }else if(liBisnisId[1]==803){// Term Rider 
                                //simultan
                                if (liCnt[2]== 1)
                                    //simultan
                                    ldecReasLf[liCnt[1]][1][1] =new Double( ldecReasLf[liCnt[1]][1][1] + 
                                                                            selectKursAdjust(ldecSar,ldecKurs,lkuId,lsKursId));
                                //now
                                if (liCnt[2] == 2){
                                    //tsi
                                    ldecReasLf[liCnt[1]][2][1] =new Double( ldecReasLf[liCnt[1]][2][1]+ldecTsi );
                                    //sar
                                    ldecReasLf[liCnt[1]][3][1] =new Double( ldecReasLf[liCnt[1]][3][1]+ldecSar );
                                }   
                            }else if(liBisnisId[1]==804){// WPD
                                //simultan
                                if (liCnt[2]== 1)
                                    //simultan
                                    ldecReasTPD[liCnt[1]][1][1] = new Double( ldecReasLf[liCnt[1]][1][1] + 
                                            selectKursAdjust(ldecSar,ldecKurs,lkuId,lsKursId));
                                //now
                                if (liCnt[2] == 2){
                                    //tsi
                                    ldecReasTPD[liCnt[1]][2][1] =new Double( ldecReasLf[liCnt[1]][2][1]+ldecTsi );
                                    //sar
                                    ldecReasTPD[liCnt[1]][3][1] =new Double( ldecReasLf[liCnt[1]][3][1]+ldecSar );
                                }   
                            }else if(liBisnisId[1]==820){// WPD
                                Double sar=dataProd.getMspr_tsi();
                                ldecUp=dataProd.getMspr_tsi();
                                ldecTsi =selectGetTsiReas(spaj,insuredNo,liPHolder,liBisnisId[2],liBisnisNo[2],
                                        liBisnisId[1],liBisnisNo[1],lsKursId,ldecUp, mapper);
//                              aiCnt=uwDao.insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, sar, liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref());
                                
                            }else if(liBisnisId[1]==802){
//                              Double sar=dataProd.getMspr_tsi();
//                              aiCnt=uwDao.insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, sar, liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref());
                                
                                Double reas=0.;
                                Double retensiPa=new Double(0);
//                                List lsRider=uwDao.selectMstProductInsuredRiderTambahanSar(spaj, 1, 1); -> Not needed for PA BSM - Daru
//                                for(int i=0;i<lsRider.size();i++){
//                                    Double retensiAwal=0.0; 
////                                  retensiAwal=0.0;
//                                    liReasRider=0;  
//                                    Product prod=(Product)lsRider.get(i);
//                                    retensiPa=uwDao.selectGetRetensi(prod.getLsbs_id(), prod.getLsdbs_number(), 1, 1, liMedis, prod.getLku_id(), begDate, liAge[1], spaj,err);
//                                    //cek di sar_temp dimana groupnya pa lalu bandingkan dengan retensi
////                                  reas=prod.getSar()-retensiLife; //yang lama dari ferry
//                                    retensiAwal=retensiPa;
//                                    Double sarTemp=prod.getSar();
////                                  retensiPa=retensiPa-prod.getSar();
////                                  reas=retensiPa;
//                                    if(sarTemp<=retensiAwal) //dian :prod rider sar<retensi reas=nol
////                                      retensiPa=0.0;
//                                        reas=0.0;
//                                    else{
//                                        reas= sarTemp-retensiPa;//prod rider sar>retensi
////                                      reas=retensiPa; //rider itu di reas
//                                    }
//                                    if(reas<=0)//rider tersebut di reas kan.
//                                        reas=0.0;
//                                    else{
//                                        reasRider=true;
//                                        liReasRider=1;  
//                                    }
//                                    /*
//                                    //kalo perlu aja di buka
//                                    Integer contReasTemp= uwDao.selectCountReasTempNew(spaj);
//                                    if (contReasTemp>0){
//                                        
//                                    }else
//                                    */
//                                    insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                                            liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas); 
//                                }
                                
                            }else{//tanya atik  untuk WPD dan HCR bagaimana? (802,804,805)
                                //produk yang sudah tidak terjual.
                                //dian: WPD udah di jual kembali 
                                return null;
                            }
                        }
                    }else if (dataProd.getLsgr_id()==3&&dataProd.getLsbs_id()==821){ 
                        Double sar = dataProd.getMspr_tsi();
                        aiCnt=insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, sar, liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref(), mapper);
                    //rider link & rider hidup bahagia (814,815) - Yusuf (26/02/2008)                       
                    }else if(dataProd.getLsgr_id()==5){
                        // --->cek product mainnya termasuk group yang mana
                        if(dataProd.getLsbs_id()==813||dataProd.getLsbs_id()==822){
//                          Double sar=dataProd.getMspr_tsi()*0.5;
                            Double sar=dataProd.getMspr_tsi();
                            if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu EKA SISWA EMAS.. yang tertanggungnya aja yang direasin
                                break;  
                            }else
                            aiCnt=insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, sar, liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref(), mapper);
                        }else if(dataProd.getLsbs_id()>=814 && dataProd.getLsbs_id()<=817){//sistem reas surplus (life) -- UP dari tabel
//                            Integer age; -> Not needed for PA BSM - Daru
//                            Double rate;
//                            Double sar;
//                            age=selectMstInsuredMsteAge(dataProd.getReg_spaj_ref(), 1, mapper);
//                            
////                          if(dataProd.getLsbs_id()==817){
////                              rate=4.312;
////                          }else
//                            
//                            //Yusuf (8 Aug 2011)
//                            //Selain produk2 tertentu, maka rate rider diambil dengan lsdbs_number = 1 (di hardcode karena sama semua)
//                            int lsdbs_number = 1;
//                            if(dataProd.getLsbs_id().intValue() == 815 && dataProd.getLsdbs_number().intValue() == 6 /*|| dataProd.getLsbs_id().intValue() == 815*/ /*&& dataProd.getLsdbs_number().intValue() == 5*/){
//                                lsdbs_number = 6;
//                            }
//                            
//                            if(lsbsId==120 && "019,020,021,022,023,024".indexOf(bisnisNumber)>-1){
//                                String kode_rider=f3.format(dataProd.getLsbs_id().intValue());
//                                if("815,817".indexOf(kode_rider)>-1){
//                                    lsdbs_number=dataProd.getLsdbs_number().intValue();
//                                    age=1;
//                                }
//                            }
//                            
//                            rate=uwDao.selectLstRateRider(dataProd.getLsbs_id(), lsdbs_number, age, dataProd.getLku_id());
//                            
//                            //TODO: KHUSUS TESTING 
//                            //if(rate == null) rate = 1000.;
//                            
//                            Product prodUtama=uwDao.selectMstProductInsuredUtamaFromSpaj(dataProd.getReg_spaj());
//                            
//                            //perhitungan utama (Yusuf, 20 Jun 2011) - nolnya kebanyakan 3
//                            sar = rate * prodUtama.getMspr_premium() / 1000;
//                            
//                            if (dataProd.getLsbs_id()==816||dataProd.getLsbs_id()==817){
//                                if (dataProd.getLscb_id()==6){//dian--cara bayar bulanan
//                                    sar=sar/0.1;
//                                }
//                                if (dataProd.getLscb_id()==1){//dian--triwulanan
//                                    sar=sar/0.27;
//                                }
//                                if (dataProd.getLscb_id()==2){//dian--semesteran
//                                    sar=sar/0.525;
//                                }
//                            }else if (dataProd.getLsbs_id()==814 || dataProd.getLsbs_id()==815){
//                                if (dataProd.getLscb_id()==6){//dian--cara bayar bulanan
//                                    sar=sar*12;
//                                }
//                                if (dataProd.getLscb_id()==1){//dian--triwulanan
//                                    sar=sar*4;
//                                }
//                                if (dataProd.getLscb_id()==2){//dian--semesteran
//                                    sar=sar*2;
//                                }
//                            }
//                            if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu eka siswa.. yang tertanggungnya aja yang direasin
//                                break;  
//                            }else{
//                                aiCnt=uwDao.insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, sar, liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref());
//                            }
                        }else{
                            if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu EKA SISWA EMAS.. yang tertanggungnya aja yang direasin
                                break;
//                          }else if(liBisnisId[1].intValue() == 810){ //Yusuf (5 Jul 2011) req hanifah, untuk PA tidak ada SAR nya
//                              continue;
                            }else
                            aiCnt=insertMSarTemp(spaj, noPolis, liBisnisId[1], liBisnisNo[1], lsKursId, dataProd.getMspr_tsi(), liStsPolis, liMedis, aiCnt, dataProd.getReg_spaj_ref(), mapper);
                        }
                    }else if(dataProd.getLsgr_id()==6 ){//rider include
                        if(liReasClient[1] == 1 && liPHolder == 0  || 
                                liReasClient[1]== 2 && liPHolder == 1 || liReasClient[1] > 2 ){
                            ldecTsi =selectGetTsiReas(spaj,insuredNo,liPHolder,liBisnisId[2],liBisnisNo[2],
                                        liBisnisId[1],liBisnisNo[1],lsKursId,ldecUp, mapper);
                            if(ldecTsi==null)
                                return null;
                            //
                            //procare
                            //if(liBisnisId[1] == 601 && liBisnisId[2] == 54)
                            //  ldecSar = new Double(100 * ldecTsi);
                            //else 
                            //  ldecSar = ldecTsi;
                            //tidak ada sar 23012008 (tabel dr atik) 
                            //
                            ldecSar=0.0;
                            ldecReasSar = new Double(ldecSar * ldecQsReas[1]/100);  
                            ldecOwnSar  = new Double(ldecSar - ldecReasSar);
                            if(liBisnisId[1]==600){// PA Include Resiko A
                                if (liCnt[2]== 1)
                                    ldecReasPa[liCnt[1]][1][2] =new Double( ldecReasPa[liCnt[1]][1][2] + 
                                                                            selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasPa[liCnt[1]][2][2] =new Double( ldecReasPa[liCnt[1]][2][2] +ldecTsi );
                                    ldecReasPa[liCnt[1]][3][2] =new Double( ldecReasPa[liCnt[1]][3][2]+ ldecSar );
                                }   
                            }else if(liBisnisId[1]==601){// PK Include
                                if (liCnt[2]== 1)
                                    ldecReasPk[liCnt[1]][1][1] =new Double( ldecReasPk[liCnt[1]][1][1] + 
                                                                            selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasPk[liCnt[1]][2][1] =new Double( ldecReasPk[liCnt[1]][2][1] + ldecTsi );
                                    ldecReasPk[liCnt[1]][3][1] =new Double( ldecReasPk[liCnt[1]][3][1]+ ldecSar );
                                }   
                            }else if(liBisnisId[1]>=806 && liBisnisId[1]<=808){/// CASH PLAN, tpd
                                if (liCnt[2]== 1)
                                    ldecReasCp[liCnt[1]][1][1] =new Double( ldecReasCp[liCnt[1]][1][1] + 
                                                                            selectKursAdjust(ldecOwnSar,ldecKurs,lkuId,lsKursId));
                                if (liCnt[2] == 2){
                                    ldecReasCp[liCnt[1]][2][1] =new Double( ldecReasCp[liCnt[1]][2][1] +ldecTsi );
                                    ldecReasCp[liCnt[1]][3][1] =new Double( ldecReasCp[liCnt[1]][3][1]+ ldecSar );
                                }   
                            }else{
                               return null;
                            }
                            if (abAdd)
                                if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu eka siswa.. yang tertanggungnya aja yang direasin
                                    break;  
                                }else
                                aiCnt=insertMSarTemp(spaj,noPolis,liBisnisId[1],liBisnisNo[1],lsKursId,ldecSar,
                                        liStsPolis,liMedis,aiCnt,dataProd.getReg_spaj_ref(), mapper);
                        }
                    }else if(liCnt[2]==2 && liBisnisId[1]==900 ){//Extra Mortalita
//                        ldecExtMort[1]=dataProd.getMspr_extra(); -> Not needed for PA BSM - Daru
//                        if(ldecExtMort[1]==null)
//                            ldecExtMort[1]=new Double(0);
//                        ldecExtMort[2]=uwDao.selectLstEmMaxLsemMaxLsemValue(paramTypeBisnis,liTypeReas,begDate);
//                        if(ldecExtMort[2]==null){
//                            err.reject("","EM Max not found Bisnis="+paramTypeBisnis+" reins="+liTypeReas+" begdate="+defaultDateFormat.format(begDate));
//                            return null;
//                        }
//                        if (ldecExtMort[1]> ldecExtMort[2]){
//                            ldecQsReas[2] = new Double(Math.max( 50, ldecQsReas[1] ) );
//                            //SET FACULTATIVE
//                            liReas = 2;
//                        }
                    }
                }   
            }
            /* Hitung Retensi & Sum-Reas */
            // reas_life
            
            if(ldecReasLf[liCnt[1]][3][1] + ldecReasLf[liCnt[1]][3][2] > 0){
                lsOldSpaj=mapper.selectMstCancelRegSpaj(spaj);
                if (lsbsId.equals(172)){
                    idSimultan=selectIdSimultan(spaj, 1, mapper);
                }else {
                    if(lsOldSpaj!=null){
                        idSimultan=selectIdSimultan(lsOldSpaj, liCnt[1]-1, mapper);
                    }else
                        idSimultan=selectIdSimultan(spaj, liCnt[1]-1, mapper);
                }
                List spajList=mapper.selectSpajSimultan(idSimultan);
                llRow[1]=spajList.size();
                for(int i=0;i<llRow[1];i++){
                    Map spajMap= (Map) spajList.get(i);
                    String spajTemp=(String) spajMap.get("REG_SPAJ");
                    if(lsOldSpaj!=null){//khusus untuk polis endors
                        if (lkuId=="02"||lkuId.equals("02")){
                            ldecLimit[1] =new Double(775000);
                            ldecLimit[2] =new Double(75000);
                        }else{
                            ldecLimit[1] =new Double("7000000000");
                            ldecLimit[2] =new Double("7000000000");
                        }
                        
                    }else{
                    ldecLimit[1] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp,mapper);//Retensi limit                 
                    ldecLimit[2] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp, mapper); //UW limit
                    }
                }
//              ldecLimit[1] = uwDao.selectGetRetensi(liBisnisId[2],1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj,err); //Retensi limit
//              ldecLimit[2] = uwDao.selectGetRetensi(liBisnisId[2],1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj,err); //UW limit
                
                if(ldecLimit[1]==null || ldecLimit[2]==null )
                    return null;
                //
                ldecReasLf[liCnt[1]][4][1] = new Double( Math.max( 0, ldecLimit[1] - ldecReasLf[liCnt[1]][1][1] - 
                                                                      ldecReasLf[liCnt[1]][1][2] ) );
                ldecReasLf[liCnt[1]][5][1] = new Double(Math.max( ldecReasLf[liCnt[1]][3][1] * ldecQsReas[2] / 100, 
                                                                   ldecReasLf[liCnt[1]][3][1] - ldecReasLf[liCnt[1]][4][1] )) ;
                ldecReasLf[liCnt[1]][4][2] = new Double(Math.max( 0, ldecReasLf[liCnt[1]][4][1] - ( ldecReasLf[liCnt[1]][3][1] -
                                                                     ldecReasLf[liCnt[1]][5][1] ) ));
                if (bisnisId.equals("169") || bisnisId.equals("212")){
                    if (ldecReasLf[liCnt[1]][4][2]>ldecReasLf[liCnt[1]][3][2]){
                        ldecReasLf[liCnt[1]][5][2]= new Double(0.0);
                    }else
                        ldecReasLf[liCnt[1]][5][2]= new Double(Math.max( 0,ldecReasLf[liCnt[1]][3][2]-ldecReasLf[liCnt[1]][4][2]));
                }else
                ldecReasLf[liCnt[1]][5][2] = new Double( Math.max( ldecReasLf[liCnt[1]][3][2] * ldecQsReas[2] / 100, 
                                                                  ldecReasLf[liCnt[1]][3][2] - ldecReasLf[liCnt[1]][4][2] ));
                
                //
                if (ldecReasLf[liCnt[1]][3][1] == 0)
                    ldecReasLf[liCnt[1]][4][1] = new Double(0);
                if (ldecReasLf[liCnt[1]][3][2] == 0) 
                    ldecReasLf[liCnt[1]][4][2] = new Double(0);

                //Yusuf (20 Jul 2011) - Bila produk ulink, ada excess of loss sampai dengan 7 M.
                //Dengan arti, walaupun melebihi retensi 750 jt, kalau sar nya belum sampai 7M masih tetap treaty (tidak facultative dan tidak perlu dibackup)
                Double limit = ldecLimit[2];
                if(mapper.selectIsUlink(spaj) > 0) {
                    if(limit < new Double("7000000000")){
                        limit = new Double("7000000000");
                    }
                }
                
                if ( (ldecReasLf[liCnt[1]][1][1] + ldecReasLf[liCnt[1]][1][2] + ldecReasLf[liCnt[1]][3][1] +
                    ldecReasLf[liCnt[1]][3][2]) > limit.doubleValue()){
                    //SET FACULTATIVE
                    liReas = 2;
                }
            }
            // reas_pa
            if (ldecReasPa[liCnt[1]][3][1] + ldecReasPa[liCnt[1]][3][2] + 
                    ldecReasPa[liCnt[1]][3][3] > 0){
                lsOldSpaj=mapper.selectMstCancelRegSpaj(spaj);
                if (lsbsId.equals(172)){
                    idSimultan=selectIdSimultan(spaj, 1, mapper);
                }else {
                    if(lsOldSpaj!=null){
                        idSimultan=selectIdSimultan(lsOldSpaj, liCnt[1]-1, mapper);
                    }else
                        idSimultan=selectIdSimultan(spaj, liCnt[1]-1, mapper);
                }
                List spajList=mapper.selectSpajSimultan(idSimultan);
                llRow[1]=spajList.size();
                for(int i=0;i<llRow[1];i++){
                    Map spajMap= (Map) spajList.get(i);
                    String spajTemp=(String) spajMap.get("REG_SPAJ");
                    Integer lstb_Id=((BigDecimal) spajMap.get("LSTB_ID")).intValue();
                    //limit > 0 dan resiko awal ssp = 0
                    if ( (ldecQsReas[1] > 0) && (ldecReasPa[liCnt[1]][3][1] == 0) ){
                        if(lsOldSpaj!=null||lstb_Id==2){//khusus untuk polis endors
                            if (lkuId=="02"){
                                ldecLimit[1] =new Double(75000);
                                ldecLimit[2] =new Double(75000);
                            }else{
                                ldecLimit[1] =new Double(750000000);
                                ldecLimit[2] =new Double(750000000);
                            }
                            
                        }else
                        ldecLimit[1] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 3,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp, mapper); 
                        ldecLimit[2] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 3,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp, mapper); 
                    }else{
                        if(lsOldSpaj!=null){//khusus untuk polis endors
                            if (lkuId=="02"){
                                ldecLimit[1] =new Double(75000);
                                ldecLimit[2] =new Double(75000);
                            }else{
                                ldecLimit[1] =new Double(750000000);
                                ldecLimit[2] =new Double(750000000);
                            }
                            
                        }else
                        ldecLimit[1] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp, mapper);    
                        ldecLimit[2] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp, mapper); 
                    
                    }
                }
//              String prodSave=props.getProperty("product.plan_power_save");
//              prodSave.contains(bisnisId);
//              if (prodSave.contains(bisnisId)&& aiCnt>1){
//                  for (int d=0;d<spajList.size()-1;d++){
//                      Map spajMap= (Map) spajList.get(d);
//                      String spajTemp=(String) spajMap.get("REG_SPAJ");
//                      ldecLimitTemp[1] = uwDao.selectGetRetensi(liBisnisId[2],1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp,err); 
//                      ldecLimitTemp[2] = uwDao.selectGetRetensi(liBisnisId[2],1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp,err); 
//                  }
//                  ldecLimitTemp[3]=new Double(Math.max(0,ldecLimit[2]-ldecLimitTemp[2]));
//              }
                if(ldecLimit[1]==null || ldecLimit[2]==null )
                    return null;
            
                //khsusus power save itu < retensi non reas ato qouta reas=0
//              String prodSave=props.getProperty("product.plan_powero_save");
                String tempId=f3.format(liBisnisId[1]);
//              if(prodSave.indexOf(tempId)>0 && ldecTsi >ldecLimit[1]) {
//                  ldecQsReas[2]=new Double(50);
//              }
                
                // [x][][] = inssured ; 2=policy-holder
                // [][x][] = total-sar-simultan ; 2=tsi ; 3=resiko-awal ; 4=retensi ; 5=sum-reas
                
                //retensi = limit - total_sar_simultan
                if((isProductSyariah(liBisnisId[2].toString(), liBisnisNo[2].toString(), mapper))){ // retensi untuk product syariah... retensi rider nya sama retensi dengan retensi main product
                    ldecReasPa[liCnt[1]][4][1]= new Double(500000000);
                }else
                ldecReasPa[liCnt[1]][4][1] = new Double(Math.max(0, ldecLimit[1] - ldecReasPa[liCnt[1]][1][1] - ldecReasPa[liCnt[1]][1][2] - ldecReasPa[liCnt[1]][1][3] ));
                //sum_reas = sar * qs / 100
                //sum_reas = resiko_awal - retensi
                ldecReasPa[liCnt[1]][5][1] = new Double(Math.max( 0, 
                                                                  ldecReasPa[liCnt[1]][3][1] - ldecReasPa[liCnt[1]][4][1] ));
                
                //

//              logger.info(1);
//              TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                
                if (aiCnt>1){
                    //retensi = limit - total_sar_simultan
                    //YUSUF
//                  if (ldecLimit[1]>(ldecReasPa[liCnt[1]][1][1])){
//                      ldecLimit[1]= (ldecLimit[1]-(ldecReasPa[liCnt[1]][1][1]))+ (ldecReasPa[liCnt[1]][1][1]);
//                      ldecReasPa[liCnt[1]][4][1]= new Double(Math.max(0, ldecLimit[1]-ldecReasPa[liCnt[1]][3][2]));
//                  }else
                        ldecReasPa[liCnt[1]][4][1]= new Double(Math.max(0, ldecLimit[1]-(ldecReasPa[liCnt[1]][1][1])));
                        //ldecReasPa[liCnt[1]][4][1] = new Double(Math.max(0, ldecLimit[1] - ldecReasPa[liCnt[1]][1][1] - ldecReasPa[liCnt[1]][1][2] - ldecReasPa[liCnt[1]][1][3] ));
                    
                    //
//                  logger.info(2);
//                  TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                    //apabila retensi > resiko awal
                    if (ldecReasPa[liCnt[1]][4][1]>ldecReasPa[liCnt[1]][3][1]){
                        //reas = 0 dan retensi = limit - total_sar_simultan
                        ldecReasPa[liCnt[1]][5][1] =new Double(0);
                        ldecReasPa[liCnt[1]][4][1] = new Double(Math.max( 0, ldecLimit[1] - ldecReasPa[liCnt[1]][1][1] - 
                                 ldecReasPa[liCnt[1]][1][2] - ldecReasPa[liCnt[1]][1][3] ));
//                      ldecReasPa[liCnt[1]][4][1]= new Double(Math.max(0, ldecLimit[1]-(300000000)));
                        //
//                      logger.info(3);
//                      TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                    }else{
                        //apabila total_sar_simultan/2 > limit
                        if ((ldecReasPa[liCnt[1]][1][1]*0.5)>ldecLimit[1]){
                            //retensi = 0 dan reas = resiko awal - retensi
                            ldecReasPa[liCnt[1]][4][1]=new Double(0);
                            ldecReasPa[liCnt[1]][5][1] = new Double(ldecReasPa[liCnt[1]][3][1]-ldecReasPa[liCnt[1]][4][1]);
                            //
//                          logger.info(4);
//                          TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                        }else{
                            //reas = resiko awal - retensi
                            ldecReasPa[liCnt[1]][5][1] = new Double(ldecReasPa[liCnt[1]][3][1]-ldecReasPa[liCnt[1]][4][1]);
                            //
//                          logger.info(5);
//                          TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                        }
                    }
                }
                ldecReasPa[liCnt[1]][4][2] = new Double(Math.max( 0, ldecReasPa[liCnt[1]][4][1] - ( ldecReasPa[liCnt[1]][3][1] - 
                                                                     ldecReasPa[liCnt[1]][5][1] ) ));
                ldecReasPa[liCnt[1]][5][2] = new Double(Math.max( ldecReasPa[liCnt[1]][3][2] * ldecQsReas[2] / 100, 
                                                                  ldecReasPa[liCnt[1]][3][2] - ldecReasPa[liCnt[1]][4][2] ));
                ldecReasPa[liCnt[1]][4][3] = new Double(Math.max( 0, ldecReasPa[liCnt[1]][4][2] - 
                                                                   ( ldecReasPa[liCnt[1]][3][2] - ldecReasPa[liCnt[1]][5][2] ) ));
                ldecReasPa[liCnt[1]][5][3] = new Double(Math.max( ldecReasPa[liCnt[1]][3][3] * ldecQsReas[2] / 100, 
                                                                  ldecReasPa[liCnt[1]][3][3] - ldecReasPa[liCnt[1]][4][3] ));
                //testing 
//              if (Integer.valueOf(tempId)==143 || Integer.valueOf(tempId)==144){
//                  if (ldecReasPa[liCnt[1]][3][1]>=ldecLimit[1]){
//                      ldecReasPa[liCnt[1]][4][2] = new Double(Math.max( 0, ldecReasPa[liCnt[1]][4][1] - ( ldecReasPa[liCnt[1]][3][1] - 
//                               ldecReasPa[liCnt[1]][5][1] ) *0.5));
//                      if (ldecReasPa[liCnt[1]][4][1]>ldecReasPa[liCnt[1]][4][2]){
//                          ldecReasPa[liCnt[1]][4][1] = new Double(Math.max( 0, ldecReasPa[liCnt[1]][4][1] - ( ldecReasPa[liCnt[1]][3][1] - 
//                                   ldecReasPa[liCnt[1]][5][1] ) *0.5));
//                          ldecReasPa[liCnt[1]][5][1]= new Double(ldecReasPa[liCnt[1]][4][1]-ldecReasPa[liCnt[1]][4][2]);
//                      }else{
//                          ldecReasPa[liCnt[1]][5][1]= new Double(0);
//                      }
//                      //
//                      logger.info(6);
//                      TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
//                  }else {
//                      ldecReasPa[liCnt[1]][4][2] = new Double(ldecLimit[1]-ldecReasPa[liCnt[1]][3][1]);
//                      if (ldecReasPa[liCnt[1]][4][1]>ldecReasPa[liCnt[1]][4][2]){
//                          ldecReasPa[liCnt[1]][5][1]= new Double(ldecReasPa[liCnt[1]][4][1]-(ldecLimit[1]-ldecReasPa[liCnt[1]][3][1]));
//                          ldecReasPa[liCnt[1]][4][1]=new Double(ldecReasPa[liCnt[1]][4][2]);
//                      }else{
//                          ldecReasPa[liCnt[1]][5][1]= new Double(0);
//                      }
//                      //
//                      logger.info(7);
//                      TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
//                  }
//              }
                //
//              logger.info(8);
//              TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                
                if (ldecReasPa[liCnt[1]][3][1] == 0)
                    ldecReasPa[liCnt[1]][4][1] = new Double(0);
                if (ldecReasPa[liCnt[1]][3][2] == 0)
                    ldecReasPa[liCnt[1]][4][2] = new Double(0);
                if (ldecReasPa[liCnt[1]][3][3] == 0)
                    ldecReasPa[liCnt[1]][4][3] = new Double(0);
                if ( (ldecReasPa[liCnt[1]][1][1] + ldecReasPa[liCnt[1]][1][2] 
                        + ldecReasPa[liCnt[1]][1][3] + ldecReasPa[liCnt[1]][3][1]
                        + ldecReasPa[liCnt[1]][3][2] + ldecReasPa[liCnt[1]][3][3])
                        > ldecLimit[2]){
                    String prodSave="086, 094, 123, 124, 142, 143, 144, 155, 158,40,130, 175, 176, 177, 207";
                    Integer pos=prodSave.indexOf(bisnisId);
                    if(pos>0 && dataReas.getUmurTt()<69){
                        liReas = 0;
                    }
                    
                }

                //
//              logger.info(9);
//              TestUtils.printDetailReas(ldecReasLf, ldecReasPa, ldecReasPk, ldecReasSs, ldecReasCp);
                
            }
            // reas_pk
            if( ldecReasPk[liCnt[1]][3][1] + ldecReasPk[liCnt[1]][3][2] > 0){
                
                    if (ldecQsReas[1] > 0){
                        ldecLimit[1] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 4,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj, mapper); 
                        ldecLimit[2] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 4,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj, mapper); 
                    }else{
                        ldecLimit[1] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 2,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj, mapper); 
                        ldecLimit[2] = selectGetRetensi(liBisnisId[2], liBisnisNo[2], 2,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj, mapper); 
                    
                }   
                    if(ldecLimit[1]==null || ldecLimit[2]==null )
                    return null;
                //
                ldecReasPk[liCnt[1]][4][1] = new Double(Math.max( 0, ldecLimit[1] - ldecReasPk[liCnt[1]][1][1] 
                                                                     - ldecReasPk[liCnt[1]][1][2] ));
                ldecReasPk[liCnt[1]][5][1] = new Double(Math.max( (ldecReasPk[liCnt[1]][3][1] * ldecQsReas[2] / 100), 
                                                                   (ldecReasPk[liCnt[1]][3][1] - ldecReasPk[liCnt[1]][4][1]) ));
                ldecReasPk[liCnt[1]][4][2] = new Double(Math.max( 0, ldecReasPk[liCnt[1]][4][1] - 
                                                                    ( ldecReasPk[liCnt[1]][3][1] - ldecReasPk[liCnt[1]][5][1] ) ));
                ldecReasPk[liCnt[1]][5][2] = new Double(Math.max( (ldecReasPk[liCnt[1]][3][2] * ldecQsReas[2] / 100),//SUPER SEHAT
                                                                   (ldecReasPk[liCnt[1]][3][2] - ldecReasPk[liCnt[1]][4][2]) ));
                //
                if (ldecReasPk[liCnt[1]][3][1] == 0)
                    ldecReasPk[liCnt[1]][4][1] = new Double(0);
                if (ldecReasPk[liCnt[1]][3][2] == 0)
                    ldecReasPk[liCnt[1]][4][2] = new Double(0);
                
                //Yusuf (20 Jul 2011) - Bila produk ulink, ada excess of loss sampai dengan 7 M.
                //Dengan arti, walaupun melebihi retensi 750 jt, kalau sar nya belum sampai 7M masih tetap treaty (tidak facultative dan tidak perlu dibackup)
                Double limit = ldecLimit[2];
                if(mapper.selectIsUlink(spaj) > 0) {
                    limit = new Double("7000000000");
                }
                
                if ( (ldecReasPk[liCnt[1]][1][1] + ldecReasPk[liCnt[1]][1][2]
                        + ldecReasPk[liCnt[1]][3][1] + ldecReasPk[liCnt[1]][3][2]) 
                        > limit.doubleValue()){
                        //SET FACULTATIVE
                        liReas = 2;
                }
                
            }
            //806, 807 = cash plan, tpd
            if (ldecReasCp[liCnt[1]][2][1] > 0){
                ldecReasCp[liCnt[1]][5][1] = new Double( ldecReasCp[liCnt[1]][3][1] * ldecQsReas[2] / 100);
                ldecReasCp[liCnt[1]][4][1] = ldecReasCp[liCnt[1]][5][1];
            }
            // simas sehat harian
            if (ldecReasSs[liCnt[1]][3][1] > 0){
                ldecReasSs[liCnt[1]][5][1] = new Double(ldecReasSs[liCnt[1]][3][1] * ldecQsReas[2] / 100);
                ldecReasSs[liCnt[1]][4][1] = new Double(ldecReasSs[liCnt[1]][3][1] - ldecReasSs[liCnt[1]][5][1] );
            }
            ldecTotalReas[liCnt[1]] = new Double(ldecReasLf[liCnt[1]][5][1] + ldecReasLf[liCnt[1]][5][2] 
                                        + ldecReasPa[liCnt[1]][5][1] + ldecReasPa[liCnt[1]][5][2]
                                        + ldecReasPa[liCnt[1]][5][3] + ldecReasPk[liCnt[1]][5][1] 
                                        + ldecReasPk[liCnt[1]][5][2] + ldecReasSs[liCnt[1]][5][1] 
                                        + ldecReasCp[liCnt[1]][5][1]); 
            if(ldecTotalReas[liCnt[1]] > 0 )
                liReas = Math.max( 1, liReas);
        }

    /*
        
        
        //Dian B

        idSimultan=uwDao.selectIdSimultan(spaj, 0);
        List spajListPowerSave=uwDao.selectReasSimultanPowerSave(idSimultan);
        
        
        //tarik retensi powersave terakhir (tapi yang sebelumnya)
        for (int d=0;d<spajListPowerSave.size()-1;d++){
            Map spajMap= (Map) spajListPowerSave.get(d);
            String spajTemp=(String) spajMap.get("REG_SPAJ");
            Double sarTemp=((BigDecimal) spajMap.get("SAR_SSP")).doubleValue();
            Double retensiTemp=((BigDecimal) spajMap.get("RETENSI_SSP")).doubleValue();
            Double reastemp =((BigDecimal) spajMap.get("REAS_SSP")).doubleValue();
//          
            List prod = uwDao.selectBisnisId(spajTemp);
            for (int i=0;i<prod.size();i++){
                Map lsbsMap= (Map)prod.get(i);
                String lsbs = f3.format((Integer) lsbsMap.get("LSBS_ID"));
                if(prodSave.contains(lsbs)){
                    ldecLimitTemp[1]=new Double(0);
                    ldecLimitTemp[1] =  uwDao.selectGetRetensi(liBisnisId[2],1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp,err); 
                    ldecLimitTemp[2] =  uwDao.selectGetRetensi(liBisnisId[2],1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spajTemp,err);
                }
            }
        }
        
        //kurangi retensi terbaru dgn retensi yg terakhir
        if (prodSave.contains(bisnisId)&& aiCnt>1){
//          ldecLimitTemp[3]=new Double(Math.max(0,ldecLimit[2]-ldecLimitTemp[1]));
            ldecLimitTemp[1] =  uwDao.selectGetRetensi(liBisnisId[2],1,1,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj,err); 
            ldecLimitTemp[2] =  uwDao.selectGetRetensi(liBisnisId[2],1,2,liMedis,lkuId,begDate,liAge[liCnt[1]],spaj,err);
            ldecReasPa[liCnt[1]-1][4][1] = ldecLimitTemp[1];
            ldecReasPa[liCnt[1]-1][5][1] = new Double(Math.max( 0,ldecReasPa[liCnt[1]-1][3][1]-ldecLimitTemp[1]));

        }
*/
        if(liReasClient[2] == 3){
            if(ldecTsi==null)
                return null;
            Double tempTsi1=selectGetTsiReas(spaj,insuredNo,0,liBisnisId[2],liBisnisNo[2],
                            liBisnisId[2],liBisnisNo[2],lsKursId,ldecUp,mapper);
            Double tempTsi2=selectGetTsiReas(spaj,insuredNo,1,liBisnisId[2],liBisnisNo[2],
                            liBisnisId[2],liBisnisNo[2],lsKursId,ldecUp, mapper);
            if(tempTsi1==null || tempTsi2==null)
                return null;
            ldecReasComp=new Double(tempTsi1/tempTsi2);
            ldecReasLf[1][5][2] = new Double(Math.max( ldecReasLf[1][5][2], ldecReasLf[2][5][2] * ldecReasComp ));
            ldecReasLf[2][5][2] = new Double(ldecReasLf[1][5][2] / ldecReasComp);
        }
        //untuk provestara jika error proses REAS, dilakukan perubahan dari for(liCnt[1]=1;liCnt[1]<=2; menjadi for(liCnt[1]=1;liCnt[1]<2; (Ridhaal - warning )
        if(abAdd){
            for(liCnt[1]=1;liCnt[1]<=2;liCnt[1]++){
                if( (liReasClient[2] == liCnt[1]) || (liReasClient[2] > 2 ) ){
                    if ( (liReasClient[2] > 2 ) && (liCnt[1] == 2 ) &&
                            (msteInsured.equals(mspoPolicyHolder))) 
                        return null;
                    ReasTemp insReas=new ReasTemp();
                    //
                    
                    insReas.setReg_spaj(spaj);
                    if (bisnisId.equals(172)){
                        insReas.setPemegang(liCnt[1]);
                    }else{
                        insReas.setPemegang(liCnt[1]-1);
                    }
                    insReas.setMste_reas(liReas);
                    insReas.setExtra_mortality(ldecExtMort[1]);
                    insReas.setNil_kurs(ldecKurs.intValue());
                    insReas.setLku_id(lkuId);
                    //
                    insReas.setSimultan_tr_rd(ldecReasLf[liCnt[1]][1][1]);
                    insReas.setTsi_tr_rd(ldecReasLf[liCnt[1]][2][1]);
                    insReas.setSar_tr_rd(ldecReasLf[liCnt[1]][3][1]);
                    insReas.setRetensi_tr_rd(ldecReasLf[liCnt[1]][4][1]);
                    insReas.setReas_tr_rd(ldecReasLf[liCnt[1]][5][1] );
                    //
                    insReas.setSimultan_life(ldecReasLf[liCnt[1]][1][2]);
                    insReas.setTsi_life(ldecReasLf[liCnt[1]][2][2]);
                    insReas.setSar_life(ldecReasLf[liCnt[1]][3][2]);
                    insReas.setRetensi_life(ldecReasLf[liCnt[1]][4][2]);
                    insReas.setReas_life(ldecReasLf[liCnt[1]][5][2]);
                    //
                    //untuk produk power save perhitungan retensi balik ke awal lagi 
                    //jikalau simultan polis yang ambil retensi status acceptnya sudah CCV, maturity, dll
                    
                    //PowerSave Dian 
                    //jikalau sudah ambil rider yang berjenis PA akan mengurangi retensi power save yang dengan di ambil
                    if(isProductPowerSave(bisnisId)){
//                      String simultaId=(String)uwDao.selectMstSimultaneousIdSimultan(dataReas.getMsteInsured());
                        lsOldSpaj=mapper.selectMstCancelRegSpaj(spaj);
                        if (lsbsId.equals(172)){
                            idSimultan=selectIdSimultan(spaj, 1, mapper);
                        }else {
                            if(lsOldSpaj!=null){
                                idSimultan=selectIdSimultan(lsOldSpaj, liCnt[1]-1, mapper);
                            }else
                                idSimultan=selectIdSimultan(spaj, liCnt[1]-1, mapper);
                        }
                        List listSarProdSave= mapper.selectListOldProdSave(idSimultan);
                        Integer rlistSarProdSave= listSarProdSave.size();
                        
                        for(int r=0; r<rlistSarProdSave-1;r++){
                            Map listSarOldMap= (Map)listSarProdSave.get(r);
                            Double sarSimultan=((BigDecimal)listSarOldMap.get("SAR")).doubleValue();
                            ldecReasPa[liCnt[1]][4][1]= new Double(Math.max(0 , ldecLimit[1] - sarSimultan ));
                            if (ldecReasPa[liCnt[1]][1][1]> ldecLimit[1]){
                                ldecReasPa[liCnt[1]][5][1]= new Double(Math.max(0,ldecReasPa[liCnt[1]][3][1]));//reas
                                ldecReasPa[liCnt[1]][4][1]=0.0; //retensi
                                liReas=1;
                                insReas.setMste_reas(liReas);
                            }else {
                                ldecReasPa[liCnt[1]][4][1]= new Double(Math.max(0,ldecLimit[1]-ldecReasPa[liCnt[1]][1][1]));
                                ldecReasPa[liCnt[1]][5][1]=new Double(Math.max(0,ldecReasPa[liCnt[1]][3][1]-ldecReasPa[liCnt[1]][4][1]));
                                liReas=1;
                                insReas.setMste_reas(liReas);
                            }
                        }
                        //lopping id yang simultan di table sar_temp
                        //trus hitung retensi nya.
                        //uwDao.selectMSarTempGroup(spaj, groupReas)
/*
                        SELECT b.*
                          FROM eka.mst_policy a,
                               eka.m_sar_temp b,
                               eka.lst_bisnis c,
                               eka.lst_policy_status d
                         WHERE a.mspo_policy_no = b.no_polis
                           AND b.bisnis_id = c.lsbs_id
                           AND a.lssp_id = d.lssp_id
                           AND c.lsgr_id = 2
                           AND d.lms_id IN (1, 2, 6)
                           AND b.reg_spaj = '09200800720'
                        UNION
                        SELECT b.*
                          FROM eka.mst_policy a,
                               eka.m_sar_temp b,
                               eka.lst_bisnis c,
                               eka.lst_policy_status d
                         WHERE a.reg_spaj= b.reg_spaj 
                           AND b.bisnis_id = c.lsbs_id
                           AND a.lssp_id = d.lssp_id
                           AND c.lsgr_id = 2
                           AND d.lms_id IN (1, 2, 6)
                           AND b.no_polis IS NULL
                           AND b.reg_spaj = '09200800720'*/
                    }
                    
                    insReas.setSimultan_ssp(ldecReasPa[liCnt[1]][1][1]);
                    insReas.setTsi_ssp(ldecReasPa[liCnt[1]][2][1]);
                    insReas.setSar_ssp(ldecReasPa[liCnt[1]][3][1]); 
                    insReas.setRetensi_ssp(ldecReasPa[liCnt[1]][4][1]);
                    insReas.setReas_ssp(ldecReasPa[liCnt[1]][5][1]);
                    if (bisnisId.equals("045")|| bisnisId=="45"||bisnisId.equals("130")|| bisnisId=="130"){
                        if (insReas.getRetensi_ssp()>insReas.getSar_ssp()){
                            insReas.setReas_ssp(0.0);
                            liReas=0;
                            insReas.setMste_reas(liReas);
                        }else{
                            Double reas_sspTemp=new Double(Math.max( 0,insReas.getSar_ssp()-ldecLimit[1]));
//                          Double reas_sspTemp=insReas.getSar_ssp() * 0.5;//kalo lebih dari retensi langsung quateshared.....
//                          Double reas_sspTemp=(insReas.getSar_ssp()-insReas.getRetensi_ssp());
                            liReas=1;
                            insReas.setMste_reas(liReas);
                            insReas.setReas_ssp(reas_sspTemp);
                        }
                    }
                    insReas.setSimultan_pa_in(ldecReasPa[liCnt[1]][1][2]);
                    insReas.setTsi_pa_in(ldecReasPa[liCnt[1]][2][2]);
                    insReas.setSar_pa_in(ldecReasPa[liCnt[1]][3][2]);
                    insReas.setRetensi_pa_in(ldecReasPa[liCnt[1]][4][2]);
                    insReas.setReas_pa_in(ldecReasPa[liCnt[1]][5][2]);
                    //
                    insReas.setSimultan_pa_rd(ldecReasPa[liCnt[1]][1][3]);
                    insReas.setTsi_pa_rd(ldecReasPa[liCnt[1]][2][3]);
                    insReas.setSar_pa_rd(ldecReasPa[liCnt[1]][3][3]);
                    insReas.setRetensi_pa_rd(ldecReasPa[liCnt[1]][4][3]);
                    insReas.setReas_pa_rd(ldecReasPa[liCnt[1]][5][3]);
                    //
                    insReas.setSimultan_pk_in(ldecReasPk[liCnt[1]][1][1]);
                    insReas.setTsi_pk_in(ldecReasPk[liCnt[1]][2][1]);
                    insReas.setSar_pk_in(ldecReasPk[liCnt[1]][3][1]);
                    insReas.setRetensi_pk_in(ldecReasPk[liCnt[1]][4][1]);
                    insReas.setReas_pk_in(ldecReasPk[liCnt[1]][5][1]);
                    //
                    insReas.setSimultan_pk_rd(ldecReasPk[liCnt[1]][1][2]);
                    insReas.setTsi_pk_rd(ldecReasPk[liCnt[1]][2][2]);
                    insReas.setSar_pk_rd(ldecReasPk[liCnt[1]][3][2]);
                    insReas.setRetensi_pk_rd(ldecReasPk[liCnt[1]][4][2]);
                    insReas.setReas_pk_rd(ldecReasPk[liCnt[1]][5][2]);
                    //
                    insReas.setSimultan_ssh(ldecReasSs[liCnt[1]][1][1]);
                    insReas.setTsi_ssh(ldecReasSs[liCnt[1]][2][1]);
                    insReas.setSar_ssh(ldecReasSs[liCnt[1]][3][1]);
                    insReas.setRetensi_ssh(ldecReasSs[liCnt[1]][4][1]);
                    insReas.setReas_ssh(ldecReasSs[liCnt[1]][5][1]);
                    insReas.setTipe(0);
                    if (bisnisId.equals("172")&&(liCnt[1]-1!=0)){//khusus untu EKA SISWA EMAS.. yang tertanggungnya aja yang direasin
                        break;  
                    }else
                    mapper.insertMReasTemp(insReas);
                    
                    if (lsbsId.equals(214)){
                        liCnt[1]=3; // di set 3 biar lebih besar 2 biar untuk provestara tidak terjadi pengulangan for yg menyebabkan error - Ridhaal
                    }
                }
            }
            //untuk reas cth kasus.
            //mis ada 3 polis power save maka
            //polis 1 tsi=700 jt retensi=500 maka reas=350jt ekalife=350jt
            //polis 2 tsi 200 jt retensi=500 maka reas=100 jt ekalife 100 jt
            //polis 3 tsi 600 jt retensi=500-350+100(ekalife)
            //khsusus power save itu <500 jt non reas ato qouta reas=0
            
            //sar = 100% up
            //reas =quota share 50/50
            //reas = retensi = 50% up
            //ldecUp=dataProd.getMspr_tsi();
            //TODO untuk rider new setelah insert ke reas temp baru inset ke reas temp new berdasa
            //rkan spaj jadi nilai retensi bisa surplus.
            //ldecSar=ldecTsi;

            Double reas=0.0;
            boolean getRetenPa = false;
            //jika retensi pa belum ada coz produk utama tersebut bukan pa 
            if(ldecReasPa[1][4][1]==0 && ldecReasPa[1][5][2]==0)
                getRetenPa=true;
            
                
            Double retensiPa=new Double(Math.max( 0,ldecReasPa[1][4][1]-ldecReasPa[1][3][1]));
            Double retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
            Double retensiTPD=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
            
            Double retensiH=new Double(Math.max( 0,ldecReasSs[1][4][1]));
            Integer counterL=0;
            Integer counterT=0;
            Integer counterTL=0;
            Integer counterH=0;
            //HCP banyak peserta..//TODO untuk rider link lebih baik query ulang aja. trus di looping.
            //if(flagRider==0){
            //khusus link >= 810 and <= 819
//            List lsRider=uwDao.selectMstProductInsuredRiderTambahanSar(spaj, 1, 1); -> Not needed for PA BSM - Daru
//            //--> cari tau dulu main product termasuk group apa??
////          just for DIAN
//            List groupReas=mapper.selectGroupReas(bisnisId);
//            
//            Boolean is822udah=false;
//            for(int i=0;i<lsRider.size();i++){
//                if(is822udah)continue;// : biar ga masuk 2 kali rider swine flue by Bertho 26092009 NOTE klo mau disable tinggal comment bagian ini aja
//                liReasRider=0;  
//                Product prod=(Product)lsRider.get(i);
//                if(prod.getLsbs_id()==810){//PA
//                    Double retensiAwal=0.0; 
////                  retensiAwal=0.0;
//                    if(getRetenPa){
//                        if((products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString()))){ // retensi untuk product syariah... retensi rider nya sama retensi dengan retensi main product
//                        retensiPa= ldecReasLf[1][4][2];
//                        }else
//                        retensiPa=uwDao.selectGetRetensi(prod.getLsbs_id(), prod.getLsdbs_number(), 1, 1, liMedis, prod.getLku_id(), begDate, liAge[1], spaj,err);
//                    }
//                    //cek di sar_temp dimana groupnya pa lalu bandingkan dengan retensi
////                  reas=prod.getSar()-retensiLife; //yang lama dari ferry
//                    retensiAwal=retensiPa;
//                    Double sarTemp=prod.getSar();
////                  retensiPa=retensiPa-prod.getSar();
////                  reas=retensiPa;
//                    if(sarTemp<=retensiAwal) //dian :prod rider sar<retensi reas=nol
////                      retensiPa=0.0;
//                        reas=0.0;
//                    else{
//                        reas= sarTemp-retensiPa;//prod rider sar>retensi
////                      reas=retensiPa; //rider itu di reas
//                    }
//                    if(reas<=0)//rider tersebut di reas kan.
//                        reas=0.0;
//                    else{
//                        reasRider=true;
//                        liReasRider=1;  
//                    }
//                    /*
//                    //kalo perlu aja di buka
//                    Integer contReasTemp= uwDao.selectCountReasTempNew(spaj);
//                    if (contReasTemp>0){
//                        
//                    }else
//                    */
//                    insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                            liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas); 
//                }else if(prod.getLsbs_id()!=802 && prod.getLsbs_id()!= 811 && prod.getLsbs_id()!= 819&&prod.getLsbs_id()!= 820&&prod.getLsbs_id()!= 821 ) {//sistem reas surplus (life) 100%UP
//                    Double retensiAwal=0.0;
//                    
//                    if (prod.getLsbs_id()== 813 ||prod.getLsbs_id().equals(813)||prod.getLsbs_id()== 822 ||prod.getLsbs_id().equals(822)||prod.getLsbs_id()== 803 ||prod.getLsbs_id().equals(803)||prod.getLsbs_id()== 816 ||prod.getLsbs_id().equals(816)||prod.getLsbs_id()== 818 ||prod.getLsbs_id().equals(818)||prod.getLsbs_id()== 817 ||prod.getLsbs_id().equals(817)){                  
//                        
//                        if(prod.getLsbs_id()==822)is822udah=true;// TANYA KE DIAN APAKAH  rider yang sama dengan polis yang berbeda bisa masuk ke eka.mst_reas_temp_new
//                        
//                        idSimultan=uwDao.selectIdSimultan(spaj, 0);
//                        Integer rowke;
//                        //SAROLD
//                        Integer row=lsRider.size();
//                        List listSarOld= uwDao.listOldSar(idSimultan);
//                        Integer rlistSarOld= listSarOld.size();
//                        
//                        for(int r=0; r<rlistSarOld;r++){
//                        Map listSarOldMap= (Map)listSarOld.get(r);
//                        rowke=((BigDecimal)listSarOldMap.get("ROWKE")).intValue();
//                        counterL=counterL+1;
////                      if ((products.syariah(liBisnisId[2].toString()))){
////                              retensiLife= ldecReasLf[1][4][2];
////                              retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
////                              retensiAwal=retensiLife;
////                              break;
////                              if (){
////                                  Double sarOld= uwDao.selectOldSar(idSimultan,counterL-rowke);   
////                                  retensiLife=new Double(Math.max( 0,retensiLife-sarOld));
////                              }
////                      }else {
//                            if(ldecLimit[1]==null){
//                                ldecLimit[1]=0.0;
//                            }
//                            if (reas<ldecLimit[1]){
//                                if ((counterL-rowke==0) &&products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){
//                                    retensiLife= new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                                    reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                    retensiAwal=retensiLife;
////                                  retensiLife=new Double(Math.max( 0,retensiLife-ldecReasLf[1][3][2]));
//                                    break;
//                                }else if ((counterL-rowke==0)||retensiLife==ldecLimit[1]){
////                                  retensiLife=ldecLimit[1];
//                                    retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                                    retensiAwal=retensiLife;
//                                    if (retensiAwal==0.0)
//                                        reas=prod.getSar();
//                                    break;
//                                }else{
//                                    Double sarOld= uwDao.selectOldSar(idSimultan,counterL-rowke);   
//                                    retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]-sarOld));
//                                    reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                    retensiAwal=retensiLife;
//                                    break;
//                                }
//                            }else{
//                                retensiLife=0.0;
////                              reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
////                              retensiAwal=retensiLife;
//                                break;}
//                        }
////                      if(retensiLife<0){
////                          retensiLife=0.0;}
////                      if(reas<0)//rider tersebut di reas kan.
////                          reas=0.0;
////                      else{
////                          reasRider=true;
////                          liReasRider=1;  
////                      }
////                      }
//                    }
//                    if(prod.getLsbs_id()== 814 ||prod.getLsbs_id().equals(814)||prod.getLsbs_id()== 812 ||prod.getLsbs_id().equals(812)||prod.getLsbs_id()== 807 ||prod.getLsbs_id().equals(807)){//TPD Dipisahkan dari life
//                        idSimultan=uwDao.selectIdSimultan(spaj, 0);
//                        //SAROLD
//                            Integer row=lsRider.size();
//                            List listOldTpd=uwDao.listOldSarTPD(idSimultan);
//                            Integer rlistOldTpd=listOldTpd.size();
//                            for(int r=0; r<rlistOldTpd;r++){
//                                Map listOldTpdMap= (Map)listOldTpd.get(r);
//                                Integer rowke=((BigDecimal)listOldTpdMap.get("ROWKE")).intValue();
//                                counterT=counterL+1;
//                                if ((counterT-rowke==0) &&products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){
//                                    retensiTPD= ldecReasLf[1][4][2];
//                                    reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                    retensiAwal=retensiTPD;
////                                  retensiLife=new Double(Math.max( 0,retensiLife-ldecReasLf[1][3][2]));
//                                    break;
//                                }else if((counterT-rowke!=0) &&products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){
//                                    retensiTPD= ldecReasLf[1][4][2];
//                                    reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                    retensiAwal=retensiTPD;
//                                    break;
//                                }else if ((counterT-rowke==0)||retensiLife==ldecLimit[1]){
////                                  retensiTPD=new Double(Math.max( 0,ldecLimit[1]-ldecReasLf[1][3][2]));
//                                    retensiTPD=ldecLimit[1];
//                                    retensiAwal=retensiTPD;
//                                    break;
//                                }else{
//                                    Double sarOld= uwDao.selectOldSarTPD(idSimultan,counterT-rowke);    
//                                    if (sarOld==null){
//                                        retensiTPD=retensiTPD;
//                                    }else{
//                                    retensiTPD=new Double(Math.max( 0,retensiTPD-sarOld));
//                                    }
//                                    reas=new Double(Math.max( 0,prod.getSar()-retensiTPD));
//                                    retensiAwal=retensiTPD;
//                                    break;
//                                }
//                            }
//                        if(reas<0)//rider tersebut di reas kan.
//                            reas=0.0;
//                        else{
//                            reasRider=true;
//                            liReasRider=1;  
//                        }
//                    }
//                    if(prod.getLsbs_id()== 815 ||prod.getLsbs_id().equals(815)){
//                        if (prod.getLsdbs_number()==1||prod.getLsdbs_number()==6){//Dian :ikut di dalam retensi life
//                            idSimultan=uwDao.selectIdSimultan(spaj, 0);
//                            //SAROLD
//                            Double sarOld=0.0;
////                          Integer row=lsRider.size();
////                          sarOld= sarOld= uwDao.selectOldSar(idSimultan, (row-(i-1)));        
////                          Integer row=lsRider.size();
//                            List listSarOld= uwDao.listOldSar(idSimultan);
//                            Integer rlistSarOld= listSarOld.size();
//                            
//                            for(int r=0; r<rlistSarOld;r++){
//                            Map listSarOldMap= (Map)listSarOld.get(r);
//                            Integer rowke=((BigDecimal)listSarOldMap.get("ROWKE")).intValue();
//                            counterL=counterL+1;
//                            if ((products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString()))){
//                                    retensiLife= ldecReasLf[1][4][2];
//                                    retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                                    retensiAwal=retensiLife;
//                                    break;
//                            }else {
//                                if (reas<ldecLimit[1]){
//                                    if ((counterL-rowke==0) &&products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){
//                                        retensiLife= ldecReasLf[1][4][2];
//                                        reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
////                                      retensiAwal=retensiLife;
////                                      retensiLife=new Double(Math.max( 0,retensiLife-ldecReasLf[1][3][2]));
//                                        break;
//                                    }else if ((counterL-rowke==0)||retensiLife==ldecLimit[1]){
////                                      retensiLife=ldecLimit[1];
//                                        retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                                        retensiAwal=retensiLife;
//                                        break;
//                                    }else{
//                                        
//                                        sarOld= uwDao.selectOldSar(idSimultan,counterL-rowke);  
//                                        retensiLife=new Double(Math.max( 0,retensiLife-sarOld));
//                                        reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                        retensiAwal=retensiLife;
//                                        break;
//                                    }
//                                }else{
//                                    retensiLife=0.0;
////                                  reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
////                                  retensiAwal=retensiLife;
//                                    break;
//                                }
//                            }}  
//                        }else{//Dian : masuk ke dalam retensi TPD
//                            idSimultan=uwDao.selectIdSimultan(spaj, 0);
//                            //SAROLD
//                                Integer row=lsRider.size();
//                                List listOldTpd=uwDao.listOldSarTPD(idSimultan);
//                                Integer rlistOldTpd=listOldTpd.size();
//                                for(int r=0; r<rlistOldTpd;r++){
//                                    Map listOldTpdMap= (Map)listOldTpd.get(r);
//                                    Integer rowke=((BigDecimal)listOldTpdMap.get("ROWKE")).intValue();
//                                    counterT=counterL+1;
//                                    if ((counterT-rowke==0) &&products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){
//                                        retensiTPD= ldecReasLf[1][4][2];
//                                        reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
//                                        retensiAwal=retensiTPD;
////                                      retensiLife=new Double(Math.max( 0,retensiLife-ldecReasLf[1][3][2]));
//                                        break;
//                                    }else if ((counterT-rowke==0)||retensiLife==ldecLimit[1]){
////                                      retensiTPD=new Double(Math.max( 0,ldecLimit[1]-ldecReasLf[1][3][2]));
//                                        retensiTPD=ldecLimit[1];
//                                        retensiAwal=retensiTPD;
//                                        break;
//                                    }else{
//                                        Double sarOld= uwDao.selectOldSarTPD(idSimultan,counterT-rowke);    
//                                        if(sarOld!=null){
//                                            retensiTPD=new Double(Math.max( 0,retensiTPD-sarOld));
//                                        }
//                                        reas=new Double(Math.max( 0,prod.getSar()-retensiTPD));
//                                        retensiAwal=retensiTPD;
//                                        break;
//                                    }
//                                }
//                            if(reas<0)//rider tersebut di reas kan.
//                                reas=0.0;
//                            else{
//                                reasRider=true;
//                                liReasRider=1;  
//                            }}
//                    }
//                    
//                    if(prod.getLsbs_id().intValue() != 804){ //Yusuf (22 jul 2011) - 804 dipisah insertnya dibawah (WPD)
//                        insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                                liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas);
//                    }
//                    if(products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString())){ // retensi untuk product syariah... retensi rider nya sama retensi dengan retensi main product
//                        retensiLife= ldecReasLf[1][4][2];
//                        if (groupReas.equals("1")&&prod.getLsbs_id()== 803 ||prod.getLsbs_id().equals(803)||prod.getLsbs_id()== 816 ||prod.getLsbs_id().equals(816)||prod.getLsbs_id()== 813 ||prod.getLsbs_id().equals(813)||prod.getLsbs_id()== 815 ||prod.getLsbs_id().equals(815)||prod.getLsbs_id()== 818 ||prod.getLsbs_id().equals(818)||prod.getLsbs_id()== 817 ||prod.getLsbs_id().equals(817)||prod.getLsbs_id()== 822 ||prod.getLsbs_id().equals(822)){
//                            idSimultan=uwDao.selectIdSimultan(spaj, 0);
//                            Double sarOld= uwDao.selectOldSar(idSimultan, i);                   
//                                if(sarOld!=null){
//                                    if (i==1){ //ada kondisi tertentu 1 ga perlu dikurangi
//                                        retensiLife=retensiLife-sarOld;
////                                      retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                                        retensiLife=new Double(Math.max( 0,retensiLife));
//                                    }else 
//                                    retensiLife=new Double(Math.max( 0,retensiLife-sarOld));
//                        }else {
//                            if((products.syariah(liBisnisId[2].toString(), liBisnisNo[2].toString()))){ // retensi untuk product syariah... retensi rider nya sama retensi dengan retensi main product
//                                retensiPa= ldecReasLf[1][4][2];
//                            }else
//                            retensiLife=new Double(Math.max( 0,ldecReasLf[1][4][2]-ldecReasLf[1][3][2]));
//                        }
//                    reas=new Double(Math.max( 0,prod.getSar()-retensiLife));
////                  retensiLife=new Double(Math.max( 0,retensiLife-prod.getSar()));
//                    retensiAwal=retensiLife;
//                    if(retensiLife<0)
//                        retensiLife=0.0;
//                    if(reas<0)//rider tersebut di reas kan.
//                        reas=0.0;
//                    else{
//                        reasRider=true;
//                        liReasRider=1;  
//                    }
////                  insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
////                          liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas);
//                    
//                    //TODO insert cuma satu doang
//                    List lsRider2=uwDao.selectMstProductInsuredRiderTambahan(spaj, 1, 1);
//                    for(int k=0;k<lsRider2.size();k++){
//                        Product prod2=(Product)lsRider2.get(k);
//                        if(prod.getReg_spaj().equals(prod2.getReg_spaj()) && 
//                                prod.getLsbs_id().compareTo(prod2.getLsbs_id())==0 &&
//                                prod.getLsdbs_number().compareTo(prod2.getLsdbs_number())==0){//jika sama
//                            /*
//                            //dibuka kalo perlu
//                            Integer contReasTemp= uwDao.selectCountReasTempNew(spaj);
//                            if (contReasTemp>0){
//                                
//                            }else
//*/
////                          insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
////                                  liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas);
//                        }}
//                    }
//                    }
//                }
//            } ///END HERE BRAIS
//            //sitem reas untuk HCP yang sistem reas quota share.
//            List lsRiderHcp=uwDao.selectMstProductInsuredRiderTambahan(spaj, 1, 1);
//            for(int i=0;i<lsRiderHcp.size();i++){
//                Product prod=(Product)lsRiderHcp.get(i);
//                if(prod.getLsbs_id()==811 || prod.getLsbs_id()==819){//HCP 811 dan 819 sar=100%UP
//                    liReasRider=1;  
//                    liReas=0;
//                    reasRider=false;
//                    ldecUp=prod.getMspr_tsi();
//                    retensiTPD=ldecLimit[1];
//                    reas=ldecUp/2;
//                    /*
//                        //dibuka kalo perlu
//                    Integer contReasTemp= uwDao.selectCountReasTempNew(spaj);
//                    if (contReasTemp>0){
//                        
//                    }else
//                    */
//                    
//                    insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                            liReas, ldecUp, ldecUp, reas, reas);
//                    
//                }
//            }   
//            
//            //Dian: WPD (Atik minta WPD dipisahkan 09112008)
//            List lsRiderWPD=uwDao.selectMstProductInsuredRiderWPD(spaj, 1, 1);
//            for(int i=0;i<lsRiderWPD.size();i++){
//                Product prod=(Product)lsRiderWPD.get(i);
//                if(prod.getLsbs_id()==804){//WPD
//                    reasRider=true;
//                    ldecUp=prod.getMspr_tsi();
//                    retensiTPD=ldecLimit[1];
//                    reas=new Double(Math.max( 0,prod.getSar()-retensiTPD));
//                    Double retensiAwal=retensiTPD;
//                    if(retensiAwal<0)
//                        retensiAwal=0.0;
//                    if(reas<0)//rider tersebut di reas kan.
//                        reas=0.0;
//                    else{
//                        reasRider=true;
//                        liReasRider=1;  
//                    }
//                
//            insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                    liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas);
//                
//                }
//            }   
//            List lsRiderHealth=uwDao.selectMstProductInsuredRiderHealth(spaj, 1, 1);
//            for(int i=0;i<lsRiderHealth.size();i++){
//                Product prod=(Product)lsRiderHealth.get(i);
//                if(prod.getLsbs_id()==820||prod.getLsbs_id()==821||prod.getLsbs_id()==823){//Health
//                    reasRider=false;
//                    ldecUp=prod.getMspr_tsi();
//                    retensiH=ldecUp*0.5;
//                    reas=new Double(Math.max( 0,prod.getSar()-retensiH));
//                    Double retensiAwal=retensiH;
//                    if(retensiAwal<0)
//                        retensiAwal=0.0;
//                    if(reas<0)//rider tersebut di reas kan.
//                        reas=0.0;
//                    else{
//                        reasRider=true;
//                        liReasRider=1;  
//                    }
//                
//            insertMreasTempNew(spaj, insuredNo, prod.getLsbs_id(), prod.getLsdbs_number(), prod.getLku_id(), 
//                    liReasRider, prod.getMspr_tsi(), prod.getSar(), retensiAwal, reas);
//                
//                }
//            }
        }
        Map map=new HashMap();
        map.put("liReas", liReas);
//        if(reasRider){ -> Not needed for PA BSM - Daru
//            uwDao.updateMReasTempMsteReas(spaj, 1, null);
//            map.put("liReasRider", 1);
//        }else
            map.put("liReasRider", 0);
        return map;
    }
    
    public Double selectGetKursReins(String kurs,Date begDate, CommonDao mapper){
        Map param=new HashMap();
        param.put("lkuId",kurs);
        param.put("begDate",defaultDateFormat.format(begDate));
        return mapper.selectLstReinsCurrency(param);
    }
    
    public Integer selectMinMstSimultaneous(String spaj,String mclId, CommonDao mapper){
        Map param = new HashMap();
        param.put("ls_old_spaj", spaj);
        param.put("ls_client", mclId);
        return mapper.selectNoSimultan(param);
    }
    
    public String selectIdSimultan(String spaj,Integer pemegang, CommonDao mapper){
        Map param=new HashMap();
        param.put("spaj",spaj);
        param.put("pemegang",pemegang);
        return mapper.selectIdSimultan(param);
    }
    
    public List selectDdsSarNew(String idSimultan, String spaj, Integer liSimultanNo, CommonDao mapper){
        Map param=new HashMap();
        param.put("id_simultan",idSimultan);
        param.put("simultanNo",liSimultanNo);
        param.put("spaj", spaj);
        return mapper.selectDdsSarNew(param);
    }
    
    public List selectDdsSarnNew(String spaj,Integer insured, CommonDao mapper){
        Map param=new HashMap();
        param.put("spaj",spaj);
        param.put("insured",insured);
        return mapper.selectDdsSarnNew(param);
    }
    
    public Double selectTahunKe(String asTgl,String asToday, CommonDao mapper){
        Map param=new HashMap();
        param.put("as_tgl",asTgl);
        param.put("as_today",asToday);
        return mapper.selectFCheckTahunKe(param);
    }
    
    public Double selectGetTsiReas(String spaj,Integer insured,Integer pemegang,Integer mainBisnisId,Integer mainBisnisNo,
            Integer bisnisId,Integer bisnisNo,String kurs,Double ldecUp, CommonDao mapper)throws DataAccessException{
        Double ldecTsi=null,ldecKaliUp=null;
        Integer liAge=null, liUnit=null;
        
        liAge=selectMstInsuredMsteAge(spaj,insured, mapper);
        if(liAge==null){
            return ldecTsi;
        }
        //
        if(mainBisnisId == 79){
            if(mainBisnisNo <= 3)
                bisnisNo = new Integer(1);
            else if (mainBisnisNo > 3 && mainBisnisNo <= 6)
                bisnisNo = new Integer(2);
            else if (mainBisnisNo > 6 && mainBisnisNo <= 9)
                bisnisNo = new Integer(3);
            else
                bisnisNo = new Integer(4);
                    
        }
        //Ekasiswa
        if(bisnisId==24 || bisnisId==31 || bisnisId==33 || bisnisId==172){
            if (pemegang== 0 )
                ldecTsi = new Double(0.5 * ldecUp.doubleValue());
            else 
                ldecTsi = ldecUp;
            //Dana Sejahtera, End Care
        }else if(bisnisId==163||bisnisId==168||bisnisId==164||bisnisId==40||bisnisId==174||bisnisId==186){
            ldecTsi = ldecUp;
        
        //Eka Simponi Dollar
        }else if(bisnisId==56 || bisnisId==64){//BC ASALNYA CUMA 56 DOANG 050401
            if(liAge < 17 )
                ldecTsi = new Double(Math.min( 30000, ldecUp.doubleValue()));
            else if(liAge <= 55 )
                ldecTsi = new Double(Math.min( 75000, ldecUp.doubleValue()));
            else
                ldecTsi = new Double(Math.min( 30000, ldecUp.doubleValue()* 20/100 ));
        //Eka Simponi       
        }else if(bisnisId==58 || bisnisId==83){
            if(liAge < 17 )
                ldecTsi = new Double(Math.min( 200000000, ldecUp.doubleValue()));
            else if(liAge <= 55 )
                ldecTsi = new Double(Math.min( 500000000, ldecUp.doubleValue()));
            else
                ldecTsi = new Double(Math.min( 200000000, ldecUp.doubleValue()* 20/100 ));
        //PA Rider & Term Rider
        }else if(bisnisId==800 || bisnisId==803){
            liUnit=selectMstProductInsuredMsprUnit(spaj,insured,bisnisId,bisnisNo, mapper);
            if(liUnit!=null)
                ldecTsi=new Double(liUnit*ldecUp.doubleValue());
        //PK Rider
        }else if(bisnisId==801){
            ldecTsi = new Double(0.5 * ldecUp.doubleValue());
        //PA Include & PK Include, CASH PLAN, tpd
        }else if(bisnisId==600 || bisnisId==601 || bisnisId==806 ||bisnisId==807){
            ldecKaliUp=selectLstRiderIncludeLsridKaliUp(mainBisnisId,mainBisnisNo,bisnisId,bisnisNo,kurs, mapper);
            if(ldecKaliUp!=null)
                ldecTsi=new Double(ldecKaliUp.doubleValue()*ldecUp.doubleValue());
            //
            if(ldecKaliUp ==null){
                ldecTsi=null;
            }else if(bisnisId==600){
                if (mainBisnisId == 56){//PA Include pd plan Eka Simponi Dollar
                    if (liAge < 17)
                        ldecTsi = new Double(Math.min( 30000, ldecTsi.doubleValue()));
                    else if (liAge <= 55)
                        ldecTsi = new Double(Math.min( 75000, ldecTsi.doubleValue()));
                    else
                        ldecTsi = new Double(Math.min( 30000, ldecTsi.doubleValue() * 20/100 ));
                }else if (mainBisnisId== 58 || mainBisnisId == 83){//PA Include pd plan Eka Simponi
                    if(liAge < 17 )
                        ldecTsi = new Double(Math.min( 200000000, ldecTsi.doubleValue()));
                    else if(liAge <= 55)
//                      ldecTsi = new Double(Math.min( 500000000, ldecTsi.doubleValue()));
                        ldecTsi = new Double(Math.min( 750000000, ldecTsi.doubleValue())); // per 1 apri 2008
                    else
                        ldecTsi = new Double(Math.min( 200000000, (ldecTsi.doubleValue() * 20/100 ) ));
                }else if (mainBisnisId == 65){ 
                    if (ldecTsi.doubleValue() + ldecUp.doubleValue()> 600000000 )
                        ldecTsi = new Double(600000000 - ldecUp.doubleValue());
                }

            }else if(bisnisId==601){
                if (mainBisnisId == 56) {//PK Include pd plan Eka Simponi Dollar
                    if(liAge < 17)
                        ldecTsi =new Double(Math.min( 15000, ldecTsi.doubleValue()));
                    else if(liAge <= 55)
                        ldecTsi =new Double(Math.min( 37500, ldecTsi.doubleValue()));
                    else
                        ldecTsi =new Double(Math.min( 15000, (ldecTsi.doubleValue() * 20/100 ) ));
                }else if(mainBisnisId == 58 || mainBisnisId == 83) {//PK Include pd plan Eka Simponi
                    if (liAge < 17 )
                        ldecTsi = new Double(Math.min( 100000000, ldecTsi.doubleValue()));
                    else if (liAge <= 55)
                        ldecTsi = new Double(Math.min( 250000000, ldecTsi.doubleValue() ));
                    else
                        ldecTsi = new Double(Math.min( 100000000, ldecTsi.doubleValue() * 20/100 ));
                }else if(mainBisnisId == 65){ 
                    if (ldecTsi.doubleValue() > 300000000 )
                        ldecTsi =new Double(300000000);
                }
            }else if(bisnisId==806){
                ldecTsi = ldecKaliUp;           
            }else if(bisnisId==807){
                if (ldecTsi.doubleValue() > 300000000 ) 
                    ldecTsi = new Double(300000000);
            }
                
        }else{
            ldecKaliUp=selectLstReinsDescLsrpKaliUp(bisnisId,bisnisNo,kurs, mapper);
            if(ldecKaliUp==null)
                ldecTsi=null;
            else 
                ldecTsi = new Double(ldecKaliUp.doubleValue() * ldecUp.doubleValue());
        }

        if(ldecTsi==null){
          
        }
        return ldecTsi;
    }
    
    public Integer selectMstInsuredMsteAge(String spaj,Integer insured, CommonDao mapper){
        Map param=new HashMap();
        param.put("as_spaj",spaj);
        param.put("ai_insured",insured);
        return mapper.selectMstInsuredAge(param);
        
    }
    
    public Integer selectMstProductInsuredMsprUnit(String spaj,Integer insured,Integer lsbsId,Integer lsdbsNumber, CommonDao mapper){
        Map param=new HashMap();
        param.put("reg_spaj",spaj);
        param.put("mste_insured_no",insured);
        param.put("lsbs_id",lsbsId);
        param.put("lsdbs_number",lsdbsNumber);
        return mapper.selectMstProductInsuredMsprUnit(param);
    }
    
    public Double selectLstRiderIncludeLsridKaliUp(Integer mainBisnisId,Integer mainBisnisNo,Integer bisnisId,Integer bisnisNo,String kurs, CommonDao mapper){
        Map param=new HashMap();
        param.put("ai_main_bisnis_id",mainBisnisId);
        param.put("ai_main_bisnis_no",mainBisnisNo);
        param.put("ai_bisnis_id",bisnisId);
        param.put("ai_bisnis_no",bisnisNo);
        param.put("as_kurs",kurs);
        return mapper.selectLstRiderInclude2(param);
    }
    
    public Double selectLstReinsDescLsrpKaliUp(Integer bisnisId,Integer bisnisNo,String kurs, CommonDao mapper){
        Map param=new HashMap();
        param.put("ai_bisnis_id",bisnisId);
        param.put("ai_bisnis_no",bisnisNo);
        param.put("as_kurs",kurs);
        return mapper.selectLstReinsDesc2(param);
        
    }
    
    public Double selectGetSar(Integer aiType,Integer aiBisnisId,Integer aiBisnisNo, String asKurs, Integer aiCbayar,
            Integer aiLbayar, Integer aiLTanggung, Integer aiYear, Integer aiAge, CommonDao mapper){
        Double ldecValue;
        Integer liLTanggungMax;
        if(aiBisnisId<300){
            ldecValue=null;
            liLTanggungMax=null;
            if (aiBisnisId == 70 || aiBisnisId == 71 || aiBisnisId == 72 || aiBisnisId == 172)
                aiBisnisId = new Integer(31);
            else if (aiBisnisId == 78) 
                aiBisnisId = new Integer(52); 
            else if (aiBisnisId == 82) 
                aiBisnisId = new Integer(69);
            else if (aiBisnisId == 83) 
                aiBisnisId = new Integer(58);
            //
            if (aiCbayar == 1 || aiCbayar == 2 || aiCbayar == 6)
                aiCbayar = new Integer(3);
            if (aiCbayar == 4 || aiCbayar == 5 )
                aiCbayar = new Integer(3);
            //
            liLTanggungMax=selectLstReinsDesc1(asKurs,aiBisnisId,aiBisnisNo, mapper);
            if(liLTanggungMax!=null)
                if(liLTanggungMax>0)
                    aiLTanggung=new Integer(liLTanggungMax-aiAge);
            //
            /*if (aiBisnisId == 182 && aiBisnisNo == 12)
                    if(aiLTanggung==null)
                        aiLTanggung=new Integer(8);
                        aiLbayar = new Integer(5);
            */
            if(aiType == 1)
                aiYear = new Integer(1);
            //
            if (aiBisnisId == 62 )
                aiBisnisId = new Integer(52); //040401BC
            if (aiBisnisId == 56 || aiBisnisId == 64 )
                aiLTanggung = new Integer(8); //ASAL LSBS=56 050401BC
            if (aiBisnisId == 64)
                aiBisnisId = new Integer(56);
            if (aiBisnisId != 65 )
                aiAge = new Integer(1);         
            if (aiCbayar == 0)
                aiLbayar = new Integer(1);
            //
            ldecValue=selectLstTableAwal(aiType,aiBisnisId,asKurs,aiCbayar,
                    aiLbayar,aiLTanggung,aiYear,aiAge, mapper);
            //
        }else if(aiBisnisId < 600 )
            ldecValue=null;
        else
            ldecValue = new Double(1000);

        if(ldecValue==null)
            ldecValue = new Double(1000);
            
        return ldecValue;
    }
    
    public Integer selectLstReinsDesc1 (String as_kurs,int ai_bisnis_id,int ai_bisnis_no, CommonDao mapper){
        Map param=new HashMap();
        param.put("ai_bisnis_id",new Integer(ai_bisnis_id));
        param.put("ai_bisnis_no",new Integer(ai_bisnis_no));
        param.put("as_kurs",as_kurs);
        return mapper.selectLstReinsDesc1(param);
    }
    
    public Double selectLstTableAwal(int ai_type,int ai_bisnis_id,String as_kurs,int ai_cbayar,int ai_lbayar,int ai_ltanggung,int ai_year,int ai_age, CommonDao mapper){
        Map param=new HashMap();
        param.put("ai_type",new Integer(ai_type));
        param.put("ai_bisnis_id",new Integer(ai_bisnis_id));
        param.put("as_kurs",as_kurs);
        param.put("ai_cbayar",new Integer(ai_cbayar));
        param.put("ai_lbayar",new Integer(ai_lbayar));
        param.put("ai_ltanggung",new Integer(ai_ltanggung));
        param.put("ai_year",new Integer(ai_year));
        param.put("ai_age",new Integer(ai_age));
        return mapper.selectLstTableAwal(param);
    }
    
    public double selectKursAdjust(Double ldecUp, Double ldecKurs, String asKurs1,String asKurs2){
        double ldecReturn=0;
        //
        if(asKurs1.equals(asKurs2))
            ldecReturn = ldecUp.doubleValue();
        else if(asKurs1.equals("01"))
            ldecReturn = ldecUp.doubleValue()* ldecKurs.doubleValue();
        else if(asKurs1.equals("02"))
            ldecReturn = ldecUp.doubleValue()/ ldecKurs.doubleValue();
        
        return ldecReturn;  
    }
    
    public int insertMSarTemp(String spaj,String asPolis,Integer aiBisnisId, Integer aiBisnisNo,
            String aiKursId, Double ldecSar, Integer aiSts, Integer aiMedical, int aiCnt,String reg_spaj_ref, CommonDao mapper){
        aiCnt++;
        try{
            ldecSar=new Double(dec2.format(ldecSar.doubleValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
        
        Map insParam =new HashMap();
        insParam.put("txtnospaj",spaj);
        //insParam.put("reg_spaj_ref",reg_spaj_ref);
        insParam.put("li_cnt",new Integer(aiCnt));
        insParam.put("ls_polis",asPolis);
        insParam.put("li_bisnis_id",aiBisnisId);
        insParam.put("li_bisnis_no",aiBisnisNo);
        insParam.put("ls_kurs_id",aiKursId);
        insParam.put("ldec_sar",ldecSar);
        insParam.put("li_sts_polis",aiSts);
        insParam.put("li_medis",aiMedical);
        mapper.insertMSarTemp(insParam);
        return aiCnt;
    }
    
    public Double selectGetRetensi(Integer aiMainBisnisId,Integer aiMainBisnisNo, Integer aiRider, Integer aiType, Integer aiMedis,
            String asKurs, Date begDate,Integer aiAge,String spaj , CommonDao mapper){
        Integer liTbisnis=null, liTreins = null;
        Double ldecValue=null;
        if (aiMainBisnisId == 331){
            if(aiType == 1){//Retensi Limit
                if (asKurs.equals("01")){
                    if(aiMedis == 0 )
                        ldecValue = new Double(15000000);
                    else
                        ldecValue = new Double(30000000);
                }
            }else if(aiType == 2){//UW Limit
                if (asKurs.equals("01")){
                    if( aiMedis == 0){
                        if (aiAge >= 1 && aiAge <= 50 )
                            ldecValue = new Double(200000000);
                        else if (aiAge >= 51 && aiAge <= 55)
                            ldecValue = new Double(150000000);
                        else if (aiAge >= 56 && aiAge <= 60 )
                            ldecValue = new Double(100000000);
                    }else if (aiMedis == 1){
//                      ldecValue = new Double(500000000);
                        if((isProductSyariah(aiMainBisnisId.toString(), aiMainBisnisNo.toString(), mapper))){   
                            ldecValue = new Double(500000000); //per 1 april 2008
                        }else
                            ldecValue = new Double(750000000); //per 1 april 2008
                    }
                }
            }
            
        }else if (aiMainBisnisId == 539){// doel020305
            if (aiMedis == 0)
                ldecValue = new Double(20000);
            else
                ldecValue = new Double(32000);
        }
        else{
            Map param=mapper.selectLstBisnisRetensi2(aiMainBisnisId);
            liTbisnis=(Integer) param.get("LSTB_ID");
            liTreins=(Integer) param.get("LSTR_ID");
            //ldecValue=new Double(500000000); //special case 42200800143(novie)
//          List ldecValueList=selectLstLimitReinsuranceLsliValue(liTbisnis,liTreins,aiRider,aiType,
//                  asKurs,aiMedis,aiAge,begDate,spaj);
//          for (int j = 0; j < ldecValueList.size(); j++) {
//              Map ldecValueMap= (Map) ldecValueList.get(j);
//              ldecValue= ((BigDecimal)ldecValueMap.get("LSLI_VALUE")).doubleValue();
//              }
            if((isProductSyariah(aiMainBisnisId.toString(), aiMainBisnisNo.toString(), mapper))){   
                ldecValue = new Double(500000000); //per 1 april 2008
            }else
            ldecValue=selectLstLimitReinsuranceLsliValue(liTbisnis,liTreins,aiRider,aiType,
                    asKurs,aiMedis,aiAge,begDate,spaj, mapper);
        }
        
        // Untuk Special Case Tidak Mendapatkan Retensi
        //default value retensi untuk tahun 2007 medis dan nonmedis sama (untuk special case
//      if(asKurs.equals("01")){
//          ldecValue=new Double(750000000); //RP
//      }else{
//          ldecValue=new Double(75000); //US$
//      }
        if(ldecValue==null){
       /*     err.reject("","Retensi Not Found Bisnis Id="+aiMainBisnisId+" BisnisTYpe="+liTbisnis+" reins Type="+liTreins+
                    "Rider Type="+aiRider+" table type="+aiType+" Medical Status="+aiMedis+" kurs="+asKurs+
                    "Date="+defaultDateFormat.format(begDate)+" age="+aiAge+" Check selectGetRetensi or lst_limit_reinsurance");
    */    }
        return ldecValue;
    }
    
    public Double selectLstLimitReinsuranceLsliValue(Integer liTbisnis,Integer liTreins,Integer aiRider,
            Integer aiType, String asKurs, Integer aiMedis, Integer aiAge,Date begDate,String spaj, CommonDao mapper){
        Map param=new HashMap();
        param.put("li_tbisnis",liTbisnis);
        param.put("li_treins",liTreins);
        param.put("ai_rider",aiRider);
        param.put("ai_type",aiType);
        param.put("as_kurs",asKurs);
        param.put("ai_medis",aiMedis);
        param.put("ai_age",aiAge);
        param.put("adt_bdate",begDate);
        param.put("spaj",spaj);
        return  mapper.selectLstLimitReinsureance3(param); 
//      return (Double)querySingle("select.lst_limit_reinsureance2",param); 
    }
    
    public boolean isProductSyariah(String businessID, String businessNo, CommonDao mapper) {
        //yg satu lsbs_id namun bukan produk syariah
        int lsbs = Integer.parseInt(businessID);
        int lsdbs = Integer.parseInt(businessNo);
        
        if(mapper.selectLineBusLstBisnis(businessID)==3){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean isProductInCategory(String kategori, String businessID) {
        String[] array = kategori.split(",");
        for (int i = 0; i < array.length; i++)
            array[i] = array[i].trim();
        Set set = new HashSet(Arrays.asList(array));
        if (set.contains(FormatString.rpad("0", businessID, 3))) {
            return true;
        } else
            return false;       
    }
    
    public boolean isProductPowerSave(String businessID){
        return isProductInCategory("086,094,123,124,142,143,144,155,158,175,176,177,184,188,207", businessID);
    }
    
    public void updateMReasTempMsteReas(String spaj,Integer msteReas, Integer mste_backup, CommonDao mapper){
        Map param=new HashMap();
        param.put("spaj",spaj);
        param.put("mste_reas",msteReas);
        param.put("mste_backup", mste_backup);
        mapper.updateMReasTempMsteReas(param);
    }
    
    public void updateMstInsuredReasnBackup(String spaj,Integer insured,Integer liReas,Integer liBackup,
            Integer lssaId,String medis, CommonDao mapper){
        Map param=new HashMap();
        param.put("txtnospaj",spaj);
        param.put("txtli_insured_no",insured);
        param.put("li_reas",liReas);
        param.put("li_backup",liBackup);
        param.put("lssaId", lssaId);
        param.put("medis", medis);
        mapper.updateMstInsuredReasnBackup(param);
    }
    
    public void wf_ins_cash_tpd(String spaj,Integer insured,CommonDao mapper){
        Integer liMbisnisId, liMbisnisNo;
        Double ldecMtsi;
        double ldecTsiCash,ldecSarCash,ldecRtsCash,ldecReaCash,ldecTsiTpd;
        double ldecSarTpd,ldecRtsTpd,ldecReaTpd; 
        String lsKursId;
        List list=mapper.selectProductUtama(spaj);
        Product product=(Product)list.get(0);
        liMbisnisId=product.getLsbs_id();
        liMbisnisNo=product.getLsdbs_number();
        ldecMtsi=product.getMspr_tsi();
        lsKursId=product.getLku_id();
        //
        if(liMbisnisId == 65){
            ldecTsiCash = 400000;
            ldecSarCash = 400000;
            ldecRtsCash = 200000;
            ldecReaCash = 200000;
            ldecTsiTpd = ldecMtsi.doubleValue();
            ldecSarTpd = ldecMtsi.doubleValue();
            ldecRtsTpd = 25000000;
            ldecReaTpd = ldecMtsi.doubleValue() - 25000000;
            
        }else if(liMbisnisId == 66 || liMbisnisId == 79 || liMbisnisId == 91 ){
            if (liMbisnisId == 79 || liMbisnisId == 91 ){
                if (liMbisnisNo <= 3 )
                    liMbisnisNo = new Integer(1);
                else if(liMbisnisNo > 3 && liMbisnisNo <= 6)
                    liMbisnisNo = new Integer(2);
                else if (liMbisnisNo > 6 && liMbisnisNo <= 9)
                    liMbisnisNo = new Integer(3);
                else
                    liMbisnisNo = new Integer(4);
            }
            
            if (liMbisnisNo == 1){
                ldecTsiCash = 250000;
                ldecSarCash = 250000;
                ldecRtsCash = 125000;
                ldecReaCash = 125000;
            }else if (liMbisnisNo == 2){
                ldecTsiCash = 500000;
                ldecSarCash = 500000;
                ldecRtsCash = 250000;
                ldecReaCash = 250000;
            }else if (liMbisnisNo == 3){
                ldecTsiCash = 750000;
                ldecSarCash = 750000;
                ldecRtsCash = 375000;
                ldecReaCash = 375000;
            }else{
                ldecTsiCash = 1000000;
                ldecSarCash = 1000000;
                ldecRtsCash = 500000;
                ldecReaCash = 500000;
            }
            ldecTsiTpd = 0;
            ldecSarTpd = 0;
            ldecRtsTpd = 0;
            ldecReaTpd = 0;
        }else{
            ldecTsiCash = 0;
            ldecSarCash = 0;
            ldecRtsCash = 0;
            ldecReaCash = 0;
            ldecTsiTpd = 0;
            ldecSarTpd = 0;
            ldecRtsTpd = 0;
            ldecReaTpd = 0; 
        }
        //
        updateMReasTemp(spaj,0,ldecTsiCash,ldecSarCash,ldecRtsCash,ldecReaCash,
                0,ldecTsiTpd,ldecSarTpd,ldecRtsTpd,ldecReaTpd, mapper);
    }
    
    public void updateMReasTemp(String spaj,double simCash,double tsiCash,double sarCash,double rtsCash,
            double reaCash,double simTpd,double tsiTpd,double sarTpd,double rtsTpd,double reaTpd, CommonDao mapper){
        Map param=new HashMap();
        param.put("txtnospaj",spaj);
        param.put("sim_cash",new Double(simCash));
        param.put("ldec_tsi_cash",new Double(tsiCash));
        param.put("ldec_sar_cash",new Double(sarCash));
        param.put("ldec_rts_cash",new Double(rtsCash));
        param.put("ldec_rea_cash",new Double(reaCash));
        param.put("sim_tpd",new Double(simTpd));
        param.put("ldec_tsi_tpd",new Double(tsiTpd));
        param.put("ldec_sar_tpd",new Double(sarTpd));
        param.put("ldec_rts_tpd",new Double(rtsTpd));
        param.put("ldec_rea_tpd",new Double(reaTpd));
        mapper.updateMReasTemp(param);
    }
    
    public boolean wf_ins_bill_pas(String spaj,Integer insured,Integer active,Integer lsbsId,Integer lsdbsNumber,Integer lspdId,Integer lstbId,List lsDp,
            String lusId,Policy policy, CommonDao mapper)throws DataAccessException{
        Integer liPmode,liPperiod,liMonth;
        int liPremiKe, liThKe, liPaid = 0;
        Integer liId = new Integer(0); //3 ~ biaya polis = Rp. 20.000,-
        String lsCab, lsKursId, lsPayId, lsWakil, lsRegion, lsKursPolis;
        Double ldecPremi, ldecBpolis, ldecDp;
        Date ldtBegDate, ldtEndDate, ldtDueDate;
        boolean lbRet = true, lbTp = false;
        Double ldecKurs = new Double(1);
        Date ldt_tgl_book;
    
        //Policy policy=(Policy)selectDw1Underwriting(spaj,lspdId,lstbId);
        
        lsKursId=policy.getLku_id();
        lsKursPolis=policy.getLku_id();
        liPmode=policy.getLscb_id();
        lsCab=policy.getLca_id();
        liPperiod=policy.getMspo_pay_period();
        lsWakil=policy.getLwk_id();
        lsRegion=policy.getLsrg_id();
        //
        Map map=selectMstInsuredBegDateEndDate(spaj,insured, mapper);
        ldtBegDate=(Date)map.get("MSTE_BEG_DATE");
        ldtEndDate=(Date)map.get("MSTE_END_DATE");
        
        //Yusuf (16 Oct 2009) - Bila Stable Link, ambil begdate nya itu dari slink, bukan dari insured
//        if(products.stableLink(uwDao.selectBusinessId(spaj))) { -> Not needed for PA BSM - Daru
//            List<Map> daftarStableLink = uwDao.selectInfoStableLink(spaj);
//            ldtBegDate = null;
//            ldtEndDate = null;
//            for(Map info : daftarStableLink) {
//                int msl_no = ((BigDecimal) info.get("MSL_NO")).intValue(); 
//                if(msl_no == 1) { //apabila 1, maka itu premi pokok
//                    ldtBegDate=(Date) info.get("MSL_BDATE");
//                    ldtEndDate=(Date) info.get("MSL_EDATE");
//                    break;
//                }
//            }
//            if(ldtBegDate == null || ldtEndDate == null) throw new RuntimeException("BEGDATE SLINK ERROR, HARAP HUBUNGI ITwebandmobile@sinarmasmsiglife.co.id");
//        }
        
        //
        ldecPremi = selectMstProductInsuredPremiDiscount(spaj,insured,active, mapper);
        if(lsKursId.equals("02"))
            liId = new Integer(0); //4~biaya polis = $5
        //
        ldecBpolis=mapper.selectMstDefault(liId); //tarik nilai default biaya polis
        //Kalo proteksiNARMAS, biaya polis tidak ada !!! juga pa pti, juga unit-linked
        int pos=0;
        String kode="061, 073, 077, 084, 079, 080, 081, 087, 088, 090, 091, 092, 095, 097, 098, 100, 101, 102, 103, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 126, 127, 128, 129, 133, 135, 136, 137, 138, 139, 150, 151, 107, 140, 141, 148, 134,152,153,154,155,157,158,159,160,162,164,165,166,174,199,217,218";
        String bisnisId=f3.format(lsbsId);
        pos=kode.indexOf(bisnisId);
        Map mapTt=mapper.selectTertanggung(spaj);
        Integer flagGuthrie=(Integer)mapTt.get("MSTE_FLAG_GUTHRIE");
        
        if(pos>=0 || flagGuthrie.intValue()==1 || isProductPowerSave(FormatString.rpad("0", lsbsId.toString(), 3))){
            ldecBpolis = new Double(0);
        }
            
        //Multi Invest
        if(lsbsId==96){
            ldecBpolis = new Double(0);
        }
        //Untuk cerdas new(121), bebas biaya polis
        else if(lsbsId==121){
            ldecBpolis = new Double(0);
        }
        //khusus produk simas sehat biaya polisnya 37,000
        else if(lsbsId==161){
            //ldecBpolis = new Double(37000);
            ldecBpolis = new Double(0);
        //Yusuf - Stable link, tidak ada biaya polis
        }else if(lsbsId==164) {
            ldecBpolis = (double) 0;
//        }else if(products.muamalat(spaj)){ -> Not needed for PA BSM - Daru
//            //ldecBpolis = (double) 75000;
//            ldecBpolis = new Double(0);
//            //khusus mabrur, biayanya beda
//            if(lsbsId==153) ldecBpolis = (double) 0;//(double) 40000;
        }else if(lsbsId==171){
            ldecBpolis = (double) 0;
        }else if(lsbsId==172){
            ldecBpolis = (double) 0;
        }else if (lsbsId==183 || lsbsId==189 || lsbsId == 193) {//Eka Sehat
            if(lsdbsNumber<16){
                ldecBpolis=new Double(37000);
            }else{
                ldecBpolis = (double) 0;
            }
            if(lsbsId==189){
                ldecBpolis = (double) 0;
            }
            //ldecBpolis = new Double(0);
        
        }else if (lsbsId==186) {//Progressive Link
            ldecBpolis = (double) 0;
        }
        
        //super prot, eka protection, biaya polis 20 rebu
        if(lsbsId.intValue() == 45 || lsbsId.intValue()==85|| lsbsId.intValue() == 130) {
            if(lsKursId.equals("02")){
                ldecBpolis = (double) 5;
            }else if(lsKursId.equals("01")){
                ldecBpolis = (double) 20000;
            }
        //super sejahtera dan (super sehat atau super sehat plus), biaya polis 20rb
        }else if(lsbsId.intValue() == 145 || lsbsId.intValue() == 53 || lsbsId.intValue() == 54 || lsbsId.intValue() == 131 || lsbsId.intValue() == 132){
            ldecBpolis = (double) 20000;
//        }else if(products.SuperSejahtera(lsbsId.toString())){ -> Not needed for PA BSM - Daru
//                ldecBpolis = (double) 0;
//        }else if(products.stableSave(lsbsId.intValue(), lsdbsNumber.intValue())){
//            ldecBpolis = (double) 0;
        }
        
        //
        if(liPmode!=0){
            liMonth=mapper.selectLstPayModeLscbTtlMonth(liPmode);
        /*    if(logger.isDebugEnabled())logger.debug("ldt enddate ="+defaultDateFormat.format(ldtEndDate));
        */    Date dTemp=selectAddMonths(defaultDateFormat.format(ldtBegDate),liMonth);
           /* if(logger.isDebugEnabled())logger.debug("dtemp ="+defaultDateFormat.format(dTemp));
           */ ldtEndDate=FormatDate.add(dTemp,Calendar.DATE,-1);
           /* if(logger.isDebugEnabled())logger.debug("ldt enddate ="+defaultDateFormat.format(ldtEndDate));
           */ //Himmia 30/01/2001
            if(sdfDd.format(ldtEndDate).equals(sdfDd.format(ldtBegDate))) {
                ldtEndDate=FormatDate.add(ldtEndDate,Calendar.DATE,-1);
            }
        }
        //
        liThKe = 1;
        liPremiKe = 1;
        Calendar calTemp=new GregorianCalendar(2006,06-1,1);
        
        //yusuf 02062006 due date lebih besar dari 1 juni 2006 ditambah 1 minggu
        // direvisi jadi ditambah 30 hari
        if(ldtBegDate.compareTo(calTemp.getTime()) >0){
            //ldtDueDate=FormatDate.add(ldtBegDate,Calendar.DATE,7);
            ldtDueDate=FormatDate.add(ldtBegDate,Calendar.DATE,30);
        }else
        ldtDueDate = selectAddMonths(defaultDateFormat.format(ldtBegDate),new Integer(1));
        
        if(ldtDueDate==null){
      /*      err.reject("","Get End-Date Is Not Working Properly (e)");
      */      return false;
        }
        //
//        if(!lsDp.isEmpty()){ -> Not needed for PA BSM - Daru
//            for(int i=0;i<lsDp.size();i++){
//                DepositPremium depPremi=(DepositPremium) lsDp.get(i);
//                ldecDp=new Double(0);
//                liId=depPremi.getMsdp_jtp();
//                if(liId==1){
//                    lsKursId=depPremi.getLku_id();
//                    ldecDp=depPremi.getMsdp_payment();
//                    if( (!lsKursPolis.equals(lsKursId) ) || (lsKursId.equals("02")) ){
//                        ldt_tgl_book = depPremi.getMsdp_date_book();
//                        ldecKurs = selectGetKursJb(ldt_tgl_book,"J");
//                        if(ldecKurs==null){
//                            err.reject("","Kurs tgl "+defaultDateFormat.format(ldt_tgl_book)+" (dd/mm/yyyy) tidak ada");
//                            return false;
//                        }
//                    }
//                    
//                    if(!lsKursPolis.equals(lsKursId)) {
//                        if(lsKursPolis.equals("01"))
//                            ldecDp =new Double(ldecDp.doubleValue()* ldecKurs.doubleValue());
//                        else
//                            ldecDp =new Double(ldecDp.doubleValue()/ ldecKurs.doubleValue());
//                    }
//                    
//                    ldecPremi = new Double(ldecPremi.doubleValue()-ldecDp.doubleValue());
//                    lbTp = true;
//                }
//            }
//        }
        //
        ldecPremi = new Double(ldecPremi.doubleValue() + ldecBpolis.doubleValue());
        if(ldecPremi.doubleValue() <= 0)
            liPaid=1;
        //EKALINK FAMILY / ekalink famili syariah
        Integer msbiThBak=null,msbiPremiBak=null;
//        Integer idxUlink="115,116,117,118,119,122,138,139,153,159,160,162,164,165,166,174,190,191,199,200,202,213,215,216,217,218".indexOf(bisnisId); -> Not needed for PA BSM - Daru
//        if(idxUlink>=0){
//            //
//            Double ldecPremiAsli=selectMstProductInsuredPremiDiscount(spaj,insured,active, mapper);
//            msbiThBak=1;
//            msbiPremiBak=1;
//            if (liPmode == 1 || liPmode == 2 || liPmode == 6 || liPmode == 3)
//                liPmode= 3;
//            
//            Double persenAk = getBiaAkuisisi(lsbsId, lsdbsNumber,liPmode, liPperiod, new Integer(1));
//            
//            PremiUlink premiUlink=new PremiUlink();
//            premiUlink.setReg_spaj(spaj);
//            premiUlink.setMsbi_tahun_ke(new Integer(1));
//            premiUlink.setMsbi_premi_ke(new Integer(1));
//            premiUlink.setPremi_ke(new Integer(1));
//            premiUlink.setLine_ak(new Integer(1));
//            premiUlink.setTh_ak(new Integer(1));
//            premiUlink.setPremi(ldecPremiAsli);
//            premiUlink.setTotal_premi(ldecPremiAsli);
//            premiUlink.setPersen_ak(persenAk);
//            insertMstPremiUlink(premiUlink);
//
//        }
        
        //himmia 06/03/2007 untuk produk multi invest msbi_persen_paid=100
        Integer msbiPersenPaid=null;
        if(bisnisId.indexOf("074,076,096,099,135,136,182,194")>0){
            msbiPersenPaid=100;
        }
        Integer countBill= selectCountMstBill(spaj, new Integer(liThKe),new Integer(liPremiKe), mapper);
        if(countBill>0){
            
        }else{
            if(lsbsId==187 && (lsdbsNumber>=11 && lsdbsNumber<=14)){
                Map dataRecur = mapper.selectDataVirtualAccount(spaj);
                int flag_cc = ((BigDecimal) dataRecur.get("MSTE_FLAG_CC")).intValue();
                if(flag_cc==1 || flag_cc ==2){
                    insertMstBilling(spaj,new Integer(liThKe),new Integer(liPremiKe),ldtBegDate,ldtEndDate,ldtDueDate,lsKursPolis,
                            ldecBpolis,new Double(0),new Double(0),new Double(0),ldecPremi,new Integer(0),new Integer(1),
                            new Integer(0),new Integer(0),lusId,new Integer(56),lsCab,lsWakil,lsRegion,null,msbiThBak,msbiPremiBak,msbiPersenPaid,null, mapper);
                }else{
                    insertMstBilling(spaj,new Integer(liThKe),new Integer(liPremiKe),ldtBegDate,ldtEndDate,ldtDueDate,lsKursPolis,
                            ldecBpolis,new Double(0),new Double(0),new Double(0),ldecPremi,new Integer(0),new Integer(1),
                            new Integer(0),new Integer(0),lusId,new Integer(4),lsCab,lsWakil,lsRegion,null,msbiThBak,msbiPremiBak,msbiPersenPaid,null, mapper);
                }
                insertMstDetBilling(spaj, mapper);
            }else{
                insertMstBilling(spaj,new Integer(liThKe),new Integer(liPremiKe),ldtBegDate,ldtEndDate,ldtDueDate,lsKursPolis,
                        ldecBpolis,new Double(0),new Double(0),new Double(0),ldecPremi,new Integer(0),new Integer(1),
                        new Integer(0),new Integer(0),lusId,new Integer(4),lsCab,lsWakil,lsRegion,null,msbiThBak,msbiPremiBak,msbiPersenPaid,null, mapper);
                insertMstDetBilling(spaj, mapper);
            }
        
        }

        //Yusuf (1/5/08) stable link, insert billing dan det billingnya, diambil dari MST_SLINK
//        if(products.stableLink(bisnisId)) { -> Not needed for PA BSM - Daru
//            List<Map> daftarStableLink = uwDao.selectInfoStableLink(spaj);
//
//            for(Map info : daftarStableLink) {
//                int msl_no = ((BigDecimal) info.get("MSL_NO")).intValue(); 
//                if(msl_no>1) { //apabila 1, maka itu premi pokok
//                    double premi = ((BigDecimal) info.get("MSL_PREMI")).doubleValue();
//                    insertMstBilling(spaj, 1, msl_no,
//                            (Date) info.get("MSL_BDATE"),
//                            (Date) info.get("MSL_EDATE"),
//                            (Date) info.get("MSL_NEXT_DATE"),
//                            lsKursPolis, new Double(0), new Double(0), new Double(0), new Double(0),
//                            premi, 0, 1, 0, 0, lusId, 4, 
//                            lsCab, lsWakil, lsRegion, 1, null, null, msbiPersenPaid,null);
//                    insertMstDetBilling(spaj, 1, msl_no, 1, lsbsId, lsdbsNumber, premi, new Double(0));
//                }
//            }                   
//        //jika unit link
//        }else if(products.unitLink(bisnisId)) {
//            
////          Integer li_tu=new Integer(0);
////          Map h=selectMstUlinkTopup(spaj,new Integer(1));
////          li_tu=(Integer)h.get("MU_PERIODIC_TU");
////          ldecPremi=(Double)h.get("MU_JLH_TU");
////          if(li_tu==null)
////              li_tu=new Integer(0);
////          if(ldecPremi==null)
////              ldecPremi=new Double(0);
////          
////          if(li_tu >= 1 && ldecPremi.doubleValue() > 0) {
////              insertMstBilling(spaj,new Integer(liThKe), new Integer(2),ldtBegDate,ldtEndDate,ldtDueDate,
////                      lsKursPolis,new Double(0),new Double(0),new Double(0),new Double(0),ldecPremi,new Integer(0),
////                      new Integer(1),new Integer(0),new Integer(0),lusId,new Integer(4),lsCab,lsWakil,lsRegion,new Integer(1),null,null,msbiPersenPaid);
////              insertMstDetBillingTopup(spaj,ldecPremi);
////          }
//            int topupBerkala=this.uwDao.validationTopup(spaj);
//            //List<Integer> daftarMuKe = uwDao.selectMuKe(spaj);
//            List daftarTopUp= uwDao.selectMstUlinkTopupNewForDetBilling(spaj);
//            
//            if(topupBerkala>=1){
//                Integer x=selectMaxMstDetBillingDetKe(spaj); 
//                for(int d=0;d<daftarTopUp.size();d++) {
//                    x++;
//                    Map mapUlink=(HashMap)daftarTopUp.get(d);
//                    Double premi=(Double)mapUlink.get("MU_JLH_PREMI");
//                    Integer premiKe=(Integer)mapUlink.get("MU_PREMI_KE");
//                    Integer topup=(Integer)mapUlink.get("LT_ID");//topup 2=tunggal 5=berkala
//                    Integer flagTopup=null;
//                    if(topup==2)
//                        flagTopup=1;
//                    else if(topup==5)
//                        flagTopup=2;
//                        
//                    insertMstBilling(spaj,new Integer(liThKe),d+2,ldtBegDate,ldtEndDate,ldtDueDate,
//                    lsKursPolis,new Double(0),new Double(0),new Double(0),new Double(0),ldecPremi,new Integer(0),
//                    new Integer(1),new Integer(0),new Integer(0),lusId,new Integer(4),lsCab,lsWakil,lsRegion,flagTopup,null,null,msbiPersenPaid,null);
//                    if(premiKe!=1)
//                        x=1;
//                    
//                    insertMstDetBilling(spaj, 1, premiKe, x, lsbsId, lsdbsNumber, premi, new Double(0));
//                }
//            }   
//        }
        
        //
//        if(lbTp){//kalo ada DP -> Not needed for PA BSM - Daru
//            for(int i=0;i<lsDp.size();i++){
//                DepositPremium depPremi=(DepositPremium) lsDp.get(i);
//                if(depPremi.getMsdp_jtp()==1){
//                    lsKursId=depPremi.getLku_id();
//                    ldecKurs=new Double(1);
//                    if( (!lsKursPolis.equals(lsKursId) ) || (lsKursId.equals("02")) ){
//                        ldt_tgl_book = depPremi.getMsdp_date_book();
//                        ldecKurs = selectGetKursJb(ldt_tgl_book,"J");
//                        if(ldecKurs==null){
//                            err.reject("","Kurs tgl "+defaultDateFormat.format(ldt_tgl_book)+" (dd/mm/yyyy) tidak ada");
//                            return false;
//                        }
//                    }
//                    //(12 May 2014) Deddy - Ditutup karena memakai seq dari Oracle lgsg.
////                  String sequence=selectGetCounter(9,lsCab);
////                  updateCounter(sequence,9,lsCab);
////                  DecimalFormat f10=new DecimalFormat("0000000000");
////                  lsPayId=lsCab+f10.format(Integer.parseInt(sequence));
//                    lsPayId= selectSeqPaymentId();
//                    insertMstPayment(lsPayId,ldecKurs,depPremi);
//                    insertMstTagPayment(spaj,lsPayId,new Integer(1),new Integer(1),depPremi);
//                }
//            }
//        }
        //
        if(lbRet){
            if(liPmode!=0){
               /* if(logger.isDebugEnabled())logger.debug("ldtEnddate= "+defaultDateFormat.format(ldtEndDate));
               */ 
                Date nextBill=FormatDate.add(ldtEndDate,Calendar.DATE,1);
              /*  if(logger.isDebugEnabled())logger.debug("ldtnext bill= "+defaultDateFormat.format(nextBill));
              */  updateMstPolicyMspoNextBill(spaj,new Integer(1),nextBill, mapper);
            }   
        }
        
        return lbRet;
    }
    
    public Map selectMstInsuredBegDateEndDate(String spaj,Integer insured, CommonDao mapper){
        Map param=new HashMap();
        param.put("reg_spaj",spaj);
        param.put("mste_insured_no",insured);
        return mapper.selectMstInsuredBegEndDate(param);
        
    }
    
    public Double selectMstProductInsuredPremiDiscount(String spaj, Integer insured, Integer active, CommonDao mapper){
        Map param=new HashMap();
        param.put("reg_spaj",spaj);
        param.put("mste_insured_no",insured);
        param.put("mspr_active",active);
        return mapper.selectMstInsuredPremiDiscount(param);
    }
    
    public Date selectAddMonths(String tanggal,Integer month){
        Date date = null;
        try{
            Date tanggal2 = (Date) defaultDateFormat.parse(tanggal);
            date = DateUtil.selectAddDate(tanggal2, "mm", true, month.intValue());
        }catch(Exception e) {
           // logger.error("ERROR :", e);
        }
        return date;
    }
    
    public Integer selectCountMstBill(String reg_spaj,Integer msbi_tahun_ke , Integer msbi_premi_ke, CommonDao mapper)throws DataAccessException{
        Map map = new HashMap();
        map.put("reg_spaj",reg_spaj);
        map.put("msbi_tahun_ke", msbi_tahun_ke);
        map.put("msbi_premi_ke", msbi_premi_ke);
        
        return mapper.selectCountMstBill(map);
    }
    
    public void insertMstBilling(String spaj,Integer liThKe,Integer liPremiKe,Date ldtBegDate,Date ldtEndDate,
            Date ldtDueDate,String lkuId,Double ldecBPolis,Double hcrPolicyCost, Double ttlCardCost,Double stamp,
            Double ldecPremi,Integer liPaid,Integer msbiActive,Integer msbiPrint,Integer addBill,String lusId,Integer lspdId,
            String lcaId,String lwkId,String lsrgId,Integer flagTopup,Integer msbiThBak,Integer msbiPremiBak,Integer msbiPersenPaid,Date tglDebet, CommonDao mapper)throws DataAccessException{
        Map insBill=new HashMap();
        insBill.put("reg_spaj",spaj);
        insBill.put("msbi_tahun_ke",liThKe);
        insBill.put("msbi_premi_ke",liPremiKe);
        insBill.put("msbi_beg_date",ldtBegDate);
        insBill.put("msbi_end_date",ldtEndDate);
        
        insBill.put("msbi_due_date",ldtDueDate);
        insBill.put("msbi_aktif_date",ldtBegDate);
        insBill.put("lku_id",lkuId);
        insBill.put("msbi_policy_cost",ldecBPolis);
        insBill.put("msbi_hcr_policy_cost",hcrPolicyCost);
        
        insBill.put("msbi_ttl_card_cost",ttlCardCost);
        insBill.put("msbi_stamp",stamp);
        insBill.put("msbi_remain",ldecPremi);
        insBill.put("msbi_paid",liPaid);
        insBill.put("msbi_active",msbiActive);
        
        insBill.put("msbi_print",msbiPrint);
        insBill.put("msbi_add_bill",addBill);
        insBill.put("lus_id",lusId);
        insBill.put("lspd_id",lspdId);
        insBill.put("lca_id",lcaId);
        insBill.put("lwk_id",lwkId);
        insBill.put("lsrg_id",lsrgId);
        insBill.put("msbi_flag_topup",flagTopup);
        insBill.put("msbi_th_bak", msbiThBak);
        insBill.put("msbi_premi_bak", msbiPremiBak);
        insBill.put("msbi_persen_paid", msbiPersenPaid);
        insBill.put("tgl_debet",tglDebet);
        mapper.insertMstBilling(insBill);
    }

    public void insertMstDetBilling(String spaj, CommonDao mapper){
        Map insDetBill=new HashMap();
        insDetBill.put("reg_spaj",spaj);
        mapper.insertMstDetBilling(insDetBill);
    }
    
    private void updateMstPolicyMspoNextBill(String spaj, Integer lstbId, Date nextBill, CommonDao mapper) {
        Map dw_1=new HashMap();
        dw_1.put("mspo_next_bill",nextBill);
        dw_1.put("lstb_id",lstbId);
        dw_1.put("reg_spaj",spaj);
        mapper.updateMstPolicyMspoNextBill(dw_1);
    }
    
    public int prosesAkseptasiPas(Akseptasi akseptasi,int thn,int bln,String desc,   CommonDao mapper, CommonDao mapperEspaj)throws Exception{
        //TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        
        int liAktif=0;
        int setNopol = 0;
        
        if(akseptasi.getProses().equals("1")){
            
            if(akseptasi.getLiAksep()==5 || akseptasi.getLiAksep()==10)
                liAktif=1;
            
            //
            if(akseptasi.getNoPolis()==null) {
                setNopol=wf_set_nopol(akseptasi, 2, mapper);
            }
            
            if(akseptasi.getLiAksep()==5 || akseptasi.getLiAksep()==10){//akseptasi
                if(setNopol>0){//rollback dan tampilkan message
//                    TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//                    return setNopol;
                    throw new Exception("Error getting policy number from mst_cnt_polis");
                }
                
                updateMstInsuredStatus(akseptasi.getSpaj(),akseptasi.getInsuredNo(),
                        akseptasi.getLiAksep(),liAktif,akseptasi.getCurrentUser().getLus_id(),new Integer(1), mapper);
                if(akseptasi.getLiAksep()==5) saveMstTransHistory(akseptasi.getSpaj(), "tgl_akseptasi_polis", null, null, null, mapperEspaj);
                if(akseptasi.getLiAksep()==10) saveMstTransHistory(akseptasi.getSpaj(), "tgl_special_accept", null, null, null, mapperEspaj);
                String va = mapper.selectVirtualAccountSpaj(akseptasi.getSpaj());
                if (va==null){
                updateVirtualAccountBySpaj(akseptasi.getSpaj(), mapper); }
            }
        }
        //TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        return 0;
    }
    
    public int wf_set_nopol(Akseptasi akseptasi, int lspd_tujuan, CommonDao mapper) {
        String lsNopol;
        String mspo_policy_no_manual = mapper.selectPolicyNoFromSpajManualMstTempDMTM(akseptasi.getSpaj());
        if(mspo_policy_no_manual==null || mspo_policy_no_manual.equals("")){
            lsNopol = f_get_nopolis(akseptasi.getLcaId(),akseptasi.getLsbsId(), mapper);
        }else{
            lsNopol = mspo_policy_no_manual;
        }

        if(lsNopol==null || lsNopol.length()<=0 ){
            return 51; //MESSAGEBOX('iNfo','No Polis is Null!!!')
        }
        
        String lsRegSpaj=mapper.selectMstPolicyRegSpaj(lsNopol);
        if(lsRegSpaj!=null){
            int i=lsNopol.length();
            int ldNo;
            
            if(akseptasi.getLcaId().equals("62")){
                ldNo=Integer.parseInt(lsNopol.substring(i-7,i));
            }else{
                ldNo=Integer.parseInt(lsNopol.substring(i-9,i));
            }
            
            String s_ld_no=f1.format(ldNo);
            
            if(ldNo>0){
                ldNo++;
                lsNopol = akseptasi.getLcaId()+ f3.format(akseptasi.getLsbsId())+ ldNo;
                
                lsRegSpaj = mapper.selectMstPolicyRegSpaj(lsNopol);
                if(lsRegSpaj!=null){
                    return 52; //MessageBox( 'Pesan', 'Nomor Polis Kembar, Coba tranfer ulang...')
                }else{
                    updateMstCntPolicy(akseptasi.getLsbsId(),akseptasi.getLcaId(),ldNo, mapper);
                }
            }
        }
        
        String lsNopolFormated=FormatString.nomorPolis(lsNopol);
        updateMstPolicyFormated(akseptasi.getSpaj(),lsNopol,lsNopolFormated, mapper);
        // update no blanko dan statusnya
        //update no blanko
        
        Integer liRekrutan=mapper.selectMstDetRekruter(akseptasi.getMsagId());
        if(liRekrutan!=null)
            if(liRekrutan>0)
                updateMstPolicyMspoPreExixting(akseptasi.getSpaj(),new Integer(1), lspd_tujuan,new Integer(1), mapper);
        
        akseptasi.setNoPolis(lsNopol);
        
        return 0;
    }
    
    public String f_get_nopolis(String lcaId,Integer lsbsId, CommonDao mapper){
        Long ld_no = null, ld_max = null;
        long ll_th_now, ll_ctl = 0, ll_th = 0; 
        String  ls_nopol=null ;
        boolean baru;//,up_mst_cnt_polis=false,ins_mst_cnt_polis=false; 
        String s_ld_no="",s_as_cab="",s_th_now;
        String bisnisId;
        int i_ld_no=0,i_as_cab=0;
        baru      = false;
        Date tglNow;
        DecimalFormat fBisnis= new DecimalFormat ("000");
        bisnisId=fBisnis.format(lsbsId.intValue());
        //tglNow=selectSysdate(new Integer(1));
        tglNow=DateUtil.selectSysdateTrunc();
        ll_th_now = tglNow.getYear()+1900;
//      if(logger.isDebugEnabled())logger.debug("ll_th_now ="+ll_th_now);
        //
        Map cnt=selectMstCntPolis(lcaId,lsbsId, mapper);
        
        if(cnt==null){
            baru=true;
            ld_no=new Long(0);
            ld_max=new Long(0);
        }else{
            if(cnt.get("MSCNP_VALUE")==null)
                ld_no=null;
            else
                ld_no=Long.valueOf(cnt.get("MSCNP_VALUE").toString());
            //
            if(cnt.get("MSCNP_MAX")==null)
                ld_max=null;
            else
                ld_max=Long.valueOf(cnt.get("MSCNP_MAX").toString());
            
            if(ld_max.longValue() <= 0){ 
                ls_nopol=null;
                return ls_nopol;
            }else{ 
                if(ld_no==null || ld_max==null){
                    ls_nopol=null;
                    return ls_nopol;
                }
            }
            

        }
        //
        if(ld_no.longValue() >= ld_max.longValue())
            ld_no = new Long(0);
        //
        if(ld_no.longValue() == 0){
            if(lcaId.equals("62")){
                ll_th = Long.parseLong(Long.toString(ll_th_now).substring(2, 4)) ;
            }else{
                ll_th = ll_th_now;
            }
            ll_ctl = 1;
        }else{
            s_ld_no=String.valueOf(ld_no);
            if (s_ld_no.length()== 9 ){
                i_ld_no=s_ld_no.length();
                if(lcaId.equals("62")){
                    s_th_now = Long.toString(ll_th_now);
                    ll_th=Long.parseLong(s_ld_no.substring(0,2));
                    ll_ctl = Long.parseLong(s_ld_no.substring(i_ld_no-7,i_ld_no));
                    
                    ll_ctl++;
                    if(!s_ld_no.substring(0,2).equals(s_th_now.substring(2,4))){
                        ll_th = Long.parseLong(s_th_now.substring(2,4));
                        ll_ctl = 1;
                    }
                }else{
                    ll_th=Long.parseLong(s_ld_no.substring(0,4));
                    ll_ctl = Long.parseLong(s_ld_no.substring(i_ld_no-5,i_ld_no));
                    
                    ll_ctl++;
                    if(ll_th != ll_th_now){
                        ll_th = ll_th_now;
                        ll_ctl = 1;
                    }
                }
                
                
            }
        }
        
        if(lcaId.equals("62")){
            ld_no = new Long((ll_th * 10000000) + ll_ctl);
        }else{
            ld_no = new Long((ll_th * 100000) + ll_ctl);
        }
        
        //
        s_as_cab=lcaId.trim();
        i_as_cab=s_as_cab.length();
        lcaId=s_as_cab.substring(i_as_cab-2,i_as_cab);
        
        if(baru){//insert
            insertMstCntPolis(lcaId,lsbsId,ld_no, mapper);
            
        }else{//update
            updateMstCntPolis(lcaId,lsbsId,ld_no);
        }
        //
        NumberFormat fmt = new DecimalFormat ("#");
        s_ld_no=fmt.format(ld_no);
        
        ls_nopol = lcaId+ bisnisId + ld_no;
        
        return ls_nopol;
    }
    
    public Map selectMstCntPolis(String lcaId,Integer lsbsId, CommonDao mapper) throws DataAccessException{
        Map param=new HashMap();
        param.put("as_cab",lcaId);
        param.put("as_bisnis",lsbsId);
        return mapper.selectMstCntPolis(param);
    }
    
    public void insertMstCntPolis(String lcaId, Integer lsbsId, Long ldNo, CommonDao mapper) throws DataAccessException{
        Map insParam=new HashMap();
        insParam.put("as_cab",lcaId);
        insParam.put("as_bisnis",lsbsId);
        insParam.put("ld_no",ldNo);
        mapper.insertMstCntPolis(insParam);
    }
    
    public void updateMstCntPolis(String lcaId,Integer lsbsId,Long ldNo) throws DataAccessException{
        Map upParam=new HashMap();
        upParam.put("ld_no",ldNo);
        upParam.put("as_cab",lcaId);
        upParam.put("as_bisnis",lsbsId);
    }
    
    public void updateMstCntPolicy(Integer lsbsId,String lcaId,int ldNo, CommonDao mapper){
        Map up=new HashMap();
        up.put("as_bisnis",lsbsId);
        up.put("as_cab",lcaId);
        up.put("ld_no",new Integer(ldNo));
        mapper.updateMstCntPolis(up);
    }
    
    public void updateMstPolicyFormated(String spaj,String lsNopol,String lsNopolFormated, CommonDao mapper){
        Map up=new HashMap();
        up.put("txtnospaj",spaj);
        up.put("ls_nopol",lsNopol);
        up.put("ls_nopol_formated",lsNopolFormated);
        mapper.updateMstPolicyNopolis(up);
    }
    
    public void updateMstPolicyMspoPreExixting(String spaj,Integer value,Integer lspdId,Integer lstbId, CommonDao mapper){
        Map map=new HashMap();
        map.put("txtnospaj",spaj);
        map.put("value",value);
        map.put("lspdId",lspdId);
        map.put("lstbId",lstbId);
        mapper.updateMstPolicyMspoPreExisting(map);
    }
    
    public void updateMstInsuredStatus(String spaj,Integer insuredNo,Integer liAksep,Integer liAktif,String lusId,Integer flagTgl, CommonDao mapper){
        Map up=new HashMap();
        up.put("txtnospaj",spaj);
        up.put("txtinsured_no",insuredNo);
        up.put("li_aksep",liAksep);
        up.put("li_aktif",liAktif);
        up.put("lusId",lusId);
        up.put("flagTgl",flagTgl);
        mapper.updateMstInsuredStatus(up);
    }
    
    public void updateVirtualAccountBySpaj(String reg_spaj, CommonDao mapper) throws DataAccessException{
        //yg diupdate hanya yg :
        //1. bukan USD
        //2. bukan bancass
        //3. bukan syariah
        //4. pembayaran bukan sekaligus
        //5. bukan autodebet
        Map data        = mapper.selectDataVirtualAccount(reg_spaj);
        int lscb_id     = ((BigDecimal) data.get("LSCB_ID")).intValue();
        int flag_cc     = ((BigDecimal) data.get("MSTE_FLAG_CC")).intValue();
        String lku_id   = (String) data.get("LKU_ID");
        String lca_id   = (String) data.get("LCA_ID");
        String no_virtual = "8006";//default 4 digit depan produk konvensional
        Map tampung = new HashMap();
        tampung.put("reg_spaj", reg_spaj);
        if(lku_id.equals("01") &&
                //!lca_id.equals("09") &&
                lscb_id != 0 
//              && flag_cc == 0){ request Himmia (2 Dec 2011) - untuk No Virtual Account kondisi autodebet dihilangkan
                ){
            if(!isProductSyariah(mapper.selectBusinessId(reg_spaj), mapper.selectBusinessNumber(reg_spaj).toString(), mapper)){
                tampung.put("no_virtual", no_virtual);
                mapper.updateVirtualAccountBySpaj(tampung);
            }else{
                no_virtual ="8076";
                tampung.put("no_virtual", no_virtual);
                mapper.updateVirtualAccountBySpaj(tampung);
            }
        }
    }
    
    public void updateKartuPas2(String no_kartu, Integer flag_active, String reg_spaj, CommonDao mapper){
        Map map = new HashMap();
        map.put("no_kartu", no_kartu);
        map.put("flag_active", flag_active);
        map.put("reg_spaj", reg_spaj);
        mapper.updateKartuPas2(map);
    }
    
    public void updateDetVa(String no_va,String lus_id, String spaj, CommonDao mapper) {
        Map map = new HashMap();
        map.put("no_va", no_va);        
        map.put("lus_id", lus_id);
        map.put("spaj",spaj);
        mapper.updateMstDetVa(map);
    }
    
    public void insertMstPositionSpajPas(String lus_id, String msps_desc, String reg_id, Integer addSecond, CommonDao mapper) throws Exception{
        Map params = new HashMap();
        params.put("lus_id", lus_id);
        params.put("msps_desc", msps_desc);
        params.put("reg_id", reg_id);
        params.put("addSecond", addSecond);
        mapper.insertMstPositionSpajPas(params);
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
//            e.printStackTrace();
        } finally {
        }
        
        return result;
    }
	

	
	public HashMap<String, Object> selectDataProposal(String no_temp) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        
        try {
            result = commonDao.selectDataProposal(no_temp);
        } catch (Exception e) {
//            e.printStackTrace();
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
//          e.printStackTrace();
        } finally {
        }
        
        return result;
    }
    
    public List<PesertaPlus_mix> selectPesertaTemp(String no_temp) {
       List<PesertaPlus_mix> result = null;
            
        try {
            result = commonDao.selectPesertaTemp(no_temp);
        } catch(Exception e) {
//          e.printStackTrace();
        } finally {
        }
        
        return result;
    }
    
    public List<Benefeciary> select_benef_temp(String no_temp) {
        List<Benefeciary> result = null;
            
        try {
            result = commonDao.select_benef_temp(no_temp);
        } catch(Exception e) {
//          e.printStackTrace();
        } finally {
         }
        
        return result;
    }
    
      public String selectIdLstPekerjaan(String namaPekerjaan) {
            String result = null;
                    
            try {
                result = commonDao.selectIdLstPekerjaan( namaPekerjaan );
            } catch (Exception e) {
//              e.printStackTrace();
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
      
//      public Long selectCounter(String strBranch) {
//            SqlSession sqlSession = sqlSessionFactory.openSession();
//            Long result = null;
//            HashMap<String, Object> params = new HashMap<String, Object>();
//            params.put("kodecbg", strBranch);           
//            try {
//                MAP_espaj mapper = sqlSession.getMapper(MAP_espaj.class);
//                result = mapper.selectCounter( params );
//            } catch (Exception e) {
//                e.printStackTrace();
//                result = null;
//            } finally {
//                sqlSession.close();
//            }           
//            return result;
//        }
      
       public void updateMstCounter(Long intIDCounter, String strBranch) throws Exception{
           HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("intIDCounter", intIDCounter);
            params.put("strBranch", strBranch);             
            try {
                commonDao.commonupdateMstCounter(params);
             }catch(Exception e){
//              if(sqlSession==null)
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
//              if(sqlSession==null)
               e.printStackTrace();
            }finally {
           }
        }
       
       public String selectKeteranganKerja(String lsp_id) {
           String result = null;       
            try {
               result = commonDao.selectKeteranganKerja( lsp_id );
            } catch (Exception e) {
//              e.printStackTrace();
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
//              e.printStackTrace();
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
       //     mapper.insertMstAgentProd( agen );          
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
//              e.printStackTrace();
            } finally {
            }
            
            return result;
        }
     
    public void insertMstAgentBa(Agen agen, CommonDao mapper) throws Exception {        
      //  mapper.insertMstAgentBa( agen );
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
      //      mapper.insertMst_position_no_spaj_pb(params);           
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
       // mapper.insertMst_sts_client(param);         
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
//              e.printStackTrace();
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
//                  insert mst rek client dan mst rek hist
                    proc_save_rek_client(edit,strTmpSPAJ, mapper);
                    
                }else{
                    if (v_pil_invest.equalsIgnoreCase("2"))
                    {
//                      insert mst rek client dan mst rek hist
                        proc_save_rek_client(edit,strTmpSPAJ, mapper );
                    }else{
                        String v_strAcctHolder1 = edit.getRekening_client().getMrc_no_ac().toUpperCase();
                        String v_bank1 = edit.getRekening_client().getLsbp_id();
                        String atasnama1 = edit.getRekening_client().getMrc_nama().toUpperCase();
                        String cabang_bank = edit.getRekening_client().getMrc_cabang().toUpperCase();
                        String kota_rek = edit.getRekening_client().getMrc_kota().toUpperCase();
                        if (!v_strAcctHolder1.equalsIgnoreCase("") || !v_bank1.equalsIgnoreCase("") || !atasnama1.equalsIgnoreCase("") || !cabang_bank.equalsIgnoreCase("") || !kota_rek.equalsIgnoreCase(""))
                        {
//                          insert mst rek client dan mst rek hist
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
            //insertMstRekClientHist(edit.getRekening_client(), mapper);
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
//              System.out.println("update mst billing");
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
//            edit.getPersonal().setMcl_id(edit.getPemegang().getMcl_id());
//            edit.getPersonal().setFlag_ws(0);
//            edit.getPersonal().setLca_id(edit.getAgen().getLca_id());
//            edit.getPersonal().setLwk_id(edit.getAgen().getLwk_id());
//            edit.getPersonal().setLsrg_id(edit.getAgen().getLsrg_id());
//            edit.getPersonal().setLsrg_nama(edit.getAgen().getLsrg_nama());
//            edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
//            edit.getPersonal().setMcl_first(edit.getPemegang().getMcl_first());
            //List<DropDown> gelar = ((List<DropDown>)query("selectGelar", 1));
            Map<String, String> params = new HashMap<String, String>();
                    
//            List<DropDownMenu> bidangUsaha = (List<DropDownMenu>) selectBidangUsaha("0", mapper);
//            if(bidangUsaha != null){
//                for(int i = 0 ; i < bidangUsaha.size() ; i++){
//                    if((bidangUsaha.get(i).getValue().toUpperCase()).equals(edit.getPemegang().getMkl_industri())){
//                        edit.getPersonal().setLju_id(Integer.parseInt(bidangUsaha.get(i).getKey()));
//                        i = bidangUsaha.size();
//                    }
//                }
//            }
//            
//            edit.getPersonal().setMpt_usaha_desc(edit.getPemegang().getMkl_industri());
//            edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
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
//                  System.out.println("update mst address pp");                
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
//                    edit.getPemegang().setMspo_flat(1);
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
        
        public ConcurrentHashMap<String,Agen> saveInMemory(Map mapAgentCodeAO) throws Exception{
        	ConcurrentHashMap<String,Agen> table = new ConcurrentHashMap<String, Agen>();
        	String code_lvl3 = (String) mapAgentCodeAO.get("AGENT_3");
            String code_lvl2 = (String) mapAgentCodeAO.get("AGENT_2");
            String code_lvl1 = (String) mapAgentCodeAO.get("AGENT_1");
            String code_lvl0 = (String) mapAgentCodeAO.get("AGENT_4");
            
            Map param = new HashMap();                          
            param.put("strTmpAgentId", code_lvl3);
            Agen data =  selectMstAgent( param, commonDao);
            table.put(code_lvl3, data);
            param = new HashMap(); 
            param.put("strTmpAgentId", code_lvl2);
            data =  selectMstAgent( param, commonDao);
            table.put(code_lvl2, data);
            param = new HashMap(); 
            param.put("strTmpAgentId", code_lvl1);
            data =  selectMstAgent( param, commonDao);
            table.put(code_lvl1, data);
            param = new HashMap(); 
            param.put("strTmpAgentId", code_lvl0);
            data =  selectMstAgent( param, commonDao);
            table.put(code_lvl0, data);
        	return table;
        }
 	   
        
        public void proc_save_agen(Cmdeditbac edit, String strTmpSPAJ ,String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao mapper) throws Exception
        {       
            
            Integer intBII =new Integer(0);
            Integer v_intPribadi = edit.getPemegang().getMspo_pribadi();
            if(v_intPribadi == null)v_intPribadi = 0;
            String v_kode_ao = edit.getPemegang().getMspo_ao().toUpperCase();
            Integer v_intFollowUp = edit.getPemegang().getMspo_follow_up();
             Map mapAgentCodeAO = selectAgentCodeAO(v_strAgentId);
             ConcurrentHashMap<String,Agen> table = saveInMemory(mapAgentCodeAO);
             
           // System.out.println("ada ngak nih"+mapAgentCodeAO.size());
                    
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
//                          insert mst agent comm
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
//                                      insert mst agent comm
                                        proc_save_agen_comm(edit,arrAgentartha1, strTmpSPAJ ,new Integer(i), mapper);
                                        //System.out.println("insert mst_agent_comm" + i);

                                    }else{
                                        if (arrAgentartha1[i].getComm_id().intValue() == 3 && ( strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 2))
                                        {
//                                          insert mst agent comm
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
                    
                
                arrAgentRec1 = proc_process_agentaoTwo(v_strAgentId, mapAgentCodeAO, mapper,table);
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
        
        
        
        private Agentrec[] proc_process_agentaoTwo(String v_strAgentId,Map agentCodeAo, CommonDao mapper,ConcurrentHashMap<String,Agen> table) throws Exception{
        	
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
            	 Map mapAgentCodeAO = agentCodeAo;	
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
                             //Agen data2 =  selectMstAgent( param, mapper);
                             Agen data2 = table.get(strTmpAgentId);
                             
                                                     
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
                
                insertMstAgentProd( edit.getAgen(), mapper );
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

//          ------------------------------------------------------------
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
//                  if(end_payRider[i].after(edit.getDatausulan().getMspr_end_pay())){
//                      end_payRider[i]=edit.getDatausulan().getMspr_end_pay();
//                  }
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
//                  if((v_intRiderId[i]==820 && v_intRiderNo[i] >105) || (v_intRiderId[i]==826 && v_intRiderNo[i] >10) || (v_intRiderId[i]==823 && v_intRiderNo[i] >15) || (v_intRiderId[i]==825 && v_intRiderNo[i] >15) || (v_intRiderId[i]==819 && ((v_intRiderNo[i] >=20 && v_intRiderNo[i] <=140) || (v_intRiderNo[i] >160 && v_intRiderNo[i] <=280) || (v_intRiderNo[i] >300 && v_intRiderNo[i] <=380) || (v_intRiderNo[i] >390 && v_intRiderNo[i] <=430) || (v_intRiderNo[i] >450 && v_intRiderNo[i] <=530))) ){
//                          flagBikinPusing = 1;
//                  }
                    
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
//                          insert peserta HCP Family (utama)
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
                                
//                              if (status.equalsIgnoreCase("input") || (flag_rider_hcp.intValue() == 0))
//                              {
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                              }
                            }
                        }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
                            Simas simas = new Simas();
                            simas.setLsbs_id(v_intRiderId[i]);
                            simas.setLsdbs_number(v_intRiderNo[i]);
                            simas.setDiscount(new Double(0));
                            simas.setPremi(premi_rider[i]);
//                          if(edit.getDatausulan().getLsbs_id().intValue()==183 || edit.getDatausulan().getLsbs_id().intValue()==189 || edit.getDatausulan().getLsbs_id().intValue()==193 || edit.getDatausulan().getLsbs_id().intValue()==195){
//                              if (status.equalsIgnoreCase("input")){
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                              }
//                          }else{
//                              if(flagBikinPusing==0){
//                                  if (status.equalsIgnoreCase("input")){
//                                      proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                                  }else if (status.equalsIgnoreCase("edit")){
//                                      proc_save_peserta(edit,strTmpSPAJ,simas,"edit");
//                                      flag_ekasehat = 1;
//                                  }
//                              }
//                          }
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
//                		if(edit.getDatausulan().getLscb_id()==6){
//                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.1);
//                		}else if(edit.getDatausulan().getLscb_id()==1){
//                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.27);
//                		}else if(edit.getDatausulan().getLscb_id()==2){
//                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium() * 0.525);
//                		}else{
//                			rd.setMspr_premium(edit.getDatausulan().getMspr_premium());
//                		}
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
//                  if(end_payRider[i].after(edit.getDatausulan().getMspr_end_pay())){
//                      end_payRider[i]=edit.getDatausulan().getMspr_end_pay();
//                  }
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
//                  if((v_intRiderId[i]==820 && v_intRiderNo[i] >105) || (v_intRiderId[i]==826 && v_intRiderNo[i] >10) || (v_intRiderId[i]==823 && v_intRiderNo[i] >15) || (v_intRiderId[i]==825 && v_intRiderNo[i] >15) || (v_intRiderId[i]==819 && ((v_intRiderNo[i] >=20 && v_intRiderNo[i] <=140) || (v_intRiderNo[i] >160 && v_intRiderNo[i] <=280) || (v_intRiderNo[i] >300 && v_intRiderNo[i] <=380) || (v_intRiderNo[i] >390 && v_intRiderNo[i] <=430) || (v_intRiderNo[i] >450 && v_intRiderNo[i] <=530))) ){
//                          flagBikinPusing = 1;
//                  }
                    
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
//                          insert peserta HCP Family (utama)
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
                                
//                              if (status.equalsIgnoreCase("input") || (flag_rider_hcp.intValue() == 0))
//                              {
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                              }
                            }
                        }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
                            Simas simas = new Simas();
                            simas.setLsbs_id(v_intRiderId[i]);
                            simas.setLsdbs_number(v_intRiderNo[i]);
                            simas.setDiscount(new Double(0));
                            simas.setPremi(premi_rider[i]);
//                          if(edit.getDatausulan().getLsbs_id().intValue()==183 || edit.getDatausulan().getLsbs_id().intValue()==189 || edit.getDatausulan().getLsbs_id().intValue()==193 || edit.getDatausulan().getLsbs_id().intValue()==195){
//                              if (status.equalsIgnoreCase("input")){
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                              }
//                          }else{
//                              if(flagBikinPusing==0){
//                                  if (status.equalsIgnoreCase("input")){
//                                      proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                                  }else if (status.equalsIgnoreCase("edit")){
//                                      proc_save_peserta(edit,strTmpSPAJ,simas,"edit");
//                                      flag_ekasehat = 1;
//                                  }
//                              }
//                          }
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
//          Excellink Secure $  
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
//                              plus2.setLsre_id(edit.getPemegang().getLsre_id());
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
            
//                  if(edit.getDatausulan().isPsave || edit.getPowersave().getMsl_spaj_lama()!=null){
//                      edit.getDatausulan().setKopiSPAJ(edit.getPowersave().getMsl_spaj_lama());
//                  }else{
//                      edit.getDatausulan().setKopiSPAJ("");
//                  }
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
//      if (flag_worksite.intValue()==1 && v_intAutoDebet.intValue() == 3)
//      {
            lssa_id = new Integer(5);
//      }else{
//          lssa_id = new Integer(1);
//      }
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
        
        Integer exist = commonDao.selectMstTransHist(reg_spaj);
        
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
//        if(selectPositionSpaj( edit.getPemegang().getReg_spaj(), mapper ) != 3 ){
            String result_akseptasi = prosesAkseptasiSpeedy(edit.getPemegang().getReg_spaj(),  edit.getPemegang().getLus_id().toString(), mapper);
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
//        }
        
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
