package org.quelea.services.interaction;

import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.events.responses.SwitchScenesResponse;
import net.twasi.obsremotejava.requests.SetCurrentScene.SetCurrentSceneResponse;
import org.quelea.services.utils.LoggerUtils;

import java.util.logging.Logger;

public class TrackedOBSState extends OBSState {

    private static final Logger LOGGER = LoggerUtils.getLogger();

    private OBSRemoteController controller;

    public TrackedOBSState(){
        super(null);
    }

    public void register(OBSRemoteController controller){
        this.controller = controller;
        controller.registerSwitchScenesCallback( (SwitchScenesResponse response)->{
            this.scene = response.getSceneName();
            LOGGER.info("OBS State is now Scene:"+this.scene);
        });
    }

    public void synchroniseTo(OBSState requestedState) {
        if ( requestedState.scene != null && !requestedState.scene.equals(this.scene)){
            controller.setCurrentScene( requestedState.scene , (SetCurrentSceneResponse response)->
                    LOGGER.info("Changed scene based on request to "+requestedState.scene));
        }
    }
}
