package com.app.utils;

public class f_hit_umur {

	
	public int umur(int tahun1, int bulan1, int tanggal1,int tahun2, int bulan2, int tanggal2) 
	{
		int li_month = 0;
		int li_Umur = 0;
		int li_add = 0;
		int li_curr_month = 0;
		int li_curr_year = 0;
		int li_curr_day = 0;
		//Calendar tgl_sekarang = Calendar.getInstance(); 
		/*li_curr_day   = tgl_sekarang.get(Calendar.DAY_OF_MONTH);
		li_curr_month = tgl_sekarang.get(Calendar.MONTH)+1;
		li_curr_year  = tgl_sekarang.get(Calendar.YEAR);*/
		li_curr_day =tanggal2;
		li_curr_month=bulan2;
		li_curr_year=tahun2;

		if (tahun1 != li_curr_year)
		{
			if (li_curr_month >= bulan1)
			{
				li_Umur = li_curr_year - tahun1;
			}else{
				li_Umur = (li_curr_year - tahun1) - 1;
				li_add = 12;
			}
			li_month = li_curr_month + li_add - bulan1;
			if (li_month >= 6)
			{
				if (li_month==6)
				{
					if ((li_curr_day  - tanggal1) >= 0)
					{
						li_Umur = li_Umur+1;
					}
				}else{
					li_Umur = li_Umur+1;
				}
				
			}
		}
		if (li_Umur<0){
			li_Umur=0;
		}
		return li_Umur;
	}
}
