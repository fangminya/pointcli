package com.foodchain.controller;

import com.foodchain.consts.Consts;
import com.foodchain.util.Misc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;

@Controller
@RequestMapping("/file")
public class FileUploadController extends BaseController {

    @RequestMapping(value = "upload", produces = Consts.Mapping.PRODUCES_TEXT)
    public void uploadImg(HttpServletRequest request, HttpServletResponse response) {
        try {
            MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
            MultipartFile multFile =  multipartRequest.getFile("file");
            String filePath = multFile != null ? multFile.getOriginalFilename() : null;
            if (Misc.isEmpty(filePath)) {
                toWeb(response, "error");
                return;
            }

            String fileName = System.currentTimeMillis() + filePath.substring(filePath.lastIndexOf("."));
            String storeFolder = "/file-upload/custom/" + Misc.getDefDate(new Date()) + "/";
            String storePath = request.getSession().getServletContext().getRealPath(storeFolder);
            File folder = new File(storePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, fileName);
            multFile.transferTo(file);

            toWeb(response, storeFolder + fileName);
        } catch (Exception e) {
            toWeb(response, "error");
        }
    }
}
