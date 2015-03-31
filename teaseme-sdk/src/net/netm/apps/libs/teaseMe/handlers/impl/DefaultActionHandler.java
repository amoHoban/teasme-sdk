package net.netm.apps.libs.teaseMe.handlers.impl;

import java.util.Map;

import net.netm.apps.libs.teaseMe.handlers.ActionHandler;
import net.netm.apps.libs.teaseMe.handlers.ActionType;

/**
 * Created by ahoban on 27.03.15.
 */
public class DefaultActionHandler implements ActionHandler {

    @Override
    public boolean canHandle(String actionType) {
        return !ActionType.Link.name().equalsIgnoreCase(actionType);

    }

    @Override
    public void handle(String actionType, String actionValue) {
        // do nothing
    }

    @Override
    public void handle(String actionType, String actionValue, Map<String, String> options) {

    }
}
