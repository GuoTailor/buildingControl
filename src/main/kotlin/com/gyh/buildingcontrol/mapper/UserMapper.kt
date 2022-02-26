package com.gyh.buildingcontrol.mapper

import com.gyh.buildingcontrol.model.User

interface UserMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: User): Int
    fun insertSelective(record: User): Int
    fun selectByPrimaryKey(id: Int): User?
    fun loadUserByUsername(s: String): User?
    fun findUserByUsername(username: String): List<User>
    fun findAll(): List<User>
    fun updateByPrimaryKeySelective(record: User): Int
    fun updateByPrimaryKey(record: User): Int
}