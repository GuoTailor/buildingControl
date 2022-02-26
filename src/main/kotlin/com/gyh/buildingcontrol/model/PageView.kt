package com.gyh.buildingcontrol.model

import com.github.pagehelper.Page
import com.github.pagehelper.PageInfo
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.BeanUtils

/**
 * Created by gyh on 2018/10/19.
 * @apiDefine PageView
 * @apiSuccess (返回) {Long} pageNum 当前页号
 * @apiSuccess (返回) {Long} pageSize 每页条数
 * @apiSuccess (返回) {Long} pages 总页数
 * @apiSuccess (返回) {Long} total 总条数
 */
@Schema(description = "分页对象")
class PageView<T>(pojo: List<T>) {
    @Schema(description = "当前页号")
    var pageNum = 0

    @Schema(description = "每页条数")
    var pageSize = 0

    @Schema(description = "总条数")
    var total: Long = 0

    @Schema(description = "数据")
    var list: List<T>? = null
    val pages: Int
        @Schema(description = "总页数")
        get() = getPages(total, pageSize)

    companion object {
        @JvmStatic
        fun <K, P : List<K>> build(pojo: P): PageView<K> {
            return PageView(pojo)
        }

        @JvmStatic
        fun getPages(total: Long, pageSize: Int): Int {
            return if (total == 0L || pageSize == 0) {
                0
            } else (if (total % pageSize == 0L) total / pageSize else total / pageSize + 1).toInt()
        }
    }

    init {
        if (pojo is Page<*>) {
            BeanUtils.copyProperties((pojo as Page<*>).toPageInfo(), this)
        } else {
            BeanUtils.copyProperties(PageInfo(pojo), this)
        }
    }

    override fun toString(): String {
        return "PageView(pageNum=$pageNum, pageSize=$pageSize, total=$total, list=$list, pages=$pages)"
    }


}