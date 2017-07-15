package com.plisken1997.converter.writer

trait JSONWriter[T] {
  def write(row: Array[Byte]): T
}
