@echo off
echo Telechargement du wrapper Gradle...

set GRADLE_VERSION=8.5
set WRAPPER_JAR=gradle\wrapper\gradle-wrapper.jar
set WRAPPER_URL=https://raw.githubusercontent.com/gradle/gradle/v%GRADLE_VERSION%/gradle/wrapper/gradle-wrapper.jar

if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Telechargement de gradle-wrapper.jar...
powershell -Command "Invoke-WebRequest -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"

if exist "%WRAPPER_JAR%" (
    echo Wrapper telecharge avec succes.
) else (
    echo Echec du telechargement. Tentative alternative...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile 'gradle.zip'"
)

echo Configuration terminee.
