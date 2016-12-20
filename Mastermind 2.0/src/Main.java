import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class Main {

	public static void main(String[] args) {



		menu1();


	}

	@SuppressWarnings("resource")
	public static void menu1(){
		int rep1;

		Scanner sc = new Scanner(System.in);
		do{
			System.out.println(" _______________________________________________");
			System.out.println("|\t\t\t\t\t\t|");
			System.out.println("|\t************ Bienvenue ************\t|");
			System.out.println("|\t\t\t\t\t\t|");
			System.out.println("|\t1- Se connecter à un compte existant\t|");
			System.out.println("|\t2- Se connecter en tant qu'invite\t|");
			System.out.println("|\t3- Créer un compte\t\t\t|");
			System.out.println("|\t4- Consulter tout l'historique\t\t|");
			System.out.println("|\t5- Quitter\t\t\t\t|");
			System.out.println("|_______________________________________________|");
			rep1 = sc.nextInt();
		}while(rep1<0 && rep1>4);

		switch(rep1){
		case 1 :
			Login login1 = new Login();
			if(login1.verifier())menuJoueur(login1);
			else menu1();
			break;

		case 2 :
			Login login3 = new Login("invite");
			if(login3.verifier())menuJoueur(login3);
			break;

		case 3 :
			Login login2 = new Login();
			if(login2.creation())menuJoueur(login2);
			break;

		case 4 : 
			consulterAllHistorique();
			menu1();
			break;


		default:
			System.out.println("Au plaisir de vous revoir !");

		}

	}

	@SuppressWarnings("resource")
	public static void menuJoueur(Login login){
		int rep2;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("\t\tMenu "+ login.getUser());
			System.out.println("<><><><><><><><><><>-<><><><><><><><><><><>");
			System.out.println("<> _____________________________________ <>");
			System.out.println("<>|\t1 - Historique\t\t\t|<>");
			System.out.println("<>|\t2 - Jouer au MasterMind\t\t|<>");
			System.out.println("<>|\t3 - Jouer au Puissance 4\t|<>");
			System.out.println("<>|\t4 - Nombre Mystère et convertir\t|<>");
			System.out.println("<>|\t5 - Quitter\t\t\t|<>");
			System.out.println("<>|_____________________________________|<>");
			System.out.println("<><><><><><><><><><>-<><><><><><><><><><><>");
			rep2 = sc.nextInt();
		}while(rep2<0 && rep2>4);

		switch(rep2){
		case 1 :
			login.consulterHistorique();
			menuJoueur(login);
			break;
		case 2 :
			MMPlateau game = new MMPlateau(login);
			menuJoueur(login);
			break;
		case 3:
			Grille tab;
			tab = new Grille();
			tab.Menu();
			while(tab.isGagner()==true){
				tab = new Grille();
				if(tab.isGagner()==true)tab.JouerTest();
			}
			menuJoueur(login);
			break;
		case 4:
			nombreMystère nB = new nombreMystère();
			nB.nBMystère();
			menuJoueur(login);
			break;
		case 5 :
			System.out.println("A bientot " + login.getUser() + " !" );
			menu1();
			break;

		}

	}
	public static void consulterAllHistorique(){

		try{
			String nbHisto="5";
			System.out.print("voulez vous afficher plus ou moins d'historique au lieu de 5 ?\n"
					+ "\n1 - \toui"
					+ "\nautre - non");
			Scanner sc = new Scanner(System.in);
			Scanner scan = new Scanner(System.in);
			int choix = sc.nextInt();
			if(choix==1){
				System.out.print("indiquer combien:");
				nbHisto=scan.nextLine();
			}
			String query ="SELECT resultat,coup,score,combinaison, playlogin, difficulter, nbcoupmax FROM histoparti,joueur_histoparti,joueur WHERE joueur.id_joueur = joueur_histoparti.id_joueur AND"
					+ " histoparti.id_histoparti = joueur_histoparti.id_histoparti_k order by score desc limit " + nbHisto ;

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
	}
}






