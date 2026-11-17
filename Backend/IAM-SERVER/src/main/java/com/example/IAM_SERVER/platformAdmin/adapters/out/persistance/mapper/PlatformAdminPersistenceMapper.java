package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.mapper;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;

public final class PlatformAdminPersistenceMapper {

    private PlatformAdminPersistenceMapper() {
    }

    // Domain -> JPA
    public static PlatformAdminJpaEntity toEntity(platformAdmin admin) {

        return new PlatformAdminJpaEntity(
                null,
                admin.getUsername(),
                admin.getpassword(),
                admin.getCreatedAT(),
                admin.getUpdatedAt()
        );
    }

    // JPA -> Domain
    public static platformAdmin toDomain(PlatformAdminJpaEntity entity) {

        platformAdmin admin = new platformAdmin();

        admin.setIamadmin_id(entity.getIamAdminId());
        admin.setUsername(entity.getUsername());
        admin.setCreatedAT(entity.getCreatedAt());
        admin.setUpdatedAt(entity.getUpdatedAt());

        return admin;
    }





        public static platformAdmin toDomain(PlatformAdminData data) {

            if (data == null) {
                return null;
            }

            platformAdmin admin = new platformAdmin();
            admin.setUsername(data.username());
            admin.setPassword(data.passwordHash());
            admin.setIamadmin_id(data.id());
            return admin;



        }
    }
