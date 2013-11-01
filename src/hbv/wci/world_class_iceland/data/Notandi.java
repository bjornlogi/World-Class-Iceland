package hbv.wci.world_class_iceland.data;

/**
 * 
 * @author Karl
 *
 */
public class Notandi {
	private long mId;
	private String netfang;
	private String lykilord;
	private String stadfest;
	private String kort;
	
	/**
	 * 
	 * @param mId
	 * @param netfang
	 * @param lykilord
	 * @param stadfest
	 * @param kort
	 */
	public Notandi(long mId, String netfang, String lykilord, String stadfest, String kort) {
		this.mId = mId;
		this.netfang = new String(netfang);
		this.lykilord = new String(lykilord);
		this.stadfest = new String(stadfest);
		this.kort = new String(kort);
	}
	
	/**
	 * 
	 * @return
	 */
	public long getmId() {
		return mId;
	}

	/**
	 * 
	 * @return
	 */
	public String getNetfang() {
		return netfang;
	}

	/**
	 * 
	 * @return
	 */
	public String getLykilord() {
		return lykilord;
	}

	/**
	 * 
	 * @return
	 */
	public String getStadfest() {
		return stadfest;
	}

	/**
	 * 
	 * @return
	 */
	public String getKort() {
		return kort;
	}

}
