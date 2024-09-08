# Установка и запуск

## Установка

Для установки перейдите в пустую директорию и ввдите команду `git clone https://github.com/Spfeed/TestTaskApp`;

Установите Docker Desktop: [Docker desktop](https://www.docker.com/products/docker-desktop/);

Если вы работаете в linux введите команду `sudo apt-get docker`.

## Запуск

### Первый запуск

Откройте териминал и перейдите в директорию `task`;

В терминале введите команду `docker-compose up --build`;

После завершения сборки убедитесь, что все контейнеры создались, введите команду: `docker ps`, при успешной сборке вы должны увидеть 5 контейнеров (backed, frontend, postgres, grafana, prometheus);

Если вы используете Docker Desktop, то перейдите во вкладку `containers`, нажмите кнопку `Show running containers`, вы должны увидеть 5 контейнеров;

Контейнеры работают по следующим адресам:

- Backend: [http://localhost:8080]([http://localhost:8080/);
- Frontend: [http://localhost:3000]([http://localhost:3000/);
- Prometheus: [http://localhost:9090]([http://localhost:9090/);
- Grafana: [http://localhost:3001](http://localhost:3001/);
- Postgres: [http://localhost:5432]([http://localhost:5432/).

Проверьте доступность контейнеров при помощи ввдения адресов в строку поиска браузера.
В случае проверки postgres попробуйте добавить новый сервер в PgAdmin с адресом `http://postgres:5432` (логин и пароль указаны в файле `application.yml` директории `backend`).

Если все данные отображаются должным образом, то контейнеры запущены и работают.

Остановить работу контейнеров можно при помощи команды: `docker-compose down` или нажатием на клавишу остановки в Docker Desktop.

### Повторный запуск

Для повторного запуска контейнеров введите команду `docker-compose up -d`.

## Использование

### Приложение

Для использования приложение перейдите по адресу [http://localhost:3000]([http://localhost:3000/).

### Grafana

Для просмотра метрик перейдите по адресу [http://localhost:3001](http://localhost:3001/).
Вас попросят ввести логин и пароль: ввдете `username: admin, password: admin`;
Далее вы можете поменять пароль на свой.

Метрики будут достпуны во вкладке `Dashboards` в папке `MyDashboards`.

При повторном запуске введение данных не потребуется.

## Продолжение разработки

Вы можете продолжить разработку проекта для этого вам потребуется скачать Intellij IDEA: [Intellij IDEA](https://www.jetbrains.com/idea/download/?section=windows);
Также скачать VSCode: [VsCode](https://code.visualstudio.com/download);

### Backend

Откройте папку backend в Intellij IDEA, перейдите во вкладку maven и нажмите кнопку `Reload All Maven Projects`;
После загрузки зависимостей вы сможете продолжать разработку.

Если вы хотите увидеть резлуьтат без связи с frontend, то просто запустите приложение и перейдите по адресу: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/), вы увидите список доступных эндпоинтов.

### Frontend

Откройте папку frontend в VSCode, установите плагин, ввдеите в термина команду: `npm install`;
После загрузки зависимотсей можете продолжать разработку.

Если вы хотите увидеть резльтат без связи с сервером, то введите команду `npm run`.

### Просмотр результата

Для применения внесенных изменений перейдите через терминал в директорию `task` и ввдеите команды:

- `docker-compose down`;
- `docker-compose up -d`.
