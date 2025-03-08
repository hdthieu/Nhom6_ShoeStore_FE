package com.shoestore.client.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
  private int roleID;
  private String name;
  private String description;
}
