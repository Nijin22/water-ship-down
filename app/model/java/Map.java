package model.java;

public class Map {
	private final int SIZE = 18; //Set in team meeting 2014-04-27 Probably better not to change...
	private boolean host; //True if host, false if guest
	private String name;
	private Match match;
	
	private MapField[][] map = new MapField[SIZE][SIZE];
	public Map(boolean host, String name, Match match){
		this.host = host;
		this.name = name;
		this.match = match;
		int counter_y = 0;
		int counter_x = 0;
		while (counter_y < SIZE){
			while (counter_x < SIZE){
				map[counter_y][counter_x] = new MapField(counter_y, counter_x, null);
				counter_x++;
			}
			counter_y++;
			counter_x = 0;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Match getMatch() {
		return match;
	}
	

	
}
