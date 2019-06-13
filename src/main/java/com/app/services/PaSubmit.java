package com.app.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.model.gadget.prod.Cmdeditbac;
import com.app.model.gadget.prod.User;
import com.app.utils.CommonUtil;

public class PaSubmit extends AbstractSubmit {

	public PaSubmit(PlatformTransactionManager platform, SqlSession sqlSession) {
		super(platform, sqlSession);
	
	}

	@Override
	public Cmdeditbac save(Cmdeditbac edit) throws Exception {
		this.init();
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

}
