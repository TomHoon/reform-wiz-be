package com.reform.wiz.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {

  private List<T> dtoList = new ArrayList<>();
  private int totalPages = 0;
  private long totalElements = 0;

  public PageResponseDTO(Page<T> result) {
    this.dtoList = result.getContent();
    this.totalPages = result.getTotalPages();
    this.totalElements = result.getTotalElements();
  }
}
