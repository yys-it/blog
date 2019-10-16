package lhb.blog.com.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author lhb
 * @date 2019/10/10 9:13
 */
public class FileUtils {


    public static FileStatus upload(MultipartFile file){

        FileStatus fileStatus = new FileStatus();

        try{if (file.isEmpty()) {
            fileStatus.setCode(2);
            return fileStatus;
        }
            //获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为："+fileName);

            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("文件的后缀名为：" + suffixName);

            //设置新名字
            String NewFileName = UUID.randomUUID()+fileName;


            //设置文件存储路径
            String filePath = "E:\\LHB\\MyImg\\";
            String path = filePath+ NewFileName;
            File dest = new File(path);

            //检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs(); //新建文件夹
            }
            file.transferTo(dest);// 文件写入
            fileStatus.setCode(1);
            fileStatus.setFileName(NewFileName);
            fileStatus.setFileUrl(path);
            return fileStatus;

        }catch (IllegalStateException e){
            e.getStackTrace();
        }catch (Exception e){
            e.getStackTrace();
        }
        fileStatus.setCode(0);
        return fileStatus;
    }


}
