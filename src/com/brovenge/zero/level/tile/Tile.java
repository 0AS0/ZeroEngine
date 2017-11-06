package com.brovenge.zero.level.tile;

import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.graphics.Sprite;

public abstract class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	public static Tile test = new TestTile(Sprite.test);

	public static final int col_test = 0xffffffff;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public abstract boolean solid();

	public abstract void render(int x, int y, Render render);

}
