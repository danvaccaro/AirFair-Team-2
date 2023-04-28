@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  FlightFinder startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and FLIGHT_FINDER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\FlightFinder-0.0.1-SNAPSHOT-plain.jar;%APP_HOME%\lib\htmlunit-2.51.0.jar;%APP_HOME%\lib\jsoup-1.10.3.jar;%APP_HOME%\lib\selenium-java-3.14.0.jar;%APP_HOME%\lib\spring-boot-starter-3.0.4.jar;%APP_HOME%\lib\spring-boot-autoconfigure-3.0.4.jar;%APP_HOME%\lib\spring-boot-3.0.4.jar;%APP_HOME%\lib\spring-context-6.0.6.jar;%APP_HOME%\lib\spring-aop-6.0.6.jar;%APP_HOME%\lib\spring-beans-5.3.6.jar;%APP_HOME%\lib\xalan-2.7.2.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\commons-text-1.9.jar;%APP_HOME%\lib\httpmime-4.5.14.jar;%APP_HOME%\lib\htmlunit-core-js-2.51.0.jar;%APP_HOME%\lib\neko-htmlunit-2.51.0.jar;%APP_HOME%\lib\htmlunit-cssparser-1.8.0.jar;%APP_HOME%\lib\commons-io-2.10.0.jar;%APP_HOME%\lib\selenium-opera-driver-3.14.0.jar;%APP_HOME%\lib\httpclient-4.5.14.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-net-3.8.0.jar;%APP_HOME%\lib\dec-0.1.2.jar;%APP_HOME%\lib\salvation2-3.0.0.jar;%APP_HOME%\lib\websocket-client-9.4.42.v20210604.jar;%APP_HOME%\lib\selenium-chrome-driver-4.5.3.jar;%APP_HOME%\lib\selenium-edge-driver-4.5.3.jar;%APP_HOME%\lib\selenium-firefox-driver-4.5.3.jar;%APP_HOME%\lib\selenium-ie-driver-4.5.3.jar;%APP_HOME%\lib\selenium-safari-driver-4.5.3.jar;%APP_HOME%\lib\selenium-support-4.5.3.jar;%APP_HOME%\lib\selenium-chromium-driver-4.5.3.jar;%APP_HOME%\lib\selenium-devtools-v85-4.5.3.jar;%APP_HOME%\lib\selenium-remote-driver-4.5.3.jar;%APP_HOME%\lib\selenium-http-4.5.3.jar;%APP_HOME%\lib\selenium-json-4.5.3.jar;%APP_HOME%\lib\selenium-api-4.5.3.jar;%APP_HOME%\lib\byte-buddy-1.12.23.jar;%APP_HOME%\lib\commons-exec-1.3.jar;%APP_HOME%\lib\commons-codec-1.15.jar;%APP_HOME%\lib\auto-service-1.0.1.jar;%APP_HOME%\lib\auto-common-1.2.jar;%APP_HOME%\lib\guava-31.1-jre.jar;%APP_HOME%\lib\httpcore-4.4.16.jar;%APP_HOME%\lib\okhttp-4.10.0.jar;%APP_HOME%\lib\spring-boot-starter-logging-3.0.4.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\spring-expression-6.0.6.jar;%APP_HOME%\lib\spring-core-6.0.6.jar;%APP_HOME%\lib\snakeyaml-1.33.jar;%APP_HOME%\lib\serializer-2.7.2.jar;%APP_HOME%\lib\xercesImpl-2.12.0.jar;%APP_HOME%\lib\jetty-client-11.0.14.jar;%APP_HOME%\lib\websocket-common-9.4.42.v20210604.jar;%APP_HOME%\lib\jetty-http-11.0.14.jar;%APP_HOME%\lib\jetty-alpn-client-11.0.14.jar;%APP_HOME%\lib\jetty-io-11.0.14.jar;%APP_HOME%\lib\jetty-util-11.0.14.jar;%APP_HOME%\lib\auto-service-annotations-1.0.1.jar;%APP_HOME%\lib\jcommander-1.82.jar;%APP_HOME%\lib\async-http-client-2.12.3.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.89.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.89.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-classes-epoll-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.89.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-transport-classes-kqueue-4.1.89.Final.jar;%APP_HOME%\lib\netty-reactive-streams-2.0.4.jar;%APP_HOME%\lib\netty-handler-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.89.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.89.Final.jar;%APP_HOME%\lib\netty-codec-4.1.89.Final.jar;%APP_HOME%\lib\netty-transport-4.1.89.Final.jar;%APP_HOME%\lib\async-http-client-netty-utils-2.12.3.jar;%APP_HOME%\lib\netty-buffer-4.1.89.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.89.Final.jar;%APP_HOME%\lib\netty-common-4.1.89.Final.jar;%APP_HOME%\lib\opentelemetry-exporter-logging-1.19.0.jar;%APP_HOME%\lib\opentelemetry-sdk-extension-autoconfigure-1.19.0-alpha.jar;%APP_HOME%\lib\opentelemetry-sdk-extension-autoconfigure-spi-1.19.0.jar;%APP_HOME%\lib\opentelemetry-sdk-1.19.0.jar;%APP_HOME%\lib\opentelemetry-sdk-trace-1.19.0.jar;%APP_HOME%\lib\opentelemetry-sdk-metrics-1.19.0.jar;%APP_HOME%\lib\opentelemetry-sdk-logs-1.19.0-alpha.jar;%APP_HOME%\lib\opentelemetry-sdk-common-1.19.0.jar;%APP_HOME%\lib\opentelemetry-semconv-1.19.0-alpha.jar;%APP_HOME%\lib\opentelemetry-exporter-common-1.19.0.jar;%APP_HOME%\lib\opentelemetry-api-logs-1.19.0-alpha.jar;%APP_HOME%\lib\opentelemetry-api-1.19.0.jar;%APP_HOME%\lib\opentelemetry-context-1.19.0.jar;%APP_HOME%\lib\jtoml-2.0.0.jar;%APP_HOME%\lib\okio-jvm-3.0.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.7.22.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.7.22.jar;%APP_HOME%\lib\kotlin-stdlib-1.7.22.jar;%APP_HOME%\lib\logback-classic-1.4.5.jar;%APP_HOME%\lib\log4j-to-slf4j-2.19.0.jar;%APP_HOME%\lib\jul-to-slf4j-2.0.6.jar;%APP_HOME%\lib\spring-jcl-6.0.6.jar;%APP_HOME%\lib\xml-apis-1.4.01.jar;%APP_HOME%\lib\slf4j-api-2.0.6.jar;%APP_HOME%\lib\websocket-api-9.4.42.v20210604.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-3.12.0.jar;%APP_HOME%\lib\error_prone_annotations-2.11.0.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\reactive-streams-1.0.4.jar;%APP_HOME%\lib\jakarta.activation-1.2.2.jar;%APP_HOME%\lib\failsafe-3.3.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.7.22.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\logback-core-1.4.5.jar;%APP_HOME%\lib\log4j-api-2.19.0.jar


@rem Execute FlightFinder
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %FLIGHT_FINDER_OPTS%  -classpath "%CLASSPATH%" main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable FLIGHT_FINDER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%FLIGHT_FINDER_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
