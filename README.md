# YasudisCFT
## Тестовое задание 
## Курс JAVA.
Описание задачи по ссылке в гите https://github.com/yasudis/FilterTypeContentInFile/blob/master/Java_TestTask.pdf

## Инструкция по запуску
В директории с проектом написать в командную строку:

* mvn compile
* mvn package
* cd target
* java -jar FilterTypeContentInFile-1.0-SNAPSHOT.jar -s -a -p sample- in1.txt in2.txt

## Стек технологий
Версия Java : 17.0.0 
Сборка : Maven : 4.0.0
Использованные библиотеки:
<dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.7.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
