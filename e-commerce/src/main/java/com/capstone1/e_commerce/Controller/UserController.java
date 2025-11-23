package com.capstone1.e_commerce.Controller;

import com.capstone1.e_commerce.Api.ApiResponse;
import com.capstone1.e_commerce.Model.User;
import com.capstone1.e_commerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // CRUD
    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers(){
        List<User> users = userService.getUsers();
        if(users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("please enter users to show them"));
        }
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if(userService.createUser(user)){
            return ResponseEntity.status(200).body(new ApiResponse("user created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user already exist"));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("user updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id doesn't exist"));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("user deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id doesn't exist"));
    }

    // extra
    @PutMapping("/buy-product/{id}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProduct(@PathVariable Integer id, @PathVariable Integer productID,
                                        @PathVariable Integer merchantID){
        int status = userService.buyProduct(productID, merchantID, id);
        if(status == 1){
            return ResponseEntity.status(200).body(new ApiResponse("item bought successfully"));
        }else if(status == -5){
            return ResponseEntity.status(400).body(new ApiResponse("item out of stock"));
        }else if(status == -1){
            return ResponseEntity.status(400).body(new ApiResponse("not enough balance to buy the item"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("one of the entered ids was not found"));
    }

    @PutMapping("/add-balance/{id}/{balance}")
    public ResponseEntity<?> addBalance(@PathVariable Integer id, @PathVariable double balance){
        int status = userService.addBalance(id, balance);
        if(status == -1){
            return ResponseEntity.status(400).body(new ApiResponse("balance can not be 0 or less"));
        }
        if(status == 1){
            return ResponseEntity.status(200).body(new ApiResponse("balance added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("failed to add balance unable to find the user"));
    }

    @PutMapping("/return-product/{id}/{productID}/{merchantID}")
    public ResponseEntity<?> returnProduct(@PathVariable Integer id, @PathVariable Integer productID,
                                        @PathVariable Integer merchantID){
        int status = userService.returnProduct(productID, merchantID, id);
        if(status == 1){
            return ResponseEntity.status(200).body(new ApiResponse("item returned successfully"));
        }return ResponseEntity.status(400).body(new ApiResponse("one of the entered ids was not found"));
    }

}
