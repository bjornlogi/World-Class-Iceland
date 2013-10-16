package hbv.wci.world_class_iceland;

/**
 * Geymslu klasi til ad geyma tima i stundartoflu
 * 
 * @author Bjorn
 */
public class Timi {
	public String nafn;
	public String klukkan;
	public String salur;
	public String tjalfari;
	
	/**
	 * Byr til nyjann tima og geymir upplysingarnar um hann.
	 * 
	 * @param nafni
	 * @param klukkani
	 * @param saluri
	 * @param tjalfarii
	 */
	public Timi (String nafn, String klukkan, String salur, String tjalfari){
		this.nafn = nafn;
		this.klukkan = klukkan;
		this.salur = salur;
		this.tjalfari = tjalfari;
	}
}
