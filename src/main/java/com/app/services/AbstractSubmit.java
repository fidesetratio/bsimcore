package com.app.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.logging.Logger;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.dao.CommonDao;
import com.app.model.esehat.DropDownMenu;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Agentrec;
import com.app.model.gadget.prod.Benefeciary;
import com.app.model.gadget.prod.Biayainvestasi;
import com.app.model.gadget.prod.Cmdeditbac;
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

abstract class AbstractSubmit {
	protected static Logger logger = Logger.getLogger(AbstractSubmit.class);
	protected PlatformTransactionManager transactionManager;
	protected SqlSession sqlSession;
	protected CommonDao commonDao;
	protected AbstractSubmit(PlatformTransactionManager platform,SqlSession sqlSession){
		this.commonDao = commonDao;
		this.transactionManager = platform;
		this.sqlSession = sqlSession;
	}
	
	protected void init(){
		commonDao=sqlSession.getMapper(CommonDao.class);
	}
	
	protected abstract Cmdeditbac save(Cmdeditbac edit) throws Exception;
	
	protected void save_product_insured(Cmdeditbac edit, String counterSpaj,
				Integer v_intActionBy, Integer flag_jenis_plan, Date ldt_endpay1,
				User currentUser, CommonDao commonDao) {
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
	        
	        if (flag_jenis_plan.intValue()==4)
            {
	        	intClass = v_intClass;
                Map param28=new HashMap();
                param28.put("strTmpSPAJ",counterSpaj);   
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
                inserMst_product_insured45( param28, commonDao );
            }else if (flag_jenis_plan.intValue() == 5){
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
                 param28.put("strTmpSPAJ",counterSpaj);   
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
                 insertMst_product_insuredPA( param28, commonDao );
            }else{
            	
            	
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
	      
		}
    public void insertMst_product_insuredPA(Map param28, CommonDao commonDao) {
    	commonDao.inserMst_product_insured45(param28);
    }
	   
    public void inserMst_product_insured45(Map param28, CommonDao commonDao) {          
    	commonDao.inserMst_product_insured45(param28);          
     }
	
    public String selectAddmonths(Map param27, CommonDao commonDao) throws Exception {
        String result = null;
        try {
            result = commonDao.selectAddmonths(param27);
        } catch (Exception e) {         
            throw e;
        }      
        return result;
   }
	
    
    
 protected Double sum_premi ( String spaj, CommonDao commonDao)
 {
        Double result = null;
         try {
             result = commonDao.sum_premi(spaj);
         } catch (Exception e) {
//           e.printStackTrace();
         } finally {
         }           
         return result;  
 }
  
    
    protected void save_rider(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao commonDao )throws Exception
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
//              if(end_payRider[i].after(edit.getDatausulan().getMspr_end_pay())){
//                  end_payRider[i]=edit.getDatausulan().getMspr_end_pay();
//              }
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
//              if((v_intRiderId[i]==820 && v_intRiderNo[i] >105) || (v_intRiderId[i]==826 && v_intRiderNo[i] >10) || (v_intRiderId[i]==823 && v_intRiderNo[i] >15) || (v_intRiderId[i]==825 && v_intRiderNo[i] >15) || (v_intRiderId[i]==819 && ((v_intRiderNo[i] >=20 && v_intRiderNo[i] <=140) || (v_intRiderNo[i] >160 && v_intRiderNo[i] <=280) || (v_intRiderNo[i] >300 && v_intRiderNo[i] <=380) || (v_intRiderNo[i] >390 && v_intRiderNo[i] <=430) || (v_intRiderNo[i] >450 && v_intRiderNo[i] <=530))) ){
//                      flagBikinPusing = 1;
//              }
                
                param28.put("v_percent",percentRider[i]);
                param28.put("ins_rider",ins_rider[i]);
                param28.put("mspr_extra",mspr_extra[i]);
                if(v_intRiderId[i]==835){
                    param28.put("flag_up_sc",1);
                }else{
                    param28.put("flag_up_sc",0);
                }
                if(flagBikinPusing==0){
                    insertMst_product_insured_rider( param28, commonDao );
                }
                
                //System.out.println("insert mst product insured rider");
                //(Deddy) untuk bagian ini dipastiin dulu apakah stable link nantinya akan memakai ini juga.klo iya, di tabel utama mst_slink perlu ditambah kolom total ridernya ga.
                // ......
                
                if (v_intRiderId[i] == 819 || v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825  || v_intRiderId[i]==826)
                {
                    if(v_intRiderId[i]==819  || v_intRiderId[i]==826){
//                      insert peserta HCP Family (utama)
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
                            
//                          if (status.equalsIgnoreCase("input") || (flag_rider_hcp.intValue() == 0))
//                          {
//                              proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                          }
                        }
                    }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
                        Simas simas = new Simas();
                        simas.setLsbs_id(v_intRiderId[i]);
                        simas.setLsdbs_number(v_intRiderNo[i]);
                        simas.setDiscount(new Double(0));
                        simas.setPremi(premi_rider[i]);
//                      if(edit.getDatausulan().getLsbs_id().intValue()==183 || edit.getDatausulan().getLsbs_id().intValue()==189 || edit.getDatausulan().getLsbs_id().intValue()==193 || edit.getDatausulan().getLsbs_id().intValue()==195){
//                          if (status.equalsIgnoreCase("input")){
//                              proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                          }
//                      }else{
//                          if(flagBikinPusing==0){
//                              if (status.equalsIgnoreCase("input")){
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"utama");
//                              }else if (status.equalsIgnoreCase("edit")){
//                                  proc_save_peserta(edit,strTmpSPAJ,simas,"edit");
//                                  flag_ekasehat = 1;
//                              }
//                          }
//                      }
                    }               
                }
                }
    }
    
    
         public void insertMst_product_insured_rider(Map param28, CommonDao commonDao) {     
        	 commonDao.insertMst_product_insured_rider(param28);         
}
	
		protected void inserMst_product_insured1(Map param28, CommonDao commonDao) {
			commonDao.inserMst_product_insured1(param28);             
		}


		protected void saveMst_sts_client(String mcl_id, CommonDao commonDao) {
	        Map param=new HashMap();    
	        param.put("strInsClientID", mcl_id);        
	        commonDao.insertMst_sts_client(param);         
	    }
		
		
	    protected void saveMst_position_no_spaj_pb(String spaj, String lus_id, int lspd, Integer lssp, String desc,Integer count, CommonDao commonDao) {
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



		protected void saveMstInsuredTglAdmin(String spaj,Integer insuredNo, Date tanggal,Integer show, CommonDao commonDao){
	        Map param = new HashMap();
	        param.put("spaj", spaj);
	        param.put("insuredNo", insuredNo);
	        param.put("show", show);
	        param.put("tanggal", tanggal);
	        commonDao.updateMstInsuredTglAdmin( param );       
	}
		protected void save_position_spaj(String lus_id, String msps_desc, String reg_spaj, int addSecond, CommonDao commonDao){
			  Map p = new HashMap();
	          p.put("lus_id", lus_id);
	          p.put("msps_desc", msps_desc);
	          p.put("reg_spaj", reg_spaj);
	          p.put("addSecond", addSecond);          
	          commonDao.insertMstPositionSpaj( p );
		}
		
		
	    
	    public void save_mst_sts_client(String mcl_id, CommonDao commonDao) {
	        Map param=new HashMap();    
	        param.put("strInsClientID", mcl_id);        
	        commonDao.insertMst_sts_client(param);         
	    }
	    
	    
	    protected void save_rider_ultimate(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao commonDao )throws Exception
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
                        insertMst_product_insured_rider( param28, commonDao );
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
                                

                            }
                        }else if(v_intRiderId[i]==820 || v_intRiderId[i]==823 || v_intRiderId[i]==825){
                            Simas simas = new Simas();
                            simas.setLsbs_id(v_intRiderId[i]);
                            simas.setLsdbs_number(v_intRiderNo[i]);
                            simas.setDiscount(new Double(0));
                            simas.setPremi(premi_rider[i]);

                        }               
                    }
                    }
        }
	    
	    
	    public void insertmst_deposit(DetailPembayaran detailPembayaran,CommonDao commonDao) {
	        	   commonDao.insertmst_deposit( detailPembayaran );
	     
	    }
	    
	    
		public static Date FormatDateAdd(Date tanggal, int kalendar, int angka) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(tanggal);
			cal.add(kalendar, angka);
			return cal.getTime();
		}
	    
	       protected void save_karyawan(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy, CommonDao commonDao)throws Exception
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
	            Map data4 = selectNamaPlan(param22, commonDao);
	            
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
	        
	                insertMstEmp( edit.getEmployee(),commonDao );
	                //System.out.println("edit mst emp");
	            }
	        }    
	       
	       public Map<String, Object> selectNamaPlan(Map param , CommonDao commonDao) throws Exception {
	            Map<String, Object> result = null;    
	            try {
	                result = commonDao.selectNamaPlan( param );
	            } catch (Exception e) {
	                throw e;
	            }           
	            return result;
	        };
	        
	        
	        public void insertMstEmp(Employee employee, CommonDao commonDao) {
	        	commonDao.insertMstEmp( employee );
	         
	        }
	        
	        
	        
	        
	        
	        protected void save_benef(Cmdeditbac edit, String strTmpSPAJ, CommonDao commonDao )throws Exception
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
	                        insertMst_benefeciary(bf1, commonDao );
	                    } catch (Exception e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
	            }
	           }
	            
	        
	        
	        protected void save_dth(Cmdeditbac edit, String strTmpSPAJ, CommonDao commonDao)throws Exception
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
	                    insertMst_rencana_penarikan(rp1, commonDao);
	                }
	            }
	        };
	        
	        public void copyMedquestTemp(String no_temp,String reg_spaj, CommonDao commonDao) throws Exception{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);        
	            commonDao.insertMedQuestFromTemp(p);           
	        };
	        public void copyReffBiiTemp(String no_temp,String reg_spaj, CommonDao commonDao) throws Exception{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);        
	            commonDao.insertReffBiiFromTemp(p);            
	    };
	        
	        public void copyQuestionaireTemp(String no_temp,String reg_spaj, CommonDao commonDao) throws Exception{
	            Map p = new HashMap();
	            p.put("no_temp", no_temp);
	            p.put("reg_spaj", reg_spaj);            
	            commonDao.insertQuestionaireFromTemp(p);
	     };
	     
	     
	     
	     public void updateMstDetVa(Map paramx, CommonDao commonDao) throws Exception{
	    	 commonDao.updateMstDetVa(paramx);              
	    }
	       
	        protected void save_pesertax(Cmdeditbac edit, PesertaPlus_mix pesertaPlus_mix, String strTmpSPAJ, CommonDao commonDao)throws Exception
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
	                                    insert_mst_peserta_plus_mix(plus2, commonDao);
	                                }
	                            }
	                        }
	            }    
	        
	        
	        public void insert_mst_peserta_plus_mix(PesertaPlus_mix pesertaPlus_mix, CommonDao commonDao) throws Exception {
	        	commonDao.insert_mst_peserta_plus_mix( pesertaPlus_mix );      
	        }   
	        
	        
	        public void insertMst_rencana_penarikan(RencanaPenarikan rencanaPenarikan, CommonDao commonDao) throws Exception {
	        	commonDao.insertMst_rencana_penarikan( rencanaPenarikan );     
	        }
	                public void insertMst_benefeciary(Benefeciary benefeciary, CommonDao commonDao) throws Exception {     
	                	commonDao.insertMst_benefeciary( benefeciary );            
	                }       

	        protected void proc_unitlink(Cmdeditbac edit, String strTmpSPAJ,Date v_strDateNow,Integer v_intActionBy , User currentUser ,Date ldt_endpay1,  Date ldt_endpay4,  Date ldt_endpay5, CommonDao commonDao)throws Exception
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
	            
	            
	            List l = edit.getInvestasiutama().getDaftarbiaya();
	            for(int i=0;i<l.size();i++){
	            	if(l.get(i) instanceof Map){
	            		Map m = (Map)l.get(i);
	            		biayaUlink[(Integer)m.get("mu_ke")] += (Double)m.get("mbu_jumlah");
	            		 
	            	}else{
	            	    Biayainvestasi bi = (Biayainvestasi) edit.getInvestasiutama().getDaftarbiaya().get(i);
		                biayaUlink[bi.getMu_ke()] += bi.getMbu_jumlah();
	            		
	            	};
	            }
	            /*
	            for(int i=0; i<edit.getInvestasiutama().getDaftarbiaya().size(); i++) {
	                Biayainvestasi bi = (Biayainvestasi) edit.getInvestasiutama().getDaftarbiaya().get(i);
	                biayaUlink[bi.getMu_ke()] += bi.getMbu_jumlah();
	            }
	            */
	            int mu_ke = 1;  
	            //Save MST_ULINK untuk Premi Pokok
	            proc_save_mst_ulink(edit,strTmpSPAJ, v_intActionBy ,v_tglsurat,1,1,1,v_curBasePremium,v_jmlhtopup_berkala,v_jmlhtopup_tunggal , v_topup_berkala,v_topup_tunggal,v_strBeginDate,"", commonDao);
	            //Save MST_DET_ULINK untuk Premi Pokok
	            for(int i=0; i<invvl.size(); i++) {
	            	
	            if(invvl.get(i) instanceof Map){
	            	Map m = (Map)invvl.get(i) ;
	            	Integer mdu_persen1 = (Integer)m.get("mdu_persen1");
	            	String lji_id1 = (String)m.get("lji_id1");
	            	
	            //	{mu_ke1=null, lji_id1=03, mdu_persen1=100, mdu_jumlah1=0.0, lji_invest1=Excellink Aggressive Fund, reg_spaj1=null, mdu_jumlah_top=null, mdu_jumlah_top_tunggal=null}
	            	if(mdu_persen1 != null){
	            		if(mdu_persen1 >0){
	            			int persen_tu = 0; 
	            		     proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, mdu_persen1, persen_tu, mdu_persen1 * (v_curBasePremium - biayaUlink[mu_ke]) /100, mu_ke, lji_id1, v_strBeginDate, commonDao);
	            		         
	            		}
	            	}
	            	
	            	//System.out.println(m);
	            	
	            }else{
	            	
	            	
	                DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                if(di.getMdu_persen1() != null) {
	                    if(di.getMdu_persen1() > 0) {
	                        //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                        int persen_tu = 0; 
	                        proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), persen_tu, di.getMdu_persen1() * (v_curBasePremium - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, commonDao);
	                    }
	                }
	                
	            };
	                
	                
	            }       
	            if (v_topup_berkala.intValue() > 0 ){
	                mu_ke++;
	                //Save MST_ULINK untuk Top-Up Berkala
	                proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,2,lt_id_tunggal,lt_id_berkala ,v_jmlhtopup_berkala  , 0., 0.,0 , 0,v_strBeginDate,"berkala", commonDao);   
	                //Save MST_DET_ULINK untuk Top-Up Berkala
	                for(int i=0; i<invvl.size(); i++) {
	                    DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                    if(di.getMdu_persen1() != null) {
	                        if(di.getMdu_persen1() > 0) {
	                            //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                            int persen_tu = 0; 
	                            proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), di.getMdu_persen1(), di.getMdu_persen1() * (v_jmlhtopup_berkala - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, commonDao);
	                        }
	                    }
	                }
	            }
	            if (v_topup_tunggal.intValue() > 0 ){
	                mu_ke++;
	                //Save MST_ULINK untuk Top-Up Tunggal
	                proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,mu_ke,lt_id_tunggal,lt_id_berkala ,v_jmlhtopup_tunggal , 0., 0. , 0, 0, v_strBeginDate, "tunggal", commonDao); 
	                //Save MST_DET_ULINK untuk Top-Up Tunggal
	                for(int i=0; i<invvl.size(); i++) {
	                    DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                    if(di.getMdu_persen1() != null) {
	                        if(di.getMdu_persen1() > 0) {
	                            //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                            int persen_tu = 0; 
	                            proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), di.getMdu_persen1(), di.getMdu_persen1() * (v_jmlhtopup_tunggal - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, commonDao);
	                        }
	                    }
	                }
	            }
	            
	            //Deddy (10 aug 2012) - Apabila special offer untuk additional unit, diinsert ke mst_ulink dan det_ulink dgn lt_id = 10
	            if(edit.getTertanggung().getMste_flag_special_offer()!=null){
	                if(edit.getTertanggung().getMste_flag_special_offer()==2){
	                    mu_ke++;
	                    proc_save_mst_ulink(edit,strTmpSPAJ,v_intActionBy ,null,mu_ke,10,10 ,edit.getDatausulan().getPremi_additional_unit() , 0., 0. , 0, 0, v_strBeginDate, "Additional Unit", commonDao);   
	                    //Save MST_DET_ULINK untuk Premi Pokok
	                    for(int i=0; i<invvl.size(); i++) {
	                        DetilInvestasi di = (DetilInvestasi) invvl.get(i);
	                        if(di.getMdu_persen1() != null) {
	                            if(di.getMdu_persen1() > 0) {
	                                //int persen_tu = (v_topup_berkala.intValue() > 0 || v_topup_tunggal.intValue() > 0) ? di.getMdu_persen1() : 0; 
	                                int persen_tu = 0; 
	                                proc_save_det_ulink(edit, strTmpSPAJ, v_intActionBy, di.getMdu_persen1(), persen_tu, di.getMdu_persen1() * (edit.getDatausulan().getPremi_additional_unit() - biayaUlink[mu_ke]) /100, mu_ke, di.getLji_id1(), v_strBeginDate, commonDao);
	                            }
	                        }
	                    }
	                }
	            }
	            
	            l = edit.getInvestasiutama().getDaftarbiaya();
	            
	            
	            
	            for(int i=0; i<edit.getInvestasiutama().getDaftarbiaya().size(); i++) {
	            	

	                Biayainvestasi bi = null;
	                
	                
	            	if(edit.getInvestasiutama().getDaftarbiaya().get(i) instanceof Map){

	            		 Map m  = (Map) edit.getInvestasiutama().getDaftarbiaya().get(i);
	            		 
	            		 bi = new Biayainvestasi();
	            		 bi.setLjb_id((Integer)m.get("ljb_id"));
	            		 bi.setMbu_jumlah((Double)m.get("mbu_jumlah"));
	            		 bi.setMbu_persen((Double)m.get("mbu_persen"));
	            		 bi.setMu_ke((Integer)m.get("mu_ke"));
	            		 

	            	}else{
	            		 bi = (Biayainvestasi) edit.getInvestasiutama().getDaftarbiaya().get(i);
	            	}
	            	
	               
	                
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
	                insertMst_biaya_ulink( param30, commonDao );           
	            }
	            //FIXME
	            System.out.println("================== END PROC_UNITLINK ==================");              
	        }      
	        
	        public void insertMst_biaya_ulink(Map param, CommonDao commonDao) throws Exception {       
	        	commonDao.insertMst_biaya_ulink( param );      
	    }

	        
	        private void proc_save_det_ulink(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Integer value,Integer persen, Double jumlah , Integer mu_ke , String id ,  Date tgl_trans, CommonDao commonDao)throws Exception
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
	            insertFixed(param, commonDao);
	        }     
	        
	        public void insertFixed(Map param, CommonDao commonDao) throws Exception {   
	        	commonDao.insertFixed( param );            
	    }
	    
	        
	        private void proc_save_mst_ulink(Cmdeditbac edit, String strTmpSPAJ,Integer v_intActionBy ,Date v_tglsurat,Integer mu_ke, Integer lt_id_tunggal,Integer lt_id_berkala , Double v_premiexcell , Double v_jmlhtopup_berkala, Double v_jmlhtopup_tunggal , Integer v_topup_berkala, Integer v_topup_tunggal , Date tgl_trans, String keterangan, CommonDao commonDao)throws Exception
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
	            int rowupdated = update_mst_ulink(edit.getInvestasiutama(), commonDao);        
	            if (rowupdated <=0)
	            {
	                insert_mst_ulink(edit.getInvestasiutama(), commonDao); 
	            }
	        }           
	             
	        
	        public Integer update_mst_ulink(InvestasiUtama investasiUtama, CommonDao commonDao) throws Exception {
	            Integer result = 0;    
	            try {
	                result = commonDao.update_mst_ulink( investasiUtama );
	            }catch(Exception e){
	                result = 0;
	                throw e;        
	            }
	            return result;
	       }    
	        public void insert_mst_ulink(InvestasiUtama investasiUtama, CommonDao commonDao) throws Exception {        
	        	commonDao.insert_mst_ulink( investasiUtama );          
	    }
	     
		 public void save_position_spaj_red_flag(String lus_id, String msps_desc, String reg_spaj, int addSecond,String jenis, CommonDao commonDao){
		        Map p = new HashMap();
		        p.put("lus_id", lus_id);
		        p.put("msps_desc", msps_desc);
		        p.put("reg_spaj", reg_spaj);
		        p.put("addSecond", addSecond);
		        p.put("jenis", jenis);
		        commonDao.insertMstPositionSpajRedFlag(p);     
		    }
		
		protected void save_rekening(Cmdeditbac edit,String counterSpaj,Integer kode_flag, CommonDao commonDao){
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
	        	 if (kode_flag.intValue() != 1) {
			        	try {
							save_rek_client(edit,counterSpaj, commonDao);
						} catch (Exception e) {
							e.printStackTrace();
						}
	        	 }else{
	        		 if (v_pil_invest.equalsIgnoreCase("2")){
	        			 try {
							save_rek_client(edit,counterSpaj, commonDao );
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		 }else{
	        			  	String v_strAcctHolder1 = edit.getRekening_client().getMrc_no_ac().toUpperCase();
	                        String v_bank1 = edit.getRekening_client().getLsbp_id();
	                        String atasnama1 = edit.getRekening_client().getMrc_nama().toUpperCase();
	                        String cabang_bank = edit.getRekening_client().getMrc_cabang().toUpperCase();
	                        String kota_rek = edit.getRekening_client().getMrc_kota().toUpperCase();
	                        
	                        if (!v_strAcctHolder1.equalsIgnoreCase("") || !v_bank1.equalsIgnoreCase("") || !atasnama1.equalsIgnoreCase("") || !cabang_bank.equalsIgnoreCase("") || !kota_rek.equalsIgnoreCase(""))
	                        {
//	                          insert mst rek client dan mst rek hist
	                            try {
									save_rek_client(edit,counterSpaj, commonDao);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        }
	        		 }
	        	 }
	        	 
	        	 if ((kode_account.intValue() ==3) ||(v_intAutoDebet.intValue()==1) || (v_intAutoDebet.intValue()==2)){
	 	        	save_account_recur(edit,counterSpaj, commonDao);
	 	         }
	        	
	        }else{
	        	 if ((v_intAutoDebet.intValue()==1) || (v_intAutoDebet.intValue()==2) || (v_intAutoDebet.intValue()==9))
	                {
	                    save_account_recur(edit,counterSpaj, commonDao);
	                }
	        }
	       
	        
		}
		
		
		



		protected void save_account_recur(Cmdeditbac edit, String counterSpaj,
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

		protected void insertMstAccountRecur(Account_recur account_recur,
				CommonDao commonDao) {
			// TODO Auto-generated method stub
			commonDao.insertMstAccountRecur( account_recur );  
		}

		protected void save_rek_client(Cmdeditbac edit, String counterSpaj,
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
		
		
		
		
		
		 protected void insertMstRekClientHist(Rekening_client rekening_client, CommonDao commonDao) throws Exception {        
			 commonDao.insertMstRekClientHist( rekening_client );       
		 };
	 
	 
		
		
		 protected void insertMstRekClient(Rekening_client rekening_client,CommonDao commonDao) {
			 commonDao.insertMstRekClient(rekening_client);
		}

		protected Integer updateMstRekClient(Rekening_client rekening_client, CommonDao commonDao) throws Exception {
		        Integer result = 0;    
		        try {
		            result = commonDao.updateMstRekClient( rekening_client );
		        }catch(Exception e){
		            result = 0;
		            throw e;
		        }
		        return result;
		    }  

		protected void save_pemegang(Cmdeditbac edit, String counterHolderClient,
				CommonDao commonDao, Date date) {
			// TODO Auto-generated method stub
			
		}
		 protected void save_addr_bill(Cmdeditbac edit,String strTmpSPAJ, CommonDao commonDao){
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
		   
		 protected void insertMstAddressBilling(AddressBilling addressBilling, CommonDao commonDao) {
			 commonDao.insertMstAddressBilling( addressBilling );       
		 }
		 protected Integer selectKabupaten(String nama_wilayah, CommonDao commonDao) 
		    {
		        Integer result = 0;         
		        
		        try{
		          result = commonDao.selectKabupaten( nama_wilayah );
		        }catch(Exception e){
		        	result = 0;
		        }
		        return result;
		 }
		 
		 
		 protected Integer updateMstAddressBilling(AddressBilling addressBilling, CommonDao commonDao)  {
		        Integer result = 0;    
		      
		            result = commonDao.updateMstAddressBilling( addressBilling );
		       
		        return result;
		    }   
		 protected void save_agen(Cmdeditbac edit, String counterSpaj,CommonDao commonDao)
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
		 
		 
		 protected Agentrec[] proc_process_agentao(String v_strAgentId, CommonDao commonDao,Map mapAgentCodeAO,HashMap<String, Agen> map){
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
		 protected void proc_save_agen_prod(Cmdeditbac edit, Agentrec[]  arrAgentRec,String strTmpSPAJ , Integer i, Integer flag_sbm, Integer lvl_fcd,CommonDao commonDao){
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

		 
		 
		 
		 protected void save_mst_policy(  Cmdeditbac edit, String strPOClientID,String strTmpSPAJ,Date v_strDateNow , String v_strAgentId, String strAgentBranch,String strBranch,String strWilayah,String strRegion,String v_strregionid, CommonDao commonDao)throws Exception
	        {
	            DateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");          
	             
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
	            
	                edit.getPemegang().setMspo_flat(0);
	                
	                if(edit.getAgen().getLca_id().equals("42")){
	                    edit.getPemegang().setMspo_customer(edit.getPemegang().getMspo_customer());
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
	                if( commonDao.selectLineBusLstBisnis(edit.getDatausulan().getLsbs_id().toString())==3){
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
	                int rowupdated= updateMstPolicy(edit.getPemegang(),edit.getTertanggung(),edit.getDatausulan(),edit.getAgen(), commonDao );
	                //System.out.println("edit mst policy");
	                if (rowupdated <= 0)
	                {
	                    insertMstPolicy( edit.getPemegang(),edit.getTertanggung(),edit.getDatausulan(),edit.getAgen(), commonDao );
	                    //System.out.println("insert mst policy");
	                }           
	                
	                if(edit.getAgen().getLca_id().equals("58")){
	                    //untuk masukkan kode appointmentID
	                    updateLeadReffBii(edit.getPemegang().getMspo_plan_provider(), strTmpSPAJ, commonDao );             
	                }
	                
	                if(edit.getPemegang().getMspo_spaj_date() != null) {
	                    Date tanggal = edit.getPemegang().getMspo_spaj_date();
	                    if(tanggal == null) tanggal = commonDao.selectSysdate();
	                            
	                    Integer exist = selectMstTransHist( strTmpSPAJ, commonDao );               
	                    
	                    if(exist > 0){
	                        updateMstTransHistory(strTmpSPAJ, "tgl_spaj_asli", tanggal, null, null, commonDao);                    
	                    }else{
	                        insertMstTransHistory(strTmpSPAJ, "tgl_spaj_asli", tanggal, null, null, commonDao);
	                    }               
	                }
	                Integer flag_simponi=edit.getDatausulan().getIsBungaSimponi();
	                Integer flag_tahapan=edit.getDatausulan().getIsBonusTahapan();
	                edit.getPemegang().setReg_spaj(strTmpSPAJ);         
	                //update bunga simponi
	                if (flag_simponi.intValue()==1)
	                {
	                    updateBungaMstPolicy( edit.getPemegang(), commonDao );             
	                }           
	                //update bonus  tahapan
	                if (flag_tahapan.intValue()==1)
	                {
	                    Double bonus =edit.getPemegang().getBonus_tahapan();
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(),commonDao ); 
	                }
	                if((edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==3) || (edit.getDatausulan().getLsbs_id()==137 && edit.getDatausulan().getLsdbs_number()==4) ){
	                    Double bonus = 5.00;
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(), commonDao ); 
	                }else if((edit.getDatausulan().getLsbs_id()==114 && edit.getDatausulan().getLsdbs_number()>=2)  ){
	                    Double bonus = 2.92;
	                    edit.getPemegang().setMspo_under_table(bonus);
	                    updateBungaMstPolicy( edit.getPemegang(), commonDao ); 
	                }
	                
	                String []pendapatanRutinBulan = edit.getPemegang().getPendapatanBulan();
	                String []tujuanInvestasi = edit.getPemegang().getTujuanInvestasi();
	                Integer counter=0;
	                deleteMstKyc( strTmpSPAJ, "3", "2", commonDao);    
	                for(int i=0;i<pendapatanRutinBulan.length;i++){
	                    if(!pendapatanRutinBulan[i].contains("-")){
	                        insertKyc(strTmpSPAJ, counter++, "3", "2", pendapatanRutinBulan[i], commonDao);
	                    }
	                }
	                deleteMstKyc( strTmpSPAJ, "5", "2", commonDao);    
	                for(int i=0;i<tujuanInvestasi.length;i++){
	                    if(!tujuanInvestasi[i].contains("-")){
	                    insertKyc(strTmpSPAJ, counter++, "5", "2", tujuanInvestasi[i], commonDao);
	                    }
	                }
	                
	                //Ridhaal - update no_blanko menjadi Non Further setelah SPAJ di input
	                updateMonitoringSpaj(edit.getPemegang().getMspo_no_blanko(), null, "update_further_to_NF_inputSpaj", null, null , null, null,null,null,strTmpSPAJ, commonDao);
	        
	            } catch (ParseException e) {
	                e.printStackTrace();
	                throw e;
	            }
	        }
		 
		  public void updateMonitoringSpaj(String no_blanko, Integer jenis, String type, Date tgl_kembali_ke_agen, Date tgl_terima_dari_agen, String jenis_further, String keterangan_further,String msag_id,String nama_pemegang, String emailcc, CommonDao commonDao) {
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
		        commonDao.updateMonitoringSpaj( param );
		    }
		 
		   
		    public void updateBungaMstPolicy(Pemegang pemegang, CommonDao commonDao) throws Exception {
		    	commonDao.updateBungaMstPolicy( pemegang );
		    }   
		 
		 
		 public void insertMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao commonDao) throws Exception {
		        HashMap<String, Object> params = new HashMap<String, Object>();
		        params.put("reg_spaj", reg_spaj);
		        params.put("kolom_tgl", kolom_tgl);
		        params.put("tgl", tgl);
		        params.put("kolom_user", kolom_user);
		        params.put("lus_id", lus_id);
		        commonDao.insertMstTransHistory(params);       
		    }   
		    public void updateMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao commonDao) throws Exception {
		        HashMap<String, Object> params = new HashMap<String, Object>();
		        params.put("reg_spaj", reg_spaj);
		        params.put("kolom_tgl", kolom_tgl);
		        params.put("tgl", tgl);
		        params.put("kolom_user", kolom_user);
		        params.put("lus_id", lus_id);
		        commonDao.updateMstTransHistory(params);           
		    }   
		   public Integer selectMstTransHist(String reg_spaj, CommonDao commonDao) throws Exception {
	            Integer result = 0;         
	            try {
	                result = commonDao.selectMstTransHist(reg_spaj);
	            } catch (Exception e) {
	                result = 0;
	                throw e;
	            }
	            return result;
	     }  
        
		    public void updateLeadReffBii(String mspo_plan_provider, String strTmpSPAJ, CommonDao commonDao) throws Exception{             
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("kode", mspo_plan_provider);
                params.put("spaj", strTmpSPAJ); 
                commonDao.updateLeadReffBii(params);           
		    }    
		   public Integer insertMstPolicy(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao commonDao) throws Exception {
	            Integer result = 0;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("pemegang", pemegang);
	            params.put("tertanggung", datausulan);
	            params.put("datausulan", datausulan);
	            params.put("agen", agen);           
	            try {
	                result = commonDao.insertMstPolicy( params );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            }           
	            return result;
	        }
		 public Integer updateMstPolicy(Pemegang pemegang, Tertanggung tertanggung, Datausulan datausulan, Agen agen, CommonDao commonDao) throws Exception{
	            Integer result = 0;
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("pemegang", pemegang);
	            params.put("tertanggung", datausulan);
	            params.put("datausulan", datausulan);
	            params.put("agen", agen);           
	            try {
	                result = commonDao.updateMstPolicy( params );
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
		protected void save_policy(User currentUser, Cmdeditbac edit,CommonDao commonDao, String strPOClientID,String counterSpaj){
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
		
		
		protected void save_mst_insured(Cmdeditbac edit,String strInsClientID,String strTmpSPAJ,CommonDao commonDao){
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
		        };
		        
		        if(CommonUtil.isEmpty(edit.getDatausulan().getMste_flag_el())){
					edit.getDatausulan().setMste_flag_el(0);
				};
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
		
		
		protected void savePendapatanDanTujuanInvestasi(Cmdeditbac edit,String counterSpaj,CommonDao commonDao) throws Exception{
		    String []pendapatanRutinBulan = new String[]{};
	        String []tujuanInvestasi  = new String[]{};
	        // save KYC untuk Pendapatan & Tujuan Investasi
	        if(edit.getTertanggung().getPendapatanBulan() != null )
	        	pendapatanRutinBulan = edit.getTertanggung().getPendapatanBulan();
            if(edit.getTertanggung().getTujuan_investasi() != null)
            	tujuanInvestasi = edit.getTertanggung().getTujuanInvestasi();
        
			deleteMstKyc( counterSpaj, "3", "1", commonDao);
			
            Integer counter=0;  
            for(int i=0;i<pendapatanRutinBulan.length;i++){
                if(!pendapatanRutinBulan[i].contains("-")){
                    insertKyc(counterSpaj, counter++, "3", "1", pendapatanRutinBulan[i], commonDao);
                }
            }   
            deleteMstKyc( counterSpaj, "5", "1", commonDao);  
            for(int i=0;i<tujuanInvestasi.length;i++){
                if(!tujuanInvestasi[i].contains("-")){
                insertKyc(counterSpaj, counter++, "5", "1", tujuanInvestasi[i], commonDao);
                }
            }
		}
		  public void insertKyc(String strTmpSPAJ,Integer noUrut,String kycId,String kycPp,String kycDesc, CommonDao commonDao) throws Exception {
		        HashMap<String, Object> params = new HashMap<String, Object>();
		        params.put("reg_spaj", strTmpSPAJ);
		        params.put("no_urut",noUrut);
		        params.put("kyc_id",kycId);
		        params.put("kyc_pp",kycPp);
		        params.put("kyc_desc", kycDesc);
		        commonDao.insertKyc( params );     
		    }   
		
		   public void deleteMstKyc(String spaj, String kyc_id, String kyc_pp, CommonDao commonDao) throws Exception {
		        HashMap<String, Object> params = new HashMap<String, Object>();
		        params.put("spaj", spaj);
		        params.put("kyc", kyc_id);
		        params.put("kyc_pp", kyc_pp);    
		        commonDao.deleteMstKyc( params );          
		    }   
		   
		   
		
		   protected void saveMstTransHistory(String reg_spaj, String kolom_tgl, Date tgl, String kolom_user, String lus_id, CommonDao commonDao){
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

		protected void save_tertanggung(Cmdeditbac edit,String counterTertanggungClient,CommonDao commonDao,Date v_strDateNow){
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
		
		protected Integer updateClientTertanggung(CommonDao commonDao,Tertanggung tertanggung){
			Integer result = 0;     
			result = commonDao.updateClientTtg(tertanggung);
			return result;
		}
		
	    protected Integer insertMstClientTtg(CommonDao commonDao,Tertanggung tertanggung) {
	        Integer result = 0;
	        result = commonDao.insertMstClientTtg(tertanggung);
	        return result;
	    }
	    protected Integer updateMstAddressTtg(CommonDao commonDao,Tertanggung tertanggung){      
	        Integer result = 0;     
	        result = commonDao.updateMstAddressTtg(tertanggung);
	        return result;
	    }
	    
	    protected void insertMstAddressTtg(CommonDao commonDao,Tertanggung tertanggung) {       
	    	commonDao.insertMstAddressTtg(tertanggung);
	   }
	    
	    
	   protected Agentrec[] proc_process_agent(CommonDao commonDao,String v_strAgentId, Integer flag,HashMap<String, Agen> map){
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
	    
	   
	   
	   
	   protected Date selectSysdate(CommonDao commonDao){
		
			return commonDao.selectSysdate();
	   };
			
			
	   protected String selectkodegutri(String nama,String tanggal, CommonDao commonDao){
		   String result = null;
		   HashMap<String, Object> params = new HashMap<String, Object>();
		   params.put("nama", nama);
           params.put("tanggal", tanggal);         
           try {
               result = commonDao.selectkodegutri( params );
           } catch (Exception e) {
              throw e;
           } 
           return result;
	   }
	   protected HashMap<String, Object> selectRegionalAgen(String kodeagen, CommonDao commonDao) {
           HashMap<String, Object> params = new HashMap<String, Object>();
           params.put("kodeagen", kodeagen);
           return commonDao.selectRegionalAgen( params );
       }
	   public String selectSpajSeq(String strBranch,CommonDao commonDao) {
		  return commonDao.selectSeqNoSpaj(strBranch);
	   }
	   
	   protected void insertMstAgentTmp(String strTmpSPAJ, String v_stragentnama, CommonDao commonDao) throws Exception{ 
           HashMap<String, Object> params = new HashMap<String, Object>();
           params.put("strTmpSPAJ", strTmpSPAJ);
           params.put("v_stragentnama", v_stragentnama);
           commonDao.insertMstAgentTmp(params);               
       }
	   
	   public void save_data_ttg (Cmdeditbac edit, String strInsClientID, Date v_strDateNow, CommonDao commonDao )throws Exception
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
           if (rowupdated<=0)
           {
               insertMstClientTtg( edit.getTertanggung(), commonDao );
               //System.out.println("insert mst client ttg");
           }
           
           // Insert Insured Client information to MST_ADDRESS_NEW
           int rowupdated1 = updateMstAddressTtg( edit.getTertanggung(), commonDao);
           if (rowupdated1 <=0)
           {
               insertMstAddressTtg( edit.getTertanggung(),commonDao );
               //System.out.println("insert mst address ttg");
           }
       }

	   
	   public void save_data_pp(Cmdeditbac edit, String strPOClientID,Date v_strDateNow, CommonDao commonDao  )throws Exception
	    {           
	        //pengiriman polis
	        //Pekerjaan
	        edit.getPemegang().setMkl_kerja(selectKeteranganKerja(edit.getPemegang().getMkl_kerja(),commonDao));
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
//	        edit.getPersonal().setMcl_id(edit.getPemegang().getMcl_id());
//	        edit.getPersonal().setFlag_ws(0);
//	        edit.getPersonal().setLca_id(edit.getAgen().getLca_id());
//	        edit.getPersonal().setLwk_id(edit.getAgen().getLwk_id());
//	        edit.getPersonal().setLsrg_id(edit.getAgen().getLsrg_id());
//	        edit.getPersonal().setLsrg_nama(edit.getAgen().getLsrg_nama());
//	        edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
//	        edit.getPersonal().setMcl_first(edit.getPemegang().getMcl_first());
	        //List<DropDown> gelar = ((List<DropDown>)query("selectGelar", 1));
	        Map<String, String> params = new HashMap<String, String>();
	                
//	        List<DropDownMenu> bidangUsaha = (List<DropDownMenu>) selectBidangUsaha("0", mapper);
//	        if(bidangUsaha != null){
//	            for(int i = 0 ; i < bidangUsaha.size() ; i++){
//	                if((bidangUsaha.get(i).getValue().toUpperCase()).equals(edit.getPemegang().getMkl_industri())){
//	                    edit.getPersonal().setLju_id(Integer.parseInt(bidangUsaha.get(i).getKey()));
//	                    i = bidangUsaha.size();
//	                }
//	            }
//	        }
//	        
//	        edit.getPersonal().setMpt_usaha_desc(edit.getPemegang().getMkl_industri());
//	        edit.getPersonal().setMpt_contact(edit.getContactPerson().getNama_lengkap().toUpperCase());
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
	            int rowupdated = updateMstClientPP( edit.getPemegang(), commonDao );
	            if (rowupdated <= 0)
	            {
	                insertMstClientPP( edit.getPemegang(), commonDao );
	                //System.out.println("insert mst client PP");
	            }
	            
	            //------------------------------------------------------------
	            // Insert Policy Holder Home Address information to MST_ADDRESS
//	              System.out.println("update mst address pp");                
	            int rowupdated1 = updateMstAddressPP( edit.getPemegang(), commonDao);
	            if (rowupdated1 <= 0)
	            {
	                insertMstAddressPP( edit.getPemegang(), commonDao );
	                //System.out.println("insert mst client PP");
	            }
	            
	            /*------------------------------------------------------------
	             Insert Policy Holder Company information to MST_COMPANY*/
	                if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
	                    int rowupdated2 = updateMstCompany( edit.getPersonal(), commonDao );
	                    //System.out.println("update mst client pp");
	                    if (rowupdated2 <= 0)
	                    {
	                        insertMstCompany( edit.getPersonal(), commonDao );
	                        //System.out.println("insert mst company pp");
	                    }
	                }
	                
	                //Insert Policy Holder Home Address Company information to MST_ADDRESS_NEW
	                if(edit.getDatausulan().getJenis_pemegang_polis() == 1){
	                    int rowupdated3 = updateMstCompanyAddress( edit.getPemegang(), commonDao );
	                    //System.out.println("update mst client pp");
	                    if (rowupdated3 <= 0)
	                    {
	                        insertMstCompanyAddress( edit.getPemegang(),  commonDao );
	                        //System.out.println("insert mst company pp");
	                    }
	                }       
	    }
	   
	   public Integer insertMstCompanyAddress(Pemegang pemegang, CommonDao commonDao) throws Exception {
           Integer result = 0;         
           try {
               result = commonDao.insertMstCompanyAddress( pemegang );
           } catch(Exception e) {
               result = 0;
              
           } 
           return result;
       }
	   public Integer updateMstCompanyAddress(Pemegang pemegang, CommonDao commonDao)throws Exception {
           Integer result = 0;         
           try {
               result = commonDao.updateMstCompanyAddress( pemegang );
           } catch(Exception e) {
               result = 0;
               throw e;
           }
           return result;
       }
	   
	   public Integer insertMstCompany(Personal personal, CommonDao commonDao) throws Exception {
           Integer result = 0;         
           try {
               result = commonDao.insertMstCompany( personal );
           } catch(Exception e) {
               result = 0;
               throw e;
           }
           return result;
       }
       
	     public Integer updateMstCompany(Personal personal, CommonDao commonDao) throws Exception {
	            Integer result = 0;         
	            try {
	                result = commonDao.updateMstCompany(personal);
	            } catch(Exception e) {
	                result = 0;
	                throw e;
	            } 
	            return result;
	        }
	   
	    public Integer insertMstAddressPP(Pemegang pemegang, CommonDao commonDao) throws Exception {
            Integer result = 0;         
            try {
                result = commonDao.insertMstAddressPP( pemegang );
            } catch(Exception e) {
                result = 0;
                throw e;
            }           
            return result;
        }
	   public Integer updateMstAddressPP(Pemegang pemegang, CommonDao commonDao) throws Exception {
           Integer result = 0;         
          
               result = commonDao.updateMstAddressPP(pemegang);
        
           return result;
       }
	   public Integer insertMstClientPP(Pemegang pemegang, CommonDao commonDao) throws Exception {
           Integer result = 0;         
               result = commonDao.insertMstClientPP( pemegang );
       
           return result;
       }
	   public Integer updateMstClientPP(Pemegang pemegang, CommonDao commonDao) throws Exception {
           Integer result = 0;         
    
               result = commonDao.updateMstClientPP(pemegang);
          
           return result;
       }
       public String selectKeteranganKerja(String lsp_id,CommonDao commonDao) {
    	   return  commonDao.selectKeteranganKerja( lsp_id );
         
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
	   public Integer updateClientTtg(Tertanggung tertanggung,  CommonDao commonDao) {        
           Integer result = 0;     
           result = commonDao.updateClientTtg(tertanggung);
           return result;
       }
       
	   public void insertMstAddressTtg(Tertanggung tertanggung,  CommonDao commonDao) {       
		   commonDao.insertMstAddressTtg(tertanggung);
      }
	   public Integer insertMstClientTtg(Tertanggung tertanggung, CommonDao commonDao) {
           Integer result = 0;
           result = commonDao.insertMstClientTtg(tertanggung);
           return result;
       }
	   protected String selectCounterClient(CommonDao commonDao) {
		   return commonDao.selectSequenceClientID();
       }   
	   
	   
	   protected void save_worksite_flag(Cmdeditbac edit,String strTmpSPAJ, CommonDao commonDao){
		   Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
		   if (flag_worksite.intValue() == 1 || flag_worksite.intValue() == 2 || (edit.getAgen().getLca_id().equals("52") && edit.getDatausulan().getMste_flag_cc()==3) || edit.getDatausulan().getMste_gmit()==1) 
			{
				if(!CommonUtil.isEmpty(edit.getPemegang().getMspo_customer())){
					
				}
				edit.getPemegang().setReg_spaj(strTmpSPAJ);
				
				int rowUpdate = commonDao.updateMstWorksiteFlag(edit.getPemegang());
				
				if(rowUpdate<=0){
					commonDao.insertMstWorksiteFlag(edit.getPemegang());
				}
				
			
			}
	   }
	   
	   protected void save_no_blanko(Cmdeditbac edit, String strTmpSPAJ,CommonDao commonDao) throws Exception{
		   Integer flag_worksite = edit.getDatausulan().getFlag_worksite();
		   if (flag_worksite.intValue() == 1)
           {
			   String no_blanko = edit.getPemegang().getMspo_no_blanko();
			   if (no_blanko.equalsIgnoreCase(""))
               {
				    String lca_id = edit.getAgen().getLca_id();
				    HashMap<String, Object> params = new HashMap<String, Object>();
		            params.put("number", new Integer(71));
		            params.put("lca_id", lca_id);
				    Long intIDCounter =  commonDao.select_Counter(params);
                    intIDCounter = new Long(intIDCounter.longValue()+1);  
                    String blanko = FormatString.rpad("0",Long.toString(intIDCounter),7);
                    
                   
               }
           }
		   String no_blanko = edit.getPemegang().getMspo_no_blanko();
           if (no_blanko== null) no_blanko = "";
           String nomor = no_blanko.replaceAll(" ", "").toUpperCase();
           
           List<DropDownMenu> daftar = selectJenisForm(commonDao);
           for(DropDownMenu d : daftar){
               if(nomor.startsWith(d.getKey())){
                 update_no_blanko(nomor.substring(d.getKey().length()), strTmpSPAJ, Integer.valueOf(d.getValue()), commonDao);
               }   
           }
	   }
	   public List<DropDownMenu> selectJenisForm(CommonDao commonDao) {
          List<DropDownMenu> result = null;                   
        result = commonDao.selectJenisForm();
           return result;
       }
	   
	   public Integer update_no_blanko(String no_blanko, String spaj, Integer lsjs_id, CommonDao commonDao) throws Exception
	    {
	        Integer result = 0;
	        HashMap<String, Object> params = new HashMap<String, Object>();
	        params.put("no_blanko", no_blanko);
	        params.put("spaj", spaj);
	        params.put("lsjs_id", lsjs_id);     
	        try {
	            result = commonDao.update_no_blanko( params );
	        } catch(Exception e) {
	            result = 0;
	            throw e;
	        }       
	        return result;
	    } 
	   protected void deleteMstKeluarga(String lsbs_id, CommonDao commonDao) throws Exception {    
		   commonDao.deleteMstKeluarga( lsbs_id );        
   }       
      
	   
	   
protected void save_suamiistri_pp(Cmdeditbac edit, String counterSpaj, CommonDao commonDao)throws Exception
       {
           
           String nama_suamiistri = edit.getPemegang().getNama_si();
           if (!nama_suamiistri.equalsIgnoreCase(""))
           {
               Date tanggal_lahir_suamiistri = edit.getPemegang().getTgllhr_si();      
               insertMstKeluarga(counterSpaj, edit.getPemegang().getNama_si(), 5, tanggal_lahir_suamiistri, 1, 0, commonDao);
           };
           
           String nama_anak1 = edit.getPemegang().getNama_anak1();
           if (!nama_anak1.equalsIgnoreCase(""))
           {
               Date tanggal_lahir_anak1 = edit.getPemegang().getTgllhr_anak1();            
               insertMstKeluarga(counterSpaj, edit.getPemegang().getNama_anak1(), 4, tanggal_lahir_anak1, 1, 1, commonDao);
           }
           
           String nama_anak2 = edit.getPemegang().getNama_anak2();
           if (!nama_anak2.equalsIgnoreCase(""))
           {
               Date tanggal_lahir_anak2 = edit.getPemegang().getTgllhr_anak2();        
               insertMstKeluarga(counterSpaj, edit.getPemegang().getNama_anak2(), 4, tanggal_lahir_anak2, 1, 2, commonDao);
           }
           
           String nama_anak3 = edit.getPemegang().getNama_anak3();
           if (!nama_anak3.equalsIgnoreCase(""))
           {
               Date tanggal_lahir_anak3 = edit.getPemegang().getTgllhr_anak3();
               insertMstKeluarga(counterSpaj, edit.getPemegang().getNama_anak3(), 4, tanggal_lahir_anak3, 1, 3, commonDao);
           }
       }
	   
	   protected void save_suamiistri_ttg(Cmdeditbac edit, String counterSpaj, CommonDao commonDao)throws Exception
       {
           
           deleteMstKeluarga(counterSpaj, commonDao);
           
           String nama_suamiistri = edit.getTertanggung().getNama_si();
           Date tanggal_lahir_suamiistri = edit.getTertanggung().getTgllhr_si();
           if (!nama_suamiistri.equalsIgnoreCase(""))
           {
               insertMstKeluarga(counterSpaj, edit.getTertanggung().getNama_si(), 5, tanggal_lahir_suamiistri, 0, 0, commonDao);
               //System.out.println("insert mst keluarga");
           }
       
           String nama_anak1 = edit.getTertanggung().getNama_anak1();
           if (!nama_anak1.equalsIgnoreCase(""))
           {       
               Date tanggal_lahir_anak1 = edit.getTertanggung().getTgllhr_anak1();
               insertMstKeluarga(counterSpaj, edit.getTertanggung().getNama_anak1(), 4, tanggal_lahir_anak1, 0, 1, commonDao);
           }
           
           String nama_anak2 = edit.getTertanggung().getNama_anak2();
           if (!nama_anak2.equalsIgnoreCase(""))
           {           
               Date tanggal_lahir_anak2 = edit.getTertanggung().getTgllhr_anak2();
               insertMstKeluarga(counterSpaj, edit.getTertanggung().getNama_anak2(), 4, tanggal_lahir_anak2, 0, 2, commonDao);
           }
           
           String nama_anak3 = edit.getTertanggung().getNama_anak3();
           if (nama_anak3 == null)
           {
               nama_anak3 = "";
           }
           if (!nama_anak3.equalsIgnoreCase(""))
           {
               Date tanggal_lahir_anak3 = edit.getTertanggung().getTgllhr_anak3();
               insertMstKeluarga(counterSpaj, edit.getTertanggung().getNama_anak3(), 4, tanggal_lahir_anak3, 0, 3, commonDao);         
           }
       };
       
       protected void insertMstKeluarga(String strTmpSPAJ, String nama, Integer lsre_id, Date tanggal_lahir, Integer insured,Integer no, CommonDao commonDao) throws Exception {
           HashMap<String, Object> param = new HashMap<String, Object>();
           param.put("strTmpSPAJ", strTmpSPAJ);
           param.put("nama", nama);
           param.put("lsre_id", lsre_id);
           param.put("tanggal_lahir", tanggal_lahir );
           param.put("insured", insured);
           param.put("no", no);
           commonDao.insertMstKeluarga( param );      
       }
       
       
       
       protected void save_keluarga(Cmdeditbac edit,String strTmpSPAJ, CommonDao commonDao) throws Exception{
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
                       int rowupdated =  updateMstKeluarga( paramSuami, commonDao);
                       if(rowupdated <= 0){
                    	   insert_Mst_Keluarga( paramSuami, commonDao);
                       }
                        
        		   }else if (edit.getPemegang().getPemegang_polis().equals("1")){
        			   Integer tanggalAyah = edit.getPemegang().getTgl_ayah().getDate();
                       Integer bulanAyah = edit.getPemegang().getTgl_ayah().getMonth()+1;
                       Integer tahunAyah = edit.getPemegang().getTgl_ayah().getYear()+1900;
                       Integer tanggalIbu = edit.getPemegang().getTgl_ibu().getDate();
                       Integer bulanIbu = edit.getPemegang().getTgl_ibu().getMonth()+1;
                       Integer tahunIbu = edit.getPemegang().getTgl_ibu().getYear()+1900;
                       Integer usiaAyah = umr.umur(tahunAyah,bulanAyah,tanggalAyah,tahun2,bulan2,tanggal2);
                       Integer usiaIbu = umr.umur(tahunIbu,bulanIbu,tanggalIbu,tahun2,bulan2,tanggal2);
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
                       
                       int rowupdated2 =  updateMstKeluarga( paramAyah, commonDao );

                       if(rowupdated2 == 0) 
                       {
                    	   insert_Mst_Keluarga( paramAyah, commonDao );
                       }

                       
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
                       
                       int rowupdated3 =  updateMstKeluarga( paramIbu, commonDao );
                       if(rowupdated3 == 0) insert_Mst_Keluarga( paramIbu, commonDao );          
                       
        		   }
        	   }
           }
       }
       
       
       protected Integer updateMstKeluarga( HashMap<String, Object> params, CommonDao commonDao ) throws Exception {
           Integer result = 0;    
           try {
               result = commonDao.updateMstKeluarga( params );
           }catch(Exception e){
               result = 0;
               throw e;        
           }
           return result;
       }    
       
       protected void insert_Mst_Keluarga( HashMap<String, Object> params, CommonDao commonDao) throws Exception {
    	   commonDao.insertMstKeluarga( params );         
       }
	   
       
       protected void save_pembayar_premi(Cmdeditbac edit,String strTmpSPAJ,CommonDao commonDao) throws Exception{
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
        	   strPemPremiID = this.selectCounterClient(commonDao);
        	   if(calonPembayarpremi.equals("41")){
		        	   edit.getPembayarPremi().setMcl_id(strPemPremiID);
					   edit.getPembayarPremi().setMcl_jenis("1");
					   HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
						dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
						dataPolicy.put("reg_spaj", strTmpSPAJ);
						dataPolicy.put("mspo_payer",strPemPremiID);
						Integer rowupdated1 = updateNewClientPayerBadanHukum(edit.getPembayarPremi(),commonDao);
						if (rowupdated1 <= 0){
		                    insertNewClientPayerBadanHukum(edit.getPembayarPremi(), commonDao);
		                }
						
						Integer rowupdated2 = updateAddressPemPremiIndividu(edit.getPembayarPremi(), commonDao);
		                if (rowupdated2 <= 0){
		                    insertAddressPemPremiBadanHukum(edit.getPembayarPremi(), commonDao);
		                }
		                updateMstPolicyPayer(dataPolicy, commonDao);    
						
		           }else{
		        	      edit.getPembayarPremi().setMcl_id(strPemPremiID);
		                  edit.getPembayarPremi().setMcl_jenis("0");
		                  
		                  // Map dataPolicy = new HashMap();
		                  HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
		                  dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
		                  dataPolicy.put("reg_spaj", strTmpSPAJ);
		                  dataPolicy.put("mspo_payer", strPemPremiID);
		                  
		                  Integer rowupdated1 = updateNewClientPayerIndividu(edit.getPembayarPremi(), commonDao);                
		                  if (rowupdated1 <= 0){
		                       insertNewClientPayerIndividu(edit.getPembayarPremi(), commonDao);
		                  }
		                  
		                  Integer rowupdated2 = updateAddressPemPremiIndividu(edit.getPembayarPremi(), commonDao);
		                  if (rowupdated2 <= 0){
		                      insertAddressPemPremiIndividu(edit.getPembayarPremi(), commonDao);
		                  }
		                  updateMstPolicyPayer(dataPolicy, commonDao);   
		           }
		           
           }else{
               //Map dataPolicy = new HashMap();
               HashMap<String, Object> dataPolicy = new HashMap<String, Object>();
               dataPolicy.put("lsre", edit.getPembayarPremi().getLsre_id_payer());
               dataPolicy.put("reg_spaj", strTmpSPAJ);
               dataPolicy.put("mspo_payer", edit.getPemegang().getMcl_id());           
               updateMstPolicyPayer(dataPolicy, commonDao);   
           }
           
           
           deleteMstKyc( strTmpSPAJ, "6", "0", commonDao);    
           if (edit.getPembayarPremi().getMkl_kerja_other_radio().equals("1")){
               insertKyc(strTmpSPAJ, counter++, "6", "0", edit.getPembayarPremi().getMkl_kerja_other(), commonDao );
           }
               
           deleteMstKyc( strTmpSPAJ, "7", "0", commonDao);    
           if(edit.getPembayarPremi().getTotal_rutin()==null)edit.getPembayarPremi().setTotal_rutin("");
           if (!edit.getPembayarPremi().getTotal_rutin().equals("")){
               insertKyc(strTmpSPAJ, counter++, "7", "0", edit.getPembayarPremi().getTotal_rutin(), commonDao );
           }
                   
           deleteMstKyc( strTmpSPAJ, "8", "0", commonDao);
           if(edit.getPembayarPremi().getTotal_non_rutin()==null)edit.getPembayarPremi().setTotal_non_rutin("");
           if (!edit.getPembayarPremi().getTotal_non_rutin().equals("")){
               insertKyc(strTmpSPAJ, counter++, "8", "0", edit.getPembayarPremi().getTotal_non_rutin(), commonDao );
           }       
       
           deleteMstKyc( strTmpSPAJ, "3", "0", commonDao);
           for(int i=0;i<pendapatanRutinBulan.length;i++){
               if(!pendapatanRutinBulan[i].contains("-")){
                   insertKyc(strTmpSPAJ, counter++, "3", "0", pendapatanRutinBulan[i], commonDao);
               }
           }
                   
           deleteMstKyc( strTmpSPAJ, "4", "0", commonDao);
           for(int i=0;i<pendapatanNonTahun.length;i++){
               if(!pendapatanNonTahun[i].contains("-")){
                   insertKyc(strTmpSPAJ, counter++, "4", "0", pendapatanNonTahun[i], commonDao );
               }
           }
               
           deleteMstKyc( strTmpSPAJ, "5", "0", commonDao);
           for(int i=0;i<tujuanInvestasi.length;i++){
               if(!tujuanInvestasi[i].contains("-")){
               insertKyc(strTmpSPAJ, counter++, "5", "0", tujuanInvestasi[i], commonDao );
               }
           }
       }
       
       
       protected void save_data_pic(Cmdeditbac edit, String strPOClientID, CommonDao commonDao)throws Exception
       {
           
           edit.getContactPerson().setMcl_id(strPOClientID);
           edit.getContactPerson().setFlag_ut(1);
           edit.getContactPerson().setNo_urut(1);
           edit.getContactPerson().setTelp_kantor(edit.getContactPerson().getTelpon_kantor());
           edit.getContactPerson().setTelp_hp(edit.getContactPerson().getNo_hp());
           edit.getContactPerson().setLus_id(edit.getPemegang().getLus_id());
           
           // Insert Policy Holder PIC information to MST_COMPANY_CONTACT*/
            int rowupdated =  updateMstClientPic(  edit.getContactPerson(), commonDao );
            if (rowupdated<=0)
               {
                insertMstClientPic(  edit.getContactPerson(), commonDao );
                   //System.out.println("insert mst client pic");
               }       
           // Insert Policy Holder PIC Home Address information to MST_COMPANY_CONTACT_ADDRESS
           int rowupdated1 =  updateMstAddressPic(  edit.getContactPerson(), commonDao );
           if (rowupdated1<=0)
           {
                insertMstAddressPic(  edit.getContactPerson(), commonDao );
               //System.out.println("insert mst address pic");
           }       
           deleteMstCompanyContactFamily( strPOClientID, commonDao);  
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
               inserMstCompanyContactFamily( param1, commonDao );
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
               inserMstCompanyContactFamily( param1, commonDao );
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
               inserMstCompanyContactFamily( param1, commonDao );
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
               inserMstCompanyContactFamily( param1, commonDao);
               //System.out.println("insert mst keluarga");
           }
       }

       
       public void inserMstCompanyContactFamily( Map param1, CommonDao commonDao) throws Exception {      
    	   commonDao.inserMstCompanyContactFamily( param1 );          
   }   
       
       public void deleteMstCompanyContactFamily(String strPOClientID, CommonDao commonDao) throws Exception {        
    	   commonDao.deleteMstCompanyContactFamily( strPOClientID );      
   }       
       public void insertMstAddressPic(ContactPerson contactPerson, CommonDao commonDao) throws Exception {
    	   commonDao.insertMstAddressPic( contactPerson );            
       }
           
       
       public void insertMstClientPic(ContactPerson contactPerson, CommonDao commonDao) throws Exception {    
    	   commonDao.insertMstClientPic( contactPerson );         
   }
       
       public Integer updateMstClientPic(ContactPerson contactPerson, CommonDao commonDao) throws Exception {
           Integer result = 0;    
           try {
               result = commonDao.updateMstClientPic( contactPerson );            
           }catch(Exception e){
               result = 0;
               throw e;        
           }
           return result;
       }   
         
       
       
       public Integer updateMstAddressPic(ContactPerson contactPerson, CommonDao commonDao) throws Exception {
           Integer result = 0;    
           try {
               result = commonDao.updateMstAddressPic( contactPerson );
           }catch(Exception e){
               result = 0;
               throw e;        
           }
           return result;
       }
       
       public void insertAddressPemPremiIndividu(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {    
    	   commonDao.insertAddressPemPremiIndividu( pembayarPremi );          
       }   
       
       public void insertNewClientPayerIndividu(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {   
    	   commonDao.insertNewClientPayerIndividu( pembayarPremi );           
       }  
       protected Integer updateNewClientPayerIndividu(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {
           Integer result = 0;    
           try {
               result = commonDao.updateNewClientPayerIndividu( pembayarPremi );          
           }catch(Exception e){
               result = 0;
               throw e;        
           }
           return result;
       }   
       
       protected Integer updateNewClientPayerBadanHukum(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {
           Integer result = 0;    
           result = commonDao.updateNewClientPayerBadanHukum( pembayarPremi );
           return result;
       };
       protected void insertNewClientPayerBadanHukum(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {    
    	   commonDao.insertNewClientPayerBadanHukum( pembayarPremi );     
       };
       
       protected Integer updateAddressPemPremiIndividu(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {
           Integer result = 0;    
           try {
               result = commonDao.updateAddressPemPremiIndividu( pembayarPremi );
           }catch(Exception e){
               result = 0;
               throw e;        
           }
           return result;
       }   
       
       
       protected void insertAddressPemPremiBadanHukum(PembayarPremi pembayarPremi, CommonDao commonDao) throws Exception {
    	   commonDao.insertAddressPemPremiBadanHukum( pembayarPremi );        
       }  
       
       
       protected void updateMstPolicyPayer( HashMap<String, Object> params, CommonDao commonDao ) throws Exception {
    	   commonDao.updateMstPolicyPayer( params );      
       }   
       

   	protected void updateSpajTemp(String no_temp, String reg_spaj,
   			CommonDao commonDao) {
   				// TODO Auto-generated method stub
   		        Map p = new HashMap();
   		        p.put("no_temp", no_temp);
   		        p.put("reg_spaj", reg_spaj);    
   		        commonDao.updateMstSpajTemp(p);        
   		    
   	}
   	
   	   public void updateProductTemp(String no_temp,String reg_spaj, CommonDao commonDao){
   	        Map p = new HashMap();
   	        p.put("no_temp", no_temp);
   	        p.put("reg_spaj", reg_spaj);
   	        commonDao.updateMstPesertaTemp(p);     
   	    }
   	   
   	   protected void updateAddressBillingTemp(String no_temp,String reg_spaj, CommonDao commonDao){
   	        Map p = new HashMap();
   	        p.put("no_temp", no_temp);
   	        p.put("reg_spaj", reg_spaj);    
   	        commonDao.updateMstAddressBillingTemp(p);      
   	    }
   	   
   	   protected void updatePesertaTemp(String no_temp,String reg_spaj, CommonDao commonDao) {
   	        Map p = new HashMap();
   	        p.put("no_temp", no_temp);
   	        p.put("reg_spaj", reg_spaj);
   	        commonDao.updateMstPesertaTemp(p);     
   	    }
   	    
   	   protected Integer selectCountQuestionaireTemp(String no_temp, CommonDao commonDao) throws Exception {
   	        Integer result = 0;    
   	        try {
   	            result = commonDao.selectCountQuestionaireTemp( no_temp );
   	        }catch(Exception e){
   	            result = 0;
   	            
   	        }
   	        return result;
   	       }   
   	   protected Integer selectCountMedquestTemp(String no_temp, CommonDao commonDao) throws Exception {
   	         Integer result = 0;    
   	         try {
   	             result = commonDao.selectCountMedquestTemp( no_temp );
   	         }catch(Exception e){
   	             result = 0;
   	            
   	         }
   	         return result;
   	        }
   	     
   	   protected Integer selectCountbenefTemp(String no_temp, CommonDao commonDao) throws Exception {
   	            Integer result = 0;    
   	            try {
   	                result = commonDao.selectCountbenefTemp( no_temp );
   	            }catch(Exception e){
   	                result = 0;
   	               
   	            }
   	            return result;
   	       }    
   	     
   	   protected String selectCountReffBiiTemp(String no_temp, CommonDao commonDao) throws Exception {
   	         String result = "";    
   	         try {
   	             result = commonDao.selectCountReffBiiTemp( no_temp );
   	         }catch(Exception e){
   	             result = "";     
   	         }
   	         return result;
   	        }
}
