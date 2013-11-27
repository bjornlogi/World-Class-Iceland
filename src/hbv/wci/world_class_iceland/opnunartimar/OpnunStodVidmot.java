package hbv.wci.world_class_iceland.opnunartimar;

import hbv.wci.world_class_iceland.data.Stod;

/**
 * Vidmot fyrir stodvarnar
 * 
 * @author Bjorn
 *
 */
public interface OpnunStodVidmot {
	void setTitle();
	void birtaErOpid(String opnunIDag);		
	void birtaMynd(String stod);
	void birtaOpnunartima(Stod timarObj);
	void setDrawer();
}
