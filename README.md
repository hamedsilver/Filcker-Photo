<h1 align="center"> Filcker photo </h1> <br>

## Table of Contents

-   [Introduction](#introduction)
-   [Features](#features)
-   [Architecture](#architecture)
-   [Roadmap (Features to be added )](#roadmap)
-   [Download](#download)
-   [Getting started](#getting-started)



## Introduction

<!-- [![Build Status](https://img.shields.io/travis/gitpoint/git-point.svg?style=flat-square)](https://travis-ci.org/gitpoint/git-point)-->

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

An Android Application written purely in Kotlin utilizing all the latest tech in Android. The project uses Material Design guidelines, Clean + MVI architecture, Dependency Injection and uses Room.
The data is fetched from <a href='https://www.flickr.com/services/api/'>Flickr API</a>


## Features

A few of the things you can do with app:

-   Search
-   Bookmark
-   Search History
-   FullScreen Image

<p align="center">
  <img src = https://github.com/hamedsilver/Filcker-Photo/blob/main/screenshots/history.jpg width=240 height=420>
    <img src = https://github.com/hamedsilver/Filcker-Photo/blob/main/screenshots/search.jpg width=240 height=420>
      <img src = https://github.com/hamedsilver/Filcker-Photo/blob/main/screenshots/detail.jpg width=240 height=420>
</p>

## Architecture


This application implements the following concepts :
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
-   MVI + View binding
-   Navigation Component
-   ViewModel
-   Repository
-   Unit Test
- [Kotlin](https://kotlinlang.org/)  
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)  
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
- [Room](https://developer.android.com/topic/libraries/architecture/room) - For storing Database.
- [Glide](https://github.com/bumptech/glide) - Glide supports fetching, decoding, and displaying video stills, images, and animated GIFs.



## Roadmap (Features to be added 🛠️ )
- Using Jetpack compose
- Animations using Jetpack compose
- Pagination for search list
- Modularize Project 


## Download

[Download apk file](/app/release/app-release.apk)

## Getting started

-   Clone this repository
-   Import the repository to Android Studio
-   Build
