package com.example.demo_kot.exception

class ControllerSelfThrowException(description: String?, throwable: Throwable?) : Exception(description, throwable) {
}