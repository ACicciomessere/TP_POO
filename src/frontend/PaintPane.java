package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton copyFormat = new ToggleButton("Cop. Form.");


	// Botones para manipular la configuracion del borde y relleno
	Label borderLabel = new Label("Borde");
	Slider borderSlider = new Slider(1, 50, 25);

	ColorPicker borderColorPicker = new ColorPicker();

	Label fillLabel = new Label("Relleno");
	ColorPicker fillColorPicker = new ColorPicker();

	Label layerLabel = new Label("Capa");

	ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	private final Layer layer1;
	private final Layer layer2;
	private final Layer layer3;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		layer1 = canvasState.addCheckedLayer();
		CheckBox layer1CheckBox = new CheckBox(layer1.getName()); //Revisar si se puede hacer mejor esto

		layer2 = canvasState.addCheckedLayer();
		CheckBox layer2CheckBox = new CheckBox(layer2.getName());

		layer3 = canvasState.addCheckedLayer();
		CheckBox layer3CheckBox = new CheckBox(layer3.getName());


		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, copyFormat};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(borderLabel, borderSlider, borderColorPicker, fillLabel, fillColorPicker, layerLabel, layerChoiceBox);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		CheckBox[] checkBoxes = {layer1CheckBox, layer2CheckBox, layer3CheckBox};
		HBox checkBox = new HBox();
		Label layers = new Label("Mostrar Capas: ");
		checkBox.getChildren().add(layers);
		checkBox.getChildren().addAll(checkBoxes);
		checkBox.setStyle("-fx-background-color: #999");
		checkBox.setPrefHeight(25);
		checkBox.setPrefWidth(600);
		checkBox.setAlignment(Pos.CENTER);


		layerChoiceBox.getItems().addAll(layer1, layer2, layer3); // Ver si se puede hacer algo mas generico, y no solo para 3 items



		borderSlider.setShowTickLabels(true);
		borderSlider.setShowTickMarks(true);

		//Array de layers en el que se actualiza al checkear un box

		layer1CheckBox.setOnAction(event -> handleCheckBoxAction(layer1CheckBox, layer1));
        layer2CheckBox.setOnAction(event -> handleCheckBoxAction(layer2CheckBox, layer2));
        layer3CheckBox.setOnAction(event -> handleCheckBoxAction(layer3CheckBox, layer3));

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			Figure newFigure = null;
			if(rectangleButton.isSelected()) {
				newFigure = new Rectangle(layerChoiceBox.getValue().getName(), startPoint, endPoint);
			}
			else if(circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(layerChoiceBox.getValue().getName(), startPoint, circleRadius);
			} else if(squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(layerChoiceBox.getValue().getName(), startPoint, size);
			} else if(ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new Ellipse(layerChoiceBox.getValue().getName(), centerPoint, sMayorAxis, sMinorAxis);
			} else {
				return ;
			}
			canvasState.addFigure(layerChoiceBox.getValue(), newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure != null) {
					selectedFigure.updateCoordinates(diffX, diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
		setBottom(checkBox);
	}



	private void handleCheckBoxAction(CheckBox checkBox, Layer layer) {
		if (checkBox.isSelected()) {
			canvasState.activateLayer(layer);
		} else {
			canvasState.deactivateLayer(layer);
		}
		redrawCanvas();
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
			figure.fill(gc);
			figure.stroke(gc);
		}
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.pointBelongs(eventPoint);
	}
}
