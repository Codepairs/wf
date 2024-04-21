# WealthFamily API

Это API является проектным заданием по курсу Операционные Системы.

## Используемые технологии

- Spring
- Hibernate
- Postgresql

# Инструкция по запуску Maven проекта

1. Откройте командную строку.

2. Перейдите в желаемую директорию используя команду `cd <путь до директории>`, внутри которой будет создана корневая
   директория `wf` проекта.

3. Склонируйте этот репозиторий используя команду `git clone -b api https://github.com/Codepairs/wf.git`

4. Перейдите в коревую директорию используя команду `cd wf`

5. [Установите Maven](https://www.educative.io/answers/how-to-install-maven-in-windows-and-linux-unix), если у вас его
   нет
6. Соберите проект командой `mvn clean package`, чтобы удалить предыдущие собранные файлы(если они были) и создать jar
   файл проекта в директории `target`.
7. [Установите pgAdmin4](https://www.pgadmin.org/download/), если у вас его нет

8. [Создайте PostgreSQL базу данных](https://www.pgadmin.org/docs/pgadmin4/development/connecting.html) для работы
   проекта

9. Отредактируйте файл `application.properties`, который находится в папке `src/main/resources`, добавив информацию для
   подключения к вашей базе данных. Название бд берем из созданной бд. Для url будет hostname, port, username, password
   из (тык на ваш сервер) --> Properties...  
   Пример:
   ```
   spring.datasource.url=jdbc:mysql://localhost:2020/mydbname
   spring.datasource.username=mydbusername
   spring.datasource.password=mydbpassword
   ```

10. Теперь можно запустить проект, используя команду `java -jar target/myapp-0.0.1-SNAPSHOT.jar`.

В итоге должен собраться и запуститься Maven проект для учета товаров на складе.

Как только запустился проект, вы можете отправлять запросы на запущенный локально WealthFamily API.

Все доступные запросы можно посмотреть при помощи [SwaggerEditor](https://editor.swagger.io/).

1. Переходим по ссылке на [SwaggerEditor](https://editor.swagger.io/)
2. Сверху слева тыкаем file -> Import file
3. В открывшейся вкладе выбираем файл этого проекта `api.yaml`
4. Вуаля открывается WealthFamilyApi со всеми запросами

Запросики можно прям там и проверить с условием запущенной локально апишки.
(Там не будут тыкаться на сваггере GET запросы, потому что он ноет на то что нельзя добавлять RequestBody,
но не бойтесь оно работает везде кроме сваггера...)
