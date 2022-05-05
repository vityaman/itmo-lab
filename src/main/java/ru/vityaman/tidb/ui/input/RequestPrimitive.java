package ru.vityaman.tidb.ui.input;

import java.util.Arrays;

import ru.vityaman.tidb.ui.out.Out;

public final class RequestPrimitive {
    public interface Setter<T> {
        void take(T value) throws Exception;
    }

    public static abstract class Pattern<T> {
        String prefix() { return ""; }
        String pretify(String string) { return string; }
        String errorMessage(Exception e) { return e.getMessage(); };
        protected abstract T convert(String string) throws Exception;
    }

    private final Input in;
    private final Out out;

    public RequestPrimitive(Input in, Out out) {
        this.in = in;
        this.out = out;
    }    

    public <T> void request(Pattern<T> pattern, Setter<T> setter) {
        while (true) {
            try {
                out.print(pattern.prefix());
                setter.take(
                    pattern.convert(
                        pattern.pretify(
                            in.readLine()
                        )
                    )
                );
                break;
            } catch (Exception e) {
                out.error(
                    "Invalid input: " + pattern.errorMessage(e)
                );
            }
        }
    }

    public static RequestPrimitive.Pattern<Double> doubleFor(String fieldName) {
        return new TrimmedField<Double>(fieldName)  {
            @Override
            protected Double convert(String string) throws Exception {
                return Double.parseDouble(string);
            }

            @Override
            String errorMessage(Exception e) {
                return "can't parse double";
            }
        };
    }

    public static RequestPrimitive.Pattern<Float> floatFor(String fieldName) {
        return new TrimmedField<Float>(fieldName)  {
            @Override
            protected Float convert(String string) throws Exception {
                return Float.parseFloat(string);
            }

            @Override
            String errorMessage(Exception e) {
                return "can't parse float";
            }
        };
    }

    public static RequestPrimitive.Pattern<String> stringFor(String fieldName) {
        return new TrimmedField<String>(fieldName)  {
            @Override
            protected String convert(String string) throws Exception {
                return string;
            }
        };
    }

    public static RequestPrimitive.Pattern<Integer> intFor(String fieldName) {
        return new TrimmedField<Integer>(fieldName)  {
            @Override
            protected Integer convert(String string) throws Exception {
                return Integer.parseInt(string);
            }

            @Override
            String errorMessage(Exception e) {
                return "can't parse integer";
            }
        };
    }

    public static <T extends Enum<T>> RequestPrimitive.Pattern<T> enumFor(
        String fieldName, Class<T> enumeration
    ) {
        return new TrimmedField<T>(fieldName)  {
            @Override
            protected T convert(String string) throws Exception {
                return Enum.valueOf(enumeration, string.toUpperCase());
            }

            @Override
            String errorMessage(Exception e) {
                return "must be one of " +
                    Arrays.toString(enumeration.getEnumConstants());
            }
        };
    }

    public static abstract class TrimmedField<T> 
    extends RequestPrimitive.Pattern<T> {
        private final String fieldName;
        TrimmedField(String fieldName) {
            this.fieldName = fieldName;
        }
        @Override
        String prefix() {
            return fieldName + ": ";
        }
        @Override
        String pretify(String string) {
            return string.trim();
        }
    }
}
