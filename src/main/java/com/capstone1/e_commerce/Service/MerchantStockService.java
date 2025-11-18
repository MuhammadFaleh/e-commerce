package com.capstone1.e_commerce.Service;

import com.capstone1.e_commerce.Model.Category;
import com.capstone1.e_commerce.Model.MerchantStock;
import com.capstone1.e_commerce.Model.Product;
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

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;
    private final CategoryService categoryService;

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
            return -3; // product doesn't exist
        }
        return -2; // merchant doesn't exist

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
                return -4; // merchant stock id doesn't exist
            }
            return -3; // product doesn't exist
        }
        return -2; // merchant doesn't exist
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

    public Map<String, Integer> getTotalStockForProduct(String productId) {
        Map<String, Integer> productStocks = new HashMap<>();
        int total = 0;
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductID().equals(productId)) {
                total += stock.getStock();
            }
        }
        productStocks.put(productId, total);
        return productStocks;
    }

    public int stockHandle(String id, String productID, boolean buy){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getMerchantID().equalsIgnoreCase(id)){
                if(merchantStocks.get(i).getProductID().equalsIgnoreCase(productID)){
                if(buy){
                    if(merchantStocks.get(i).getStock() > 0){
                        merchantStocks.get(i).setStock(merchantStocks.get(i).getStock() - 1);
                        return 1;
                    } return -5; // not enough stock
                }else{
                    merchantStocks.get(i).setStock(merchantStocks.get(i).getStock() + 1);
                    return 1;
                    }
                } return -3; // no product found
            }
        }
        return -2; // no merchant found
    }

    //extra
    public ArrayList<MerchantStock> getLowStock(double range){
        ArrayList<MerchantStock> lowStocks = new ArrayList<>();
        for (MerchantStock merchantStock : merchantStocks){
            if( merchantStock.getStock() <= range) {
                lowStocks.add(merchantStock);
            }
        }
        return lowStocks;
    }

    public ArrayList<MerchantStock> getLowStockID(double range, String id){
        ArrayList<MerchantStock> lowStocks = new ArrayList<>();
        for (MerchantStock merchantStock : merchantStocks){
            if( merchantStock.getStock() <= range && merchantStock.getMerchantID().equalsIgnoreCase(id)) {
                lowStocks.add(merchantStock);
            }
        }
        return lowStocks;
    }

    public int addStock(String merchantStockID, int stock){
        if(stock <= 0){
            return -1;
        }
        for (int i = 0; i < merchantStocks.size() ; i++) {
            if(merchantStocks.get(i).getId().equalsIgnoreCase(merchantStockID)){
                merchantStocks.get(i).setStock(merchantStocks.get(i).getStock() + stock);
                return 1;
            }
        }
        return -2;
    }

    public Map<String, Integer> getCategoryDistribution(){
        Map<String, Integer> distribution = new HashMap<>();

        ArrayList<Product> products = productService.getProducts();

        for(Product product: products){
            Category category = categoryService.getCategoryByID(product.getCategoryID());
            int totalStock = getTotalStockForProduct(product.getId()).get(product.getId());
            distribution.put(category.getName(), distribution.getOrDefault(category.getName(), 0) + totalStock);
        }
        return distribution;
    }

    public Map<String, Double> getStockSummary(String merchantID){
        Map<String, Double> invSummary = new HashMap<>();
        List<MerchantStock> matchedStock =merchantStocks.stream()
                .filter(e->e.getMerchantID().equalsIgnoreCase(merchantID)).toList();
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
