package com.app.model.product;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.app.services.SetupProductService;

public class Product implements Serializable {
	
	
	public  int ii_usia_tt =0;
	public  int ii_usia_pp=0;
	public  int ii_age_from=0;
	public  int ii_age_to=0;//batas usia masuk
	public  int ii_age=0;
	public  Calendar idt_beg_date  = Calendar.getInstance();
	public  Calendar idt_end_date  = Calendar.getInstance();
	public  Calendar adt_bdate = Calendar.getInstance();
	public  Calendar ldt_end = Calendar.getInstance();
	public  double idec_up=0;
	public  double idec_kurs = 0;
	public  double idec_disc = 0;
	public  int ii_end_from=0; // utk hit end_date 
	public  double idec_min_up01=0;
	public  double idec_min_up02=0;
	public  double idec_max_up01=0;	
	public  double idec_max_up02=0;	
	public  double idec_min_up01_karyawan=0;
	public  double idec_min_up02_karyawan=0;
	public  double idec_kelipatan_up01=1;	
	public  double idec_kelipatan_up02=1;		
	public  String is_kurs_id="";
	public  String is_agen_id="";
	public  double[] idec_pct_list;
	public  int ii_bisnis_id=0;
	public int ii_bisnis_no=0;// bisnis_id = 1..99, bisnis_no 1.1, 40.1
	public  int ii_class=0;
	public int lsr_jenis = 1; //lsr_jenis untuk menarik data rate ke lst_rider, default = 1
	
	public  int[] ii_lama_bayar;
	public  double[] idec_list_premi;  //buat list premi kalo pilih premi
	public  int[] li_pmode;
	public  int[] li_pmode_list;
	public  int ii_contract_period=0;
	public  int li_cp=0;
	public  int ii_bisnis_id_utama=0;
	public  int ii_bisnis_no_utama=0;
	public  int ii_group_id = 0;
	public  boolean ib_single_premium = false;//utk cek; plan ada single premium / tidak
	public  double idec_premi=0;
	public  double idec_rate=0;
	public  int ii_lbayar = 0;
	public  int ii_pmode=0;
	public  int ii_jenis = 1;
	public  int ii_tahun_ke=1;
	public  double ii_permil=1000;
	public  double idec_add_pct=0;
	public  double idec_premi_main=0;
	public  double ldec_rate=0;
	public  double ldec_rate_include=0;
	public  boolean ib_flag_pp = false; //flag utk cek umur ambil dr tt/pp
	public  int[] ii_pmode_list;
	public  int ii_medis = 0;
	public  int ii_sex_tt = 1;
	public  boolean ib_flag_end_age = true; //utk cek; end_date - ii_end_age;
	public  int ii_end_age=0; //79 - issue age
	public  int ii_pay_period =0;
	public  int ii_comm_period=0;
	public  int ii_sex_pp = 1;
	public  String[] is_forex;
	public  String[] lds_1;
	public  int[] li_usia;
	public  int indeks_is_forex=0;
	public  int indeks_idec_pct_list=0;
	public  int indeks_ii_lama_bayar=0;
	public  int indeks_lds_1=0;
	public  int indeks_ii_pmode_list=0;
	public  int indeks_idec_list_premi=0;
	public  int indeks_li_pmode=0;
	public  int indeks_li_pmode_list=0;
	public  int li_jenis=0;
	public  int li_lama=0;
	public  int li_lbayar=0;
	public  int li_umur=0;
	public  long ll_premi=0;
	public  double ldec_up=0;
	public  double ldec_sisa=0;
	public  double ldec_premi_tahunan=0;
	public  int li_ltanggung=0;
	public  String hsl="";
	public  String err="";
	public  int flag_uppremi=0; // isian 0 up, 1 premi, 2 up dan premi
	public  int umur_tahun=0;
	public  int umur_bulan=0;
	public  int umur_hari=0;
	public int samePPTT=0; // 0 PP !=TT, 1 PP == TT
	
	public int simas = 0; // 0 bukan simas dan ttg hanya 1, 1 simas dan ttg lebih dari 1
	
	/*
	 * 0 - tutup (bukan link)
	 * 1 - Power save
	 * 2 - buka fixed & dyna
	 * 3 - buka aggresive
	 * 4 - buka fixed & dyna & aggresive
	 * 5 - buka secured & dyna dollar
	 * 6 - buka syariah fixed & dyanamic syariah 
	 * 7 - buka fixed & dyna & aggresive Syariah
	 * 8 - buka secured & dyna dollar Syariah
	 * 9 - buka artha fixed , dyna , aggressive
	 * 10 buka artha secure dan dynamic
	 * 11 buka stable fund rupiah atau stable fund dollar
	 * 12 buka BPPI Plus Fund-1
	 * 13 buka dyna & aggresive Syariah
	 * 14 muamalat - Mabrur
	 * 15 buka stable fund syariah rupiah dan stable fund syariah dollar
	 * 16 buka stable fund 2 rupiah 
	*/
	public  int kode_flag=0;
	
	public  int flag_class=0;// 0 tidak buka kotak isian klas, 1 buka kotak isian klas
	public  double rate_rider=0;
	
	public  int li_sd = 60;
	public  int li_insured=0;
	public  Date tanggal_sementara=null;
	public  Date tanggal_sementara1=null;
	public  Calendar ldt_edate = Calendar.getInstance();
	public  Calendar ldt_epay  = Calendar.getInstance();
	public  int li_kali = 1;	
	
	//0==> biasa 1==> tidak bisa untuk cara bayar sekaligus
	//public  int flag_cara_bayar=0;
	
	public  double up_pa=0;
	public  double up_pb=0;
	public  double up_pc=0;
	public  double up_pd=0;
	public  double up_pm=0;
	
	//rider include
	public  int indeks_rider_list=0;
	public  int[] ii_rider;
	
	// 0==> rider lama 1==> rider baru
	public  int flag_rider=0;
	
	public  int iiunit=0;//set nilai default unit
	public  int iiclass=0;//set nilai default class
	public  int li_insper = 18;
	public  int li_id = 18; 

	//biaya ulink
	public double mbu_jumlah=0;
	public double mbu_jumlah1=0;
	public double mbu_persen1=0;
	public double mbu_jumlah2=0;
	public double mbu_persen2=0;
	public double mbu_jumlah3=0;
	public double mbu_persen3=0;
	public double mbu_persen=0;
	public double mbu_rate=0;
	public double mbu_jumlah4=0;
	public double mbu_persen4=0;
	
	public double ldec_temp1=0;
	public double ldec_temp2=0;
	public double ldec_temp3=0;
	public double ldec_temp4=0;
	public double ldec_temp5=0;
	public double ldec_temp6=0;
	public double ldec_temp7=0;
	public double ldec_temp8=0;
	public double ldec_temp9=0;	
	
	public double ldec_rate1=0;
	public double ldec_rate2=0;
	public double ldec_rate3=0;
	public double ldec_rate4=0;
	public double ldec_rate5=0;
	public double ldec_rate6=0;
	public double ldec_rate7=0;
	public double ldec_rate8=0;
	public double ldec_rate9=0;	
	
	public int  nomor_rider1=0;
	public int  nomor_rider2=0;
	public int  nomor_rider3=0;
	public int  nomor_rider4=0;
	public int  nomor_rider5=0;
	public int  nomor_rider6=0;
	public int  nomor_rider7=0;
	public int  nomor_rider8=0;
	public int  nomor_rider9=0;
	
	public int unit_rider1=0;
	public int unit_rider2=0;
	public int unit_rider3=0;
	public int unit_rider4=0;
	public int unit_rider5=0;
	public int unit_rider6=0;	
	public int unit_rider7=0;
	public int unit_rider8=0;
	public int unit_rider9=0;
	
	public int class_rider1=0;
	public int class_rider2=0;
	public int class_rider3=0;
	public int class_rider4=0;
	public int class_rider5=0;
	public int class_rider6=0;
	public int class_rider7=0;
	public int class_rider8=0;
	public int class_rider9=0;	

	public  int[] rider_include;
	public  int indeks_rider_include = 0;
	
	//0 ==> umum 1 ==>waiver critical 2 ==> tpd supaya tidak boleh ada ambil yang sama CI dan TPD
	public  int flag_rider_baru=0;
	
	public  int rider_unit=0;
	public  int rider_class=0;
	
	public int flag_jenis_plan=0;
	//0 ==> plan biasa 1==> plan excellink 18 2 ==> plan cerdas 3 ==>plan fast excellink  
	//4 ==>plan  super protection 5 ==>pa stand alone 6==>arthalink 7==>stable link 8 ==> investimax
	//9 ==> plan hidup bahagia
	public int flag_excell80plus =0;
	public int flag_as=0; 
	//0 ==> Plan regional 1  ==> Plan AS 2 ==> Plan Karyawan 3 ==> regional dan as
	public int flag_artha = 0;
	public int flag_cerdas_global = 0; //0 ==>bukan jenis cerdas global, 1 = jenis cerdas global
	
	public int nomor_rider_include=0;
	public Date mspr_beg_date=null;
	public int klases=0;
	public int units=0;	
	public int flag_cerdas_siswa=0; //0 bukan cerdas siswa  1 cerdas siswa (127)
	public int flag_account =2;// 0 untuk umum  1 untuk account recur 2 untuk rek client 3 untuk account recur dan rek client//permintaan bas dan nb semua produk harus input data rek client
	public int flag_uppremiopen=0; // untuk biasa, 1 untuk excellink 80plus
	public int flag_debet = 0;//0 tidak harus autodebet , 1 harus autodebet
	public int flag_default_up =0; //0 tidak default, 1 default UP(ambil dari idec_min_up atau idec_max_up)
	
	public int flag_warning_autodebet = 0;//1 seharusnya autodebet, tapi tidak diblokir bila pilihannya TUNAI, hanya diberikan warning

	public boolean isBungaSimponi=false;// Produk yang input Bunga Simponi (Produk Simponi Rupiah & Produk Simponi Dollar)
	public boolean isBonusTahapan=false;// Produk yang input Bonus Tahapan (Produk Premium Saver & simponi 8 )
	public int flag_reff_bii = 0;
	//Yusuf - 20050203
	public boolean isProductBancass = false; //Produk yang termasuk BancAss, flag digunakan di : (Entry BAC - Reff BII, Transfer - Komisi Reff BII)  
	public boolean isInvestasi = false; //Produk yang termasuk produk investasi / unitlink (beda dengan powersave)
	public int flag_biaya_tambahan = 0; // 0 --> tanpa biaya tambahan untuk menutupi minus investasi, 1 ada tambahan biaya top up untuk menutupi minus
	
	public int li_pct_biaya = 5;
	public int li_trans = 5;
	public int flag_worksite = 0;
	public int usia_nol = 1; // 1--> produk yang umur 0 dianggap 1 
	public int flag_powersave = 0; // lihat fungsi f_flag_rate_powersave
	public int flag_platinumlink = 0; // 0 --> bukan platinum link 1 --> platinum link
	public int flag_cuti_premi = 0; // 0 -- >tidak ada cuti premi 1 --> cuti premi
	public int flag_endowment = 0; // -> bukan endowment 20   1 --> endowment 20
	public int flag_mediplan = 0; // --> 0 bukan mediplan 1 -- mediplan
	public int flag_powersavebulanan = 0; // 0 bukan power save bulanan 1 power save bulanan
	public int flag_medivest = 0;// 0 bukan medivest 1 medivest
	public int indeks_discount = 0; // khusus endowment
	public  double[] discount;
	public int indeks_perusahaan =0;
	public String[] perusahaan;
	public int indeks_mgi=0;
	public int[] mgi;
	public int flag_ekalink = 0; // 0--> bukan ekalink 1 --> ekalink
	public int flag_horison = 0;//0 bukan horison 1 horison
	public int indeks_kombinasi =0;
	public String[] kombinasi;
	
	NumberFormat f = new DecimalFormat("#,##0.00;(#,##0.00)");
	
	
	private SetupProductService setupProductService;
	private GroovyShell sheel;
	private Binding binding;
	
	private HashMap<String,Object> rets;

	private int product;
	private int sub_product;
	private int cb;
	private String kurs;
	private HashMap<String,Object> request;
	public Product(SetupProductService setupProductService,GroovyShell sheel, int product, int sub_product, int cb, String kurs,HashMap<String,Object> request){
		this.setupProductService = setupProductService;
		this.sheel = sheel;
		this.binding = new Binding();
		this.sheel = new GroovyShell(binding);
		this.binding.setVariable("db", setupProductService);
		this.rets = new HashMap<String,Object>();
		this.product = product;
		this.sub_product = sub_product;
		this.cb = cb;
		this.kurs = kurs;
		this.request = request;
		this.setInitVariable(product, sub_product,this.request);
	}
	
	
	private void setInitVariable(int product, int sub_product,HashMap<String,Object> request){
		List<HashMap<String,Object>> list = setupProductService.selectNProdVar(product, sub_product);
		System.out.println("product"+product+"msize"+list.size());
		for(HashMap m:list){
			String variableName = (String)m.get("VARIABLE_NAME");
			String variableType = (String)m.get("VARIABLE_TYPE");
			String variableValue = (String)m.get("VARIABLE_VALUE");
			BigDecimal flag_return = (BigDecimal)m.get("FLAG_RETURN");
			BigDecimal is_from_request = (BigDecimal)m.get("IS_FROM_REQUEST");
			if(is_from_request.intValue() == 1){
				variableValue = (String)request.get(variableName);
			}
			if(variableType.equals("INT")){
				this.binding.setVariable(variableName, Integer.parseInt(variableValue));
			}
			if(variableType.equals("STRING")){
				this.binding.setVariable(variableName,variableValue);
			}
			
			if(variableType.equals("DOUBLE")){
				System.out.println("error:"+variableName+":"+variableValue);
				this.binding.setVariable(variableName,Double.parseDouble(variableValue));
			}
			if(flag_return.intValue() == 1){
				rets.put(variableName, variableValue);
			};
		}
	}
	
	
	
	public void proses(int typeProses){
	
		
		if(typeProses == 1){
				List<HashMap<String,Object>>	list = this.setupProductService.selectNProdFunctionOrderBy(this.product, this.sub_product, this.cb, this.kurs);
				for(HashMap<String,Object> m:list){
					String function_name = (String)m.get("FUNCTION_NAME");
					String function_scripts = (String)m.get("FUNCTION_SCRIPTS");
					System.out.println("function scripts"+function_scripts);
					if(function_scripts != null){
						sheel.evaluate(function_scripts);
					}
				}
		};
	}
	
	
	
	
	public HashMap<String,Object> getRets(){
		HashMap<String,Object> ret = new HashMap<String, Object>();
		for(String key:rets.keySet()){
			Object value = this.binding.getVariable(key);
			ret.put(key, value);
		}
		return ret;
	}
	
	
	
}
