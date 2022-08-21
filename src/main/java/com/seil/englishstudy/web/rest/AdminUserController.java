package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.dto.request.AdminUserCreateRequest;
import com.seil.englishstudy.service.admin.AdminUserCreateService;
import com.seil.englishstudy.service.admin.AdminUserDeleteService;
import com.seil.englishstudy.service.admin.AdminUserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "/admin/users")
@RestController
public class AdminUserController {

    private final AdminUserCreateService adminUserCreateService;
    private final AdminUserDeleteService adminUserDeleteService;
    private final AdminUserReadService adminUserReadService;

    @PostMapping
    public ResponseEntity createAdminAccount(@RequestBody @Valid final AdminUserCreateRequest adminUserCreateRequest) {
        Long id = adminUserCreateService.createAdminUser(adminUserCreateRequest);
        return ResponseEntity.created(URI.create("/admin/users/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readAllUser() {
        return ResponseEntity.ok(adminUserReadService.readAllUser());
    }

    @DeleteMapping(value = "/{adminEmail}")
    public ResponseEntity deleteAdminAccount(@PathVariable final String adminEmail) {
        adminUserDeleteService.deleteAdminUser(adminEmail);
        return ResponseEntity.noContent().build();
    }
}
