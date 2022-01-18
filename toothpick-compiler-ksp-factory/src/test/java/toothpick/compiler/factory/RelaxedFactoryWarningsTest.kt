/*
 * Copyright 2019 Stephane Nicolas
 * Copyright 2019 Daniel Molinero Reguera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package toothpick.compiler.factory

import org.junit.Test
import toothpick.compiler.*
import toothpick.compiler.common.ToothpickOptions.Companion.CrashWhenNoFactoryCanBeCreated

class RelaxedFactoryWarningsTest {

    @Test
    fun testOptimisticFactoryCreationForSingleton_shouldFailTheBuild_whenThereIsNoDefaultConstructor() {
        val source = javaSource(
            "TestOptimisticFactoryCreationForSingleton",
            """
            package test;
            import javax.inject.Inject;
            import javax.inject.Singleton;
            @Singleton
            public class TestOptimisticFactoryCreationForSingleton {
              TestOptimisticFactoryCreationForSingleton(int a) { }
            }
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(FactoryProcessorProvider())
            .withOptions(CrashWhenNoFactoryCanBeCreated to "true")
            .failsToCompile()
    }

    @Test
    fun testOptimisticFactoryCreationForSingleton_shouldNotFailTheBuild_whenThereIsNoDefaultConstructorButClassIsAnnotated() {
        val source = javaSource(
            "TestOptimisticFactoryCreationForSingleton",
            """
            package test;
            import javax.inject.Inject;
            import javax.inject.Singleton;
            @SuppressWarnings("injectable")
            @Singleton
            public class TestOptimisticFactoryCreationForSingleton {
              TestOptimisticFactoryCreationForSingleton(int a) { }
            }
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(FactoryProcessorProvider())
            .withOptions(CrashWhenNoFactoryCanBeCreated to "true")
            .compilesWithoutError()
    }

    @Test
    fun testOptimisticFactoryCreationWithInjectedMembers_shouldFailTheBuild_whenThereIsNoDefaultConstructor() {
        val source = javaSource(
            "TestOptimisticFactoryCreationForSingleton",
            """
            package test;
            import javax.inject.Inject;
            public class TestOptimisticFactoryCreationForSingleton {
              @Inject String s;
              TestOptimisticFactoryCreationForSingleton(int a) { }
            }
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(FactoryProcessorProvider())
            .withOptions(CrashWhenNoFactoryCanBeCreated to "true")
            .failsToCompile()
    }

    @Test
    fun testOptimisticFactoryCreationWithInjectedMembers_shouldNotFailTheBuild_whenThereIsNoDefaultConstructorButClassIsAnnotated() {
        val source = javaSource(
            "TestOptimisticFactoryCreationForSingleton",
            """
            package test;
            import javax.inject.Inject;
            @SuppressWarnings("injectable")
            public class TestOptimisticFactoryCreationForSingleton {
              @Inject String s;
              TestOptimisticFactoryCreationForSingleton(int a) { }
            }
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(FactoryProcessorProvider())
            .withOptions(CrashWhenNoFactoryCanBeCreated to "true")
            .compilesWithoutError()
    }
}