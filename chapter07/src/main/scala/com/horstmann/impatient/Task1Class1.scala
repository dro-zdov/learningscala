//1. Напишите программу, на примере которой можно убедиться, что
//package com.horstmann.impatient
//не то же самое, что и
//package com
//package horstmann
//package impatient

package com.horstmann.impatient {
  class Task1Class1
  //val c3 = new Task1Class3 // Недоступно
  val c3 = new com.horstmann.Task1Class3
}