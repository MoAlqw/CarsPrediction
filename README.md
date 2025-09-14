# CarsPrediction

**Коротко:** мобильное приложение Android + backend (FastAPI + Ultralytics YOLO) для предсказания состояния кузова (повреждения) и чистоты автомобиля по фото.

---

## Структура репозитория

```
/app                       # Android приложение (Kotlin, Hilt, Retrofit, KSP)
/app/src/main/...          # Activity / Fragments / ViewModels / Repository
libs.versions.toml         # version catalog (AGP / Kotlin / Hilt / KSP и др.)
```

---

## Что делает проект

* На Android: позволяет сделать фото/выбрать изображение и отправить на сервер для анализа. Отображает результат (тип повреждения и чистоту).
* На сервере (FastAPI + Ultralytics): загружает модели (`damage.pt`, `clean.pt`) и возвращает JSON с детекциями для двух моделей: `damage` и `clean`.

---

## Быстрый запуск — backend (локально)

1. Скачайте в моем профиле репозиторий - hostRenderCar

```
git clone https://github.com/MoAlqw/hostRenderCar.git
cd hostRenderCar
pip install -r requirements.txt
```

2. Запустите сервер:

```bash
uvicorn main:app --host 0.0.0.0 --port 8000
```

3. (Опционально) Проверьте локально через curl:

```bash
curl -X POST "http://localhost:8000/predict" -F "file=@/path/to/photo.jpg"
```

**Важно:** на Android физическом устройстве `localhost` указывает на устройство, а не на ваш ноутбук. Для теста с физ. устройством используйте:

* IP машины в локальной сети (`http://192.168.x.y:8000`) — узнайте IP командой `ifconfig`/`ipconfig` на хосте; или
* проброс через `ngrok`: `ngrok http 8000` и используйте полученный HTTPS URL.
* На данный момент - 14.09.2025 сервер запущен и к нему можно обратиться через `https://9106ad4761c3.ngrok-free.app`
---

## Быстрый запуск — Android приложение

1. Откройте проект в Android Studio (Arctic Fox / Flamingo / Giraffe и т.д.).
2. Убедитесь, что в `libs.versions.toml` (или в `build.gradle.kts`) версии Kotlin и KSP совместимы:
3. Проверьте наличие разрешений в `AndroidManifest.xml`: `INTERNET`, `READ_MEDIA_IMAGES`/`READ_EXTERNAL_STORAGE`, `CAMERA`.
4. В `PhotoPredictApi` проверьте базовый URL Retrofit — укажите локальный IP хоста или ngrok URL.
5. Запустите на устройстве.

---

## API контракты

**POST /predict** (multipart/form-data, поле `file`):

Возвращает JSON в формате:

```json
{
  "damage": [ {"class": 2, "confidence": 0.92, "x": ..., "y": ..., ...}, ... ],
  "clean":  [ {"class": 0, "confidence": 0.87, ...}, ... ]
}
```

* *damage* и *clean* — списки обнаруженных объектов. Если ничего не найдено — пустой массив `[]`.
* Поле `class` (int) — индекс класса: например, `0` = clear/clean, `1` = corrosion, `2` = dent, `3` = scratch (зависит от вашей разметки модели).

**Пример curl:**

```bash
curl -X POST "https://<ngrok-or-host>/predict" -F "file=@photo.jpg"
```

---

## Где менять поведение Android-приложения

* `PredictPhotoRepositoryImpl.bitmapToRequestBody` — имя поля form-data (`file`), формат (`image/jpeg`) и качество JPEG можно настроить.
* `PredictionResponse.toDomain()` — место, где вы парсите JSON в доменные сущности `Prediction`.
* `LoadPhotoViewModel` и `ResultPredictionFragment` — UI/State handling.

---

## Testing & Debugging

* Запустите сервер, сделайте `curl` запрос, убедитесь, что JSON корректный.
* В Android включите логирование (Logcat) для Retrofit/OkHttp, чтобы видеть запрос/ответ.
* Если ответ пустой — убедитесь, что модель действительно возвращает детекции и что сервер возвращает корректный `damage` и `clean` как списки.

---

## Предложения по улучшению (roadmap)

* Собрать больше данных и сделать баланс классов (особенно `dent` vs `scratch`).
* Улучшить постобработку: порог confidence, NMS, агрегация детекций в одно значение.
* Локальные условия: учитывать погоду (добавить вызов погоды по GPS и учитывать дождь/грязь), автоматическая предобработка (коррекция яркости).

---
