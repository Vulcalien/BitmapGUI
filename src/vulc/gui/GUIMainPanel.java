package vulc.gui;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import vulc.gui.input.GuiInputHandler;
import vulc.gui.input.GuiInputHandler.Key;
import vulc.gui.input.GuiInputHandler.KeyType;

public class GUIMainPanel extends GUIPanel {

	public final GuiInputHandler input = new GuiInputHandler();

	// screen's properties
	public int xOffset, yOffset; 				// where it gets rendered (relative to component's (0; 0) corner)
	public int scrWidth, scrHeight;				// width and height 
	public int scrScaledWidth, scrScaledHeight;	// scaled width and height, when they are rendered

	private final List<Character> keyBuffer = new ArrayList<Character>();
	private final KeyAdapter keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			keyBuffer.add(e.getKeyChar());
		}
	};

	private final Key mouse1 = input.new Key(KeyType.MOUSE, MouseEvent.BUTTON1);

	private int wheelRotCount = 0;
	private final MouseWheelListener wheelListener = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			wheelRotCount += e.getWheelRotation();
		}
	};

	public GUIMainPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void init(Component component,
	                 int xOffset, int yOffset,
	                 int scrWidth, int scrHeight,
	                 int scrScaledWidth, int scrScaledHeight) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;

		this.scrWidth = scrWidth;
		this.scrHeight = scrHeight;

		this.scrScaledWidth = scrScaledWidth;
		this.scrScaledHeight = scrScaledHeight;

		input.init(component);

		component.addKeyListener(keyListener);
		component.addMouseWheelListener(wheelListener);
	}

	public void init(Component component,
	                 int scrWidth, int scrHeight) {
		init(component,
		     0, 0,
		     scrWidth, scrHeight,
		     scrWidth, scrHeight);
	}

	public void tick() {
		super.tick();

		input.tick();

		int xMouse = (input.xMouse - xOffset) * scrWidth / scrScaledWidth - this.x;
		int yMouse = (input.yMouse - yOffset) * scrHeight / scrScaledHeight - this.y;

		while(keyBuffer.size() != 0) {
			char c = keyBuffer.remove(0);
			this.onKeyPress(c);
		}

		if(mouse1.isKeyDown()) {
			if(this.isPointInside(xMouse, yMouse)) {
				this.onMouseDown(xMouse, yMouse);
			}
		}

		if(mouse1.isPressed()) {
			if(this.isPointInside(xMouse, yMouse)) {
				this.onMousePress(xMouse, yMouse);
			}
		}
		if(mouse1.isReleased()) {
			if(this.isPointInside(xMouse, yMouse)) {
				this.onMouseRelease(xMouse, yMouse);
			}
		}

		// do this even if isPointInside is false
		this.onMouseInside(xMouse, yMouse);

		if(wheelRotCount != 0) {
			if(this.isPointInside(xMouse, yMouse)) {
				this.onMouseScroll(xMouse, yMouse, wheelRotCount);
			}
			wheelRotCount = 0;
		}
	}

	public void render() {
		super.render(null);
	}

//	public void removeInputListeners() {
//		Console console = Console.instance;
//
//		input.remove();
//		console.removeKeyListener(keyListener);
//		console.removeMouseWheelListener(wheelListener);
//	}

}
