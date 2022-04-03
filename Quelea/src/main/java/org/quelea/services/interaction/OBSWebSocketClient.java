package org.quelea.services.interaction;

import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.quelea.services.utils.LoggerUtils;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ClientEndpoint
public class OBSWebSocketClient  {

    private static final Logger LOGGER = LoggerUtils.getLogger();

    private final OBSWebSocket obs;

    private String password = null;

    private JSONParser parser = new JSONParser();

    private static final Map<String, String> extra_headers = buildExtraHeaders();

    private static Map<String, String> buildExtraHeaders() {
        Map<String, String> httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Sec-WebSocket-Protocol", "obswebsocket.json");
        return httpHeaders;
    }

    public OBSWebSocketClient(URI serverURI, OBSWebSocket obsWebSocket, String password){
        this.obs = obsWebSocket;
        this.password = password;
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, serverURI);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening");
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing");
        this.obs.connectLost( this );
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        System.out.println(bytes);
    }

    public void desiredStateChanged() {
        checkForStateUpdateRequest();
    }

    private void checkForStateUpdateRequest() {
    }
}
