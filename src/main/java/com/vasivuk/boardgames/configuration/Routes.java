package com.vasivuk.boardgames.configuration;

public class Routes {

    //Authentication
    public static final String REGISTER = "/api/register";
    public static final String LOGIN = "/api/login";
    public static final String REFRESH_TOKEN = "/api/token/refresh";

    //User
    public static final String USER_COMMON = "/api/users";
    public static final String ASSIGN_ADMIN = "/assignAdmin";

    //Category
    public static final String CATEGORY_COMMON = "/api/categories";

    //Product
    public static final String PRODUCT_COMMON = "/api/products";

    //Order


    public static final String ID = "/{id}";
    public static final String CREATE = "/create";
    public static final String NAME = "/name";

    public static final String[] PUBLIC_ROUTES = {
            LOGIN,
            REFRESH_TOKEN,
            REGISTER,
//            PRODUCT_COMMON,
//            PRODUCT_COMMON + NAME,
//            PRODUCT_COMMON + ID,
            CATEGORY_COMMON,
            CATEGORY_COMMON + NAME,
            CATEGORY_COMMON + ID
    };

    public static final String[] ADMIN_POST_PUT_DELETE_ROUTES = {
            CATEGORY_COMMON + CREATE,
            CATEGORY_COMMON + NAME,
            CATEGORY_COMMON + ID,
            PRODUCT_COMMON + CREATE,
            PRODUCT_COMMON + NAME,
            PRODUCT_COMMON + ID
    };
}
