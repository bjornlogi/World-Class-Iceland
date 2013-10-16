package hbv.wci.world_class_iceland;

/**
 * Geymslu klasi til ad geyma tíma í stundartöflu
 * 
 */
public class Timi {
	
	public String nafn;
	public String klukkan;
	public String salur;
	public String tjalfari;
	
	
	/**
	 * Býr til nýjann tíma og geymir upplýsingarnar um hann.
	 * 
	 * @param nafni
	 * @param klukkani
	 * @param saluri
	 * @param tjalfarii
	 */
	public Timi (String nafni, String klukkani, String saluri, String tjalfarii){
		nafn = nafni;
		klukkan = klukkani;
		salur = saluri;
		tjalfari = tjalfarii;
	}
}
