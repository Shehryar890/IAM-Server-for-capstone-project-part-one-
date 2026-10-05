package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.adapter;

import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.jPaRepository.platfromAdminRepository;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.mapper.PlatformAdminPersistenceMapper;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;
import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
@Component
public class Jpaadapter  implements Repository {

   private final platfromAdminRepository jparepository;
        public    Jpaadapter(platfromAdminRepository jparepository){
       this.jparepository = jparepository;
           }

    @Override
    public void save(platformAdmin Admin) {
Instant instant = Instant.now();

        PlatformAdminJpaEntity entity = PlatformAdminPersistenceMapper.toEntity(Admin);
entity.setCreatedAt(instant);
entity.setUpdatedAt(instant);
        jparepository.insertPlatformAdmin(
                entity.getUsername(),
                entity.getPassword(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()

        );



    }




}
