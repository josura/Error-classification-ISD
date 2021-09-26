#mvn package
cd target/classes/
rmiregistry &
sleep 1
java -jar ../elastic-server-interface-0.0.1-SNAPSHOT.jar