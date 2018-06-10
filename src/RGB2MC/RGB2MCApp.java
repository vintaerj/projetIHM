package RGB2MC;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RGB2MCApp extends Application {

	private Stage primaryStage;
	private Parent rootLayout;
	private ObservableList<MyColorData> colorData = FXCollections.observableArrayList();

	public RGB2MCApp() {
		colorData.add(new MyColorData(Color.rgb(0,0,0).toString(), "#000000", "(0,0,0)","(0°, 0%, 0%)"));
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
		this.primaryStage.setTitle("RGB2MC");

		this.primaryStage.getIcons().add(new Image("file:resources/images/RGB2MC.png"));

		initRootLayout();
	}


	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/RGB2MC/RGB2MC.fxml"));
			rootLayout = loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			RGB2MCController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	void showExportDialog(ObservableList<MyColorData> myColorDatas) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/RGB2MC/RGB2MCExport.fxml"));
			AnchorPane page = loader.load();

			Stage exportStage = new Stage();
			exportStage.setResizable(false);
			exportStage.setTitle("RGB2MC->Export Color");
			exportStage.initModality(Modality.WINDOW_MODAL);
			exportStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			exportStage.setScene(scene);

			ExportController controller = loader.getController();
			controller.setDialogStage(exportStage);
			controller.setColorData(myColorDatas);
			exportStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public ObservableList<MyColorData> getColorData() {
		return colorData;
	}
}
