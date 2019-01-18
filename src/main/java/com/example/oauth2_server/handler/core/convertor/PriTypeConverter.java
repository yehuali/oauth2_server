package com.example.oauth2_server.handler.core.convertor;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

public class PriTypeConverter extends BaseSimpleTypeConverter {
    private static final PriTypeConverter convertor;
    private static final char[] DIGITAL_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    static {
        convertor = new PriTypeConverter();
    }

    public static PriTypeConverter getInstance(){
        return convertor;
    }

    /**
     * 对基本类型进行类型转换
     */
    @Override
    protected Object doConvertValue(Object value, Class<?> toType, Object... params) {
        /**
         * 如果都是数组类型，则构造数组
         */
        if (value != null && value.getClass().isArray() && toType.isArray()) {
            Object result = null;
            Class<?> componentType = toType.getComponentType();
            result = Array.newInstance(componentType, Array.getLength(value));
            for (int i = 0; i < Array.getLength(value); i++) {
                Array.set(result, i, convertValue(Array.get(value, i),componentType, params));
            }
            return result;
        }

        Class<?> vaClass = value.getClass();
        if (value != null && (!PrimitiveType.isPriType(value.getClass()) || !PrimitiveType.isPriType(toType))) {
            return null;
        }
        return doConvert(value, toType);
    }


    private Object doConvert(Object value, Class<?> toType) {
        Object result = null;
        if (value != null) {
            if ((toType == Integer.class) || (toType == Integer.TYPE))
                result = (int) longValue(value);
            else if ((toType == Double.class) || (toType == Double.TYPE))
                result = doubleValue(value);
            else if ((toType == Boolean.class) || (toType == Boolean.TYPE))
                result = booleanValue(value);
            else if ((toType == Byte.class) || (toType == Byte.TYPE))
                result = (byte) longValue(value);
            else if ((toType == Character.class) || (toType == Character.TYPE))
                result = charValue(value);
            else if ((toType == Short.class) || (toType == Short.TYPE))
                result = (short) longValue(value);
            else if ((toType == Long.class) || (toType == Long.TYPE))
                result = longValue(value);
            else if ((toType == Float.class) || (toType == Float.TYPE))
                result = (float) doubleValue(value);
            else if (toType == BigInteger.class)
                result = bigIntValue(value);
            else if (toType == BigDecimal.class)
                result = bigDecValue(value);
            else if (toType == String.class)
                result = stringValue(value);

        }else {
            if (toType.isPrimitive()) {
                result = PrimitiveType.getPriDefaultValue(toType);
            }
        }

        return result;
    }

    private BigInteger bigIntValue(Object value) throws NumberFormatException {
        if (value == null){
            return BigInteger.valueOf(0L);
        }
        Class<? extends Object> c = value.getClass();
        if (c == BigInteger.class){
            return (BigInteger) value;
        }
        if (c == BigDecimal.class){
            return ((BigDecimal) value).toBigInteger();
        }
        if (c.getSuperclass() == Number.class){
            return BigInteger.valueOf(((Number) value).longValue());
        }
        if (c == Boolean.class){
            return BigInteger.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
        }
        if (c == Character.class){
            return BigInteger.valueOf(((Character) value).charValue());
        }

        String s = stringValue(value, true);
        return (s.length() == 0) ? BigInteger.valueOf(0L) : new BigInteger(s);
    }

    private BigDecimal bigDecValue(Object value) throws NumberFormatException {
        if (value == null){
            return BigDecimal.valueOf(0L);
        }
        Class<? extends Object> c = value.getClass();
        if (c == BigDecimal.class){
            return (BigDecimal) value;
        }
        if (c == BigInteger.class){
            return new BigDecimal((BigInteger) value);
        }
        if (c.getSuperclass() == Number.class){
            return new BigDecimal(((Number) value).doubleValue());
        }
        if (c == Boolean.class){
            return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
        }
        if (c == Character.class){
            return BigDecimal.valueOf(((Character) value).charValue());
        }

        String s = stringValue(value, true);
        return (s.length() == 0) ? BigDecimal.valueOf(0L) : new BigDecimal(s);
    }

    private char charValue(Object value) {
        char result;

        if (value.getClass() == String.class && ((String) value).length() > 0 && !isNumberString((String) value)){
            result = ((String) value).charAt(0);
        }else{
            result = (char) longValue(value);
        }

        return result;
    }

    private boolean isNumberString(String stringValue) {
        if (stringValue == null || stringValue.length() == 0){
            return false;
        }

        OUTTER: for (char charInString : stringValue.toCharArray()) {
            for (char digit : DIGITAL_CHAR) {
                if (charInString == digit){
                    continue OUTTER;
                }
            }
            return false;
        }
        return true;
    }

    private boolean booleanValue(Object value) {
        if (value == null){
            return false;
        }
        Class<? extends Object> c = value.getClass();
        if (c == Boolean.class){
            return (Boolean) value;
        }
        if (c == String.class) {
            String stringValue = (String) value;
            return !(stringValue.length() == 0
                    || stringValue.equals("0")
                    || stringValue.equalsIgnoreCase("false")
                    || stringValue.equalsIgnoreCase("no")
                    || stringValue.equalsIgnoreCase("f") || stringValue
                    .equalsIgnoreCase("n"));
        }
        if (c == Character.class){
            return ((Character) value).charValue() != 0;
        }
        if (value instanceof Number){
            return ((Number) value).doubleValue() != 0;
        }

        return true;
    }


    private double doubleValue(Object value) throws NumberFormatException {
        if (value == null){
            return 0.0;
        }
        Class<? extends Object> c = value.getClass();
        if (c.getSuperclass() == Number.class){
            return ((Number) value).doubleValue();
        }
        if (c == Boolean.class){
            return ((Boolean) value).booleanValue() ? 1 : 0;
        }
        if (c == Character.class){
            return ((Character) value).charValue();
        }
        String s = stringValue(value, true);
        return (s.length() == 0) ? 0.0 : Double.parseDouble(s);
    }

    private long longValue(Object value) throws NumberFormatException {
        if (value == null){
            return 0L;
        }
        Class<? extends Object> c = value.getClass();
        if (c.getSuperclass() == Number.class){
            return ((Number) value).longValue();
        }
        if (c == Boolean.class){
            return ((Boolean) value).booleanValue() ? 1 : 0;
        }
        if (c == Character.class){
            return ((Character) value).charValue();
        }
        String s = stringValue(value, true);
        return (s.length() == 0) ? 0L : Long.parseLong(s);
    }

    private String stringValue(Object value) {
        return stringValue(value, false);
    }

    private String stringValue(Object value, boolean trim) {
        String result;
        if (value == null) {
            result = null;
        }else {
            result = value.toString();
            if (trim) {
                result = result.trim();
            }
        }
        return result;
    }
}
