package com.capstone1.e_commerce.Controller;

import com.capstone1.e_commerce.Api.ApiResponse;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    // CRUD
    @GetMapping("/get-stock")
    public ResponseEntity<?> getStock(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        if(merchantStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("please enter merchant stocks to show them"));
        }
        return ResponseEntity.status(200).body(merchantStocks);
    }

    @PostMapping("/create-stock")
    public ResponseEntity<?> createStock(@RequestBody @Valid MerchantStock merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        int status = merchantStockService.createMerchantStock(merchant);
        switch (status){
            case 1-> {return ResponseEntity.status(200).body(new ApiResponse("stock created successfully"));}
            case -2->{return ResponseEntity.status(400).body(new ApiResponse("merchant doesn't exist"));}
            case -3->{return ResponseEntity.status(400).body(new ApiResponse("product doesn't exist"));}
            default -> {return ResponseEntity.status(400).body(new ApiResponse("merchant already exist"));}
        }
    }

    @PutMapping("/update-stock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
            int status = merchantStockService.updateMerchantStock(id, merchant);
            switch (status){
                case 1-> {return ResponseEntity.status(200).body(new ApiResponse("stock updated successfully"));}
                case -2->{return ResponseEntity.status(400).body(new ApiResponse("merchant doesn't exist"));}
                case -3->{return ResponseEntity.status(400).body(new ApiResponse("product doesn't exist"));}
                case -4->{return ResponseEntity.status(400).body(new ApiResponse("stock id doesn't exist"));}
                default -> {return ResponseEntity.status(400).body(new ApiResponse("look up id doesn't match stock id"));}
            }
    }

    @DeleteMapping("/delete-stock/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable String id){
        if(merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new ApiResponse("stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id doesn't exist"));
    }

    //extra
    @GetMapping("/get-low-stock/{threshold}")
    public ResponseEntity<?> getLowStock(@PathVariable int threshold){
        ArrayList<MerchantStock> matchedStocks = merchantStockService.getLowStock(threshold);
        if(matchedStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no items below the entered threshold"));
        }
        return ResponseEntity.status(200).body(matchedStocks);
    }

    @GetMapping("/get-low-stock-id/{threshold}/{id}")
    public ResponseEntity<?> getLowStockID(@PathVariable int threshold, @PathVariable String id){
        ArrayList<MerchantStock> matchedStocks = merchantStockService.getLowStockID(threshold, id);
        if(matchedStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no items below the entered threshold"));
        }
        return ResponseEntity.status(200).body(matchedStocks);
    }

    @GetMapping("/get-category-distribution")
    public ResponseEntity<?> getCategoryDistribution(){
        return ResponseEntity.status(200).body(merchantStockService.getCategoryDistribution());
    }

    @GetMapping("/get-stock-summary/{id}")
    public ResponseEntity<?> getStockSummary(@PathVariable String id){
        return ResponseEntity.status(200).body(merchantStockService.getStockSummary(id));
    }

    @GetMapping("/get-product-stock/{id}")
    public ResponseEntity<?> getProductStock(@PathVariable String id){
        return ResponseEntity.status(200).body(merchantStockService.getTotalStockForProduct(id));
    }

    @PutMapping("/add-stock/{id}/{stock}")
    public ResponseEntity<?> addStock(@PathVariable String id,@PathVariable int stock) {
        int status = merchantStockService.addStock(id, stock);

        switch (status){
            case 1-> {return ResponseEntity.status(200).body(new ApiResponse("stock updated successfully"));}
            case -2->{return ResponseEntity.status(400).body(new ApiResponse("merchant doesn't exist"));}
            default -> {return ResponseEntity.status(400).body(new ApiResponse("stock can not be zero or lower"));}
        }
    }


}
