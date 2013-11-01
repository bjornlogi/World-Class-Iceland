package hbv.wci.world_class_iceland.data;

/**
 * Heldur utan um opnunartimana og er library til ad vinna med opnunartimana.
 * 
 * @author Bjorn
 */
public class Stod {
	public String stod;
	
	/**
	 * Upphafsstillir stod med stodi
	 * 
	 * @param stodi
	 */
	public Stod(String stodi){
		stod = stodi;
	}
	
	/**
	 * Finnur opnunartima fyrir daginn sem bedid er um.
	 * 
	 * @param dagur dagurinn sem tarf upplysingar um
	 * @return opnunartima fyrir daginn dagur
	 */
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
	
	/**
	 * Skilar skilabodum ef torf er a teim fyrir vidkomandi stod
	 * 
	 * @return skilabod fyrir tessa stod
	 */
	public String Skilabod(){
		if (Taekjasalur())
			return "* Húsinu lokað - tækjasalur lokar 30.mín fyrr";
		if (AlltafOpid())
			return "Stöðin er opin allan sólarhringinn en starfsmaður er á vakt á ofangreindum tímum";
		if (this.stod.equals("Háskólinn í Reykjavík"))
			return "Nemendur í HR hafa aðgang að stöðinni allan sólarhringinn og alla daga vikunnar.";
		
		return "";
	}
	
	/**
	 * Athugar hvort tad se taekjasalur i stodinni.
	 * 
	 * @return true ef tad er taekjasalur i stodinni, annars false
	 */
	public boolean Taekjasalur (){
		return !(this.stod.equals("Kringlan") || this.stod.equals("Háskólinn í Reykjavík"));
	}
	
	/**
	 * Athugar hvort tad se alltaf opid i stodinni
	 * 
	 * @return true ef tad er alltaf opid, false annars.
	 */
	private boolean AlltafOpid (){
		return this.stod.equals("Kringlan");
	}
	
	/**
	 * Finnur opnunartima fyrir Spong a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Spong a deginum dagur
	 */
	private static String Spong(String dagur){
		if (dagur.equals("margir eins"))
			return "Mán-Fim";

		//fyrsti dagurinn sem er med annan opnunartima, 4 stendur fyrir fostudag
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
	
	/**
	 * Finnur opnunartima fyrir Kringluna a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Kringluna a deginum dagur
	 */
	private static String Kringlan(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Laugar a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Laugar a deginum dagur
	 */
	private static String Laugar(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Egilsholl a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Egilsholl a deginum dagur
	 */
	private static String Egilsholl(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Hafnarfjordur a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Hafnarfjordur a deginum dagur
	 */
	private static String Hafnarfjordur(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir HR a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir HR a deginum dagur
	 */
	private static String HR(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Kopavogur a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Kopavogur a deginum dagur
	 */
	private static String Kopavogur(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Moso a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Moso a deginum dagur
	 */
	private static String Moso(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Ogurhvarf a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Ogurhvarf a deginum dagur
	 */
	private static String Ogurhvarf(String dagur){
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
	
	/**
	 * Finnur opnunartima fyrir Nesid a deginum dagur
	 * 
	 * @param dagur dagurinn sem a ad skoda
	 * @return opnunartima fyrir Nesid a deginum dagur
	 */
	private static String Nesid(String dagur){
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
