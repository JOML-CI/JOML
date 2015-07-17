	.file	"intrinsics.c"
	.intel_syntax noprefix
	.section	.text.unlikely,"x"
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	mmul_sse
	.def	mmul_sse;	.scl	2;	.type	32;	.endef
	.seh_proc	mmul_sse
mmul_sse:
	.seh_endprologue
	lea	r9, 64[rdx]
.L3:
	movss	xmm1, DWORD PTR [rdx]
	xor	eax, eax
	shufps	xmm1, xmm1, 0
	mulps	xmm1, XMMWORD PTR [rcx]
.L2:
	movss	xmm0, DWORD PTR 4[rdx+rax]
	shufps	xmm0, xmm0, 0
	mulps	xmm0, XMMWORD PTR 16[rcx+rax*4]
	add	rax, 4
	cmp	rax, 12
	addps	xmm1, xmm0
	jne	.L2
	add	rdx, 16
	movaps	XMMWORD PTR [r8], xmm1
	add	r8, 16
	cmp	rdx, r9
	jne	.L3
	ret
	.seh_endproc
	.section	.text.unlikely,"x"
.LCOLDE0:
	.text
.LHOTE0:
	.ident	"GCC: (x86_64-win32-sjlj-rev0, Built by MinGW-W64 project) 5.1.0"
