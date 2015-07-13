#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

#include "dynasm/dasm_proto.h"
#include "dynasm/dasm_x86.h"
#include "codegen.h"

|.arch x64
|.section code
|.globals GLOB_
|.actionlist actionlist

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
  | movaps xmm0, [rcx]
  | shufps xmm0, xmm0, 0x0
  | movaps [rcx], xmm0
  | mov eax, 10
  | ret
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE_READ, &dwOld);
  dasm_free(&state);
  return (sequence_func_t) code;
}
