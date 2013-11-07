package hbv.wci.world_class_iceland.data;

import java.util.HashMap;
import java.util.List;

/**
 * Object sem heldur utan um upplysingar um stundatofluna. Med tessum haetti er haegt ad skila lista og
 * hakktoflum i einu knippi
 * 
 * @author Bjorn
 */

public class StundatofluTimi {
	
	public List<String> listHeader;
	public HashMap<String, List<String>> listChild;
	public HashMap<String, String> infoChild;
	
	public StundatofluTimi(List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, HashMap<String, String> infoChildData){
		
		listHeader = listDataHeader;
		listChild = listChildData;
		infoChild = infoChildData;
		
	}

}
