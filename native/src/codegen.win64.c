/*
** This file has been pre-processed with DynASM.
** http://luajit.org/dynasm.html
** DynASM version 1.3.0, DynASM x64 version 1.3.0
** DO NOT EDIT! The original file is in "codegen.c".
*/

#line 1 "codegen.c"
#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

#include "dynasm/dasm_proto.h"
#include "dynasm/dasm_x86.h"
#include "codegen.h"

//|.arch x64
#if DASM_VERSION != 10300
#error "Version mismatch between DynASM and included encoding engine"
#endif
#line 10 "codegen.c"
//|.section code
#define DASM_SECTION_CODE	0
#define DASM_MAXSECTION		1
#line 11 "codegen.c"
//|.globals GLOB_
enum {
  GLOB__MAX
};
#line 12 "codegen.c"
//|.actionlist actionlist
static const unsigned char actionlist[17] = {
  15,40,1,15,198,192,0,15,41,1,184,10,0,0,0,195,255
};

#line 13 "codegen.c"

sequence_func_t codegen(void) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  size_t code_size;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  //| movaps xmm0, [rcx]
  //| shufps xmm0, xmm0, 0x0
  //| movaps [rcx], xmm0
  //| mov eax, 10
  //| ret
  dasm_put(Dst, 0);
#line 30 "codegen.c"
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE_READ, &dwOld);
  dasm_free(&state);
  return (sequence_func_t) code;
}
