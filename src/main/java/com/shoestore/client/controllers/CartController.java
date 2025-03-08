package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.CartDTO;
import com.shoestore.client.dto.request.CartItemDTO;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.request.ProductDetailDTO;
import com.shoestore.client.dto.response.CartItemResponseDTO;
import com.shoestore.client.service.CartItemService;
import com.shoestore.client.service.CartService;
import com.shoestore.client.service.ProductDetailService;
import com.shoestore.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

//    @Autowired
//    private CartItemService cartItemService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private ProductDetailService productDetailService;
//    @GetMapping("/cart/{id}")
//    public String showCart(@PathVariable("id") int id, Model model) {
//        List<CartItemResponseDTO> cartItems = cartItemService.getCartItemsByCartId(id);
//        cartItems.forEach((item) -> {
//            ProductDTO productDTO = productService.getProductByProductDetail(item.getId().getProductDetailId());
//            ProductDetailDTO productDetailDTO = productDetailService.getProductDetailById(item.getId().getProductDetailId());
//            item.setProductName(productDTO.getProductName());
//            item.setProductImage(productDTO.getImageURL());
//            item.setProductPrice(productDTO.getPrice());
//            item.setColor(productDetailDTO.getColor());
//            item.setSize(productDetailDTO.getSize());
//            item.setStockQuantity(productDetailDTO.getStockQuantity());
//        });
//        model.addAttribute("cartItems", cartItems);
//        return "page/Customer/Cart";
//    }
//
//    @PostMapping("/cart/add")
//    public ResponseEntity<CartItemDTO> addCartItem(@RequestParam("productDetailID") int productDetailID,
//                              @RequestParam("quantity") int quantity,
////                              CartItemResponseDTO cartItemDTO,
//                              Model model) {
//            CartItemDTO cartItemDTO=new CartItemDTO();
//
//            CartItemDTO.IdDTO idDTO=new CartItemDTO.IdDTO(1,productDetailID);
//            cartItemDTO.setId(idDTO);
//            cartItemDTO.setQuantity(quantity);
//            ProductDTO productDTO = productService.getProductByProductDetail(productDetailID);
//            double price=productDTO.getPrice();
//            double subTotal= price*quantity;
//            cartItemDTO.setSubTotal(subTotal);
//            CartDTO cartDTO=cartService.getCartById(1);
//            ProductDetailDTO productDetailDTO = productDetailService.getProductDetailById(productDetailID);
//            cartItemDTO.setCart(cartDTO);
//            cartItemDTO.setProductDetailDTO(productDetailDTO);
//            CartItemDTO cartItemSave=cartItemService.addCartItem(cartItemDTO);
//            if(cartItemSave!=null){
//                return ResponseEntity.status(HttpStatus.CREATED).body(cartItemSave);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//            }
//
//
//    }
}


