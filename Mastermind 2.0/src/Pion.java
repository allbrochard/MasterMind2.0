

public class Pion {
	String couleur;
	
	public Pion(int i){
	if (i == 0) couleur ="vide";
	else if (i == 1) couleur = "jaune";
	else couleur="rouge";
	}
	public boolean IsJaune(){
		if (couleur == "jaune") return true;
		else return false;
	}
	public boolean IsRouge(){
		if (couleur == "rouge") return true;
		else return false;
	}
	public boolean IsVide(){
		if (couleur == "vide") return true;
		else return false;
	}
	/**
	 * @return the couleur
	 */
	public String getCouleur() {
		return couleur;
	}
	/**
	 * @param couleur the couleur to set
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
}
