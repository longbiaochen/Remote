; globals
$ip=@IPAddress1
$port='2777'

; socket listener ================================
UDPStartup()
$socket = UDPBind($ip, $port)
If @error <> 0 Then Exit
ConsoleWrite(StringFormat('UDP Server listening at %s:%s \n', $ip, $port))

While 1
	; socket
	$recv = UDPRecv($socket, 2048, 2)
    If $recv <> "" Then
        ConsoleWrite('Received: '&$recv[0]&@CRLF)
		; dispatch message
		dispatch($recv[0])
    EndIf
WEnd

Func dispatch($msg)
	Switch $msg
		Case "PLAY_PAUSE"
			Send('{SPACE}')
			$tooltip="Play/Pause"
		Case "VOLUME_UP"
			Send('{VOLUME_UP}')
			Send('{VOLUME_UP}')
			Send('{VOLUME_UP}')
			$tooltip="Volume Up"
		Case "VOLUME_DOWN"
			Send('{VOLUME_DOWN}')
			Send('{VOLUME_DOWN}')
			Send('{VOLUME_DOWN}')
			$tooltip="Volume Down"
		Case "CLEAR"
			$tooltip=""
		Case Else

	EndSwitch

	ToolTip($tooltip, @DesktopWidth-120, @DesktopHeight-50, "Control",1)
	; Sleep(2000)	; Sleep to give tooltip time to display

EndFunc
