package hbv.wci.world_class_iceland;

/**
 * Geymir eitthvad
 * 
 * 
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
	
	/**
	 * 
	 * @return mId
	 */
	public long getmId() {
		return mId;
	}

	/**
	 * 
	 * @param mId
	 */
	public void setmId(long mId) {
		this.mId = mId;
	}
	
	/**
	 * 
	 * @return nafn
	 */
	public String getNafn() {
		return nafn;
	}
	
	/**
	 * 
	 * @param nafn
	 */
	public void setNafn(String nafn) {
		this.nafn = nafn;
	}
	
	/**
	 * 
	 * @return stod
	 */
	public String getStod() {
		return stod;
	}
	
	/**
	 * 
	 * @param stod
	 */
	public void setStod(String stod) {
		this.stod = stod;
	}
	
	/**
	 * 
	 * @return salur
	 */
	public String getSalur() {
		return salur;
	}
	
	/**
	 * 
	 * 
	 * @param salur
	 */
	public void setSalur(String salur) {
		this.salur = salur;
	}

	/**
	 * 
	 * @return tjalfari
	 */
	public String getTjalfari() {
		return tjalfari;
	}
	
	/**
	 * 
	 * @param tjalfari
	 */
	public void setTjalfari(String tjalfari) {
		this.tjalfari = tjalfari;
	}
	
	/**
	 * 
	 * 
	 * @return tegund
	 */
	public String getTegund() {
		return tegund;
	}

	/**
	 * 
	 * @param tegund
	 */
	public void setTegund(String tegund) {
		this.tegund = tegund;
	}
	
	/**
	 * 
	 * @return klukkan
	 */
	public String getKlukkan() {
		return klukkan;
	}
	
	/**
	 * 
	 * @param klukkan
	 */
	public void setKlukkan(String klukkan) {
		this.klukkan = klukkan;
	}

	/**
	 * 
	 * @return timi
	 */
	public String getTimi() {
		return timi;
	}
	
	/**
	 * 
	 * @param timi
	 */
	public void setTimi(String timi) {
		this.timi = timi;
	}
	
	/**
	 * 
	 * @return dagur
	 */
	public String getDagur() {
		return dagur;
	}
	
	/**
	 * 
	 * @param dagur
	 */
	public void setDagur(String dagur) {
		this.timi = dagur;
	}
	
	/**
	 * 
	 * @return lokad
	 */
	public String getLokad() {
		return lokad;
	}
	
	/**
	 * 
	 * @param lokad
	 */
	public void setLokad(String lokad) {
		this.lokad = lokad;
	}
	
	/**
	 * @return String representation of the object
	 */
	@Override
	public String toString(){
		String upplysingar = "";
		upplysingar += klukkan;
		if (!salur.equals(""))
			upplysingar += "\n" + salur;
		if (!tjalfari.equals(""))
			upplysingar += "\n" + tjalfari;
		return upplysingar;
	}
}
