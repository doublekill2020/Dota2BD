package com.badr.infodota.util;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:38
 */
public interface Updatable<T> {
    void onUpdate(T entity);
}
