; minesweeper written in assembly for the TBC69000 cpu
; by tbcsuperman

; tile layout:
; bit 7: uncovered
; bit 6: mine
; bit 5: flag
; bit 4: red mine
; bits 0-3: # adjacent mines

; vector table (0000-01ff)
org 0000h
mov r15h, 08h
dw vBlank
dw keyPress
dw keyRelease
dw 0
dw 0
dw leftClick
dw leftRelease
dw rightClick
dw rightRelease

; data section (0200-03ff)

; interrupt toggle flags
org 0200h
db 0
db 1
db 1
db 1
db 0
db 0
leftClickEnable: db 1
db 1
db 1
db 1

; i/o state registers
org 0210h
mouseXPos: db 0
mouseYPos: db 0
currentKey: db 0

; 8-bit game state variables
org 0220h
leftMouse: db 0
rightMouse: db 0
spacebar: db 0
backspace: db 0
generatedMines: db 0
gameActive: db 0
lostGame: db 0

; 16-bit game state variables
org 0230h
currRng: dw 0
timer: dw 0
numFlags: dw 0

; tile ascii and colors
org 0240h
numbers: db 00h,31h,32h,33h,34h,35h,36h,37h,38h,39h
colors: db 00h,03h,0ch,30h,13h,21h,0ah,00h,15h,33h

; game info gui template
org 0260h
db '  +----------+  '
db '  |TIMER  REM|  '
db '  |000    000|  '
db '  +----------+  '

; tile array
org 0300h
tiles: fill 0100h, 00h

; new data section (0400-04ff)
org 0400h
dw 0
dw 0
dw 0
dw 0
dw main
dw end

; screen section (0500-07ff)
screen: equ 0500h
textColor: equ 0600h
bgColor: equ 0700h

; color constants
black: equ 00h
dark: equ 15h
light: equ 2ah
white: equ 3fh
red: equ 30h
green: equ 0ch

; game constants
numMines: equ 40

; code section (0800-0fff)
org 0800h
main:
	xnor r12, r12
	xor r13, r13
	mov r3, tiles
	call clearBoard
	call updateScreen
	sti
loop:
	hlt
	jr loop

vBlank:
	call rng
	call incTimer
	call updateInfoScreen
	reti

keyPress:
	mov r0, spacebar
	mov b0, [r0]
	orf r0, r0
	jr ne, keyPressReturn
	mov r0, currentKey
	mov b0, [r0]
	mov r1, 08h
	cmp r0, r1
	jr e, keyPressBackspace
	mov r1, 20h
	cmp r0, r1
	jr e, keyPressSpace
	jr keyPressReturn
keyPressSpace:
	mov r0, spacebar
	xnor r1, r1
	mov [r0], b1
	mov r0, leftClickEnable
	xor r1, r1
	mov [r0+], r1
	mov [r0+], r1
	mov r0, bgColor
	xor r1, r1
	mov r2, 2
	mov r3, 6
	mov r4, 12
	mov r5, 4
	call drawRectangle
	mov r0, 0200h
	mov r1, screen
	call copyRectangle
	mov r0, textColor
	xnor r1, r1
	call drawRectangle
	call updateInfoScreen
	jr keyPressSpaceEnd
keyPressSpaceEnd:
	jr keyPressReturn
keyPressBackspace:
	mov r3, tiles
	call clearBoard
	call updateScreen
	jr keyPressReturn
keyPressReturn:
	reti

keyRelease:
	mov r0, currentKey
	mov b0, [r0]
	mov r1, 20h
	cmp r0, r1
	jr e, keyReleaseSpace
	jr keyReleaseReturn
keyReleaseSpace:
	mov r0, spacebar
	xor r1, r1
	mov [r0], b1
	mov r0, leftClickEnable
	xnor r1, r1
	mov [r0+], r1
	mov [r0+], r1
	call updateScreen
	jr keyReleaseReturn
keyReleaseReturn:
	reti

leftClick:
	mov r0, lostGame
	mov b0, [r0]
	orf r0, r0
	jr e, leftClickNoLost
leftClickLost:
	reti
leftClickNoLost:
	mov r0, leftMouse
	xnor r1, r1
	mov [r0], b1
	mov r0, rightMouse
	mov b1, [r0]
	orf r1, r1
	jr ne, leftDoubleClick
leftSingleClick:
	call getMousePos
	mov r3, tiles
	mov b4, [r3+r2]
	tst r4, 4
	jr e, clickedNoVictory
clickedVictory:
	reti
clickedNoVictory:
	tst r4, 5
	jr e, clickedNoFlag
clickedFlag:
	reti
clickedNoFlag:
	push r0
	push r1
	push r2
	call generateMines
	pop r2
	pop r1
	pop r0
	call uncoverTilesBase
	call updateScreen
	reti
leftDoubleClick:
	call doubleClick
	reti

leftRelease:
	mov r0, leftMouse
	xor r1, r1
	mov [r0], b1
	reti

rightClick:
	mov r0, lostGame
	mov b0, [r0]
	orf r0, r0
	jr e, rightClickNoLost
rightClickLost:
	reti
rightClickNoLost:
	mov r0, rightMouse
	xnor r1, r1
	mov [r0], b1
	mov r0, leftMouse
	mov b1, [r0]
	orf r1, r1
	jr ne, rightDoubleClick
rightSingleClick:
	call getMousePos
	mov r0, numFlags
	mov r1, [r0]
	mov r3, tiles
	mov b4, [r3+r2]
	tst r4, 7
	jr ne, rightSingleClickEnd
	tst r4, 4
	jr ne, rightSingleClickEnd
rightSingleClickCovered:
	tst r4, 5
	jr ne, rightSingleClickDecFlags
rightSingleClickIncFlags:
	inc r1, 2 ; add 2 so it can safely fall through to the next instruction
rightSingleClickDecFlags:
	dec r1
rightSingleClickContinue:
	mov [r0], r1
	cpl r4, 5
	mov [r3+r2], b4
	call updateScreen
rightSingleClickEnd:
	reti
rightDoubleClick:
	call doubleClick
	reti

rightRelease:
	mov r0, rightMouse
	xor r1, r1
	mov [r0], b1
	reti

doubleClick:
	call getMousePos
	mov r3, tiles
	mov b4, [r3+r2]
	tst r4, 7
	jr e, doubleClickEnd
doubleClickAdjacent:
	mov r5, 0fh
	andf r4, r5
	jr e, doubleClickEnd
doubleClickCheck:
	call checkAdjacent
	call updateScreen
doubleClickEnd:
	ret

; name: printd
; desc: prints a 16-bit number in decimal
; params:
;	r0: number
;	r1: dest address (lsd first, address-1 next digit, and so on)
;	r2: digits
; returns:
;	r1-r7: DESTROYED
printd:
	mov r3, 10
	mov r4, 1
	mov r5, 10
	mov r6, 30h
printdLoop:
	orf r2, r2
	jr e, printdLoopEnd
	mov r7, r0
	umod r7, r3
	udiv r7, r4
	add r7, r6
	mov [r1-], b7
	umul r3, r5
	umul r4, r5
	dec r2
	jr printdLoop
printdLoopEnd:
	ret

; name: drawRectangle
; desc: draws a rectangle with a given color in a 16x16 matrix
; params:
;	r0: matrix ptr
;	r1: color
;	r2: xPos
;	r3: yPos
;	r4: xSize
;	r5: ySize
; locals:
;	r6: x iterator
;	r7: y iterator
;	r8: matrix index
; returns:
;	r6-r8: DESTROYED
drawRectangle:
	xor r7, r7
	mov r8, r3
	shl r8, 4
drawRectangleOuterLoop:
	xor r6, r6
	add r8, r2
drawRectangleInnerLoop:
	mov [r0+r8], b1
	inc r6
	inc r8
	cmp r6, r4
	jr ne, drawRectangleInnerLoop
drawRectangleInnerLoopEnd:
	inc r7
	shr r8, 4
	inc r8
	shl r8, 4
	cmp r7, r5
	jr ne, drawRectangleOuterLoop
drawRectangleOuterLoopEnd:
	ret

; name: copyRectangle
; desc: copies a rectangular region from the source 16x16 matrix to the destination 16x16 matrix
;	r0: src matrix ptr
;	r1: dest matrix ptr
;	r2: xPos
;	r3: yPos
;	r4: xSize
;	r5: ySize
; locals:
;	r6: x iterator
;	r7: y iterator
;	r8: matrix index
; returns:
;	r6-r9: DESTROYED
copyRectangle:
	xor r7, r7
	mov r8, r3
	shl r8, 4
copyRectangleOuterLoop:
	xor r6, r6
	add r8, r2
copyRectangleInnerLoop:
	mov b9, [r0+r8]
	mov [r1+r8], b9
	inc r6
	inc r8
	cmp r6, r4
	jr ne, copyRectangleInnerLoop
copyRectangleInnerLoopEnd:
	inc r7
	shr r8, 4
	inc r8
	shl r8, 4
	cmp r7, r5
	jr ne, copyRectangleOuterLoop
copyRectangleOuterLoopEnd:
	ret

; name: rng
; desc: updates the current rng number
; returns:
;   r0-r2: DESTROYED
;	r1: number
rng:
	mov r0, currRng
	mov r1, [r0]
	mov r2, 25173
	umul r1, r2
	mov r2, 13849
	add r1, r2
	mov [r0], r1
	ret

; name: incTimer
; desc: increments the timer by 1 every frame and caps it at 59999 (999 seconds and 59 frames)
; returns:
;	r1: timer
;	r0-r3: DESTROYED
incTimer:
    mov r0, gameActive
	mov b1, [r0]
	orf r1, r1
	ret e
incTimerGameActive:
    mov r2, timer
	mov r1, [r2]
	inc r1
    mov r3, 59999
	cmp r1, r3
	jr ne, incTimerNoOverflow
incTimerOverflow:
	dec r1
incTimerNoOverflow:
	mov [r2], r1
	ret

; name: getMousePos
; desc: gets the x and y position and the effective address of the mouse
; returns:
;	r0: xPos
;	r1: yPos
;	r2: addr
getMousePos:
	mov r0, mouseXPos
	mov b0, [r0]
	mov r1, mouseYPos
	mov b1, [r1]
	mov r2, r1
	shl r2, 4
	add r2, r0
	ret

; name: generateMines
; desc: checks if mines have already been generated and if not generates new mines while considering the tile that was clicked
; params:
;	r0: tile xPos
;	r1: tile yPos
;	r2: tile index
generateMines:
	mov r3, tiles
	mov r4, generatedMines
	mov b5, [r4]
	orf r5, r5
	ret ne
	xnor r5, r5
	mov [r4], b5
	mov r4, gameActive
	mov [r4], b5
	mov r4, numMines
generateMinesLoop:
	push r2
	call rng
	mov r0, r1
	shr r0, 8
	shl r1, 8
	shr r1, 8
	xor r1, r0
	pop r2
	cmp r1, r2
	jr e, generateMinesLoop
generateMinesLoopNoClick:
	mov b5, [r3+r1]
	tst r5, 6
	jr ne, generateMinesLoop
generateMinesLoopNoMine:
	set r5, 6
	mov [r3+r1], b5
	dec r4
	orf r4, r4
	jr ne, generateMinesLoop
generateNumbers:
	mov r3, tiles
	mov r1, 16
generateNumbersOuterLoop:
	dec r1
	mov r0, 16
generateNumbersInnerLoop:
	dec r0
	mov r2, r1
	shl r2, 4
	add r2, r0
	mov b4, [r3+r2]
	tst r4, 6
	jr e, generateNumbersNoMine
generateNumbersMine:
	call incAdjacent
generateNumbersNoMine:
	orf r0, r0
	jr ne, generateNumbersInnerLoop
generateNumbersInnerEnd:
	orf r1, r1
	jr ne, generateNumbersOuterLoop
generateNumbersOuterEnd:
	ret

; name: explodeMines
; desc: marks the given tile as red, uncovers all other mines, and disables mouse input
; params:
;	r0: tile xPos
;	r1: tile zPos
;	r2: tile array index
explodeMines:
	mov r3, tiles
	mov b4, [r3+r2]
	set r4, 4
	mov [r3+r2], b4
	mov r4, 0100h
explodeMinesLoop:
	dec r4
	mov b5, [r3+r4]
	tst r5, 6
	jr e, explodeMinesLoopNoMine
explodeMinesLoopMine:
	set r5, 7
	mov [r3+r4], b5
explodeMinesLoopNoMine:
	orf r4, r4
	jr ne, explodeMinesLoop
explodeMinesLoopEnd:
	mov r4, gameActive
	xor r5, r5
	mov [r4], b5
	mov r4, lostGame
	xnor r5, r5
	mov [r4], b5
	ret

; name: clearBoard
; desc: clears the board and resets all variables
clearBoard:
	mov r4, leftClickEnable
	xnor r5, r5
	mov [r4+], r5
	mov [r4+], r5
	xor r5, r5
	mov r4, generatedMines
	mov [r4], b5
	mov r4, gameActive
	mov [r4], b5
	mov r4, lostGame
	mov [r4], b5
	mov r4, timer
	mov [r4], r5
	mov r4, numFlags
	mov [r4], r5
	mov r4, 0100h
clearBoardLoop:
	dec r4
	mov [r3+r4], b5
	orf r4, r4
	jr ne, clearBoardLoop
clearBoardEnd:
	ret

; name: updateScreen
; desc: updates the screen
updateScreen:
	mov r0, tiles
	mov r1, screen
	mov r2, textColor
	mov r3, bgColor

; checks if the game has been won
updateScreenCheck:
	mov r4, 0100h
updateScreenCheckLoop:
	dec r4
	mov b5, [r0+r4]
	tst r5, 6
	jr ne, updateScreenCheckMine
updateScreenCheckNoMine:
	tst r5, 7
	jr e, updateScreenEntry
	jr updateScreenCheckLoopCheck
updateScreenCheckMine:
	tst r5, 7
	jr ne, updateScreenEntry
updateScreenCheckLoopCheck:
	orf r4, r4
	jr ne, updateScreenCheckLoop

; if the game has been won, update tile array
updateScreenSuccess:
	mov r4, gameActive
	xor r5, r5
	mov [r4], b5
	mov r4, 0100h
updateScreenSuccessLoop:
	dec r4
	mov b5, [r0+r4]
	tst r5, 6
	jr e, updateScreenSuccessNoMine
updateScreenSuccessMine:
	set r5, 4
	mov [r0+r4], b5
updateScreenSuccessNoMine:
	orf r4, r4
	jr ne, updateScreenSuccessLoop

; update the screen based on tile array
updateScreenEntry:
	mov r4, 0100h
updateScreenLoop:
	dec r4
	mov b5, [r0+r4]
	tst r5, 7
	jr ne, updateScreenUncovered
updateScreenCovered:
	mov r6, dark
	mov [r3+r4], b6
	tst r5, 4
	jr e, updateScreenNoVictory
updateScreenVictory:
	mov r6, green
	mov [r3+r4], b6
updateScreenNoVictory:
	tst r5, 5
	jr ne, updateScreenFlag
updateScreenNoFlag:
	xor r6, r6
	mov [r1+r4], b6
	mov [r2+r4], b6
	jr updateScreenLoopCheck
updateScreenFlag:
	mov r6, 'F'
	mov [r1+r4], b6
	xnor r6, r6
	mov [r2+r4], b6
	jr updateScreenLoopCheck
updateScreenUncovered:
	mov r6, light
	mov [r3+r4], b6
	tst r5, 6
	jr ne, updateScreenMine
updateScreenNoMine:
	shl r5, 12
	shr r5, 12
	mov r6, numbers
	mov b6, [r6+r5]
	mov [r1+r4], b6
	mov r6, colors
	mov b6, [r6+r5]
	mov [r2+r4], b6
	jr updateScreenLoopCheck
updateScreenMine:
	mov r6, '@'
	mov [r1+r4], b6
	xor r6, r6
	mov [r2+r4], b6
	mov r6, light
	mov [r3+r4], b6
	tst r5, 4
	jr e, updateScreenLoopCheck	
updateScreenMineRed:
	mov r6, red
	mov [r3+r4], b6
updateScreenLoopCheck:
	orf r4, r4
	jr ne, updateScreenLoop
updateScreenReturn:
	ret

; name: updateInfoScreen
; desc: checks if the info screen is visible and if so updates it
; params: none
; returns: none
updateInfoScreen:
    mov r0, 0762h
	mov b0, [r0]
	orf r0, r0
	ret ne
    mov r0, timer
	mov r0, [r0]
	mov r1, 60
	udiv r0, r1
	mov r1, 0585h
	mov r2, 3
	call printd
	mov r0, numMines
    mov r1, numFlags
	mov r1, [r1]
	subf r0, r1
	jr uge, updateInfoScreenMinesPos
updateInfoScreenMinesNeg:
	xnor r1, r1
	sub r1, r0
	inc r1
	mov r0, r1
    mov r1, 0589h
	mov r2, 2dh
	mov [r1], b2
updateInfoScreenMinesPos:
	mov r1, 058ch
	mov r2, 3
	call printd
	ret

left:
	dec r0
	dec r2
    ret

right:
	inc r0
	inc r2
    ret

up:
	dec r1
	decb r2, 4 ; sub r2, 16
    ret

down:
	inc r1
    incb r2, 4 ; add r2, 16
    ret

; name: adjacent
; desc: calls a function on all adjacent tiles
; params:
;   r10: function pointer (not finalized, if there are any available registers before it will be used instead)
adjacent:
    mov r5, 15
adjacentLeft:
    orf r0, r0
    jr e, adjacentRight
    call left
    call r10
    call right
adjacentTopLeft:
    orf r1, r1
    jr e, adjacentBottomLeft
    call left
    call up
    call r10
    call down
    call right
adjacentBottomLeft:
    cmp r1, r5
    jr e, adjacentRight
    call left
    call down
    call r10
    call up
    call right
adjacentRight:
    cmp r0, r5
    jr e, adjacentTop
    call right
    call r10
    call left
adjacentTopRight:
    orf r1, r1
    jr e, adjacentBottomRight
    call right
    call up
    call r10
    call down
    call left
adjacentBottomRight:
    cmp r1, r5
    jr e, adjacentTop
    call right
    call down
    call r10
    call up
    call left
adjacentTop:
    orf r1, r1
    jr e, adjacentBottom
    call up
    call r10
    call down
adjacentBottom:
    cmp r1, r5
    jr e, adjacentEnd
    call down
    call r10
    call up
adjacentEnd:
    ret

; name: uncoverTiles
; desc: uncovers all adjacent tiles with 0 adjacent tiles
; params:
;	r0: xPos
;	r1: yPos
;	r2: tile index
;	r3: tile array
; locals:
;	r4: current tile data
;	r5: constant 15 (for extracting number of adjacent mines)
; returns:
;	r4-r5: DESTROYED
uncoverTilesBase:
	push r4
	mov b4, [r3+r2]
	mov r9, 0fh
	tst r4, 6
	jr e, uncoverTilesBaseNoMine
uncoverTilesBaseMine:
	call explodeMines
	jr uncoverTilesEnd
uncoverTilesBaseNoMine:
	tst r4, 7
	jr ne, uncoverTilesEnd
uncoverTilesBaseCovered:
	set r4, 7
	mov [r3+r2], b4
	jr uncoverTiles

; recursive entry point
uncoverTilesRec:
	push r4
	mov b4, [r3+r2]
	tst r4, 5
	jr ne, uncoverTilesEnd
	tst r4, 6
	jr ne, uncoverTilesEnd
	tst r4, 7
	jr ne, uncoverTilesEnd
uncoverTilesRecAdjacent:
	set r4, 7
	mov [r3+r2], b4
	andf r4, r9
	jr ne, uncoverTilesEnd
	jr uncoverTiles

; main function
uncoverTiles:
    mov r10, uncoverTilesRec
    call adjacent
uncoverTilesEnd:
	pop r4
	ret

; name: incAdjacent
; desc: adds 1 to all adjacent tiles
; params:
;	r0: xPos
;	r1: yPos
;	r2: tile index
;	r3: tile array
; locals:
;	r4: current tile data
; returns:
;	r4-r5: DESTROYED
incAdjacent:
	mov r10, incTile
    call adjacent
incAdjacentEnd:
	ret

incTile:
	mov b4, [r3+r2]
	inc r4
	mov [r3+r2], b4
    ret

; name: checkAdjacent
; desc: checks if there is the correct number of flags around a tile and if so, uncovers the adjacent unflagged tiles
; params:
;	r0: xPos
;	r1: yPos
;	r2: tile index
;	r3: tile array
; locals:
;	r4: current tile data
;	r6: adjacent count
;	r7: scratch
; returns:
;	r4-r7: DESTROYED
checkAdjacent:
	xor r6, r6
	mov r10, checkAdj
    call adjacent
checkAdjacentEnd:
	cmp r4, r6
	ret ne
clickAdjacent:
	mov r10, clickAdj
    call adjacent
clickAdjacentEnd:
	ret

checkAdj:
	mov b7, [r3+r2]
	tst r7, 7
	jr ne, 3
	tst r7, 5
	jr e, 1
	inc r6
    ret

clickAdj:
	mov b7, [r3+r2]
	tst r7, 5
	jr ne, 3
	call uncoverTilesBase
    ret

end: