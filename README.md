<h1 align="center">Pixels2</h1>
<h2 align="left">О проекте</h2>
  <body>
    <p align="left">Всем привет <img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="24"/>.
    <br>Проект Pixels2 был создан с целью исследования возможностей Jetpack Compose. Это приложение использует открытый API сервиса <a href="https://wallhaven.cc/help/api">"wallhaven"</a> и позволяет пользователю просматривать графические ресурсы, размещённые на этом сервисе.</p>
  </body>
<h2 align="left">Технологический стек</h2>
  <body>
    <p align="left">При написании данного проекта использовались следующие языки программирования, технологии и фреймворки:
      <ul>
        <li>Kotlin</li>
        <li>Compose</li>
        <li>Navigation compose</li>
        <li>Dagger</li>
        <li>Retrofit</li>
        <li>Room</li>
        <li>Coil</li>
        <li>Junit4</li>
        <li>Mockito</li>
        <li>Turbine</li>
        <li>Truth</li>
      </ul>
    Более подробную информацию об используемых фреймворках можно найти в файле <a href="https://github.com/SergoKuzneczow/pixels2/blob/main/gradle/libs.versions.toml">libs</a>.
    </p>
  </body>
  <h2 align="left">Интерфейс приложения</h2>
  <body>
    <p align="left">Проект Pixels2 — это простое приложение. Основные экраны и их состояния показаны на следующем <a href="https://github.com/SergoKuzneczow/pixels2/blob/main/Pixels2.png">рисунке</a>.
    <br>
    <br><img src="https://github.com/SergoKuzneczow/pixels2/blob/main/Pixels2.png?raw=true"/>
    </p>
  </body>
  <h2 align="left">Архитектура</h2>
  <body>
    <p align="left">Проект Pixels2 — это многомодульное приложение. Он состоит из трех типов модулей: app module, feature modules, core modules.
    <br>В app module хранятся следующие ключевые элементы: класс Application, MainActivity, top navigation graph и ViewModel, в котором хранятся экземпляры классов, влияющих на глобальное состояние приложения.
    <br>Core modules используются для предоставления или преобразования данных.
    <br>Feature modules хранят «destinations» (composable функции) и предоставляют способы навигации к ним.
</p>
  </body>
<br>
<br>
<br>
<h1 align="center">Pixels2</h1>
<h2 align="left">About project</h2>
  <body>
    <p align="left">Hi there <img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="24"/>.
    <br>The project Pixels2 was created with the aim of exploring the capabilities of Jetpack Compose. This application uses the open API of the <a href="https://wallhaven.cc/help/api">"wallhaven"</a> service and allows the user to view graphic resources hosted on this service.</p>
  </body>
<h2 align="left">Tech stack</h2>
  <body>
    <p align="left">The following programming languages, technologies, and frameworks were used in writing this project:
      <ul>
        <li>Kotlin</li>
        <li>Compose</li>
        <li>Navigation compose</li>
        <li>Dagger</li>
        <li>Retrofit</li>
        <li>Room</li>
        <li>Coil</li>
        <li>Junit4</li>
        <li>Mockito</li>
        <li>Turbine</li>
        <li>Truth</li>
      </ul>
    More detailed information about the frameworks used can be found in the <a href="https://github.com/SergoKuzneczow/pixels2/blob/main/gradle/libs.versions.toml">libs</a> file.
    </p>
  </body>
  <h2 align="left">Application interface</h2>
  <body>
    <p align="left">The Pixels2 project is a simple application. The main screens and their states are shown in the following <a href="https://github.com/SergoKuzneczow/pixels2/blob/main/Pixels2.png">figure</a>.
    <br>
    <br><img src="https://github.com/SergoKuzneczow/pixels2/blob/main/Pixels2.png?raw=true"/>
    </p>
  </body>
  <h2 align="left">Architecture</h2>
  <body>
    <p align="left">The Pixels2 project is a multi-module application. It's divided into three types of modules: app module, feature modules, core modules.
    <br>The application module stores the following key elements: the Application class, MainActivity, the top navigation graph, and the ViewModel, which stores instances of classes that affect the global state of the application.
    <br>The core modules are used to provide or transform data.
    <br>The feature modules store "destinations" (composable functions) and provide ways to "navigate" to them.
    </p>
  </body>