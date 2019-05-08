package com.app.rest;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.request.AJSMSIG;
import com.app.request.Request;
import com.app.response.BankSinarmasSyariahResponse;
import com.app.utils.CommonUtil;
import com.app.utils.HashText;
import com.app.utils.f_hit_umur;

@RestController
@RequestMapping("api")
public class RestPaBankSinarmas {
	
	
	private static Logger logger = Logger.getLogger(RestPaBankSinarmas.class);
	
	
	 
	@RequestMapping(value="/submit", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BankSinarmasSyariahResponse> submit(@RequestBody Request bankSinarmasSyariahRequest){
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
	    		 bss.setMessage("Signature required");
				 bss.setResult("Failed");
				 bss.setData(errors);
				 ResponseEntity<BankSinarmasSyariahResponse> response  = new ResponseEntity<BankSinarmasSyariahResponse>(bss,HttpStatus.OK);
				 return response;
			
	    	}
	    }
	    
	    if(clean){
	    	logger.info("clean ::"+clean);
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
}
