import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;



public class Login {
	private String user;
	private String mdp;
	
	public Login(){
		System.out.print("Entrez votre playlogin : ");
		Scanner sc = new Scanner(System.in);
		user = sc.nextLine();
		System.out.print("Entrez votre pwd : ");
		mdp = sc.nextLine();
		
		
	}
	
	public Login(String invite){
		user = invite;
		mdp = invite;
	}
	
	

public String getUser(){
	return user;
}
	
	
	public boolean verifier(){
		
		try{
			
		
		
		
		String queryplaylogin = "SELECT playlogin,pwd FROM joueur WHERE playlogin = '"+user+"';";
		String queryMdp = "SELECT playlogin,pwd FROM joueur WHERE pwd = '"+mdp+"';";
		
		ResultSet resultplaylogin = ConnectBdd.getInstance().createStatement().executeQuery(queryplaylogin);
		ResultSet resultMdp = ConnectBdd.getInstance().createStatement().executeQuery(queryMdp);
		
		
		if (resultplaylogin.next()){
			System.out.println("playlogin OK!");
			if (resultMdp.next()){
				System.out.println("Mdp OK!");
				return true;
			}
			System.out.println("*** Mauvais Mdp ***");
			return false;
		}
		System.out.println("*** Mauvais playlogin ***");
		return false;
		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean creation(){
		try{
		String queryTest = "SELECT * FROM joueur WHERE playlogin = ?;";
		PreparedStatement preparedTest = ConnectBdd.getInstance().prepareStatement(queryTest, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		preparedTest.setString(1, user);
		if (preparedTest.executeQuery().next()){
			System.out.println("Ce nom d'utilisateur existe déjà ! ");
			return false;
		}
		
		String query = "INSERT INTO joueur(playlogin,pwd) VALUES(?,?);";
		
		
		
		PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		prepared.setString(1, user);
		prepared.setString(2, mdp);
		
		
		if(prepared.executeUpdate()==1)System.out.println("*******************Création OK**********************");
		else System.out.println("ERREUR");
		prepared.close();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void consulterHistorique(){

		
		try{
			String nbHisto="5";
			System.out.print("voulez vous afficher plus ou moins d'historique au lieu de 5 ?\n"
					+ "\n1 - \toui"
					+ "\nautre - non");
			Scanner sc = new Scanner(System.in);
			Scanner scan = new Scanner(System.in);
			int choix = sc.nextInt();
			if(choix==1){
				System.out.print("indiquer combien :");
				nbHisto=scan.nextLine();
			}
			String query ="SELECT resultat,coup,score,combinaison FROM histoparti,joueur_histoparti,joueur WHERE joueur.id_joueur = joueur_histoparti.id_joueur AND"
					+ " histoparti.id_histoparti = joueur_histoparti.id_histoparti_k AND playlogin = ? ORDER BY id_histoparti DESC limit " + nbHisto ;
			PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared.setString(1, user);
			ResultSet result = prepared.executeQuery();
			ResultSetMetaData resultMeta = result.getMetaData();
			System.out.println("\n*********************************************************************************************************************************");

			for (int i = 1 ; i<=resultMeta.getColumnCount();i++){
				System.out.print("\t"+ resultMeta.getColumnName(i).toUpperCase()+"\t*");
			}
			System.out.println("\n*********************************************************************************************************************************");

			
			while(result.next()){
				for (int i =1; i<=resultMeta.getColumnCount();i++){
					System.out.print("\t"+result.getObject(i).toString() + "   \t|");
				}
			System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
			}
			
			
			
			prepared.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public int getUserId(){
		
		try{
			String query = "SELECT id_joueur FROM joueur WHERE playlogin = '"+user+"';";
			ResultSet result = ConnectBdd.getInstance().createStatement().executeQuery(query);
			if(result.next())return result.getInt(1);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getplaylogin(){
		String query = "SELECT playlogin FROM joueur  WHERE playlogin = '"+user+"';";
		ResultSet result;
		String nom ="";
		try {
			result = ConnectBdd.getInstance().createStatement().executeQuery(query);
			nom = result.getString("playlogin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nom;
	}

}
