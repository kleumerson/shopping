package com.services.java.back.end.shoppingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopReportDto {

  private Integer count;
  private Double total;
  private Double mean;

}
