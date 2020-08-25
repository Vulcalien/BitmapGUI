package vulc.gui;

import vulc.bitmap.font.Font;

public abstract class GUIResources {

	public static Font defaultFont;

	public static void init(Font font) {
		defaultFont = font;
	}

	public static void init() {
		init(new Font(GUIPanel.class.getResourceAsStream("/vulc/gui/fonts/tinyfont.fv4")));
	}

}
