package com.example.IAM_SERVER.platformAdmin.domain.port.out;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;

import java.util.Optional;
import java.util.UUID;

public interface Repository {
     boolean existsPlatformAdmin();

     void save(platformAdmin admin);
     Optional<PlatformAdminData> findPlatformAdminById(UUID id);

//     Optional<PlatformAdminData> findPlatformAdminById(UUID id);
     public Optional<platformAdmin> getPlatformAdminData(String username);
}
