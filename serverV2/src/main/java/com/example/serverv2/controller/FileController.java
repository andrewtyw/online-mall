package com.example.serverv2.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.serverv2.common.Result;
import com.example.serverv2.networks.serverNetwork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;
    private final String myPath = "/serverV2/src/main/resources/files/";

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir")+this.myPath+flag+"_"+originalFileName;
        System.out.println("save to path:"+rootFilePath);
        FileUtil.writeBytes(file.getBytes(),rootFilePath); //把文件写到本地
        String ip = "120.24.230.237";  //ip这里懒得改, 写死了, 自己修改
        return Result.success("http://" + ip +":"+port+"/files/"+flag);
    }

    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;
        String basePath = System.getProperty("user.dir")+this.myPath;
        List<String> filenames = FileUtil.listFileNames(basePath);
        String fileName = filenames.stream().filter(name->name.contains(flag)).findAny().orElse("");//找到和参数一致的文件
        try {
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath+fileName); // 通过文件路径读取文件字节流
                os = response.getOutputStream(); //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
