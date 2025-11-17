package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Merchant;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public int createMerchantStock(MerchantStock merchantStock){
        if(merchantStockExist(merchantStock.getId())){
            return 0; // Merchant stock already exists
        }
        if(merchantService.merchantExist(merchantStock.getMerchantID())){
            if(productService.productExist(merchantStock.getProductID()) != null){
                merchantStocks.add(merchantStock);
                return 1;
            }
            return 3; // product doesn't exist
        }
        return 2; // merchant doesn't exist

    }

    public int updateMerchantStock(String id, MerchantStock merchantStock){
        if(!id.equalsIgnoreCase(merchantStock.getId())){
            return 0; // merchantStock id doesn't match entered id
        }
        if(merchantService.merchantExist(merchantStock.getMerchantID())){
            if(productService.productExist(merchantStock.getProductID()) != null){
                for (int i = 0; i<merchantStocks.size(); i++){
                    if(id.equalsIgnoreCase(merchantStocks.get(i).getId())){
                        merchantStocks.set(i, merchantStock);
                        return 1;
                    }
                }
                return 2; // merchant stock id doesn't exist
            }
            return 3; // product doesn't exist
        }
        return 4; // merchant doesn't exist
    }

    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    //logic
    public boolean merchantStockExist(String id) {
        return merchantStocks.stream().anyMatch(e-> e.getId().equalsIgnoreCase(id));
    }


    public int stockRemains(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getMerchantID().equalsIgnoreCase(id)){
                if(merchantStocks.get(i).getStock() > 0){
                    merchantStocks.get(i).setStock(merchantStocks.get(i).getStock() - 1);
                    return 1;
                }
                return 5; // not enough stock
            }
        }
        return 2; // no merchant found
    }

    //extra
    public ArrayList<MerchantStock> getLowStock(){
        ArrayList<MerchantStock> lowStocks = new ArrayList<>();
        for (MerchantStock merchantStock : merchantStocks){
            if( merchantStock.getStock() == 0 ) {
                lowStocks.add(merchantStock);
            }
        }
        return lowStocks;
    }

}
