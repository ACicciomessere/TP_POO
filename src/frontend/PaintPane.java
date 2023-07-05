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

import java.util.Set;
import java.util.TreeSet;

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

	ChoiceBox<String> layerChoiceBox = new ChoiceBox<>();

	CheckBox layer1 = new CheckBox("Layer 1");
	CheckBox layer2 = new CheckBox("Layer 2");
	CheckBox layer3 = new CheckBox("Layer 3");

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
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

		CheckBox[] checkBoxes = {layer1, layer2, layer3};
		HBox checkBox = new HBox();
		Label layers = new Label("Mostrar Capas: ");
		checkBox.getChildren().add(layers);
		checkBox.getChildren().addAll(checkBoxes);
		checkBox.setStyle("-fx-background-color: #999");
		checkBox.setPrefHeight(25);
		checkBox.setPrefWidth(600);
		checkBox.setAlignment(Pos.CENTER);

		layerChoiceBox.getItems().addAll("Layer 1", "Layer 2", "Layer 3");

		borderSlider.setShowTickLabels(true);
		borderSlider.setShowTickMarks(true);

		//Array de layers en el que se actualiza al checkear un box

		layer1.setOnAction(event -> handleCheckBoxAction(layer1, "Layer 1"));
        layer2.setOnAction(event -> handleCheckBoxAction(layer2, "Layer 2"));
        layer3.setOnAction(event -> handleCheckBoxAction(layer3, "Layer 3"));

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
				newFigure = new Rectangle(layerChoiceBox.getValue(), startPoint, endPoint);
			}
			else if(circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(layerChoiceBox.getValue(), startPoint, circleRadius);
			} else if(squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(layerChoiceBox.getValue(), startPoint, size);
			} else if(ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new Ellipse(layerChoiceBox.getValue(), centerPoint, sMayorAxis, sMinorAxis);
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
			for(Figure figure : canvasState.figures(checkedLayers)) {
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
				for (Figure figure : canvasState.figures(checkedLayers)) {
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

	private Set<String> checkedLayers = new TreeSet<>();

	private void handleCheckBoxAction(CheckBox checkBox, String layer) {
		if (checkBox.isSelected()) {
			checkedLayers.add(layer);
		} else {
			checkedLayers.remove(layer);
		}
		redrawCanvas();
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures(checkedLayers)) {
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
