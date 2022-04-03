/*
 * This file is part of Quelea, free projection software for churches.
 * (C) 2012 Michael Berry
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.services.interaction;

import org.quelea.data.displayable.Displayable;
import org.quelea.data.displayable.TextDisplayable;
import org.quelea.windows.main.DisplayPanelListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class OBSWebSocket implements DisplayPanelListener {

    private OBSWebSocketClient activeClient = null;

    public OBSState desired = null;

    public void start() throws IOException {

    }

    private void ensureClientIsActive() {
        try {
            if ( activeClient == null ){
                activeClient = new OBSWebSocketClient(new URI("ws://localhost:4444") , this ,"test" );
            }else{
                activeClient.desiredStateChanged();
            }
        } catch (URISyntaxException e) {
            activeClient = null;
            e.printStackTrace();
        }
    }

    @Override
    public void updateDisplayable(Displayable displayable, int index, Map<String, Boolean> stateMap) {
        synchronized (this) {
            if (stateMap.getOrDefault("black", false)) {
                desired = new OBSState("camera");
            } else if (displayable instanceof TextDisplayable) {
                desired = new OBSState("song");
            }
        }
        ensureClientIsActive();
    }

    public void connectLost(OBSWebSocketClient obsWebSocketClient) {
        if (this.activeClient == obsWebSocketClient ){
            this.activeClient = null;
        }
    }

    public OBSState getDesiredState() {
        OBSState target;
        synchronized (this) {
            target = desired;
            desired = null;
        }
        return target;
    }
}