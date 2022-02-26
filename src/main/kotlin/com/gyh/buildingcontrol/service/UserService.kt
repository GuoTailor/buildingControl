package com.gyh.buildingcontrol.service

import com.github.pagehelper.PageHelper
import com.gyh.buildingcontrol.common.getCurrentUser
import com.gyh.buildingcontrol.mapper.UserMapper
import com.gyh.buildingcontrol.model.PageView
import com.gyh.buildingcontrol.model.Role
import com.gyh.buildingcontrol.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/3
 */
@Service
class UserService(val passwordEncoder: PasswordEncoder) : UserDetailsService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Value("\${fileUploadPath}")
    lateinit var rootPath: String

    @Resource
    lateinit var userMapper: UserMapper

    @Autowired
    lateinit var roleService: RoleService

    override fun loadUserByUsername(s: String): User {
        return userMapper.loadUserByUsername(s)
            ?: throw UsernameNotFoundException("用户：" + s + "不存在")
    }

    fun findUserByUsername(username: String): List<User> {
        return userMapper.findUserByUsername("%$username%")
    }

    fun getAllUser(page: Int, size: Int): PageView<User> {
        PageHelper.startPage<User>(page, size)
        return PageView.build(userMapper.findAll())
    }

    fun getById(id: Int) = userMapper.selectByPrimaryKey(id) ?: error("用户：" + id + "不存在")


    /**
     * 注册用户，并添加默认角色[Role.USER]
     * @param user user
     * @return user
     */
    fun register(user: User): User {
        val result = userMapper.loadUserByUsername(user.username!!)
        if (result != null) error("用户名重复")
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userMapper.insertSelective(user)
        roleService.addRoleToUser(user.id!!, Role.USER)
        user.setPassword("")
        return user
    }

    fun update(user: User): Int {
        val id: Int = getCurrentUser()?.id!!
        // 如果修改的用户是自己或者自己是超级管理员就允许修改
        return if (id == user.id || id == 1) {
            user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
            userMapper.updateByPrimaryKeySelective(user)
        } else throw IllegalStateException("不能修改")
    }

    /**
     * 删除用户
     */
    fun deleteUser(id: Int): Int {
        if (id == 1) error("该账户为管理员，不能删除")
        return userMapper.deleteByPrimaryKey(id)
    }

}