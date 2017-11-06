package com.brovenge.zero.level;

public class Coordinate {

	public int x, y;
	private final int TILE_SIZE = 16;

	public Coordinate(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}

	public int[] getXY() {
		int[] r = new int[2];
		r[0] = x;
		r[1] = y;
		return r;
	}

}
