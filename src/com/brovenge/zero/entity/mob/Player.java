package com.brovenge.zero.entity.mob;

import com.brovenge.zero.Input;
import com.brovenge.zero.Main;
import com.brovenge.zero.entity.Projectile;
import com.brovenge.zero.entity.TestProjectile;
import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.graphics.Sprite;
import com.brovenge.zero.level.Level;

public class Player extends Mob {

	private Input input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int speed = 1;

	private int fireRate;

	public Player(Input input) {
		this.input = input;
		init(Level.main);
		sprite = Sprite.player;
	}

	public Player(int x, int y, Input input) {
		this.x = x;
		this.y = y;
		this.input = input;
		init(Level.main);
		sprite = Sprite.player;
	}

	public void update() {
		int xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;
		if (input.up) ya -= speed;
		if (input.down) ya += speed;
		if (input.left) xa -= speed;
		if (input.right) xa += speed;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

		updateShooting();
		clearProjectiles();
	}

	private void updateShooting() {
		if (Input.mouseB == 1) {
			double dx = Input.mouseX - (Main.width * Main.scale) / 2;
			double dy = Input.mouseY - (Main.height * Main.scale) / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = TestProjectile.fireRate;
		}
	}

	private void clearProjectiles() {
		for (Projectile p : level.projectiles)
			if (p.removed) p.remove();
	}

	public void render(Render render) {
		if (dir == 0) {
			sprite = Sprite.player;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player;
				} else {
					sprite = Sprite.player;
				}
			}
		}

		if (dir == 1) {
			sprite = Sprite.player;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player;
				} else {
					sprite = Sprite.player;
				}
			}
		}

		if (dir == 2) {
			sprite = Sprite.player;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player;
				} else {
					sprite = Sprite.player;
				}
			}
		}

		if (dir == 3) {
			sprite = Sprite.player;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player;
				} else {
					sprite = Sprite.player;
				}
			}
		}

		render.renderSprite(x - 16, y - 16, sprite, true);
	}

}
