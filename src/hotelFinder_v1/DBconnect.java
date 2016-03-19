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
	//Nafnið á database er fyrir aftan aftasta skástrikið  
	//Þetta er á forminu hostinn:port(mysql port)/database name
	private String url = "jdbc:mysql://localhost:3306/hotelsearch";
	//Hvaða user eru þið að nota til að tengjast?
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
	
	
	//Þarf að skoða þetta fall aðeins betur,  ef við ætlum að nota stored procedures þá verðum við að breyta þessu aðeins
	//Við þyrftum eitt fall fyrir hverja stored procedure því færibreyturnar eru mismunandi og mismargar.
	//Fall sem skilar niðustöðum úr gagnagrunns query.
	public ArrayList<ArrayList<String>> queryDataBase( String query, Date date1, Date date2 , String hotelString){
		ArrayList<ArrayList<String>> returnData = new ArrayList<ArrayList<String>>();		
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki búið til statement");
		}
		try {
			//Gamli kóðinn fyrir Stored Procedure, (spurning um að hafa hann í smá stund ef við skyldum vilja nota hann aftur?)
			cs = (CallableStatement)dbcon.prepareCall(query);
			//Hérna koma færibreyturnar inn í stored procedure.
			
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
			
			//Kallað á stored procedure
			cs.execute();
			//Niðurstöður settar í resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengið result! "+e);
		}
		try {
			System.out.println("Breyti gögnum í lista af strengjum");	
			returnData = convertResultSetToLists(resultSet);
		} catch (Exception e) {
			System.out.println("Gat ekki breytt gögnum í lista :(" + e);
		}	
		finally{
			closeConnection();
		}
		//closeConnection();
		return returnData;		
	}
	
	/*
	public int[] getHotelIDs(){
		int[] returnData = null; //Þarf að vera null.
		int total = 0;
		ResultSet rs;
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki búið til statement");
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
			System.out.println("Gat ekki náð í dótið: "+e);
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
		
		//Býr til lista fyrir hvern dálk í útkomunni og setur inn í aðal listann.
		for (int i = 0; i < numColumns; i++) {
			 dataListArray.add( new ArrayList<String>() );
		}
		
		//Fyllir listana fyrri hvern dálk
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
	
	//Fall sem lokar á gagnagrunnstenginguna.
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
