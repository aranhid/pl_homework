%macro pushd 0
    push edx
    push ecx
    push ebx
    push eax
%endmacro

%macro popd 0
    pop eax
    pop ebx
    pop ecx
    pop edx
%endmacro

%macro print 2
    pushd
    mov edx, %1
    mov ecx, %2
    mov ebx, 1
    mov eax, 4
    int 0x80
    popd
%endmacro

%macro dprint 0
    pushd
    
    mov ecx, 10
    mov bx, 0
    
    %%_divide:
        mov edx, 0
        div ecx
        push dx
        inc bx
        test eax, eax
        jnz %%_divide

    mov cx, bx
    
    %%_digit:
        pop ax
        add ax, '0'
        mov [count], ax
        print 1, count
        dec cx
        mov ax, cx
        cmp cx, 0
        jg %%_digit
        
    print nlen, newline
    popd
%endmacro

%macro negdprint 0
    pushd
    
    print mlen, minus
    
    xor ax, 0xFFFF
    add ax, 1
    
    mov ecx, 10
    mov bx, 0
    
    %%_divide:
        mov edx, 0
        div ecx
        push dx
        inc bx
        test eax, eax
        jnz %%_divide

    mov cx, bx
    
    %%_digit:
        pop ax
        add ax, '0'
        mov [count], ax
        print 1, count
        dec cx
        mov ax, cx
        cmp cx, 0
        jg %%_digit
        
    print nlen, newline
    
    sub ax, 1
    xor ax, 0xFFFF
    
    popd
%endmacro

section .text

global _start

_start:

    mov ax, 0
    mov bx, 0
    
    _loop:
        add ax, [x + ebx]
        sub ax, [y + ebx]
        ; cmp ax, 0
        ; jl _negative_print_debug
        ; dprint
        ; jmp _printed_debug
            
        ; _negative_print_debug:
        ;     negdprint
            
        ; _printed_debug:
        add bx, 4
        cmp bx, xlen
    jne _loop
    
    mov cx, 0
    cmp ax, 0
    jge _divide
        mov cx, 1
        xor ax, 0xFFFF
        add ax, 1
    _divide:
        mov bx, 4
        mov dx, 0
        mul bx
        mov bx, xlen
        div bx
        cmp cx, 1
        jne _divided

    sub ax, 1
    xor ax, 0xFFFF
    
    _divided:
    cmp ax, 0
    jl _negative_print
    dprint
    jmp _printed
            
    _negative_print:
        negdprint
            
    _printed:
    
    print nlen, newline
    mov     eax, 1
    mov     ebx, 0
    int     0x80

section .data

    x dd 5, 3, 2, 6, 1, 7, 4
    xlen equ $ - x
    
    y dd 0, 10, 1, 9, 2, 8, 5
    ylen equ $ - y
    
    newline db 0xA, 0xD
    nlen equ $ - newline
    
    minus db "-"
    mlen equ $ - minus
    
section .bss
    
    count resb 1