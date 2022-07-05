package com.vasivuk.boardgames.configuration;

public class Routes {

    //Authentication
    public static final String REGISTER = "/api/register";
    public static final String LOGIN = "/api/login";
    public static final String LOGOUT = "/api/logout";
    public static final String REFRESH_TOKEN = "/api/token/refresh";

    //User
    public static final String USER_COMMON = "/api/users";

    //Category
    public static final String CATEGORY_COMMON = "/api/categories";

    //Product
    public static final String PRODUCT_COMMON = "/api/products";

    //Order
    public static final String ORDER_COMMON = "/api/users/{id}/orders";


    public static final String SPECIFIC = "/{id}";
    public static final String CREATE = "/create";
}
