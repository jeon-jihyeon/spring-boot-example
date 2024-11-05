package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamIdTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L})
    @DisplayName("TeamId 유효성 검증")
    void test_validation(Long errorValue) {
        assertThrows(InvalidValueException.class, () -> new TeamId(errorValue));
    }

    @Test
    @DisplayName("TeamId 객체 초기화 검증")
    void test_newId_should_generate_id() {
        final Long id = TeamId.newId().value();
        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }

    @Test
    void test_noTeam_is_a_value_of_zero() {
        assertThat(TeamId.NoTeam).isEqualTo(new TeamId(0L));
        assertThat(TeamId.NoTeam.value()).isEqualTo(0L);
    }
}