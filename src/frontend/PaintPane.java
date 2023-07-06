package frontend;

import backend.CanvasState;
import backend.formatting.*;
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

	double lineWidth= 1;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton copyFormat = new ToggleButton("Cop. Form.");
	ToggleButton cutButton = new ToggleButton("Cut");
	ToggleButton pasteButton = new ToggleButton("Paste");


	// Botones para manipular la configuracion del borde y relleno
	Label borderLabel = new Label("Borde");
	Slider borderSlider = new Slider(1, 50, 25);

	ColorPicker borderColorPicker = new ColorPicker(lineColor);

	Label fillLabel = new Label("Relleno");
	ColorPicker fillColorPicker = new ColorPicker(fillColor);

	Label layerLabel = new Label("Capa");

	//Etiquetas con los nombres de las acciones y la cantidad de elementos que hay para hacer y deshacer
	Label textoIzquierda = new Label();
	Label valorIzquierda = new Label();
	Label textoDerecha = new Label();
	Label valorDerecha = new Label();

	ToggleButton deshacerButton = new ToggleButton("Deshacer");
	ToggleButton rehacerButton = new ToggleButton("Rehacer");

	ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// Seleccionar una figura
	Figure copiedFigure;

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


		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, copyFormat, cutButton, pasteButton};
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

		HBox doUndo = new HBox();
		Label[] varsIzquierda = {textoIzquierda, valorIzquierda};
		Label[] varsDerecha = {valorDerecha, textoDerecha};
		ToggleButton[] Buttons = {deshacerButton, rehacerButton};
		doUndo.getChildren().addAll(varsIzquierda);
		doUndo.getChildren().addAll(Buttons);
		doUndo.getChildren().addAll(varsDerecha);
		doUndo.setStyle("-fx-background-color: #999");
		doUndo.setPrefHeight(35);
		doUndo.setPrefWidth(600);
		doUndo.setAlignment(Pos.CENTER);

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
			if(startPoint == null || startPoint.equals(endPoint)) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			MakeActionForFigure makeAction;
			if(rectangleButton.isSelected()) {
				makeAction = new MakeActionForRectangle(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
				makeAction.setLayer(layerChoiceBox.getValue());
			}
			else if(circleButton.isSelected()) {
				makeAction = new MakeActionForCircle(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
				makeAction.setLayer(layerChoiceBox.getValue());
			} else if(squareButton.isSelected()) {
				makeAction = new MakeActionForSquare(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
				makeAction.setLayer(layerChoiceBox.getValue());
			} else if(ellipseButton.isSelected()) {
				makeAction = new MakeActionForEllipse(startPoint, event, gc, canvasState, lineColor, fillColor, lineWidth);
				makeAction.setLayer(layerChoiceBox.getValue());
			} else {
				return ;
			}
			canvasState.addAction(makeAction);
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

		setTop(doUndo);
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

	private void redrawCanvas() {
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

