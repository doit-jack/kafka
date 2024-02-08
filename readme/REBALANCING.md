![rebalancing.png](..%2Fassets%2Frebalancing.png)

아래 2가지 상황에서 리밸런싱 발생
1. 컨슈머가 추가되는 상황 
2. 컨슈머가 제거되는 상황

- 추가, 제거 되는 상황에 대해 RebalanceListener 를 제공함.
- 파티션 개수가 굉장히 많을 때 할당 과정이 굉장히 길다. -> 리밸런싱이 일어나는 상황이 일어나지 않도록 운영해야 함.
- 관련 대응 로직을 작성해야함 

 # 리밸런스 리스너를 가진 컨슈머
- 리밸런스 발생을 감지하기 위해 카프카 라이브러리는 ConsumerRebalanceListener 인터페이스를 지원
- 해당 인터페이스로 구현된 클래스는 onPartitionAssigned() 메서드와 onPartitionRevoked() 메서드를 구현해야 함
  - onPartitionAssigned(): 리밸런스가 끝난 뒤, 파티션이 할당 완료되면 호출되는 메서드
  - onPartitionRevoked(): 리밸런스가 시작되기 직전에 호출되는 메서드, 마지막으로 처리한 레코드를 기준으로 컴시을 하기 위해서는 리밸런스가 시작하기 직전에 커밋을 하면 되므로 해당 메서드에 커밋을 구현하여 처리
