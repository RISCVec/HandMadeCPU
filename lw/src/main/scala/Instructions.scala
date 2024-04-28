package common

import chisel3._
import chisel3.util._

// RISC-V Instructions Define
// RISC-V Instruction width is 32-bit
// 0-6bit: opcode 7bit
// 7-11bit: rd 5bit
// 12-14bit: func3 3bit
// 15-19bit: rs2 5bit
// 20-24bit: rs1 5bit
// 25-31bit: func7 7bit

object Instructions {

    // Load 
    val LW      = BitPat("b???????_?????_?????_010_?????_0000011")
    
}