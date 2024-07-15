package com.robbie.shoppingmall.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PagedResponse<T> {
  private Integer limit;
  private Integer page;
  private Integer nextPage;
  private long total;
  private List<T> result;
}
