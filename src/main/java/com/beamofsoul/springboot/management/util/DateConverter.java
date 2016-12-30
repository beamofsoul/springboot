package com.beamofsoul.springboot.management.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateConverter implements Converter {

    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DATETIME_PATTERN_NO_SECOND = "yyyy-MM-dd HH:mm";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String MONTH_PATTERN = "yyyy-MM";

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convert(Class type, Object value) {
        Object result = null;
        
        if (type.equals(Date.class)) {
            try {
                result = doConvertToDate(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals(String.class)) {
            result = doConvertToString(value);
        }
        return result;
    }

    /**
     * Convert String to Date
     */
    private Date doConvertToDate(Object value) throws ParseException {
        Date result = null;

        if (value instanceof String) {
            result = DateUtils.parseDate((String) value, new String[]{DATE_PATTERN, DATETIME_PATTERN,
                    DATETIME_PATTERN_NO_SECOND, MONTH_PATTERN});

            // all patterns failed, try a milliseconds constructor
            if (result == null && StringUtils.isNotEmpty((String) value)) {

                try {
                    result = new Date(new Long((String) value).longValue());
                } catch (Exception e) {
                	log.error("Converting from milliseconds to Date fails!");
                    e.printStackTrace();
                }

            }

        } else if (value instanceof Object[]) {
            // let's try to convert the first element only
            Object[] array = (Object[]) value;

            if (array.length >= 1) {
                value = array[0];
                result = doConvertToDate(value);
            }

        } else if (Date.class.isAssignableFrom(value.getClass())) {
            result = (Date) value;
        }
        return result;
    }

    /**
     * Convert Date to String
     */
    private String doConvertToString(Object value) {
        return value instanceof Date ? new SimpleDateFormat(DATETIME_PATTERN).format(value) : null;
    }

}
