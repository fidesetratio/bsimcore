package com.app.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.dao.CommonDao;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Agentrec;
import com.app.model.gadget.prod.Cmdeditbac;
import com.app.model.gadget.prod.Rekening_client;
import com.app.model.gadget.prod.Tertanggung;
import com.app.model.gadget.prod.User;
import com.app.utils.CommonUtil;
import com.app.utils.FormatString;

@Service
public class SubmitServices {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	private static Logger logger = Logger.getLogger(SubmitServices.class);
	

	
	public Cmdeditbac save(Cmdeditbac edit) throws Exception{
		CommonDao commonDao=sqlSession.getMapper(CommonDao.class);
		TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        String counterSpaj = null;
        String counterTertanggungClient=null;
        String counterHolderClient = null;
        User currentUser = new User();
        try {
        	String v_strAgentId = edit.getAgen().getMsag_id().toUpperCase();
        	HashMap params = new HashMap();
        	params.put("kodeagen", v_strAgentId);
        	if(edit.getCurrentUser()  == null){
        		User user = new User();
        		user.setLus_id("0");
        		edit.setCurrentUser(user);
        	}
        	Map agenData = commonDao.selectRegionalAgen(params);
        	Integer lssa_id = new Integer(0);
            Integer kode_flag = edit.getDatausulan().getKode_flag();
            Integer v_intActionBy = edit.getPemegang().getLus_id();
            Date tanggal = new Date();
            String no_pb=edit.getPemegang().getNo_pb();
            Date ldt_endpay1=null;
            Integer flag_jenis_plan = edit.getDatausulan().getFlag_jenis_plan();
            
            
            edit.getDatausulan().setLssa_id(lssa_id);
            if(edit.getPemegang().getLsre_id() == null){
            	edit.getPemegang().setLsre_id(new Integer(1));
            };
            if(agenData != null){
        		 String strBranch = (String)agenData.get("STRBRANCH");
        		 String strWilayah = (String)agenData.get("STRWILAYAH");
        		 String strRegion = (String)agenData.get("STRREGION");
        		 String strAgentBranch = (String)agenData.get("STRAGENTBRANCH");
        		 edit.getAgen().setLca_id(strBranch);
                 edit.getAgen().setLwk_id(strWilayah);
                 edit.getAgen().setLsrg_id(strRegion);
                 edit.getAgen().setKode_regional(strAgentBranch);
        	}
        	counterSpaj = commonDao.selectSeqNoSpaj(edit.getAgen().getLca_id());
        	counterTertanggungClient = commonDao.selectSequenceClientID();
        	counterHolderClient = counterTertanggungClient;
        	save_tertanggung(edit,counterTertanggungClient,commonDao, new Date());
        
        	
        	if(edit.getPemegang().getLsre_id() > 1 ){
        		counterHolderClient = commonDao.selectSequenceClientID();
        		save_pemegang(edit,counterHolderClient,commonDao, new Date());
        	};
        	save_policy(currentUser,edit,commonDao, counterHolderClient,counterSpaj);
        	save_mst_insured(edit, counterHolderClient, counterSpaj, commonDao);
        	save_agen(edit, counterSpaj, commonDao);
        	save_addr_bill(edit,counterSpaj, commonDao);
        	save_rekening(edit,counterSpaj,kode_flag, commonDao);
        	save_position_spaj(v_intActionBy.toString(), "INPUT SPAJ", counterSpaj, 0, commonDao);
        	saveMstInsuredTglAdmin(counterSpaj, 1, tanggal, 0, commonDao);
        	saveMstTransHistory(counterSpaj, "tgl_input_spaj_admin", tanggal, null, null, commonDao);
        	
        	saveMst_position_no_spaj_pb(counterSpaj, currentUser.getLus_id(),4, 10, "NO PB: "+no_pb,2, commonDao);
        	saveMst_sts_client(counterTertanggungClient,commonDao);	
        	save_product_insured(edit,counterSpaj,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser, commonDao);
        	
       
        	transactionManager.commit(txStatus);
        	
        	if(!CommonUtil.isEmpty(counterSpaj)){
        		edit.getPemegang().setReg_spaj(counterSpaj);
        	}
        	
        
        } catch (Exception e) {
        	e.printStackTrace();
            transactionManager.rollback(txStatus);
            throw e;
        }
      
        return edit;
	}
	
	
    private void save_product_insured(Cmdeditbac edit, String counterSpaj,
			Integer v_intActionBy, Integer flag_jenis_plan, Date ldt_endpay1,
			User currentUser, CommonDao commonDao) {
		// TODO Auto-generated method stub
		
    	
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
        
        
        Map param28=new HashMap();
        param28.put("strTmpSPAJ",counterSpaj);   
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
        inserMst_product_insured1( param28, commonDao );
	}


	private void inserMst_product_insured1(Map param28, CommonDao commonDao) {
		commonDao.inserMst_product_insured1(param28);             
	}


	public void saveMst_sts_client(String mcl_id, CommonDao commonDao) {
        Map param=new HashMap();    
        param.put("strInsClientID", mcl_id);        
        commonDao.insertMst_sts_client(param);         
    }
	
	
    private void saveMst_position_no_spaj_pb(String spaj, String lus_id, int lspd, Integer lssp, String desc,Integer count, CommonDao commonDao) {
		// TODO Auto-generated method stub
    	 Map params = new HashMap();
         params.put("strTmpSPAJ", spaj);
         params.put("lus_id", lus_id);
         params.put("lspd", new Integer(lspd));
         params.put("lssp", lssp);
         params.put("desc", desc);
         params.put("tgl", "SYSDATE+0.0000"+count);    
         commonDao.insertMst_position_no_spaj_pb(params); 
	}



	public void saveMstInsuredTglAdmin(String spaj,Integer insuredNo, Date tanggal,Integer show, CommonDao commonDao){
        Map param = new HashMap();
        param.put("spaj", spaj);
        param.put("insuredNo", insuredNo);
        param.put("show", show);
        param.put("tanggal", tanggal);
        commonDao.updateMstInsuredTglAdmin( param );       
}
	private void save_position_spaj(String lus_id, String msps_desc, String reg_spaj, int addSecond, CommonDao commonDao){
		  Map p = new HashMap();
          p.put("lus_id", lus_id);
          p.put("msps_desc", msps_desc);
          p.put("reg_spaj", reg_spaj);
          p.put("addSecond", addSecond);          
          commonDao.insertMstPositionSpaj( p );
	}
	
	private void save_rekening(Cmdeditbac edit,String counterSpaj,Integer kode_flag, CommonDao commonDao){
		Integer kode_account=edit.getDatausulan().getFlag_account();
        Integer v_intAutoDebet = edit.getDatausulan().getMste_flag_cc();
        
        if(kode_account==2){
        	try {
				save_rek_client(edit,counterSpaj, commonDao);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        if(v_intAutoDebet == 1 || v_intAutoDebet == 2){
        	save_account_recur(edit,counterSpaj, commonDao);
        }
        
	}
	
	
	



	private void save_account_recur(Cmdeditbac edit, String counterSpaj,
			CommonDao commonDao) {
		// TODO Auto-generated method stub
		edit.getAccount_recur().setReg_spaj(counterSpaj);
        edit.getAccount_recur().setMar_jenis(edit.getDatausulan().getMste_flag_cc());
        edit.getAccount_recur().setLus_id(edit.getPemegang().getLus_id());
        try {
            insertMstAccountRecur( edit.getAccount_recur(), commonDao);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void insertMstAccountRecur(Account_recur account_recur,
			CommonDao commonDao) {
		// TODO Auto-generated method stub
		commonDao.insertMstAccountRecur( account_recur );  
	}

	private void save_rek_client(Cmdeditbac edit, String counterSpaj,
			CommonDao commonDao) throws Exception {
		// TODO Auto-generated method stub
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
         edit.getRekening_client().setReg_spaj(counterSpaj);
         edit.getRekening_client().setLus_id(edit.getPemegang().getLus_id());
         
         int rowupdated =  updateMstRekClient( edit.getRekening_client(), commonDao);       
         
         if(rowupdated<=0){
        	 insertMstRekClient(edit.getRekening_client(), commonDao);
         };
        	 insertMstRekClientHist(edit.getRekening_client(), commonDao);
      
	}
	
	
	
	
	
	 public void insertMstRekClientHist(Rekening_client rekening_client, CommonDao commonDao) throws Exception {        
		 commonDao.insertMstRekClientHist( rekening_client );       
	 };
 
 
	
	
	 private void insertMstRekClient(Rekening_client rekening_client,CommonDao commonDao) {
		 commonDao.insertMstRekClient(rekening_client);
	}

	public Integer updateMstRekClient(Rekening_client rekening_client, CommonDao commonDao) throws Exception {
	        Integer result = 0;    
	        try {
	            result = commonDao.updateMstRekClient( rekening_client );
	        }catch(Exception e){
	            result = 0;
	            throw e;
	        }
	        return result;
	    }  

	private void save_pemegang(Cmdeditbac edit, String counterHolderClient,
			CommonDao commonDao, Date date) {
		// TODO Auto-generated method stub
		
	}
	 public void save_addr_bill(Cmdeditbac edit,String strTmpSPAJ, CommonDao commonDao){
		 String v_strBillRegion=edit.getAddressbilling().getRegion();
         edit.getAddressbilling().setReg_spaj(strTmpSPAJ);
         if(edit.getAddressbilling().getKota() != null) {
             edit.getAddressbilling().setLska_id(selectKabupaten(edit.getAddressbilling().getKota().toUpperCase().trim(), commonDao ));
         }
         if(v_strBillRegion != null) {
             edit.getAddressbilling().setLca_id(FormatString.rpad("0",(v_strBillRegion.substring(0,2)),2));
             edit.getAddressbilling().setLwk_id(FormatString.rpad("0",(v_strBillRegion.substring(2,4)),2));
             edit.getAddressbilling().setLsrg_id(FormatString.rpad("0",(v_strBillRegion.substring(4,6)),2));
         }
         
         edit.getAddressbilling().setKota(edit.getAddressbilling().getKota_tgh());
         edit.getAddressbilling().setFlag_cc(edit.getDatausulan().getMste_flag_cc());
         int rowupdated = updateMstAddressBilling( edit.getAddressbilling(), commonDao );  
         if (rowupdated <=0)
         {
             insertMstAddressBilling( edit.getAddressbilling(), commonDao );
         }
	 }
	   
	 public void insertMstAddressBilling(AddressBilling addressBilling, CommonDao commonDao) {
		 commonDao.insertMstAddressBilling( addressBilling );       
	 }
	 private Integer selectKabupaten(String nama_wilayah, CommonDao commonDao) 
	    {
	        Integer result = 0;         
	        
	        try{
	          result = commonDao.selectKabupaten( nama_wilayah );
	        }catch(Exception e){
	        	result = 0;
	        }
	        return result;
	 }
	 
	 
	 public Integer updateMstAddressBilling(AddressBilling addressBilling, CommonDao commonDao)  {
	        Integer result = 0;    
	      
	            result = commonDao.updateMstAddressBilling( addressBilling );
	       
	        return result;
	    }   
	 public void save_agen(Cmdeditbac edit, String counterSpaj,CommonDao commonDao)
     {
		 Integer intBII =new Integer(0);
         Integer v_intPribadi = edit.getPemegang().getMspo_pribadi();
         if(v_intPribadi == null)v_intPribadi = 0;
         String v_kode_ao = edit.getPemegang().getMspo_ao().toUpperCase();
         Integer v_intFollowUp = edit.getPemegang().getMspo_follow_up();
         Map mapAgentCodeAO = commonDao.selectAgentCodeAO(v_kode_ao);
         HashMap<String,Agen> mapAgent = new HashMap<String,Agen>();
         
         
         
         String v_strAgentId = edit.getAgen().getMsag_id();
         
         Agentrec[]  arrAgentRec;
         Agentrec[]  arrAgentArtha;
         Agentrec[]  arrAgentartha1;
         Agentrec[]  arrAgentWorksite = null;
         
         Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
         Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
		 	
         arrAgentRec = proc_process_agent(commonDao,v_strAgentId,1,mapAgent);
         arrAgentWorksite = proc_process_agent(commonDao,v_strAgentId,2,null);
         intBII = new Integer(0);
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
             
             if(arrAgentRec[i].getLevel_id()!=4){
                 if(edit.getAgen().getJenis_ref()==null){
                     
                 }
                 else if(edit.getAgen().getJenis_ref()==0){
                     arrAgentRec[i].setComm_id(null);
                 }
             };
             
             proc_save_agen_prod(edit,arrAgentRec,counterSpaj ,new Integer(i),ssbm,lvl_fcd, commonDao);
         }
         
         
         
         if (intBII.intValue() == 1 )
         {
        	 Integer ssbm=new Integer(0);
             Agentrec[]  arrAgentRec1;
             if(!v_kode_ao.equals("")){
            	 arrAgentRec1 = proc_process_agentao(v_kode_ao, commonDao,mapAgentCodeAO,mapAgent);
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
                	  edit.getAgen().setReg_spaj(counterSpaj);
                      edit.getAgen().setMsag_id(arrAgentRec1[i].getAgent_id());
                      edit.getAgen().setLstb_id(arrAgentRec1[i].getBisnis_id());
                      edit.getAgen().setLsle_id(arrAgentRec1[i].getLevel_id());
                      edit.getAgen().setLev_comm(arrAgentRec1[i].getComm_id());
                      edit.getAgen().setFlag_sbm(ssbm);
                      commonDao.insertMstAgentBa(edit.getAgen());
                 }
            	 
            	
                  
                  
             }
             
         }
       edit.getAgen().setMsag_id(v_strAgentId);
       System.out.println("Hello world..");
         
     }
	 
	 
	 private Agentrec[] proc_process_agentao(String v_strAgentId, CommonDao commonDao,Map mapAgentCodeAO,HashMap<String, Agen> map){
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
                     Agen data2 =  null;
                     if(map != null){
                    	 data2 = map.get(strTmpAgentId);
                    	 
                     }else{
                    	 data2 = commonDao.selectMstAgent(param);
                    	 
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
	 private void proc_save_agen_prod(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, Integer flag_sbm, Integer lvl_fcd,CommonDao commonDao){
		 edit.getAgen().setReg_spaj(strTmpSPAJ);
         edit.getAgen().setLstb_id(arrAgentRec[i.intValue()].getBisnis_id());
         edit.getAgen().setMsag_id(arrAgentRec[i.intValue()].getAgent_id());
         if(arrAgentRec[i.intValue()] != null){
        	if(arrAgentRec[i.intValue()].getComm_id() != null){
        		edit.getAgen().setLev_comm(arrAgentRec[i.intValue()].getComm_id());
        	}
        	
         }
         edit.getAgen().setLsle_id(arrAgentRec[i.intValue()].getLevel_id());
         edit.getAgen().setFlag_sbm(flag_sbm);
         edit.getAgen().setLvl_fcd(lvl_fcd);
         commonDao.insertMstAgentProd(edit.getAgen());
         
	 }

	private void save_policy(User currentUser, Cmdeditbac edit,CommonDao commonDao, String strPOClientID,String counterSpaj){
		edit.getPemegang().setMcl_id(strPOClientID);
		edit.getDatausulan().setReg_spaj(counterSpaj);
		edit.getPemegang().setMspo_age(edit.getPemegang().getUsiapp());
		edit.getDatausulan().setLus_id(edit.getPemegang().getLus_id());
		edit.getDatausulan().setLstp_id(edit.getDatausulan().getTipeproduk());
		edit.getPemegang().setMspo_policy_holder(strPOClientID);
		edit.getDatausulan().setMspo_ins_period(edit.getDatausulan().getMspr_ins_period());
        edit.getDatausulan().setMspo_spaj_date(edit.getPemegang().getMspo_spaj_date());
        edit.getPemegang().setMspo_flat(0);
        if(!(edit.getPemegang().getMspo_customer()==null?"":edit.getPemegang().getMspo_customer()).equals("")){
            edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
        }else{
            edit.getPemegang().setMspo_customer(edit.getPemegang().getSumber_id());
        }
        if(edit.getFlag_gmanual_spaj()!=null){
            edit.getPemegang().setMspo_customer("");
        }
        edit.getDatausulan().setMspo_syahriah(0); // konven
        edit.getDatausulan().setMspo_flag_new(1);// paling baru
        
        HashMap params = new HashMap();
        params.put("pemegang",edit.getPemegang());
        params.put("tertanggung",edit.getTertanggung());
        params.put("datausulan",edit.getDatausulan());
        params.put("agen",edit.getAgen());
        
        int rowUpdate = commonDao.updateMstPolicy(params);
        
        if(rowUpdate<=0){
        	commonDao.insertMstPolicy(params);
        };
        
        Integer	flag_simponi=edit.getDatausulan().getIsBungaSimponi();
		Integer	flag_tahapan=edit.getDatausulan().getIsBonusTahapan();
		edit.getPemegang().setReg_spaj(counterSpaj);
        
		if(edit.getPemegang().getMspo_spaj_date() != null) saveMstTransHistory(counterSpaj, "tgl_spaj_asli", edit.getPemegang().getMspo_spaj_date(), null, null, commonDao);
          
		if (flag_simponi.intValue()==1)
        {
			commonDao.updateBungaMstPolicy(edit.getPemegang());
        }
		
		//update bonus  tahapan
        if (flag_tahapan.intValue()==1)
        {
            Double bonus =edit.getPemegang().getBonus_tahapan();
            edit.getPemegang().setMspo_under_table(bonus);
            commonDao.updateBungaMstPolicy(edit.getPemegang());
        }
        
      //bagian ini untuk Bonus Maxi Deposit.
        if((edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==3) || (edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==4) ){
            Double bonus = 5.00;
            edit.getPemegang().setMspo_under_table(bonus);
            commonDao.updateBungaMstPolicy(edit.getPemegang());
        }else if((edit.getDatausulan().getLsbs_id()==114 && edit.getDatausulan().getLsdbs_number()>=2)  ){
            Double bonus = 2.92;
            edit.getPemegang().setMspo_under_table(bonus);
            commonDao.updateBungaMstPolicy(edit.getPemegang());
        }
        
        
		 
	}
	
	
	private void save_mst_insured(Cmdeditbac edit,String strInsClientID,String strTmpSPAJ,CommonDao commonDao){
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
	        lssa_id = new Integer(5);
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
	        if(edit.getPemegang().getMste_spaj_asli() == null) edit.getPemegang().setMste_spaj_asli(1);
	       
	        if(edit.getAgen().getLca_id().equals("01")){
	            edit.getDatausulan().setMste_flag_el(1);
	        }else{
	            edit.getDatausulan().setMste_flag_el(0);
	        }
	        param.put("pemegang",edit.getPemegang());
	        param.put("tertanggung",edit.getTertanggung());
	        param.put("datausulan",edit.getDatausulan());
	        param.put("agen",edit.getAgen());
	        int rowupdated = commonDao.updateMstInsured(param);
	        if ( rowupdated<=0)
	        {
	        	 commonDao.insertMstInsured(param);
	        }
	        
	        
	        
	}
	
	   public void saveMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao commonDao){
	        if(tgl == null) tgl = new Date();
	        HashMap map = new HashMap();
	        map.put("reg_spaj", reg_spaj);
	        map.put("kolom_tgl", kolom_tgl);
	        map.put("tgl", tgl);
	        map.put("kolom_user", kolom_user);
	        map.put("lus_id", lus_id);
	        Integer exist = commonDao.selectMstTransHist(reg_spaj);
	        if(exist > 0){
	        	commonDao.updateMstTransHistory(map);
	        }else{
	        	commonDao.insertMstTransHistory(map);
	        }
	    }

	private void save_tertanggung(Cmdeditbac edit,String counterTertanggungClient,CommonDao commonDao,Date v_strDateNow){
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
        edit.getTertanggung().setMcl_id(counterTertanggungClient);
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
        
        int rowupdated = updateClientTertanggung( commonDao,edit.getTertanggung());
        if(rowupdated<=0){
        	insertMstClientTtg(commonDao,edit.getTertanggung());
        }
        int rowAddressUpdated = updateMstAddressTtg(commonDao, edit.getTertanggung());
        if(rowAddressUpdated<=0){
        	insertMstAddressTtg(commonDao, edit.getTertanggung());
        }
        logger.info("rowupdate:"+rowupdated);
        
	}
	
	private Integer updateClientTertanggung(CommonDao commonDao,Tertanggung tertanggung){
		Integer result = 0;     
		result = commonDao.updateClientTtg(tertanggung);
		return result;
	}
	
    public Integer insertMstClientTtg(CommonDao commonDao,Tertanggung tertanggung) {
        Integer result = 0;
        result = commonDao.insertMstClientTtg(tertanggung);
        return result;
    }
    public Integer updateMstAddressTtg(CommonDao commonDao,Tertanggung tertanggung){      
        Integer result = 0;     
        result = commonDao.updateMstAddressTtg(tertanggung);
        return result;
    }
    
    public void insertMstAddressTtg(CommonDao commonDao,Tertanggung tertanggung) {       
    	commonDao.insertMstAddressTtg(tertanggung);
   }
    
    
   public Agentrec[] proc_process_agent(CommonDao commonDao,String v_strAgentId, Integer flag,HashMap<String, Agen> map){
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
       Map param1 = new HashMap();                         
		param1.put("strTmpAgentId",v_strAgentId);
		param1.put("flag", flag);
        Agen datautama =  commonDao.selectMstAgent(param1);
        if(map != null){
        	map.put(v_strAgentId, datautama);
        }
        
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
                  Agen data2 =  commonDao.selectMstAgent(param);
                  if(map != null){
                  	map.put(strTmpAgentId, data2);
                  }
                  
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
		return arrAgentRec;
       
       
   }
    
}
