package com.brovenge.zero.entity;

import java.util.Random;

import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.level.Level;

public abstract class Entity {

	public int x, y;
	public boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	public void update() {
	}

	public void render(Render render) {
	}

	public void remove() {
		removed = true;
	}

	public void init(Level level) {
		this.level = level;
	}
}
