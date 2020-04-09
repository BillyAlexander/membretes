package ec.com.wolfdev.lembretes.core.base.valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Field validation error");
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getObjectName(),fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    static class Error {
        private @Getter final int status;
        private @Getter final Boolean isCustom = true;
        private @Getter final String error;
        private @Getter  List<ErrorContent> fieldErrors = new ArrayList<>();

        Error(int status, String error) {
            this.status = status;
            this.error = error;
        }

        public void addFieldError(String name, String field, String message) {
        	ErrorContent error = new ErrorContent(name, field, message);
            fieldErrors.add(error);
        }
    }
    
    @AllArgsConstructor
    static class ErrorContent {        
        private @Getter final String objectName;
        private @Getter final String field;
        private @Getter final String defaultMessage;
    }
}
