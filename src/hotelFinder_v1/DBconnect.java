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
	//Hva�a user eru �i� a� nota til a� tengjast?
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
			//Gamli k��inn fyrir Stored Procedure, (spurning um a� hafa hann � sm� stund ef vi� skyldum vilja nota hann aftur?)
			cs = (CallableStatement)dbcon.prepareCall(query);
			//H�rna koma f�ribreyturnar inn � stored procedure.
			
			//Ef hotelID er staerra en -1 tha erum vid ad leita ad serstoku hoteli. Annars leitum vid i ollum hotelum.
			if(hotelString != null){
				cs.setString(1, hotelString);
				cs.setDate(2, date1);
				cs.setDate(3,  date2);
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
			closeConnection();
		}
		//closeConnection();
		return returnData;		
	}
	
	/*
	public int[] getHotelIDs(){
		int[] returnData = null; //�arf a� vera null.
		int total = 0;
		ResultSet rs;
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki b�i� til statement");
		}
		try {
			String sql ="SELECT HotelID FROM hotel";
			rs = stmt.executeQuery(sql);
			rs.last();
			total = rs.getRow();
			returnData = new int[total];
			rs.beforeFirst();
			int i = 0;
			while( rs.next()){
				returnData[i] = rs.getInt("hotelID");
				i++;
			}
		} catch (Exception e) {
			System.out.println("Gat ekki n�� � d�ti�: "+e);
		}
		finally{
			//Lokar tengingunni eftir ad buid er ad na i gogn ur database.
			closeConnection();
		}
		return returnData;
	}*/
	
	private ArrayList<ArrayList<String>> convertResultSetToLists( ResultSet result) throws SQLException{
		int numColumns = result.getMetaData().getColumnCount();
		ArrayList<ArrayList<String>> dataListArray = new ArrayList<ArrayList<String>>();
		
		//B�r til lista fyrir hvern d�lk � �tkomunni og setur inn � a�al listann.
		for (int i = 0; i < numColumns; i++) {
			 dataListArray.add( new ArrayList<String>() );
		}
		
		//Fyllir listana fyrri hvern d�lk
		while( result.next() ){
			dataListArray.get(0).add( result.getString("hotelID") );
			dataListArray.get(1).add( result.getString("hotelName") );
			dataListArray.get(2).add( result.getString("hotelChain") );
			dataListArray.get(3).add( result.getString("hotelLocation") );
			dataListArray.get(4).add( result.getString("roomID") );	
			dataListArray.get(5).add( result.getString("numPersons") );	
			dataListArray.get(6).add( result.getString("rate") );		
		}
				
		return dataListArray;
	}
	
	//Fall sem lokar � gagnagrunnstenginguna.
	private void closeConnection() {
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
