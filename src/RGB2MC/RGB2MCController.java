package RGB2MC;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.*;


public class RGB2MCController implements Initializable, ChangeListener, ListChangeListener {
	private static final int NB_COLORS_MAX = 10;
	private static final double DELTA = 0.0;

	private RGB2MCApp mainApp;

	private ObservableList<Rectangle> listOfColors = FXCollections.observableArrayList();
	private ObservableList<Rectangle> listOfGray = FXCollections.observableArrayList();
	private ObservableList<MyColorData> saveColors = FXCollections.observableArrayList();

	private ObservableList<Color> verification = FXCollections.observableArrayList();

	private Rectangle saveRec;
	private Button buttonUpdate;

	@FXML
	private Slider sliderRed;

	@FXML
	private Slider sliderGreen;

	@FXML
	private Slider sliderBlue;

	@FXML
	private TextField textFiedRed;

	@FXML
	private TextField textFieldGreen;

	@FXML
	private TextField textFieldBlue;

	@FXML
	private ColorPicker colorPicker;

	@FXML
	private Rectangle previewColor;

	@FXML
	private Rectangle previewGray;

	@FXML
	private HBox viewColors;

	@FXML
	private HBox viewGray;

	@FXML
	private Button deleteColor;

	@FXML
	private Button addColor;

	@FXML
	private ButtonBar buttonBar;

	@FXML
	private AnchorPane zoneColors;

	@FXML
	private AnchorPane zoneGray;

	@FXML
	private Button exporter;


	@FXML
	void addNewColorFromEdit(ActionEvent event) {
		if(listOfColors.size() < NB_COLORS_MAX && ! isColorAlreadyExist(colorPicker.getValue())) {
			Color colorFill = colorPicker.getValue();
			if(!isColorAlreadyExist(ConverterColor.rgb2gray(colorFill)) && !isColorNearToColorAlreadyExist(colorFill)) {
				int nbRectAfterAdd = listOfColors.size() + 1;
				Rectangle rect;

				rect = new Rectangle(viewColors.getWidth() / nbRectAfterAdd, viewColors.getHeight(), colorFill);
				rect.setOnMouseClicked(e-> handler(e));
				viewColors.getChildren().add(rect);
				listOfColors.add(rect);

				rect = new Rectangle(viewGray.getWidth() / nbRectAfterAdd, viewGray.getHeight(), ConverterColor.rgb2gray(colorFill));
				viewGray.getChildren().add(rect);
				listOfGray.add(rect);

				verification.add(colorFill);
				saveColors.add(new MyColorData(colorFill.toString(), ConverterColor.color2Hex(colorFill), ConverterColor.color2rgb(colorFill), ConverterColor.color2tsl(colorFill)));
			}else if(isColorAlreadyExist(ConverterColor.rgb2gray(colorFill))) {
				showAlert("Une couleur ayant déjà le même niveau de gris existe !");
			}else showAlert("Une couleur avec un niveau de gris assez proche existe!");
		}else{
			String msg = (listOfColors.size() >= NB_COLORS_MAX) ? "Nombre maximale de couleur atteint": "Cette couleur est déjà présente.";
			showAlert(msg);
		}
	}

	private void handler(MouseEvent e) {
		if(e.getButton().equals(MouseButton.PRIMARY) && buttonUpdate == null) {
			addColor.setVisible(false);
			deleteColor.setVisible(false);
			saveRec = ((Rectangle) e.getSource());
			Color c = (Color) (saveRec.getFill());
			sliderRed.setValue(c.getRed()*255);
			sliderGreen.setValue(c.getGreen()*255);
			sliderBlue.setValue(c.getBlue()*255);
			buttonUpdate = new Button("Update");
			buttonUpdate.getStyleClass().add("update-button");
			int index = listOfColors.indexOf(saveRec);
			listOfColors.get(index).setStroke(Color.BLACK);
			listOfColors.get(index).setStrokeDashOffset(1);
			buttonUpdate.setOnMousePressed(event->updateColorFrom(event, index));
			buttonBar.getButtons().add(buttonUpdate);
		}else if(buttonUpdate != null && buttonBar.getButtons().size() > 2){
			buttonBar.getButtons().remove(buttonBar.getButtons().size() -1);
		}
	}

	private void updateColorFrom(MouseEvent e, int index) {
		if(index != -1){
			verification.remove(index);
			saveColors.remove(index);
			Color colorFill = colorPicker.getValue();
			listOfColors.get(index).setFill(colorFill);
			listOfColors.get(index).setStroke(Color.TRANSPARENT);
			listOfColors.get(index).setStrokeWidth(0);
			listOfGray.get(index).setFill(ConverterColor.rgb2gray(colorFill));
			verification.add(index, colorFill);
			saveColors.add(index, new MyColorData(colorFill.toString(), ConverterColor.color2Hex(colorFill), ConverterColor.color2rgb(colorFill), ConverterColor.color2tsl(colorFill)));
			buttonBar.getButtons().remove(buttonUpdate);
			buttonUpdate = null;
			addColor.setVisible(true);
			deleteColor.setVisible(true);
		}
	}


	@FXML
	void deleteLastColor(ActionEvent event) {
		if(listOfColors.size() > 0 ){
			listOfColors.remove(listOfColors.size()-1);
			listOfGray.remove(listOfGray.size()-1);
			verification.remove(saveColors.size()-1);
			saveColors.remove(saveColors.size()-1);
		}else{
			showAlert("Il n'y a aucune couleur à supprimer");
		}
	}

	@FXML
	void colorFromColorPicker(ActionEvent event) {
		Color c = colorPicker.getValue();
		sliderRed.setValue(c.getRed()*255);
		sliderGreen.setValue(c.getGreen()*255);
		sliderBlue.setValue(c.getBlue()*255);
	}

	@FXML
	void colorFromTextField(ActionEvent event) {
		try{
			sliderRed.setValue(Double.valueOf(textFiedRed.getText()));
			sliderGreen.setValue(Double.valueOf(textFieldGreen.getText()));
			sliderBlue.setValue(Double.valueOf(textFieldBlue.getText()));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}

	@FXML
	void exportColor(ActionEvent event) {
		if (verification.size() > 0) {
			mainApp.showExportDialog(saveColors);
		} else {
			showAlert("Il n'y a rien à exporter");
		}
	}

	@Override
	public void changed(ObservableValue observableValue, Object o, Object t1) {
		Double r = sliderRed.getValue();
		Double g = sliderGreen.getValue();
		Double b = sliderBlue.getValue();


		Color color = Color.rgb(r.intValue(), g.intValue(), b.intValue());

		previewColor.setFill(color);
		previewGray.setFill(ConverterColor.rgb2gray(r,g,b));

		textFiedRed.setTextFormatter(new TextFormatter<>(change -> controleInput(change)));
		textFieldGreen.setTextFormatter(new TextFormatter<>(change -> controleInput(change)));
		textFieldBlue.setTextFormatter(new TextFormatter<>(change -> controleInput(change)));

		textFiedRed.setText(String.valueOf(r.intValue()));
		textFieldGreen.setText(String.valueOf(g.intValue()));
		textFieldBlue.setText(String.valueOf(b.intValue()));


		colorPicker.setValue(color);
	}

	@Override
	public void onChanged(Change change) {
		viewColors.getChildren().clear();
		viewGray.getChildren().clear();

		Double widthRect = (listOfColors.size() == 0) ? viewColors.getWidth(): viewColors.getWidth()/listOfColors.size();
		for(int i = 0; i < listOfColors.size(); i++){
			listOfColors.get(i).setWidth(widthRect);
		}
		viewColors.getChildren().addAll(listOfColors);
		widthRect = (listOfColors.size() == 0) ? viewGray.getWidth(): viewGray.getWidth()/listOfGray.size();
		for(int i = 0; i < listOfGray.size(); i++){
			listOfGray.get(i).setWidth(widthRect);
		}
		viewGray.getChildren().addAll(listOfGray);

		if(listOfColors.size() == 0 ) {
			exporter.setDisable(true);
			deleteColor.setDisable(true);
		}else {
			exporter.setDisable(false);
			deleteColor.setDisable(false);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		sliderRed.setStyle("-thumb-color: red;");
		sliderGreen.setStyle("-thumb-color: green;");
		sliderBlue.setStyle("-thumb-color: blue;");
		deleteColor.setStyle(
				"-fx-background-color: #ff0000;"
				+ "-fx-color: white;"
		);

		sliderRed.valueProperty().addListener(this);
		sliderGreen.valueProperty().addListener(this);
		sliderBlue.valueProperty().addListener(this);

		textFiedRed.onActionProperty().addListener(this);
		textFieldGreen.onActionProperty().addListener(this);
		textFieldBlue.onActionProperty().addListener(this);

		listOfColors.addListener(this);
		listOfGray.addListener(this);

		exporter.setDisable(true);
		deleteColor.setDisable(true);
	}

	private void showAlert(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERREUR");
		alert.setHeaderText(s);

		alert.showAndWait();
	}

	private TextFormatter.Change controleInput(TextFormatter.Change change) {
		DecimalFormat format = new DecimalFormat( "#.0" );
		if ( change.getControlNewText().isEmpty() )
		{
			return change;
		}

		ParsePosition parsePosition = new ParsePosition( 0 );
		Object object = format.parse( change.getControlNewText(), parsePosition );

		if ( object == null || parsePosition.getIndex() < change.getControlNewText().length() )
		{
			return null;
		}
		else
		{
			return change;
		}
	}

	private boolean isColorAlreadyExist(Color color) {
		return verification.contains(color);
	}

	private boolean isColorNearToColorAlreadyExist(Color color){
		if(verification.size() == 0) return false;
		Double gray = ConverterColor.gray(color);

		for(Color c: verification){
			Double tmpGray = ConverterColor.gray(c);
			if(Math.abs(gray-tmpGray) <= DELTA){
				return true;
			}
		}
		return false;
	}

	public void setMainApp(RGB2MCApp mainApp) {
		this.mainApp = mainApp;
	}
}
