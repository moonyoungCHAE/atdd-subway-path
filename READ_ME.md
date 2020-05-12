## 2단계 시나리오
```
Feature: 출발역과 도착역의 최단 경로를 조회  
    Scenario: 출발역과 도착역의 최단 경로를 조회한다.  
     
    Given: 여러 개의 노선이 존재한다.
        And 지하철역이 여러 개 추가되어있다.
        And 지하철 구간이 여러 개 추가되어있다.
    When: 출발역과 도착역의 최단 경로를 조회 요청한다.  
    Then: 출발역과 도착역의 최단 경로를 응답 받는다.
```