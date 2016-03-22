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
	//Hvaða user eru þið að nota til að tengjast? - root
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
			cs = (CallableStatement)dbcon.prepareCall(query);
			//Hérna koma færibreyturnar inn í stored procedure.
			
			
			if(hotelString != null && date1 != null){
				cs.setString(1, hotelString);
				cs.setDate(2, date1);
				cs.setDate(3,  date2);
			}
			//Erum að ná í facilities
			else if(date1 == null){
				cs.setString(1, hotelString);
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
			//Þurfum að finna besta tímann til að loka á tenginguna.  Er ekki viss um að þetta sé besti staðurinn.
			//closeConnection();
		}
		return returnData;		
	}
	
	public void insertQueryDatabase(String query, int hotelID, int roomID, Date date1, Date date2){
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki búið til statement");
		}
		try {
			cs = (CallableStatement)dbcon.prepareCall(query);
			//Hérna koma færibreyturnar inn í stored procedure.
			
			cs.setInt(1, hotelID);
			cs.setInt(2, roomID);
			cs.setDate(3, date1);
			cs.setDate(4, date2);
			
			//Kallað á stored procedure
			cs.execute();
			//Niðurstöður settar í resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengið result! "+e);
		}
		
		finally{
			//Þurfum að finna besta tímann til að loka á tenginguna.  Er ekki viss um að þetta sé besti staðurinn.
			//closeConnection();
		}
	}
	
	public void dropQueryDatabase(String query, int BookID){
		try {
			stmt = (Statement)dbcon.createStatement();	
		} catch (Exception e) {
			System.out.println("Get ekki búið til statement");
		}
		try {
			cs = (CallableStatement)dbcon.prepareCall(query);
			//Hérna koma færibreyturnar inn í stored procedure.
			
			cs.setInt(1, BookID);
			
			//Kallað á stored procedure
			cs.execute();
			//Niðurstöður settar í resultSet
			resultSet = cs.getResultSet();
		} catch (Exception e) {
			System.out.println("Get ekki fengið result! "+e);
		}
		
		finally{
			//Þurfum að finna besta tímann til að loka á tenginguna.  Er ekki viss um að þetta sé besti staðurinn.
			//closeConnection();
		}
	}

	private ArrayList<ArrayList<String>> convertResultSetToLists( ResultSet result) throws SQLException{
		int numColumns = result.getMetaData().getColumnCount();
		ArrayList<ArrayList<String>> dataListArray = new ArrayList<ArrayList<String>>();
		//Býr til lista fyrir hvern dálk í útkomunni og setur inn í aðal listann.
		for (int i = 0; i < numColumns; i++) {
			 dataListArray.add( new ArrayList<String>() );
		}
		
		//Fyllir listana fyrir hvern dálk - Endurbætt falleg lausn.
		while( result.next() ){	
			 for(int i = 1; i <=  numColumns; i++){
				  dataListArray.get(i-1).add( result.getString( resultSet.getMetaData().getColumnName(i) ));
			  }
		}
		return dataListArray;
	}
	
	//Fall sem lokar á gagnagrunnstenginguna.
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
