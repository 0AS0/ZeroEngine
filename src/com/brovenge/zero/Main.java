package com.brovenge.zero;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.brovenge.zero.entity.mob.Player;
import com.brovenge.zero.graphics.Render;
import com.brovenge.zero.level.Coordinate;
import com.brovenge.zero.level.Level;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 426;
	public static int height = 240;
	public static int scale = 3;

	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Input input;
	private Level level;
	private Player player;
	private Coordinate spawn;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Render render;

	public Main() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		render = new Render(width, height);
		frame = new JFrame("Game");
		input = new Input();
		level = Level.main;
		spawn = new Coordinate(0, 0);
		player = new Player(spawn.x, spawn.y, input);

		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	private void init() {
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		double amountOfUpdates = 60.0;
		double ns = 1000000000 / amountOfUpdates;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	public void update() {
		input.update();
		level.update();
		player.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		render.clear();
		int xScroll = player.x - render.width / 2;
		int yScroll = player.y - render.height / 2;
		level.render(xScroll, yScroll, render);
		player.render(render);

		for (int i = 0; i < pixels.length; i++)
			pixels[i] = render.pixels[i];

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
