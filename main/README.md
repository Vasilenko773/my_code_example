#ЗАПУСК
1. Импортировать maven проект.
2. Сборка приложения :
- в папке c pom.xml выполнить команду:

mvn clean install

- в директории /target выполнить команду
  _JAVA_OPTIONS="-DHOST=хост-сервера-на-котором разворачиваеться-приложение -DISSUES=url-для-получения-задач(документация API Redmine)
  -DMAIL-PASSWORD=почта-учетки-отправителя-почты -DMAIL-USERNAME=наименование-акаунта-почтоотправителя
  -DMAIL-USERNAME-TO-1=адресат-1 -DMAIL-USERNAME-TO-2=адресат2
  -DMAIL-USERNAME-TO-3=адресат-3 -DPASSWORD=пароль-учетки-для-получения-данных-из-redmine -DPORT=порт-redmine;PROJECTS=url-для-получения-проектов(документация API Redmine)
  -DREDMINE=url-redmine -DTIME_ENTRIES=url-для-получения-списка-временных-затрат(документация API Redmine)
  -DMAIL-CRON=дата-и-переодичность-отправления-почты -Duser.name=логин-пользователя-redmine
  -DTG.DB.URL=url-БД-телеграмм-бота -DTG.DB.USER=-user-БД-tg-bot -DTG.DB.PASSWORD=пароль-БД-tg-bot -DDATA-CRON=cron-для-очередности обновлнеия данных
   -ISSUES-BY-ID=url-для-получения-задачи-по-id" java -jar soc.tech-0.0.1-SNAPSHOT.jar

  

#ОПИСАНИЕ API
для получения инфрации о сузествующих endpoint нужно перейти по аддресу
http://host:port/swagger-ui/

где host -  хост сервера
port - порт на котором запущенно приложение


