package com.example.core.values;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SymbolTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValue_createsSymbol() {
        // when
        var symbol = new Symbol("BTC");

        // then
        assertThat(symbol.value()).isEqualTo("BTC");
    }

    @Test
    @DisplayName("생성 - 소문자는 대문자로 변환")
    void constructor_lowercaseValue_convertsToUppercase() {
        // when
        var symbol = new Symbol("btc");

        // then
        assertThat(symbol.value()).isEqualTo("BTC");
    }

    @Test
    @DisplayName("생성 - null 값 시 예외 발생")
    void constructor_nullValue_throwsException() {
        assertThatThrownBy(() -> new Symbol(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Symbol cannot be null or blank");
    }

    @Test
    @DisplayName("생성 - 빈 문자열 시 예외 발생")
    void constructor_emptyValue_throwsException() {
        assertThatThrownBy(() -> new Symbol(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Symbol cannot be null or blank");
    }

    @Test
    @DisplayName("생성 - 공백 문자열 시 예외 발생")
    void constructor_blankValue_throwsException() {
        assertThatThrownBy(() -> new Symbol("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Symbol cannot be null or blank");
    }

    @Test
    @DisplayName("from - 문자열로 생성")
    void from_validString_createsSymbol() {
        // when
        var symbol = Symbol.from("ETH");

        // then
        assertThat(symbol.value()).isEqualTo("ETH");
    }

    @Test
    @DisplayName("from - 소문자도 대문자로 변환")
    void from_lowercaseString_convertsToUppercase() {
        // when
        var symbol = Symbol.from("eth");

        // then
        assertThat(symbol.value()).isEqualTo("ETH");
    }
}
