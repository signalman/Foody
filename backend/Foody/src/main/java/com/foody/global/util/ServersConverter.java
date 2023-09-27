package com.foody.global.util;

import com.opencsv.bean.AbstractBeanField;

public class ServersConverter extends AbstractBeanField<String, Integer> {

    @Override
    protected Object convert(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return (int) Float.parseFloat(value);
        }
    }
}
