package com.example.core.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssetClassTest {

    @Test
    @DisplayName("모든 AssetClass 값 존재 확인")
    void values_containsAllExpectedValues() {
        assertThat(AssetClass.values())
                .containsExactly(
                        AssetClass.CRYPTO,
                        AssetClass.COMMODITY,
                        AssetClass.FOREX,
                        AssetClass.STOCK,
                        AssetClass.BOND
                );
    }

    @Test
    @DisplayName("valueOf - 문자열로 AssetClass 조회")
    void valueOf_validString_returnsAssetClass() {
        assertThat(AssetClass.valueOf("CRYPTO")).isEqualTo(AssetClass.CRYPTO);
        assertThat(AssetClass.valueOf("COMMODITY")).isEqualTo(AssetClass.COMMODITY);
        assertThat(AssetClass.valueOf("FOREX")).isEqualTo(AssetClass.FOREX);
        assertThat(AssetClass.valueOf("STOCK")).isEqualTo(AssetClass.STOCK);
        assertThat(AssetClass.valueOf("BOND")).isEqualTo(AssetClass.BOND);
    }
}
