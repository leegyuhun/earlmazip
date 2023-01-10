package com.earlmazip.utils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionCheckingListner implements HttpSessionListener {
    static private int activeSessions = 0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
        System.out.println("sessionCreated se.getSession().getId() = " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions--;
        System.out.println("sessionDestroyed se.getSession().getId() = " + se.getSession().getId());
    }
}
