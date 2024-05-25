package add

import chisel3._
import chisel3.util._
import common.Instructions._
import common.Consts._

class Core extends Module {
    val io = IO(
        new Bundle{
            val imem = Flipped(new ImemPortIo())
            val dmem = Flipped(new DmemPortIo())
            val exit = Output(Bool())
        }
    )

    val regfile = Mem(32, UInt(WORD_LEN.W))

    //*************************************
    // Instruction Fetch Stage

    val pc_reg = RegInit(START_ADDR)
    pc_reg := pc_reg + 4.U(WORD_LEN.W)

    io.imem.addr := pc_reg
    val inst = io.imem.inst

    

    //*************************************
    // Instruction Decode Stage
    val rs1_addr = inst(19, 15)
    val rs2_addr = inst(24, 20)
    val wb_addr = inst(11, 7)

    val rs1_data = Mux((rs1_addr =/= 0.U(WORD_LEN.W)), regfile(rs1_addr), 0.U(WORD_LEN.W))
    val rs2_data = Mux((rs2_addr =/= 0.U(WORD_LEN.W)), regfile(rs2_addr), 0.U(WORD_LEN.W))

    val imm_i = inst(31, 20)
    val imm_i_sext = Cat(Fill(20, imm_i(11)), imm_i)

    val imm_s = Cat(inst(31, 25), inst(11, 7))
    val imm_s_sext = Cat(Fill(20, imm_s(11)), imm_s)

    //*************************************
    // Instruction Ex Stage
    val alu_out = MuxCase(0.U(WORD_LEN.W), Seq(
        (inst === LW) -> (rs1_data + imm_i_sext),
        (inst === SW) -> (rs1_data + imm_s_sext),
        (inst === ADD) -> (rs1_data + rs2_data),
        (inst === ADDI) -> (rs1_data + imm_i_sext),
        (inst === SUB) -> (rs1_data - rs2_data)
    ))

    io.dmem.addr := alu_out
    when(inst === LW) {
        io.dmem.addr := alu_out
    }

    //*************************************
    // WB Stage
    val wb_data = MuxCase(alu_out, Seq(
            (inst === LW) -> io.dmem.rdata
    ))
    when(inst === LW || inst === ADD || inst === ADDI || inst === SUB) {
        regfile(wb_addr) := wb_data
    }

    //*************************************
    // MEM Stage
    io.dmem.wen := (inst === SW)
    io.dmem.wdata := rs2_data





    //**********************************
    // Debug

    io.exit := (inst === 0x407300b3.U(WORD_LEN.W))
    
    printf(p"pc_reg : 0x${Hexadecimal(pc_reg)}\n")
    printf(p"inst : 0x${Hexadecimal(inst)}\n")
    printf(p"rs1_addr : $rs1_addr\n")
    printf(p"rs2_addr : $rs2_addr\n")
    //printf(p"imm_i : 0x${Hexadecimal(imm_i_sext)}\n")
    printf(p"wb_addr : $wb_addr\n")
    printf(p"rs1_data : 0x${Hexadecimal(rs1_data)}\n")
    printf(p"rs2_data : 0x${Hexadecimal(rs2_data)}\n")
    printf(p"wb_data : 0x${Hexadecimal(wb_data)}\n")
    printf("----------\n")

}
