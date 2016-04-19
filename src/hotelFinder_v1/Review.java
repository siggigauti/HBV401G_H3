package hotelFinder_v1;

public class Review {
	private int hotelID, roomID, stars;
	private String title, content, reviewer, date;

	public Review(String title, String content, int stars, String reviewer, String date){
		
		this.title = title;
		this.content = content;
		this.stars = stars;
		this.reviewer = reviewer;
		this.date = date;
	}
	public int getStars(){
		return this.stars;
	}
	public int getHotelID(){
		return this.hotelID;
	}
	public int getID(){
		return this.roomID;
	}
	public String getContent(){
		return this.content;
	}
	public String getTitle(){
		return this.title;
	}
	public String getReviewer(){
		return this.reviewer;
	}
	public String getDate(){
		return this.date;
	}
	
}
