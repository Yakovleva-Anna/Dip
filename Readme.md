# Дипломный проект профессии «Тестировщик» #

## Запуск SUT, авто-тестов и генерация репорта ##
1. Запустить Docker Desktop

2. Склонировать проект и открыть в IntelliJ IDEA

### Подключение SUT к MySQL ###


3. В терминале IntelliJ IDEA в корне проекта запустить контейнеры командой:

` docker-compose up `

Дождаться подъёма контейнеров

4. В терминале IntelliJ IDEA выполнить команду для запуска приложения:

` java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`

5. После запуска приложения сервис будет доступен в браузере по адресу: http://localhost:8080/

6. Открыть второй терминал в IntelliJ IDEA, запустить тесты командой:

` ./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"` 

Дождаться окончания тестирования

7. В терминале IntelliJ IDEA запустить генерацию отчета о тестировании с помощью команды: 

` ./gradlew allureServe` 

Отчёт автоматически откроется в браузере. 

8. После просмотра отчета о тестировании закрыть страницу браузера и во втором терминале IntelliJ IDEA нажать 

` CTRL + C -> y -> Enter`

9. Перейти в первый терминал и остановить приложение:

` CTRL + C`

10. Остановить контейнеры командой :

` docker-compose down` 

### Подключение SUT к PostgreSQL ###

3. В терминале IntelliJ IDEA в корне проекта запустить контейнеры командой:

` docker-compose up `

Дождаться подъёма контейнеров

4. В терминале IntelliJ IDEA выполнить команду для запуска приложения:

` java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar `

5. После запуска приложения сервис будет доступен в браузере по адресу: http://localhost:8080/

6. Открыть второй терминал в IntelliJ IDEA, запустить тесты командой:

` ./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app" `

Дождаться окончания тестирования

7. В терминале IntelliJ IDEA запустить генерацию отчета о тестировании с помощью команды:

` ./gradlew allureServe`

Отчёт автоматически откроется в браузере.

8. После просмотра отчета о тестировании закрыть страницу браузера и во втором терминале IntelliJ IDEA нажать

` CTRL + C -> y -> Enter`

9. Перейти в первый терминал и остановить приложение:

` CTRL + C`

10. Остановить контейнеры командой :

` docker-compose down` 



