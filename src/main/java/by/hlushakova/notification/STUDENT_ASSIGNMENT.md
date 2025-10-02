# Задание: Автоматическое создание фруктов и овощей для ферм

## Что мы делаем?

Создаем систему, которая:
1. **Создает ферму** по запросу
2. **Создает фрукты и овощи** по отдельным запросам
3. **Отправляет уведомления** о всех действиях

## Архитектура (простая схема)

```
Пользователь → Notification Service → Farm Service
                            ↓                      ↓
                      Уведомления            Ферма (отдельно)
                                          Фрукты (по запросу)
                                          Овощи (по запросу)
```

**Что у нас есть:**
- **Farm Service** (порт 8080) - уже готов, управляет фермами, фруктами, овощами
- **Notification Service** (порт 8082) - нужно запустить, координирует всё

**Основные компоненты:**
- `FarmApiClient` - общается с Farm Service
- `ProductFactoryService` - создает случайные фрукты и овощи
- `NotificationController` - принимает запросы от пользователей
- `NotificationManagerService` - координирует всё

## Как создаются фрукты и овощи

### 1. **ProductFactoryService** - генерирует случайные продукты
- Список русских названий фруктов: "Яблоко", "Банан", "Апельсин"...
- Список русских названий овощей: "Морковь", "Картофель", "Помидор"...
- Случайное количество: фрукты 5-25 штук, овощи 5-20 штук

### 2. **ProductApiClient** - создает продукты в Farm Service
- `createFruit()` → POST `/api/fruits?farmId={id}` с данными: name, color, weight
- `createVegetable()` → POST `/api/vegetables?farmId={id}` с данными: name, color, weight
- Вес рассчитывается: фрукты = количество × 0.5, овощи = количество × 0.3

### 3. **FarmApiClient** - координирует процесс
- Создает ферму через POST `/api/farms`
- Создает фрукты через POST `/api/fruits?farmId={id}`
- Создает овощи через POST `/api/vegetables?farmId={id}`

### 4. **Последовательность:**
```
Пользователь → NotificationController → FarmApiClient →
1. Создает ферму → 2. Уведомления

Пользователь → NotificationController → ProductFactoryService → FarmApiClient →
1. Генерирует продукт → 2. Создает фрукт/овощ → 3. Уведомления
```

## Пошаговое выполнение

### Шаг 1: Запустить Farm Service
```bash
cd /path/to/farm-service
./gradlew bootRun
```
**Результат:** Сервис работает на http://localhost:8080

### Шаг 2: Запустить Notification Service  
```bash
cd /path/to/notification-service
./gradlew bootRun
```
**Результат:** Сервис работает на http://localhost:8082

### Шаг 3: Протестировать систему
```bash
# Создать ферму
curl -X POST http://localhost:8082/api/notifications/create-farm \
  -H "Content-Type: application/json" \
  -d '{
    "farmName": "Моя ферма",
    "location": "Москва",
    "notificationMessage": "Создана новая ферма"
  }'

# Создать фрукты и овощи для фермы
curl -X POST http://localhost:8082/api/notifications/create-fruit/1
curl -X POST http://localhost:8082/api/notifications/create-vegetable/1
curl -X POST http://localhost:8082/api/notifications/create-random-product/1

# Проверить что создалось
curl http://localhost:8080/api/farms
curl http://localhost:8080/api/fruits/farm/1
curl http://localhost:8080/api/vegetables/farm/1
```

## Доступные API endpoints

### Создание фермы
```bash
POST /api/notifications/create-farm
# Создает только ферму
```

### Создание продуктов
```bash
POST /api/notifications/create-fruit/{farmId}
# Создает один случайный фрукт для указанной фермы

POST /api/notifications/create-vegetable/{farmId}  
# Создает один случайный овощ для указанной фермы

POST /api/notifications/create-random-product/{farmId}
# Создает один случайный продукт (фрукт или овощ)
```

### Получение информации
```bash
GET /api/notifications/fruits/farm/{farmId}
# Получает фрукты фермы + отправляет уведомления

GET /api/notifications/vegetables/farm/{farmId}
# Получает овощи фермы + отправляет уведомления
```

## Как это работает (простое объяснение)

1. **Пользователь** отправляет запрос: "Создай ферму 'Моя ферма'"
2. **Notification Service** говорит Farm Service: "Создай ферму"
3. **Farm Service** создает ферму и возвращает ID
4. **Отправляет уведомления** о создании фермы

**Для создания продуктов:**
1. **Пользователь** отправляет запрос: "Создай фрукт для фермы 1"
2. **Notification Service** генерирует случайный фрукт
3. **Notification Service** создает фрукт в Farm Service
4. **Отправляет уведомления** о создании фрукта

**Результат:** Отдельные команды → ферма + фрукты + овощи + уведомления

## Вопросы для размышления

1. Что произойдет, если Farm Service недоступен?
2. Как можно добавить больше видов фруктов и овощей?
3. Как отследить, сколько продуктов создалось?
4. Можно ли настроить количество создаваемых продуктов?
5. В чем разница между автоматическим и ручным созданием продуктов?
6. Как можно добавить валидацию для создания продуктов?

---