package com.example.spring.domain.command.model;

import com.example.spring.domain.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamIdTest extends BaseUnitTest {
    @Test
    @DisplayName("NoTeam 테스트")
    void shouldVerifyEqualityAndZeroValueForNoTeam() {
        assertThat(TeamId.NoTeam).isEqualTo(new TeamId(0L));
        assertThat(TeamId.NoTeam.value()).isEqualTo(0L);
    }
}