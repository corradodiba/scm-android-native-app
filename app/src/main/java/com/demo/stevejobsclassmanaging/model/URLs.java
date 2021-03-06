package com.demo.stevejobsclassmanaging.model;

public class URLs {

    private static final String ROOT_URL = "http://192.168.43.156:3000";

    public static final String URL_LOGIN = ROOT_URL + "/auth/login";
    public static final String URL_SIGNUP= ROOT_URL + "/auth/signup";
    public static final String URL_USERS= ROOT_URL + "/users";
    public static final String URL_SUBJECTS= ROOT_URL + "/subjects";
    public static final String URL_COURSES= ROOT_URL + "/courses";
    public static final String URL_TEACHERS= URL_USERS + "?type=Teacher";
    public static final String URL_STUDENT= URL_USERS + "?type=Student";
    public static final String URL_ADMIN= URL_USERS + "?type=Admin";
}
