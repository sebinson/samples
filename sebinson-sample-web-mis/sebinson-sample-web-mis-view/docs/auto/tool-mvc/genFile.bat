
@set classpath=%classpath%;.;.\lib\*

@set PATH=%JAVA_HOME%\bin;%PATH%;
@java -server -Xms128m -Xmx384m com.tool.main.GenFile
@if errorlevel 1 (
@echo ----------------------------------------------
@echo   ****错误***: 请设置好JAVA_HOME环境变量再运行或者检查你的classpath路径
@pause
)

:end
@pause