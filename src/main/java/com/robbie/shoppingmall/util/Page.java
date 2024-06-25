package com.robbie.shoppingmall.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {
    private Integer limit;
    private Integer page;
    private Integer total;
    private List<T> result;
}
