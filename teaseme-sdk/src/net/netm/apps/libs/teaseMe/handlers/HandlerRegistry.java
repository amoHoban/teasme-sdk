package net.netm.apps.libs.teaseme.handlers;

import java.util.Map;

/**
 * Created by ahoban on 27.03.15.
 */
public interface HandlerRegistry {

    /**
     *
     * <p>Find handler for specific action type</p>
     *
     * @param actionType
     * @param actionValue
     * @param properties Teaser Properties
     * @return the responsible ActionHandler
     */
    public ActionHandler findHandlerFor(String actionType, String actionValue, Map<String,String> properties);


    /**
     * track clicks
     */
    public void trackClick(String actionType, String actionValue, Map<String,String> properties);
}
