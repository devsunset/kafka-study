--------------------------------------------------------------------------------

			# KAFKA-WORK #

--------------------------------------------------------------------------------

########################################################
### KAFKA

https://kafka.apache.org/

https://kafka.apache.org/documentation/

https://github.com/apache/kafka

https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Improvement+Proposals

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

* server.properties 파일 내용 체크 
$ vi config/server.properties 
하단 내용 설정 
#advertised.listeners=PLAINTEXT://your.host.name:9092
advertised.listeners=PLAINTEXT://127.0.0.1:9092

* kafka broker 실행 
$ bin/kafka-server-start.sh -daemon config/server.properties
$ jps -m 
$ tail -f logs/server.log

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

### example
* example/kafka-producer/ 
	- SimpleProducer.java
	- ProducerWithKeyValue.java
	- ProducerExactPartition.java
	- ProducerWithCustomPartitioner.java
	  CustomPartitioner.java
	- ProducerWithSyncCallback.java
	- ProducerWithAsyncCallback.java
	  ProducerWithAsyncCallback.java

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

* example/kafka-admin/	  
	- KafkaAdminClient.java

