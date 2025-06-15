package de.dhbw.utils.result;

public abstract class Result<T> {
    public static <T> Result<T> ok() {
        return new Ok<>(null);
    }

    public static <T> Result<T> ok(T value) {
        return new Ok<>(value);
    }

    public static <T> Result<T> error(Exception e) {
        return new Error<>(e);
    }

    public static final class Ok<T> extends Result<T> {
        private final T value;

        public Ok(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public boolean hasValue() {
            return value != null;
        }
    }

    public static final class Error<T> extends Result<T> {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}