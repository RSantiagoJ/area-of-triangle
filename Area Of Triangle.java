/**
 * Created by Ricardo on 10/23/2016.
 * csc-220
 * hw4
 * rjsantiago@student.stcc.edu
 */

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class AreaOfTriangle extends Application {

    private static Polygon triangle;
    private static ObservableList<Double> vertices;

    private static TextField tfArea;
    private static TextField tfSideA;
    private static TextField tfSideB;
    private static TextField tfSideC;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static double[] getCoords(char angle) {
        vertices = triangle.getPoints();

        double[] coords = new double[2];

        if (angle == 'A') {
            coords[0] = Math.abs(vertices.get(0));
            coords[1] = Math.abs(vertices.get(1));
        }

        if (angle == 'B') {
            coords[0] = Math.abs(vertices.get(2));
            coords[1] = Math.abs(vertices.get(3));
        }

        if (angle == 'C') {
            coords[0] = Math.abs(vertices.get(4));
            coords[1] = Math.abs(vertices.get(5));
        }

        return coords;
    }

    private static double getSideLength(double[] coords1, double[] coords2) {
        double length = 0;
        double val1 = Math.abs(coords1[0] - coords2[0]);
        double val2 = Math.abs(coords1[1] - coords2[1]);

        length = Math.sqrt((Math.pow(val1, 2)) + (Math.pow(val2, 2)));
        length = Math.round(length * 100) / 100;
        return length;
    }

    private static double getArea() {

        double a = getSideA();
        double b = getSideB();
        double c = getSideC();

        double s = ((a + b + c) / 2);
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

        return area;
    }

    private static double getSideA() {
        return getSideLength(getCoords('A'), getCoords('B'));
    }

    private static double getSideB() {
        return getSideLength(getCoords('A'), getCoords('C'));
    }

    private static double getSideC() {
        return getSideLength(getCoords('B'), getCoords('C'));
    }

    public static void printResults() {
        tfArea.setText("" + getArea());
        tfSideA.setText("" + getSideA());
        tfSideB.setText("" + getSideB());
        tfSideC.setText("" + getSideC());
    }

    @Override
    public void start(final Stage stage) throws Exception {
        triangle = createStartingTriangle();

        Group group = new Group();
        group.getChildren().addAll(triangle);
        group.getChildren().addAll(createControlAnchorsFor(triangle.getPoints()));

        BorderPane bp = new BorderPane();
        bp.getChildren().add(group);
        bp.setTop(getTopPane());

        stage.setTitle("Triangle Area Visualization");
        stage.setScene(new Scene(bp, 1000, 800, Color.ALICEBLUE));
        stage.show();

    }

    private HBox getTopPane() {
        HBox topPane = new HBox();
        topPane.setPrefSize(1000, 50);
        topPane.setAlignment(Pos.TOP_CENTER);
        topPane.setPadding(new Insets(10, 10, 10, 10));
        topPane.setSpacing(15);
        topPane.setStyle("-fx-font-weight: bold;-fx-font-size: 1.3em;");

        Label lbArea = new Label("Area :");

        this.tfArea = new TextField();
        this.tfArea.setFocusTraversable(false);
        this.tfArea.setText("" + getArea());

        topPane.getChildren().addAll(lbArea, tfArea, getStatsPane());

        return topPane;
    }

    private HBox getStatsPane() {
        HBox statsPane = new HBox();
        statsPane.setAlignment(Pos.TOP_CENTER);
        statsPane.setPadding(new Insets(10, 10, 10, 10));
        statsPane.setSpacing(15);
        statsPane.setStyle("-fx-font-weight: bold;-fx-font-size: 1.3em;");

        statsPane.getChildren().addAll(getLabelPane(), getLengthPane());

        return statsPane;
    }

    private VBox getLabelPane() {
        VBox labelPane = new VBox();
        labelPane.setPrefSize(150, 300);
        labelPane.setAlignment(Pos.CENTER_RIGHT);
        labelPane.setSpacing(20);
        labelPane.setStyle("-fx-font-weight: bold;-fx-font-size: 1.3em;");

        Label lbSideA = new Label("Side A :");
        lbSideA.setPrefWidth(100);

        Label lbSideB = new Label("Side B :");
        lbSideB.setPrefWidth(100);

        Label lbSideC = new Label("Side C :");
        lbSideC.setPrefWidth(100);

        labelPane.getChildren().addAll(lbSideA, lbSideB, lbSideC);

        return labelPane;
    }

    private VBox getLengthPane() {
        VBox lengthPane = new VBox();
        lengthPane.setPrefSize(100, 100);
        lengthPane.setAlignment(Pos.CENTER_LEFT);
        lengthPane.setSpacing(10);
        lengthPane.setStyle("-fx-font-weight: bold;-fx-font-size: 1.3em;");

        this.tfSideA = new TextField("" + getSideA());
        this.tfSideA.setPrefColumnCount(50);
        this.tfSideA.setFocusTraversable(false);
        this.tfSideB = new TextField("" + getSideB());
        this.tfSideB.setPrefColumnCount(50);
        this.tfSideB.setFocusTraversable(false);
        this.tfSideC = new TextField("" + getSideC());
        this.tfSideC.setPrefColumnCount(50);
        this.tfSideC.setFocusTraversable(false);

        lengthPane.getChildren().addAll(tfSideA, tfSideB, tfSideC);

        return lengthPane;

    }

    // creates a triangle.
    private Polygon createStartingTriangle() {
        Polygon triangle = new Polygon();

        triangle.getPoints().setAll(400d, 200d, 200d, 500d, 600d, 500d);

        triangle.setStroke(Color.FORESTGREEN);
        triangle.setStrokeWidth(4);
        triangle.setStrokeLineCap(StrokeLineCap.ROUND);
        triangle.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));

        return triangle;
    }

    private ObservableList<Anchor> createControlAnchorsFor(final ObservableList<Double> points) {
        ObservableList<Anchor> anchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i += 2) {
            final int idx = i;

            DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

            xProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldX, Number x) {
                    points.set(idx, (double) x);
                }
            });

            yProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldY, Number y) {
                    points.set(idx + 1, (double) y);
                }
            });

            anchors.add(new Anchor(Color.GOLD, xProperty, yProperty));
        }

        return anchors;
    }

    class Anchor extends Circle {
        private final DoubleProperty x, y;

        Anchor(Color color, DoubleProperty x, DoubleProperty y) {

            super(x.get(), y.get(), 10);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            this.x = x;
            this.y = y;

            x.bind(centerXProperty());
            y.bind(centerYProperty());

            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getX() + dragDelta.x;
                    if (newX > 0 && newX < getScene().getWidth()) {
                        setCenterX(newX);
                    }
                    double newY = mouseEvent.getY() + dragDelta.y;
                    if (newY > 0 && newY < getScene().getHeight()) {
                        setCenterY(newY);
                    }
                    printResults();
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
        }

        // records relative x and y co-ordinates.
        private class Delta {
            double x, y;
        }
    }
}