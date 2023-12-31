# Отчёт по итогам автоматизации

## Запланировано/Сделано
Было запланировано:
1. Протестировать поля форм.
1. Протестировать поддержку СУБД MySQL и PostgreSQL.
1. Автоматизировать позитивные и негативные сценарии тестирования работы сервиса для способов покупки тура через карту и через кредит.

Сделаны всё запланированные тесты.
Были использованы все инструменты кроме Appveyor, т.к. поддержка CI не была реализована в данном случае.

## Сработавшие риски
1. Отсутствие технической документации не позволяло четко определить ожидаемый результат в тестах
2. Сложности с подключением БД.
3. Отсутствие у веб-элементов приложения атрибута test-id создало сложности с локаторами элеметов при составление page objects.

## Общий итог по времени
1. Планирование автоматизации тестирования - 10 часов / факт 15 часов
1. Написание кода тестов - 48 часов / факт 50 часов
1. Подготовка отчётных документов по итогам автоматизированного тестирования - 10 / факт 14 часов

В целом, фактически затраченное время оказалось близким к прогнозному.