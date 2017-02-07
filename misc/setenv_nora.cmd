
rem ************ Java-Eviroment ****************
#rem set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_07
#set JAVA_HOME=C:\dev\Java\jdk1.7.0_09
#set JAVA_HOME=C:\dev\Java\jdk1.7.0_25
set JAVA_HOME=C:\dev\Java\jdk1.7.0_40
set ANT_HOME=C:\dev\apache-ant-1.8.4

rem ********************* PATH *****************
rem set PATH=.
rem set PATH=%PATH%;C:\WINDOWS\system32
rem set PATH=%PATH%;C:\WINDOWS	

set PATH=%PATH%;%JAVA_HOME%\bin
set PATH=%PATH%;%ANT_HOME%\bin
set PATH=%PATH%;"C:\dev\Inno Setup 5.5.1 UNICODE"
set PATH=%PATH%;"C:\Program Files\WiX Toolset v3.6\bin"


echo %PATH% 
echo %CLASSPATH% 


