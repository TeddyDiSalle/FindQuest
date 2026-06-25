package main;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

		int col = 0;
		int row = 0;
		while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {

			eventRect[col][row] = new EventRect();
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			row++;
			col++;
		}
	}

}
