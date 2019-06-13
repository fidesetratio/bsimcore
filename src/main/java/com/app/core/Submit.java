package com.app.core;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.app.dao.BsimDao;
import com.app.dao.CommonDao;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Agentrec;
import com.app.model.gadget.prod.Cmdeditbac;
import com.app.model.gadget.prod.Pemegang;
import com.app.model.gadget.prod.Personal;
import com.app.model.gadget.prod.Rekening_client;
import com.app.model.gadget.prod.Tertanggung;
import com.app.model.gadget.prod.User;
import com.app.utils.CommonUtil;
import com.app.utils.FormatString;

public class Submit {
	
	
	protected SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	protected DecimalFormat f1 = new DecimalFormat ("0");
    protected DecimalFormat f3 = new DecimalFormat("000");
    protected NumberFormat dec2 = new DecimalFormat("#.00;(#,##0.00)");
    protected SimpleDateFormat sdfDd=new SimpleDateFormat("dd");
    
    
    protected CommonDao commonDao;
    protected BsimDao bsimDao;
    
    
    
    
    
    public Submit(){
	   defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	   f1 = new DecimalFormat ("0");
	   f3 = new DecimalFormat("000");
	   dec2 = new DecimalFormat("#.00;(#,##0.00)");
	   sdfDd=new SimpleDateFormat("dd");
   }
    
    
    
    public Submit(CommonDao commonDao,BsimDao bsimDao){
    	this.commonDao=commonDao;
    	this.bsimDao = bsimDao;
    	defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");
 	    f1 = new DecimalFormat ("0");
 	    f3 = new DecimalFormat("000");
 	    dec2 = new DecimalFormat("#.00;(#,##0.00)");
 	    sdfDd=new SimpleDateFormat("dd");
    }
    
    
    
    
    public Cmdeditbac submitPas(Cmdeditbac cmd,User currentUser, String jenis_produk){
    	return this.insertspajpas(cmd, currentUser, jenis_produk, commonDao, bsimDao);
    }
    
    
    public Cmdeditbac insertspajpas(Object cmd, User currentUser, String jenis_produk, CommonDao commonDao,BsimDao bsimDao){
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
                kode_id = commonDao.selectkodegutri(params);
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
            
            String tanggalTerimaAdmin;
            
            
            
            String tgl_s =  FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.DATE)),2);
            tgl_s = tgl_s.concat("/");
            tgl_s = tgl_s.concat(FormatString.rpad("0",Integer.toString(tgl_sekarang.get(Calendar.MONTH)+1),2));
            tgl_s = tgl_s.concat("/");
            tgl_s = tgl_s.concat(Integer.toString(tgl_sekarang.get(Calendar.YEAR)));    
            Date v_strInputDate = df.parse(tgl_s);
            Date v_strDateNow = v_strInputDate;

            String v_strregionid = edit.getAgen().getKode_regional().toUpperCase();
            String v_strAgentId = edit.getAgen().getMsag_id().toUpperCase();
            String v_stragentnama = edit.getAgen().getMcl_first().toUpperCase();
            
           Integer lssa_id = new Integer(0);
           edit.getDatausulan().setLssa_id(lssa_id);
           if  (v_strAgentId.equalsIgnoreCase("000000"))
            {
                   strBranch = FormatString.rpad("0",(v_strregionid.substring(0,2)),2);
                   strWilayah = FormatString.rpad("0",(v_strregionid.substring(2,4)),2);
                   strRegion = FormatString.rpad("0",(v_strregionid.substring(4,6)),2);
                   strAgentBranch = strBranch.concat("00");
            }else{
                    HashMap m = new HashMap();
                    m.put("kodeagen", v_strAgentId);
                    Map data = commonDao.selectRegionalAgen(m);
                    
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

            
          
            //MANTA (23/06/2017) - Generate No SPAJ langsung dari sequence
            if(edit.getFlag_gmanual_spaj()!=null){
                if(edit.getFlag_gmanual_spaj()>=1){
                    strTmpSPAJ = edit.getTertanggung().getReg_spaj();
                }else{
                    strTmpSPAJ = commonDao.selectSeqNoSpaj(strBranch);
                }
            }else{
                strTmpSPAJ = commonDao.selectSeqNoSpaj(strBranch);
            }
            
            String no_pb=edit.getPemegang().getNo_pb();
    

            String nomor=null;
            if (flag_gutri.intValue() ==1)
            {
                strInsClientID=edit.getTertanggung().getMcl_id();
            }else{
          
                strInsClientID = commonDao.selectSequenceClientID();
                proc_save_data_ttg(edit, strInsClientID, v_strDateNow, commonDao);
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
                        
                        strPOClientID = commonDao.selectSequenceClientID();
                        proc_save_data_pp(edit, strPOClientID, v_strDateNow, commonDao);
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
                HashMap paramBlanko = new HashMap();
                paramBlanko.put("no_kartu", edit.getDatausulan().getMspo_nasabah_acc());
                String mspo_no_blanko = commonDao.selectNoBlankoPas(paramBlanko);
                edit.getPemegang().setMspo_no_blanko(mspo_no_blanko);
                proc_save_mst_policy_pas(currentUser, edit,strPOClientID,strTmpSPAJ,v_strDateNow ,v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid, commonDao);
                
                proc_save_mst_insured_pas(edit,strInsClientID,strTmpSPAJ, commonDao);          
       
           
                //------------------------------------------------------------
                // Process Application closing and Agent Commission
                if("AP/BP".equals(jenis_produk) || "AP/BP ONLINE".equals(jenis_produk) || "BP CARD".equals(jenis_produk)){// tidak ada komisi
//                    proc_save_agen_bp(edit,strTmpSPAJ, v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid); -> Not needed for PA BSM - Daru
                }else{//pas, dbd bp, dbd agency -- komisi hanya lev 4
                    proc_save_agen(edit,strTmpSPAJ, v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid, commonDao);
                }
    
                //input  address billing
                proc_save_addr_bill(edit,strTmpSPAJ, commonDao);
                    
                //-------------------------------------------------
                // Insert rekening baik account recur maupun rek client
                proc_save_rekening(edit,strTmpSPAJ,kode_flag, commonDao);
                                    
                // Insert information to MST_POSITION_SPAJ
                insertMstPositionSpaj(v_intActionBy.toString(), "INPUT SPAJ", strTmpSPAJ, 0, commonDao);
                updateMstInsuredTglAdmin(strTmpSPAJ, 1, tanggal, 0, commonDao);
                saveMstTransHistory(strTmpSPAJ, "tgl_input_spaj_admin", tanggal, null, null, commonDao);
                //insert ke mst_position_spaj u/history no PB(dian)
                //bacDao.insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),1, 10, "NO PB: "+no_pb,2);
                if(edit.getDatausulan().getLsbs_id()==187 && (edit.getDatausulan().getLsdbs_number()>=11 && edit.getDatausulan().getLsdbs_number()<=14) 
                        && (edit.getDatausulan().getMste_flag_cc()==1 || edit.getDatausulan().getMste_flag_cc()==2) ){
                    	insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),118, 10, "NO PB: "+no_pb,2, commonDao);
                }else{
                	insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),4, 10, "NO PB: "+no_pb,2, commonDao);
                }
//              if(edit.getPemegang().getInfo_special_case() != null && !"".equals(edit.getPemegang().getInfo_special_case())) {
//                  //insert ke mst_position_spaj u/ info special case (yusuf)
//                  bacDao.insertMst_position_no_spaj_pb(strTmpSPAJ, currentUser.getLus_id(),1, 10, "SC:"+edit.getPemegang().getInfo_special_case(),5);
//              }
                // red_flag , ryan
                if(edit.getPemegang().getMkl_red_flag()==1||edit.getTertanggung().getMkl_red_flag()==1){
                    String jenis =edit.getPemegang().getMkl_kerja();
                    String jenis2 =edit.getTertanggung().getMkl_kerja();
                    insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk Pekerjaan : Pemegang: "+jenis+" & Tertanggung: "+jenis2, strTmpSPAJ, 5,"REDFLAG", commonDao);
                }
                if (kode_flag.intValue()>1 ){
                if(edit.getInvestasiutama().getDaftartopup().getRedFlag_topup_berkala()==1){
                    if(edit.getDatausulan().getLku_id().equals("01")){
                        insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > Rp. 100 Juta", strTmpSPAJ, 10,"REDFLAG", commonDao);
                    }else if(edit.getDatausulan().getLku_id().equals("02")){
                        insertMstPositionSpajRedFlag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > $ 10000", strTmpSPAJ, 10,"REDFLAG", commonDao);  
                    }
                }}
                //------------------------------------------------------------
                // Insert Insured information to MST_STS_CLIENT
                if (flag_gutri.intValue() !=1)
                {
                   insertMst_sts_client(strInsClientID, commonDao);
                    //logger.info("insert status client");
                }
              
                //------------------------------------------------------------
                // Insert Basic Plan information to MST_PRODUCT_INSURED
                proc_save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser, commonDao);     
                
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
                    proc_save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser, commonDao);
                    edit.getDatausulan().setLsbs_id(lsbs_id);
                    edit.getDatausulan().setLsdbs_number(lsdbs_id);
                    edit.getDatausulan().setMspr_tsi(mspr_tsi);
                    edit.getDatausulan().setMspr_premium(mspr_premium);
                }
 
        } catch (Exception e){
            strTmpSPAJ="";
//            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
//            prosesError(currentUser, e);
           
        }
        edit.getPemegang().setReg_spaj(strTmpSPAJ);
        return edit;
		
	            
	            
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
	
    public void updateMstInsuredTglAdmin(String spaj,Integer insuredNo, Date tanggal,Integer show, CommonDao mapper) throws DataAccessException{
        Map param = new HashMap();
        param.put("spaj", spaj);
        param.put("insuredNo", insuredNo);
        param.put("show", show);
        param.put("tanggal", tanggal);
        mapper.updateMstInsuredTglAdmin( param );       
}
	
	
	
	 public void insertMstPositionSpaj(String lus_id, String msps_desc, String reg_spaj, int addSecond, CommonDao mapper) throws DataAccessException{
         Map p = new HashMap();
         p.put("lus_id", lus_id);
         p.put("msps_desc", msps_desc);
         p.put("reg_spaj", reg_spaj);
         p.put("addSecond", addSecond);          
         mapper.insertMstPositionSpaj( p );
 }
	
	
	
	 public void insertMst_sts_client(String mcl_id, CommonDao mapper) {
	        Map param=new HashMap();    
	        param.put("strInsClientID", mcl_id);        
	        mapper.insertMst_sts_client(param);         
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
//              insert mst rek client dan mst rek hist
                proc_save_rek_client(edit,strTmpSPAJ, mapper);
                
            }else{
                if (v_pil_invest.equalsIgnoreCase("2"))
                {
//                  insert mst rek client dan mst rek hist
                    proc_save_rek_client(edit,strTmpSPAJ, mapper );
                }else{
                    String v_strAcctHolder1 = edit.getRekening_client().getMrc_no_ac().toUpperCase();
                    String v_bank1 = edit.getRekening_client().getLsbp_id();
                    String atasnama1 = edit.getRekening_client().getMrc_nama().toUpperCase();
                    String cabang_bank = edit.getRekening_client().getMrc_cabang().toUpperCase();
                    String kota_rek = edit.getRekening_client().getMrc_kota().toUpperCase();
                    if (!v_strAcctHolder1.equalsIgnoreCase("") || !v_bank1.equalsIgnoreCase("") || !atasnama1.equalsIgnoreCase("") || !cabang_bank.equalsIgnoreCase("") || !kota_rek.equalsIgnoreCase(""))
                    {
//                      insert mst rek client dan mst rek hist
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
	
	 private void proc_save_account_recur(Cmdeditbac edit,String strTmpSPAJ, CommonDao mapper)throws Exception
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
	 
	 public void insertMstAccountRecur(Account_recur account_recur, CommonDao mapper) throws Exception {
         mapper.insertMstAccountRecur( account_recur );          
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
         insertMstRekClientHist(edit.getRekening_client(), mapper);
         //System.out.println("insert rek cliet hist");
     }
	   public void insertMstRekClientHist(Rekening_client rekening_client, CommonDao mapper) throws Exception {        
           mapper.insertMstRekClientHist( rekening_client );       
   }
   
	 
	 public void insertMstRekClient(Rekening_client rekening_client, CommonDao mapper) throws Exception {
         mapper.insertMstRekClient( rekening_client );           
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
//          System.out.println("update mst billing");
            if (rowupdated ==0)
            {
                insertMstAddressBilling( edit.getAddressbilling(), mapper );
                //System.out.println("insert mst billing");
            }
    }
    public void insertMstAddressBilling(AddressBilling addressBilling, CommonDao mapper) throws Exception {
        mapper.insertMstAddressBilling( addressBilling );       
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
	
	 public Integer selectKabupaten(String nama_wilayah, CommonDao mapper) throws Exception
	    {
	        Integer result = 0;         
	        try {
	            result = mapper.selectKabupaten( nama_wilayah );
	        } catch (Exception e) {
	            result = 0;
	            throw e;
	        }      
	        return result;
	    }
	public void proc_save_product_insured(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Integer flag_jenis_plan, Date ldt_endpay1, User currentUser, CommonDao mapper)throws Exception
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

//      ------------------------------------------------------------
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
    
	
	  public void insertMst_product_insuredPA(Map param28, CommonDao mapper) {
          mapper.inserMst_product_insured45(param28);
}
	     public void inserMst_product_insured45(Map param28, CommonDao mapper) {          
             mapper.inserMst_product_insured45(param28);          
   }
  
    
    public void inserMst_product_insured1(Map param28, CommonDao mapper) {        
               mapper.inserMst_product_insured1(param28);             
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
//                       insert mst agent comm
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
//                                   insert mst agent comm
                                     proc_save_agen_comm(edit,arrAgentartha1, strTmpSPAJ ,new Integer(i), mapper);
                                     //System.out.println("insert mst_agent_comm" + i);

                                 }else{
                                     if (arrAgentartha1[i].getComm_id().intValue() == 3 && ( strAgentBranch.equalsIgnoreCase("0905") && v_intFollowUp.intValue() == 2))
                                     {
//                                       insert mst agent comm
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
	 
	 
	 private void proc_save_agen_artha(Cmdeditbac edit, Agentrec[]  arrAgentArtha,String strTmpSPAJ , Integer i, CommonDao mapper)throws Exception
     {
         
         edit.getAgen().setReg_spaj(strTmpSPAJ);
         edit.getAgen().setMsag_id(arrAgentArtha[i.intValue()].getAgent_id());
         edit.getAgen().setLev_comm(arrAgentArtha[i.intValue()].getComm_id());
         edit.getAgen().setLsla_id(arrAgentArtha[i.intValue()].getLevel_id());
         insertMstAgentArtha( edit.getAgen(), mapper );
         //System.out.println("insert mst_agent_artha");
     }   
	  public void insertMstAgentArtha( Agen agen, CommonDao mapper ) throws Exception {          
          mapper.insertMstAgentArtha( agen );             
	  }
	 
	 public void proc_save_agen_comm_rm(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, CommonDao mapper) throws Exception
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
     
	 
	 public Agentrec[] proc_process_agentao(String v_strAgentId, CommonDao mapper) throws Exception
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
              
         Map mapAgentCodeAO = selectAgentCodeAO(v_strAgentId,mapper);
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
	 
	 
	  public HashMap<String, Object> selectAgentCodeAO(String selectAgentCodeAO,CommonDao mapper) {
         HashMap<String, Object> result = null;    
          try {
              result = mapper.selectAgentCodeAO( selectAgentCodeAO );
          } catch (Exception e) {
//            e.printStackTrace();
          } finally {
           
          }
          
          return result;
      }
	   public void insertMstAgentBa(Agen agen, CommonDao mapper) throws Exception {        
	        mapper.insertMstAgentBa( agen );
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
                             if(lca_id.equals("42") && selectIsAgenCorporate(arrAgentRec[1].getAgent_id(),mapper)==1 && lsle_id.intValue()==3){
                                 dapatKomisi=true;
                             }                   
                             if(lca_id.equals("42") && selectIsAgenCorporate(arrAgentRec[1].getAgent_id(),mapper)==0 && lsle_id.intValue()==3 && selectIsAgenKaryawan(arrAgentRec[i].getAgent_id(),mapper)!=1){
                                 dapatKomisi=true;
                             }                       
                             if(lca_id.equals("42") && selectIsAgenKaryawan(arrAgentRec[i].getAgent_id(),mapper)==1){
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

	   public Integer selectIsAgenCorporate(String kdAgen,CommonDao mapper) {
          Integer result = null;          
           try {
               result = mapper.selectIsAgenCorporate( kdAgen );
           } catch (Exception e) {
               e.printStackTrace();
               result = null;
           } finally {
             
           }           
           return result;
       }
   
	 public Integer selectIsAgenKaryawan(String kdAgen,CommonDao commonDao) {
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
	    
	     public void insertMstAgentRekruter( Agen agen, CommonDao mapper) throws Exception {
	            mapper.insertMstAgentRekruter( agen );          
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
	     
	 
     public void proc_save_agen_prod(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, Integer flag_sbm, Integer lvl_fcd,  CommonDao mapper)throws Exception
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
     
     public void insertMstAgentProd( Agen agen,CommonDao mapper ) throws Exception {
         mapper.insertMstAgentProd( agen );          
 }
     public Agen selectMstAgent( Map param1 , CommonDao mapper) throws Exception {
         Agen result = null;
         try {
             result = mapper.selectMstAgent( param1 );
         }catch(Exception e){            
             throw e;        
         }
         return result;
     }   
	 
	 public Agentrec[] proc_process_agent(String v_strAgentId, CommonDao mapper) throws Exception
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
	        
	   public void updateLeadReffBii(String mspo_plan_provider, String strTmpSPAJ, CommonDao mapper) throws Exception{             
           HashMap<String, Object> params = new HashMap<String, Object>();
           params.put("kode", mspo_plan_provider);
           params.put("spaj", strTmpSPAJ); 
           mapper.updateLeadReffBii(params);           
}       
	    
	 public void proc_save_data_ttg (Cmdeditbac edit, String strInsClientID, Date v_strDateNow, CommonDao commonDao )throws Exception
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
         
         int rowupdated = updateClientTtg( edit.getTertanggung(), commonDao);
         if (rowupdated ==0)
         {
        	 insertMstClientTtg( edit.getTertanggung(), commonDao );
             //System.out.println("insert mst client ttg");
         }
         
         // Insert Insured Client information to MST_ADDRESS_NEW
         int rowupdated1 = updateMstAddressTtg( edit.getTertanggung(), commonDao);
         if (rowupdated1 ==0)
         {
             insertMstAddressTtg( edit.getTertanggung(), commonDao );
             //System.out.println("insert mst address ttg");
         }
     }
	 
	 public void proc_save_data_pp(Cmdeditbac edit, String strPOClientID,Date v_strDateNow, CommonDao mapper  )throws Exception
     {           
         //pengiriman polis
         //Pekerjaan
         edit.getPemegang().setMkl_kerja(selectKeteranganKerja(edit.getPemegang().getMkl_kerja(),mapper));
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
//         edit.getPersonal().setMcl_id(edit.getPemegang().getMcl_id());
//         edit.getPersonal().setFlag_ws(0);
//         edit.getPersonal().setLca_id(edit.getAgen().getLca_id());
//         edit.getPersonal().setLwk_id(edit.getAgen().getLwk_id());
//         edit.getPersonal().setLsrg_id(edit.getAgen().getLsrg_id());
//         edit.getPersonal().setLsrg_nama(edit.getAgen().getLsrg_nama());
//         edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
//         edit.getPersonal().setMcl_first(edit.getPemegang().getMcl_first());
         //List<DropDown> gelar = ((List<DropDown>)query("selectGelar", 1));
         Map<String, String> params = new HashMap<String, String>();
                 
//         List<DropDownMenu> bidangUsaha = (List<DropDownMenu>) selectBidangUsaha("0", mapper);
//         if(bidangUsaha != null){
//             for(int i = 0 ; i < bidangUsaha.size() ; i++){
//                 if((bidangUsaha.get(i).getValue().toUpperCase()).equals(edit.getPemegang().getMkl_industri())){
//                     edit.getPersonal().setLju_id(Integer.parseInt(bidangUsaha.get(i).getKey()));
//                     i = bidangUsaha.size();
//                 }
//             }
//         }
//         
//         edit.getPersonal().setMpt_usaha_desc(edit.getPemegang().getMkl_industri());
//         edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
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
//               System.out.println("update mst address pp");                
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
	  public String selectKeteranganKerja(String lsp_id,CommonDao commonDao) {
         String result = null;       
          try {
             result = commonDao.selectKeteranganKerja( lsp_id );
          } catch (Exception e) {
//            e.printStackTrace();
          } 
          
          return result;
      }
     
	    public Integer updateClientTtg(Tertanggung tertanggung,  CommonDao commonDao) {        
            Integer result = 0;     
            result = commonDao.updateClientTtg(tertanggung);
            return result;
        }
	     
        public Integer insertMstClientTtg(Tertanggung tertanggung, CommonDao commonDao) {
            Integer result = 0;
            result = commonDao.insertMstClientTtg(tertanggung);
            return result;
        }
        
        public Integer updateMstAddressTtg(Tertanggung tertanggung,  CommonDao commonDao)throws Exception {        
            Integer result = 0;     
            try {
                result = commonDao.updateMstAddressTtg(tertanggung);
            } catch(Exception e) {
                result = 0;
                throw e;
            }           
            return result;
        }
        
        public void insertMstAddressTtg(Tertanggung tertanggung,  CommonDao mapper) {       
            mapper.insertMstAddressTtg(tertanggung);
       }
}
