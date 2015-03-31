package net.netm.apps.libs.teaseMe.handlers;

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
     * @return
     */
    public boolean canHandle(String actionType);

    /**
     * Handle the action
     *
     * @param actionType the teaserActionType
     * @param actionValue the teaserActionValue
     */
    public void handle(String actionType, String actionValue);

    /**
     * If options are passed, handle the action here
     *
     * @param actionType  the teaserActionType
     * @param actionValue the teaserActionValue
     * @param options additional options passed
     */
    public void handle(String actionType, String actionValue, Map<String, String> options);

}
