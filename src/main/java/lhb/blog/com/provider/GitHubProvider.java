package lhb.blog.com.provider;

import com.alibaba.fastjson.JSON;
import lhb.blog.com.dto.AssessTokenDTO;
import lhb.blog.com.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @author lhb
 * @date 2019/10/11 10:09
 */
@Component
public class GitHubProvider {
    public String getAssessTokenDTO(AssessTokenDTO assessTokenDTO){
        System.out.println(assessTokenDTO.toString());
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        System.out.println(mediaType);

        OkHttpClient client = new OkHttpClient();


        System.out.println("json后的东西为"+JSON.toJSONString(assessTokenDTO));

        //RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(assessTokenDTO));
        RequestBody body = okhttp3.RequestBody.create(JSON.toJSONString(assessTokenDTO), mediaType);
       // System.out.println("body的值为"+body.toString());
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
           // System.out.println("这里："+response.body().string());
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println("开始："+token+"结束");
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String assessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + assessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
