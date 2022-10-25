package com.Myblog.demo.dto;

import lombok.Data;

@Data
public class FileDTO {
    // 0 表示上传失败，1 表示上传成功
    Integer success; // 0 表示上传失败，1 表示上传成功
    // "提示的信息，上传成功或上传失败及错误信息等。",
    String message;
    String url;
}
