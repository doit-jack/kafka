# Kafka Streams
- 카프카 스트림처리를 위한 라이브러리
- 스트림즈 라이브러리는 Exactly Once, 장애 허용 시스템(fault-tolerance)의 특징을 갖는다.
- 다만, 소스 토픽과 싱크 토픽(저장 토픽)의 카프카 클러스터가 서로 다른 경우는 스트림즈가 지원하지 않는다.

![streams-strucdture.png](..%2Fassets%2Fstreams-strucdture.png)

- 태스크: 파티션 개수에 맞춰서 생성, 데이터 처리 최소 단위
- applicationId: consumer-group id 와 동일한 개념
- 파티션 개수만큼 프로세스를 두고 안정적으로 운영하는 경우가 많음

![topology.png](..%2Fassets%2Ftopology.png)

![processor.png](..%2Fassets%2Fprocessor.png)

![processor2.png](..%2Fassets%2Fprocessor2.png)

![streamsDsl.png](..%2Fassets%2FstreamsDsl.png)

- 스트림즈 DSL에는 레코드의 흐름을 추상화하는 3가지 개념을 알아야 함
  - KStream
  - KTable
  - GlobalKTable

- 코파티셔닝이란 조인을 하는 2개 데이터의 파티션 개수가 동일하고 파티셔닝 전략을 동일하게 맞추는 작업
- 파티셔닝 전략이 동일하다는 것은, Anderson이라는 key를 가진 데이터가 토픽A, 토픽 B의 각 파티션 0에 동일하게 들어가야 한다는 것을 의미함
- 코파티셔닝 되지 않은 KStream과 KTable 을 조인해서 사용하고 싶으면 KTable을 GlobalKTable로 선언하여 사용할 수 있음
- 글로벌 테일은 토픽의 전체 파티션의 모든 데이터를 MaterializedView로 각 태스크에서 사용할 수 있음
- 전체 데이터를 각각의 태스크가 가져가서 사용하기 때문에 데이터가 굉장히 클 경우 부담이 됨, GlobalKTable 의 양이 적을 때 활용할 수 있음