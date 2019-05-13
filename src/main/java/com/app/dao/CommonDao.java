package com.app.dao;

import java.util.HashMap;

public interface CommonDao {
	
	
	 //umum select 
    public String selectkodegutri(HashMap<String, Object> params);
    public HashMap<String, Object> selectRegionalAgen( HashMap<String, Object> params );
    public String selectSpajSeq( HashMap<String, Object> params );
    public String selectSequenceClientID();
	
	

}
