package com.app.rest;

import java.security.NoSuchAlgorithmException;
import java.security.Provider.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.core.ProcessPas;
import com.app.dao.CommonDao;
import com.app.model.bsim.Pas;
import com.app.model.gadget.prod.User;
import com.app.request.AJSMSIG;
import com.app.request.Request;
import com.app.response.BankSinarmasSyariahResponse;
import com.app.services.BsimService;
import com.app.utils.CommonUtil;
import com.app.utils.HashText;
import com.app.utils.f_hit_umur;
import com.msig.utils.Common;

@RestController
@RequestMapping("api")
public class RestPaBankSinarmas {
	
	@Autowired
	private BsimService bsimService;
	
	private static Logger logger = Logger.getLogger(RestPaBankSinarmas.class);
	
	
	 
	@RequestMapping(value="/submit", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BankSinarmasSyariahResponse> submit(@RequestBody Request bankSinarmasSyariahRequest) throws ParseException{
		 BankSinarmasSyariahResponse bss = new BankSinarmasSyariahResponse("message","result", null);
		 boolean requestIsValid = true;
		 AJSMSIG ajsMsig = null;
		 if(bankSinarmasSyariahRequest == null){
			 bss.setMessage("Request is not valid");
			 bss.setResult("Failed");
		 }else{
			 if(bankSinarmasSyariahRequest.getAjsmsig() == null){
				 bss.setMessage("Request is not valid");
				 bss.setResult("Failed");
				 requestIsValid = false;
			 }else{
				 ajsMsig = bankSinarmasSyariahRequest.getAjsmsig();
				 requestIsValid = true;
			 }
			 
				 
			 
		 }
		
		 
		 
		 if(!requestIsValid){
			 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
			 return response;
		 }
		 
		 boolean signatureInValid = true;
		 
		 if(ajsMsig.getSignature() != null){
			  if(!StringUtils.isEmpty(ajsMsig.getSignature())){
				  signatureInValid = false;
			  }
		  }
		 
		 if(signatureInValid){
			 bss.setMessage("Signature required");
			 bss.setResult("Failed");
			 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
			 return response;
		 }
		 
		 
		 String signature = ajsMsig.getSignature();
		 String clientId = ajsMsig.getClient_id();
		 String custIdentityNumber = ajsMsig.getCust_identity_number();
	     try {
			String compareSig = HashText.sha1(clientId + custIdentityNumber + "P@ssW0rd#PA#BSIM");
			 if(!signature.equals(compareSig)) {
				 bss.setMessage("Signature tidak sama, mohon dicek kembali");
				 bss.setResult("Failed");
				 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
				 return response;
			 };
	     } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	        
		boolean clean = true;
	
	    if(ajsMsig.getRequest().trim().equals("PA BSIM")){
	    	ArrayList<String> errors = validatePaBsim(ajsMsig);
	    	if(errors.size() > 0){
	    		 clean = false;
	    		 bss.setMessage("Error validasi");
				 bss.setResult("Failed");
				 bss.setData(errors);
				 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
				 return response;
			
	    	}
	    }
	    
	    if(clean){
	    	logger.info("clean ::"+clean);
	    	int requestType = -1;
	    	try{
	    		requestType = Integer.parseInt(ajsMsig.getRequest_type().trim());
	    	}catch(NumberFormatException e){
	    		clean = false;
	    		 bss.setMessage("Error Parsing to Number");
				 bss.setResult("Failed");
				 bss.setData(e.getMessage());
				 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
				 return response;
	    	};
	    	
	    	if(requestType == 1){
	    		CommonDao commonDao = bsimService.getCommonDao();
				PlatformTransactionManager transactionManager = bsimService.getTransactionManager();
				/*	TransactionDefinition txDef = new DefaultTransactionDefinition();
		        TransactionStatus txStatus = transactionManager.getTransaction(txDef);*/
				
	    		DateFormat df = new SimpleDateFormat("yyyyMMdd");
	    		Pas pas = new Pas();
	    		pas.setCif(ajsMsig.getCust_cif().trim());
                pas.setMsp_pas_nama_pp(ajsMsig.getCust_name().trim());
                pas.setMsp_full_name(ajsMsig.getCust_name().trim());
                pas.setMsp_sex_pp(Integer.parseInt(ajsMsig.getCust_sex()));
                pas.setMsp_sex_tt(Integer.parseInt(ajsMsig.getCust_sex()));
                Date dob = new SimpleDateFormat("yyyymmdd").parse(ajsMsig.getCust_dob());
                pas.setMsp_pas_dob_pp(dob);
                dob = new SimpleDateFormat("yyyymmdd").parse(ajsMsig.getCust_dob());
                pas.setMsp_date_of_birth(dob);
                pas.setMsp_identity_no(ajsMsig.getCust_identity_number());
                pas.setMsp_identity_no_tt(ajsMsig.getCust_identity_number());
                pas.setMsp_address_1(ajsMsig.getCust_address().trim());
                pas.setMsp_city(ajsMsig.getCust_city().trim());
                pas.setMsp_postal_code(ajsMsig.getCust_postal_code().trim());
                pas.setMsp_pas_phone_number(ajsMsig.getCust_phone().trim());
                pas.setMsp_mobile(ajsMsig.getCust_mobile().trim());
                pas.setMsp_pas_email(ajsMsig.getCust_email().trim());
                pas.setMsp_no_rekening(ajsMsig.getCust_account().trim());
                pas.setMsp_rek_nama(ajsMsig.getCust_name().trim());
                pas.setMsp_rek_nama_autodebet(ajsMsig.getCust_name().trim());
                pas.setMsp_no_rekening_autodebet(ajsMsig.getCust_account().trim());
              
                pas.setPribadi(0);
                pas.setLscb_id(Integer.parseInt(ajsMsig.getLscb_id().trim())); // tahunan only
                pas.setLsbp_id("224");
                HashMap<String, Object> bank = (HashMap<String, Object>) bsimService.selectBankPusat(pas.getLsbp_id());
                pas.setLsbp_nama((String) bank.get("BANK_NAMA"));
                pas.setLsbp_id_autodebet("224");
                HashMap<String, Object> bankAutoDebet = (HashMap<String, Object>) bsimService.selectBankPusat(pas.getLsbp_id());
                pas.setLsbp_nama_autodebet((String) bankAutoDebet.get("BANK_NAMA"));
                // Set beg date & end date
                Calendar begDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                endDate.add(Calendar.YEAR, 1);
                endDate.add(Calendar.DATE, -1);
                pas.setMsp_pas_beg_date(begDate.getTime());
                pas.setMsp_pas_end_date(endDate.getTime());
                
                
                // Produk
                pas.setProduct_code(ajsMsig.getProduct_code());
                pas.setProduk(Integer.parseInt(ajsMsig.getSub_product_code()));
                pas.setLsre_id(1);
                pas.setMsp_flag_cc(2);
                pas.setMsp_tgl_debet(begDate.getTime());
                pas.setMsp_tgl_valid(begDate.getTime());
                
                
                pas.setLus_id(0);
                pas.setLus_login_name("SYSTEM");
                pas.setJenis_pas("PABSMSY");
                
                String no_va = bsimService.selectBsimNoVaSyariah();
                pas.setNo_va(no_va);
                pas.setLspd_id(1);
				pas.setLssp_id(10);
				pas.setDist("05");
				pas.setMsp_kode_sts_sms("00");
				pas.setMsp_pas_create_date(bsimService.selectSysdate());
				pas.setMsag_id(ajsMsig.getMsag_id());
				pas.setMsp_up(ajsMsig.getUp());
				
				pas.setPremi(getPremi(pas) + "");
				pas.setMsp_premi(pas.getPremi());
				try {
					Pas p = bsimService.insert(pas);
					
					no_va =pas.getNo_va();
					if(no_va != null){
						String no_temp = pas.getMsp_fire_id();
						bsimService.update_no_va(no_va, no_temp);
					}
					
					ProcessPas processPas = new ProcessPas(commonDao, transactionManager);
					User currentUser = new User();
					currentUser.setLus_id(new String("0"));
					currentUser.setJn_bank(new Integer(1));
				    processPas.processPas("update", pas, "input", currentUser);
					
					
					
				} catch (Exception e) {
					System.out.println("hehehe");
					// TODO Auto-generated catch block
					e.printStackTrace();
				//	 transactionManager.commit(txStatus);
				}
				
				
				
				
				
				
				
                
	    	}
	    	
	    }
	     
	     
		 
		 
		 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
		 return response;
	}
	
	
	private ArrayList<String> validatePaBsim(AJSMSIG ajsMsig) {
		   ArrayList<String> error = new ArrayList<String>();
		   
		   
		   if(ajsMsig.getCust_name() != null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_name())){
				   error.add("Nama customer harus diisi");
			   }
		   }else{
			   error.add("Nama customer harus diisi");
		   }
				   
		   if(ajsMsig.getCust_sex() != null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_sex())){
				   error.add("Jenis kelamin customer harus diisi");
			   }
		   }else{
			   error.add("Jenis kelamin customer harus diisi");
		   }
		   
		   
		   if(ajsMsig.getCust_dob() != null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_dob())){
				   error.add("Tanggal lahir customer harus diisi");
			   }else{
				    DateFormat df = new SimpleDateFormat("yyyyMMdd");
	                Calendar sysdate = Calendar.getInstance();
	                Calendar dob = Calendar.getInstance();
	                
	                try {
	                    dob.setTime(df.parse(ajsMsig.getCust_dob()));
	                    f_hit_umur fHitUmur = new f_hit_umur();
	                    Integer usia = fHitUmur.umur(dob.get(Calendar.YEAR), dob.get(Calendar.MONTH), dob.get(Calendar.DATE), sysdate.get(Calendar.YEAR), sysdate.get(Calendar.MONTH), sysdate.get(Calendar.DATE));
	                    
	                    if(!(usia >= 17 && usia <= 60)) {
	                        error.add("Umur customer minimal 17 tahun & maksimal 60 tahun");
	                    }
	                } catch (ParseException e) {
	                    error.add("Format tanggal lahir customer tidak valid");
	                }
	                
	                
			   }
		   }else{
			   error.add("Tanggal lahir customer harus diisi");
		   }
		   
		   
		   if(ajsMsig.getCust_identity_number() != null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_identity_number())){
				   error.add("No. Identitas customer harus diisi");
			   }
		   }else{ error.add("No. Identitas customer harus diisi");
	        }
		   
		   
		   if(ajsMsig.getCust_address() != null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_address())){
				   error.add("Alamat customer harus diisi");
			   }
		   }else{ error.add("Alamat customer harus diisi");
	        }
		   
		   
		   if(ajsMsig.getCust_city()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_city())){
				   error.add("Kota Customer harus di isi");
			   }
		   }else{ error.add("Kota Customer harus di isi");
	        }
		   
		   
		   if(ajsMsig.getCust_postal_code()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_postal_code())){
				   error.add("Kode pos customer harus diisi");
			   }
		   }else{  error.add("Kode pos customer harus diisi");
	        
		   
		   }
		   
   
		   
		   if(ajsMsig.getCust_phone()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_phone())){
				   error.add("No. telepon customer harus diisi");
			   }else{
				   if(!CommonUtil.validPhone(ajsMsig.getCust_phone()))
	                    error.add("No. telepon customer harus diisi angka");
	                else if(ajsMsig.getCust_phone().length() > 20)
	                    error.add("No. telepon customer max 20 digit");
			   }
		   }else{    error.add("No. telepon customer harus diisi");
	        }
		   
		   
		   if(ajsMsig.getCust_mobile()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_mobile())){
				   error.add("No. HP customer harus diisi angka");
			   }else{
				   if(!CommonUtil.validPhone(ajsMsig.getCust_mobile()))
	                    error.add("No. HP customer harus diisi angka");
	                else if(ajsMsig.getCust_mobile().length() > 20)
	                    error.add("No. HP customer harus diisi angka");
			   }
		   }else{    
			   error.add("No. HP customer harus diisi angka");
	        
		   }
		   if(ajsMsig.getCust_email()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_email())){
				   error.add("Email customer harus diisi");
			   }else{
				   if(!CommonUtil.isEmailValid(ajsMsig.getCust_email().trim().toLowerCase()))
	                    error.add("Email customer tidak valid");
			   }
		   }else{    
			   error.add("Email customer harus diisi");
	        
		   }
		   
		   if(ajsMsig.getCust_account()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getCust_email())){
				   error.add("No. rekening customer harus diisi");
			   }else{
				   if(!CommonUtil.isEmailValid(ajsMsig.getCust_email().trim().toLowerCase()))
	                    error.add("No. rekening customer harus diisi");
			   }
		   }else{    
			   error.add("No. rekening customer harus diisi");
	        
		   }
		   

		   if(ajsMsig.getPremium()!= null){
			   if(StringUtils.isEmpty(ajsMsig.getPremium())){
				   error.add("Jumlah premi harus diisi");
			   };
		   }else{    
			   error.add("Jumlah premi harus diisi");
	       }
		   
		   return error;
		
	}
	
	
	private static int getPremi(Pas pas){
		Double premi;
		Integer cb =pas.getLscb_id();
		int product = pas.getProduk();
		String product_code = pas.getProduct_code();
		
		Double up = 0.0;
		Double premiTahunan=0.0;
		Double pembagi = new Double(1.0);
		
		if(product_code.equals("205")){
			if(product == 9){
			  up = new Double("100000000");
   			  pas.setMsp_up("100000000");
   			  premiTahunan  = new Double("300000");
   			  
			}else if(product == 10){
			  up = new Double("50000000");
			  pas.setMsp_up("50000000");
   			  premiTahunan  = new Double("500000");
   			  
			}else if(product == 11){
			 up = new Double("100000000");
			  pas.setMsp_up("100000000");
   			  premiTahunan  = new Double("900000");
   			  
			}else if(product == 12){
			 up = new Double("200000000");
			 pas.setMsp_up("200000000");
			  premiTahunan  = new Double("1600000");
			}
		}
		
		
		double rate = 0.00;
		if(cb == 3 ){
			rate = 1.0;
		}else if(cb == 2){
			rate = 0.525;
		}else if(cb == 1){
			rate = 0.27;
		}else if(cb == 6){
			rate = 0.1;
		}
		premi = (premiTahunan * rate) / pembagi;
		premi = Math.ceil(premi);
		return premi.intValue();
	}
}
