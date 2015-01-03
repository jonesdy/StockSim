cd ..
mvn package

sudo rm -rf /usr/share/tomcat7/webapps/StockSim
sudo rm /usr/share/tomcat7/webapps/StockSim.war
sudo cp ./target/StockSim.war /usr/share/tomcat7/webapps

cd script
