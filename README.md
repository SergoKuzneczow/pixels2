<h1 align="center">Pixels2</a> 
<h2 align="left">About project</h2>
  <body>
    <p align="left">Hi there <img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="24"/>.
    <br>The project Pixels2 was created with the aim of exploring the capabilities of Jetpack Compose. This application uses the open API of the <a href="https://wallhaven.cc/help/api">"wallhaven"</a> service and allows the user to view graphic resources hosted on this service.</p>
  </body>
<h2 align="left">Tech stack</h2>
  <body>
    <p align="left">In addition to Jatpack Compose, the following programming languages, technologies, and frameworks were used in writing this project:
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
