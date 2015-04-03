package net.netm.apps.libs.teaseMe.handlers;

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
     * @return
     */
    public ActionHandler findHandlerFor(String actionType, String actionValue);

    /**
     * <p>register a handler</p>
     * @param type
     * @param handler
     */
    public void registerHandler(ActionType type, ActionHandler handler);

    /**
     * track clicks
     */
    public void trackClick(String actionType, String actionValue);
}
