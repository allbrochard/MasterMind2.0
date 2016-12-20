import java.util.Arrays;

public class MMHistorique {
	private MMPion[] histoProp;
	private int bP,mP;
	
	
	public MMHistorique(MMPion[] tabProp, int bP, int mP){
		histoProp = new MMPion[tabProp.length];
		for (int i = 0 ; i < tabProp.length; i++) {
			this.histoProp[i] = tabProp[i];
		}
		this.bP = bP;
		this.mP = mP;
	}


	public MMPion[] getHistoProp() {
		return this.histoProp;
	}


	public int getbP() {
		return this.bP;
	}
	
	public int getmP() {
		return this.mP;
	}
	
	/**
	 * public String afficher (){
	 
		String prop="";
		for (int i=0; i<histoProp.size();i++) {
			prop += Arrays.toString(histoProp.get(i)) + " " + " BP : "+ histoPt.get(i)[0] + " MP : "+histoPt.get(i)[1]+ "\n" ;
		}
		return prop;
	}
	*/


	@Override
	public String toString() {
		return ( Arrays.toString(histoProp) + " BP : " + bP + " MP : " + mP);
	}
	
	
	
	

}
