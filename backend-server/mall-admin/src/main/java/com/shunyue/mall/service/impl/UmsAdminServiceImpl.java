package com.shunyue.mall.service.impl;

import com.shunyue.mall.dto.UmsAdminParam;
import com.shunyue.mall.mapper.UmsAdminMapper;
import com.shunyue.mall.model.UmsAdmin;
import com.shunyue.mall.model.UmsAdminExample;
import com.shunyue.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
         // 存在该用户返回 null
        if (umsAdminList.size() > 0) {
            return null;
        }
        umsAdmin.setPassword(umsAdmin.getPassword());
        //将密码进行加密操作
//        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
//        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }
}
