package common

import chisel3._
import chisel3.util._

object Consts {
    val WORD_LEN        = 32
    val START_ADDR      = 0.U(WORD_LEN.W)
}
