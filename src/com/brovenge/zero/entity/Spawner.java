package com.brovenge.zero.entity;

import com.brovenge.zero.level.Level;

public class Spawner extends Entity {

	public enum Type {
		MOB, PARTICLE
	}

	protected Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}

}
