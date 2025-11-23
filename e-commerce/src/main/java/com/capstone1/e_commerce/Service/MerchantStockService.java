package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Model.Product;
import com.capstone1.e_commerce.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantStockRepository merchantStocks;
    private final MerchantService merchantService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public List<MerchantStock> getMerchantStocks(){
        return merchantStocks.findAll();
    }

    public int createMerchantStock(MerchantStock merchantStock){
        if(merchantStockExist(merchantStock.getId()) != null){
            return 0; // Merchant stock already exists
        }
        if(merchantService.merchantExist(merchantStock.getMerchantID()) != null){
            if(productService.productExist(merchantStock.getProductID()) != null){
                merchantStocks.save(merchantStock);
                return 1;
            }
            return -3; // product doesn't exist
        }
        return -2; // merchant doesn't exist

    }

    public int updateMerchantStock(Integer id, MerchantStock merchantStock){
        if(merchantService.merchantExist(merchantStock.getMerchantID()) != null){
            if(productService.productExist(merchantStock.getProductID()) != null){
                MerchantStock ms = merchantStockExist(id);
                if(ms!=null){
                    merchantStocks.delete(ms);
                }
                return -4; // merchant stock id doesn't exist
            }
            return -3; // product doesn't exist
        }
        return -2; // merchant doesn't exist
    }

    public boolean deleteMerchantStock(Integer id){
        MerchantStock merchantStock = merchantStockExist(id);
        if (merchantStock!=null){
            merchantStocks.delete(merchantStock);
        }
        return false;
    }

    //logic
    public MerchantStock merchantStockExist(Integer id) {
        return merchantStocks.findAll().stream().filter(e-> e.getId().equals(id)).findFirst().orElse(null);
    }

    public Map<String, Integer> getTotalStockForProduct(Integer productId) {
        Map<String, Integer> productStocks = new HashMap<>();
        int total = 0;
        for (MerchantStock stock : merchantStocks.findAll()) {
            if (stock.getProductID().equals(productId)) {
                total += stock.getStock();
            }
        }
        Product product = productService.getProducts().stream().filter(e->e.getId().equals(productId)).findFirst().orElse(null);
        if(product != null) {
            productStocks.put(product.getName(), total);
        }
        return productStocks;
    }

    public int stockHandle(Integer id, Integer productID, boolean buy){
        MerchantStock merchantStock = merchantStockExist(id);
        if(merchantStock != null){
            if(merchantStock.getProductID().equals(productID)){
                if(buy){
                    if (merchantStock.getStock() > 0){
                        merchantStock.setStock(merchantStock.getStock() - 1);
                        merchantStocks.save(merchantStock);
                        return 1;
                    }
                    return -5;
                }else {
                    merchantStock.setStock(merchantStock.getStock() + 1);
                    return 1;
                }
            }
            return -3;
        }
        return -2;

    }

    //extra
    public ArrayList<MerchantStock> getLowStock(double range){
        ArrayList<MerchantStock> lowStocks = new ArrayList<>();
        for (MerchantStock merchantStock : merchantStocks.findAll()){
            if( merchantStock.getStock() <= range) {
                lowStocks.add(merchantStock);
            }
        }
        return lowStocks;
    }

    public ArrayList<MerchantStock> getLowStockID(double range, Integer id){
        ArrayList<MerchantStock> lowStocks = new ArrayList<>();
        for (MerchantStock merchantStock : merchantStocks.findAll()){
            if( merchantStock.getStock() <= range && merchantStock.getMerchantID().equals(id)) {
                lowStocks.add(merchantStock);
            }
        }
        return lowStocks;
    }

    public int addStock(Integer merchantStockID, int stock){
        if(stock <= 0){
            return -1;
        }
        MerchantStock merchantStock = merchantStockExist(merchantStockID);
        if(merchantStock != null){
            merchantStock.setStock(merchantStock.getStock() + stock);
            merchantStocks.save(merchantStock);
            return 1;
        }
        return -2;
    }

    public Map<String, Integer> getCategoryDistribution(){
        Map<String, Integer> distribution = new HashMap<>();

        List<Product> products = productService.getProducts();

        for(Product product: products){
            Category category = categoryService.getCategoryByID(product.getCategoryID());
            int totalStock = getTotalStockForProduct(product.getId()).get(product.getId());
            distribution.put(category.getName(), distribution.getOrDefault(category.getName(), 0) + totalStock);
        }
        return distribution;
    }

    public Map<String, Double> getStockSummary(Integer merchantID){
        Map<String, Double> invSummary = new HashMap<>();
        List<MerchantStock> matchedStock =merchantStocks.findAll().stream()
                .filter(e->e.getMerchantID().equals(merchantID)).toList();
        double totalProducts = 0.0;
        double totalStock = 0.0;
        double productsOutStock = 0.0;
        double productsInStock = 0.0;
        double averageStockPerProduct = 0.0;
        double minStock = 0.0;
        double maxStock = 0.0;

        if(!matchedStock.isEmpty()){
            totalProducts = matchedStock.size();
            totalStock = matchedStock.stream().mapToInt(MerchantStock::getStock).sum();
            productsOutStock = matchedStock.stream().filter(e-> e.getStock() == 0).count();
            productsInStock = totalProducts - productsOutStock;
            averageStockPerProduct = totalStock / totalProducts;
            minStock = matchedStock.stream().mapToInt(MerchantStock::getStock).min().orElse(0);
            maxStock = matchedStock.stream().mapToInt(MerchantStock::getStock).max().orElse(0);
        }


        invSummary.put("totalProducts", totalProducts);
        invSummary.put("totalStock", totalStock);
        invSummary.put("productsInStock", productsInStock);
        invSummary.put("productsOutOfStock", productsOutStock);
        invSummary.put("averageStockPerProduct", averageStockPerProduct);
        invSummary.put("minStock", minStock);
        invSummary.put("maxStock", maxStock);
        return invSummary;
    }

}
