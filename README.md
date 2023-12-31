# Дипломый проект профессии "Тестировщик"

## Запуск SUT, автотестов и генерация отчета:

Для запуска приложения на пк должно быть установленно следующее ПО:
* Docker Desktop - Приложение для скачивания и установки контейнеров (в нашем случае понадобится для установки 
и использования БД(MySQL и PostgeSQL) и эмулятора банковского сервиса написанного на NodeJS).
* IntelliJ IDEA - Среда разработки и запуска програм написанных на различных языках.
* Git - Cистема управления версиями с распределенной архитектурой. Нужна для отслеживания и ведения истории 
и изменения файлов в проекте.

### Предварительные условия:

1. Скачать репозиторий на компьютерер. (https://github.com/unicred666/DiplomQa.git).
2. Запустить Docker Decktop.
3. Запустить IntelliJ IDEA.
4. Открыть проект в IntelliJ IDEA.

### Подключение SUT к БД MySQL, запуск тестов и создание отчета:

1. Открыть окно терминала и в командной строке набрать команду:

    `docker-compose up -d`
    
    (запустится установка и запуск контейнеров с БД и эмулятором банковского сервиса, флаг **-d** позволит дальше работать с консолью)

2. Открыть второй терминал и запустить проект командой:

   `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
   
   (ключ **-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar** позволяет подключится к БД **MySQL** на порту **3306** с именем базы **"app"**)
   
3. Открыть третий терминал и запустить тесты командой: 

   `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
   
   (ключ **-Ddb.url=jdbc:mysql://localhost:3306/app** позволяет подключится к БД **MySQL** на порту **3306** с именем базы **"app"**)
   
5. После завершения всех тестов, для генерации отчета в командной строке ввести команду:

   `.\gradlew allureServe`
   
   (Сформируется и запустится в браузере отчет о пройденных тестах)
   
7. Для завершения генерации отчета в командной строке нажать сочетание клавиш:

   **Ctrl + C** -> **y** -> **Enter**
   
9. Для закрытия приложения в консоли в командной строке нажать сочетание клавиш:

   **Ctrl + C**
   
11. Для остановки контейнеров в консоли в командной строке ввести команду:

     `docker-compose down`


### Подключение SUT к БД PostgreSQL, запуск тестов и создание репорта:

1. Открыть окно терминала и в командной строке набрать команду:

    `docker-compose up -d`
   
   (запустится установка и запуск контейнеров с БД и эмулятором банковского сервиса, флаг **-d** позволит дальше работать с консолью)
   
3. Открыть второй терминал и запустить проект командой:

   `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
   
   (ключ **-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app** позволяет подключится к БД **PostgreSQL** на порту **5432** с именем базы **"app"**)
   
4. Открыть третий терминал и запустить тесты командой:

   `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
   
   (ключ **-Ddb.url=jdbc:postgresql://localhost:5432/app** позволяет подключится к БД **PostgreSQL** на порту **5432** с именем базы **"app"**)
   
6. После завершения всех тестов, для генерации отчета в командной строке ввести команду:

   `.\gradlew allureServe`
   
   (Сформируется и запустится в браузере отчет о пройденных тестах)
   
7. Для завершения генерации отчета в командной строке нажать сочетание клавиш:

   **Ctrl + C** -> **y** -> **Enter**
   
8. Для закрытия приложения в консоли в командной строке нажать сочетание клавиш:

   **Ctrl + C**
   
10. Для остановки контейнеров в консоли в командной строке ввести команду:

     `docker-compose down`
    
## Документация:

* [Задание](https://github.com/netology-code/qa-diploma)
* [План автоматизации](https://github.com/unicred666/DiplomQa/blob/main/Documents/Plan.md)
* [Отчётные документы по итогам тестирования](https://github.com/unicred666/DiplomQa/blob/main/Documents/Report.md)
* [Отчётные документы по итогам автоматизации](https://github.com/unicred666/DiplomQa/blob/main/Documents/Summary.md)

