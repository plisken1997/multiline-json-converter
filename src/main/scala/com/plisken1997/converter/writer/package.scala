package com.plisken1997.converter

package object writer {
  type IoWritter[T] = Array[Byte] => T
}
