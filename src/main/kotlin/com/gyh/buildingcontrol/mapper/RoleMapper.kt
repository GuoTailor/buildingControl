package com.gyh.buildingcontrol.mapper

import com.gyh.buildingcontrol.model.Role
import org.apache.ibatis.annotations.Param

/**
 * Created by gyh on 2021/2/4
 */
interface RoleMapper {
    fun findRoleByUserId(userId: Int): List<Role>
    fun insert(@Param("userId") userId: Int, @Param("roleId") roleId: Int): Int
    fun findAll(): List<Role>
    fun findIdByName(name: String): Int
    fun removeRoleToUser(@Param("userId") userId: Int, @Param("roleId") roleId: Int): Int
    fun deleteById(id: Int): Int
    fun updateRoleById(@Param("id") id: Int, @Param("roleId") roleId: Int): Int
    fun updateRoleByUserIdAndUnitId(@Param("userId") userId: Int, @Param("roleId") roleId: Int): Int
}