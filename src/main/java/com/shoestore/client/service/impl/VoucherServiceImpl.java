package com.shoestore.client.service.impl;

import com.shoestore.client.client.ProductClient;
import com.shoestore.client.dto.request.VoucherDTO;
import com.shoestore.client.dto.response.VoucherResponseDTO;
import com.shoestore.client.service.VoucherService;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVER_URL = "http://localhost:8765/products/voucher";

//    private static final String SERVER_URL = "http://api-gateway:8765/products/voucher";
    @Autowired
    private ProductClient productClient;
    @Override
    public List<VoucherDTO> searchVouchers(LocalDate startDate, LocalDate endDate, String status) {
        StringBuilder urlBuilder = new StringBuilder(SERVER_URL + "/search?");

        if (startDate != null) {
            urlBuilder.append("startDate=").append(startDate).append("&");
        }
        if (endDate != null) {
            urlBuilder.append("endDate=").append(endDate).append("&");
        }
        if (status != null && !status.isEmpty()) {
            urlBuilder.append("status=").append(status);
        }

        String url = urlBuilder.toString();
        // Xoá dấu `&` nếu nó là ký tự cuối cùng
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        VoucherDTO[] response = restTemplate.getForObject(url, VoucherDTO[].class);
        return Arrays.asList(response);
    }


    @Override
    public VoucherDTO addVoucher(VoucherDTO voucher) {
        // Gửi request đến API server
        String url = SERVER_URL + "/add";
        return restTemplate.postForObject(url, voucher, VoucherDTO.class);
    }

    public List<VoucherDTO> getVouchersFromServer() {
        String apiUrl = SERVER_URL;    // URL API trả về đối tượng chứa mảng "vouchers"
        // Sử dụng ResponseEntity để lấy đối tượng JSON chứa danh sách vouchers
        ResponseEntity<VoucherResponseDTO> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null,
                VoucherResponseDTO.class
        );

        // Truyền trả về danh sách vouchers từ đối tượng JSON
        return response.getBody().getVouchers();
    }



    @Override
    public void deleteVoucher(int voucherID) {
        String apiUrl = SERVER_URL+"/"+ voucherID;
        restTemplate.delete(apiUrl);
    }

    @Override
    public VoucherDTO getVoucherById(int id) {
        return restTemplate.getForObject( SERVER_URL + "/"+ id, VoucherDTO.class);
    }

    @Override
    public VoucherDTO updateVoucher(int id, VoucherDTO voucher) {
        HttpEntity<VoucherDTO> request = new HttpEntity<>(voucher);
        ResponseEntity<VoucherDTO> response = restTemplate.exchange(
                SERVER_URL +"/" + id,
                HttpMethod.PUT,
                request,
                VoucherDTO.class
        );
        return response.getBody();
    }







    @Retry(name = "voucherRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "voucherRateLimiter", fallbackMethod = "fallback")
    public VoucherDTO checkVoucherByCode(String code) {
        log.info("🔁 Gọi API kiểm tra mã voucher: {}", code);
        try {
            return productClient.checkVoucherByCode(code);
        } catch (FeignException e) {
            if (e.status() == 404 || e.status() == 400) {
                log.warn("❌ Mã [{}] không tồn tại hoặc không hợp lệ.", code);
                throw new InvalidVoucherException("❌ Mã không hợp lệ hoặc đã hết hạn.");
            }

            throw e; // Cho phép fallback xử lý lỗi hệ thống
        }
    }


    public VoucherDTO fallback(String code, Throwable t) {
        log.error("⚠️ Fallback triggered for [{}]: {}", code, t.toString());

        // 👉 Fallback bị gọi vì cấu hình sai, bắt lại lỗi gốc nếu là InvalidVoucherException
        if (t.getCause() instanceof InvalidVoucherException) {
            throw (InvalidVoucherException) t.getCause();
        }

        if (t instanceof InvalidVoucherException) {
            throw (InvalidVoucherException) t;
        }

        if (t instanceof RateLimitException || t instanceof RequestNotPermitted) {
            throw new RateLimitException("🚫 Bạn thao tác quá nhanh. Vui lòng thử lại sau.");
        }

        throw new RetryFailureException("⚠️ Không thể kết nối tới hệ thống. Vui lòng thử lại sau.");
    }





    // ===== Custom Exceptions =====
    public static class InvalidVoucherException extends RuntimeException {
        public InvalidVoucherException(String message) {
            super(message);
        }
    }

    public static class RetryFailureException extends RuntimeException {
        public RetryFailureException(String message) {
            super(message);
        }
    }

    public static class RateLimitException extends RuntimeException {
        public RateLimitException(String message) {
            super(message);
        }
    }




}
