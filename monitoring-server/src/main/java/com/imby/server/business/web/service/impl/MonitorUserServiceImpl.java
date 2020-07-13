package com.imby.server.business.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imby.server.business.web.dao.IMonitorRoleDao;
import com.imby.server.business.web.dao.IMonitorUserDao;
import com.imby.server.business.web.entity.MonitorRole;
import com.imby.server.business.web.entity.MonitorUser;
import com.imby.server.business.web.realm.MonitorUserRealm;
import com.imby.server.business.web.service.IMonitorUserService;
import com.imby.server.util.SpringSecurityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 监控用户服务实现类
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/7/1 17:39
 */
@Service
public class MonitorUserServiceImpl extends ServiceImpl<IMonitorUserDao, MonitorUser> implements IMonitorUserService, UserDetailsService {

    /**
     * 监控用户数据访问对象
     */
    @Autowired
    private IMonitorUserDao monitorUserDao;

    /**
     * 监控用户角色数据访问对象
     */
    @Autowired
    private IMonitorRoleDao monitorRoleDao;

    /**
     * <p>
     * 根据账号获取用户
     * </p>
     *
     * @param account 账号
     * @return {@link UserDetails}
     * @author 皮锋
     * @custom.date 2020/7/5 14:05
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 根据username在数据库中查询用户
        LambdaQueryWrapper<MonitorUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(MonitorUser::getAccount, account);
        MonitorUser monitorUser = this.monitorUserDao.selectOne(userQueryWrapper);
        // 用户为空
        if (monitorUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        // 根据角色ID在数据库中查询角色
        LambdaQueryWrapper<MonitorRole> roleQueryWrapper = new LambdaQueryWrapper<>();
        roleQueryWrapper.eq(MonitorRole::getId, monitorUser.getRoleId());
        List<MonitorRole> monitorRoles = this.monitorRoleDao.selectList(roleQueryWrapper);
        // 设置授权信息
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(monitorRoles)) {
            for (MonitorRole monitorRole : monitorRoles) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(monitorRole.getRoleName()));
            }
        }
        return new MonitorUserRealm(monitorUser.getId(), monitorUser.getUsername(), account, monitorUser.getPassword(),
                monitorUser.getRoleId(), monitorUser.getRegisterTime(), monitorUser.getUpdateTime(), monitorUser.getEmail(),
                monitorUser.getRemarks(), grantedAuthorityList);
    }

    /**
     * <p>
     * 校验密码是否正确
     * </p>
     *
     * @param password 密码
     * @return 密码是否校验成功
     * @author 皮锋
     * @custom.date 2020/7/8 16:59
     */
    @Override
    public boolean verifyPassword(String password) {
        // 获取用户ID
        MonitorUserRealm monitorUserRealm = SpringSecurityUtils.getCurrentMonitorUserRealm();
        Long userId = monitorUserRealm.getId();
        // 查询数据库
        MonitorUser monitorUser = this.monitorUserDao.selectById(userId);
        String dbPassword = monitorUser.getPassword();
        // 判断输入的原密码和加密后的密码是否一致
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        return bc.matches(password, dbPassword);
    }

    /**
     * <p>
     * 修改密码
     * </p>
     *
     * @param password 密码
     * @return 密码是否修改成功
     * @author 皮锋
     * @custom.date 2020/7/11 15:27
     */
    @Override
    public boolean updatePassword(String password) {
        // 加密密码
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String enPassword = bc.encode(password);
        // 获取用户ID
        MonitorUserRealm monitorUserRealm = SpringSecurityUtils.getCurrentMonitorUserRealm();
        Long userId = monitorUserRealm.getId();
        // 修改密码
        LambdaUpdateWrapper<MonitorUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(MonitorUser::getId, userId).set(MonitorUser::getPassword, enPassword);
        int result = this.monitorUserDao.update(null, lambdaUpdateWrapper);
        return result == 1;
    }
}