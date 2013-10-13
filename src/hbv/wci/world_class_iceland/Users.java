package hbv.wci.world_class_iceland;

public class Users {
	
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
	
	public long getmId() {
		return mId;
	}
	public void setmId(long mId) {
		this.mId = mId;
	}
	public String getNafn() {
		return nafn;
	}
	public void setNafn(String nafn) {
		this.nafn = nafn;
	}
	public String getStod() {
		return stod;
	}
	public void setStod(String stod) {
		this.stod = stod;
	}
	public String getSalur() {
		return salur;
	}
	public void setSalur(String salur) {
		this.salur = salur;
	}
	public String getTjalfari() {
		return tjalfari;
	}
	public void setTjalfari(String tjalfari) {
		this.tjalfari = tjalfari;
	}
	public String getTegund() {
		return tegund;
	}
	public void setTegund(String tegund) {
		this.tegund = tegund;
	}
	public String getKlukkan() {
		return klukkan;
	}
	public void setKlukkan(String klukkan) {
		this.salur = klukkan;
	}
	public String getTimi() {
		return timi;
	}
	public void setTimi(String timi) {
		this.timi = timi;
	}
	public String getDagur() {
		return dagur;
	}
	public void setDagur(String dagur) {
		this.timi = dagur;
	}
	public String getLokad() {
		return lokad;
	}
	public void setLokad(String lokad) {
		this.lokad = lokad;
	}
	@Override
	public String toString(){
		return nafn+" "+stod+" "+ salur + " "+tjalfari+" "+tegund+" "+klukkan+" "+timi+" "+dagur+" "+lokad;
		
	}

}
