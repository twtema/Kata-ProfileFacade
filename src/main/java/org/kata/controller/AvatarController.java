package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.service.AvatarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/avatar")
public class AvatarController {

    private final AvatarService avatarService;

}
