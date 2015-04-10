jar包导入到maven仓库

E:\git-workspace\samples\sebinson-sample-cache-ehcache\sebinson-sample-cache-ehcache-single\lib\ehcache-web-2.0.4.jar

mvn install:install-file -Dfile=E:\git-workspace\samples\sebinson-sample-cache-ehcache\sebinson-sample-cache-ehcache-single\lib\ehcache-web-2.0.4.jar -DgroupId=net.sf.ehcache -DartifactId=ehcache-web -Dversion=2.0.4 -Dpackaging=jar