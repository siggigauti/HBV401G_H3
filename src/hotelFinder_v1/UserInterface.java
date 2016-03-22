package hotelFinder_v1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Scrollbar;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class UserInterface {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	ArrayList<Hotel> hotels;
	JList hotelList;
	JList hotelRoomList;
	DefaultListModel hotelListModel, hotelRoomListModel;
	JCheckBox ckboxRoomService,chckbxBreakfast,chckbxRestaurant, chckbxPool, chckbxLuggageStorage, chckbxGarage, chckbxBar, chckbxGym, chckbxSpecialEvents, chckbxWifi, chckbxHandicapAccess, chckbxComputers, chckbxSpa;
	JRadioButton rdbtnName, rdbtnLocation, rdbtnHotelChain, rdbtnSubstring, rdbtnAllHotels;
	JTextPane textPaneSearchString;
	int selectedRoomID;
	private JButton btnBookSelectedRoom;
	HotelFinder hotelfinder;
	private JTextField txtCheckIn;
	private JTextField txtCheckOut;
	private JLabel lblDagsetningar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 829, 906);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		hotelListModel = new DefaultListModel();
		hotelRoomListModel = new DefaultListModel();
		
		hotelList = new JList(hotelListModel);
		hotelList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg) {
				try {
					insertIntoHotelRoomList(hotelList.getSelectedIndex());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				btnBookSelectedRoom.setEnabled(false);
				
			}
		});
		
		hotelList.setBounds(296, 49, 507, 147);
		frame.getContentPane().add(hotelList);
		
		hotelRoomList = new JList(hotelRoomListModel);
		hotelRoomList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnBookSelectedRoom.setEnabled(true);
				try {
					selectedRoomID = hotels.get( hotelList.getSelectedIndex() ).getHotelRooms().get(hotelRoomList.getSelectedIndex()).getId();
				} catch (Exception e) {
					
				}
				
			}
		});
		hotelRoomList.setBounds(296, 207, 507, 650);
		

		frame.getContentPane().add(hotelRoomList);
		
		
		
		
		JLabel lblHtel = new JLabel("Hotels");
		lblHtel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblHtel.setBounds(508, 11, 117, 35);
		frame.getContentPane().add(lblHtel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 457, 276, 235);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		 ckboxRoomService = new JCheckBox("Room Service");
		 
		 
		ckboxRoomService.setBounds(6, 46, 118, 23);
		panel.add(ckboxRoomService);
		
		 chckbxBreakfast = new JCheckBox("Breakfast");
		
		
		chckbxBreakfast.setBounds(6, 72, 97, 23);
		panel.add(chckbxBreakfast);
		
		 chckbxRestaurant = new JCheckBox("Restaurant");
		 
		
		chckbxRestaurant.setBounds(6, 98, 97, 23);
		panel.add(chckbxRestaurant);
		
		 chckbxPool = new JCheckBox("Pool");
		 
		 
		chckbxPool.setBounds(6, 124, 97, 23);
		panel.add(chckbxPool);
		
		 chckbxLuggageStorage = new JCheckBox("Luggage storage");
		 

		chckbxLuggageStorage.setBounds(6, 150, 118, 23);
		panel.add(chckbxLuggageStorage);
		
		 chckbxGarage = new JCheckBox("Garage");
		 

		chckbxGarage.setBounds(6, 179, 97, 23);
		panel.add(chckbxGarage);
		
		 chckbxBar = new JCheckBox("Bar");
		
		 
		chckbxBar.setBounds(126, 46, 97, 23);
		panel.add(chckbxBar);
		
		 chckbxGym = new JCheckBox("Gym"); 
		 
		chckbxGym.setBounds(126, 72, 97, 23);
		panel.add(chckbxGym);
		
		 chckbxSpecialEvents = new JCheckBox("Special events");
		 
		 
		chckbxSpecialEvents.setBounds(126, 98, 118, 23);
		panel.add(chckbxSpecialEvents);
		
		 chckbxWifi = new JCheckBox("Wi-Fi");
		
		 
		chckbxWifi.setBounds(126, 124, 97, 23);
		panel.add(chckbxWifi);
		
		 chckbxHandicapAccess = new JCheckBox("Handicap access");
		
		
		chckbxHandicapAccess.setBounds(126, 150, 144, 23);
		panel.add(chckbxHandicapAccess);
		
		 chckbxComputers = new JCheckBox("Computers");
		 
		
		chckbxComputers.setBounds(126, 179, 97, 23);
		panel.add(chckbxComputers);
		
		 chckbxSpa = new JCheckBox("Spa");
		 
		 
		chckbxSpa.setBounds(6, 205, 97, 23);
		panel.add(chckbxSpa);
		
		JLabel lblFacilities = new JLabel("Facilities");
		lblFacilities.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFacilities.setBounds(90, 11, 83, 28);
		panel.add(lblFacilities);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 49, 266, 385);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		 textPaneSearchString = new JTextPane();
		textPaneSearchString.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textPaneSearchString.setBounds(6, 175, 251, 32);
		panel_1.add(textPaneSearchString);
		
		JLabel lblSearchString = new JLabel("Search string");
		lblSearchString.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSearchString.setBounds(90, 136, 172, 32);
		panel_1.add(lblSearchString);
		
		 rdbtnName = new JRadioButton("Name");
		buttonGroup.add(rdbtnName);
		rdbtnName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnName.setBounds(6, 277, 109, 23);
		panel_1.add(rdbtnName);
		
		JLabel lblWhatDoYou = new JLabel("What do you want to search for?");
		lblWhatDoYou.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWhatDoYou.setBounds(16, 218, 229, 25);
		panel_1.add(lblWhatDoYou);
		
		 rdbtnLocation = new JRadioButton("Location");
		buttonGroup.add(rdbtnLocation);
		rdbtnLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLocation.setBounds(6, 303, 109, 23);
		panel_1.add(rdbtnLocation);
		
		 rdbtnHotelChain = new JRadioButton("Hotel chain");
		buttonGroup.add(rdbtnHotelChain);
		rdbtnHotelChain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnHotelChain.setBounds(6, 329, 109, 23);
		panel_1.add(rdbtnHotelChain);
		
		 rdbtnSubstring = new JRadioButton("Substring");
		buttonGroup.add(rdbtnSubstring);
		rdbtnSubstring.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnSubstring.setBounds(6, 355, 109, 23);
		panel_1.add(rdbtnSubstring);
		
		 rdbtnAllHotels = new JRadioButton("All hotels");
		 rdbtnAllHotels.setSelected(true);
		buttonGroup.add(rdbtnAllHotels);
		rdbtnAllHotels.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnAllHotels.setBounds(136, 277, 109, 23);
		panel_1.add(rdbtnAllHotels);
		
		txtCheckIn = new JTextField();


		txtCheckIn.setText(" ");
		txtCheckIn.setBounds(16, 85, 86, 20);
		panel_1.add(txtCheckIn);
		txtCheckIn.setColumns(10);
		
		txtCheckOut = new JTextField();

		txtCheckOut.setColumns(10);
		txtCheckOut.setBounds(171, 85, 86, 20);
		panel_1.add(txtCheckOut);
		
		JLabel lblCheckindate = new JLabel("CheckInDate");
		lblCheckindate.setBounds(16, 60, 73, 14);
		panel_1.add(lblCheckindate);
		
		JLabel lblCheckoutdate = new JLabel("CheckOutDate");
		lblCheckoutdate.setBounds(171, 60, 90, 14);
		panel_1.add(lblCheckoutdate);
		
		lblDagsetningar = new JLabel("Dates   (YYYY-MM-DD)");
		lblDagsetningar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDagsetningar.setBounds(52, 11, 178, 32);
		panel_1.add(lblDagsetningar);
		
		JButton btn = new JButton("Search Hotels");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchButtonClick();
			}
		});
		btn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn.setBounds(10, 726, 266, 60);
		frame.getContentPane().add(btn);
		
		btnBookSelectedRoom = new JButton("Book Selected Room");
		btnBookSelectedRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hotelfinder.book( hotels.get( hotelList.getSelectedIndex() ), selectedRoomID);
				hotelRoomList.remove( hotelRoomList.getSelectedIndex() );
				hotelRoomListModel.remove( hotelRoomList.getSelectedIndex());
			}
		});
		btnBookSelectedRoom.setEnabled(false);
		btnBookSelectedRoom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBookSelectedRoom.setBounds(10, 797, 266, 60);
		frame.getContentPane().add(btnBookSelectedRoom);
	}
	
	
	private void searchButtonClick(){
		hotelList.clearSelection();
		hotelRoomList.clearSelection();
		hotelListModel.clear();
		hotelRoomListModel.clear();
		
		int[] facilities = updateFacilityArray();
		String checkInDate = txtCheckIn.getText();
		String checkOutDate = txtCheckOut.getText();
		updateFacilityArray();
		int whatToCall = whatToCall();
		hotelfinder = new HotelFinder(checkInDate, checkOutDate);
		String searchString = textPaneSearchString.getText();
		
		
		switch (whatToCall) {
		case 0:
			//Leitum í öllum hótelum.
			hotels = hotelfinder.getFreeRoomsFromAnyHotel();
			break;
		case 1:
			//Leitum að hótelkeðju
			if(searchString != "") hotels = hotelfinder.getFreeRoomsFromHotelChain(searchString);
			
			break;
		case 2:
			if(searchString != "") hotels = hotelfinder.getFreeRoomsFromHotelLocation(searchString);
			//Leitum eftir hótel staðsetningu
			break;
		case 3:
			if(searchString != "") hotels = hotelfinder.getFreeRoomsFromHotel(searchString);
			//Leitum eftir hótel nafni
			break;
		case 4:
			if(searchString != "") hotels = hotelfinder.getFreeRoomsFromAnyHotelSubString(searchString);
			//Leitum eftir substring í hótel nafni.
			break;
		}
		
		if( facilities[0] != 0){
			//Einhver facilities eru valin,  filterum hótelin út sem eru ekki með þau.
			hotels = hotelfinder.filterHotelWithFacilities(hotels, facilities);
		}
		
		
		insertIntoHotelList();
		
	}
	
	private void filterByFacility(){
		
		hotelRoomListModel.clear();
		hotelListModel.clear();
		
		int[] facilities = updateFacilityArray();
		for (int i = 0; i < facilities.length; i++) {
			System.out.println(facilities[i]);
		}
		
		if(hotels.size() > 0 && facilities[0] != 0){					
			hotels = hotelfinder.filterHotelWithFacilities(hotels, facilities);
			insertIntoHotelList();				
		}
	}
	
	
	private void insertIntoHotelList(){
		
		String element = "", hotelName = "", hotelLoc = "", hotelChain = "";
		for (int i = 0; i < hotels.size(); i++) {
			
			hotelName = hotels.get(i).getName();
			hotelLoc = hotels.get(i).getCity();
			hotelChain = hotels.get(i).getChain();
			element = "Name: " + hotelName + ".   Chain: " + hotelChain + ".   City: " + hotelLoc;
			hotelListModel.insertElementAt(element, i);
		}
	}
	
	private void insertIntoHotelRoomList( int hotelNumber ){
		hotelRoomListModel.clear();
		String roomID = "", numPers = "", rate = "", element ="";
		for (int i = 0; i < hotels.get(hotelNumber).getHotelRooms().size(); i++) {
			roomID = ""+hotels.get(hotelNumber).getHotelRooms().get(i).getId();
			numPers = ""+hotels.get(hotelNumber).getHotelRooms().get(i).getNumPerson();
			rate = ""+hotels.get(hotelNumber).getHotelRooms().get(i).getRate();
			element = "Room number: " + roomID + ".   space for  " + numPers + " persons.  Costs "+rate+"kr per night.";
			
			hotelRoomListModel.insertElementAt(element, i);
		}
		
	}
	
	//Finnur út hvaða facilities eru valin og býr til array með facilityID's.  
	private int[] updateFacilityArray(){
		int index = 0;
		int[] facilities = new int[13];
		
		
		if( ckboxRoomService.isSelected() ) facilities[index++] =  1;
		if( chckbxBreakfast.isSelected() ) facilities[index++] =  2;
		if( chckbxRestaurant.isSelected() ) facilities[index++] =  3;
		if( chckbxPool.isSelected() ) facilities[index++] =  4;
		if( chckbxLuggageStorage.isSelected() ) facilities[index++] =  5;
		if( chckbxGarage.isSelected() ) facilities[index++] =  6;
		if( chckbxBar.isSelected() ) facilities[index++] =  7;
		if( chckbxGym.isSelected() ) facilities[index++] =  8;
		if( chckbxSpecialEvents.isSelected() ) facilities[index++] =  9;
		if( chckbxWifi.isSelected() ) facilities[index++] =  10;
		if( chckbxHandicapAccess.isSelected() ) facilities[index++] =  11;
		if( chckbxComputers.isSelected() ) facilities[index++] =  12;
		if( chckbxSpa.isSelected() ) facilities[index++] =  13;	
		
		return facilities;
	}
	private int whatToCall(){
		if( rdbtnAllHotels.isSelected() ) return 0;
		if( rdbtnHotelChain.isSelected() ) return 1;
		if( rdbtnLocation.isSelected() ) return 2;
		if( rdbtnName.isSelected() ) return 3;
		if( rdbtnSubstring.isSelected() ) return 4;
		
		//Ef ekkert er valið þá sjáum við öll hótel.
		return 0;
	}
}
