package com.boot.board.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtils {
    private final HttpSession session;

    public SessionUtils(HttpSession session) {
        this.session = session;
    }

    public String getLoggedInUser() {
        return (String) session.getAttribute("loggedInUser");
    }
}
