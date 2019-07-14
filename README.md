# README
## How to configure database
### create database container
```bash
# create docker network
$ docker network create -d bridge --subnet=192.168.100.0/24 sandbox-network

# create docker container 
$ docker run \
  --net=sandbox-network \
  --ip=192.168.100.2 \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_USER=test \
  -e MYSQL_PASSWORD=password \
  -e MYSQL_DATABASE=test \
  -e TZ=Asia/Tokyo \
  -p 3306:3306 \
  --name mysql_sandbox \
  -d mysql:5.7 \
  --character-set-server=utf8
  
# when the container was already created
$ docker network connect --ip 192.168.100.2 sandbox-network mysql_sandbox  
```

### create tables
```sql
USE test;

CREATE TABLE division
(id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE product(
	id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	division INT(10) UNSIGNED NOT NULL,
	created DATETIME NOT NULL,
	name VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE KEY uk_product (division, created),
	CONSTRAINT fk_division
		FOREIGN KEY(division)
		REFERENCES division (id)
		ON DELETE NO ACTION
   ON UPDATE NO ACTION);
   
 INSERT INTO division (name) VALUES 
 ('item')
 ;
```

## launch app server on docker
```bash
# build app serveer container
$ docker build . -t spring_sandbox

# run container with absolute datetime configuration
$ docker run \
  --rm \
  --env=FAKETIME='2020-12-25 20:30:00' \
  --net=sandbox-network \
  --name spring_sandbox \
  -p 8080:8080 \
  spring_sandbox
  
# run container with relative datetime configuration
$ docker run \
  --rm \
  --env=FAKETIME='+60d' \
  --net=sandbox-network \
  --name spring_sandbox \
  -p 8080:8080 \
  spring_sandbox
```