package org.quelea.windows.options;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.model.validators.ValidationResult;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Setting;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.quelea.services.languages.LabelGrabber;
import org.quelea.services.utils.QueleaProperties;

import java.util.HashMap;


public class OptionsOBSPanel {
    private final HashMap<Field, ObservableValue> bindings;
    private final SimpleBooleanProperty useObsProperty;
    private final SimpleStringProperty passwordProperty;
    private final SimpleStringProperty obsWebSocketURI;

    public OptionsOBSPanel(HashMap<Field, ObservableValue> bindings) {
        this.bindings = bindings;

        this.useObsProperty = new SimpleBooleanProperty(QueleaProperties.get().getUseObsConnection());
        this.obsWebSocketURI = new SimpleStringProperty(QueleaProperties.get().getObsWebSocketURL());
        this.passwordProperty = new SimpleStringProperty(QueleaProperties.get().getObsWebSocketPassword());
    }

    private ValidationResult validatePortNumber(String s) {
        int portNum = 0;
        try {
            portNum = Integer.parseInt(s);
        } catch (NumberFormatException ignored) {}
        return IntegerRangeValidator.between(1029, 49151, LabelGrabber.INSTANCE.getLabel("enter.valid.port")).validate(portNum);
    }

    public Category getObsTab() {
        return Category.of(LabelGrabber.INSTANCE.getLabel("obs.options.heading"), new ImageView(new Image("file:icons/obsicon.png")),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.use.label"), this.useObsProperty),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.uri.label"), this.obsWebSocketURI),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.password.label"), passwordProperty)
       );
    }
}
