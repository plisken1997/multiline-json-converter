[![Build Status](https://travis-ci.org/plisken1997/multiline-json-com.plisken1997.converter.svg?branch=develop)](https://travis-ci.org/plisken1997/multiline-json-com.plisken1997.converter)

Inline JSON Converter
======

Hi !

This is a simple project to convert inlined JSON to multilined JSON. It works well but needs some refactoring, optimization, tests and so on. 
Still refactoring now with some cool stuff like [cats](https://github.com/non/cats) integration !

### usage
`sbt "run --source=../resources/data/extract.json --dest=../resources/data/nice-output.json"`

@todo
 - add a sample source file into src/main/resources
-  use things like http://typelevel.org/blog/2017/05/02/io-monad-for-cats.html for formated JSON writer

### tests
`sbt test`

@todo
 - the source code is now pretty cleaner, some module / classes must be tested !
 - add test coverage tool

### LICENSE
MIT
