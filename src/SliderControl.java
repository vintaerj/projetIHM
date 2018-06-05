import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

public class SliderControl implements Initializable, ChangeListener, MapChangeListener {
	private static final int NB_COLORS = 10;

	@FXML
	private AnchorPane background;

	@FXML
	private Slider red;

	@FXML
	private Slider green;

	@FXML
	private Slider blue;

	@FXML
	private TextField valueRed;

	@FXML
	private TextField valueGreen;

	@FXML
	private TextField valueBlue;

	@FXML
	private Rectangle previewColor;

	@FXML
	private Rectangle previewGray;

	@FXML
	private ColorPicker hexaColor;

	@FXML
	private Button addButton;

	@FXML
	private AnchorPane dcolor;

	@FXML
	private HBox hBoxColors;

	@FXML
	private AnchorPane dgray;

	@FXML
	private HBox hBoxGray;

	private SliderApp sliderApp;

	private ObservableList<Rectangle> rColors = FXCollections.observableArrayList();
	private ObservableList<Rectangle> rGrays = FXCollections.observableArrayList();
	private ObservableList<Color> colors = FXCollections.observableArrayList();

	@Override
	public void changed(ObservableValue observableValue, Object o, Object t1) {
		Double r = red.getValue();
		Double g = green.getValue();
		Double b = blue.getValue();


		Color color = Color.rgb(r.intValue(), g.intValue(), b.intValue());

		previewColor.setFill(color);
		previewGray.setFill(niveauDeGris(r,g,b));

		valueBlue.setTextFormatter(new TextFormatter<>(change -> {
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

		}));

		valueRed.setText(String.valueOf(r.intValue()));
		valueGreen.setText(String.valueOf(g.intValue()));
		valueBlue.setText(String.valueOf(b.intValue()));




		hexaColor.setValue(color);
	}



	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		red.valueProperty().addListener(this);
		green.valueProperty().addListener(this);
		blue.valueProperty().addListener(this);

		valueRed.onActionProperty().addListener(this);
		valueGreen.onActionProperty().addListener(this);
		valueBlue.onActionProperty().addListener(this);





	}





	@FXML
	void cliked(MouseEvent event) {
		if(rColors.size() < NB_COLORS) {
			//System.out.println("add "+ hexaColor.getValue());
			Double w = dcolor.getWidth() / (rColors.size() + 1);

			Color rectFill = hexaColor.getValue();

			Rectangle cRect = new Rectangle(w, dcolor.getHeight(), rectFill);

			cRect.setOnMouseClicked(e ->{
				if(e.getButton().equals(MouseButton.PRIMARY)) {
					System.out.println("click gauche");
					Color c = (Color) ((Rectangle) e.getSource()).getFill();
					red.setValue(c.getRed()*255);
					green.setValue(c.getGreen()*255);
					blue.setValue(c.getBlue()*255);

				}
				if(e.getButton().equals(MouseButton.SECONDARY))
					System.out.println("click droit");


			});
			hBoxColors.getChildren().add(cRect);
			rColors.add(cRect);

			Rectangle gRect = new Rectangle(w, dgray.getHeight(), niveauDeGris(rectFill));
			hBoxGray.getChildren().add(gRect);
			rGrays.add(gRect);

			System.out.println(rGrays.size() + "   " + w);
			for (int i = 0; i < rColors.size(); i++) {
				rColors.get(i).setWidth(w);
				rGrays.get(i).setWidth(w);
			}
		}else{
			// Show the error message.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERREUR");
			alert.setHeaderText("pas plus de "+NB_COLORS);

			alert.showAndWait();
		}
	}

	private Color niveauDeGris(Color value) {
		return niveauDeGris(value.getRed()*255, value.getGreen()*255, value.getBlue()*255);
	}

	private Color niveauDeGris(Double r, Double g, Double b) {
		//NiveauGris = 0.3   Rouge + 0.59   Vert + 0.11   Bleu
		return Color.grayRgb((int)(0.3*r + 0.59*g + 0.11*b));
	}

	@Override
	public void onChanged(Change change) {
		hBoxColors.getProperties().addListener(this);
		hBoxGray.getProperties().addListener(this);
	}

	public void setMainApp(SliderApp mainApp) {
		this.sliderApp = mainApp;
	}

	public void changedTextField(javafx.event.ActionEvent actionEvent) {

			try{
				red.setValue(Double.valueOf(valueRed.getText()));
				green.setValue(Double.valueOf(valueGreen.getText()));
				blue.setValue(Double.valueOf(valueBlue.getText()));
			}catch(NumberFormatException e){
				//e.printStackTrace();
			}


	}
}
