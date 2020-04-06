tidy:
	astyle --style=java --recursive src/*.java
	find . -type f -name '*.orig' -delete

run:
	./mvnw spring-boot:run