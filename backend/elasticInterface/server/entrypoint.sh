mvn package
cd target/classes/
rmiregistry &
sleep 0.5
cd ../..
java -jar target/elastic-server-interface-0.0.1-SNAPSHOT.jar