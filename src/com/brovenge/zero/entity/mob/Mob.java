package com.brovenge.zero.entity.mob;

import com.brovenge.zero.entity.Entity;
import com.brovenge.zero.entity.Projectile;
import com.brovenge.zero.entity.TestProjectile;
import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = 0;
		if (xa < 0) dir = 1;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 3;

		for (int x = 0; x < Math.abs(xa); x++) {
			if (!collision(abs(xa), ya)) {
				this.x += abs(xa);
			}
		}

		for (int y = 0; y < Math.abs(ya); y++) {
			if (!collision(xa, abs(ya))) {
				this.y += abs(ya);
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}

	protected void shoot(int x, int y, double dir) {
		Projectile p = new TestProjectile(x, y, dir);
		level.add(p);
	}

	public abstract void update();

	public abstract void render(Render render);

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
}
