package lhb.blog.com.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler("/LHB/MyImg/**") // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
                   .addResourceLocations("file:E:/LHB/MyImg/"); //媒体资源
        }
	}

}
