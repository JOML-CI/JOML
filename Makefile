FILES := $(shell find src -iname '*.ts')
all: target/joml.js
target/joml.js: $(FILES) Makefile
	tsc --out target/joml.js $(FILES)
