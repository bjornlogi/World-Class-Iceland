package hbv.wci.world_class_iceland;

import java.util.HashMap;
import java.util.List;

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
