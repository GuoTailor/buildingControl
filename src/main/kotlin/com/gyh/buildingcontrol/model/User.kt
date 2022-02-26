package com.gyh.buildingcontrol.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.gyh.buildingcontrol.common.BaseUser
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime
import java.util.stream.Collectors

/**
 * ka_user
 * @author gyh
 */
@Schema(description = "用户")
open class User(
    @Schema(description = "id")
    override var id: Int? = null,

    @Schema(description = "用户名")
    private var username: String? = null,

    /**
     * 密码
     */
    @Schema(description = "密码")
    private var password: String? = null,

    @Schema(description = "角色")
    private var roles: MutableList<Role>? = null,

    /**
     * 地址
     */
    @Schema(description = "地址")
    var location: String? = null,

    /**
     * 电话
     */
    @Schema(description = "电话")
    var phone: String? = null,

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    var name: String? = null,


    /**
     * 物流
     */
    @Schema(description = "物流")
    var logistics: String? = null,

    /**
     * 图片地址，用空格分割
     */
    @JsonIgnore
    @Schema(description = "图片地址，用空格分割")
    var imgs: String? = null,

    /**
     * 备注
     */
    @Schema(description = "备注")
    var remark: String? = null,

    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    ) : BaseUser {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword(): String? = password

    override fun getUsername(): String? = username

    override fun setUsername(username: String?) {
        this.username = username
    }

    override fun setRoles(roles: MutableList<String>) {
        this.roles = roles.stream().map { Role(it) }.collect(Collectors.toList())
    }

    override fun getRoles(): MutableList<String> =
        (roles ?: mutableListOf()).stream().map { it.name!! }.collect(Collectors.toList())

    fun setPassword(password: String?) {
        this.password = password
    }

    @JsonIgnore
    override fun getAuthorities() = roles?.map { GrantedAuthority { it.name } } ?: listOf()
    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true
    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true
    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true
    @JsonIgnore
    override fun isEnabled(): Boolean = true

}
