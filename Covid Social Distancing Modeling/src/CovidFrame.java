import java.awt.Color;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CovidFrame extends JPanel implements ActionListener {

	private ArrayList<Person> people = new ArrayList<Person>();
	private int numPeople = 100;
	private ArrayList<Line> lines = new ArrayList<Line>();
	private int time = 0;

	public static void main(String[] arg) {
		CovidFrame sim = new CovidFrame();
	}

	public void paint(Graphics g) {
		// g.fillOval(0, 0, 100, 100);
		// System.out.println("hi");
		// have the person objects draw themselves
		super.paintComponent(g);
		for (Person p : people) {
			p.paint(g);
		}
		for(Line l : lines) {
			l.paint(g);
		}
		g.setColor(Color.black);
		g.drawString("Num Infected: " + Person.infected, 100, 100);

	}

	public void update() {
		time += 20;
		for (int i = 0; i < people.size(); i++) {
			for (int j = i + 1; j < people.size(); j++) {
				people.get(i).collide(people.get(j));
			}
		}
		if(time%100 == 0) {
			lines.add(new Line(time/100, 200, time/100, 200-Person.infected));
		}
	}

	public CovidFrame() {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		Timer t = new Timer(16, this);
		t.start();
		// add int numPeople amount into ArrayList of People objects
		for (int i = 0; i < numPeople; i++) {
			people.add(new Person());
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		update();
	}

	// @Override
}
