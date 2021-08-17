package com.example.demo_kot.service.impl

import com.example.demo_kot.entity.User
import com.example.demo_kot.mapper.UserMapper
import com.example.demo_kot.service.BaseService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : BaseService<UserMapper, User>()
