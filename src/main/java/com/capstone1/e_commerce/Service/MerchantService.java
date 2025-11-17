package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Merchant;
import com.capstone1.e_commerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean createMerchants(Merchant merchant){
        if(merchantExist(merchant.getId())){
            return false; // merchant already exists
        }
        merchants.add(merchant);
        return true;
    }

    public int updateMerchant(String id, Merchant merchant){
        if(!id.equalsIgnoreCase(merchant.getId())){
            return 0; //merchant id doesn't match entered id
        }
        for (int i = 0; i<merchants.size(); i++){
            if(id.equalsIgnoreCase(merchants.get(i).getId())){
                merchants.set(i, merchant);
                return 1;
            }
        }
        return 2; // merchant not found
    }

    public boolean deleteMerchant(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

    //logic
    public boolean merchantExist(String id){
        return merchants.stream().anyMatch(e-> e.getId().equalsIgnoreCase(id));
    }
}
