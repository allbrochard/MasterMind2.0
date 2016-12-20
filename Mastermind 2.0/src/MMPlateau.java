
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MMPlateau {

	private int NBCOMBI=4;
	private int nbEssai = 10;
	private int nbCoup = 0;
	private int dif=0;
	private MMPion[] tabRand = new MMPion[NBCOMBI];
	private MMPion[] tabProp = new MMPion [NBCOMBI];
	private MMPion[] tabComp = new MMPion [NBCOMBI];
	private MMPion[] tabCompProp = new MMPion [NBCOMBI];
	private ArrayList<MMHistorique> historique = new ArrayList<>();
	private int score=0;
	private int idUser;
	private String dificult;
	private int cptBp;
	private int cptMp;
	private String partCoup;
	public MMPlateau(Login login){
		this.idUser = login.getUserId();
		System.out.println("***Choisissez la difficulter***");
		System.out.println("\n1 - Facile : 30 essais");
		System.out.println("\n2 - Moyen : 15 essais");
		System.out.println("\n3 - Difficile : 7 essais");
		//		System.out.println("\n4 - Hardcore : 15 essais et une combinaison de 6 chiffres");
		Scanner sc = new Scanner(System.in);
		switch(sc.nextInt()){
		case 1 : 
			nbEssai=30;
			dif=100;
			dificult="Facile";
			partCoup="30 coups";
			break;
		case 2 : 
			nbEssai=15;
			dif=200;
			dificult="Moyen";
			partCoup="15 coups";
			break;
		case 3 : 
			nbEssai=7;
			dif=400;
			dificult="Difficile";
			partCoup="7 coups";
			break;

		default :System.out.println("je n'ai pas saisis votre demande, vous serez donc en difficulter moyenne!");
		}
		try{

			String query ="SELECT resultat, coup, score, combinaison, playlogin,  FROM histoparti,joueur_histoparti,joueur WHERE joueur.id_joueur = joueur_histoparti.id_joueur AND"
					+ " histoparti.id_histoparti = joueur_histoparti.id_histoparti_k order by score desc limit 10;" ;
			PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet result = prepared.executeQuery();
			ResultSetMetaData resultMeta = result.getMetaData();
			System.out.println("\n*********************************************************************************************************************************************************");
			for (int i = 1 ; i<=resultMeta.getColumnCount();i++){
				System.out.print("\t"+ resultMeta.getColumnName(i).toUpperCase()+"\t*");
			}
			System.out.println("\n*********************************************************************************************************************************************************");

			while(result.next()){
				for (int i =1; i<=resultMeta.getColumnCount();i++){
					System.out.print("\t"+result.getObject(i).toString() + "   \t|");
				}
				System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
			prepared.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		jouer();

	}
	public void jouer(){
		System.out.println("nombre d'essais : " + nbEssai + " combinaison de " + NBCOMBI + " chiffres\n");
		for (int i=0; i<NBCOMBI; i++){
			int rand = (int)new Random().nextInt(8)+1;

			tabRand[i] = new MMPion(rand);

		}

		System.out.println("Ca y'est j'ai trouvé la combinaison à deviner ! c'est "+ Arrays.toString(tabRand));

		proposer();
	}



	@SuppressWarnings("resource")
	public void proposer(){
		int prop;
		System.out.println("Proposer une séquence de " + NBCOMBI + " chiffres entre 1 et 8");
		System.out.println("Entrez un 0 pour quitter");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i<NBCOMBI;i++){

			System.out.print("Chiffre "+(i+1)+" : ");
			prop = sc.nextInt();
			while (prop >8 || prop <0){
				System.out.println("Mauvaise entrée, proposez une chiffre entre 1 et 8");
				System.out.print("Chiffre "+(i+1)+" : ");
				prop = sc.nextInt();
			}
			//if(prop == 0 )quitter();

			tabProp[i]= new MMPion(prop);

		}

		bienPlace();

	}
	/**
	 * compare les bien placés
	 */
	public void bienPlace(){
		cptBp = 0;
		cptMp = 0;

		for (int i =0; i<tabRand.length;i++){
			if (tabRand[i].getnPion() == tabProp[i].getnPion()){
				tabCompProp[i] = new MMPion(0);
				tabComp[i] = new MMPion(0);
				cptBp ++;
			}
			else {tabComp[i] = new MMPion(tabRand[i].getnPion());tabCompProp[i] = new MMPion(tabProp[i].getnPion());}

		}
		if (cptBp == tabRand.length){
			System.out.println("BRAVO c'est gagné !");
			nbCoup++;
			score=(nbEssai-nbCoup)*dif;
			System.out.println("votre score est de : "+score);
			sendPartie("gagner");

		}
		else{

			malPlace();

		}
	}

	public void malPlace(){


		for (int i = 0; i <tabRand.length; i++){
			for (int j = 0; j<tabRand.length; j++){
				if (tabComp[i].getnPion()!=0&& tabCompProp[j].getnPion()!=0 && tabComp[i].getnPion() == tabCompProp[j].getnPion()){
					tabComp[i].setnPion(0);
					tabCompProp[j].setnPion(0);
					cptMp ++;
				}
			}
			//			else {
			//				cptBp++;
			//			}
		}
		System.out.println();
		System.out.println("Il y a "+cptBp+" bien placé(s) et "+cptMp+" mal placé(s)");


		MMHistorique vHisto = new MMHistorique(tabProp,cptBp,cptMp);
		historique.add(vHisto);


		for (int i =0; i<historique.size();i++) {
			System.out.println(historique.get(i));
		}




		System.out.println();
		nbEssai --;
		nbCoup++;



		if (nbEssai>0){

			proposer();
		}
		else{
			System.out.println();
			System.out.println("Tu as perdu ! LOOSER !");
			System.out.println("la combinaison à trouver était : "+ Arrays.toString(tabRand));
			System.out.println();
			sendPartie("perdu");

		}


	}
	public void sendPartie(String win){
		int idPartie = 0;
		try{


			//requete sql pour insert la partie
			String queryPartie = "INSERT INTO histoparti(resultat,coup,combinaison,score) VALUES (?,?,?,?) RETURNING id_histoParti;";


			PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(queryPartie, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared.setString(1, win);
			prepared.setInt(2, nbCoup);
			prepared.setString(3, Arrays.toString(tabRand));
			prepared.setInt(4,score);
			prepared.execute();

			ResultSet result = prepared.getResultSet();
			if(result.first()) idPartie = result.getInt(1);


			//requete sql pour insert la table de jointure
			String queryUserPartie = "INSERT INTO joueur_histoparti (id_joueur,id_histoparti_k) VALUES (?,?);";

			PreparedStatement prepared2 = ConnectBdd.getInstance().prepareStatement(queryUserPartie, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared2.setInt(1, idUser);
			prepared2.setInt(2, idPartie);
			prepared2.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 

	@SuppressWarnings("resource")
	public void connexion(){
		System.out.print("Entrez votre login : ");
		Scanner sc = new Scanner(System.in);
		String user1 = sc.nextLine();
		System.out.println("Entrez votre mdp : ");
		String mdp1 = sc.nextLine();

		Login login = new Login(user1,mdp1);

		if (login.verifier())user = user1;

	}


	@SuppressWarnings("resource")
	public void creerCompte(){
		System.out.println("***********Création de compte*****************");
		System.out.print("Entrez votre login : ");
		Scanner sc = new Scanner(System.in);
		String user1 = sc.nextLine();
		System.out.print("Entrez votre mdp : ");
		String mdp1 = sc.nextLine();

		Login login = new Login(user1,mdp1);
		login.creation();
	}

	 */



}