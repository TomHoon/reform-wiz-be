package com.reform.wiz.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {

  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int size = 10;

  public Pageable getPageable(Sort sort) {
    int pageNum = page < 0 ? 1 : page - 1;
    // int sizeNum = size <= 10 ? 10 : size;
    int sizeNum = 9;

    return PageRequest.of(pageNum, sizeNum, sort);
  }

}
