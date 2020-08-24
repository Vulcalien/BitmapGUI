package vulc.gui;

import vulc.bitmap.font.Font;

public abstract class GUIResources {

	public static Font defaultFont;

	public static void init() {
		defaultFont = new Font(GUIPanel.class.getResourceAsStream("/vulc/gui/fonts/tinyfont.fv4"));
	}

}
