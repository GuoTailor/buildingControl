package com.gyh.buildingcontrol

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@MapperScan("com.gyh.buildingcontrol.mapper")
@EnableTransactionManagement
class BuildingControlApplication

fun main(args: Array<String>) {
    runApplication<BuildingControlApplication>(*args)
}
