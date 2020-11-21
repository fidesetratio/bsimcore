package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;


public class MstProductTemp implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 5, 2014 (11:21:02 PM)
	 */
	private static final long serialVersionUID = 8682655329411711842L;
	private String no_temp;
	private String reg_spaj;
	private Integer mste_insured_no;	
	private Integer lsbs_id;
	private Integer lsdbs_number;
	private String lku_id;
	private Date mspr_beg_date;	
	private Date mspr_end_date;
	private BigDecimal mspr_tsi;
	private BigDecimal mspr_tsi_pa_a;
	private BigDecimal mspr_tsi_pa_b;
	private BigDecimal mspr_tsi_pa_c;
	private BigDecimal mspr_tsi_pa_d;
	private BigDecimal mspr_tsi_pa_m;
	private Integer mspr_class;
	private Integer mspr_unit;
	private BigDecimal mspr_rate;
	private Integer mspr_persen;
	private BigDecimal mspr_premium;
	private BigDecimal mspr_discount;
	private String mspr_ref_no;
	private Integer mspr_active;
	private BigDecimal mspr_extra;
	private Integer mspr_ins_period;
	private BigDecimal mspr_bunga_kpr;
	private Integer mspr_ins_bulan;
	private BigDecimal mspr_sisa_limit;
	private Integer mspr_wait_period;
	private BigDecimal mspr_ujroh;
	private BigDecimal mspr_tabarru;
	private Date mspr_end_pay;
	private Integer mspr_tt;
	private Integer mspr_lpinjam;
	private Integer mspr_pu_ptb;
	private Date mspr_nonactive_date;
	private Integer flag_up_sc;
	private Integer mspo_pay_period;
	private Integer mspo_ins_period;
	private Integer cara_premi;
	private String kombinasi;
	private Integer flag_packet;

	public MstProductTemp(){};
	
	public MstProductTemp(HashMap map){
		if(map.get("NO_TEMP")!=null)				no_temp = (String) map.get("NO_TEMP");
		if(map.get("REG_SPAJ")!=null)				reg_spaj = (String) map.get("REG_SPAJ");
		if(map.get("MSTE_INSURED_NO")!=null)		mste_insured_no = new BigDecimal(map.get("MSTE_INSURED_NO").toString()).intValue();
		if(map.get("LSBS_ID")!=null)				lsbs_id = new BigDecimal(map.get("LSBS_ID").toString()).intValue();
		if(map.get("LSDBS_NUMBER")!=null)			lsdbs_number = new BigDecimal(map.get("LSDBS_NUMBER").toString()).intValue();
		if(map.get("LKU_ID")!=null)					lku_id = (String) map.get("LKU_ID");
		if(map.get("MSPR_BEG_DATE")!=null)			mspr_beg_date = (Date) map.get("MSPR_BEG_DATE");
		if(map.get("MSPR_END_DATE")!=null)			mspr_end_date = (Date) map.get("MSPR_END_DATE");
		if(map.get("MSPR_TSI")!=null)				mspr_tsi = new BigDecimal(map.get("MSPR_TSI").toString());
		if(map.get("MSPR_TSI_PA_A")!=null)			mspr_tsi_pa_a = new BigDecimal(map.get("MSPR_TSI_PA_A").toString());
		if(map.get("MSPR_TSI_PA_B")!=null)			mspr_tsi_pa_b = new BigDecimal(map.get("MSPR_TSI_PA_B").toString());
		if(map.get("MSPR_TSI_PA_C")!=null)			mspr_tsi_pa_c = new BigDecimal(map.get("MSPR_TSI_PA_C").toString());
		if(map.get("MSPR_TSI_PA_D")!=null)			mspr_tsi_pa_d = new BigDecimal(map.get("MSPR_TSI_PA_D").toString());
		if(map.get("MSPR_TSI_PA_M")!=null)			mspr_tsi_pa_m = new BigDecimal(map.get("MSPR_TSI_PA_M").toString());
		if(map.get("MSPR_CLASS")!=null)				mspr_class = new BigDecimal(map.get("MSPR_CLASS").toString()).intValue();
		if(map.get("MSPR_UNIT")!=null)				mspr_unit = new BigDecimal(map.get("MSPR_UNIT").toString()).intValue();
		if(map.get("MSPR_RATE")!=null)				mspr_rate = new BigDecimal(map.get("MSPR_RATE").toString());
		if(map.get("MSPR_PERSEN")!=null)			mspr_persen = new BigDecimal(map.get("MSPR_PERSEN").toString()).intValue();
		if(map.get("MSPR_PREMIUM")!=null)			mspr_premium = new BigDecimal(map.get("MSPR_PREMIUM").toString());
		if(map.get("MSPR_DISCOUNT")!=null)			mspr_discount = new BigDecimal(map.get("MSPR_DISCOUNT").toString());
		if(map.get("MSPR_REF_NO")!=null)			mspr_ref_no = (String) map.get("MSPR_REF_NO");
		if(map.get("MSPR_ACTIVE")!=null)			mspr_active = new BigDecimal(map.get("MSPR_ACTIVE").toString()).intValue();
		if(map.get("MSPR_EXTRA")!=null)				mspr_extra = new BigDecimal(map.get("MSPR_EXTRA").toString());
		if(map.get("MSPR_INS_PERIOD")!=null)		mspr_ins_period = new BigDecimal(map.get("MSPR_INS_PERIOD").toString()).intValue();
		if(map.get("MSPR_BUNGA_KPR")!=null)			mspr_bunga_kpr = new BigDecimal(map.get("MSPR_BUNGA_KPR").toString());
		if(map.get("MSPR_INS_BULAN")!=null)			mspr_ins_bulan = new BigDecimal(map.get("MSPR_INS_BULAN").toString()).intValue();
		if(map.get("MSPR_SISA_LIMIT")!=null)		mspr_sisa_limit = new BigDecimal(map.get("MSPR_SISA_LIMIT").toString());
		if(map.get("MSPR_WAIT_PERIOD")!=null)		mspr_wait_period = new BigDecimal(map.get("MSPR_WAIT_PERIOD").toString()).intValue();
		if(map.get("MSPR_UJROH")!=null)				mspr_ujroh = new BigDecimal(map.get("MSPR_UJROH").toString());
		if(map.get("MSPR_TABARRU")!=null)			mspr_tabarru = new BigDecimal(map.get("MSPR_TABARRU").toString());
		if(map.get("MSPR_END_PAY")!=null)			mspr_end_pay = (Date) map.get("MSPR_END_PAY");
		if(map.get("MSPR_TT")!=null)				mspr_tt = new BigDecimal(map.get("MSPR_TT").toString()).intValue();
		if(map.get("MSPR_LPINJAM")!=null)			mspr_lpinjam = new BigDecimal(map.get("MSPR_LPINJAM").toString()).intValue();
		if(map.get("MSPR_PU_PTB")!=null)			mspr_pu_ptb = new BigDecimal(map.get("MSPR_PU_PTB").toString()).intValue();
		if(map.get("MSPR_NONACTIVE_DATE")!=null)	mspr_nonactive_date = (Date) map.get("MSPR_NONACTIVE_DATE");
		if(map.get("FLAG_UP_SC")!=null)				flag_up_sc = new BigDecimal(map.get("FLAG_UP_SC").toString()).intValue();
		if(map.get("MSPO_PAY_PERIOD")!=null)		mspo_pay_period = new BigDecimal(map.get("MSPO_PAY_PERIOD").toString()).intValue();
		if(map.get("MSPO_INS_PERIOD")!=null)		mspo_ins_period = new BigDecimal(map.get("MSPO_INS_PERIOD").toString()).intValue();
		if(map.get("CARA_PREMI")!=null)	        	cara_premi = new BigDecimal(map.get("CARA_PREMI").toString()).intValue();
		if(map.get("KOMBINASI")!=null)			    kombinasi = (String) map.get("KOMBINASI");
        if(map.get("FLAG_PACKET")!=null)             flag_packet = new BigDecimal(map.get("FLAG_PACKET").toString()).intValue();
	}

	public String getNo_temp() {
		return no_temp;
	}

	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}

	public String getReg_spaj() {
		return reg_spaj;
	}

	public void setReg_spaj(String reg_spaj) {
		this.reg_spaj = reg_spaj;
	}

	public Integer getMste_insured_no() {
		return mste_insured_no;
	}

	public void setMste_insured_no(Integer mste_insured_no) {
		this.mste_insured_no = mste_insured_no;
	}

	public Integer getLsbs_id() {
		return lsbs_id;
	}

	public void setLsbs_id(Integer lsbs_id) {
		this.lsbs_id = lsbs_id;
	}

	public Integer getLsdbs_number() {
		return lsdbs_number;
	}

	public void setLsdbs_number(Integer lsdbs_number) {
		this.lsdbs_number = lsdbs_number;
	}

	public String getLku_id() {
		return lku_id;
	}

	public void setLku_id(String lku_id) {
		this.lku_id = lku_id;
	}

	public Date getMspr_beg_date() {
		return mspr_beg_date;
	}

	public void setMspr_beg_date(Date mspr_beg_date) {
		this.mspr_beg_date = mspr_beg_date;
	}

	public Date getMspr_end_date() {
		return mspr_end_date;
	}

	public void setMspr_end_date(Date mspr_end_date) {
		this.mspr_end_date = mspr_end_date;
	}

	public BigDecimal getMspr_tsi() {
		return mspr_tsi;
	}

	public void setMspr_tsi(BigDecimal mspr_tsi) {
		this.mspr_tsi = mspr_tsi;
	}

	public BigDecimal getMspr_tsi_pa_a() {
		return mspr_tsi_pa_a;
	}

	public void setMspr_tsi_pa_a(BigDecimal mspr_tsi_pa_a) {
		this.mspr_tsi_pa_a = mspr_tsi_pa_a;
	}

	public BigDecimal getMspr_tsi_pa_b() {
		return mspr_tsi_pa_b;
	}

	public void setMspr_tsi_pa_b(BigDecimal mspr_tsi_pa_b) {
		this.mspr_tsi_pa_b = mspr_tsi_pa_b;
	}

	public BigDecimal getMspr_tsi_pa_c() {
		return mspr_tsi_pa_c;
	}

	public void setMspr_tsi_pa_c(BigDecimal mspr_tsi_pa_c) {
		this.mspr_tsi_pa_c = mspr_tsi_pa_c;
	}

	public BigDecimal getMspr_tsi_pa_d() {
		return mspr_tsi_pa_d;
	}

	public void setMspr_tsi_pa_d(BigDecimal mspr_tsi_pa_d) {
		this.mspr_tsi_pa_d = mspr_tsi_pa_d;
	}

	public BigDecimal getMspr_tsi_pa_m() {
		return mspr_tsi_pa_m;
	}

	public void setMspr_tsi_pa_m(BigDecimal mspr_tsi_pa_m) {
		this.mspr_tsi_pa_m = mspr_tsi_pa_m;
	}

	public Integer getMspr_class() {
		return mspr_class;
	}

	public void setMspr_class(Integer mspr_class) {
		this.mspr_class = mspr_class;
	}

	public Integer getMspr_unit() {
		return mspr_unit;
	}

	public void setMspr_unit(Integer mspr_unit) {
		this.mspr_unit = mspr_unit;
	}

	public BigDecimal getMspr_rate() {
		return mspr_rate;
	}

	public void setMspr_rate(BigDecimal mspr_rate) {
		this.mspr_rate = mspr_rate;
	}

	public Integer getMspr_persen() {
		return mspr_persen;
	}

	public void setMspr_persen(Integer mspr_persen) {
		this.mspr_persen = mspr_persen;
	}

	public BigDecimal getMspr_premium() {
		return mspr_premium;
	}

	public void setMspr_premium(BigDecimal mspr_premium) {
		this.mspr_premium = mspr_premium;
	}

	public BigDecimal getMspr_discount() {
		return mspr_discount;
	}

	public void setMspr_discount(BigDecimal mspr_discount) {
		this.mspr_discount = mspr_discount;
	}

	public String getMspr_ref_no() {
		return mspr_ref_no;
	}

	public void setMspr_ref_no(String mspr_ref_no) {
		this.mspr_ref_no = mspr_ref_no;
	}

	public Integer getMspr_active() {
		return mspr_active;
	}

	public void setMspr_active(Integer mspr_active) {
		this.mspr_active = mspr_active;
	}

	public BigDecimal getMspr_extra() {
		return mspr_extra;
	}

	public void setMspr_extra(BigDecimal mspr_extra) {
		this.mspr_extra = mspr_extra;
	}

	public Integer getMspr_ins_period() {
		return mspr_ins_period;
	}

	public void setMspr_ins_period(Integer mspr_ins_period) {
		this.mspr_ins_period = mspr_ins_period;
	}

	public BigDecimal getMspr_bunga_kpr() {
		return mspr_bunga_kpr;
	}

	public void setMspr_bunga_kpr(BigDecimal mspr_bunga_kpr) {
		this.mspr_bunga_kpr = mspr_bunga_kpr;
	}

	public Integer getMspr_ins_bulan() {
		return mspr_ins_bulan;
	}

	public void setMspr_ins_bulan(Integer mspr_ins_bulan) {
		this.mspr_ins_bulan = mspr_ins_bulan;
	}

	public BigDecimal getMspr_sisa_limit() {
		return mspr_sisa_limit;
	}

	public void setMspr_sisa_limit(BigDecimal mspr_sisa_limit) {
		this.mspr_sisa_limit = mspr_sisa_limit;
	}

	public Integer getMspr_wait_period() {
		return mspr_wait_period;
	}

	public void setMspr_wait_period(Integer mspr_wait_period) {
		this.mspr_wait_period = mspr_wait_period;
	}

	public BigDecimal getMspr_ujroh() {
		return mspr_ujroh;
	}

	public void setMspr_ujroh(BigDecimal mspr_ujroh) {
		this.mspr_ujroh = mspr_ujroh;
	}

	public BigDecimal getMspr_tabarru() {
		return mspr_tabarru;
	}

	public void setMspr_tabarru(BigDecimal mspr_tabarru) {
		this.mspr_tabarru = mspr_tabarru;
	}

	public Date getMspr_end_pay() {
		return mspr_end_pay;
	}

	public void setMspr_end_pay(Date mspr_end_pay) {
		this.mspr_end_pay = mspr_end_pay;
	}

	public Integer getMspr_tt() {
		return mspr_tt;
	}

	public void setMspr_tt(Integer mspr_tt) {
		this.mspr_tt = mspr_tt;
	}

	public Integer getMspr_lpinjam() {
		return mspr_lpinjam;
	}

	public void setMspr_lpinjam(Integer mspr_lpinjam) {
		this.mspr_lpinjam = mspr_lpinjam;
	}

	public Integer getMspr_pu_ptb() {
		return mspr_pu_ptb;
	}

	public void setMspr_pu_ptb(Integer mspr_pu_ptb) {
		this.mspr_pu_ptb = mspr_pu_ptb;
	}

	public Date getMspr_nonactive_date() {
		return mspr_nonactive_date;
	}

	public void setMspr_nonactive_date(Date mspr_nonactive_date) {
		this.mspr_nonactive_date = mspr_nonactive_date;
	}

	public Integer getFlag_up_sc() {
		return flag_up_sc;
	}

	public void setFlag_up_sc(Integer flag_up_sc) {
		this.flag_up_sc = flag_up_sc;
	}

	public Integer getMspo_pay_period() {
		return mspo_pay_period;
	}

	public void setMspo_pay_period(Integer mspo_pay_period) {
		this.mspo_pay_period = mspo_pay_period;
	}

	public Integer getMspo_ins_period() {
		return mspo_ins_period;
	}

	public void setMspo_ins_period(Integer mspo_ins_period) {
		this.mspo_ins_period = mspo_ins_period;
	}

	public Integer getCara_premi() {
		return cara_premi;
	}

	public void setCara_premi(Integer cara_premi) {
		this.cara_premi = cara_premi;
	}

	public String getKombinasi() {
		return kombinasi;
	}

	public void setKombinasi(String kombinasi) {
		this.kombinasi = kombinasi;
	}

    
    public Integer getFlag_packet() {
        return flag_packet;
    }

    
    public void setFlag_packet(Integer flag_packet) {
        this.flag_packet = flag_packet;
    }
	
}
