package RGB2MC;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExportController {
	private Stage exportStage;

	@FXML
	private AnchorPane zoneTableau;

	@FXML
	private TableView<MyColorData> colorTable;

	@FXML
	private TableColumn<MyColorData, String> nameCOlor;

	@FXML
	private TableColumn<MyColorData, String> webColor;

	@FXML
	private TableColumn<MyColorData, String> rgbColor;

	@FXML
	private TableColumn<MyColorData, String> tslColor;
	private ObservableList<MyColorData> colorData;
	private ObservableList<Color> saveColors;


	public ExportController() {
	}

	@FXML
	private void initialize() {
		nameCOlor.setCellValueFactory(new PropertyValueFactory<>("Couleur"));
		webColor.setCellValueFactory(new PropertyValueFactory<>("WEB"));
		rgbColor.setCellValueFactory(new PropertyValueFactory<>("RGB"));
		tslColor.setCellValueFactory(new PropertyValueFactory<>("HSL/TSL"));
	}

	private ObservableList<MyColorData> getColorList() {
		ObservableList<MyColorData> listColors = FXCollections.observableArrayList();
		for(Color color: saveColors){
			MyColorData listColor = new MyColorData("      ", ConverterColor.color2Hex(color), ConverterColor.color2rgb(color), ConverterColor.color2tsl(color));
			listColors.add(listColor);
		}
		System.out.println(listColors);
		return listColors;
	}


	public void setDialogStage(Stage exportStage) {
		this.exportStage = exportStage;
	}

	public void setColorData(ObservableList<Color> saveColors) {
		this.saveColors = saveColors;
		colorData = getColorList();
		colorTable.setItems(colorData);
	}
}
