
UserVar 戰ぐ小夜=吾爱破解论坛 CF枪王辅助V1.0正式版
UserVar 52pojie=www.52pojie.cn 吾爱破解论坛
UserVar ok=︻┳═一 ︻┳═一
UserVar suikers=↓↓设置项目↓↓ ↓↓↓下面为设置项↓↓↓
UserVar resolution=DropList{800*600:0|1024*768:1|1280*800:2}=1 游戏分辨率[目前只支持1024*768]
UserVar pv=30 开枪频率【M4推荐频率】
UserVar killx=1 单次开枪子弹数量
UserVar onekill=0 第一枪延时[爆破模式]
UserVar c1=超越简单游脚本 在游戏内可以随时修改开枪频率

SetEnv "onekill",onekill
SetEnv "killx",killx
SetEnv "resolution",resolution
SetEnv "sleep",pv

If resolution=1
  sMsg="1024*768"
EndIf

If resolution=2
  sMsg="1280*800"
EndIf

If resolution=0
  sMsg="800*600"
EndIf

//MessageBox smsg

msg="游戏分辨率为:"&sMsg&vbcrlf& "开枪频率为"&pv &vbcrlf& "单次开枪子弹数量为"&killx &vbcrlf& "第一枪延时为"&onekill &vbcrlf&"随客CF枪王辅助脚本已经开始运行，确认无误后就进入游戏吧！"

Plugin Msg.ShowScrTXT(0,0,1024, 100, msg, "0000FF")
BeginThread "fire"

While 1
  GetLastKey key
  If key=121
    sl=GetEnv("sleep")
    sl=sl+1
    SetEnv "sleep",sl
    Plugin Msg.ShowScrTXT(300,240,800,600, "开枪频率为:"&sl, "0000FF")
  ElseIf key=120
    sle=GetEnv("sleep")
    sle=sle-1
    If sle < 5
    sle=5
  EndIf

  SetEnv "sleep",sle
  Plugin Msg.ShowScrTXT(300,240,800,600, "开枪频率为:"&sle, "0000FF")
  EndIf
  Delay 10
EndWhile
EndScript

Sub fire
  XY=GetEnv("resolution")
  o=GetEnv("onekill")
  X1=380
  Y1=345
  X2=420
  Y2=355

  If XY=1
    X1=X1+112
    X2=X2+112
    Y1=Y1+84
    Y2=Y2+84
  EndIf

If XY=2
X1=X1+240
X2=X2+240
Y1=Y1+100
Y2=Y2+100
EndIf

While 1
  VBSCall FindColorEx(X1,Y1,X2,Y2,"3239A0",0,0.7,x,y)
  If x>0
    Delay o
    Gosub gun
  EndIf
  Delay 1
  VBSCall FindColorEx(X1,Y1,X2,Y2,"2A3EB8",0,0.7,x,y)
  If x>0
    Gosub gun
  EndIf
  Delay 1
  VBSCall FindColorEx(X1,Y1,X2,Y2,"1849F0",0,0.7,x,y)
  If x>0
    Gosub gun
  EndIf
  Delay 1
  VBSCall FindColorEx(X1,Y1,X2,Y2,"1849F0",0,0.7,x,y)
  If x>0
    Gosub gun
  EndIf
  Delay 1
EndWhile

Return

Sub gun
  k=GetEnv("killx")
  s=GetEnv("sleep")
  LeftClick k
Delay s
Return
