; #############################################################################
; # This script is part of the 7ROF project.
; # This script takes the inputs (RCtrl + F12, RCtrl + F11) from the user and
; # then passes them to the timer script.
; #############################################################################
#NoEnv	; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input	; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%	; Ensures a consistent starting directory.
#SingleInstance Force
#Persistent
#Include, Neutron.ahk

; #############################################################################
; # Preparing Secondary Screen
; #############################################################################
Gui -Caption
SysGet, PrimaryScreenWidth, 0
SysGet, PrimaryScreenHeight, 1

SysGet,TotalScreenWidth, 78
SysGet, TotalscreenHeight, 79

SysGet, Mon1, Monitor, 1
; SysGet, Mon2, Monitor, 2
SysGet, Moni1, MonitorWorkArea, 1
; SysGet, Moni2, MonitorWorkArea, 2
; SecondaryScreenWidth := (Mon2Right - Mon2Left)
; SecondaryScreenHieght := (Mon2Bottom - Mon2Top)/1

; MsgBox, System monitor 1::: Left: %Mon1Left% -- Top: %Mon1Top% -- Right: %Mon1Right% -- Bottom %Mon1Bottom%`n`n System Monitor 2: Left: %Mon2Left% -- Top: %Mon2Top% -- Right: %Mon2Right% -- Bottom %Mon2Bottom%`n`n`n Virtual Screen Width %SecondaryScreenWidth% Virtual Screen Height %TotalScreenHeight%
; Gui , Show, x%Mon2Left% y%Mon2Top% w%SecondaryScreenWidth% h%SecondaryScreenHieght%

; #############################################################################
; # Secondary Screen Content
; #############################################################################
name := new NeutronWindow()
name.Load("index.html")
; position = x%Mon2Left% y%Mon2Top% w%SecondaryScreenWidth% h%SecondaryScreenHieght%
position = w100 h100
name.Show(position)
UpdateKey(name, "Green1", "spacer")
UpdateKey(name, "Green2", "spacer")
UpdateKey(name, "Red1", "spacer")
UpdateKey(name, "Red2", "spacer")

clicker := 0
answered := 1
m::
    If (answered = 0) {
        answered := 3
        SoundPlay, RZFWLXE-bell-hop-bell.mp3
        answered := 0

        ; answered := 0
    }
Return
>!F12::
    If (clicker = 0) {
        clicker := 1
        Goto Clicked
    }
Return
>!F11::
    If (clicker = 0) {
        clicker := -1
        Goto Clicked
    }
Return

>!F10::
    If (clicker = 0) {
        clicker := 2
        Goto Clicked
    }
Return
>!F9::
    If (clicker = 0) {
        clicker := -2
        Goto Clicked
    }
Return

Clicked:
    answered := 0
    If (abs(clicker) = 1) {
        ; Send, Green{Enter}
        If (clicker>0)
            UpdateKey(name, "Green1", "green")
        If (clicker<0)
            UpdateKey(name, "Green2", "green")
        Gui, name:Show
        ; Sleep, 100 ; sleep for 100ms to give time to breathe
        SoundPlay, buzzer.wav
    }
    If (abs(clicker) = 2) {
        ; Send, Red{Enter}
        If (clicker>0)
            UpdateKey(name, "Red2", "red")
        If (clicker<0)
            UpdateKey(name, "Red1", "red")
        Gui, name:Show
        ; Sleep, 100 ; sleep for 100ms to give time to breathe
        SoundPlay, buzzer.wav
    }
    ; Gui Margin, 0, 0
    ; Gui, Add, Text,, Hello World!
    ; Gui, Add, Button, w100 h18 ,OK
    answered := 0
    Sleep 7000	; Wait 1 second
    ; Gui , Hide
    UpdateKey(name, "Green1", "spacer")
    UpdateKey(name, "Green2", "spacer")
    UpdateKey(name, "Red1", "spacer")
    UpdateKey(name, "Red2", "spacer")
    SoundPlay, RZFWLXE-bell-hop-bell.mp3

    If (answered>0){
        If (abs(clicker) = 1){
            UpdateKey(name, "Red1", "red")
            UpdateKey(name, "Red2", "red")
        }
        If (abs(clicker) = 2){
            UpdateKey(name, "Green1", "green")
            UpdateKey(name, "Green2", "green")
        }
    }
    ; If (answered = 1){
    ;     Goto, Done_Click
    ; }
    ; ; Sleep, 10000
    ; If (answered = 1){
    ;     Goto, Done_Click
    ; }
    ; SoundPlay, RZFWLXE-bell-hop-bell.mp3

Done_Click:
    UpdateKey(name, "Green1", "spacer")
    UpdateKey(name, "Green2", "spacer")
    UpdateKey(name, "Red1", "spacer")
    UpdateKey(name, "Red2", "spacer")
    answered := 0
    clicker := 0

Return

UpdateKey(neutron, keyName, className) {
    ; Use the JavaScript function document.querySelectorAll to find elements
    ; based on a CSS selector.
    keyDivs := neutron.doc.querySelectorAll("div")

    ; Use Neutron's .Each() method to iterate through the HTMLCollection in a
    ; for loop.
    for i, div in neutron.Each(keyDivs)
    {
        ; Check if the div's innerText matches the key that was pressed
        if (div.attributes.id.nodeValue == keyName)
        {
            ; Update the div's className property to change its style on the fly
            div.className := className
        }
    }
}