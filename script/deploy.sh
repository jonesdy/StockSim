cd ..
mvn package

sudo rm -rf /usr/share/tomcat7/webapps/ROOT
sudo rm /usr/share/tomcat7/webapps/ROOT.war
sudo cp ./target/StockSim.war /usr/share/tomcat7/webapps/ROOT.war

cd script
