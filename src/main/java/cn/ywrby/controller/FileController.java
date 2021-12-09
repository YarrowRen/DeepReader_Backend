package cn.ywrby.controller;


import cn.ywrby.res.ResultResponse;
import cn.ywrby.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    SimpleDateFormat sdf=new SimpleDateFormat("/yyyy/MM/dd/");


    @PostMapping("/file/upload")
    public ResultResponse fileUpload(MultipartFile file, HttpServletRequest req){
        ResultResponse res=new ResultResponse();
        String originName=file.getOriginalFilename();
        if (!originName.endsWith(".pdf")){
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+"文件类型错误！");
        }
        String classifyId = req.getParameter("classifyId");
        System.out.println(classifyId);
        String format=sdf.format(new Date());
        String realPath=uploadPath+format;
        //http://localhost:8081/files/2021-12-04/40b56984-17be-4525-a5d4-b472fb44a1d4.pdf
        //String contextPath = req.getServletContext().getContextPath();
        File folder=new File(realPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        //System.out.println(folder);
        String newName= UUID.randomUUID().toString()+".pdf";
        try {
            file.transferTo(new File(folder,newName));
            String url=req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+format+newName;
            res.setData(url);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        } catch (IOException e) {
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+e.getMessage());
        }
        return res;
    }
}
