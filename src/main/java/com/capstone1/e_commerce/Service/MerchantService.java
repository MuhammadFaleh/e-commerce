package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Merchant;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchants;

    public List<Merchant> getMerchants(){
        return merchants.findAll();
    }

    public boolean createMerchants(Merchant merchant){
        if(merchantExist(merchant.getId()) != null){
            return false; // merchant already exists
        }
        merchants.save(merchant);
        return true;
    }

    public boolean updateMerchant(Integer id, Merchant merchant){
        Merchant m = merchantExist(id);
        if(m != null){
            m.setName(merchant.getName());
            merchants.save(m);
        }
        return false; // merchant not found
    }

    public boolean deleteMerchant(Integer id){
        Merchant merchant = merchantExist(id);

        if(merchant!=null){
            merchants.delete(merchant);
            return true;
        }
        return false;
    }

    //logic
    public Merchant merchantExist(Integer id){
        if(id == null){
            return null;
        }
        return merchants.findAll().stream().filter(e-> e.getId().equals(id)).findFirst().orElse(null);
    }
}
