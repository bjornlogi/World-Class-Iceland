package hbv.wci.world_class_iceland;

public class OpnunObj {
	public String stod;
	public String Mon;
	public String Tue;
	public String Wed;
	public String Thu;
	public String Fri;
	public String Sat;
	public String Sun;
	
	public OpnunObj(String stodi){
		stod = stodi;
		if (stod.equals("Spöngin")){
			Mon="06:00-22:30";
			Tue=Mon;
			Wed=Mon;
			Thu=Mon;
			Fri="06:00-20:30";
			Sat="08:00-16:30";
			Sun="10:00-14:00";
		}	
	}
	
	public String OpnunFyrirDag (String dagur){
		if (this.stod.equals("Spöngin"))
			return Spong(dagur);
		else if (this.stod.equals("Kringlan"))
			return Kringlan(dagur);
		else if (this.stod.equals("Laugar"))
			return Laugar(dagur);
		return "";
	}
	
	public Boolean Taekjasalur (){
		if (this.stod.equals("Spöngin")
			|| this.stod.equals("Laugar"))
			return true;
		return false;
	}
	
	public Boolean AlltafOpid (){
		if (this.stod.equals("Kringlan"))
			return true;
		return false;
	}
	
	public static String Spong(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";
		if (dagur.equals("hvar á að byrja")) //fyrsti dagurinn sem er med annan opnunartima, 4 stendur fyrir fostudag
			return "4";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu"))
			return "06:00-22:30";
		else if (dagur.equals("Fri"))
			return "06:00-20:30";
		else if (dagur.equals("Sat"))
			return "08:00-16:30";
		else if (dagur.equals("Sun"))
			return "10:00-14:00";
		return "Error";
	}
	public static String Kringlan(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";
		if (dagur.equals("hvar á að byrja"))
			return "4";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu"))
			return "06:00-22:30";
		else if (dagur.equals("Fri"))
			return "06:00-20:30";
		else if (dagur.equals("Sat"))
			return "09:00-13:00";
		else if (dagur.equals("Sun"))
			return "Lokað";
		return "Error";
	}
	public static String Laugar(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fös";
		if (dagur.equals("hvar á að byrja"))
			return "5";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu") || dagur.equals("Fri"))
			return "06:00-23:30";
		else if (dagur.equals("Sat"))
			return "08:00-22:00";
		else if (dagur.equals("Sun"))
			return "08:00-20:00";
		return "Error";
	}
}
