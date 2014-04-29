package model.java;

public class Map {
	private final int SIZE = 18; //Set in team meeting 2014-04-27 Probably better not to change...
	private MapField[][] map = new MapField[SIZE][SIZE];
	public Map(){
		int counter_y = 0;
		int counter_x = 0;
		while (counter_y <= SIZE){
			while (counter_x <= SIZE){
				map[counter_y][counter_x] = new MapField(counter_y, counter_x, null);
				counter_x++;
			}
			counter_y++;
			counter_x = 0;
		}
	}

	
}
