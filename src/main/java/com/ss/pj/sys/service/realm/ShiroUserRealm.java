package com.ss.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ss.pj.sys.dao.SysMenuDao;
import com.ss.pj.sys.dao.SysRoleMenuDao;
import com.ss.pj.sys.dao.SysUserDao;
import com.ss.pj.sys.dao.SysUserRoleDao;
import com.ss.pj.sys.po.SysUser;

/**
 * 借助此realm完成认证和授权信息的获取及封装
 * @author td
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	/**
	 * 通过此方法完成授权信息的获取及封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1. 获取登陆用户的信息
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		Integer userId = sysUser.getId();
		//2. 基于用户id获取用户拥有的角色
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		//2.1 校验, 没有权限则抛出异常
		if (roleIds==null || roleIds.size()==0) {
			throw new AuthorizationException();
		}
		//3. 基于角色id获取菜单id
		Integer[] array = {};
		//3.1 list转array
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		//3.2 校验
		if (menuIds==null || menuIds.size()==0) {
			throw new AuthorizationException();
		}
		//4. 基于菜单id获取权限标识
		List<String> permissionList = sysMenuDao.findPermissionsByMenuIds(menuIds.toArray(array));
		//5. 对权限标识信息进行封装并返回, 接受set去重
		Set<String> set = new HashSet<>();
		for (String permission : permissionList) {
			//5.1 去除null值
			if (!StringUtils.isEmpty(permission)) {
				set.add(permission);
			}
		}
		System.out.println(permissionList);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}

	/**
	 * 此方法负责认证信息的获取及封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1. 从token中获取用户名
		UsernamePasswordToken upTaken = (UsernamePasswordToken) token;
		String username = upTaken.getUsername();
		//2. 基于用户名查询用户信息
		SysUser sysUser = sysUserDao.findUserByUserName(username);
		//3. 校验用户信息(用户是否存在, 是否禁用)
		if (sysUser == null) {
			throw new UnknownAccountException("UnknownAccountException: 账户不存在!");
		}
		if (sysUser.getValid() == 0) {
			throw new LockedAccountException("LockedAccountException: 账号被禁用!");
		}
		//4. 封装数据并返回给认证管理器
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				sysUser, // principal 身份
				sysUser.getPassword(),// hashedCredentials 已加密密码 
				ByteSource.Util.bytes(sysUser.getSalt()),// credentialsSalt 盐值的ByteSource封装
				getName()// realmName 当前使用的realm名称
				);
		return info;
	}
	
	/**
	 * 设置凭证匹配器
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//1. 构建凭证匹配对象
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		//2. 设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//3. 设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
}
