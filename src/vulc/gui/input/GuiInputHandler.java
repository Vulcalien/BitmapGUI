package vulc.gui.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class GuiInputHandler implements KeyListener,
                             MouseListener,
                             MouseMotionListener,
                             MouseWheelListener {

	public final Object keyLock = new Object();
	public final Object mouseLock = new Object();
	public final Object mouseMotionLock = new Object();
	public final Object mouseWheelLock = new Object();

	public final List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();

	public final List<MouseEvent> mousePress = new ArrayList<MouseEvent>();
	public final List<MouseEvent> mouseRelease = new ArrayList<MouseEvent>();

	public int xMouse = -1, yMouse = -1;
	public int wheelRotCount = 0;

	public void init(Component component) {
		component.addKeyListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		synchronized(keyLock) {
			keyEvents.add(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		synchronized(keyLock) {
			keyEvents.add(e);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		synchronized(mouseLock) {
			mousePress.add(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		synchronized(mouseLock) {
			mouseRelease.add(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		synchronized(mouseMotionLock) {
			xMouse = e.getX();
			yMouse = e.getY();
		}
	}

	public void mouseMoved(MouseEvent e) {
		synchronized(mouseMotionLock) {
			xMouse = e.getX();
			yMouse = e.getY();
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		synchronized(mouseWheelLock) {
			wheelRotCount += e.getWheelRotation();
		}
	}

}
