package othergames2;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;

	private final Point[][][] Piece = {
//			 I-Piece
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) } },

//			 J-Piece
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) } },

//			 L-Piece
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) } },

//			 O-Piece
			{ { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) } },

//			 S-Piece
			{ { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) } },

//			 T-Piece
			{ { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) } },

//			 Z-Piece
			{ { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) } } };

	private final Color[] tetraminoColors = { Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
			Color.pink, Color.red };

	private Point StartPiece;
	private int currentPiece;
	private int spin;
	private ArrayList<Integer> newPieces = new ArrayList<Integer>();

	private long win;
	private Color[][] holes;

//	 Creates a border around the well and initializes the dropping piece
	private void init() {
		holes = new Color[12][24];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					holes[i][j] = Color.GRAY;
				} else {
					holes[i][j] = Color.BLACK;
				}
			}
		}
		newPiece();
	}

//	 Put a new, random piece into the dropping position
	public void newPiece() {
		StartPiece = new Point(5, 2);
		spin = 0;
		if (newPieces.isEmpty()) {
			Collections.addAll(newPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(newPieces);
		}
		currentPiece = newPieces.get(0);
		newPieces.remove(0);
	}

//	 Collision test for the dropping piece
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Piece[currentPiece][rotation]) {
			if (holes[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

//	 Rotate the piece clockwise or counterclockwise
	public void rotate(int i) {
		int newRotation = (spin + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(StartPiece.x, StartPiece.y, newRotation)) {
			spin = newRotation;
		}
		repaint();
	}

//	 Move the piece left or right
	public void move(int i) {
		if (!collidesAt(StartPiece.x + i, StartPiece.y, spin)) {
			StartPiece.x += i;
		}
		repaint();
	}

//	 Drops the piece one line or fixes it to the well if it can't drop
	public void dropDown() {
		if (!collidesAt(StartPiece.x, StartPiece.y + 1, spin)) {
			StartPiece.y += 1;
		} else {
			fixToWell();
		}
		repaint();
	}

//	 Make the dropping piece part of the well, so it is available for
//	 collision detection.
	public void fixToWell() {
		for (Point p : Piece[currentPiece][spin]) {
			holes[StartPiece.x + p.x][StartPiece.y + p.y] = tetraminoColors[currentPiece];
		}
		clearRows();
		newPiece();
	}

	public void deleteRow(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				holes[i][j + 1] = holes[i][j];
			}
		}
	}

//	 Clear completed rows from the field and award score according to
//	 the number of simultaneously cleared rows.
	public void clearRows() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (holes[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}

		switch (numClears) {
		case 1:
			win += 100;
			break;
		case 2:
			win += 300;
			break;
		case 3:
			win += 500;
			break;
		case 4:
			win += 800;
			break;
		}
	}

//	 Draw the falling piece
	private void drawPiece(Graphics g) {
		g.setColor(tetraminoColors[currentPiece]);
		for (Point p : Piece[currentPiece][spin]) {
			g.fillRect((p.x + StartPiece.x) * 26, (p.y + StartPiece.y) * 26, 25, 25);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
//		 Paint the well
		g.fillRect(0, 0, 26 * 12, 26 * 23);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(holes[i][j]);
				g.fillRect(26 * i, 26 * j, 25, 25);
			}
		}

//		 Display the score
		g.setColor(Color.WHITE);
		g.drawString("" + win, 19 * 12, 25);

//		 Draw the currently falling piece
		drawPiece(g);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(12 * 26 + 10, 26 * 23 + 25);
		f.setVisible(true);

		final Tetris game = new Tetris();
		game.init();
		f.add(game);

//		 Keyboard controls
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				case KeyEvent.VK_DOWN:
					game.rotate(+1);
					break;
				case KeyEvent.VK_LEFT:
					game.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					game.move(+1);
					break;
				case KeyEvent.VK_SPACE:
					game.dropDown();
					game.win += 1;
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});

//		 Make the falling piece drop every second
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						game.dropDown();
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}
}