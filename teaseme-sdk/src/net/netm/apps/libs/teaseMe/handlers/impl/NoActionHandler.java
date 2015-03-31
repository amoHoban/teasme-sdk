package net.netm.apps.libs.teaseMe.handlers.impl;

import java.util.Map;

import net.netm.apps.libs.teaseMe.handlers.ActionHandler;

/**
 * Created by ahoban on 27.03.15.
 */
public class NoActionHandler implements ActionHandler {

    @Override
    public boolean canHandle(String actionType) {
        return false;
    }

    @Override
    public void handle(String actionType, String actionValue) {

    }

    @Override
    public void handle(String actionType, String actionValue, Map<String, String> options) {

    }
}
