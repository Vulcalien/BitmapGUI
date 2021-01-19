/*
 * Copyright 2019-2021 Vulcalien
 * This code is licensed under MIT License by Vulcalien (see LICENSE)
 */
package vulc.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import vulc.gui.input.GuiInputHandler;

public class GUIMainPanel extends GUIPanel {

	public final GuiInputHandler input = new GuiInputHandler();
	public final List<Integer> mouseDownButtons = new ArrayList<Integer>();

	// scaled width and height, when they are rendered
	public int scrScaledWidth, scrScaledHeight;

	// these are used to draw directly to a Graphics object
	private BufferedImage img;
	private int[] imgPixels;

	public GUIMainPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void init(Component component, int scrScaledWidth, int scrScaledHeight) {
		this.scrScaledWidth = scrScaledWidth;
		this.scrScaledHeight = scrScaledHeight;

		input.init(component);
	}

	public void init(Component component) {
		init(component, this.w, this.h);
	}

	public void tick() {
		super.tick();

		synchronized(input.keyLock) {
			while(!input.keyEvents.isEmpty()) {
				KeyEvent key = input.keyEvents.remove(0);

				if(key.getID() == KeyEvent.KEY_PRESSED) {
					this.onKeyPress(key.getKeyChar());
				} else if(key.getID() == KeyEvent.KEY_RELEASED) {
					this.onKeyRelease(key.getKeyChar());
				}
			}
		}

		int xMouse, yMouse;
		synchronized(input.mouseMotionLock) {
			xMouse = (input.xMouse - this.x) * this.w / scrScaledWidth;
			yMouse = (input.yMouse - this.y) * this.h / scrScaledHeight;
		}

		synchronized(input.mouseLock) {
			while(!input.mousePress.isEmpty()) {
				MouseEvent e = input.mousePress.remove(0);

				int xm = (e.getX() - this.x) * this.w / scrScaledWidth;
				int ym = (e.getY() - this.y) * this.h / scrScaledHeight;

				this.onMousePress(xm, ym, e.getButton());

				if(!mouseDownButtons.contains(e.getButton())) {
					mouseDownButtons.add(e.getButton());
				}
			}

			for(int mouseButton : mouseDownButtons) {
				if(this.isPointInside(xMouse, yMouse)) {
					this.onMouseDown(xMouse, yMouse, mouseButton);
				}
			}

			while(!input.mouseRelease.isEmpty()) {
				MouseEvent e = input.mouseRelease.remove(0);

				int xm = (e.getX() - this.x) * this.w / scrScaledWidth;
				int ym = (e.getY() - this.x) * this.h / scrScaledHeight;

				this.onMouseRelease(xm, ym, e.getButton());

				if(mouseDownButtons.contains(e.getButton())) {
					mouseDownButtons.remove((Object) e.getButton());
				}
			}
		}

		// do this even if isPointInside is false
		this.onMouseInside(xMouse, yMouse);

		synchronized(input.mouseWheelLock) {
			if(input.wheelRotCount != 0) {
				this.onMouseScroll(xMouse, yMouse, input.wheelRotCount);

				input.wheelRotCount = 0;
			}
		}
	}

	public void render() {
		super.render(null);
	}

	public void render(Graphics g) {
		this.render();

		if(img == null || img.getWidth() != scrScaledWidth || img.getHeight() != scrScaledHeight) {
			img = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_RGB);
			imgPixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		}

		for(int i = 0; i < imgPixels.length; i++) {
			imgPixels[i] = this.screen.raster.getPixel(i);
		}

		g.drawImage(img, x, y, scrScaledWidth, scrScaledHeight, null);
	}

//	public void removeInputListeners() {
//		Console console = Console.instance;
//
//		input.remove();
//		console.removeKeyListener(keyListener);
//		console.removeMouseWheelListener(wheelListener);
//	}

}
