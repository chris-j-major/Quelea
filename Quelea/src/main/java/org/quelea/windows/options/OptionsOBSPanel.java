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
import org.quelea.services.utils.QueleaPropertyKeys;

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
/*
        this.websocketUriField = Field.ofStringType(this.obsWebSocketURI);
        this.websocketPasswordField = Field.ofStringType(this.passwordProperty);
        bindings.put(websocketUriField, useObsProperty.not());
        bindings.put(websocketPasswordField, useObsProperty.not());

 */
    }

    public Category getObsTab() {
        return Category.of(LabelGrabber.INSTANCE.getLabel("obs.options.heading"), new ImageView(new Image("file:icons/obsicon.png")),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.use.label"), useObsProperty).customKey(QueleaPropertyKeys.useObsKey),
            /*
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.uri.label"), websocketUriField, obsWebSocketURI ).customKey(QueleaPropertyKeys.obsWebSocketURLKey),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.password.label"), websocketPasswordField, passwordProperty ).customKey(QueleaPropertyKeys.obsWebSocketPasswordKey)
             */
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.uri.label"), obsWebSocketURI ).customKey(QueleaPropertyKeys.obsWebSocketURLKey),
                Setting.of(LabelGrabber.INSTANCE.getLabel("obs.websocket.password.label"),  passwordProperty ).customKey(QueleaPropertyKeys.obsWebSocketPasswordKey)
       );
    }
}
