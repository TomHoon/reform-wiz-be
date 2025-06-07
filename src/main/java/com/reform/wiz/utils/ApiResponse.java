package com.reform.wiz.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
  private String status;
  private String message;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", "OK", data);
  }

  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>("error", message, null);
  }
}
