

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Grille {
	private Pion[][]tabGrille;
	private int lastCol, lastLigne;
	private boolean gagner=true;
	private int quit = 100;
	/**
	 * createur de la grille a vide
	 */
	public Grille(){
		tabGrille = new Pion [6][7];
		for (int i=0; i<tabGrille.length; i++){
			for (int j=0; j<tabGrille[i].length; j++){
				tabGrille[i][j]= new Pion(0);
			}
		}
	}
	/**
	 * affiche le tableau du puissance 4
	 */
	public void afficher(){
		for (int i=0; i<tabGrille.length; i++){
			for (int j=0; j<tabGrille[i].length; j++){
				if (tabGrille[i][j].IsVide()) System.out.print("| ");
				else if (tabGrille[i][j].IsJaune()) System.out.print("|O");
				else System.out.print("|X");
			}
			System.out.println("|");
		}
		for (int i=0; i<=tabGrille.length; i++){
			System.out.print("|"+(i+1));
		}
		System.out.println("|");
	}
	/**
	 * Placer le pion dans la colonne choisis
	 * @param choixCol
	 * @param Joueur
	 */
	public int Placer(int choixCol, int Joueur){
		if(quit==100)return 100;
		for (int i=tabGrille.length-1; i>=0; i--){
			if(tabGrille[i][choixCol-1].IsVide()){
				tabGrille[i][choixCol-1]= new Pion(Joueur);
				lastLigne=i;
				return i;
			}
		}
		return 0;
	}
	/**
	 * methode pour rentrer la colonne de notre choix
	 * @return
	 */
	public int Choix(){
//		int quit=100;
//		String quit="100";
		try{
			Scanner sc = new Scanner(System.in);
			System.out.println("\"" + 100 + "\" pour quitter le jeu");
			System.out.print("choisir la colonne : ");
			int choix=sc.nextInt();
			if (choix==100)return quit;
			System.out.println("\n\n\n\n");

			while (choix>7||choix<0){
				System.out.print("\ninscrire une colonne entre 1 et 7 :");
				choix=sc.nextInt();
			}
			if(tabGrille[0][choix-1].IsVide()==false){
				System.out.println("\nla colonne est pleine");
				choix=Choix();
			}
			lastCol=choix;
		}catch(InputMismatchException e){
			System.out.println("il ne faut pas inscrire une lettre mais un chiffre, recommence.");
			Choix();
		}
		return lastCol;
	}
	/**
	 * cet methode n'est plus utiliser pour le jeux
	 * mais elle est toujours fonctionnel
	 */
	public void Jouer(){
		int test=0;// compteur du joueur
		//		boolean gagner=true;
		afficher();

		do{

			if (test%2==0){
				System.out.println("\n*** JOUEUR 1 ***\n");
				Placer(Choix(), 1);
				if (Gagner(1)){
					System.out.println("\n***JOUEUR 1 TU A GAGNER !!!***");
					gagner=false;
					break;
				}
			}
			else {
				System.out.println("\n*** JOUEUR 2 ***\n");
				Placer(Choix(),2);
				if (Gagner(2)){
					System.out.println("\n***JOUEUR 2 TU A GAGNER !!!***");
					gagner=false;
					break;
				}
			}
			afficher();
			test++;
		}while(gagner);
		afficher();
		rejouer();

	}
	/**
	 * @return the gagner
	 */
	public boolean isGagner() {
		return gagner;
	}
	/**
	 * @param gagner the gagner to set
	 */
	public void setGagner(boolean gagner) {
		this.gagner = gagner;
	}
	/**
	 * 
	 * @param joueur //1 joueur1 et 2 joueur2
	 * @return
	 */
	public boolean Gagner(int joueur){
		if(CheckCol(joueur)||checkLig(joueur)||CheckDiag(joueur)||CheckDiag2(joueur))return true ;
		return false;
	}
	/** 
	 * verifie si la colonne est pas gagnante
	 * @return
	 */
	public boolean checkLig(int joueur){
		int i = lastCol-1, i2 = lastCol-1;
		int cpt = 0;
		Pion pionJ = new Pion(joueur);
		do
		{
			if(tabGrille[lastLigne][i].getCouleur() == pionJ.getCouleur()){
				cpt++;

			}
			else break;
			i++;
		}while(i<tabGrille[lastLigne].length);

		if(i2>0){
			do 
			{
				if(tabGrille[lastLigne][i2-1].getCouleur() == pionJ.getCouleur()){
					cpt++;

				}
				else break;
				i2--;
			}while(i2>0);
		}
		if(cpt>=4)return true;
		return false;
	}
	/**
	 * verifie si la ligne est pas gagnante
	 * @return
	 */
	public boolean CheckCol(int joueur){

		int compteur=0;
		Pion pionJ = new Pion(joueur); // cree le pion du joueur pour les test

		for(int i=lastLigne; i<tabGrille.length; i++){
			if(tabGrille[i][lastCol-1].getCouleur()==pionJ.getCouleur()){
				compteur++;
				//				System.out.println("\ncpt col : "+compteur);
			}
			else break;
		}
		if(compteur>=4)return true;
		return false;
	}
	/**
	 * verifie si la diagonal est gagnante
	 * @return
	 */
	public boolean CheckDiag(int joueur){
		int ligne = lastLigne, ligne2=lastLigne;
		int col = lastCol-1, col2=lastCol-1;
		Pion pionJ = new Pion(joueur);
		int compteur =0;
		do
		{
			if(tabGrille[ligne][col].getCouleur() == pionJ.getCouleur()){
				compteur++;
				//				System.out.println("cpt diag : " + compteur);
			}
			else break;
			col++;
			ligne--;
		}while(col<tabGrille[lastLigne].length && ligne>0);

		if(col2> 0 && ligne2<tabGrille.length-1){
			do{
				if(tabGrille[ligne2+1][col2-1].getCouleur()==pionJ.getCouleur()){
					compteur++;
					//				System.out.println("cpt diag : " + compteur);
				}
				else break;
				col2--;
				ligne2++;
			}while(col2>0 && ligne2<tabGrille.length-1);
		}
		if(compteur>=4)return true;


		else return false;


	}
	/**
	 * c'est le deuxieme test de diagonal
	 * @param joueur
	 * @return
	 */
	public boolean CheckDiag2(int joueur){
		int ligne = lastLigne, ligne2=lastLigne;
		int col = lastCol-1, col2=lastCol-1;
		Pion pionJ = new Pion(joueur);
		int compteur =0;
		do
		{
			if(tabGrille[ligne][col].getCouleur() == pionJ.getCouleur()){
				compteur++;
				//				System.out.println("cpt diag : " + compteur);
			}
			else break;
			col--;
			ligne--;
		}while(col>0 && ligne>0);

		if(col2<tabGrille.length && ligne2<tabGrille.length-1){
			do{
				if(tabGrille[ligne2+1][col2+1].getCouleur()==pionJ.getCouleur()){
					compteur++;
					//				System.out.println("cpt diag : " + compteur);
				}
				else break;
				col2++;
				ligne2++;
			}while(col2<tabGrille.length && ligne2<tabGrille.length-1);
		}
		if(compteur>=4)return true;


		else return false;


	}
	/**
	 * on affice le Main Title grace a cet methode
	 * intègre la methode choix
	 */
	public void Menu(){
		System.out.println(" _______          _________ _______  _______  _______  _        _______  _______       ___   ");
		System.out.println("(  ____ )|\\     /|\\__   __/(  ____ \\(  ____ \\(  ___  )( (    /|(  ____ \\(  ____ \\     /   )  ");
		System.out.println("| (    )|| )   ( |   ) (   | (    \\/| (    \\/| (   ) ||  \\  ( || (    \\/| (    \\/    / /) |  ");
		System.out.println("| (____)|| |   | |   | |   | (_____ | (_____ | (___) ||   \\ | || |      | (__       / (_) (_ ");
		System.out.println("|  _____)| |   | |   | |   (_____  )(_____  )|  ___  || (\\ \\) || |      |  __)     (____   _)");
		System.out.println("| (      | |   | |   | |         ) |      ) || (   ) || | \\   || |      | (             ) (  ");
		System.out.println("| )      | (___) |___) (___/\\____) |/\\____) || )   ( || )  \\  || (____/\\| (____/\\       | |  ");
		System.out.println("|/       (_______)\\_______/\\_______)\\_______)|/     \\||/    )_)(_______/(_______/       (_)  ");
		System.out.println("\n VOULEZ VOUS JOUER AU PUISSANCE 4 ?!!!!\n");
		System.out.println("\n\n-1: oui" + "\n-2: non");
		if(ChoixON()==true)gagner=true;

	}
	/**
	 * Methode pour faire un choix oui ou non
	 * @return true= le choix est oui
	 * false le choix est sois non, sois une erreur
	 */
	public boolean ChoixON(){
		Scanner sc = new Scanner (System.in);
		boolean b=false;
		switch (sc.nextInt()){
		case(1): 
			System.out.println("\n\n\n");
		b=true;
		gagner=true;
		return b;
		case(2): 
			System.out.println("\n");
		System.out.println("     ___      __    __         .______       ___________    ____  ______    __  .______         ");
		System.out.println("    /   \\    |  |  |  |        |   _  \\     |   ____\\   \\  /   / /  __  \\  |  | |   _  \\        ");
		System.out.println("   /  ^  \\   |  |  |  |  ______|  |_)  |    |  |__   \\   \\/   / |  |  |  | |  | |  |_)  |      ");
		System.out.println("  /  /_\\  \\  |  |  |  | |______|      /     |   __|   \\      /  |  |  |  | |  | |      /       ");
		System.out.println(" /  _____  \\ |  `--'  |        |  |\\  \\----.|  |____   \\    /   |  `--'  | |  | |  |\\  \\----.  ");
		System.out.println("/__/     \\__\\ \\______/         | _| `._____||_______|   \\__/     \\______/  |__| | _| `._____|  ");
		System.out.println("                                                                                               ");
		gagner=false;
		return b;
		default:{ 
			System.out.println("je n'ai pas compris votre demande. Recommencez :");
			ChoixON();
			return b;
		}
		}

	}
	/**
	 * methode pour rejouer une parti ou non
	 * qui integre la methode choix
	 */
	public void rejouer(){
		Scanner sc = new Scanner (System.in);
		System.out.println("\n Veux tu rejouer ? " + "\n-1: oui" + "\n-2: non");
		if(ChoixON()==true) gagner=true;
	}
	/**
	 * methode pour joué qui es utiliser dans le main
	 * différence avec Jouer(): permet de quitter pendant la selection de colonne
	 */
	public void JouerTest(){
		int test=0;// compteur du joueur
		//		boolean gagner=true;
		afficher();
		Grille a = new Grille();
		Grille b = new Grille();
		do{

			if (test%2==0){
				System.out.println("\n*** JOUEUR 1 ***\n");
//				a.Placer(Choix(), 1);
				if(a.Placer(Choix(), 1)==100){
					gagner=false;
					break;
				}
				if (Gagner(1)){
					System.out.println("\n***JOUEUR 1 TU A GAGNER !!!***");
					gagner=false;
					break;
				}
			}
			else {
				System.out.println("\n*** JOUEUR 2 ***\n");
//				b.Placer(Choix(),2);
				if(b.Placer(Choix(),2)==100){
					gagner=false;
					break;
				}
				if (Gagner(2)){
					System.out.println("\n***JOUEUR 2 TU A GAGNER !!!***");
					gagner=false;
					break;
				}
			}
			afficher();
			test++;
		}while(gagner);
		afficher();
		rejouer();

	}
}
