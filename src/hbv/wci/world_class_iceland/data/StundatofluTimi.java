package hbv.wci.world_class_iceland.data;

import java.util.HashMap;
import java.util.List;

/**
 * Object sem heldur utan um upplysingar um stundatofluna. Med tessum haetti er haegt ad skila lista og
 * hakktoflum i einu
 * 
 * @author Bjorn
 */

public class StundatofluTimi {
	
	public List<String> listHeader;
	public HashMap<String, List<String>> listChild;
	public HashMap<String, String> infoChild;
	
	/**
	 * Smidur sem stillir breytur tilviksins
	 * @param listDataHeader - Headerarnir
	 * @param listChildData - Timarnir
	 * @param infoChildData - Upplysingar um hvern tima
	 */
	public StundatofluTimi(List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, HashMap<String, String> infoChildData){
		
		listHeader = listDataHeader;
		listChild = listChildData;
		infoChild = infoChildData;
		
	}
	
	/**
	 * Kannar hvort einhverjir timar hafi fundist fyrir gefin leitarskilyrdi
	 * @return
	 */
	public boolean isEmpty(){
		return listHeader.get(0) == "Enginn tími fannst";
	}

}
