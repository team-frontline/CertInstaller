package org.frontline.certInstaller.utils;

public class InvalidCSRParamException extends Exception {
    public InvalidCSRParamException(String message){
        super(message);
    }
}
