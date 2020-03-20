package othergames;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Driver extends JPanel implements ActionListener, KeyListener {
	int table_width = 900; // width of the screen "table"
	int table_height = 600;// height of the screen "table"
	// more var for ball
	int radius = 35;
	int g = 10;
	// var for ball
	int b_x = 0;
	int b_y = 0;
	// velo var for ball
	int b_vx = 6;
	int b_vy = 6;

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
//	int

	public void paint(Graphics g) {

		super.paintComponent(g);

		// painting background
		// setColor for changing color before a paint method
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, table_width, table_height);

		// color and font of the score
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Harrington", 0, 40));
		g.drawString(Integer.toString(Lscore), 25, 25);
		g.drawString(Integer.toString(Rscore), table_width - 55, 25);
		g.drawString(b_x + "", 400, 400);

		// paddle color
		// paddle location
		g.setColor(Color.MAGENTA);
		g.fillRect(p1x, p1y, pw, ph);
		g.fillRect(p2x, p2y, pw, ph);

		// draw ball based on prob var
		// ball color
		g.setColor(Color.RED);
		g.fillOval(b_x, b_y, radius, radius);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		b_x += b_vx;
		b_y += b_vy;

		// helps with the alignment of the ball
		if (b_y >= table_height - radius) {
			b_vy = -b_vy;
		}

		if (b_y <= 0) {
			b_vy = -b_vy;
		}

		repaint();
		// boundaries
		
		
		/*
		 if (p1y < table_height - table_height) {
			 p1v = -p1v; 
			 }
		 if (p1y > table_height - 150) {
			 p1v = -p1v; 
			 } 
		 if (p2y < table_height - table_height) {
			 p2v = -p2v;
			 }
		 if (p2y > table_height -150) {
			 p2v = -p2v;
			 }
		 */
		 if(p1y < 0) {
			 p1y = 0;
		 }
		 else if(p1y >= 600) {
			 p1y = 600;
		 }
		 if(p2y < 0) {
			 p2y = 0;
		 }
		 if(p2y > 600) {
			 p2y = 600;
		 }
		
		// paddle boundaries for collision
		if (b_x <= p1x + pw && b_x >= p1x && b_y <= p1y + ph && b_y >= p1y) {
			b_vx = -b_vx;
		}
		if (b_x <= p2x + pw && b_x + radius >= p2x && b_y <= p2y + ph && b_y >= p2y) {
			b_vx = -b_vx;
		}
		if (b_x < -radius) {
			b_vx = -b_vx;
			b_vy = -b_vy;
			b_x = table_width / 2;
			b_y = table_height / 2;
			Rscore++;
		}
		if (b_x > table_width) {
			b_vx = -b_vx;
			b_vy = -b_vy;
			b_x = table_width / 2;
			b_y = table_height / 2;
			Lscore++;
		}
		p1y += p1v;
		p2y += p2v;

		/*
		 * range1 (min,max) range2 (min, max)
		 */

	}

	public static void main(String[] arg) {
		Driver d = new Driver();
	}

	public Driver() {

		JFrame f = new JFrame();
		f.setTitle("Pong");
		f.setSize(table_width, table_height);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);

		f.add(this);
		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyCode());
		// creates a nice smooth movement of the paddles
		if (e.getKeyCode() == 87) { // 87 is w
			p1v = -7;
		} else if (e.getKeyCode() == 83) { // 83 is s
			p1v = 7;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p2v = -7;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			p2v = 7;
		}
		/*
		 * if (e.getKeyCode() == KeyEvent.VK_UP) { p2y -= 25; } if (e.getKeyCode() ==
		 * KeyEvent.VK_DOWN) { p2y += 25; } if (e.getKeyCode() == 87) { p1y -= 25; } if
		 * (e.getKeyCode() == 83) { p1y += 25; }
		 */
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// it is what happens when the key I click is realeased
		if (e.getKeyCode() == 87 || e.getKeyCode() == 83) {
			p1v = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			p2v = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// press then release

	}
}
