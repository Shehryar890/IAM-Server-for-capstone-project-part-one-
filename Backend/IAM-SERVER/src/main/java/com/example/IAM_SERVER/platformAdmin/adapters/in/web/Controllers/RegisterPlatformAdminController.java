package com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers;


import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterPlatformAdminCommand;
import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterPlatformAdminUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/adminsignup")
public class RegisterPlatformAdminController {

    private final RegisterPlatformAdminUseCase registerPlatformAdminUseCase;

    public RegisterPlatformAdminController(
            RegisterPlatformAdminUseCase registerPlatformAdminUseCase
    ) {
        this.registerPlatformAdminUseCase =
                registerPlatformAdminUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> register(
            @RequestBody RegisterPlatformAdminRequest request
    ) {

        RegisterPlatformAdminCommand command =
                new RegisterPlatformAdminCommand(
                        request.username(),
                        request.password()
                );

        registerPlatformAdminUseCase.register(command);

        return ResponseEntity.ok().build();
    }
}