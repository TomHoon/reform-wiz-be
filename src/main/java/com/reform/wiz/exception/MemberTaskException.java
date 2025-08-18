package com.reform.wiz.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTaskException extends RuntimeException {

 private String msg;
 private int code;

 public MemberTaskException(String msg, int code) {
  this.msg = msg;
  this.code = code;
 }
}
