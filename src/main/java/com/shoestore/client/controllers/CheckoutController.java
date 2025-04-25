package com.shoestore.client.controllers;

import com.shoestore.client.client.OrderClient;
import com.shoestore.client.client.PaymentClient;
import com.shoestore.client.client.ProductClient;
import com.shoestore.client.dto.request.*;
import com.shoestore.client.dto.response.PaymentResponseDTO;
import com.shoestore.client.dto.response.ProductDetailCheckoutDTO;
import com.shoestore.client.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@Autowired private ProductClient productClient;
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
                    System.out.println(">> Giá đúng từ ProductDetailDTO: " + pd.getPrice());
                    System.out.println(">> Giá sai từ ProductDTO (có thể là 0): " + p.getPrice());
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
    public ResponseEntity<?> addOrder(@RequestBody Map<String, Object> payload, HttpServletRequest request) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Chưa đăng nhập");

        Integer addressId = Integer.parseInt(payload.get("address").toString());
        Integer total = Integer.parseInt(payload.get("total").toString());
        Integer delivery = Integer.parseInt(payload.get("delivery").toString());
        Integer paymentCase = Integer.parseInt(payload.get("paymentCase").toString());
        List<Map<String, Object>> productDetails = (List<Map<String, Object>>) payload.get("productDetails");


        String voucherCode = (payload.get("voucherCode") != null) ? payload.get("voucherCode").toString().trim() : null;
        Integer voucherID = null;

        // ✅ Gọi BE để lấy thông tin voucher và lấy voucherID
        if (voucherCode != null && !voucherCode.isEmpty()) {
            try {
                VoucherDTO voucher = productClient.checkVoucherByCode(voucherCode);
                if (voucher != null && LocalDate.now().isBefore(voucher.getEndDate())
                        && total >= voucher.getMinValueOrder()) {

                    double discount = voucher.getDiscountType().equalsIgnoreCase("PERCENT")
                            ? total * voucher.getDiscountValue() / 100
                            : voucher.getDiscountValue();

                    total -= (int) discount;
                    voucherID = voucher.getVoucherID(); // ✅ Gán vào order
                }
            } catch (Exception e) {
                System.out.println("❌ Voucher không hợp lệ hoặc đã hết hạn: " + voucherCode);
            }
        }

        AddressDTO address = addressService.getAddressById(addressId);
        String shippingAddress = formatAddress(address);

        OrderCheckoutDTO order = new OrderCheckoutDTO();
        order.setFeeShip(delivery);
        order.setTotal(total);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setStatus("Processing");
        order.setShippingAddress(shippingAddress);
        order.setVoucherID(voucherID);
        OrderCheckoutDTO savedOrder = orderClient.createOrder(order);

        for (Map<String, Object> productDetail : productDetails) {
            OrderDetailRequestDTO detail = new OrderDetailRequestDTO();
            detail.setPrice(Double.parseDouble(productDetail.get("price").toString()));
            detail.setQuantity(Integer.parseInt(productDetail.get("quantity").toString()));
            detail.setProductDetailId(Integer.parseInt(productDetail.get("productDetailId").toString()));
            detail.setOrderId(savedOrder.getId());

            orderDetailService.addOrderDetail(detail);
        }

        // 1. Tạo Payment như cũ
        PaymentRequestDTO payment = new PaymentRequestDTO();
        payment.setOrderID(savedOrder.getId());
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(paymentCase == 1 ? "Pending" : "Completed");
        PaymentDTO savedPayment = paymentClient.createPayment(payment);

// 2. Gán paymentID cho Order
        orderClient.updatePaymentID(savedOrder.getId(), savedPayment.getPaymentID());


        if (paymentCase == 1) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String orderInfo = "Thanh toán đơn hàng #" + savedOrder.getId();
            try {
                String paymentUrl = paymentClient.createVNPayUrl(total, orderInfo, baseUrl);
                Map<String, Object> response = new HashMap<>();
                response.put("redirect", true);
                response.put("paymentUrl", paymentUrl);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(Map.of("error", "Lỗi tạo URL VNPay"));
            }
        }

        return ResponseEntity.ok(Map.of(
                "redirect", false,
                "orderId", savedOrder.getId(),
                "paymentID", savedPayment.getPaymentID()
        ));




    }


}