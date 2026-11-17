package com.example.IAM_SERVER.Utility.JWT;

import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;

public interface GenerateToken {
    public String token (platformAdmin admin);
}
