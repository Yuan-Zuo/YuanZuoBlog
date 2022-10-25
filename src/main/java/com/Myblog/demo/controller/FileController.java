package com.Myblog.demo.controller;

import com.Myblog.demo.dto.FileDTO;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import com.Myblog.demo.util.COSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/yuanzuoBlog")
public class FileController {

    @Autowired
    private COSProvider cosProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upLoad(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        if(file == null)throw new GlobalException(CodeMsg.SERVER_ERROR);
        String url = cosProvider.upload(file.getInputStream() , file.getOriginalFilename());
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(url);
        return fileDTO;
    }
}
