package com.grade.service;

import com.grade.dao.SysUserDao;
import com.grade.entity.SysUser;
import com.grade.util.Md5Util;

/**
 * 认证服务
 */
public class AuthService {

    private final SysUserDao userDao = new SysUserDao();

    /**
     * 登录验证
     * @return 成功返回用户对象，失败返回 null
     */
    public SysUser login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        SysUser user = userDao.findByUsername(username);
        if (user != null && Md5Util.verify(password, user.getPassword())) {
            user.setPassword(null); // 不返回密码
            return user;
        }
        return null;
    }
}
