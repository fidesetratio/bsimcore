package com.app.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.dao.CommonDao;
import com.app.model.gadget.prod.Cmdeditbac;
import com.app.model.gadget.prod.DetailPembayaran;
import com.app.model.gadget.prod.PesertaPlus_mix;
import com.app.utils.CommonUtil;
import com.app.utils.FormatString;

public class MobileSubmit extends AbstractSubmit {

	public MobileSubmit(PlatformTransactionManager platform,
			SqlSession sqlSession) {
		super(platform, sqlSession);
	}

	@Override
	public Cmdeditbac save(Cmdeditbac edit) throws Exception {
		// TODO Auto-generated method stub
		this.init();
		TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

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
              String no_temp=edit.getNo_temp();
           
              
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
                  kode_id = selectkodegutri(nama, tgl, commonDao);
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
              Integer jmlDaftarKyc2 = edit.getPemegang().getJmlkyc2();
              Integer jmlDaftarKyc = edit.getPemegang().getJmlkyc();
              Integer pesertax = edit.getDatausulan().getJml_peserta();
              
              
              
              Date tanggal = selectSysdate(commonDao);
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
              
              
              Integer index_biaya = edit.getInvestasiutama().getJmlh_biaya();
              if (index_biaya==null)
              {
                  index_biaya = new Integer(0);
              }
              
              
              String v_strregionid = edit.getAgen().getKode_regional().toUpperCase();
              String v_strAgentId = edit.getAgen().getMsag_id().toUpperCase();
              String v_stragentnama = edit.getAgen().getMcl_first().toUpperCase();
              
              
              
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
              
              
              //----------------------------------
              // Get the Agent Branch information
              if  (v_strAgentId.equalsIgnoreCase("000000"))
              {
                     strBranch = FormatString.rpad("0",(v_strregionid.substring(0,2)),2);
                     strWilayah = FormatString.rpad("0",(v_strregionid.substring(2,4)),2);
                     strRegion = FormatString.rpad("0",(v_strregionid.substring(4,6)),2);
                     strAgentBranch = strBranch.concat("00");
              }else{
                  Map data = (HashMap) selectRegionalAgen( v_strAgentId, commonDao );
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
              
              
              
          	if(edit.getFlag_gmanual_spaj()!=null){
				if(edit.getFlag_gmanual_spaj()>=1){
					strTmpSPAJ = edit.getTertanggung().getReg_spaj();
				}else{
					strTmpSPAJ = selectSpajSeq(strBranch,commonDao);
				}
			}else{
				strTmpSPAJ = selectSpajSeq(strBranch,commonDao);
			}
              
          	
            String no_pb=edit.getPemegang().getNo_pb();
            
            if (v_strAgentId.equalsIgnoreCase("000000"))
            {           
                //System.out.println("insert kalau agent baru");                
            	insertMstAgentTmp(strTmpSPAJ, v_stragentnama, commonDao);
            }
            
            String nomor=null;
            if (flag_gutri.intValue() ==1)
            {
                strInsClientID=edit.getTertanggung().getMcl_id();
            }else{
                //Get Insured Client ID counter from MST_COUNTER table
            	strInsClientID = selectCounterClient(commonDao);
                
                /* Increase current Insured Client ID by 1 and
                 update the value to MST_COUNTER table */
             //   updateMstCounterClient(new Long(intIDCounter.longValue()+1), gc_strTmpBranch);
                //System.out.println(intIDCounter);
                //System.out.println("update counter mcl id ttg");
            
                
                /* Combine Branch Information and Client Id Counter
                 to get the temporary Insured Client ID */
              //  nomor =("000000000").concat(Long.toString(intIDCounter.longValue() + 1));
             //   strInsClientID = gc_strTmpBranch.concat(nomor.substring((nomor.length()-10),nomor.length()));
                save_data_ttg (edit,strInsClientID,v_strDateNow, commonDao );
            }   
              
            if (v_intRelation.intValue() != 1)
            {
            	   if (flag_gutri.intValue() ==1)
                   {
                       strPOClientID=kode_id;
                   }else{      
                     
                       strPOClientID = selectCounterClient(commonDao);
                       //input data pemegang polis di mst client new dan mst_address new
                       save_data_pp(edit,strPOClientID,v_strDateNow, commonDao );
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
              
            save_mst_policy( edit,strPOClientID,strTmpSPAJ,v_strDateNow ,v_strAgentId,strAgentBranch,strBranch,strWilayah,strRegion,v_strregionid, commonDao);
            save_worksite_flag(edit,strTmpSPAJ, commonDao);
            save_no_blanko(edit,strTmpSPAJ, commonDao);    
            save_mst_insured(edit,strInsClientID,strTmpSPAJ, commonDao); 
            save_suamiistri_ttg(edit,strTmpSPAJ, commonDao); 
            save_suamiistri_pp(edit,strTmpSPAJ, commonDao);     
            save_keluarga(edit, strTmpSPAJ, commonDao);
            save_pembayar_premi(edit,strTmpSPAJ, commonDao);
            if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
                save_data_pic(edit,strPOClientID, commonDao);
            };
            save_agen(edit,strTmpSPAJ,commonDao);   
            save_addr_bill(edit,strTmpSPAJ, commonDao);
            save_rekening(edit,strTmpSPAJ,kode_flag, commonDao);
            if(edit.getFlag_special_case().equals("1")){
            	save_position_spaj(v_intActionBy.toString(), "INPUT SPAJ (SPECIAL CASE)", strTmpSPAJ, 0, commonDao);     
            }else{
            	save_position_spaj(v_intActionBy.toString(), "INPUT SPAJ", strTmpSPAJ, 0, commonDao);
            }
            
            saveMstInsuredTglAdmin(strTmpSPAJ, 1, tanggal, 0, commonDao);
            Integer exist = selectMstTransHist( strTmpSPAJ, commonDao );   
            
            if(exist > 0){
                updateMstTransHistory(strTmpSPAJ, "tgl_input_spaj_admin", tanggal, null, null, commonDao);                 
            }else{
                insertMstTransHistory(strTmpSPAJ, "tgl_input_spaj_admin", tanggal, null, null, commonDao);     
                
            }
            save_position_spaj(v_intActionBy.toString(), "NO PB: "+no_pb, strTmpSPAJ, 0, commonDao);
          //ryan, red_flag
            edit.getPemegang().setMkl_red_flag(0);
            edit.getTertanggung().setMkl_red_flag(0);
            if(edit.getPemegang().getMkl_red_flag()==1||edit.getTertanggung().getMkl_red_flag()==1){
                String jenis =edit.getPemegang().getMkl_kerja();
                String jenis2 =edit.getTertanggung().getMkl_kerja();
                save_position_spaj_red_flag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk Pekerjaan : Pemegang: "+jenis+" & Tertanggung: "+jenis2, strTmpSPAJ, 5,"REDFLAG", commonDao);
            }
            if (kode_flag.intValue()>1 ){
                if(edit.getInvestasiutama().getDaftartopup().getRedFlag_topup_berkala()==1){
                    if(edit.getDatausulan().getLku_id().equals("01")){
                    	save_position_spaj_red_flag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > Rp. 100 Juta", strTmpSPAJ, 10,"REDFLAG", commonDao);
                    }else if(edit.getDatausulan().getLku_id().equals("02")){
                    	save_position_spaj_red_flag(v_intActionBy.toString(), "Masuk Kategori REDFLAG Untuk TOP-UP: TOP-UP > $ 10000", strTmpSPAJ, 10,"REDFLAG", commonDao);  
                    }
                }
            }
            
            
            // Insert Insured information to MST_STS_CLIENT
            if (flag_gutri.intValue() !=1)
            {
                save_mst_sts_client(strInsClientID, commonDao);
                //System.out.println("insert status client");
            }
            
            if (flag_rider.intValue()==1)
            {
                li_tahun =v_intInsPeriod;
                if(!CommonUtil.isEmpty(flag_jenis_plan)){
                    if ((flag_jenis_plan.intValue()==1) )
                    {
                        li_tahun = new Integer(6);
                    }
                }else if(CommonUtil.isEmpty(flag_jenis_plan)){
//                  flag_jenis_plan=pro
                }
                ai_month=new Integer((li_tahun.intValue() * 12) - 1);
                //ai_month=new Integer((li_tahun.intValue() * 12) );
                
                Map param27=new HashMap();
                param27.put("v_strBeginDate",v_strBeginDate);   
                param27.put("ai_month",ai_month);   
                //System.out.println("add month");
                
                Date ldt_endpay2 =null;
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                ldt_endpay2=df1.parse((String)selectAddmonths(param27, commonDao));

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
                ldt_endpay3=df1.parse((String)selectAddmonths(param28, commonDao));
                
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
            Integer ai_month3=new Integer((10 * 12) - 1);
            if(edit.getDatausulan().getLsbs_id()==191){
                ai_month3=new Integer((8 * 12) - 1);
            }
            Date tgl_beg_date_polis = edit.getDatausulan().getMste_beg_date();
            Integer tahun_beg_date_polis = tgl_beg_date_polis.getYear() + 1900;
            if ((flag_platinumlink.intValue() == 1) && (tahun_beg_date_polis.intValue() > 2006))
            {
                 ai_month3 =  new Integer(ai_month3.intValue() + 1);
            }
                    
            Map param29=new HashMap();
            param29.put("v_strBeginDate",v_strBeginDate);   
            param29.put("ai_month",ai_month3);  
            //System.out.println("add month");
            
            Date ldt_endpay6 =null;
            ldt_endpay6=df1.parse((String)selectAddmonths(param29, commonDao));
            
            tgl_endpay = ldt_endpay6.getDate();
            bln_endpay = ldt_endpay6.getMonth()+1;
            thn_endpay = ldt_endpay6.getYear()+1900;
            ldt_endpay = (FormatString.rpad("0",Integer.toString(tgl_endpay),2)).concat("/");
            ldt_endpay = ldt_endpay.concat(FormatString.rpad("0",Integer.toString(bln_endpay),2));
            ldt_endpay = ldt_endpay.concat("/");
            ldt_endpay = ldt_endpay.concat(Integer.toString(thn_endpay));
            ldt_endpay5 = df.parse(ldt_endpay);
        }         
            //------------------------------------------------------------
            // Insert Basic Plan information to MST_PRODUCT_INSURED
            // proc_save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1,currentUser);
            save_product_insured(edit,strTmpSPAJ,v_intActionBy ,flag_jenis_plan, ldt_endpay1, null, commonDao);      
            
            if(jumlah_rider == null){
            	jumlah_rider = new Integer(0);
            }
            
            if (jumlah_rider.intValue()>0)
            {
            	if((edit.getDatausulan().getLsbs_id()==190 && edit.getDatausulan().getLsdbs_number()==9)
                		||(edit.getDatausulan().getLsbs_id()==200 && edit.getDatausulan().getLsdbs_number()==7)){
            		save_rider_ultimate(edit, strTmpSPAJ,v_intActionBy, commonDao );
            	}else{
            		save_rider(edit, strTmpSPAJ,v_intActionBy, commonDao );
            	}
            }
            Double jumlah_premi = sum_premi(strTmpSPAJ, commonDao);
            if (jumlah_premi == null)
            {
                jumlah_premi = new Double(0);
            }
            if (flag_gutri.intValue() == 1)
            {
                DetailPembayaran dp = new DetailPembayaran();
                dp.setReg_spaj(strTmpSPAJ);
                dp.setKe(new Integer(1));
                dp.setJenis_ttp(new Integer(1));
                dp.setCara_bayar(new Integer(5));
                dp.setTgl_bayar(df.parse("01/11/2006"));
                dp.setTgl_jatuh_tempo(null);
                dp.setTgl_rk(df.parse("01/11/2006"));
                dp.setKurs(edit.getDatausulan().getLku_id());
                dp.setJml_bayar(jumlah_premi);
                dp.setNilai_kurs(new Double(0));
                dp.setTgl_skrg(new Date());
                dp.setRef_polis_no(null);
                dp.setKeterangan(null);
                dp.setLus_login_name(Integer.toString(edit.getDatausulan().getLus_id()));
                dp.setAktif(new Integer(1));
                dp.setBank(new Integer(42));
                dp.setStatus("B");
                dp.setNo_kttp(null);
                insertmst_deposit( dp,commonDao );
            }
            
            if (flag_as.intValue()==2)
            {
                save_karyawan(edit, strTmpSPAJ,v_intActionBy, commonDao);
            }
            
            //-----------------------------------------------------------
            //-- Insert Excellink information to MST_ULINK and MST_DET_ULINK
            if (kode_flag.intValue() > 1 && kode_flag.intValue() != 11 && kode_flag.intValue() != 15 && kode_flag.intValue() != 16)
            {
                //proc_unitlink(edit,strTmpSPAJ,v_strDateNow,v_intActionBy ,currentUser ,ldt_endpay1 ,ldt_endpay4 ,ldt_endpay5);
                proc_unitlink(edit,strTmpSPAJ,v_strDateNow,v_intActionBy ,null ,ldt_endpay1 ,ldt_endpay4 ,ldt_endpay5, commonDao);
            } 
            
            save_benef(edit, strTmpSPAJ, commonDao );
            save_dth(edit, strTmpSPAJ, commonDao);
            if (jumlah_rider.intValue()>=0)
            {
                PesertaPlus_mix pesertaPlus_mix = new PesertaPlus_mix();
                pesertaPlus_mix.setLsbs_id(edit.getDatausulan().getLsbs_id());
                pesertaPlus_mix.setLsdbs_number(edit.getDatausulan().getLsdbs_number());
                save_pesertax(edit, pesertaPlus_mix, strTmpSPAJ, commonDao);
            }
            
            
            if(edit.getDatausulan().getDaftaRiderAddOn()==null) edit.getDatausulan().setDaftaRiderAddOn(0);
            
            edit.getPemegang().setReg_spaj(strTmpSPAJ);
            
            
            if(edit.getPemegang().getFlag_upload()!=null){
            	//String no_temp=edit.getNo_temp();
                updateSpajTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);
                updateProductTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);
                updateAddressBillingTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);
                updatePesertaTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);

                Integer questTemp = selectCountQuestionaireTemp(no_temp, commonDao);
                Integer MedquestTemp=selectCountMedquestTemp(no_temp, commonDao);
                Integer benefTemp=selectCountbenefTemp(no_temp, commonDao);
                String ReffBiiTemp=selectCountReffBiiTemp(no_temp, commonDao);
                
                if(MedquestTemp>0){
                    copyMedquestTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);
                }
                if(ReffBiiTemp!=null){
                    copyReffBiiTemp(no_temp,edit.getPemegang().getReg_spaj(), commonDao);
                }
                if(questTemp>0){
                    copyQuestionaireTemp(no_temp, edit.getPemegang().getReg_spaj(), commonDao);
                }  
                
                if(!edit.getTertanggung().getMste_no_vacc().equals("")){
                    Map map = new HashMap();
                    map.put("no_va", edit.getTertanggung().getMste_no_vacc());      
                    map.put("lus_id", null);
                    map.put("spaj",edit.getPemegang().getReg_spaj());
                    updateMstDetVa(map, commonDao);
                }      
            }
            edit.getPemegang().setReg_spaj(strTmpSPAJ);
            
            transactionManager.commit(txStatus);
            
            
            
        }catch(Exception e){
        	   e.printStackTrace();
        	   transactionManager.rollback(txStatus);
        }
        
        if(edit.getPemegang().getReg_spaj() != null){
        	return edit;
        }
		return null;
	}

}
