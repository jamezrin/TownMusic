@echo off
echo Installing dependencies...
cd libs
start cmd.exe /c mvn install:install-file -Dfile=Towny.jar -DgroupId=com.palmergames -DartifactId=bukkit.towny -Dversion=0.91.1.0 -Dpackaging=jar
start cmd.exe /c mvn install:install-file -Dfile=MCJukebox.jar -DgroupId=net.mcjukebox.plugin -DartifactId=bukkit -Dversion=2.1 -Dpackaging=jar

echo This script has finished downloading and installing the dependencies, exiting...
exit