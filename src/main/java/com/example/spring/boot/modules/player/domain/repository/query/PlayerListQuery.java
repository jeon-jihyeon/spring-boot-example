package com.example.spring.boot.modules.player.domain.repository.query;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public record PlayerListQuery(PlayerId id, Grade grade, FullName fullName, Boolean hasTeam) {
}
