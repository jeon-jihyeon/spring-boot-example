package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.repository.PlayerCommandRepository;
import com.example.spring.boot.modules.player.domain.repository.PlayerQueryRepository;
import com.example.spring.boot.modules.player.domain.repository.command.PlayerCreateCommand;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerCreateServiceTest {
    @Mock
    private PlayerCommandRepository commandRepository;
    @Mock
    private PlayerQueryRepository queryRepository;
    @InjectMocks
    private PlayerCreateService service;

    @Test
    @DisplayName("Player 생성 서비스 테스트")
    void test_invoke() {
        final PlayerCreateCommand command = new PlayerCreateCommand(new FullName("first", "last"));
        final Player model = Player.create(command);
        final PlayerQuery query = PlayerQuery.from(model);
        when(commandRepository.save(any(Player.class))).thenReturn(model.getId());
        when(queryRepository.findPlayer(any(PlayerId.class))).thenReturn(query);

        // then
        assertThat(service.invoke(command)).isEqualTo(query);
    }
}