sudo docker-compose down
./gradlew build
sleep 1
sudo docker-compose build
sleep 1
sudo docker-compose up
