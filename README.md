# KSP Processor for Toothpick [![Release](https://jitpack.io/v/fr.outadoc/toothpick-ksp.svg)](https://jitpack.io/#fr.outadoc/toothpick-ksp) [![Build and test](https://github.com/outadoc/toothpick-ksp/actions/workflows/build.yml/badge.svg)](https://github.com/outadoc/toothpick-ksp/actions/workflows/build.yml)

## Background

[Toothpick](https://github.com/stephanenicolas/toothpick) is a Dependency Injection library for Java & Kotlin.

An important part of Toothpick is its ability to generate code at compile-time to avoid doing expensive reflection at
runtime. This is handled by an *annotation processor*, which uses Java's `apt` and Kotlin's `kapt` APIs.

Recently, Google has released an alternative, modern annotation processor, built for Kotlin and compatible with Java
projects: KSP (Kotlin Symbol Processor). This API is much faster than `kapt` on Kotlin projects.

## Goals

This projects aims to reimplement Toothpick's annotation processor with Kotlin-based technologies. It now uses KSP for
better build performance, and generates Kotlin code for improved type safety.

> **Important note:** This processor is mostly a drop-in replacement for the official `kapt` processor, but compatibility was *not* a main goal. You might need to make small changes to your code in order to build using this module.
>
> This is in part because of fundamental differences in the way KSP models Kotlin code compared to `kapt`, and in part because of the differences in generated Kotlin code vs. Java code (no `package` visibility modifier, for example.)

## Setup

```kotlin
plugins {
    // Remove this if you don't have any kapt(...) processors left in your dependencies:
    // kotlin("kapt") version "..."

    // Use the version that matches your Kotlin version!
    id("com.google.devtools.ksp") version "1.7.0-1.0.6"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Remove this:
    // kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:...")

    ksp("fr.outadoc.toothpick-ksp:compiler:0.0.5")
}

ksp {
    // If you need to, specify some extra options.
    // See ToothpickOptions.kt for documentation.
    arg("option1", "value1")
    arg("option2", "value2")
    // ...
}
```
