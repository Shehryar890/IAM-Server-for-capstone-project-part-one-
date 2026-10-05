package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.mapper;


import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;

public final class PlatformAdminPersistenceMapper {

    private PlatformAdminPersistenceMapper() {
    }

    public static PlatformAdminJpaEntity toEntity(platformAdmin admin) {

        return new PlatformAdminJpaEntity(
                null,
                admin.getUsername(),
                admin.getpassword(),
                null,
                (null)


        );
    }
}