package ra.project5.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.project5.model.dto.response.CommonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
//    public String handleExceptionUser(CustomException e){
//
//        return e.getMessage();
//    }
    public ResponseEntity<?> handleCustomException(CustomException e){
        return new ResponseEntity<>(new CommonResponse<String>(HttpStatus.BAD_REQUEST,e.getMessage()),HttpStatus
                .BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //String: Trường bị lỗi, String - Nội dung thông báo lỗi
    public Map<String, String> invalidRequest(MethodArgumentNotValidException ex) {
        //Khai báo map chứa tất cả các lỗi để trả về client
        Map<String, String> errors = new HashMap<>();
        //1. lấy ra và duyệt tất cả các exception ở trong bindingResult
        //Lấy tất cả các field không thỏa mãn điều kiện valid ở DTO
        List<FieldError> listFieldError = ex.getBindingResult().getFieldErrors();
        //Duyệt tất cả các fieldError add vào errors để trả về cho client
        listFieldError.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errors;
    }

}
