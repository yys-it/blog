package lhb.blog.com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadOrDownController {

    /**
     * 单个文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/1",method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file){
        try{if (file.isEmpty()) {
            return "文件为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为："+fileName);

        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件的后缀名为：" + suffixName);

        //设置文件存储路径
        String filePath = "E:\\LHB\\MyImg\\";
        String path = filePath+UUID.randomUUID()+fileName;
        File dest = new File(path);

        //检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //新建文件夹
        }

        file.transferTo(dest);// 文件写入

        return "文件上传成功";

        }catch (IllegalStateException e){
            e.getStackTrace();
        }catch (Exception e){
            e.getStackTrace();
        }
        return "上传失败";
    }


    /**
     * 文件批量上传
     * @param request
     * @return
     */
    public String uploadFile(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0;i<files.size(); ++i){
            file = files.get(i);
            String filePath = "E:\\LHB\\MyImg\\";
            if (!file.isEmpty()) {
                try{
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream
                            ((new File(filePath + file.getOriginalFilename()))));//设置文件路径以及名字
                    stream.write(bytes);
                    stream.close();
                }catch (Exception e){
                    stream = null;
                    return "第"+i+"个文件上传失败 =>"+e.getMessage();
                }
            }else {
                return "第 " + i  + " 个文件上传失败因为文件为空";
            }
        }

        return "上传成功";
    }



    /**
     * 文件下载
     * @param multipartFile
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/3",method = RequestMethod.GET)
    public String downloadFile(@RequestParam(value = "file") MultipartFile multipartFile,
                               HttpServletRequest request, HttpServletResponse response){

        //获取文件名
        String FileName = multipartFile.getOriginalFilename();
        System.out.println("下载的文件名为："+FileName);
        if(FileName != null){
            //设置文件路径
            File file = new File("E:\\LHB\\MyImg2\\"+FileName);
            System.out.println("文件路径名为："+file);
            if (file.exists()){
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                //设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + FileName);
                System.out.println("正常一");
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                System.out.println("正常二");
                try{
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i !=-1){
                        os.write(buffer,0,1);
                        i = bis.read(buffer);
                    }
                    System.out.println("正常三");
                    return "success";
                }catch (Exception e){
                    System.out.println("异常1");
                    e.printStackTrace();
                }finally {
                    if (bis != null){
                        try{
                            bis.close();
                            System.out.println("正常四");
                        }catch (IOException e){
                            e.printStackTrace();
                            System.out.println("异常2");
                        }
                    }
                    if (fis != null){
                        try{
                            fis.close();
                        }catch (IOException e){
                            e.printStackTrace();
                            System.out.println("异常3");
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}
