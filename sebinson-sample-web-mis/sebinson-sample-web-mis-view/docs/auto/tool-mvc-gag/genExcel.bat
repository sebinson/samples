
@set classpath=%classpath%;.;.\jars\*

@set PATH=%JAVA_HOME%\bin;%PATH%;
@java -server -Xms128m -Xmx384m com.tool.main.GenExcel
@if errorlevel 1 (
@echo ----------------------------------------------
@echo   ****错误***: 请设置好JAVA_HOME环境变量再运行或者检查你的classpath路径
@pause
)

:end
@pause