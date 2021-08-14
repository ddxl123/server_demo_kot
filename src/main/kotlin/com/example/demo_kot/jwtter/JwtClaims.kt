package com.example.demo_kot.jwtter

import com.example.demo_kot.util.WithFieldName

class JwtClaims() {
    var user_id: String by WithFieldName<String>()
}