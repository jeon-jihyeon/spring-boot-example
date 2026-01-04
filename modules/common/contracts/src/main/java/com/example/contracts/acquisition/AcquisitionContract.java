package com.example.contracts.acquisition;

import java.util.List;

public interface AcquisitionContract {
    /**
     * 지정된 심볼과 통화에 대한 일 단위 수집 데이터를 조회
     *
     * <p>이 메서드는 특정 기간 동안의 시장 데이터를 일 단위로 조회하여 두 가지 유형의 데이터를 반환:
     * <ul>
     *   <li>Fundamentals: 시가총액, 공급량, 해시레이트, 채굴 난이도 등 기본 지표</li>
     *   <li>Candles: OHLCV(시가/고가/저가/종가/거래량) 캔들 데이터</li>
     * </ul>
     *
     * <p>각 데이터 타입은 요청한 시간 범위(start ~ end) 내에 존재하는 모든 레코드를 시간순으로 정렬하여 반환
     *
     * @param request 수집 요청 파라미터
     *                <ul>
     *                  <li>symbol: 조회할 암호화폐 심볼 (예: BTC, ETH)</li>
     *                  <li>currency: 가격 표시 통화 (예: KRW, USD)</li>
     *                  <li>start: 조회 시작 시간 (에포크 밀리초, inclusive)</li>
     *                  <li>end: 조회 종료 시간 (에포크 밀리초, inclusive)</li>
     *                </ul>
     * @return 수집된 데이터
     * <ul>
     *   <li>{@link DailyAcquisitionResponse} 목록
     *       (marketCap, circulatingSupply, hashrate, difficulty, minerRevenue, hourlyCandles 포함)</li>
     * </ul>
     * 데이터가 없는 경우 빈 리스트를 반환
     * @throws IllegalArgumentException request의 필수 파라미터(symbol, currency, start, end)가 null인 경우
     * @apiNote 이 API는 읽기 전용이며, 데이터베이스에 저장된 기존 데이터만을 조회
     */
    List<DailyAcquisitionResponse> findDailyAcquisitions(DailyAcquisitionRequest request);
}
