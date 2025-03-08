package com.shoestore.client.dto.request;


import lombok.Data;

@Data
public class AddressDTO {
  private int addressID;
  private String street;
  private String city;
  private String ward;
  private String district;
}
