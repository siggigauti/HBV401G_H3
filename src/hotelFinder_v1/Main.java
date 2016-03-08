
public class Main {
	
	private static int counter;
	private static int counterValue;
	public static void main(String[] args){
		
		/*Hotel Fjorukrain = new Hotel(1, "Fjorukrain", "Hafnarfjordur");
		System.out.println(Fjorukrain.toString());
		
		
		Hotel Vik = new Hotel(2, "Vik", "Reykjavik");
		System.out.println(Vik.toString());
		*/
		
		//buum til array med objects.
		//Fyrir hotels sem inniheldur 0ll okkar hotel.
		//Fyrir Fundin hotel i leitarvelinni okkar.
		Hotel[] hotels = new Hotel[4];
		Hotel[] SearchResult = new Hotel[4];
		
		
		//fyllum sidan inni arrayin.
		hotels[0] = new Hotel(1, "Fjorukrain", "Hafnarfjordur");
		hotels[1] = new Hotel(2, "Vik", "Reykjavik");
		hotels[2] = new Hotel(3, "Akur", "Akureyri");
		hotels[3] = new Hotel(4, "Reykur", "Reykjavik");
		
		
		
		//prentar ut objectin
		/*for(int i = 0; i<hotels.length; i++)
		{
			System.out.println(hotels[i].toString());
		}
		*/
		
		//LeitarGLUGGINN
		String search = "adaw";
		
		//test til a[ tjekka hvort getname virkar 
		//System.out.println("hotel[0].getName: " + hotels[2].getCity());
		boolean found = false;
		for(int i = 0; i<hotels.length; i++)
		{
			if(hotels[i].getName() != null && hotels[i].getName().matches(search) ||
			   hotels[i].getCity() != null && hotels[i].getCity().matches(search))
			{
				counterValue++;
				SearchResult[counter++] = hotels[i];
				found = true;
			}
		}

		
		
		
		
		
		//Tjekk ef leitinn hafi heppnast eda ekki.
		if(found == true)
		{
			//Nidurstadan hvad vid leitudum eftir og hvad vid fundum.
			System.out.println("Searched for: " + search);
			
			//Vid fundum hvad margar nidurstodur.
			System.out.println("We found: " + counter + " items");
			
			
			for(int b = 0; b < SearchResult.length; b++)
			{
				System.out.println(SearchResult[b]);
			}
		}
		//Ef vid finnum ekki neitt.
		else
		{
			System.out.println("Didnt find anything that matches: " + search);
		}
		
		
		
		
		
	}
}
