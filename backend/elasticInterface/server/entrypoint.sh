#mvn package
cd target/classes/
rmiregistry 5000 &
sleep 1
java -jar ../elastic-server-interface-0.0.1-SNAPSHOT.jar