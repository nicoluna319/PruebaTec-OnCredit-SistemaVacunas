package com.oncredit.OnVacunas.utils.exception;

public class BadRequestException 
    extends RuntimeException {
    
        public BadRequestException(String message){
            super(message);
        }
}