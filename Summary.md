# Отчёт по итогам автоматизации
## Что было запланировано и что реализовано
1. Проведено исследовательское тестирование функционала веб-сервиса покупки тура "Путешествие дня".
2. Составлен план автоматизации
3. Согласно заданию и плану автоматизации, настроена тестовая среда с возможностью запуска и тестирования SUT под двумя разными СУБД (MySQL и PostgreSQL).
4. Автоматизированы заявленные в плане тестовые сценарии. 
5. Составлен отчет по результату прогона тестов.
6. Заведены 12 баг-репортов по найденным дефектам.

## Сработавшие риски
1. Отсутствие ТЗ не позволяло четко определить ожидаемый результат в тестах, что привело к более длительному анализу работы приложения
2. Настройка одновременной поддержки двух БД заняла дольше планируемого времени.
3. Отсутствие у веб-элементов приложения атрибута test-id создало сложности при поиске необходимых CSS-селекторов.

## Общий итог по времени
1. Настройка тестового окружения. Запланировано - 10 часов, затрачено - 15;
Причина: поиск информации о настройке тестового окружения занял больше времени чем предполагалось.
2. Планирование автоматизации тестирования. Запланировано - 20 часов, затрачено - 23;
Причина: отсутствие ТЗ для приложения.
3. Написание и отладка тестов. Запланировано - 40 часов, затрачено - 44 часа;
Причина: большое количество тестовых сценариев и сработавшие риски.
4. Выполнение тестов и заведение отчетов об ошибках. Запланировано - 10 часов, затрачено - 12 часов;
Причина: дефектов было выявлено больше ожидаемого. Их заведение в issues и оформление заняло больше времени.
5. Отчетные документы по итогам автоматизированного тестирования. Запланировано - 10 часов, затрачено - 8 часов;
6. Отчетные документы по итогам автоматизации - 10 часов, затрачено - 8 часов;

Итого: запланировано - 100 часов, затрачено - 110 часов.