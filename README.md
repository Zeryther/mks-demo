# mks-demo Project

This project is an example exercise Spigot plugin, that saves the coordinates of opened trapdoors in a MySQL database.

This plugin utilizes the [kotlin-commons Gigadrive library](https://gitlab.com/Gigadrive/kotlin-commons).

## Requirements

+ Spigot 1.16.5
+ MySQL
+ Java 8
+ Gradle 6.7

## Docker Database

In order to quickly start a MySQL server, run the following Docker command:

```shell
docker run -e MYSQL_ROOT_PASSWORD=password -e MYSQL_ROOT_HOST=% -d -p 3306:3306 mysql/mysql-server:5.7
```
