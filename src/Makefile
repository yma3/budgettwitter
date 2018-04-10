server: backend/*.java
	git pull origin master
	javac -classpath .:../lib/* backend/Main.java backend/SQLiteJDBCDriverConnection.java
	java -cp "../lib/*:./" backend.Main

clean: 
	rm -f backend/*.class backend/.*.swp
