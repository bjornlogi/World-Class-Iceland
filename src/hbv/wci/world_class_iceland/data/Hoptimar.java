package hbv.wci.world_class_iceland.data;

/**
 * Geymir einn hoptima
 * 
 * @author Maria og Sonja 
 */
public class Hoptimar {
	
	private long mId;
	private String nafn;
	private String stod;
	private String salur;
	private String tjalfari;
	private String tegund;
	private String klukkan;
	private String timi;
	private String dagur;
	private String lokad;
	
	public Hoptimar(long mId, String nafn, String stod, String salur, String tjalfari, String tegund,
			String klukkan, String timi, String dagur){
		this.mId = mId;
		this.nafn = nafn;
		this.stod = stod;
		this.salur = salur;
		this.tjalfari = tjalfari;
		this.tegund = tegund;
		this.klukkan = klukkan;
		this.timi = timi;
		this.dagur = dagur;
	}
	
	public Hoptimar(){
		
	}
	
	/**
	 * Naer i ID
	 * 
	 * @return mId
	 */
	public long getmId() {
		return mId;
	}

	/**
	 * Stillir ID sem mId
	 * 
	 * @param mId
	 */
	public void setmId(long mId) {
		this.mId = mId;
	}
	
	/**
	 * Naer i nafnid
	 * 
	 * @return nafn
	 */
	public String getNafn() {
		return nafn;
	}
	
	/**
	 * Stillir nafnid sem nafn
	 * 
	 * @param nafn
	 */
	public void setNafn(String nafn) {
		this.nafn = nafn;
	}
	
	/**
	 * Naer i stodina
	 * 
	 * @return stod
	 */
	public String getStod() {
		return stod;
	}
	
	/**
	 * Stillir stodina sem stod
	 * 
	 * @param stod
	 */
	public void setStod(String stod) {
		this.stod = stod;
	}
	
	/**
	 * Naer i sal
	 * 
	 * @return salur
	 */
	public String getSalur() {
		return salur;
	}
	
	/**
	 * Stillir sal sem salur
	 * 
	 * @param salur
	 */
	public void setSalur(String salur) {
		this.salur = salur;
	}

	/**
	 * Naer i tjalafara
	 * 
	 * @return tjalfari
	 */
	public String getTjalfari() {
		return tjalfari;
	}
	
	/**
	 * Stillir tjalfara sem tjalfari
	 * 
	 * @param tjalfari
	 */
	public void setTjalfari(String tjalfari) {
		this.tjalfari = tjalfari;
	}
	
	/**
	 * Naer i tegundina
	 * 
	 * @return tegund
	 */
	public String getTegund() {
		return tegund;
	}

	/**
	 * Stillir tegundina sem tegund
	 * 
	 * @param tegund
	 */
	public void setTegund(String tegund) {
		this.tegund = tegund;
	}
	
	/**
	 * Naer i klukkuna
	 * 
	 * @return klukkan
	 */
	public String getKlukkan() {
		return klukkan;
	}
	
	/**
	 * Stillir klukkuna sem klukkan
	 * 
	 * @param klukkan
	 */
	public void setKlukkan(String klukkan) {
		this.klukkan = klukkan;
	}

	/**
	 * Naer i timann
	 * 
	 * @return timi
	 */
	public String getTimi() {
		return timi;
	}
	
	/**
	 * Stillir timann sem timi
	 * 
	 * @param timi
	 */
	public void setTimi(String timi) {
		this.timi = timi;
	}
	
	/**
	 * Naer i daginn
	 * 
	 * @return dagur
	 */
	public String getDagur() {
		return dagur;
	}
	
	/**
	 * Stillir daginnn sem dagur
	 * 
	 * @param dagur
	 */
	public void setDagur(String dagur) {
		this.timi = dagur;
	}
	
	/**
	 * Naer i lokun
	 * 
	 * @return lokad
	 */
	public String getLokad() {
		return lokad;
	}
	
	/**
	 * Stillir lokun sem lokad
	 * 
	 * @param lokad
	 */
	public void setLokad(String lokad) {
		this.lokad = lokad;
	}
	
	/**
	 * Naer i streng sem lysir hlutnum
	 * 
	 * @return strengur sem lysir innihaldi hlutsins.
	 */
	@Override
	public String toString(){
		String info = "";
		info += klukkan;
		info += "\n" + stod;
		if (!salur.equals(""))
			info += "\n" + salur;
		if (!tjalfari.equals(""))
			info += "\n" + tjalfari;
		return info;
	}
	
	public String minnTimiInfo(){
		String info = "";
		info += klukkan;
		info += "\n" + stod;
		if (!salur.equals(""))
			info += " - " + salur;
		if (!tjalfari.equals(""))
			info += "\n" + tjalfari;
		return info;
	}
}
