package com.app.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.model.Email;
import com.app.model.bsim.Client;
import com.app.model.bsim.Pas;
import com.app.model.bsim.Policy;
import com.app.model.bsim.ReasTemp;
import com.app.model.esehat.DropDownMenu;
import com.app.model.gadget.prod.Account_recur;
import com.app.model.gadget.prod.AddressBilling;
import com.app.model.gadget.prod.AddressNew;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Benefeciary;
import com.app.model.gadget.prod.Checklist;
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
import com.app.model.gadget.prod.Tertanggung;
import com.app.model.sms.Smsserver_out;

public interface CommonDao {
	
	//MAP_bsim
	 	HashMap<String, Object> selectBankPusat(String lsbp_id);
	    HashMap<String, Object> selectMspId(String lsbp_id);    
	    HashMap<String, Object> selectMstKartuPas(String no_kartu);
	    HashMap<String, Object> selectMstPasSms(String no_kartu);
	    HashMap<String, Object> selectdatapolicypas(String spaj);
	    Long select_counter(Map params);
	    String selectGetCounterMonthYear(Map params);
	    Date selectSysdate();
	    Map<String, Object> selectAgenPenutup(String msag_id);
	    String selectCekNoKartu(String no_kartu);
	    HashMap<String, Object> selectDetailKartuPas(String no_kartu);
	    String selectSeqNoSpaj(String lca_id);
	    String selectSequenceClientID();
	    String selectNoBlankoPas(HashMap<String, Object> params);
	    HashMap<String, Object> selectDataUsulan(String spaj);
	    HashMap<String, Object> selectTertanggung(String spaj);
	    Policy selectDw1Underwriting(HashMap<String, Object> params);
	    HashMap<String, Object> selectCheckPosisi(String spaj);
	    HashMap<String, Object> selectPemegang(String spaj);
	    ArrayList<HashMap<String, Object>> selectSimultanNew(HashMap<String, Object> params);
	    Client selectMstClientNew(HashMap<String, Object> params);
	    AddressNew selectMstAddressNew(HashMap<String, Object> params);
	    String selectSequenceSimultanID();
	    List selectMstStsClientMscActive(Map params);
	    Integer selectMstSimultaneous(String mcl_id);
	    Double selectLstReinsCurrency(Map params);
	    String selectMstCancelRegSpaj(String ls_old_spaj);
	    Integer selectNoSimultan(Map params);
	    String selectIdSimultan(Map params);
	    List selectDdsSarNew(Map params);
	    List selectDdsSarnNew(Map params);
	    Double selectFCheckTahunKe(Map params);
	    Integer selectMstInsuredAge(Map params);
	    Integer selectMstProductInsuredMsprUnit(Map params);
	    Double selectLstRiderInclude2(Map params);
	    Double selectLstReinsDesc2(Map params);
	    Integer selectLstReinsDesc1(Map params);
	    Double selectLstTableAwal(Map params);
	    List selectSpajSimultan(String id_simultan);
	    Integer selectLineBusLstBisnis(String lsbs_id);
	    Map selectLstBisnisRetensi2(Integer ai_main_bisnis_id);
	    Double selectLstLimitReinsureance3(Map params);
	    Integer selectIsUlink(String spaj);
	    List selectListOldProdSave(String id_simultan);
	    List selectGroupReas(String bisnisId);
	    Integer selectCountMstSimultaneous(String reg_spaj);
	    String selectMstSimultaneousIdSimultan(String mcl_id);
	    Integer selectIntMonth(String spaj);
	    List selectProductUtama(String spaj);
	    Map selectMstInsuredBegEndDate(Map params);
	    Double selectMstInsuredPremiDiscount(Map params);
	    Double selectMstDefault(Integer li_id);
	    Integer selectLstPayModeLscbTtlMonth(Integer lscb_id);
	    Integer selectCountMstBill(Map params);
	    Map selectDataVirtualAccount(String spaj);
	    String selectPolicyNoFromSpajManualMstTempDMTM(String spaj);
	    Map selectMstCntPolis(Map params);
	    String selectMstPolicyRegSpaj(String ls_nopol);
	    Integer selectMstDetRekruter(String ls_agen);
	    String selectVirtualAccountSpaj(String spaj);
	    String selectBusinessId(String spaj);
	    Integer selectBusinessNumber(String spaj);
	    List selectCabangKK(String lcb_no);
	 
	  	void insertPas(Pas pas);
	  	void insertMstPositionSpajPas(Map params);
	  	void insertMstClientNew(Client client);
	  	void insertMstAddressNew(AddressNew addressNew);
	  	void insertMstStsClient(Map params);
	  	void insertMstSimultaneous(Map params);
	  	void insertMSarTemp(Map params);
	  	void insertMReasTemp(ReasTemp reasTemp);
	  	void insertMstBilling(Map params);
	  	void insertMstDetBilling(Map params);
	  	void insertMstCntPolis(Map params);
	    
	  	
	  	void updateKartuPas(Map params);
	  	void updateCounterMonthYear(Map params);
	//  	void updateMstCounter(Map params);
		void updateMstPolicy(Map params);
	  	void commonupdateMstPolicy(Map params);
	  	void updateMstInsured(Map params);
	  	void commonupdateMstInsured(Map params);
	  	void updateMstStsClient(Map params);
	  	void updateMReasTempMsteReas(Map params);
	  	void updateMstInsuredReasnBackup(Map params);
	  	void updateMReasTemp(Map params);
	  	void updateMstPolicyMspoNextBill(Map params);
	  	void updateMstCntPolis(Map params);
	  	void updateMstPolicyNopolis(Map params);
	  	void updateMstPolicyMspoPreExisting(Map params);
	  	void updateMstInsuredStatus(Map params);
	  	void updateVirtualAccountBySpaj(Map params);
	  	void updateKartuPas2(Map params);
	//  	void updateMstDetVa(Map params);
	  	void updatePas(Pas pas);
	  	
	    
	    //delete
	    	void deletePasSMS(String no_kartu);
	    	void deletePositionSpajPas(String msp_fire_id);
	    	void deleteMReasTemp(String spaj);
	    	void deleteMSarTemp(String spaj);
	    	void deleteMReasTempNew(String spaj);
	      
    
    
	    //map espaj
	    
	    Map selectGetNewNoVirtualAccount(Map params);
		Map selectCategoryBisnis(Map params);
		List selectGetNewNoVirtualAccountList(Map params);
		Map  selectDataReffBank(Map params);
		Map selectProdukBank(String no_temp);
		ArrayList<HashMap<String, Object>> selectReffBIIbyJenis(String jn_bank);
		void updateMstDetVaByNoVa(Map params);
		HashMap<String, Object> selectDataProposal(String no_temp);
		ArrayList<HashMap<String, Object>> selectDataCrp(String no_temp);
		int selectNoGadget(String no_gadget);
		HashMap<String, Object> selectbyNoGadget(String no_gadget);
	    
	    Pemegang selectppTemp(String no_temp);  
	    AddressBilling selectAddressBillingTemp(String no_temp);
	    Tertanggung selectttgTemp(String no_temp);
	    PembayarPremi selectPemPremiTemp(String no_temp);
	    
	    Account_recur selectAccRecurTemp(String no_temp);
	    
	    Datausulan selectMstProductTemp(String no_temp);
	    List<Datarider> selectMstProductTempRider(String no_temp);
	    List<PesertaPlus_mix> selectPesertaTemp(String no_temp);

	    List<Benefeciary> select_benef_temp(String no_temp);
	    
	    String selectIdLstPekerjaan(String namaPekerjaan);
	    Integer selectCountKeluarga(String no_temp);
	    Agen select_detilagen2(String no_temp);
	    
	    double selectSumPremiTemp(String no_temp);
	    
	    Rekening_client select_rek_client_temp(String no_temp);
	    List<DetilInvestasi> selectDaftarInvestasiTemp(String no_temp);
	    
	    String selectkodegutri(HashMap<String, Object> params);
	    HashMap<String, Object> selectRegionalAgen( HashMap<String, Object> params );

//	    Long selectCounter( HashMap<String, Object> params );
	    String selectSpajSeq( HashMap<String, Object> params );
	    void updateMstCounter(Map params);
	    void commonupdateMstCounter(Map params);
	    
	    void insertMstAgentTmp(Map params);
	    Long selectCounterClient( HashMap<String, Object> params );
	    void updateMstCounterClient(Map params);
	    
	    String selectKeteranganKerja(String lsp_id);
	    
	    Integer updateClientTtg(Tertanggung tertanggung);   
	    Integer updateMstAddressTtg(Tertanggung tertanggung);
	    Integer insertMstClientTtg(Tertanggung tertanggung);
	    void insertMstAddressTtg(Tertanggung tertanggung);
	    
	    List<DropDownMenu> selectBidangUsaha(HashMap<String, Object> params);
	   
	    Integer updateMstClientPP(Pemegang pemegang);   
	    Integer insertMstClientPP(Pemegang pemegang);
	    Integer updateMstAddressPP(Pemegang pemegang);
	    Integer insertMstAddressPP(Pemegang pemegang);
	    Integer updateMstCompany(Personal personal);    
	    Integer insertMstCompany(Personal personal);
	    Integer updateMstCompanyAddress(Pemegang pemegang);     
	    Integer insertMstCompanyAddress(Pemegang pemegang);
	    Integer updateMstPolicy(HashMap<String, Object> params);
	    Integer insertMstPolicy(HashMap<String, Object> params);
	    void insertMstPolicyPas(HashMap<String, Object> params);
	    void insertMstPolicyPasAutodebet(HashMap<String, Object> params);
	    void updateLeadReffBii(HashMap<String, Object> params);
	    
	    Integer selectMstTransHist(String reg_spaj);
	    void updateMstTransHistory(HashMap<String, Object> params);
	    void insertMstTransHistory(HashMap<String, Object> params);
	    
	    void updateBungaMstPolicy(Pemegang pemegang);
	    
	    void deleteMstKyc(HashMap<String, Object> params);
	    void insertKyc(HashMap<String, Object> params);
	    void updateMonitoringSpaj(Map params);
	    Integer updateMstWorksiteFlag(Pemegang pemegang);
	    void insertMstWorksiteFlag(Pemegang pemegang);
	    
	    Long select_Counter( HashMap<String, Object> params);   
	    void update_Counter( HashMap<String, Object> params);
	    List<DropDownMenu> selectJenisForm();
	    Integer update_no_blanko(HashMap<String, Object> params);
	    
	    Integer updateMstInsured( HashMap<String, Object> params );
	    void insertMstInsured( HashMap<String, Object> params );
	    void insertMstInsuredPas(HashMap<String, Object> params);
	    void insertMstInsuredPasAutodebet(HashMap<String, Object> params);
	    
	    void deleteMstKeluarga(String lsbs_id);
	    void insertMstKeluarga(HashMap<String, Object> params);
	    Integer updateMstKeluarga( HashMap<String, Object> params );
	    
	    Integer updateNewClientPayerBadanHukum(PembayarPremi pembayarPremi);
	    void insertNewClientPayerBadanHukum(PembayarPremi pembayarPremi);   
	    Integer updateAddressPemPremiIndividu(PembayarPremi pembayarPremi);
	    void insertAddressPemPremiBadanHukum(PembayarPremi pembayarPremi);
	    void updateMstPolicyPayer(HashMap<String, Object> params);
	    
	    Integer updateNewClientPayerIndividu(PembayarPremi pembayarPremi);
	    void insertNewClientPayerIndividu(PembayarPremi pembayarPremi);
	    void insertAddressPemPremiIndividu(PembayarPremi pembayarPremi);
	    
	    Integer updateMstClientPic(ContactPerson contactPerson);
	    void insertMstClientPic(ContactPerson contactPerson);
	    Integer updateMstAddressPic(ContactPerson contactPerson);
	    void insertMstAddressPic(ContactPerson contactPerson);
	    void deleteMstCompanyContactFamily(String strPOClientID);
	    void inserMstCompanyContactFamily( Map param1 );
	    Agen selectMstAgent( Map param1 );
	    void insertMstAgentProd(Agen agen);
	    Integer selectIsAgenCorporate(String kdAgen);
	    Integer selectIsAgenKaryawan(String kdAgen);
	    void insertMstAgentComm(Agen agen);
	    void insertMstAgentArtha(Agen agen);
	    
	    List<Map<String, Object>> selectAgentRekruter( String kdAgen );
	    void insertMstAgentRekruter(Agen agen);
	    HashMap<String, Object> selectAgentCodeAO( String kdAgen );
	    void insertMstAgentBa(Agen agen);
	    Integer selectKabupaten(String nama_wilayah);
	    Integer updateMstAddressBilling(AddressBilling addressBilling);
	    void insertMstAddressBilling(AddressBilling addressBilling);
	    Integer updateMstRekClient(Rekening_client rekening_client);
	    void insertMstRekClient( Rekening_client rekening_client );
	    void insertMstRekClientHist( Rekening_client rekening_client );
	    void insertMstAccountRecur( Account_recur account_recur );
	    void insertMstPositionSpaj( Map params );
	    void updateMstInsuredTglAdmin( Map params );
	    
	    void insertMst_position_no_spaj_pb( Map params );
	    void insertMstPositionSpajRedFlag( Map params );    
	    void insertMst_sts_client( Map params );
	    String selectAddmonths(Map param27);
	    
	    void inserMst_product_insured45( Map params );
	    void insertMst_product_insuredPA( Map params );
	    void inserMst_product_insured1( Map params );
	    void insertMst_product_insured_rider( Map params);
	    
	    double sum_premi(String strSPAJ);
	    void insertmst_deposit( DetailPembayaran detailPembayaran );
	    Map<String, Object> selectNamaPlan( Map param );
	    void insertMstEmp(Employee employee);
	    Integer update_mst_ulink(InvestasiUtama investasiUtama);
	    void insert_mst_ulink( InvestasiUtama investasiUtama );
	    void insertFixed( Map params );
	    void insertMst_biaya_ulink( Map params );
	    void insertMst_benefeciary( Benefeciary benefeciary );
	    void insertMst_rencana_penarikan( RencanaPenarikan rencanaPenarikan );
	    void insert_mst_peserta_plus_mix( PesertaPlus_mix pesertaPlus_mix );
	        
	    void updateMstSpajTemp( Map params );
	    void updateMstProductTemp( Map params );
	    void updateMstAddressBillingTemp( Map params );
	    void updateMstPesertaTemp( Map params );
	    
	    Integer selectCountQuestionaireTemp(String no_temp);
	    Integer selectCountMedquestTemp(String no_temp);
	    Integer selectCountbenefTemp(String no_temp);
	    String selectCountReffBiiTemp(String no_temp);  
	    void insertMedQuestFromTemp( Map params );
	    void insertReffBiiFromTemp( Map params );
	    void insertQuestionaireFromTemp( Map params );
	    
	    void updateMstDetVa(Map paramx);    
	    Integer selectinvestasiutamakosong( Integer kode_flag);
	    ArrayList<DetilInvestasi> selectdetilinvestasikosong(Integer kode_flag);
	    double select_biaya_akuisisi(Map params);
	    double selectNilai(Map params);
	    
	    void prosesSimultanMedis(Map params);
	    void prosesJurnalUWApproval(Map params);
	    void prosesValQuest(Map params);
	    void prosesReas(Map params);
	    Integer selectStatusAksep(String strTmpSPAJ);
	    Integer updateMstpositionSpajTgl(Map params);
	    void updateProsesKyc(Map params);
	    List<Checklist> selectCheckListBySpaj(String strTmpSPAJ);
	    String selectLcaIdBySpaj(String strTmpSPAJ);
	    Integer updateMstChecklist(Checklist c);
	    void insertMstChecklist(Checklist c);
	    void updateFlagSPH(Map params);
	    Integer selectPositionSpaj(String spaj);
	    void prosesAkseptasiSpeedy(Map params);
	    void updateFlagAprove(Map params);
	    Map selectF_check_posisi(String strTmpSPAJ);
	    void save_reinsNew(Map params);
	    HashMap selectWfGetStatus(HashMap params);
	    void updateMstPolicyPosition( HashMap<String, Object> params );
	    void updatePositionMstInsured( HashMap<String, Object> params );
		Integer selectMstSpajCrtExists(String no_polis);
		String selectSertifikatTemp(Map params);
		void updateLstMember(String no_va);
		
		
		
		
	
		
		//map common
		
		//select
	
		Timestamp selectSystimestamp();
		String selectSeqEmailId();
		Map selectMstCounter(Map params);
		Map selectMstUrlSecure(HashMap params);
		HashMap select_freePaDmtm_tmSales(HashMap params);
		HashMap selectCommon(String sql);
		List<HashMap> selectListCommon(String sql);
		List <HashMap> select_data_nasabah(Map params);
		List <HashMap> select_data_nasabah_simpol(Map params);
		List <HashMap> select_data_nasabah_simprim(Map params);
		List <HashMap> select_data_nasabah_magna(Map params);
		List <HashMap> select_data_nasabah_prime(Map params);
		List <HashMap> selectLstPekerjaan();
		List <HashMap> selectLstJabatan();
		List <HashMap> SelectLstPropinsi();
		List <HashMap> SelectLstQuestion();
		List <HashMap> SelectLstLabelQuestion();
		List <HashMap> SelectProdukBancass();
		List <HashMap> SelectProdukRider();
		List <HashMap> SelectProdukDropdown();
		List <HashMap> SelectPaket();
		List <HashMap> SelectBankPusat();
		List <HashMap> SelectBankCabang();
		List <HashMap> SelectFund();
		List <HashMap> SelectRegionalID();
		List <HashMap> SelectTypeProduct();
		List <HashMap> SelectRateRider();
		List <HashMap> SelectFaktorUP();
		
		String selectSeqUrlSecureId();
		String selectEncrypt(String desc);

		Integer selectLoginProposal(Map params);
		HashMap SelectVersion(Map params);
		
		ArrayList<HashMap<String, Object>> selectLstUserExternal(String username);
	//	UserEspaj selectLoginAuthenticationFirst(UserEspaj userEspaj);
//		UserEspaj selectLoginAuthenticationNextMobile(UserEspaj userEspaj);
		ArrayList<HashMap<String, Object>> selectAgentDataForMobile(String username);

		List <HashMap> selectTotalPremi(HashMap<String, Object> params);
		List <HashMap> selectDetailPremi(HashMap<String, Object> params);
		
		HashMap<String, Object> selectMstConfig(HashMap<String, Object> params);
		int selectAccessClient(Map params);
		
		HashMap<String, Object> selectSpajBeingProcessed(String spaj);
		
		//update
		void updateCommon(String sql);
//ada		void updateMstCounter(Map params);
		void updateMstPolicyDCIF(Map params);
		
		//insert
//		void insertMstEksternalPrint(EksternalPrint eksternalPrint);
		void insertCommon(String sql);
		void insertMstEmail(Email email);
		void insertAksesHist(Map params);
		void insertLstHistActvWs(Map params);
		void insertLstHistActvWsOut(Map params);
		void insertMstUrlSecure(Map params);
		void insertSmsserverout(Smsserver_out sms_out);
		void insertMstSpajCrt(Map params);
		
		//delete
		void deleteMstUrlSecure(HashMap params);
		ArrayList<HashMap> selectProductBisnis(String spaj);
		
    
    
    
    
    
    
}
