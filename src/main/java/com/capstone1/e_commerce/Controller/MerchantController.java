package com.capstone1.e_commerce.Controller;

import com.capstone1.e_commerce.Api.ApiResponse;
import com.capstone1.e_commerce.Model.Merchant;
import com.capstone1.e_commerce.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    // CRUD
    @GetMapping("/get-merchants")
    public ResponseEntity<?> getMerchants(){
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        if(merchants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("please enter merchants to show them"));
        }
        return ResponseEntity.status(200).body(merchants);
    }

    @PostMapping("/create-merchant")
    public ResponseEntity<?> createMerchants(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if(merchantService.createMerchants(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant already exist"));
    }

    @PutMapping("/update-merchant/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantService.updateMerchant(id, merchant)) {
            return ResponseEntity.status(200).body(new ApiResponse("merchant updated successfully"));
        }return ResponseEntity.status(400).body(new ApiResponse("merchant id doesn't exist"));
    }

    @DeleteMapping("/delete-merchant/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if(merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant id doesn't exist"));
    }

    //extra

}
