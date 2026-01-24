package gr.university.eshop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gr.university.eshop.dto.CheckoutResponseDto;
import gr.university.eshop.dto.OrderDto;
import gr.university.eshop.dto.OrderItemDto;
import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Order;
import gr.university.eshop.model.OrderItem;
import gr.university.eshop.model.Product;
import gr.university.eshop.repository.CartItemRepository;
import gr.university.eshop.repository.CartRepository;
import gr.university.eshop.repository.OrderRepository;
import gr.university.eshop.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired 
    private ProductRepository productRepo;

    @Autowired 
    private CartService cartService;

    @Transactional
    public CheckoutResponseDto checkout(Citizen citizen) throws Exception{
        
        Cart cart = cartRepo.findByCitizen_Afm(citizen.getAfm())
            .orElseThrow(() -> new Exception("Το καλάθι δεν βρέθηκε."));
        
        List<CartItem> temp = cartItemRepo.findByCart_Id(cart.getId());
        if(temp.isEmpty()) {
            throw new Exception("Το καλάθι είναι άδειο.");
        }

        Order order = new Order();
        order.setCitizen(citizen);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;
        
        for(CartItem item : temp) {
            Product p = productRepo.findById(item.getProduct().getId())
                .orElseThrow(() -> new Exception("Το προϊόν δεν βρέθηκε."));
            
            //check for quantity
            if(p.getStock() < item.getQuantity()) {
                throw new Exception("Δεν υπάρχει αρκετό απόθεμα από "+p.getBrand()+" "+p.getDescription());
            }

            //decrease product stock
            p.setStock(p.getStock()-item.getQuantity());
            productRepo.save(p);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(p);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(p.getPrice()); //price at purchase

            //add to orderItem list
            orderItems.add(orderItem);
            //calculate total now we dont have to loop list again
            total += p.getPrice()*item.getQuantity();
        }

        order.setItems(orderItems);
        //keep two decimals and round other decimals
        total = BigDecimal.valueOf(total).setScale(2,RoundingMode.HALF_UP).doubleValue();
        order.setTotalPrice(total);
        //save order
        Order saved = orderRepo.save(order);

        //clear cart contents
        cartService.clearCart(citizen);

        CheckoutResponseDto response = new CheckoutResponseDto();
        response.setOrderId(saved.getId());
        response.setOrderDate(saved.getOrderDate());
        response.setTotalPrice(saved.getTotalPrice());

        return response;
    }


    public List<OrderDto> getCitizenOrders(Citizen citizen) {
        List<Order> orders = orderRepo.findByCitizen_AfmOrderByOrderDateDesc(citizen.getAfm());

        List<OrderDto> dtos = new ArrayList<>();
        for(Order o : orders) {
            OrderDto dto = new OrderDto();
            dto.setOrderId(o.getId());
            dto.setOrderDate(o.getOrderDate());
            dto.setTotalPrice(o.getTotalPrice());

            List<OrderItemDto> itemDtos = new ArrayList<>();
            for(OrderItem item : o.getItems()){
                OrderItemDto i = new OrderItemDto();
                i.setProductId(item.getProduct().getId());
                i.setProductName(item.getProduct().getType()+" "+item.getProduct().getBrand()+" "+item.getProduct().getDescription());
                i.setPriceAtPurchase(item.getPriceAtPurchase());
                i.setQuantity(item.getQuantity());
                itemDtos.add(i);
            }
            dto.setItems(itemDtos);

            dtos.add(dto);
        }
        
        return dtos;
    }




}
