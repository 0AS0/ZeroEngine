package com.brovenge.zero.level.tile;

import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public boolean solid() {
		return true;
	}

	public void render(int x, int y, Render render) {
		render.renderTile(x << 4, y << 4, this);
	}

}
