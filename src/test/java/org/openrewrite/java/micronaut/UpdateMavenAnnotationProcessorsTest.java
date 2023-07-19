/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java.micronaut;

import org.junit.jupiter.api.Test;
import org.openrewrite.config.Environment;
import org.openrewrite.test.RecipeSpec;

import static org.openrewrite.maven.Assertions.pomXml;

public class UpdateMavenAnnotationProcessorsTest extends Micronaut4RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipes(Environment.builder()
          .scanRuntimeClasspath("org.openrewrite.java.micronaut")
          .build()
          .activateRecipes("org.openrewrite.java.micronaut.UpdateMavenAnnotationProcessors"));
    }

    @Test
    void updateCoreMavenAnnotationProcessors() {
        rewriteRun(
          //language=xml
          pomXml("""
            <project>
                <groupId>com.mycompany.app</groupId>
                <artifactId>my-app</artifactId>
                <version>1</version>
                <parent>
                    <groupId>io.micronaut.platform</groupId>
                    <artifactId>micronaut-parent</artifactId>
                    <version>%s</version>
                </parent>
                <dependencies>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-client</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-server-netty</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-jackson-databind</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>ch.qos.logback</groupId>
                        <artifactId>logback-classic</artifactId>
                        <scope>runtime</scope>
                    </dependency>
                </dependencies>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>io.micronaut.maven</groupId>
                            <artifactId>micronaut-maven-plugin</artifactId>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                <annotationProcessorPaths combine.children="append"> 
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-inject-java</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-http-validation</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-graal</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                </annotationProcessorPaths>
                                <compilerArgs>
                                    <arg>-Amicronaut.processing.group=com.example</arg>
                                    <arg>-Amicronaut.processing.module=demo</arg>
                                </compilerArgs>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """.formatted(latestMicronautVersion), """
            <project>
                <groupId>com.mycompany.app</groupId>
                <artifactId>my-app</artifactId>
                <version>1</version>
                <parent>
                    <groupId>io.micronaut.platform</groupId>
                    <artifactId>micronaut-parent</artifactId>
                    <version>%s</version>
                </parent>
                <dependencies>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-client</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-server-netty</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-jackson-databind</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>ch.qos.logback</groupId>
                        <artifactId>logback-classic</artifactId>
                        <scope>runtime</scope>
                    </dependency>
                </dependencies>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>io.micronaut.maven</groupId>
                            <artifactId>micronaut-maven-plugin</artifactId>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                <annotationProcessorPaths combine.children="append">       
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-inject-java</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-http-validation</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-graal</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                </annotationProcessorPaths>
                                <compilerArgs>
                                    <arg>-Amicronaut.processing.group=com.example</arg>
                                    <arg>-Amicronaut.processing.module=demo</arg>
                                </compilerArgs>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """.formatted(latestMicronautVersion)));
    }

    @Test
    void updateCoreMavenAnnotationProcessorsAndDontModifyModuleProcessors() {
        rewriteRun(
          //language=xml
          pomXml("""
            <project>
                <groupId>com.mycompany.app</groupId>
                <artifactId>my-app</artifactId>
                <version>1</version>
                <parent>
                    <groupId>io.micronaut.platform</groupId>
                    <artifactId>micronaut-parent</artifactId>
                    <version>%s</version>
                </parent>
                <dependencies>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-client</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-server-netty</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-jackson-databind</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>ch.qos.logback</groupId>
                        <artifactId>logback-classic</artifactId>
                        <scope>runtime</scope>
                    </dependency>
                </dependencies>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>io.micronaut.maven</groupId>
                            <artifactId>micronaut-maven-plugin</artifactId>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                <annotationProcessorPaths combine.children="append">
                                    <path>
                                        <groupId>io.micronaut.data</groupId>
                                        <artifactId>micronaut-data-processor</artifactId>
                                        <version>${micronaut.data.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-inject-java</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-http-validation</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-graal</artifactId>
                                        <version>${micronaut.version}</version>
                                    </path>
                                </annotationProcessorPaths>
                                <compilerArgs>
                                    <arg>-Amicronaut.processing.group=com.example</arg>
                                    <arg>-Amicronaut.processing.module=demo</arg>
                                </compilerArgs>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """.formatted(latestMicronautVersion), """
            <project>
                <groupId>com.mycompany.app</groupId>
                <artifactId>my-app</artifactId>
                <version>1</version>
                <parent>
                    <groupId>io.micronaut.platform</groupId>
                    <artifactId>micronaut-parent</artifactId>
                    <version>%s</version>
                </parent>
                <dependencies>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-client</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-http-server-netty</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>io.micronaut</groupId>
                        <artifactId>micronaut-jackson-databind</artifactId>
                        <scope>compile</scope>
                    </dependency>
                    <dependency>
                        <groupId>ch.qos.logback</groupId>
                        <artifactId>logback-classic</artifactId>
                        <scope>runtime</scope>
                    </dependency>
                </dependencies>
                <build>
                    <plugins>
                        <plugin>
                            <groupId>io.micronaut.maven</groupId>
                            <artifactId>micronaut-maven-plugin</artifactId>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                <annotationProcessorPaths combine.children="append">
                                    <path>
                                        <groupId>io.micronaut.data</groupId>
                                        <artifactId>micronaut-data-processor</artifactId>
                                        <version>${micronaut.data.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-inject-java</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-http-validation</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                    <path>
                                        <groupId>io.micronaut</groupId>
                                        <artifactId>micronaut-graal</artifactId>
                                        <version>${micronaut.core.version}</version>
                                    </path>
                                </annotationProcessorPaths>
                                <compilerArgs>
                                    <arg>-Amicronaut.processing.group=com.example</arg>
                                    <arg>-Amicronaut.processing.module=demo</arg>
                                </compilerArgs>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """.formatted(latestMicronautVersion)));
    }
}
