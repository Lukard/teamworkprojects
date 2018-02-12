# Yak projects

An example app to show what I can do to Teamwork team. You will find two screens. In the first one you will see the list of projects in your Yak projects account. And then by clicking a project you will see the second screen with the detail of the project and a list of its tasks.

## Getting started

When the project is cloned you need to provide the API key as a variable in your non-versioned file local.properties (the same file that contains the path of the Android SDK in the variable sdk.dir).

```
api.key="typeHereYourAPIKey"
```

Once this is set up you are ready to go!

## Kotlin

The language selected for this project is Kotlin. It uses a prefect combination between Object Oriented Programing and Functional Programing. You may find some useful features in the project like:

* Extensions files like ViewExtensions.kt that adds some methods to View related classes to make easier to operate them.
* Functional list methods like map or foreach.
* Some useful variable declarations like the *lateinit* modifier or the *by lazy* delegation property.

## Architecture & Patterns

This project use an MVVM architecture with some additions:

* **Android Architecture** components are used for 3 purposes:
  * To easily implement MVVM architecture (ViewModel).
  * To exchange lifecycle aware data between the views and the viewmodel (LiveData).
  * To give the app the possiblity to work offline by persisting webservice data (Room).
* **Repository pattern** to manage webservice and persisted data.
* **Dependency Injection**. Koin is used in this case for dependency injection. It is a quite famous and easy to use library to achieve dependency injection in Kotlin.

## RxJava

RxJava / RxAndroid / RxKotlin is used to operate between Bussiness layer and Data layer. We explode the capacity to react to data events like doOnSubscribe to be notified when someone gets subscribed. It is also really useful for handling errors.

## Test

This projects contains some Unit tests and Instrumentation tests that try to cover the most cases that the code may face at some point.