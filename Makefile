MAIN_CLASS = Main
SRC = src

clean:
	rm -rf *.cache/
	rm -rf *.hw/
	rm -rf *.ip_user_files/
	rm -rf *.sim/
	rm -rf *.runs/
	rm -rf .Xil/
	rm -f *.log *.jou *.str *.pb
	rm -rf build/
	rm -rf out/
	rm -rf target/
	rm -f database.txt database.db
	find . -name "*.iml" -type f -delete
	find . -name "*.class" -type f -delete

compile:
	javac -d out -sourcepath src src/$(MAIN_CLASS).java

run:
	java -cp out $(MAIN_CLASS)
