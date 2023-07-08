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
import java.util.NoSuchElementException;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;

	private static final int MIN_SIZE_LABEL = 280;
	private static final String GRAY_BACKGROUND_COLOR = "-fx-background-color: #999";
	private static final int BOX_SPACING = 10;
	private static final int PADDING = 5;
	private static final String FONT_SIZE = "-fx-font-size: 14";

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton copyFormat = new ToggleButton("Cop. Form.");

	private boolean added=true;

	// Botones para manipular la configuracion del borde y relleno
	Label borderLabel = new Label("Borde");
	private final Slider borderSlider = new Slider(1, 50, 25);

	private final ColorPicker borderColorPicker = new ColorPicker(lineColor);

	Label fillLabel = new Label("Relleno");
	private final ColorPicker fillColorPicker = new ColorPicker(fillColor);

	Label layerLabel = new Label("Capa");

	//Etiquetas con los nombres de las acciones y la cantidad de elementos que hay para hacer y deshacer
	ToggleButton undoButton = new ToggleButton ("Deshacer");
	ToggleButton redoButton = new ToggleButton("Rehacer");

	private final ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	private Boolean newFormat = true;

	private final Layer layer1;
	private final Layer layer2;
	private final Layer layer3;

	private Color borderColor;
	private double width;

	private Color newFillColor, newBorderColor;
	private double newBorderWidth;

	private final Label undoLabel, redoLabel;

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

		HBox doButtonsBox = new HBox(BOX_SPACING);
		doButtonsBox.setAlignment(Pos.CENTER);
		undoLabel = new Label("");
		redoLabel = new Label("");
		undoLabel.setText(String.format("%s %d",canvasState.getNextUndo() == null ? "" : canvasState.getNextUndo().toString(), canvasState.getUndoAvailable()));
		redoLabel.setText(String.format("%d %s", canvasState.getRedoAvailable(), canvasState.getNextRedo() == null ? "" : canvasState.getNextRedo().toString()));
		undoLabel.setStyle(FONT_SIZE);
		redoLabel.setStyle(FONT_SIZE);
		undoLabel.setMinWidth(MIN_SIZE_LABEL);
		redoLabel.setMinWidth(MIN_SIZE_LABEL);
		undoLabel.setAlignment(Pos.CENTER_RIGHT);
		ToggleButton[] doToolsArr = { undoButton, redoButton};
		for(ToggleButton tool : doToolsArr) {
			tool.setMinWidth(50);
			tool.setCursor(Cursor.HAND);
		}
		doButtonsBox.getChildren().add(undoLabel);
		doButtonsBox.getChildren().add(undoButton);
		doButtonsBox.getChildren().add(redoButton);
		doButtonsBox.getChildren().add(redoLabel);
		doButtonsBox.setPadding(new Insets(PADDING));

		VBox topButtonsBox = new VBox();
		topButtonsBox.getChildren().addAll(doButtonsBox);
		topButtonsBox.setStyle(GRAY_BACKGROUND_COLOR);

		borderSlider.setShowTickLabels(true);
		borderSlider.setShowTickMarks(true);
		setCursor(borderSlider);
		setCursor(fillColorPicker);
		setCursor(borderColorPicker);

		//Array de layers en el que se actualiza al checkear un box

		layer1CheckBox.setOnAction(event -> handleCheckBoxAction(layer1CheckBox, layer1));
        layer2CheckBox.setOnAction(event -> handleCheckBoxAction(layer2CheckBox, layer2));
        layer3CheckBox.setOnAction(event -> handleCheckBoxAction(layer3CheckBox, layer3));

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			fillColor = fillColorPicker.getValue();
			borderColor = borderColorPicker.getValue();
			width = borderSlider.getValue();
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			Figure newFigure;
			if(rectangleButton.isSelected()) {
				newFigure = new Rectangle(layerChoiceBox.getValue(), startPoint, endPoint, fillColor, borderColor, width, canvasState);
			}
			else if(circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(layerChoiceBox.getValue(), startPoint, circleRadius, fillColor, borderColor, width, canvasState);
			} else if(squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(layerChoiceBox.getValue(), startPoint, size, fillColor, borderColor, width, canvasState);
			} else if(ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
				double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
				double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
				newFigure = new Ellipse(layerChoiceBox.getValue(), centerPoint, sMayorAxis, sMinorAxis, fillColor, borderColor, width, canvasState);
			} else {
				return ;
			}
			added=true;
			try {
				canvasState.addFigure(layerChoiceBox.getValue(), newFigure);
			}
			 catch (IllegalArgumentException e) {
				throwWarning(e.getMessage());
				added=false;
			}
			if(added){
				canvasState.addUndo(new DrawFigure(canvasState, newFigure));
			}
			startPoint = null;
			redrawCanvas();
		});

		copyFormat.setOnAction(event -> {
			try {
				if (selectedFigure != null) {
					newBorderWidth = selectedFigure.getBorderWidth();
					newFillColor = selectedFigure.getFillingColor();
					newBorderColor = selectedFigure.getBorderColor();
					newFormat = true;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("Debe seleccionar una figura para copiar formato");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		borderColorPicker.setOnAction(event -> {
			Color newColor = borderColorPicker.getValue();
			if (selectedFigure != null) {
				Action changeBorderColorAction = new ChangeBorderColor(selectedFigure, newColor, canvasState);
				changeBorderColorAction.activateAction();
				canvasState.addUndo(changeBorderColorAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		undoButton.setOnAction(event -> {
			try {
				if(canvasState.undoLastAction()) {
					selectedFigure = null;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("No hay operaciones para deshacer");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});

		redoButton.setOnAction(event->{
			try {
				if (canvasState.redoLastAction()) {
					selectedFigure = null;
					redrawCanvas();
				}
				else
					throw new NoSuchElementException("No hay operaciones para rehacer");
			} catch (NoSuchElementException e) {
				throwWarning(e.getMessage());
			}
		});



		borderSlider.valueProperty().addListener((observableValue, oldV, newV) -> {
			width = newV.doubleValue();
			canvasState.findFigure(selectedFigure).setBorderWidth(width);
			redrawCanvas();
		});

		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				Action changeFillAction = new ChangeFillColor(selectedFigure, fillColor, canvasState);
				changeFillAction.activateAction();
				canvasState.addUndo(changeFillAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});


		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure);
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
						label.append(figure);
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());

				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
			}
			else if (copyFormat.isSelected()) {
				if (newFormat) {
					Point eventPoint = new Point(event.getX(), event.getY());
					Action copyFormat = new CopyFormat(canvasState, selectedFigure, eventPoint, newBorderColor, newFillColor, newBorderWidth);
					copyFormat.activateAction();
					canvasState.addUndo(copyFormat);
					newFormat = false;
				}
			}
			redrawCanvas();
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
				Action deleteAction = new DeleteFigure(canvasState, selectedFigure);
				deleteAction.activateAction();
				canvasState.addUndo(deleteAction);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setTop(topButtonsBox);
		setLeft(buttonsBox);
		setRight(canvas);
		setBottom(checkBox);
	}

	private void throwWarning(String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText("Operacion Invalida");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void setCursor(Control o) {
		o.setCursor(Cursor.HAND);
	}

	private void handleCheckBoxAction(CheckBox checkBox, Layer layer) {
		if (checkBox.isSelected()) {
			canvasState.activateLayer(layer);
		} else {
			canvasState.deactivateLayer(layer);
		}
		redrawCanvas();
	}

	public void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getBorderColor());
			}
			gc.setFill(figure.getFillingColor());
			gc.setLineWidth(figure.getBorderWidth());
			figure.fill(gc);
			figure.stroke(gc);
		}

		undoLabel.setText(String.format("%s %d",canvasState.getNextUndo() == null ? "" : canvasState.getNextUndo().toString(), canvasState.getUndoAvailable()));
		redoLabel.setText(String.format("%d %s", canvasState.getRedoAvailable(), canvasState.getNextRedo() == null ? "" : canvasState.getNextRedo().toString()));
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.pointBelongs(eventPoint);
	}
}

