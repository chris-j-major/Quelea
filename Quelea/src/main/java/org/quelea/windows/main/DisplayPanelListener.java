package org.quelea.windows.main;

import org.quelea.data.displayable.Displayable;

import java.util.Map;

public interface DisplayPanelListener {

    /**
     * Notify this listener that the displayable shown has changed.
     * <p/>
     *  @param displayable the displayable to show.
     * @param index       the index of the displayable to show, if relevant.
     * @param stateMap
     */
    void updateDisplayable(Displayable displayable, int index, Map<String, Boolean> stateMap);
}
