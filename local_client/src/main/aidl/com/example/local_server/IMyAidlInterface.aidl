// IMyAidlInterface.aidl
package com.example.local_server;

// Declare any non-default types here with import statements
import com.example.local_server.IAidlCallback;
import com.example.local_server.IUserObj;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setMsg(String message);

    String getServiceMsg();

    void setUserObj(IUserObj obj);

    IUserObj getUserObj();

    void setCallback(IAidlCallback callback);

    void removeCallback(IAidlCallback callback);
}