package vulc.gui;

import vulc.bitmap.Bitmap;
import vulc.bitmap.font.Font;

public class GUILabel extends GUIComponent {

	public String text = "";
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
		font.write(screen, text, textColor, x + 1, y + (h - font.getHeight()) / 2);

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
