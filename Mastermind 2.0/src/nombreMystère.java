import java.util.Scanner;

public class nombreMystère {
	int choix, page=1;
	double miles;

public void nBMystère(){
	do{

		switch(page){
		case 1:
			listerMenu();
			choix = saisirChoix();
			afficherChoix(choix);
			if (choix==1)
				page=2;
			else if (choix==2)
				page=3;
			else if (choix>3 || choix<1)
				page=1;
			else if (choix ==3)
				page =4;
			break;
		case 2:
			chiffreMystère();
			retour();
			choix=saisirChoix();
			if (choix==1)
				page=2;
			else page=1;
			break;
		case 3:
			System.out.println("quelle sont les miles que vous voulez convertir ?");
			miles=saisirMiles();
			milesKm(miles);
			retour();
			choix=saisirChoix();
			if (choix==1)
				page=3;
			else page=1;
			break;
		}			
	}
	while (page!=4);
}

public static void listerMenu (){
	System.out.println("Que voulez-vous faire ? SAisissez l'option souhaitée.\n");
	System.out.println("1 - Joué au jeux du nombre mystère");
	System.out.println("2 - Convertisseur miles en km");
	System.out.println("3 - Quitter");

}

public static int saisirChoix(){
	Scanner sc = new Scanner(System.in);
	int choix = sc.nextInt();
	return choix;
}

public static void afficherChoix(int choix){
	switch (choix){
	case 1 :
		System.out.println("c'est parti une devinète :p !\n");
		break;
	case 2 :
		System.out.println("tu a envie de convertir ? !\n");
		break;
	case 3 :
		System.out.println("aurevoir!!\n");
		break;
	default :
		System.out.println("Vous avez mal saisi votre réponse, réessayez !\n");
		

	}
}

public static void chiffreMystère(){
	int nbM, nb, count,win=1;
	Scanner sc = new Scanner(System.in);
	/*
	 *Random rand = new Random();
	 *nombreMystère = rand.nextInt(1000)+1
	 */
	nbM=(int)(Math.random()*41);
	count=0;
	do{
		System.out.println("quelle es votre nombre proposé ;)? Attention le chiffre mystère est compris entre 0 et 40!!!!\n");
		System.out.println("#####Si vous désirez quitter le jeu, rentrer 117 dans la console#####\n");
		nb=sc.nextInt();
		if (nb<nbM)
			System.out.println("c'est plus essaye encore!\n");
		else if (nb>nbM)
			System.out.println("c'est moins !\n");
		if (nb<=40 && nb>=0){				
			count=count+1;
		}
		System.out.println("tu es a " + count + " essai(s) plus que " + (15-count) + "essai(s)\n");
		if (count>15){
			System.out.println("tu a dépacer ton nombre d'essais \n");
			nb=nbM;
			win=0;
		}
		if (nb==117)
			nb=nbM;
	}
	while (nbM!=nb);
	if (win==1){
		System.out.println("BRAVO tu a gagner une clio 4 !!!\n");
		System.out.println("vous avez fais " + count + " essais\n");
	}
	else
		System.out.println("tu a perdu :/ réessayes ;)\n");



}

public static void milesKm(double miles){
	miles=1.609*miles;
	System.out.println(miles + " Km\n");

}

public static double saisirMiles(){
	Scanner sc = new Scanner(System.in);
	double miles = sc.nextDouble();
	return miles;

}

public static void retour(){
	System.out.println("voules vous refaire l'application ?\n");
	System.out.println("1 - oui");
	System.out.println("autre - non");
}
}
