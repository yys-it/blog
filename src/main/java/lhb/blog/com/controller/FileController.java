package lhb.blog.com.controller;

import lhb.blog.com.dto.FileDTO;
import lhb.blog.com.utils.FileStatus;
import lhb.blog.com.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author lhb
 * @date 2019/10/9 14:07
 */
@Controller
public class FileController {


    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO fileUpload(@RequestParam(value = "editormd-image-file") MultipartFile file){


        FileStatus upload = FileUtils.upload(file);
        if (upload.getCode() == 1){
            System.out.println("上传成功");
        } else if (upload.getCode() == 2) {
            System.out.println("文件不存在");
        } else if (upload.getCode() == 0) {
            System.out.println("上传失败");
        }

        System.out.println(upload.toString());

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(upload.getCode());
        fileDTO.setMessage("success");
        fileDTO.setUrl("//localhost:8080/LHB/MyImg/"+upload.getFileName());
        return fileDTO;
    }
}
