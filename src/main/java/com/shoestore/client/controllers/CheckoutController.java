package com.shoestore.client.controllers;

import com.shoestore.client.client.OrderClient;
import com.shoestore.client.client.PaymentClient;
import com.shoestore.client.dto.request.*;
import com.shoestore.client.dto.response.ProductDetailCheckoutDTO;
import com.shoestore.client.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {
    @Autowired private ProductDetailService productDetailService;
    @Autowired private ProductService productService;
    @Autowired private AddressService addressService;
    @Autowired private OrderDetailService orderDetailService;
    @Autowired private ReceiptService receiptService;
    @Autowired private HttpSession session;
    @Autowired private OrderClient orderClient;
    @Autowired private PaymentClient paymentClient;

    @GetMapping("/checkout")
    public String showFormCheckoutFromCart(@RequestParam("id") List<Integer> productDetailId,
                                           @RequestParam("quantity") List<Integer> quantities,
                                           @RequestParam("delivery") int delivery,
                                           @RequestParam("subToTal") int subTotal,
                                           @RequestParam("total") int total,
                                           Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<AddressDTO> addressDTOS = addressService.getAddressByUserId(user.getUserID());
        Map<Integer, Integer> selectedProducts = productDetailId.stream()
                .collect(Collectors.toMap(id -> id, id -> quantities.get(productDetailId.indexOf(id))));

        List<ProductDetailCheckoutDTO> productDetailCheckoutDTOS = selectedProducts.entrySet().stream()
                .map(entry -> {
                    ProductDetailDTO pd = productDetailService.getProductDetailById(entry.getKey());
                    ProductDTO p = productService.getProductByProductDetail(entry.getKey());
                    return new ProductDetailCheckoutDTO(pd, entry.getValue(), p.getProductName(), p.getImageURL(), p.getPrice(), p.getProductID());
                }).toList();

        DecimalFormat formatter = new DecimalFormat("#,###.##");
        model.addAttribute("user", user);
        model.addAttribute("address", addressDTOS);
        model.addAttribute("ProductDetailCheckoutDTO", productDetailCheckoutDTOS);
        model.addAttribute("subTotal", formatter.format(subTotal) + " VNĐ");
        model.addAttribute("delivery", delivery == 0 ? "FREE" : formatter.format(delivery) + " VNĐ");
        model.addAttribute("total", formatter.format(total));
        return "page/Customer/Checkout";
    }

    @GetMapping("/checkoutFromProductDetail")
    public String showFormCheckoutFromProductDetail(@RequestParam("quantity") int quantity,
                                                    @RequestParam("productDetailId") int productDetailId,
                                                    @RequestParam("price") int price,
                                                    Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<AddressDTO> addressDTOS = addressService.getAddressByUserId(user.getUserID());
        ProductDetailDTO pd = productDetailService.getProductDetailById(productDetailId);
        ProductDTO p = productService.getProductByProductDetail(productDetailId);

        ProductDetailCheckoutDTO dto = new ProductDetailCheckoutDTO(pd, quantity, p.getProductName(), p.getImageURL(), p.getPrice(), p.getProductID());
        List<ProductDetailCheckoutDTO> checkoutList = List.of(dto);

        int subTotal = price * quantity;
        int delivery = 0;
        int total = subTotal + delivery;
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        model.addAttribute("user", user);
        model.addAttribute("address", addressDTOS);
        model.addAttribute("ProductDetailCheckoutDTO", checkoutList);
        model.addAttribute("subTotal", formatter.format(subTotal) + " VNĐ");
        model.addAttribute("delivery", formatter.format(delivery) + " VNĐ");
        model.addAttribute("total", formatter.format(total));
        return "page/Customer/Checkout";
    }

    private String formatAddress(AddressDTO addressDTO) {
        return addressDTO.getStreet() + ", ward " + addressDTO.getWard() + ", district " + addressDTO.getDistrict() + ", " + addressDTO.getCity();
    }

    private Integer extractProductDetailID(String s) {
        int start = s.indexOf("productDetailID=") + 16;
        int end = s.indexOf(",", start);
        return Integer.parseInt(s.substring(start, end == -1 ? s.length() : end).trim());
    }

    @PostMapping("/payment/add")
    public String addOrder(@RequestBody Map<String, Object> payload) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Integer addressId = (Integer) payload.get("address");
        Integer total = (Integer) payload.get("total");
        Integer delivery = (Integer) payload.get("delivery");
        Integer paymentCase = (Integer) payload.get("paymentCase");
        List<Map<String, Object>> productDetails = (List<Map<String, Object>>) payload.get("productDetails");

        AddressDTO address = addressService.getAddressById(addressId);
        String shippingAddress = formatAddress(address);

        OrderCheckoutDTO order = new OrderCheckoutDTO();
        order.setFeeShip(delivery);
        order.setTotal(total);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setStatus("Processing");
        order.setShippingAddress(shippingAddress);

        OrderCheckoutDTO savedOrder = orderClient.createOrder(order);

        for (Map<String, Object> productDetail : productDetails) {
            OrderDetailRequestDTO detail = new OrderDetailRequestDTO();
            detail.setPrice(((Number) productDetail.get("price")).doubleValue());
            detail.setQuantity((Integer) productDetail.get("quantity"));
            Integer productDetailId = extractProductDetailID((String) productDetail.get("productDetailId"));
            detail.setProductDetailId(productDetailId);
            detail.setOrderId(savedOrder.getId());

            orderDetailService.addOrderDetail(detail);
        }

        return "redirect:/order/confirm";
    }

}