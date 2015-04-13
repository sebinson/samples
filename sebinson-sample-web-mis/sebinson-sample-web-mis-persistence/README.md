#请查看docs目录下相关文档
    myBatis generator configuration
    
    1. pom.xml plugin:
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-maven-plugin</artifactId>
                    <version>1.3.2</version>
                    <configuration>
                        <verbose>true</verbose>
                        <overwrite>true</overwrite>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    
    2. generatorConfig.xml
        docs/generatorConfig.xml
        
    3. project： run as > maven build > goals:mybatis-generator:generate