package com.oncredit.OnVacunas.utils.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ErrorMessages {

    private final String mensaje = "hola";
    
    public static String idNotFound(String entity){

       final String message = "No hay registros en la entidad %s con el id suministrado";
       return String.format(message, entity);
    }
}