package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.adapter;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.jPaRepository.platfromAdminRepository;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.mapper.PlatformAdminPersistenceMapper;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;
import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class Jpaadapter implements Repository {

    private final platfromAdminRepository jparepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Jpaadapter(platfromAdminRepository jparepository) {
        this.jparepository = jparepository;
    }

    @Override
    public boolean existsPlatformAdmin() {
        return jparepository.existsPlatformAdmin();
    }
    @Override
    public Optional<PlatformAdminData> findPlatformAdminById(UUID id) {

        return jparepository.findadminbyid(id);
    }

    @Override
    public void save(platformAdmin admin) {

        Instant instant = Instant.now();

        PlatformAdminJpaEntity entity =
                PlatformAdminPersistenceMapper.toEntity(admin);

        int rowsInserted = jparepository.insertPlatformAdmin(
                entity.getUsername(),
                entity.getPassword(),
                instant,
                instant
        );

        if (rowsInserted != 1) {
            throw new IllegalStateException(
                    "Platform admin could not be created"
            );
        }
    }

    @Override
    public Optional<platformAdmin> getPlatformAdminData(String username) {


            return jparepository.findPlatformAdminForLogin(username)
                    .map(data -> PlatformAdminPersistenceMapper.toDomain(data));
        }

    }
