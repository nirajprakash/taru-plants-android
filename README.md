

# Taru : Plants Search with Weather 

[![APP](https://img.shields.io/badge/APP-1.0.3-E0234E.svg?style=for-the-badge)](https://android-arsenal.com/api?level=24)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=24) ![Hilt](https://img.shields.io/badge/Hilt-2.44-FFCA28?style=for-the-badge)

> Ad Template build using json and android ConstraintLayout.



![](images/taru.webp)
![](images/scan.webp)


## [Download it on Google Play here](https://play.google.com/store/apps/details?id=com.taru)



## Feature Roadmap
- 2023: Insect Detection.
- 2023: Plantation Guidance.


<!-- - [Use Cases](https://github.com/JunkieLabs/3d-ad-template/wiki/Use-Cases) -->

## Setup:
- create `local.properties` file in [root folder](./) if not exists.
- Add `weatherKey`, `trefleKey` and `plantnetKey` in the file as shown below.


```android
...

weatherKey="key here"
trefleKey="key here"
plantnetKey="key here"
```

- Get `weatherKey` from [Open Weather Map](https://openweathermap.org/)
,  `trefleKey` from [Trefle](https://trefle.io/) and `plantnetKey` from [Plantnet](https://my.plantnet.org/)



## Features

Whole template functionality is implemented inside domain folder, where SensorProvider used for listing available sensors and SensorPacketProvider for get packets .

* Use of open source api.
* Usage of android Data binding.
* MVVM Architecture + Clean architecture.
* Android Room Sqlite Database for local cache.
* Theming in M3 for Light and Dark.



## Tech Stack

This project takes advantage of best practices of common libraryies and tools in android.

* [Kotlin](https://kotlinlang.org/)  
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - for background operations  
* [Hilt](https://dagger.dev/hilt/) - for dependency injection  
* [Coil](https://github.com/coil-kt/coil) - image loading library
* [Jetpack libraries](https://developer.android.com/jetpack):
   * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - in-app navigation
   * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
   * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way



<br>

## Show your love :heart: by giving a :star: on this project.

<br>

<br>






# Open Source Credits



- [PlantNet](https://identify.plantnet.org/) for plants detection.
- [Trefle](https://trefle.io) is open source plants database.
- [OpenWeatherMap](https://api.openweathermap.org) used of Weather Forecast.


# License

[![License](https://img.shields.io/:license-apache%202.0-blue.svg?style=for-the-badge)](LICENSE)

