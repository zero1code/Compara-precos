![ic_launcher](https://github.com/zero1code/Compara-precos/assets/59886392/d40e585d-45fe-453d-ba5a-3c36edba9626)

# Compara Preços

Compara Preços foi criado para ajudar na comparação de preços das suas listas de compras.

<img src="https://github.com/zero1code/Compara-precos/assets/59886392/0df0d00c-8f58-4ddb-888c-389850db3bff" width="186"/>
<img src="https://github.com/zero1code/Compara-precos/assets/59886392/fa566e2e-ba38-407d-a0ab-06df5f1d1a7c" width="186"/>
<img src="https://github.com/zero1code/Compara-precos/assets/59886392/6ab9e342-8d71-4cff-8ec9-e1df2c6b8047" width="186"/>
<img src="https://github.com/zero1code/Compara-precos/assets/59886392/4f226db8-1ea0-49e5-aa70-eaf8cdb11e5b" width="186"/>

## Download

<a href='https://play.google.com/store/apps/details?id=com.z1.comparaprecos'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width=240/></a>

## Rodar o projeto

+ Renomeie o arquivo `fake_local.properties` para `local.properties` e adicione os Ids necessários do [Admob](https://apps.admob.com/)
+ Ou remova os anúncios dos arquivos `Application.kt` e `AndroidManifest.xml`

## Tecnologias

Este projeto utiliza as tecnologias mais modernas do ecossistema Android.

* Tecnologias
  * [Kotlin](https://kotlinlang.org/)
    + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    + [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
    + [Kotlin Symbol Processing](https://kotlinlang.org/docs/ksp-overview.html)
    + [Kotlin Immutable Collections](https://github.com/Kotlin/kotlinx.collections.immutable)
  * [Jetpack](https://developer.android.com/jetpack)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  * [Hilt](https://dagger.dev/hilt/)
* Arquitetura
  * Single activity architecture
  * MVVM
  * [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
  * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)
    ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    , [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
    , [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
  * [Android KTX](https://developer.android.com/kotlin/ktx)
* UI
  * [Jetpack Compose](https://developer.android.com/jetpack/compose)
  * [Material Design 3](https://m3.material.io/)
    * [Dark Theme](https://material.io/develop/android/theming/dark)
    * [Dynamic Theming](https://m3.material.io/styles/color/dynamic-color/overview)
* CI
  * [GitHub Actions](https://github.com/features/actions)
* Testing
  * [MockK](https://mockk.io/)
  * [Jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
* Gradle
  * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
  * [Versions catalog](https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog)
  * [Type safe accessors](https://docs.gradle.org/7.0/release-notes.html)
 
## License

```
Copyright 2024 Compara Preços

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
