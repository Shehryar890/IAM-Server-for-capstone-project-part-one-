package com.example.IAM_SERVER.platformAdmin.application.RegisterService;

import com.example.IAM_SERVER.Utility.HashPassword.HashPassword;
import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.PlatformAdminRegisterCommand;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.PlatformAdminAlreadyExistsException;
import com.example.IAM_SERVER.platformAdmin.domain.model.Adminpassword;
import com.example.IAM_SERVER.platformAdmin.domain.model.PasswordHash;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;

import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterplatformUsecase;
import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
class SignupService implements RegisterplatformUsecase {
private final Repository platformAdminRepository;
    private final HashPassword bcrypt;

    public SignupService(
            Repository platformAdminRepository,
            HashPassword bcrypt
    ) {
        this.platformAdminRepository = platformAdminRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    @Transactional
    public void register(PlatformAdminRegisterCommand command) {

        // 1. Check whether a platform admin already exists.
        if (platformAdminRepository.existsPlatformAdmin()) {
            throw new PlatformAdminAlreadyExistsException(
                    "A platform administrator already exists."
            );
        }

        // 2. Hash the submitted password.
        Adminpassword password =
                new Adminpassword(command.password());

        String hashedPassword =
                bcrypt.hashPassword(password.getpassword());

        PasswordHash passwordHash =
                PasswordHash.from(hashedPassword);

        // 3. Create the platform admin domain object.
        platformAdmin admin = new platformAdmin();

        admin.setUsername(command.username());
        admin.setPassword(passwordHash);

        // 4. Save the platform admin.
        platformAdminRepository.save(admin);
    }
}