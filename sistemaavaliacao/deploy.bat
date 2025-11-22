@echo off
echo Compilando projeto...
mvn clean package

echo Parando Tomcat...
net stop Tomcat9

echo Copiando WAR...
copy /Y target\sistemaavaliacao.war C:\Tomcat\apache-tomcat-9.0.108-windows-x64\webapps\sistemaavaliacao.war

echo Limpando pasta expandida...
rmdir /S /Q C:\Tomcat\apache-tomcat-9.0.108-windows-x64\webapps\sistemaavaliacao

echo Iniciando Tomcat...
net start Tomcat9
echo Aplicacao atualizada!

pause

