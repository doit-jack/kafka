- 다수의 데이터에 파티션을 저장하는 경우 모든 데이터에 대해 동일한 원자성을 만족시키기 위해 사용
- 레코드만 보내는 것이 아니라 트랜잭션의 시작과 끝을 표현하기 위해 트랜잭션 레코드를 한개 더 보냄
![atomic.png](..%2Fassets%2Fatomic.png)

![transaction-how-to-work.png](..%2Fassets%2Ftransaction-how-to-work.png)

![transaction-produer.png](..%2Fassets%2Ftransaction-produer.png)

- 트랜잭션 컨슈머는 커밋이 완료된 레코드들만 읽기 위해 isolation.level 옵션을 read_committed로 설정
- 기본값은 read_uncommitted로 커밋 여부와 상관없이 모두 읽음
```java
configs.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed")
```

