package othergames;
import java.awt.Rectangle;

public class Paddles {
	int table_width = 900; // width of the screen "table"
	int table_height = 600;// height of the screen "table"
	// var for left paddle
	int p1x = 0;
	int p1y = 0;
	// var for right paddle
	int p2x = table_width - 28;
	int p2y = 0;
	// var for paddle height and width
	int pw = 20;
	int ph = 140;
	// velo var for pad 1 & 2
	int p1v = 0;
	int p2v = 0;

	// var for the left score and right score
	int Lscore = 0;
	int Rscore = 0;
	
	Rectangle paddle = new Rectangle (p1x, p1y, 10, 50);
}
