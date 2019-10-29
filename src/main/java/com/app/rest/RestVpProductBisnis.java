package com.app.rest;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.request.ProductRequest;
import com.app.request.ProductResponse;
import com.app.services.CommonDaoService;
import com.app.services.SetupProductService;
import com.itextpdf.text.pdf.StringUtils;

@RestController
@RequestMapping("api")
public class RestVpProductBisnis {
	
	@Autowired
	private SetupProductService setupProductService;
	
	
	@Autowired
	private CommonDaoService commonService;
	
	@RequestMapping(value="/checkingall", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponse> checkingall(@RequestBody ProductRequest request) throws ParseException{
		ProductResponse productResponse = new ProductResponse();
		int lsbs_id = request.getLsbs_id();
		int lsdbs_number = request.getLsdbs_number();
		int paymode = Integer.parseInt(request.getPaymode());
		String kurs = "01";
		
		List<String> functions = new ArrayList<String>();
		functions.add("F_SUM_INSURED");
		functions.add("F_MIN_SUM_INSURED");
		functions.add("F_MAX_SUM_INSURED");
		
		List<String> scripts = new ArrayList<String>();
		scripts.add("F_MIN_SUM_INSURED_ORI");
		scripts.add("F_MAX_SUM_INSURED_ORI");
		
		
		System.out.println("hehehe.."+lsbs_id+""+lsdbs_number+paymode+kurs);
		
		List<HashMap<String,Object>> listScripts = setupProductService.selectMstLanguangeScripts(lsbs_id, lsdbs_number, paymode, kurs);
		List<HashMap<String,Object>> list = setupProductService.selectVpProductBisnis(lsbs_id, lsdbs_number, paymode, kurs);
		
		if(list.size()>0){
			
			HashMap<String,Object> entity = list.get(0);
			HashMap<String,Object> entityScript = listScripts.get(0);
			
			HashMap<String,Object> params = request.getParams();
			Binding binding = new Binding();
			GroovyShell shell = new GroovyShell(binding);
			
			for(String k:params.keySet()){
					String value = (String)params.get(k);
					if(org.apache.commons.lang3.StringUtils.isNumeric(value)){
						binding.setVariable(k.toUpperCase(), Integer.parseInt(value));
					}
			}
			
			binding.setVariable("min_up1",0);
			binding.setVariable("max_up1",0);
			
			HashMap<String,Object> resultQuery = initializeParameter(functions, params, entity);
			initializeScript(scripts, entityScript,shell);
			productResponse.setResult(resultQuery);
			resultQuery.put("min_up1", binding.getVariable("min_up1"));
			resultQuery.put("max_up1", binding.getVariable("max_up1"));
			
			
			
			
		}
		
		
		ResponseEntity<ProductResponse> response  = new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
	    return response;
	}
	
	private void initializeScript(List<String> scripts,HashMap<String,Object> entityScript,GroovyShell groovySheel){
		for(String script:scripts){
			String content = (String)entityScript.get(script);
			if(content != null)
			groovySheel.evaluate(content);
		}
	}
	
	
	private HashMap<String,Object> initializeParameter(List<String> functions, HashMap<String,Object> params,HashMap<String,Object> entity){
			HashMap<String,Object> parameter = new HashMap<>();
			for(String function:functions){
				String content = (String)entity.get(function);
				for(String param:params.keySet()){
						String k = "$P{"+param.toUpperCase()+"}";
						content = content.replace(k, (String)params.get(param));
				};
			
				HashMap<String,Object> result = commonService.selectCommon(content);
				parameter.put(function, result);
			}
			
			return parameter;
	}

}
