package net.sebinson.framework.message.transport.exception;

public class TransportTooMuchRequestException extends TransportException {
    private static final long serialVersionUID = 2894542241112459682L;

    public TransportTooMuchRequestException(String errorCode, String message) {
        super(errorCode, message);
    }
}
