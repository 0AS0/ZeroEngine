package com.brovenge.zero.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.brovenge.zero.entity.Entity;
import com.brovenge.zero.entity.Particle;
import com.brovenge.zero.entity.Projectile;
import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.level.tile.Tile;

public class Level {

	public List<Entity> entities = new ArrayList<Entity>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();

	protected int width, height;
	protected int[] tiles;
	public static Level main = new MainLevel();

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
	}

	public void update() {
		for (Entity e : entities)
			e.update();

		for (Projectile p : projectiles)
			p.update();

		for (Particle p : particles)
			p.update();

		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++)
			if (entities.get(i).removed) entities.remove(i);

		for (int i = 0; i < projectiles.size(); i++)
			if (projectiles.get(i).removed) projectiles.remove(i);

		for (int i = 0; i < particles.size(); i++)
			if (particles.get(i).removed) particles.remove(i);
	}

	public void render(int xScroll, int yScroll, Render render) {
		render.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + render.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + render.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, render);
			}
		}

		for (Entity e : entities)
			e.render(render);

		for (Projectile p : projectiles)
			p.render(render);

		for (Particle p : particles)
			p.render(render);
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else {
			entities.add(e);
		}
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_test) return Tile.test;
		return Tile.voidTile;
	}

}
