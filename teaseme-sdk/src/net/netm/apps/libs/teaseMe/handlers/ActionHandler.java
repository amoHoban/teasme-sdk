package net.netm.apps.libs.teaseme.handlers;

import java.util.Map;

/**
 * ActionHandler interface for handling {@link} Teaser actionTypes
 * <p/>
 * Created by ahoban on 27.03.15.
 */
public interface ActionHandler {

    /**
     * When can this handler handle the action?
     *
     * @param actionType
     * @param properties {@link net.netm.apps.libs.teaseme.models.Teaser#getProperties()} teaser properties
     * @return true to stop handling with different handlers
     */
    public boolean canHandle(String actionType, String actionValue, Map<String, String> properties);

    /**
     * If options are passed, handle the action here
     *
     * @param actionType  the teaserActionType
     * @param actionValue the teaserActionValue
     * @param properties {@link net.netm.apps.libs.teaseme.models.Teaser#getProperties()} teaser properties
     * @return true to stop handling with different handlers
     */
    public boolean handle(String actionType, String actionValue, Map<String, String> properties);

}
