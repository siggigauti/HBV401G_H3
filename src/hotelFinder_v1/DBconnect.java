package hotelFinder_v1;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.Date;

public class DBconnect {
	//Nafni� � database er fyrir aftan aftasta sk�striki�  
	//�etta er � forminu hostinn:port(mysql port)/database name
	private String url = "jdbc:mysql://localhost:3306/hotelsearch";
	//Hva�a user eru �i� a� nota til a� tengjast? - root
	private String userName = "root";
    private Connection dbcon;
	private ResultSet resultSet = null;
	private Statement stmt;
	private CallableStatement cs = null;
    
	public DBconnect(){	
		try {
			System.out.println("Connecting to database...");
			connectToDataBase();
			System.out.println("Connected! :)");
		} catch (SQLException e) {
			System.out.println("Error connecting to database!");
		}
	}
	
	//Fall sem tengist gagnagrunninum.
	private void connectToDataBase() throws SQLException{
		//Tengjums: getConnection( url, user, pass)
		dbcon = (Connection)DriverManager.getConnection(url, userName, "");	
	}
	
	
	//�arf a� sko�a �etta fall a�eins betur,  ef vi� �tlum a� nota stored procedures �� ver�um vi� a� breyta �essu a�eins
	//Vi� �yrftum eitt fall fyrir hverja stored procedure �v� f�ribreyturnar eru mismunandi og mismargar.
	//Fall sem skilar ni�ust��um �r gagnagrunns query.
	public ArrayList<ArrayList<String>> queryDataBase( String query, Date date1, Date date2 , String hotelString){
		ArrayList<ArrayList<String>> returnData = new ArrayList<ArrayList<String>>();		
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki b�i� til statement");
		}
		try {
			cs = (CallableStatement)dbcon.prepareCall(query);
			//H�rna koma f�ribreyturnar inn � stored procedure.
			
			
			if(hotelString != null && date1 != null){
				cs.setString(1, hotelString);
				cs.setDate(2, date1);
				cs.setDate(3,  date2);
			}
			//Erum a� n� � facilities
			else if(date1 == null){
				cs.setString(1, hotelString);
			}
			else{
				cs.setDate(1, date1);
				cs.setDate(2,  date2);			
			}
			
			//Kalla� � stored procedure
			cs.execute();
			//Ni�urst��ur settar � resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengi� result! "+e);
		}
		try {
			System.out.println("Breyti g�gnum � lista af strengjum");	
			returnData = convertResultSetToLists(resultSet);
		} catch (Exception e) {
			System.out.println("Gat ekki breytt g�gnum � lista :(" + e);
		}	
		finally{
			//�urfum a� finna besta t�mann til a� loka � tenginguna.  Er ekki viss um a� �etta s� besti sta�urinn.
			//closeConnection();
		}
		return returnData;		
	}
	
	public void insertQueryDatabase(String query, int hotelID, int roomID, Date date1, Date date2){
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki b�i� til statement");
		}
		try {
			cs = (CallableStatement)dbcon.prepareCall(query);
			//H�rna koma f�ribreyturnar inn � stored procedure.
			
			cs.setInt(1, hotelID);
			cs.setInt(2, roomID);
			cs.setDate(3, date1);
			cs.setDate(4, date2);
			
			//Kalla� � stored procedure
			cs.execute();
			//Ni�urst��ur settar � resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengi� result! "+e);
		}
		
		finally{
			//�urfum a� finna besta t�mann til a� loka � tenginguna.  Er ekki viss um a� �etta s� besti sta�urinn.
			//closeConnection();
		}
	}
	
	public void dropQueryDatabase(String query, int BookID){
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki b�i� til statement");
		}
		try {
			cs = (CallableStatement)dbcon.prepareCall(query);
			//H�rna koma f�ribreyturnar inn � stored procedure.
			
			cs.setInt(1, BookID);
			
			//Kalla� � stored procedure
			cs.execute();
			//Ni�urst��ur settar � resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengi� result! "+e);
		}
		
		finally{
			//�urfum a� finna besta t�mann til a� loka � tenginguna.  Er ekki viss um a� �etta s� besti sta�urinn.
			//closeConnection();
		}
	}

	private ArrayList<ArrayList<String>> convertResultSetToLists( ResultSet result) throws SQLException{
		int numColumns = result.getMetaData().getColumnCount();
		ArrayList<ArrayList<String>> dataListArray = new ArrayList<ArrayList<String>>();
		//B�r til lista fyrir hvern d�lk � �tkomunni og setur inn � a�al listann.
		for (int i = 0; i < numColumns; i++) {
			 dataListArray.add( new ArrayList<String>() );
		}
		
		//Fyllir listana fyrir hvern d�lk - Endurb�tt falleg lausn.
		while( result.next() ){	
			 for(int i = 1; i <=  numColumns; i++){
				  dataListArray.get(i-1).add( result.getString( resultSet.getMetaData().getColumnName(i) ));
			  }
		}
		return dataListArray;
	}
	
	//Fall sem lokar � gagnagrunnstenginguna.
	public void closeConnection() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (stmt != null) {
	    	  stmt.close();
	      }

	      if (dbcon != null) {
	        dbcon.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
