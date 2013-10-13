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
		else if (this.stod.equals("Egilshöll"))
			return Egilsholl(dagur);
		else if (this.stod.equals("Hafnarfjörður"))
			return Hafnarfjordur(dagur);
		else if (this.stod.equals("Háskólinn í Reykjavík"))
			return HR(dagur);
		else if (this.stod.equals("Kópavogur"))
			return Kopavogur(dagur);
		else if (this.stod.equals("Mosfellsbær"))
			return Moso(dagur);
		else if (this.stod.equals("Ögurhvarf"))
			return Ogurhvarf(dagur);
		else if (this.stod.equals("Seltjarnarnes"))
			return Nesid(dagur);
		return "";
	}
	
	public String Skilabod(){
		if (Taekjasalur())
			return "* Húsinu lokað - tækjasalur lokar 30.mín fyrr";
		if (AlltafOpid())
			return "Stöðin er opin allan sólarhringinn en starfsmaður er á vakt á ofangreindum tímum";
		if (this.stod.equals("Háskólinn í Reykjavík"))
			return "Nemendur í HR hafa aðgang að stöðinni allan sólarhringinn og alla daga vikunnar.";
		return "";
	}
	
	public Boolean Taekjasalur (){
		if (this.stod.equals("Kringlan") || this.stod.equals("Háskólinn í Reykjavík"))
			return false;
		return true;
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
	public static String Egilsholl(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fös";
		if (dagur.equals("hvar á að byrja"))
			return "5";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu") || dagur.equals("Fri"))
			return "06:00-21:00";
		else if (dagur.equals("Sat"))
			return "10:00-14:00";
		else if (dagur.equals("Sun"))
			return "Lokað";
		return "Error";
	}
	public static String Hafnarfjordur(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";
		if (dagur.equals("hvar á að byrja"))
			return "4";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu"))
			return "06:00-22:30";
		else if (dagur.equals("Fri"))
			return "06:00-20:30";
		else if (dagur.equals("Sat"))
			return "08:00-16:30";
		else if (dagur.equals("Sun"))
			return "Lokað";
		return "Error";
	}
	public static String HR(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fös";
		if (dagur.equals("hvar á að byrja"))
			return "5";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu") || dagur.equals("Fri"))
			return "06:00-21:00";
		else if (dagur.equals("Sat"))
			return "Lokað";
		else if (dagur.equals("Sun"))
			return "Lokað";
		return "Error";
	}
	public static String Kopavogur(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";
		if (dagur.equals("hvar á að byrja"))
			return "4";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu"))
			return "06:00-22:30";
		else if (dagur.equals("Fri"))
			return "06:00-20:30";
		else if (dagur.equals("Sat"))
			return "08:00-16:30";
		else if (dagur.equals("Sun"))
			return "Lokað";
		return "Error";
	}
	public static String Moso(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fös";
		if (dagur.equals("hvar á að byrja"))
			return "5";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu") || dagur.equals("Fri"))
			return "06:00-21:30";
		else if (dagur.equals("Sat"))
			return "08:00-19:00";
		else if (dagur.equals("Sun"))
			return "08:00-19:00";
		return "Error";
	}
	public static String Ogurhvarf(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";
		if (dagur.equals("hvar á að byrja"))
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
	public static String Nesid(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fös";
		if (dagur.equals("hvar á að byrja"))
			return "5";
		else if (dagur.equals("Mon") || dagur.equals("Tue") || dagur.equals("Wed") || dagur.equals("Thu") || dagur.equals("Fri"))
			return "06:00-22:30";
		else if (dagur.equals("Sat"))
			return "08:00-20:00";
		else if (dagur.equals("Sun"))
			return "08:00-20:00";
		return "Error";
	}
}
