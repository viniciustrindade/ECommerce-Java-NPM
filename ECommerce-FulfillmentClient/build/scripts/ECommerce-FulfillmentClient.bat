@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  ECommerce-FulfillmentClient startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and E_COMMERCE_FULFILLMENT_CLIENT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\ECommerce-FulfillmentClient.jar;%APP_HOME%\lib\dot_net _webservice_stub.jar;%APP_HOME%\lib\orderservice_stub.jar;%APP_HOME%\lib\ojdbc6.jar;%APP_HOME%\lib\ECommerce-Shared.jar;%APP_HOME%\lib\aws-java-sdk-sqs-1.9.30.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\servlet-api-2.5.jar;%APP_HOME%\lib\jms-api-1.1-rev-1.jar;%APP_HOME%\lib\javax.mail-1.5.2.jar;%APP_HOME%\lib\jaxws-api-2.2.11.jar;%APP_HOME%\lib\commons-dbcp-1.4.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.7.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\spring-core-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-web-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-context-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-jdbc-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-jms-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-orm-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-tx-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-3.2.11.RELEASE.jar;%APP_HOME%\lib\org.eclipse.persistence.jpa-2.5.2.jar;%APP_HOME%\lib\castor-xml-1.3.3.jar;%APP_HOME%\lib\axis-1.4.jar;%APP_HOME%\lib\axis-jaxrpc-1.4.jar;%APP_HOME%\lib\axis-1.4.jar;%APP_HOME%\lib\aws-java-sdk-core-1.9.30.jar;%APP_HOME%\lib\activation-1.1.jar;%APP_HOME%\lib\jaxb-api-2.2.9.jar;%APP_HOME%\lib\javax.xml.soap-api-1.3.5.jar;%APP_HOME%\lib\javax.annotation-api-1.2-b03.jar;%APP_HOME%\lib\jsr181-api-1.0-MR1.jar;%APP_HOME%\lib\commons-pool-1.5.4.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\commons-logging-1.1.3.jar;%APP_HOME%\lib\spring-aop-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-beans-3.2.11.RELEASE.jar;%APP_HOME%\lib\spring-expression-3.2.11.RELEASE.jar;%APP_HOME%\lib\javax.persistence-2.1.0.jar;%APP_HOME%\lib\org.eclipse.persistence.asm-2.5.2.jar;%APP_HOME%\lib\org.eclipse.persistence.antlr-2.5.2.jar;%APP_HOME%\lib\org.eclipse.persistence.jpa.jpql-2.5.2.jar;%APP_HOME%\lib\org.eclipse.persistence.core-2.5.2.jar;%APP_HOME%\lib\castor-core-1.3.3.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\commons-collections-3.2.1.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\stax-1.2.0.jar;%APP_HOME%\lib\stax-api-1.0-2.jar;%APP_HOME%\lib\axis-jaxrpc-1.4.jar;%APP_HOME%\lib\axis-saaj-1.4.jar;%APP_HOME%\lib\axis-wsdl4j-1.5.1.jar;%APP_HOME%\lib\commons-discovery-0.2.jar;%APP_HOME%\lib\httpclient-4.3.4.jar;%APP_HOME%\lib\jackson-databind-2.3.2.jar;%APP_HOME%\lib\joda-time-2.8.1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\stax-api-1.0.1.jar;%APP_HOME%\lib\axis-saaj-1.4.jar;%APP_HOME%\lib\httpcore-4.3.2.jar;%APP_HOME%\lib\commons-codec-1.6.jar;%APP_HOME%\lib\jackson-annotations-2.3.0.jar;%APP_HOME%\lib\jackson-core-2.3.2.jar;%APP_HOME%\lib\slf4j-api-1.7.7.jar

@rem Execute ECommerce-FulfillmentClient
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %E_COMMERCE_FULFILLMENT_CLIENT_OPTS%  -classpath "%CLASSPATH%" com.appdynamicspilot.sqs.FulfillmentClient %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable E_COMMERCE_FULFILLMENT_CLIENT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%E_COMMERCE_FULFILLMENT_CLIENT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
