package com.schedule_planner.util.secret;

public interface Encrypt {
    String hashPassword(String password);

    boolean checkPassword(String password, String hashed);
}
