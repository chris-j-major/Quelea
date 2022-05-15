package org.quelea.services.interaction;

import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.requests.GetVersion.GetVersionResponse;
import org.quelea.services.utils.LoggerUtils;

import java.util.logging.Logger;

public class OBSWebSocketClient  {

    private static final Logger LOGGER = LoggerUtils.getLogger();

    private final OBSWebSocket obs;
    private final OBSRemoteController controller;

    private boolean connectionOk = false;
    private final TrackedOBSState currentState = new TrackedOBSState();

    public OBSWebSocketClient(String serverURI, OBSWebSocket obsWebSocket, String password){
        LOGGER.info("Starting OBS connection");
        this.obs = obsWebSocket;
        this.controller = new OBSRemoteController(serverURI,true,password,false);
        this.controller.registerCloseCallback( (int statusCode, String reason)->{
            LOGGER.info("OSB Connection lost "+statusCode+"  "+reason);
            connectionOk = false;
            this.obs.connectLost(this);
        });
        this.controller.registerOnError( (String message, Throwable throwable)->{
            LOGGER.info("OSB Connection error "+message);
            this.obs.connectLost(this);
            connectionOk = false;
            this.controller.disconnect();
        });
        this.controller.registerConnectionFailedCallback( (String message)->{
            LOGGER.info("OSB connection failed error "+message);
            this.obs.connectLost(this);
            connectionOk = false;
            this.controller.disconnect();
        });
        this.controller.registerConnectCallback( (GetVersionResponse response)->{
            LOGGER.info("OBS Connection connected "+response.getObsStudioVersion() );
            connectionOk = true;
            checkForStateUpdateRequest();
        });
        currentState.register(this.controller);
        this.controller.connect();
    }

    public boolean desiredStateChanged() {
        return checkForStateUpdateRequest();
    }

    private boolean checkForStateUpdateRequest() {
        if ( connectionOk ){
            OBSState requestedState = this.obs.getDesiredState();
            if ( requestedState != null ){
                currentState.synchroniseTo( requestedState );
            }
            return true;
        }else{
            return false;
        }
    }

    public void close() {
        connectionOk = false;
        controller.disconnect();
    }
}
