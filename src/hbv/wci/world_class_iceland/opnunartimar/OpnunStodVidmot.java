package hbv.wci.world_class_iceland.opnunartimar;

import hbv.wci.world_class_iceland.data.Stod;

public interface OpnunStodVidmot {
	void setTitle();
	void setDate();
	void createMap();
	void birtaErOpid(String opnunIDag);		
	void birtaMynd(String stod);
	void birtaOpnunartima(Stod timarObj);
	void setDrawer();
}
