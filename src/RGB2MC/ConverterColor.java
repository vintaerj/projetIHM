package RGB2MC;

import javafx.scene.paint.Color;

public class ConverterColor {
	public static String color2rgb(Color color) {
		return "rgb("
				+ (int) (color.getRed()*255)+","
				+ (int) (color.getGreen()*255)+","
				+ (int) (color.getBlue()*255)
				+")";
	}

	public static String color2Hex(Color color){
		return  String.format( "#%02X%02X%02X",
				(int)( color.getRed() * 255 ),
				(int)( color.getGreen() * 255 ),
				(int)( color.getBlue() * 255 ) );
	}

	public static String color2tsl(Color color){
		return "("
				+ (color.getHue()) + "°, "
				+ color.getSaturation()*100 + "%, "
				+ color.getBrightness()*100 + "%"
				+ ")";
	}
	public static Color rgb2gray(Color value) {
		return rgb2gray(value.getRed()*255, value.getGreen()*255, value.getBlue()*255);
	}

	public static Color rgb2gray(Double r, Double g, Double b) {
		//NiveauGris = 0.3   Rouge + 0.59   Vert + 0.11   Bleu
		return Color.grayRgb((int)(0.3*r + 0.59*g + 0.11*b));
	}
}
