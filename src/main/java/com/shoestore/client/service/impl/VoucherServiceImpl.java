package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.VoucherDTO;
import com.shoestore.client.dto.response.VoucherResponseDTO;
import com.shoestore.client.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVER_URL = "http://localhost:8080/vouchers";
    @Override
    public List<VoucherDTO> searchVouchers(LocalDate startDate, LocalDate endDate) {
        // Tạo URL với các tham số tìm kiếm
        String url = SERVER_URL + "?startDate=" + (startDate != null ? startDate : "")
                + "&endDate=" + (endDate != null ? endDate : "");

        // Thực hiện gọi API GET đến server
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        // Trả về danh sách voucher nếu có dữ liệu trả về
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("vouchers")) {
                return (List<VoucherDTO>) responseBody.get("vouchers");
            }
        }
        return Collections.emptyList();  // Trả về danh sách rỗng nếu không có dữ liệu
    }
    @Override
    public VoucherDTO addVoucher(VoucherDTO voucher) {
        // Gửi request đến API server
        String url = SERVER_URL + "/add";
        return restTemplate.postForObject(url, voucher, VoucherDTO.class);
    }

    public List<VoucherDTO> getVouchersFromServer() {
        String apiUrl = "http://localhost:8080/vouchers";    // URL API trả về đối tượng chứa mảng "vouchers"
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
        return restTemplate.getForObject(SERVER_URL +"/"+ id, VoucherDTO.class);
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



}
