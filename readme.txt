--------------------------------------------------------------------------------

			# KAFKA-WORK #

--------------------------------------------------------------------------------

########################################################
### KAFKA

https://kafka.apache.org/

https://kafka.apache.org/documentation/

https://github.com/apache/kafka

https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Improvement+Proposals

https://www.confluent.io/hub/

https://github.com/linkedin/Burrow

https://ksqldb.io/

########################################################
### Reference

https://kafka.apache.org/quickstart

- 아파치 카프카 - book 예제 소스 
https://github.com/bjpublic/apache-kafka-with-java

- blog
https://www.redhat.com/ko/topics/integration/what-is-apache-kafka
https://pearlluck.tistory.com/288
https://soft.plusblog.co.kr/3
https://velog.io/@jaehyeong/Apache-Kafka아파치-카프카란-무엇인가
https://engkimbs.tistory.com/691

- 동영상 강좌 
https://www.youtube.com/watch?v=VJKZvOASvUA
https://www.youtube.com/watch?v=iUX6d14bvj0
https://www.youtube.com/watch?v=dubFjEXuK6w
https://www.youtube.com/watch?v=oyNjiQ2q2CE
https://www.youtube.com/watch?v=3OPZ7_sHtWo

https://www.youtube.com/watch?v=waw0XXNX-uQ&list=PL3Re5Ri5rZmkY46j6WcJXQYRlDRZSUQ1j

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

=================================================================================

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

=================================================================================

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
