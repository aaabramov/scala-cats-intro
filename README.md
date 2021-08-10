# Scala cats demo project

## Running examples:

```shell
$ sbt
sbt:cats-demo> runMain com.github.aaabramov.catz.demo.P1_TypeClasses
The person name Alexander is 20 year(s) old
Person(A********,20)

sbt:cats-demo> runMain com.github.aaabramov.catz.demo.P8_Functor
Some(JOE)
None
List(JOE, SAM, CHLOE)
List()
``` 

## Useful links:

- cats book:
  - [HTML](https://www.scalawithcats.com/dist/scala-with-cats.html)
  - [PDF](https://www.scalawithcats.com/dist/scala-with-cats.pdf)
  - [ePUB](https://www.scalawithcats.com/dist/scala-with-cats.epub)
  - [Source code](https://github.com/scalawithcats/scala-with-cats)  
- [cats typeclasses hierarchy](https://blog.rockthejvm.com/cats-typeclass-hierarchy/)
- [All cats typeclasses](https://typelevel.org/cats/typeclasses.html)
- Awesome scala lessons [on YouTube](https://www.youtube.com/rockthejvm)
- [cats 101 on YouTube](https://www.youtube.com/results?search_query=Rock+the+JVM+cats)
- To become a PRO: [herding cats](https://eed3si9n.com/herding-cats/index.html)
- [Library](https://github.com/typelevel/simulacrum) to generate type class boilerplate (ops, syntax, implicits, etc.)

## cats typeclasses hierarchy
![Cats typeclasses hierarchy](cats_typeclasses.svg)
