package RGB2MC;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExportController {
	private final String HR = "============================================\n";
	private final String BR = "\n\n";
	private Stage exportStage;

	@FXML
	private AnchorPane zoneText;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage exportStage) {
		this.exportStage = exportStage;
	}

	public void parseColor(ObservableList<Color> saveColors) {
		String hex, rgb;
		hex = "CSS    \n"+HR;
		rgb = "RGB    \n"+HR;

		for(Color color: saveColors){
			hex += color2Hex(color)+"\n";
			rgb += color2rgb(color)+"\n";
		}
		TextArea textArea = new TextArea();
		textArea.setText(hex+BR+rgb);
		textArea.setEditable(false);
		textArea.prefWidthProperty().bind(zoneText.widthProperty());
		textArea.prefHeightProperty().bind(zoneText.heightProperty());

		zoneText.getChildren().add(textArea);
	}

	private String color2rgb(Color color) {
		return "rgb("
				+ (int) (color.getRed()*255)+","
				+ (int) (color.getGreen()*255)+","
				+ (int) (color.getBlue()*255)
				+")";
	}

	private String color2Hex(Color color){
		return  String.format( "#%02X%02X%02X",
				(int)( color.getRed() * 255 ),
				(int)( color.getGreen() * 255 ),
				(int)( color.getBlue() * 255 ) );
	}

}
