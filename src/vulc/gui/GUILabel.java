package vulc.gui;

import vulc.bitmap.Bitmap;
import vulc.bitmap.font.Font;

public class GUILabel extends GUIComponent {

	public static final int LEFT = 0, TOP = 0;
	public static final int CENTER = 1, MIDDLE = 1;
	public static final int RIGHT = 2, BOTTOM = 2;

	public String text = "";
	public int xAlign = LEFT, yAlign = MIDDLE;
	public int textColor = 0;

	public Font font;

	public Bitmap<Boolean> boolImage;
	public int colorAsBool;

	public Bitmap<Integer> image;

	public GUILabel(int x, int y, int w, int h) {
		super(x, y, w, h);

		font = GUIResources.defaultFont;
	}

	public void setImage(Bitmap<Boolean> image, int color) {
		this.boolImage = image;
		this.colorAsBool = color;

		this.image = null;
	}

	public void setImage(Bitmap<Integer> image) {
		this.image = image;

		this.boolImage = null;
	}

	public void render(Bitmap<Integer> screen) {
		super.render(screen);
		int xOffset = 0, yOffset = 0;

		if(xAlign == LEFT) xOffset = 1;
		else if(xAlign == CENTER) xOffset = (w - font.widthOf(text)) / 2;
		else if(xAlign == RIGHT) xOffset = w - font.widthOf(text) - 1;

		if(yAlign == TOP) yOffset = 1;
		else if(yAlign == MIDDLE) yOffset = (h - font.getHeight()) / 2;
		else if(yAlign == BOTTOM) yOffset = h - font.getHeight() - 1;

		font.write(screen, text, textColor, x + xOffset, y + yOffset);

		if(boolImage != null) {
			screen.drawBool(boolImage, colorAsBool,
			                x + (w - boolImage.width) / 2,
			                y + (h - boolImage.height) / 2);
		} else if(image != null) {
			screen.draw(image,
			            x + (w - image.width) / 2,
			            y + (h - image.height) / 2);
		}
	}

}
