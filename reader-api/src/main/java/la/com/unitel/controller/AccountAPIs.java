package la.com.unitel.controller;

import la.com.unitel.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("account")
public interface AccountAPIs {

    @GetMapping("{accountId}")
    ResponseEntity<?> viewAccountDetail(@PathVariable String accountId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);
}
