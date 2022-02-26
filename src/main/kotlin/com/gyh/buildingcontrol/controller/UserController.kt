package com.gyh.buildingcontrol.controller

import com.gyh.buildingcontrol.model.PageView
import com.gyh.buildingcontrol.model.ResponseInfo
import com.gyh.buildingcontrol.model.User
import com.gyh.buildingcontrol.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by gyh on 2021/5/31
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var userService: UserService

    /**
     * @api {get} /user/all 分页查询用户
     * @apiDescription 分页查询所有用户
     */
    @Operation(summary = "分页查询所有用户", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/all")
    fun getAllUser(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseInfo<PageView<User>> {
        return ResponseInfo.ok(userService.getAllUser(page ?: 1, size ?: 30))
    }

    /**
     * @api {get} /user/username 根据客户名称查询用户
     * @apiDescription 根据客户名称查询用户
     */
    @Operation(summary = "分页查询所有用户", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/username")
    fun getByUsername(@RequestParam username: String): ResponseInfo<List<User>> {
        return ResponseInfo.ok(userService.findUserByUsername(username))
    }

    /**
     * @api {get} /user/id 根据id查询用户
     * @apiDescription 根据id查询用户
     */
    @Operation(summary = "根据id查询用户", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/id")
    fun getById(@RequestParam id: Int): ResponseInfo<User?> {
        return ResponseInfo.ok(userService.getById(id))
    }

    /**
     * @api {post} /user 添加用户
     * @apiDescription 添加用户
     */
    @Operation(summary = "添加用户", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun addUser(@RequestBody user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }

    /**
     * @api {put} /user 更新用户
     * @apiDescription 更新用户
     */
    @Operation(summary = "更新用户", security = [SecurityRequirement(name = "Authorization")])
    @PutMapping
    fun updateUser(@RequestBody user: User): ResponseInfo<Int> {
        return ResponseInfo.ok(userService.update(user))
    }

    /**
     * @api {delete} /user 删除用户
     * @apiDescription 删除用户
     */
    @Operation(summary = "删除用户", security = [SecurityRequirement(name = "Authorization")])
    @DeleteMapping
    fun deleteUser(@RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(userService.deleteUser(id))
    }
}