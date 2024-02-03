package com.XAUS.Managers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Utility interface for logging
 */
public interface LogManager {

    public static void logInfo(Class currentClass, String message){
        Logger LOG = LoggerFactory.getLogger(currentClass);
        LOG.info(message);
    }

    public static void logError(Class currentClass, String message, Exception e ){
        Logger LOG = LoggerFactory.getLogger(currentClass);
        LOG.error(message + " Error: " + e.getMessage());
    }

    public static void logWarn(Class currentClass, String message){
        Logger LOG = LoggerFactory.getLogger(currentClass);
        LOG.warn(message);
    }

}
