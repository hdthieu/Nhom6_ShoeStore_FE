package com.shoestore.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shoestore.client.dto.request.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(alphabetic = false)
@ToString
public class UserResponseDTO {
  private int totalItems;
  private int totalPages;
  @JsonProperty("user")
  private List<UserDTO> userDTOs;
}
