package net.netm.apps.libs.teaseme.handlers.impl;

import java.util.Map;

import net.netm.apps.libs.teaseme.handlers.ActionHandler;

/**
 * Created by ahoban on 27.03.15.
 */
public class NoActionHandler implements ActionHandler {

    @Override
    public boolean canHandle(String actionType, String actionValue, Map<String, String> options) {
        return true;
    }

    @Override
    public boolean handle(String actionType, String actionValue, Map<String, String> options) {
        return true;
    }
}
