--------------------------------------------------------------------------------

			# KAFKA-WORK #

--------------------------------------------------------------------------------

########################################################
### KAFKA

https://kafka.apache.org/
https://www.confluent.io/
https://aws.amazon.com/ko/msk/

https://kafka.apache.org/documentation/

https://github.com/apache/kafka

https://spring.io/projects/spring-kafka#overview
https://docs.spring.io/spring-kafka/docs/current/reference/html/

https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Improvement+Proposals

https://github.com/linkedin/Burrow

https://www.confluent.io/hub/

https://ksqldb.io/

########################################################
### Reference

https://kafka.apache.org/quickstart

- 아파치 카프카 - book 예제 소스 
https://github.com/bjpublic/apache-kafka-with-java

https://github.com/AndersonChoi/

- blog
https://velog.io/@jaehyeong/Apache-Kafka아파치-카프카란-무엇인가
https://soft.plusblog.co.kr/category/정리 예정
https://pearlluck.tistory.com/category/Big%20Data/Kafka

- 동영상 강좌 
https://www.youtube.com/watch?v=waw0XXNX-uQ&list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j

https://www.youtube.com/watch?v=VJKZvOASvUA
https://www.youtube.com/watch?v=iUX6d14bvj0
https://www.youtube.com/watch?v=dubFjEXuK6w
https://www.youtube.com/watch?v=oyNjiQ2q2CE
https://www.youtube.com/watch?v=3OPZ7_sHtWo

########################################################
### Kafka Guide

* download
https://kafka.apache.org/downloads
다운로드 kafka_2.12-3.3.1.tgz 
tar xvf  kafka_2.12-3.3.1.tgz 

* heap memory 설정   .bashrc 등에 설정 
ex) export KAFKA_HEAP_OPTS="-Xmx400m -Xms400m" 

* zookeeper 실행 
cd  kafka_2.12-3.3.1
$ bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
$ jps -vm 

# zookeeper.properties
	dataDir=/tmp/zookeeper
	clientPort=2181
	maxClientCnxns=0
	admin.enableServer=false

* server.properties 파일 내용 체크 
$ vi config/server.properties 
하단 내용 설정 
#advertised.listeners=PLAINTEXT://your.host.name:9092
advertised.listeners=PLAINTEXT://127.0.0.1:9092

* kafka broker 실행 
$ bin/kafka-server-start.sh -daemon config/server.properties
$ jps -m 
$ tail -f logs/server.log

# server.properties
	# kafka server.properties for localhost broker test
	broker.id=0
	num.network.threads=3
	num.io.threads=8

	# Please modify directory
	log.dirs=/tmp/kafka
	num.partitions=3
	listeners=PLAINTEXT://localhost:9092
	advertised.listeners=PLAINTEXT://localhost:9092
	socket.send.buffer.bytes=102400
	socket.receive.buffer.bytes=102400
	socket.request.max.bytes=104857600
	num.recovery.threads.per.data.dir=1
	offsets.topic.replication.factor=1
	transaction.state.log.replication.factor=1
	transaction.state.log.min.isr=1
	log.retention.hours=168
	log.segment.bytes=1073741824
	log.retention.check.interval.ms=300000
	zookeeper.connect=localhost:2181
	zookeeper.connection.timeout.ms=18000
	group.initial.rebalance.delay.ms=0

*  kafka 통신 테스트
$ bin/kafka-broker-api-versions.sh --bootstrap-server 127.0.0.1:9092

# kafka command line tool
* kafka-topics.sh 
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic hello.kafka
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1 --config retention.ms=1728000000 --topic hello.kafka2
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic hello.kafka
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic hello.kafka2 
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic hello.kafka --alter --partitions 4
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic hello.kafka --describe
$ bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name hello.kafka --alter --add-config retention.ms=86400000
$ bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name hello.kafka --describe 

* kafka-console-producer.sh 
$ bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic hello.kafka
$ bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic hello.kafka --property "parse.key=true" --property "key.separator=:"

* kafka-console-consumer.sh
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hello.kafka --from-beginning
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hello.kafka --property print.key=true --property key.separator="-" --group hello-group --from-beginning

* kafka-consumer-groups.sh
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list hello-group
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group hello-group --describe

* kafka-verifiable-producer, consumer.sh 
$ bin/kafka-verifiable-producer.sh --bootstrap-server localhost:9092 --max-messages 10 --topic verify-test 
$ bin/kafka-verifiable-consumer.sh --bootstrap-server localhost:9092 --topic verify-test --group-id test-group

* kafka-delete-records.sh 
$ vi delete-topic.json
{"partitions": [{"topic":"test" ,"partition": 0, "offset": 50}], "version":1}
$ bin/kafka-delete-records.sh --bootstrap-server localhost:9092 --offset-json-file delete-topic.json

--------------------------------------------------------------------------------

### example (아파치 카프카 - book 예제 소스 )
https://github.com/bjpublic/apache-kafka-with-java

* example/kafka-producer/ 
	- SimpleProducer.java
	- ProducerWithKeyValue.java
	- ProducerExactPartition.java
	- ProducerWithCustomPartitioner.java
	  CustomPartitioner.java
	- ProducerWithSyncCallback.java
	- ProducerWithAsyncCallback.java
	  ProducerWithAsyncCallback.java
	- IdempotenceProducer.java
	- TransactionProducer.java
	- ConfluentSimpleProducer.java

* example/kafka-consumer/
	- SimpleConsumer.java
	- ConsumerWithSyncCommit.java
	- ConsumerWithSyncOffsetCommit.java
	- ConsumerWithASyncCommit.java
	- ConsumerWithRebalanceListener.java
	  RebalanceListener.java
	- ConsumerWithExactPartition.java
	- ConsumerWithSyncOffsetCommitShutdownHook.java
	- ConsumerWithAutoCommit.java
	- MultiConsumerThread.java
	  ConsumerWorker.java
	- MultiConsumerThreadByPartition.java
	  ConsumerWorkerByPartition.java
	- ConsumerWithMultiWorkerThread.java
	  ConsumerWorkerMulit.java
	- TransactionConsumer .java
	- ConfluentSimpleConsumer.java

* example/kafka-admin/	  
	- KafkaAdminClient.java

* example/kafka-streams/	  
	- SimpleStreamApplication.java
	- StreamsFilter.java
	- KStreamJoinKTable.java
	- KStreamJoinGlobalKTable.java
	- SimpleKafkaProcessor.java
	  FilterProcessor.java
	- KStreamCountApplication.java
	- QueryableStore.java
	- MetricStreams.java
	  MetricJsonUtils.java

* example/kafka-connector/	  
	simple-source-connector/
	- SingleFileSourceConnector.java
	  SingleFileSourceConnectorConfig.java
	  SingleFileSourceTask.java
	simple-sink-connector/
	- SingleFileSinkConnector.java
	  SingleFileSinkConnectorConfig.java
	  SingleFileSinkTask.java

* example/spring-kafka/	  
	spring-kafka-producer/
	spring-kafka-template-producer/
	spring-kafka-record-listener/
	spring-kafka-batch-listener/
	spring-kafka-commit-listener/
	spring-kafka-listener-container/

--------------------------------------------------------------------------------

# 단일 모드 커넥트 
$ bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties

# connect-distributed.properties 
	bootstrap.servers=localhost:9092
	group.id=connect-cluster

	key.converter=org.apache.kafka.connect.storage.StringConverter
	value.converter=org.apache.kafka.connect.storage.StringConverter
	key.converter.schemas.enable=false
	value.converter.schemas.enable=false

	offset.storage.topic=connect-offsets
	offset.storage.replication.factor=1
	config.storage.topic=connect-configs
	config.storage.replication.factor=1
	status.storage.topic=connect-status
	status.storage.replication.factor=1
	offset.flush.interval.ms=10000

# 분산 모드 커넥트 
$ bin/connect-distributed.sh config/connect-distributed.properties 

# 커넥터 플러그인 조회
$ curl -X GET http://localhost:8083/connector-plugins

# FileStreamSinkConnector 생성

$ curl -X POST \
  http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "file-sink-test",
    "config":
    {
	    "topics":"test",
	    "connector.class":"org.apache.kafka.connect.file.FileStreamSinkConnector",
	    "tasks.max":1,
	    "file":"/tmp/connect-test.txt"
    }
  }'

# file-sink-test 커넥터 실행 상태 확인
$ curl http://localhost:8083/connectors/file-sink-test/status

# file-sink-test 커넥터의 태스크 확인
$ curl http://localhost:8083/connectors/file-sink-test/tasks

# file-sink-test 커넥터 특정 태스크 상태 확인
$ curl http://localhost:8083/connectors/file-sink-test/tasks/0/status

# file-sink-test 커넥터 특정 태스크 재시작
$ curl -X POST http://localhost:8083/connectors/file-sink-test/tasks/0/restart

# file-sink-test 커넥터 수정
$ curl -X PUT http://localhost:8083/connectors/file-sink-test/config \
  -H 'Content-Type: application/json' \
  -d '{
	    "topics":"test",
	    "connector.class":"org.apache.kafka.connect.file.FileStreamSinkConnector",
	    "tasks.max":1,
	    "file":"/tmp/connect-test2.txt"
	}'

# file-sink-test 커넥터 중지
$ curl -X PUT http://localhost:8083/connectors/file-sink-test/pause

# file-sink-test 커넥터 시작
$ curl -X PUT http://localhost:8083/connectors/file-sink-test/resume

# file-sink-test 커넥터 재시작
$ curl -X POST http://localhost:8083/connectors/file-sink-test/restart

# file-sink-test 커넥터 삭제
$ curl -X DELETE http://localhost:8083/connectors/file-sink-test

# mac에서 내 ip 확인
$ ifconfig | grep "inet " | awk '{ print $2}'

# kafka-connect-ui 도커 실행
$ docker run --rm -it -p 8000:8000 \
           -e "CONNECT_URL=http://{{my-ip}}:8083" \
           landoop/kafka-connect-ui

# 카프카 미러케이커2 
 config/ connect-mirror-maker.properties
* connect-mirror-maker.properties
    ---------------------------------
	clusters = A, B

	A.bootstrap.servers = a-kafka:9092
	B.bootstrap.servers = b-kafka:9092

	A->B.enabled = true
	A->B.topics = test

	B->A.enabled = false
	B->A.topics = .*

	replication.factor=1

	checkpoints.topic.replication.factor=1
	heartbeats.topic.replication.factor=1
	offset-syncs.topic.replication.factor=1
	offset.storage.replication.factor=1
	status.storage.replication.factor=1
	config.storage.replication.factor=1
    ---------------------------------

	$ bin/connect-mirror-maker.sh config/connect-mirror-maker.properties 
	$ bin/kafka-console-producer.sh --bootstrap-server a-kafka:9002 --topic test
	> a 
	> b 
	> c 
	$ bin/kafka-console-consumer.sh --bootstrap-server b-kafka:9092 --topic A.test --from-beginning 
	$ bin/kafka-topics.sh --bootstrap-server a-kafka:9092 --topic test --alter --partitions 5
	$ bin/kafka-topics.sh --bootstrap-server b-kafka:9092 --topic A.test --describe 

	* Geo-Replication 
	- Active-standby
	- Active-Active
	- Hub and spoke

# 컨슈머 랙 체크 
	* 카프카 명령어를 사용하여 컨슈머 랙 조회 
	$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-group --describe

	* 컨슈머 metrics() 메소드를 사용하여 컨슈머 랙 조회 
		for (Map.Entry<MetricName, ? extends Metric> entry : kafkaConsumer.metrics().
		entrySet()) {
			if("records-lag-max".equals(entry.getKey().name()) |
			"records-lag".equals(entry.getKey().name()) |
			"records-lag-avg".equals(entry.getKey().name())){
				Metric metric = entry.getValue();
				logger.info("{}:{}", entry.getKey().name(), metric.metricValue());
			}
		}
	
	* 외부 모니터링 툴을 사용하여 컨슈머 랙 조회 
	- Datadog
	- Confluent Control Center 
	- Burrow (Open Source)

   * 컨슈머 랙 모니터링 아키텍처 툴
	- 버로우
	- 텔레그래프
	- 엘라스틱서치
	-그라파나 

=================================================================================

### 실전 예제 

## 웹 페이지 이벤트 적재 파이프라인 생성 

# 아키텍처 
* 사용자 이벤트 --- (Rest Api) ---> 프로듀서 APP  ---> Kafka ---> 컨슈머 APP ---> 하둡 
* 사용자 이벤트 --- (Rest Api) ---> 프로듀서 APP  ---> Kafka ---> 분산 커넥트  ---> 엘라스틱서치 

# 작업 리스트 
* 로컬 하둡, 엘라스틱서치 , 키바나 설치
* 토픽 생성
* 이벤트 수집 웹페이지 개발
* REST API 프로듀서 애플리케이션 개발
* 하둡 적재 컨슈머 애플리케이션 개발
* 엘라스틱서치 싱크 커넥터 개발 

1.  로컬 하둡, 엘라스틱서치 , 키바나 설치
brew install hadoop elasticsearch kibana

hadoop - core-site.xml 
<configuration>
 <property>
  <name>fs.defaultFS</name>
  <value>hdfs://localhost:9000</value>
 </property>
</configuration>

2.  토픽 생성
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
--replication-factor 1 --partitions 3 --topic select-color 

3.  이벤트 수집 웹페이지 개발 
example/practical_example/favorite-color-webpage/index.html 

4. REST API 프로듀서 애플리케이션 개발
example/practical_example/kafka-spring-producer-with-rest-controller

5. 하둡 적재 컨슈머 애플리케이션 개발
example/practical_example/kafka-multi-consumer-thread-hdfs-save

6. 엘라스틱서치 싱크 커넥터 개발 
example/practical_example/elasticsearch-kafka-connector

## 상용 인프라 아키텍처 
* L4 로드 밸런서 : 웹 이벤트를 받아서 프로듀서로 분배 역활 
* 프로듀서 :  2개 이상의 서버 , 각 서버당 1개 프로듀서 
* 카프카 클러스터 : 3개 이상의 브로커로 구성 
* 컨슈머 : 2개 이상의 서버, 각 서버당 1개 컨슈머 
* 커넥트 : 2개 이상의 서버, 분산 모드 커넥트로 구성 

# 기본 
* 사용자 이벤트 - (Rest Api) -(L4)-> 프로듀서 APP (2대) ---> Kafka(클러스터 브로커3개) ---> 컨슈머 APP(2개) ---> 하둡 
* 사용자 이벤트 - (Rest Api) -(L4)-> 프로듀서 APP (2대) ---> Kafka(클러스터 브로커3개) ---> 분산 커넥트 (2개) ---> 엘라스틱서치 

# 요쳥량 증가 
* 사용자 이벤트 - (Rest Api) -(L4)-> 프로듀서 APP (서버 스케일아웃) -> Kafka(클러스터 브로커3개 - 파티션 개수 증가) 
	-> 컨슈머 APP(2개 - 스레드 개수 증가/ 태스크 개수 증가) -> 하둡 
* 사용자 이벤트 - (Rest Api) -(L4)-> 프로듀서 APP (서버 스케일아웃) -> Kafka(클러스터 브로커3개 - 파티션 개수 증가) 
	-> 분산 커넥트 (2개 - 스레드 개수 증가/ 태스크 개수 증가) -> 엘라스틱서치 

--------------------------------------------

## 서버 지표 수집 파이프라인 생성과 카프카 스트림즈 활용

1.  토픽 생성
- 서버 전체 지표들을 저장하는 토픽 생성 
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
--replication-factor 2 --partitions 3 --topic metric.all 

- CPU 지표를  저장하는 토픽 생성 
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
--replication-factor 2 --partitions 3 --topic metric.cpu

- 메모리 지표를  저장하는 토픽 생성 
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
--replication-factor 2 --partitions 3 --topic metric.memory 

- 비정상  지표를  저장하는 토픽 생성 
$ bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
--replication-factor 2 --partitions 3 --topic metric.cpu.alert

상용 아키텍처에 적용하는 것을 기준으로 replication-factor 2 로 설정 싱글 브로커로 구성된 환경이면 1로 설정 

2.  메트릭비트 설치 및 설정
$ brew install metricbeat
$ brew info metricbeat

$ cd 메트릭설치 bin폴더 
$ vi metricbeat.yaml 
metricbeat.modules:
  - module: system
    metricsets:
      - cpu
      - memory
    enabled: true
    period: 10s
    processes: [".*"]

output.kafka:
  hosts: ["localhost:9092"]
  topic: "metric.all"

  3. 카프카 스트림즈 개발 
  * example/kafka-streams/	  
	- MetricStreams.java
	  MetricJsonUtils.java

스트림즈에서 필요한 JSON 구조
* 전체 CPU 사용량 퍼센티지 : system > cpu > total > norm > pct 값
* 메트릭 종류 추출 : metricset > name 값
* 호스트 이름과 timestamp 값 조합
   * 호스트 이름 : host > name 값 
   * timestamp 값 : @timestamp 값 

4. 기능 테스트 
- 메트릭비트 실행
$ ./metricbeat -c metricbeat.yaml 
확인) $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic metric.all --from-beginning

- 스트림 애플리케이션 실행 
 MetricStreams.java 실행 
 확인) $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic metric.cpu --from-beginning
          $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic metric.cpu.alert --from-beginning

## 상용 인프라 아키텍처 
* 카프카 클러스터 : 3개 이상의 브로커로 구성
* 스트림즈 : 2개 이상의 서버, 각 서버당 1개 스트림즈 애플리케이션 (스케일 아웃을 통해 처리량 늘릴 수 있음)
* 커넥트 : 서버 지표 데이터 저장용, 2개 이상의 서버, 분산 모드 커넥트로 구성 

--------------------------------------------

## 미러메이커2를 사용한 토픽 미러링 

1. config/ connect-mirror-maker.properties 파일 설정
* connect-mirror-maker.properties
    ---------------------------------
	clusters = A, B

	A.bootstrap.servers = a-kafka:9092
	B.bootstrap.servers = b-kafka:9092

	A->B.enabled = true
	A->B.topics = weather.seoul

	B->A.enabled = false
	B->A.topics = .*

	replication.factor=1

	checkpoints.topic.replication.factor=1
	heartbeats.topic.replication.factor=1
	offset-syncs.topic.replication.factor=1
	offset.storage.replication.factor=1
	status.storage.replication.factor=1
	config.storage.replication.factor=1
    ---------------------------------

2. 클러스터 A에 weather.seoul 토픽 생성 
$ bin/kafka-topics.sh --create --bootstrap-server a-kafka:9092 --partitions 3 --topic weather.seoul 

3. 미러메이커2 실행 
$ bin/connect-mirror-maker.sh config/connect-mirror-maker.peoperties
확인) $ bin/kafka-topics.sh --bootstrap-server b-kafka:9092 --list A.weather.seoul 
         $ bin/kafka-topics.sh --bootstrap-server b-kafka:9092 --list A.weather.seoul  --describe

$ bin/kafka-console-producer.sh --bootstrap-server a-kafka:9092 --topic weather.seoul
> sunny
> cloudy

$ bin/kafka-console-consumer.sh --bootstrap-server b-kafka:9092 --topic A.weather.seoul --from-beginning

## 상용 인프라 아키텍처 
* 미러메이커2 : 2개 이상의 서버 

=================================================================================

#  ETC 
* 로컬 테스트용 카프카 도커 이미지 실행
$ git clone https://github.com/AndersonChoi/kafka-docker.git
$ cd kafka-docker
$ docker-compose -f docker-compose-single-broker.yml up -d 
$ docker ps 
$ docker-compose down 