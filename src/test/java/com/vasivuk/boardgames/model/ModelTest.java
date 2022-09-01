package com.vasivuk.boardgames.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public abstract class ModelTest<T> {

    @Autowired
    protected LocalValidatorFactoryBean validator;

    protected abstract void testWhenValidEntity(T entity);

    protected abstract void testWhenInvalidEntity(T entity);

}
