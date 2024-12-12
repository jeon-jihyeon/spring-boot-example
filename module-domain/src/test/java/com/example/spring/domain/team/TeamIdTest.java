package com.example.spring.domain.team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamIdTest {
    @Test
    @DisplayName("TeamId 객체 초기화 테스트")
    void shouldGenerateValueForNewId() {
        final Long id = TeamId.newId().value();
        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("NoTeam 테스트")
    void shouldVerifyEqualityAndZeroValueForNoTeam() {
        assertThat(TeamId.NoTeam).isEqualTo(new TeamId(0L));
        assertThat(TeamId.NoTeam.value()).isEqualTo(0L);
    }
}