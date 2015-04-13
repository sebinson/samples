#请查看docs目录下相关文档
    myBatis generator configuration
    
    1. pom.xml plugin:
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-maven-plugin</artifactId>
                    <version>${mybatis-generator-maven-plugin-version}</version>
                    <configuration>
                        <verbose>true</verbose>
                        <overwrite>true</overwrite>
                    </configuration>
                    <dependencies>
                        <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                            <version>${mysql-connector-java-version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mybatis.generator</groupId>
                            <artifactId>mybatis-generator-core</artifactId>
                            <version>${mybatis-generator-core-version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mybatis</groupId>
                            <artifactId>mybatis</artifactId>
                            <version>${mybatis-version}</version>
                        </dependency>
                    </dependencies>
                </plugin>
            </plugins>
        </build>
    
    2. generatorConfig.xml
        docs/generatorConfig.xml
        
    3. project： run as > maven build > goals:mybatis-generator:generate