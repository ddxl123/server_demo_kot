package com.example.demo_kot.service

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl

abstract class BaseService<M : BaseMapper<E>, E> : ServiceImpl<M, E>() {
}