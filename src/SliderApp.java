import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SliderApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

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
		showRGB2MCApp();
	}

	private void showRGB2MCApp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("slider.fxml"));
			AnchorPane RGB2MC = loader.load();


			rootLayout.setCenter(RGB2MC);

			SliderControl controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SliderApp.class.getResource("RootLayout.fxml"));
			rootLayout = loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
