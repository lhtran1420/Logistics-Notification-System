call mvn -f ./pom.xml clean package -Dmaven.test.skip=true

docker build -t abc .

docker-compose up -d