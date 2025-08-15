package com.reform.wiz.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

 private final static String UPLOAD_DIR = "uploads/";

 public String upload(MultipartFile file) throws IOException {
  // 비어있는지 체크
  if (file.isEmpty())
   return null;

  // 경로없으면 생성
  File uploadDir = new File(UPLOAD_DIR);
  if (!uploadDir.exists())
   uploadDir.mkdirs();

  // 중복 안되도록 prefix 추가
  String fileName = System.currentTimeMillis() + file.getOriginalFilename();

  // Path 미리 생성
  Path path = Paths.get(UPLOAD_DIR, fileName);

  // Files.copy로 넣어주기 (덮어쓰기 옵션 추가)
  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

  return fileName;
 }
}