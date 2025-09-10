package com.bankle.common.exception;


import com.bankle.common.util.ErrorSaveUtil;
import com.google.protobuf.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @Package     : com.withuslaw.common.exception
 * @name        : GlobalAdviceController.java
 * @date        : 2023/09/14 1:51 PM
 * @author      : tigerBK
 * @version     : 1.0.0
**/
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalAdviceController {
    
    private final ErrorSaveUtil errorSaveUtil;
    
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<Object> handleServiceException(HttpServletRequest request, ServiceException ex) {
        log.debug("###################### GlobalAdviceController() > ServiceException !! Start !!");
        log.debug(ex.getMessage());
        ex.printStackTrace();
        log.debug("###################### GlobalAdviceController() > ServiceException !! End !!");

       // errorSaveUtil.errorSave( request,  ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value()),  ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR ));
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        log.debug("###################### GlobalAdviceController() > Exception !! Start !!");
        log.debug(ex.getMessage());
        log.debug("###################### GlobalAdviceController() > Exception !! End !!");

      //  errorSaveUtil.errorSave( request,  ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value()),  ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR ));
    }
    
    /**
     * try catch에서 Exception 인 경우
     * @name        : GlobalAdviceController.defaultException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = DefaultException.class)
    protected ResponseEntity<ErrorResponse> defaultException(HttpServletRequest request,DefaultException e) {
        log.debug("###################### GlobalAdviceController() > defaultException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > defaultException !! End !!");

       // errorSaveUtil.errorSave(request, e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value()),  e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR ));
    }

    /**
     * 비즈니스 오류는 모두 여기로
     * @name        : GlobalAdviceController.badRequestException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<ErrorResponse> badRequestException(HttpServletRequest request,BadRequestException e) {
        log.debug("###################### GlobalAdviceController() > BadRequestException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > BadRequestException !! End !!");

     //   errorSaveUtil.errorSave(request, e);
        return ResponseEntity.badRequest().body(new ErrorResponse(String.valueOf( HttpStatus.BAD_REQUEST.value()), e.getMessage() , HttpStatus.BAD_REQUEST ));
    }


    /**
     * not found
     * @name        : GlobalAdviceController.notFoundException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ErrorResponse> notFoundException(HttpServletRequest request,NotFoundException e) {
        log.debug("###################### GlobalAdviceController() > NotFoundException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > NotFoundException !! End !!");
      //  errorSaveUtil.errorSave(request, e);
        return ResponseEntity.badRequest().body(new ErrorResponse( String.valueOf( HttpStatus.NOT_FOUND.value()), e.getMessage() , HttpStatus.NOT_FOUND ));
    }


    /**
     * 인증 오류인경우
     * @name        : GlobalAdviceController.unAuthException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = UnAuthException.class)
    protected ResponseEntity<ErrorResponse> unAuthException(HttpServletRequest request,UnAuthException e) {
        log.debug("###################### GlobalAdviceController() > UnAuthException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > UnAuthException !! End !!");
      //  errorSaveUtil.errorSave(request, e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(String.valueOf( HttpStatus.UNAUTHORIZED.value()), e.getMessage() , HttpStatus.UNAUTHORIZED));
    }


    /**
     * 파일
     * @name        : GlobalAdviceController.fileNotFoundException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = FileNotFoundException.class)
    protected ResponseEntity<ErrorResponse> fileNotFoundException(HttpServletRequest request,FileNotFoundException e) {
        log.debug("###################### GlobalAdviceController() > FileNotFoundException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > FileNotFoundException !! End !!");
    //    errorSaveUtil.errorSave(request, e);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(String.valueOf( HttpStatus.NOT_FOUND.value()), e.getMessage() , HttpStatus.NOT_FOUND ));
     }


    /**
     * 파일
     * @name        : GlobalAdviceController.fileStorageException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(value = FileStorageException.class)
    protected ResponseEntity<ErrorResponse> fileStorageException(HttpServletRequest request,FileStorageException e) {
        log.debug("###################### GlobalAdviceController() > FileStorageException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > FileStorageException !! End !!");
     //  errorSaveUtil.errorSave(request, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR ));
    }


    /**
     * vo, dto validate error
     * @name        : GlobalAdviceController.processValidationException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationException(HttpServletRequest request,MethodArgumentNotValidException e) {
        log.debug("###################### GlobalAdviceController() > MethodArgumentNotValidException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > MethodArgumentNotValidException !! End !!");

     //   errorSaveUtil.errorSave(request, e);
        return ResponseEntity.badRequest().body(new ErrorResponse(String.valueOf( HttpStatus.BAD_REQUEST.value()), e.getBindingResult().getAllErrors().get(0).getDefaultMessage() , HttpStatus.BAD_REQUEST));
    }

    /**
     * vo, dto validate error
     * @name        : GlobalAdviceController.processUnexpectedTypeException
     * @author      : tigerBK
     * @param       :
     * @return      :
    **/
    @ExceptionHandler(UnexpectedTypeException.class)
    public Object processUnexpectedTypeException(HttpServletRequest request,UnexpectedTypeException e) {
        log.debug("###################### GlobalAdviceController() > UnexpectedTypeException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > UnexpectedTypeException !! End !!");
   //     errorSaveUtil.errorSave(request, e);
        return ResponseEntity.badRequest().body(new ErrorResponse( String.valueOf( HttpStatus.BAD_REQUEST.value()), e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    /**
     * 중복 로그인 예외 처리
     */
    @ExceptionHandler(value = DuplicateLoginException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateLoginException(HttpServletRequest request, DuplicateLoginException e) {
        log.debug("###################### GlobalAdviceController() > defaultException !! Start !!");
        log.debug(e.getMessage());
        e.printStackTrace();
        log.debug("###################### GlobalAdviceController() > defaultException !! End !!");

        // 에러 로깅 등 추가 처리 가능
        return ResponseEntity.status(HttpStatus.VARIANT_ALSO_NEGOTIATES).body(new ErrorResponse(String.valueOf(HttpStatus.VARIANT_ALSO_NEGOTIATES.value()), e.getMessage(), HttpStatus.VARIANT_ALSO_NEGOTIATES));
    }


}
