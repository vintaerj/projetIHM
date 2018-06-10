package RGB2MC;

public class MyColorData {
	private String nameColor;
	private String webColor;
	private String rgbColor;
	private String tslColor;

	@Override
	public String toString() {
		return "MyColorData{" +
				"nameColor='" + nameColor + '\'' +
				", webColor='" + webColor + '\'' +
				", rgbColor='" + rgbColor + '\'' +
				", tslColor='" + tslColor + '\'' +
				"}\n";
	}

	public MyColorData(String nameColor, String webColor, String rgbColor, String tslColor) {
		this.nameColor = nameColor;
		this.webColor = webColor;
		this.rgbColor = rgbColor;
		this.tslColor = tslColor;
	}

	public String getNameColor() {
		return nameColor;
	}

	public void setNameColor(String nameColor) {
		this.nameColor = nameColor;
	}

	public String getWebColor() {
		return webColor;
	}

	public void setWebColor(String webColor) {
		this.webColor = webColor;
	}

	public String getRgbColor() {
		return rgbColor;
	}

	public void setRgbColor(String rgbColor) {
		this.rgbColor = rgbColor;
	}

	public String getTslColor() {
		return tslColor;
	}

	public void setTslColor(String tslColor) {
		this.tslColor = tslColor;
	}
}
