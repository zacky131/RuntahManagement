package com.resikin.percakapan.connectivity;

import java.util.Observable;

/**
 * Created by stefanodp91 on 14/09/17.
 */

//bugfix Issue #61
public class ObservableNetwork extends Observable {
    private static com.resikin.percakapan.connectivity.ObservableNetwork instance = new com.resikin.percakapan.connectivity.ObservableNetwork();

    public static com.resikin.percakapan.connectivity.ObservableNetwork getInstance() {
        return instance;
    }

    private ObservableNetwork() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}