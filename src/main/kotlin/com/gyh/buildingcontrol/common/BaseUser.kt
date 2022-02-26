package com.gyh.buildingcontrol.common

import org.springframework.security.core.userdetails.UserDetails

/**
 * Created by gyh on 2021/7/3
 */
interface BaseUser : UserDetails {
    var id: Int?

    fun setUsername(username: String?)

    fun setRoles(roles: MutableList<String>)

    fun getRoles(): MutableList<String>
}