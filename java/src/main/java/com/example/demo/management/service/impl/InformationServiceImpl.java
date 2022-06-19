package com.example.demo.management.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.management.entity.Account;
import com.example.demo.management.entity.Information;
import com.example.demo.management.entity.LoginUser;
import com.example.demo.management.entity.enums.GenderEnum;
import com.example.demo.management.entity.request.SetInfoRequest;
import com.example.demo.management.entity.response.InfoResponse;
import com.example.demo.management.mapper.AccountMapper;
import com.example.demo.management.mapper.InformationMapper;
import com.example.demo.management.service.InformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author LiuJingxian
 * @since 2022-04-15
 */
@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Value("${file.url}")
    private String url;

    @Resource
    InformationMapper informationMapper;

    @Resource
    AccountMapper accountMapper;

    @Override
    public void modInfo(SetInfoRequest setInfoRequest, String loginId) {
        Information information = informationMapper.selectOne(
                Wrappers.lambdaQuery(Information.class)
                        .eq(Information::getLoginId, loginId)
        );
        BeanUtils.copyProperties(setInfoRequest, information);
        informationMapper.updateById(information);
    }


    @Override
    public InfoResponse getInfo(String loginId) {
        Information information = informationMapper.selectOne(
                Wrappers.lambdaQuery(Information.class)
                        .eq(Information::getLoginId, loginId)
        );
        Account account = accountMapper.selectOne(
                Wrappers.lambdaQuery(Account.class)
                .eq(Account::getLoginId,loginId)
        );
        InfoResponse infoResponse = new InfoResponse();
        BeanUtils.copyProperties(information, infoResponse);
        infoResponse.setGender(GenderEnum.getMessageByGender(information.getGender()));
        infoResponse.setAccount(account.getMoney().toString());
        return infoResponse;
    }

    @Override
    public String uploadHeadPic(MultipartFile file, LoginUser loginUser) throws IOException {
        // 获取工作目录
        String currentPath = System.getProperty("user.dir");
        System.out.println("1:"+currentPath);
        // 如果不是定位到java目录，就添加路径
        if (!currentPath.endsWith("java")) {
            currentPath += "/java";
        }
        System.out.println("2:"+currentPath);
        currentPath += "/upload/";
        System.out.println("3:"+currentPath);

        String fileName = UUID.randomUUID().toString().replace("-", "");
        Path path = Paths.get(currentPath, fileName + ".jpg");
        file.transferTo(path);
        // 更新学生表的头像路径
        informationMapper.update(
                null,
                Wrappers.lambdaUpdate(Information.class)
//                        .set(Information::getHeadPicUrl, url + fileName + ".jpg")
                        //由于安卓虚拟机中的ip不是localhost，所以命名需要变化
                        .set(Information::getHeadPicUrl, "/file/" + fileName + ".jpg")
                        .eq(Information::getLoginId, loginUser.getLoginId())
        );

        return file.getOriginalFilename();
    }
}
