#docker run --rm -it --network host  -e KAFKA_BROKERCONNECT=localhost:49362 -e JVM_OPTS="-Xms64M -Xmx128M"  -e SERVER_SERVLET_CONTEXTPATH="/" obsidiandynamics/kafdrop

docker run --network host -e SERVER_PORT=8092 -e KAFKA_CLUSTERS_0_NAME=local -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=localhost:49362 provectuslabs/kafka-ui:latest