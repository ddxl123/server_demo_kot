package com.example.demo_kot.util.tablegenerator

import com.example.demo_kot.DemoKotApplication

fun main(args: Array<String>) {
    TableGenerateHandler(
            args, DemoKotApplication::class.java, "com.example.demo_kot.entity", true
    ).run()
}
