package com.minesweeper.spring.service;

import com.minesweeper.business.api.UserManager;
import com.minesweeper.common.api.dto.UserDto;
import com.minesweeper.service.api.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@RestController
public class UserServiceImpl
        implements UserService {
    private final UserManager userManager;

    @Inject
    public UserServiceImpl(@Nonnull UserManager userManager) {
        this.userManager = requireNonNull(userManager);
    }

    @Nonnull
    @Override
    @PostMapping("/login")
    public CompletableFuture<String> create(@RequestBody @Nonnull UserDto dto) {
        requireNonNull(dto);
        return userManager.login(dto.getUsername(),
                dto.getPassword());
    }
}
