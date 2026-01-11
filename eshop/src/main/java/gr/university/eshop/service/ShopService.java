package gr.university.eshop.service;

import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.entity.Shop;
import gr.university.eshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public void registerShop(ShopRegisterDto dto) throws Exception {
        if (shopRepository.existsById(dto.getAfm())) {
            throw new Exception("An e-shop with this tax ID already exists!");
        }

        Shop shop = new Shop();
        shop.setAfm(dto.getAfm());
        shop.setCompanyName(dto.getCompanyName());
        shop.setOwner(dto.getOwner());
        shop.setPassword(dto.getPassword());

        shopRepository.save(shop);
    }

    // --- LOGIN SHOP ---
    public Shop login(String afm, String password) throws Exception {
        Optional<Shop> shop = shopRepository.findById(afm);

        if (shop.isPresent() && shop.get().getPassword().equals(password)) {
            return shop.get();
        } else {
            throw new Exception("Wrong tax ID or password!");
        }
    }
}