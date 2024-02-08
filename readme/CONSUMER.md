![consumer-structure.png](..%2Fassets%2Fconsumer-structure.png)

# 컨슈머 그룹
- 같은 그룹 내의 컨슈머들은 모두 같은 로직을 갖는다.
- 토픽을 subscribe 하면 각 파티션별로 컨슈머가 자동으로 할당된다.
- 컨슈머 애플리케이션의 개수를 파티션보다 같게 설정
- 파티션은 1개의 컨슈머만 할당됨
- 컨슈머 그룹을 활용하는 이유 
![consumer-group-usage.png](..%2Fassets%2Fconsumer-group-usage.png)

- 컨슈머 애플리케이션은 안전하게 종료되어야 함. -> 정상적으로 종료되지 않은 컨슈머는 세션 타임아웃이 발생할 때 까지 컨슈머 그룹에 남게 됨
- 컨슈머를 안전하게 종료하기 위해 KafkaConsumer 클래스는 wakeup() 메서드를 지원
- wakeup() 메서드가 호출되면 WakeupException 예외가 발생함

```bash
ps -ef | grep ConsumerWithSyncOffsetCommit
kill -term [port]
```

![multi-thread-consumer.png](..%2Fassets%2Fmulti-thread-consumer.png)

![consumer-lag.png](..%2Fassets%2Fconsumer-lag.png)

- consumer의 methods() 메서드 사용은 이슈가 있다. 컨슈머가 동작할 경우에만 확인할 수 있음.
- 모든 컨슈머 애플리케이션에 컨슈머 랙 모니터링 코드를 중복해서 작성해야 함
-> 외부 모니터링 툴을 사용하자. 데이터 독, 컨플루언트 컨트롤 센터와 같은 카프카 클러스터 종합 모니터링 툴을 사용하자. 컨슈머 랙만 모니터링하기 위한 오픈소스 툴인 버로우도 있음

- 카프카 버로우는 컨슈머 랙을 평가할 때, 슬라이딩 윈도우 계산을 통해 문제가 생긴 것을 확인함


![lag-monitoring.png](..%2Fassets%2Flag-monitoring.png)