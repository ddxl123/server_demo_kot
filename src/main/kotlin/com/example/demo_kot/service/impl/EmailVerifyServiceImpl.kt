package com.example.demo_kot.service.impl

import com.example.demo_kot.entity.EmailVerify
import com.example.demo_kot.mapper.EmailVerifyMapper
import com.example.demo_kot.service.BaseService
import org.springframework.stereotype.Service

@Service
class EmailVerifyServiceImpl : BaseService<EmailVerifyMapper, EmailVerify>()
