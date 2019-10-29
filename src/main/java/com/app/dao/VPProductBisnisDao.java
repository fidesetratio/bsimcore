package com.app.dao;

import java.util.HashMap;
import java.util.List;

public interface VPProductBisnisDao {
	   List<HashMap<String, Object>> selectVpProductBisnis(HashMap<String, Object> params);
	   List<HashMap<String, Object>> selectMstLanguangeScripts(HashMap<String, Object> params);
	   List<HashMap<String, Object>> selectNProdVar(HashMap<String, Object> params);
	   List<HashMap<String, Object>> selectNProdFunction(HashMap<String, Object> params);
	   List<HashMap<String, Object>> selectNProdFunctionOrderBy(HashMap<String, Object> params);
	   Double selectNilaiNew(HashMap<String,Object> params);
	   Double selectNilai(HashMap<String,Object> params);
	   Double selectPremiSuperSehat(HashMap<String,Object> params);
}
