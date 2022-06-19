package com.example.demo.common.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;


/**
 * @Author ljx
 * @Description 封装阿里提供的短信发送接口
 * @Date 2022/4/15
 * @Param
 * @return
 **/
public class SendMessageUtil {
    // 阿里云账号的keyID
    private static String keyId = "LTAI4GCo***8A1djZc1vYjV";
    private static String regionld = "cn-hangzhou";
    private static String secret = "hNV3qpRhe2GdzmnKuCh1R9twVtOrVc";


    public static boolean SendSms(String PhoneNumbers,String TemplateParam) {
        // 1.创建接口API调用对象
//        DefaultProfile profile = DefaultProfile.getProfile(regionld, keyId,secret);
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        // 2.发送post请求
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com"); //云服务(不能修改)
//        request.setSysVersion("2017-05-25"); //版本号(不能修改)
//        request.setSysAction("SendSms"); // 发送方式(不能修改)
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        // 发送的手机号（多个手机号可以用逗号隔开）
//        request.putQueryParameter("PhoneNumbers", 13476683577);
//        request.putQueryParameter("SignName", "ABC商城");
//        // 3.请求模板(短信模板)
//        request.putQueryParameter("TemplateCode", "SMS_199201372");
//        // 4.请求模板参数（需要时json数组格式，验证码可以使用随机参数）
//        request.putQueryParameter("TemplateParam","{\"code\":" + 8888 + "}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            return true;
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        return false;
    }
}
