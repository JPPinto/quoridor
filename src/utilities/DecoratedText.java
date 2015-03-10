package utilities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.*;

public class DecoratedText extends FlowPane {

    private IntegerProperty wrappingWidth;
    private ReadOnlyObjectProperty<Font> font;

    public DecoratedText(Font font) {
        super(Orientation.HORIZONTAL);
        wrappingWidth = new SimpleIntegerProperty(this, "wrappingWidth", 0);
        //getStylesheets().add(getClass().getResource(getClass().getSimpleName()+".css").toExternalForm());
        this.font = new SimpleObjectProperty(this, "font", font);
        prefWidthProperty().bindBidirectional(wrappingWidth);
    }

    /**
     * Append text. If the text represents a paragraph (indicated by '\n'), it
     * is not broken up into its parts.
     * @param text
     */
    public void append(String text) {
        if (text.endsWith("\n")) {
            Text decoText = new Text(text);

            //decoText.getStyleClass().add("decoratedText-text");
            decoText.setFont(font.get());
            decoText.wrappingWidthProperty().bind(wrappingWidth);
            getChildren().add(decoText);
        } else {
            String[] parts = text.split(" ");
            for (String part : parts) {
                Text decoText = new Text(part+" ");
                //decoText.getStyleClass().add("decoratedText-text");
                decoText.setFont(font.get());
                decoText.setStyle("-fx-fill: white; -fx-effect: dropshadow(three-pass-box , rgba(0, 0, 0, 0.7), 2, 2 , 2, 2);");
                getChildren().add(decoText);
            }
        }
    }

    /**
     * Append a control.
     * @param control
     */
    public void append(Node control) {
        getChildren().add(control);
    }

    public int getWrappingWidth() {
        return wrappingWidth.get();
    }

    public IntegerProperty wrappingWidthProperty() {
        return wrappingWidth;
    }

    public void setWrappingWidth(int wrappingWidth) {
        this.wrappingWidth.set(wrappingWidth);
    }
}
