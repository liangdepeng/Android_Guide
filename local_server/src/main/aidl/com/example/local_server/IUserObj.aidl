// IUserObj.aidl
package com.example.local_server;
parcelable UserObj;

// Declare any non-default types here with import statements

interface IUserObj {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

}