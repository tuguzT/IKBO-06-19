# Практическая работа №6

## Задача

На основе изучения материала лекций по дисциплине «Тестирование и
верификация программного обеспечения» требуется выполнить следующее:

1. Реализовать взаимодействующую объектно-ориентированную
систему (несколько объектов) с нуля на языке *Eiffel*. Можно использовать
темы заданий по объектно-ориентированному программированию.

2. Логика должна быть реализована внутри объектов, также должен
быть объект приложения, который создает все объекты, запускает
взаимодействие и печатает состояние.

3. Выделить ограничения на свойства и результаты поведения. Описать
контракты на объекты.

4. Реализовать взаимодействующую систему с помощью принципа
*SCOOP*. Для этого каждый объект должен работать в бесконечном цикле и
взаимодействовать с другими объектами. Использовать пример со спящим
парикмахером.

5. Добавить контракты в уже имеющуюся программную систему на
*.NET*.

6. Всю обработку ошибок произвести с помощью описания контрактов
и их проверки.

## Внесенные изменения

Все проекты были реализованы на языке *Kotlin*, имеющем 
нативную поддержку для контрактов в пакете **kotlin.contracts**.
