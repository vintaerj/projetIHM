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
		Double r = color.getRed();
		Double g = color.getGreen();
		Double b = color.getBlue();

		Double cmax = Math.max(Math.max(r,g),b);
		Double cmin = Math.min(Math.min(r,g),b);

		Double delta = cmax - cmin;

		Double t = 0.0;
		Double s = 0.0;

		Double l = (cmax + cmin)/2;

		if(delta == 0){
			s = 0.0;
			t = 0.0;
		}
		else{
			s = delta/(1-Math.abs(2*l-1));
		}

		if(cmax == r){
			t = 60*(((g-b)/delta)%6);
		}else if(cmax == g){
			t = 60*(((b-r)/delta)+2);
		}else if(cmax == b){
			t = 60*(((r-g)/delta)+4);
		}

		return "("
				+ t.intValue() + "°, "
				+ (s*100) + "%, "
				+ (l*100) + "%"
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
