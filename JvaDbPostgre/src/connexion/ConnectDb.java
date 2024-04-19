package connexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectDb {
	public static void Ajouter(Connection connection,String name,String prenom,int age) {
		try {
			String sql = "INSERT INTO ETUDIANT (NOM, PRENOM,AGE) VALUES (?,?,?);";
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.setString(1,name);
			stm.setString(2,prenom);
			stm.setInt(3,age);			
			stm.executeUpdate();
			stm.close();
			System.out.println("Ajouter");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void Search(Connection connection,String name){
		try {
			String sql = "SELECT * FROM ETUDIANT where nom =?;";
			PreparedStatement stm1 = connection.prepareStatement(sql);
			stm1.setString(1, name);
			ResultSet rs = stm1.executeQuery();
			while ( rs.next() ) {
				int id = rs.getInt("id");
				int age  = rs.getInt("age");
				String  prenom = rs.getString("prenom");
				System.out.println("----------------------------------");
				System.out.println( "ID = " + id );
				System.out.println( "NAME = " + name );
				System.out.println( "PRENOM = " + prenom );
				System.out.println( "AGE = " + age );
				System.out.println("----------------------------------");
			}
			rs.close();
			stm1.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void Modifie(Connection connection,String name,int age) {
		try {
			String sql = "UPDATE ETUDIANT SET AGE = ? WHERE nom = ? ;";
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.setInt(1, age);
			stm.setString(2, name);
			stm.executeUpdate();
			stm.close();
			System.out.println("Modifier");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void Affiche(Connection connection){
		try {
			String sql = "SELECT * FROM ETUDIANT;";
			Statement stm1 = connection.createStatement();
			ResultSet rs = stm1.executeQuery(sql);
			while ( rs.next() ) {
				int id = rs.getInt("id");
				int age  = rs.getInt("age");
				String name = rs.getString("nom");
				String  prenom = rs.getString("prenom");
				System.out.println("----------------------------------");
				System.out.println( "ID = " + id );
				System.out.println( "NAME = " + name );
				System.out.println( "PRENOM = " + prenom );
				System.out.println( "AGE = " + age );
				System.out.println("----------------------------------");
			}
			rs.close();
			stm1.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void Supprimer(Connection connection,String name) {
		try {
			String sql2 = "DELETE FROM ETUDIANT WHERE nom=? ;";
			PreparedStatement stm1 = connection.prepareStatement(sql2);
			stm1.setString(1, name);
			stm1.executeUpdate();
			stm1.close();
			System.out.println("Supprimer");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		Connection connection=null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","toto","1234");
			if(connection!=null) {
				boolean tr = true;
				while(tr) {
					System.out.println("Menu Principale");
					System.out.println("1: Ajouter");
					System.out.println("2: Modifier");
					System.out.println("3: Supprimer");
					System.out.println("4: Rechercher");
					System.out.println("5: Afficher");
					System.out.println("6: Quitter");
					System.out.print("Option: ");
					Scanner scan = new Scanner(System.in);
					int option = scan.nextInt();
					switch(option) {
					case 1:
						System.out.println("Nom: ");
						String name = scan.next();
						System.out.println("Prenom: ");
						String prenom = scan.next();
						System.out.println("Age: ");
						int age = scan.nextInt();
						Ajouter(connection,name,prenom,age);
						break;
					case 2:
						System.out.println("Nom: ");
						String name1 = scan.next();
						System.out.println("Age: ");
						int age1 = scan.nextInt();
						Modifie(connection,name1,age1);
						break;
					case 3:
						System.out.println("Nom: ");
						String name11 = scan.next();
						Supprimer(connection,name11);
						break;
					case 4:
						System.out.println("Nom: ");
						String name111 = scan.next();
						Search(connection,name111);
						break;
					case 5:
						Affiche(connection);
						break;
					case 6:
						System.out.println("Aurevoir");
						connection.close();
						tr = false;
						break;
					}
				}	
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}