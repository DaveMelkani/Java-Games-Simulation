import java.awt.Color;


import java.awt.Graphics;
import java.awt.Rectangle;

public class Person {
	private int x, y;
	private int status;
	private int vx, vy;
	private boolean socialDistancing;
	static int infected = 0;
	private int time = 0;
	private int recoveryTime;

	public Person() {
		recoveryTime = (int) (Math.random() * (10000 - 7000 + 1) + 7000);
		socialDistancing = true;
		this.x = (int) (Math.random() * 790);
		this.y = (int) (Math.random() * 590);
		// 0 normal, 1 infected, 2 recovered
		if (Math.random() < 0.08) {
			status = 1;
		} else {
			status = 0;
		}
		if (socialDistancing && (Math.random() < 0.3)) {
			vx = (int) (Math.random() * (10 + 1) + -5);
			vy = (int) (Math.random() * (10 + 1) + -5);
		}else if(!socialDistancing) {
			vx = (int) (Math.random() * (10 + 1) + -5);
			vy = (int) (Math.random() * (10 + 1) + -5);
		}
		else {
			vx = 0;
			vy = 0;
		}
	}

	public void paint(Graphics g) {
		switch (status) {
		case 0:
			g.setColor(Color.LIGHT_GRAY);
			break;
		case 1:
			g.setColor(Color.red);
			break;
		case 2:
			g.setColor(Color.PINK);
		}
		g.fillOval(x, y, 10, 10);

		x += vx;
		y += vy;

		if (x <= 0 || x >= 790) {
			vx *= -1;
		}

		if (y <= 0 || y >= 590) {
			vy *= -1;
		}
		time += 16;
		if (time > recoveryTime && status ==1) {
			status = 2;
			infected--;
		}
	}

	public void collide(Person p) {
		Rectangle p1 = new Rectangle(x, y, 10, 10);
		Rectangle p2 = new Rectangle(p.x, p.y, 10, 10);
		if (p1.intersects(p2)) {
			// collision detection
			if (this.status == 1 && p.status == 0) {
				p.status = 1;
				infected++;
			} else if (p.status == 1 && this.status == 0) {
				this.status = 1;
				infected++;
			}

		}
	}

}
