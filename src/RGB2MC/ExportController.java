package RGB2MC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ExportController  {
	private ObservableList<MyColorData> myColorDatas;
	private Stage exportStage;

	@FXML
	private AnchorPane zoneTableau;


	private RGB2MCApp mainApp;


	@FXML
	private void initialize() {
	}


	public void setDialogStage(Stage exportStage) {
		this.exportStage = exportStage;
	}

	public void setColorData(ObservableList<MyColorData> myColorDatas) {
		this.myColorDatas = myColorDatas;

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(25);
		gridPane.setVgap(15);

		// firt row = header
		Label couleurId = new Label("Couleur ID"); couleurId.setStyle("");
		Label couleurWeb = new Label("WEB");  couleurWeb.setStyle("");
		Label couleurRgb = new Label("RGB");  couleurRgb.setStyle("");
		Label couleurHsl = new Label("HSL/TSL");  couleurHsl.setStyle("");

		// Fill row 0
		gridPane.add(couleurId, 0, 0);
		gridPane.add(couleurWeb, 1, 0);
		gridPane.add(couleurRgb, 2, 0);
		gridPane.add(couleurHsl, 3, 0);

		ObservableList<MyColorData> listColors = FXCollections.observableArrayList();
		int row = 1;
		for(MyColorData myColorData: myColorDatas){
			listColors.add(myColorData);
			Label id = new Label(myColorData.getNameColor());
			TextField web = new TextField(myColorData.getWebColor()); web.setEditable(false);
			TextField rgb = new TextField(myColorData.getRgbColor()); rgb.setEditable(false);
			TextField hsl = new TextField(myColorData.getTslColor()); hsl.setEditable(false);
			gridPane.add(id, 0, row);
			gridPane.add(web, 1, row);
			gridPane.add(rgb, 2, row);
			gridPane.add(hsl, 3, row);
			row++;
		}
		System.out.println(listColors);
		zoneTableau.getChildren().addAll(gridPane);
	}
}
