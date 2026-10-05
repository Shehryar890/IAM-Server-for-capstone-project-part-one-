package com.example.IAM_SERVER.platformAdmin.application.RegisterService;

import com.example.IAM_SERVER.Utility.HashPassword.HashPassword;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.adapter.Jpaadapter;
import com.example.IAM_SERVER.platformAdmin.domain.model.Adminpassword;
import com.example.IAM_SERVER.platformAdmin.domain.model.PasswordHash;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;
import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterPlatformAdminCommand;
import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterPlatformAdminUseCase;
import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class SignupService implements RegisterPlatformAdminUseCase {
    private  final Repository  jparepositoryadapter ;
    private final HashPassword bcrypt ;
    public SignupService(Repository adapter , HashPassword bcrypt) {
        this.jparepositoryadapter = adapter;
    this.bcrypt = bcrypt;
    }


    @Override
    public void register(RegisterPlatformAdminCommand command) {
        Adminpassword password = new Adminpassword(command.password());
        String result = bcrypt.hashPassword(password.getpassword());
        PasswordHash hash = PasswordHash.from(result);// validatingpassword
        platformAdmin admin = new platformAdmin();  // creating admin
        admin.setUsername(command.username());
//        admin.setIamadmin_id(UUID.randomUUID());;

        admin.setPassword(hash);
        jparepositoryadapter.save(admin);






    }
}

