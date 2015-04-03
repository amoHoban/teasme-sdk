package net.netm.apps.libs.teaseme.utils;

import android.os.AsyncTask;

import java.util.concurrent.Callable;

/**
 * AsynkTaskHelper that can be called with a {@link java.util.concurrent.Callable Callable} as callback
 * <p/>
 * Created by ahoban on 30.03.15.
 */
public class BasicAsynkTask<T> extends AsyncTask<String, T, T> {

    private final Callable<T> callback;

    /**
     * @param callable executed in every case
     */
    public BasicAsynkTask(Callable<T> callable) {
        this.callback = callable;
    }

    @Override
    protected T doInBackground(String... params) {
        try {
            return callback.call();
        } catch (Exception e) {
            return null;
        }
    }
}
